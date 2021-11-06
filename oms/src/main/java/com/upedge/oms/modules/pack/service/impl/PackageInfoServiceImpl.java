package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.tms.SaiheTransportVo;
import com.upedge.common.utils.DateTools;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.common.dao.SaiheOrderRecordDao;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.pack.dao.*;
import com.upedge.oms.modules.pack.entity.*;
import com.upedge.oms.modules.pack.request.*;
import com.upedge.oms.modules.pack.response.PackageInfoListQueryResponse;
import com.upedge.oms.modules.pack.response.PackageInfoListResponse;
import com.upedge.oms.modules.pack.service.PackageInfoService;
import com.upedge.oms.modules.pack.service.PackageTrackingService;
import com.upedge.oms.modules.pack.vo.EchartsPie;
import com.upedge.oms.modules.pack.vo.PackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageData;
import com.upedge.oms.modules.pack.vo.UpdatePackage;
import com.upedge.oms.modules.statistics.vo.OrderSaleVo;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiOrderSource;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiGetPackagesResponse;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageInfo;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageList;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageOrderInfo;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiTransport;
import com.upedge.thirdparty.saihe.service.SaiheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class PackageInfoServiceImpl implements PackageInfoService {

    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private PackageOrderInfoDao packageOrderInfoDao;
    @Autowired
    private PackageOrderDao packageOrderDao;
    @Autowired
    private PackageTrackingDao packageTrackingDao;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private PackageUsdRateDao packageUsdRateDao;

    @Autowired
    PackageDailyCountDao packageDailyCountDao;

    @Autowired
    SaiheOrderRecordDao saiheOrderRecordDao;

    @Autowired
    private PackageTrackingService packageTrackingService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PackageCountVo selectPackageCount(PackageDailyCountRequest request) {
        PackageCountVo packageCountVo = packageOrderInfoDao.selectPackageAndOrderCount(request);
        Integer orderSplitManyPackageCount = packageOrderInfoDao.countOneAppOrderSplitManyPackage(request);
        Integer packageHaveSameOrderCount = packageOrderInfoDao.countPackageHaveSameAppOrder(request);
        Integer orderToManySaiheOrderCount = packageOrderInfoDao.countOneAppOrderToManySaiheOrder(request);
        packageCountVo.setOrderSplitManyPackageCount(orderSplitManyPackageCount);
        packageCountVo.setPackageHaveSameOrderCount(packageHaveSameOrderCount);
        packageCountVo.setOrderToManySaiheOrderCount(orderToManySaiheOrderCount);
        return packageCountVo;
    }

    /**
     * 获取某一天的包裹信息
     */
    @Override
    @Transactional(readOnly = false)
    public int updatePackageInfoByDate(PackageInfoUpdateRequest request){


        UpdatePackage updatePackage=new UpdatePackage(0);
        String updateToken= String.valueOf(IdGenerate.nextId());
        updatePackage.setUpdateToken(updateToken);
        updatePackage.setDateStart(request.getDateStart());
        updatePackage.setDateEnd(request.getDateEnd());

        processPackage(updatePackage,null);
        //根据updateToken查询 当前日期 updateToken未更新的数据 删除
        Integer packageCount=updatePackage.getPackageCount();
        checkRemoveAndUpdate(updatePackage);

        return packageCount;
    }


    //检查包裹根据token 不存在的删除
    //更新客户id
    //更新trackingMore运输商简码
    @Transactional(readOnly = false)
    public void checkRemoveAndUpdate(UpdatePackage updatePackage){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int oldCount=packageInfoDao.monthPackageCount(sdf.format(updatePackage.getDateStart()),
                sdf.format(updatePackage.getDateEnd()));
        int newCount=updatePackage.getPackageCount();
        //（有新的包裹或移除过旧的包裹）移除不存在的包裹、以及包裹订单信息
        // （包裹订单PackageOrder中可能对应多个包裹，不用移除、平台订单ClientOrderCode唯一，只存一次发货日期，销量只计算一次）
        //根据PackageOrderInfo 发货日期包裹---->PackageOrder 发货日期包裹订单 计算发货包裹销售额
        if(newCount>0||newCount!=oldCount){
            //移除包裹订单
            packageOrderInfoDao.deletePackageOrderInfo(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()),updatePackage.getUpdateToken());
            //移除包裹
            packageInfoDao.deletePackageInfo(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()),updatePackage.getUpdateToken());
            //--------------可以用java处理计算的部分---------------------------
            List<PackageDailyCount> packageDailyCounts = packageInfoDao.selectPackageDataByDate(updatePackage.getDateStart(),updatePackage.getDateEnd());
            packageDailyCountDao.savePackageDailyCounts(packageDailyCounts);
            //---------------------------------------------------------------
        }
   /*     if(newCount>0) {
            //更新客户id
            packageOrderInfoDao.updatePackageOrderAppUserId(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()));
            packageOrderInfoDao.updatePackageWholesaleOrderAppUserId(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()));
            //todo 更新trackingMore运输商简码
            //packageTrackingDao.updateTrackingMoreCode(sdf.format(updatePackage.getDateStart()),
            //        sdf.format(updatePackage.getDateEnd()));
            //更新客户id
            packageTrackingDao.updateAppUserId(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()));
            packageTrackingDao.updateWholesaleAppUserId(sdf.format(updatePackage.getDateStart()),
                    sdf.format(updatePackage.getDateEnd()));

        }*/

    }

    public void processPackage(UpdatePackage updatePackage, Integer token){

        ApiGetPackagesResponse apiGetPackagesResponse= SaiheService.getPackages(
                updatePackage.getDateStart(),updatePackage.getDateEnd(),token);
        if(apiGetPackagesResponse.getGetPackagesResult().getStatus().equals("OK")){
            Boolean isNextToken=apiGetPackagesResponse.getGetPackagesResult().isSetNextToken();
            Integer nextToken=apiGetPackagesResponse.getGetPackagesResult().getNextToken();
            //包裹处理
            ApiPackageList apiPackageList=apiGetPackagesResponse.getGetPackagesResult().getApiPackageList();
            if(apiPackageList!=null&&apiPackageList.getApiPackageList()!=null
                    &&apiPackageList.getApiPackageList().size()>0){
                List<ApiPackageInfo> packageList=apiPackageList.getApiPackageList();
                addPackageInfo(updatePackage.getUpdateToken(),packageList);
                updatePackage.setPackageCount(updatePackage.getPackageCount()+packageList.size());
            }

            if(isNextToken!=null&&nextToken!=null&&isNextToken&&nextToken!=-1){
                processPackage(updatePackage,nextToken);
            }else{
                System.out.println("结束");
            }
        }
    }




    public void addPackageInfo(String updateToken, List<ApiPackageInfo> packageList){
        List<PackageInfo> packageInfoList=new ArrayList<>();
        List<PackageOrderInfo> packageOrderInfoList=new ArrayList<>();
        List<PackageTracking> packageTrackingList=new ArrayList<>();
        for(ApiPackageInfo apiPackageInfo:packageList) {
            int i = 0;
            Integer orderSourceId = null;
            String orderSourceName = null;
            Long customerId = null;
            PackageInfo packageInfo = PackageInfo.toPackageInfo(apiPackageInfo);
            List<ApiPackageOrderInfo> apiPackageOrderInfos=apiPackageInfo.getOrderInfoList().getOrderInfoList();
            //包裹不重复订单数
            Set<String> orderCount=new HashSet<>();
            Date payTime = new Date();
            for (ApiPackageOrderInfo apiPackageOrderInfo : apiPackageOrderInfos) {
                Date pay_Time=apiPackageOrderInfo.getPayTime();
                //获取到最早支付时间
                if(pay_Time!=null&&pay_Time.compareTo(payTime)<0){
                    payTime=pay_Time;
                }
                String clientOrderCode = apiPackageOrderInfo.getClientOrderCode();
                SaiheOrderRecord saiheOrderRecord = saiheOrderRecordDao.selectByClientOrderCode(clientOrderCode);
                PackageOrderInfo packageOrderInfo = PackageOrderInfo.saihePackageOrderToAppPackageOrder(apiPackageOrderInfo,packageInfo,saiheOrderRecord);
                packageOrderInfoList.add(packageOrderInfo);
                if(i == 0){
                    orderSourceId = packageOrderInfo.getOrderSourceId();
                    orderSourceName = packageOrderInfo.getOrderSourceName();
                    customerId = packageOrderInfo.getCustomerId();
                }
            }
            if(packageInfo.getShipTime()!=null&&payTime!=null){
                packageInfo.setHandleTime(DateTools.getDistanceOfTwoDate(payTime,packageInfo.getShipTime()));
            }
            packageInfo.setUpdateToken(updateToken);
            packageInfo.setOrderCount(orderCount.size());
            packageInfo.setInfoCount(apiPackageOrderInfos.size());
            packageInfoList.add(packageInfo);
            if(!StringUtils.isBlank(apiPackageInfo.getTrackNumber())) {
                PackageTracking packageTracking = PackageTracking.saiheTrackingToPackageTracking(apiPackageInfo,packageInfo);
                packageTracking.setOrderSourceId(orderSourceId);
                packageTracking.setOrderSourceName(orderSourceName);
                packageTracking.setCustomerId(customerId);
                packageTrackingList.add(packageTracking);
            }
        }
        packageInfoDao.savePackageInfo(packageInfoList);
        packageOrderInfoDao.savePackageOrderInfo(packageOrderInfoList);
        if(packageTrackingList.size()>0) {
            packageTrackingDao.savePackageTracking(packageTrackingList);
        }
    }

    /**
     * 包裹报表数据
     * @param request
     * @return
     */
    @Override
    public BaseResponse packageCharts(PackageInfoQueryRequest request) {
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM");
        Date date;
        try {
            date = s.parse(request.getShipTime());
        } catch (ParseException e) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        HashMap<String, Object> map=new HashMap<>();
        if(date.compareTo(new Date())>0){
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //获取当前月的第一天
        String startDay= DateTools.getMonthStartDay(date);
        //获取当前月的最后一天
        String endDay=DateTools.getMonthEndDay(date);

        /**
         * 每天包裹数量
         */
        CompletableFuture<Void> future1= CompletableFuture.runAsync(()->{
            List<String> dayStr=DateTools.getMonthDays(date);
            Map<String, Integer> mapDate=DateTools.mapMonthDays(date);
            //日期在当前日期以内
            List<PackageData> dataList = packageInfoDao.packageMonthData(startDay, endDay);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            //获取当前月的总天数
            int dayNumOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int[] packArr=new int[dayNumOfMonth];
            for(PackageData data:dataList){
                String day=data.getDayDate();
                int packCount=data.getPackageCount()==null?0:data.getPackageCount();
                packArr[mapDate.get(day)-1]=packCount;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
            map.put("currDate",sdf.format(date));
            map.put("packList",packArr);
            map.put("dayList",dayStr);
        },threadPoolExecutor);

        /**
         * 包裹订单数量
         */
        CompletableFuture<Void> future2= CompletableFuture.runAsync(()->{
            List<EchartsPie> list=packageOrderInfoDao.totalPackageOrderPie(startDay,endDay);
            map.put("pieData",list);
        },threadPoolExecutor);


        //月包裹总数 月包裹订单数
        CompletableFuture<Void> future3= CompletableFuture.runAsync(()->{
            int monthPackageCount=packageInfoDao.monthPackageCount(startDay,endDay);
            map.put("monthPackageCount",monthPackageCount);
            int monthPackageOrderCount=packageOrderInfoDao.monthPackageOrderCount(startDay,endDay);
            map.put("monthPackageOrderCount",monthPackageOrderCount);
            //美元汇率
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
            PackageUsdRate packageUsdRate=packageUsdRateDao.queryPackageUsdRate(format.format(date));
            BigDecimal usdRate=new BigDecimal(PackageInfoService.USD_RATE);
            if(packageUsdRate!=null&&packageUsdRate.getUsdRate()!=null){
                usdRate=packageUsdRate.getUsdRate();
            }
            usdRate=usdRate.setScale(2, BigDecimal.ROUND_HALF_UP);
            map.put("usdRate",usdRate);
        },threadPoolExecutor);

        try {
            CompletableFuture.allOf(future1,future2,future3).get();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE,map);
    }

    /**
     * 包裹数据列表
     * @param request
     * @return
     */
    @Override
    public PackageInfoListResponse adminList(PackageInfoListRequest request) {
        request.initFromNum();
        List<PackageInfo> results = packageInfoDao.selectPackageInfo(request);
        Long total = packageInfoDao.countPackageInfo(request);
        request.setTotal(total);
        return new PackageInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * 包裹数据列表
     * @param request
     * @return
     */
    @Override
    public PackageInfoListQueryResponse adminListV2(PackageInfoListQueryRequest request) {
        request.initFromNum();
        List<PackageInfo> results = packageInfoDao.selectList(request);
        Long total = packageInfoDao.selectListCount(request);
        request.setTotal(total);
        return new PackageInfoListQueryResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
    }

    @Override
    public int countNormalOrderBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.countNormalOrderBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public int countAgainOrderBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.countAgainOrderBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public int countWholeSaleOrderBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.countWholeSaleOrderBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public int countPackageOrderBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.countPackageOrderBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalNormalWholeSaleOrderAsalesBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalNormalWholeSaleOrderAsalesBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalNormalOrderAsalesBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalNormalOrderAsalesBySource(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalPackagePurchaseCost(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalPackagePurchaseCost(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalPackageLogisticsCost(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalPackageLogisticsCost(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalPackageAgainOrderPurchaseCost(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalPackageAgainOrderPurchaseCost(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal totalPackageAgainOrderLogisticsCost(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.totalPackageAgainOrderLogisticsCost(startDay,endDay,orderSourceId);
    }


    @Override
    public BigDecimal monthRefundTrackingYesAmountByOrderSourceId(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.monthRefundTrackingYesAmountByOrderSourceId(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal monthWholeSaleRefundTrackingYesAmountByOrderSourceId(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.monthWholeSaleRefundTrackingYesAmountByOrderSourceId(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal monthRefundTrackingNoAmount(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.monthRefundTrackingNoAmount(startDay,endDay,orderSourceId);
    }

    @Override
    public BigDecimal monthWholeSaleRefundTrackingNoAmount(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.monthWholeSaleRefundTrackingNoAmount(startDay,endDay,orderSourceId);
    }

    @Override
    public List<PackageData> packageMonthAmount(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.packageMonthAmount(startDay,endDay,orderSourceId);
    }

    @Override
    public List<PackageData> packageMonthOrderAmount(String startDay, String endDay, Long orderSourceId, double usdRate) {
        return packageInfoDao.packageMonthOrderAmount(startDay,endDay,orderSourceId,usdRate);
    }

    @Override
    public List<PackageData> packageMonthWholeSaleOrderAmount(String startDay, String endDay, Long orderSourceId, double usdRate) {
        return packageInfoDao.packageMonthWholeSaleOrderAmount(startDay,endDay,orderSourceId,usdRate);
    }

    /**
     * 销售统计echarts图 右上导出当月普通订单发货包裹批发订单销售额
     */
    @Override
    public List<OrderSaleVo> listNormalOrderAsalesBySource(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.listNormalOrderAsalesBySource(startDay,endDay,orderSourceId);
    }

    /**
     * 销售统计echarts图 右上导出当月批发订单发货包裹批发订单销售额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    @Override
    public List<OrderSaleVo> exportWholesaleOrderSale(String startDay, String endDay, Long orderSourceId) {
        return packageInfoDao.exportWholesaleOrderSale(startDay,endDay,orderSourceId);
    }

    @Override
    @Transactional(readOnly =false)
    public void checkPackageIdNoTrackNumber(Page page) {
        List<PackageInfo> packageInfoList = packageInfoDao.listPackageIdNoTrackNumber(page);

        while (!CollectionUtils.isEmpty(packageInfoList)){
            List<PackageTracking> packageTrackingList = new ArrayList<>();
            List<PackageInfo> infoList=new ArrayList<>();
            for (PackageInfo packageInfo : packageInfoList) {
                ApiGetPackagesResponse apiGetPackagesResponse = SaiheService.getPackageById(packageInfo.getPackageId());
                if (apiGetPackagesResponse.getGetPackagesResult().getStatus().equals("OK")) {
                    //包裹处理
                    ApiPackageList apiPackageList = apiGetPackagesResponse.getGetPackagesResult().getApiPackageList();
                    if (apiPackageList != null && apiPackageList.getApiPackageList() != null
                            && apiPackageList.getApiPackageList().size() > 0) {
                        List<ApiPackageInfo> packageList = apiPackageList.getApiPackageList();
                        for (ApiPackageInfo apiPackageInfo : packageList) {
                            //更新包裹运输单号
                            PackageTracking packageTracking = new PackageTracking();
                            //目的地
                            packageTracking.setDestination(apiPackageInfo.getPackageCountry());
                            ApiTransport apiTransport = apiPackageInfo.getTransport();
                            if (apiTransport != null) {
                                packageTracking.setTransportId(apiTransport.getID());
                            }

                            List<ApiPackageOrderInfo> orderInfoList = apiPackageInfo.getOrderInfoList().getOrderInfoList();
                            if (orderInfoList != null && orderInfoList.size() > 0) {
                                for (ApiPackageOrderInfo apiPackageOrderInfo : orderInfoList) {
                                    packageTracking.setClientOrderCode(apiPackageOrderInfo.getClientOrderCode());
                                    ApiOrderSource apiOrderSource = apiPackageOrderInfo.getOrderSource();
                                    if (apiOrderSource != null) {
                                        //设置订单来源
                                        packageTracking.setOrderSourceId(apiOrderSource.getID());
                                        packageTracking.setOrderSourceName(apiOrderSource.getOrderSourceName());
                                    }
                                }
                            }
                            if (!StringUtils.isBlank(apiPackageInfo.getTrackNumber())) {
                                packageTracking.setTrackNumber(apiPackageInfo.getTrackNumber());
                                packageTracking.setState(0);
                                packageTracking.setShipTime(apiPackageInfo.getShipTime());
                                packageTracking.setUpdateTime(new Date());
                                packageTrackingList.add(packageTracking);
                                //更新包裹物流
                                packageInfo.setTrackNumber(apiPackageInfo.getTrackNumber());
                                packageInfo.setLogisticsId(apiPackageInfo.getLogisticsID()==null?"":apiPackageInfo.getLogisticsID());
                                infoList.add(packageInfo);
                            }
                        }
                    }
                }
            }
            //更新包裹物流
            if(infoList.size()>0){
                packageInfoDao.savePackageInfo(infoList);
            }
            //更新追踪信息
            if (packageTrackingList.size() > 0) {
                packageTrackingDao.savePackageTracking(packageTrackingList);
                List<String> trackNumbers=new ArrayList<>();
                for(PackageTracking packageTracking:packageTrackingList){
                    trackNumbers.add(packageTracking.getTrackNumber());
                }
                if(trackNumbers.size()>0) {
                    //更新trackingMore运输商简码
                    List<PackageTracking> packageTrackingResultList= packageTrackingDao.getTrackingMoreCodeByIds(trackNumbers);
                    for (PackageTracking packageTracking : packageTrackingResultList) {
                        String transportId =  packageTracking.getTransportId() == null ? "":packageTracking.getTransportId().toString();
                        Object object = redisTemplate.opsForValue().get(RedisKey.STRING_SAIHE_TRANSPORT_IDKEY + transportId);
                        if (null ==  object){
                            continue;
                        }
                        SaiheTransportVo saiheTransportVo = (SaiheTransportVo)object;
                        packageTracking.setTrackingMoreCode(saiheTransportVo.getTrackingMoreCode());
                        packageTrackingDao.updateTrackingMoreCodeById(packageTracking);
                    }

                    //更新客户id
                    packageTrackingDao.updateAppUserIdByIds(trackNumbers);
                }


            }
            page.setBoundary("id > "+ packageInfoList.get(packageInfoList.size()-1).getId());
            packageInfoList.clear();
            packageInfoList = packageInfoDao.listPackageIdNoTrackNumber(page);
        }

    }


//    @Transactional(readOnly = false)
//    public void savePackageInfo(String updateToken,List<ApiPackageInfo> packageList){
//
//        List<PackageInfo> packageInfoList=new ArrayList<>();
//        List<PackageOrderInfo> packageOrderInfoList=new ArrayList<>();
//        List<PackageOrder> packageOrderList=new ArrayList<>();
//        List<PackageTracking> packageTrackingList=new ArrayList<>();
//
//        for(ApiPackageInfo apiPackageInfo:packageList) {
//            PackageInfo packageInfo = new PackageInfo();
//            PackageTracking packageTracking = new PackageTracking();
//
//            packageInfo.setPackageId(apiPackageInfo.getPackageID());
//            packageInfo.setUpdateToken(updateToken);
//            packageInfo.setPackageCountry(apiPackageInfo.getPackageCountry());
//            packageTracking.setDestination(apiPackageInfo.getPackageCountry());
//
//            //发货时间
//            Date shipDate=apiPackageInfo.getShipTime();
//            packageInfo.setShipTime(apiPackageInfo.getShipTime());
//
//            ApiTransport apiTransport = apiPackageInfo.getTransport();
//            if (apiTransport != null) {
//                packageInfo.setTransportId(apiTransport.getID());
//                packageInfo.setTransportName(apiTransport.getTransportName());
//            }
//            packageInfo.setPackageWeight(apiPackageInfo.getPackageWeight());
//            packageInfo.setFinalShippingFee(apiPackageInfo.getFinalShippingFee());
//            packageInfo.setTrackNumber(apiPackageInfo.getTrackNumber()==null?"":apiPackageInfo.getTrackNumber());
//            packageInfo.setLogisticsId(apiPackageInfo.getLogisticsID()==null?"":apiPackageInfo.getLogisticsID());
//            packageInfo.setOrderAmount(apiPackageInfo.getOrderAmount());
//            packageInfo.setPurchaseAmount(apiPackageInfo.getPurchaseAmount());
//            if(!StringUtils.isBlank(apiPackageInfo.getClientSKU())&&apiPackageInfo.getClientSKU().length()>1000){
//                apiPackageInfo.setClientSKU("");
//            }
//            packageInfo.setClientSku(apiPackageInfo.getClientSKU());
//            packageInfo.setQuantity(apiPackageInfo.getQuantity());
//            packageInfo.setOperationUsername(apiPackageInfo.getOperationUserName());
//            packageInfo.setShipDate(shipDate);
//            //包裹订单数量
//            List<ApiPackageOrderInfo> orderInfoList=apiPackageInfo.getOrderInfoList().getOrderInfoList();
//            packageInfo.setInfoCount(orderInfoList.size());
//            //包裹生成时间
//            packageInfo.setPackageAddTime(apiPackageInfo.getPackageAddTime());
//
//            //包裹不重复订单数
//            Set<String> orderCount=new HashSet<>();
//            //获取包裹订单
//            if(orderInfoList!=null&&orderInfoList.size()>0){
//                //获取包裹订单的支付时间
//                Date payTime=orderInfoList.get(0).getPayTime();
//                for(ApiPackageOrderInfo apiPackageOrderInfo:orderInfoList){
//                    PackageOrderInfo packageOrderInfo=new PackageOrderInfo();
//                    packageOrderInfo.setPackageId(packageInfo.getPackageId());
//                    packageOrderInfo.setOrderCode(apiPackageOrderInfo.getOrderCode());
//                    orderCount.add(apiPackageOrderInfo.getOrderCode());
//                    packageOrderInfo.setClientOrderCode(apiPackageOrderInfo.getClientOrderCode());
//
//                    packageTracking.setClientOrderCode(apiPackageOrderInfo.getClientOrderCode());
//
//                    Date pay_Time=apiPackageOrderInfo.getPayTime();
//                    packageOrderInfo.setPayTime(apiPackageOrderInfo.getPayTime());
//                    packageOrderInfo.setFirstName(apiPackageOrderInfo.getFirstName());
//                    packageOrderInfo.setLastName(apiPackageOrderInfo.getLastName());
//                    packageOrderInfo.setShipDate(shipDate);
//                    ApiOrderSource apiOrderSource=apiPackageOrderInfo.getOrderSource();
//                    if(apiOrderSource!=null){
//                        packageOrderInfo.setOrderSourceId(apiOrderSource.getID());
//                        packageOrderInfo.setOrderSourceName(apiOrderSource.getOrderSourceName());
//                        packageOrderInfo.setOrderSourceType(apiOrderSource.getOrderSourceType());
//                        //设置订单来源
//                        packageTracking.setOrderSourceId(apiOrderSource.getID());
//                        packageTracking.setOrderSourceName(apiOrderSource.getOrderSourceName());
//                    }
//                    //设置从支付发货的处理时长
//                    if(apiPackageOrderInfo.getPayTime()!=null) {
//                        packageOrderInfo.setHandleTime(DateTools.getDistanceOfTwoDate(apiPackageOrderInfo.getPayTime(), packageInfo.getShipTime()));
//                    }
//                    //从包裹生成到发货的处理时长
//                    if(packageInfo.getPackageAddTime()!=null){
//                        packageOrderInfo.setPackageShipDuration(DateTools.getDistanceOfTwoDate(packageInfo.getPackageAddTime(),packageInfo.getShipTime()));
//                    }
//                    //从支付到包裹生成的处理时长
//                    if(apiPackageOrderInfo.getPayTime()!=null&&packageInfo.getPackageAddTime()!=null){
//                        packageOrderInfo.setPayPackageDuration(DateTools.getDistanceOfTwoDate(apiPackageOrderInfo.getPayTime(),packageInfo.getPackageAddTime()));
//                    }
//                    packageOrderInfoList.add(packageOrderInfo);
//
//
//                    //只存一份订单id 更新最后发货日期
//                    PackageOrder packageOrder=new PackageOrder();
//                    packageOrder.setShipDate(shipDate);
//                    packageOrder.setClientOrderCode(apiPackageOrderInfo.getClientOrderCode());
//                    packageOrderList.add(packageOrder);
//
//                    //获取到最早支付时间
//                    if(pay_Time!=null&&pay_Time.compareTo(payTime)<0){
//                        payTime=pay_Time;
//                    }
//                }
//
//                //设置从发货到支付的处理时长
//                if(packageInfo.getShipTime()!=null&&payTime!=null){
//                    packageInfo.setHandleTime(DateTools.getDistanceOfTwoDate(payTime,packageInfo.getShipTime()));
//                }
//            }
//
//            packageInfo.setOrderCount(orderCount.size());
//            packageInfoList.add(packageInfo);
//
//            if(!StringUtils.isBlank(apiPackageInfo.getTrackNumber())) {
//                packageTracking.setTrackNumber(packageInfo.getTrackNumber());
//                packageTracking.setTransportId(packageInfo.getTransportId());
//                packageTracking.setState(0);
//                packageTracking.setShipTime(packageInfo.getShipTime());
//                packageTracking.setUpdateTime(new Date());
//                packageTrackingList.add(packageTracking);
//            }
//        }
//        packageInfoDao.savePackageInfo(packageInfoList);
//        System.out.println(packageOrderInfoList.size());
//        packageOrderInfoDao.savePackageOrderInfo(packageOrderInfoList);
//        packageOrderDao.savePackageOrder(packageOrderList);
//        if(packageTrackingList.size()>0) {
//            packageTrackingDao.savePackageTracking(packageTrackingList);
//        }
//    }
}