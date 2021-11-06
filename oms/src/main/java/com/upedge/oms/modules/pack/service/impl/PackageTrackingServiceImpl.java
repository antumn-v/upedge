package com.upedge.oms.modules.pack.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.TmsFeignClient;

import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.ship.vo.Duration;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.tms.SaiheTransportVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pack.dao.PackageTrackingDao;
import com.upedge.oms.modules.pack.entity.PackageTracking;
import com.upedge.oms.modules.pack.request.AnalysisLogisticsRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingListRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingRefreshRequest;
import com.upedge.oms.modules.pack.service.PackageTrackingService;
import com.upedge.oms.modules.pack.vo.TrackTableItemVo;
import com.upedge.oms.modules.pack.vo.TrackTableVo;
import com.upedge.thirdparty.trackingmore.api.TrackingMoreApi;
import com.upedge.thirdparty.trackingmore.entity.GetTracking;
import com.upedge.thirdparty.trackingmore.entity.PostTracking;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class PackageTrackingServiceImpl implements PackageTrackingService {

    @Autowired
    private PackageTrackingDao packageTrackingDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private  TmsFeignClient tmsFeignClient;
    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String trackNumber) {
        PackageTracking record = new PackageTracking();
        record.setTrackNumber(trackNumber);
        return packageTrackingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PackageTracking record) {
        return packageTrackingDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PackageTracking record) {
        return packageTrackingDao.insert(record);
    }

    @Override
    public BaseResponse packageTrackingList(PackageTrackingListRequest request) {
        request.initFromNum();
        List<PackageTracking> packageTrackings = packageTrackingDao.selectPackageTracking(request);
        Long total = packageTrackingDao.countPackageTracking(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,packageTrackings,request);
    }

    @Override
    public BaseResponse refreshTrackingState(PackageTrackingRefreshRequest request) {
        List<PackageTracking> packageTrackings = packageTrackingDao.selectPackingTrackingForRefresh(request);
        if(ListUtils.isEmpty(packageTrackings)){
            return BaseResponse.failed("物流信息不存在");
        }
        packageTrackings.forEach(packageTracking -> {
            refreshTracking(packageTracking);
        });

        return BaseResponse.success();
    }

    boolean refreshTracking(PackageTracking packageTracking){
        String trackNumber = packageTracking.getTrackNumber();
        PostTracking postTracking = new PostTracking();
        GetTracking getTracking = TrackingMoreApi.refreshTrackState(postTracking);
        if(null != getTracking){
            packageTracking = new PackageTracking();
            packageTracking.setTrackNumber(trackNumber);
            packageTracking.setTrackStatus(getTracking.getStatus());
            packageTracking.setOnlineTime(getTracking.getOriginInfo().getItemReceived());
            packageTracking.setSignedTime(getTracking.getLastUpdateTime());
            packageTracking.setUpdateTime(new Date());
            packageTrackingDao.updateByPrimaryKeySelective(packageTracking);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public PackageTracking selectByPrimaryKey(String trackNumber){
        PackageTracking record = new PackageTracking();
        record.setTrackNumber(trackNumber);
        return packageTrackingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PackageTracking record) {
        return packageTrackingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PackageTracking record) {
        return packageTrackingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PackageTracking> select(Page<PackageTracking> record){
        record.initFromNum();
        return packageTrackingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PackageTracking> record){
        return packageTrackingDao.count(record);
    }

    /**
     * 物流分析
     * @param analysisLogistics
     * @return
     */
    @Override
    public BaseResponse analysisLogistics(AnalysisLogisticsRequest analysisLogistics,Session session) {

        analysisLogistics.initFromNum();
        analysisLogistics.initDateRange();
        Map<Integer, List<TrackTableItemVo>> idMap=new HashMap<>();
        List<TrackTableItemVo> packageTrackingList  =   packageTrackingDao.getAnalysisLogisticsList(analysisLogistics);
        Long count = packageTrackingDao.getAnalysisLogisticsCount(analysisLogistics);
        for (TrackTableItemVo trackTableItemVo : packageTrackingList) {
            Integer transportId=trackTableItemVo.getTransportId();
            if(transportId==null){
                continue;
            }
            // 获取   ShippingMethod
            ShippingMethodVo shippingMethodVo = new ShippingMethodVo();
            shippingMethodVo.setSaiheTransportId(transportId);
            BaseResponse baseResponse = tmsFeignClient.getShippingMethodByTransportId(shippingMethodVo);

            if (baseResponse.getCode() == ResultCode.SUCCESS_CODE && baseResponse.getData() != null){
                ShippingMethodVo smResponse = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), ShippingMethodVo.class);
                trackTableItemVo.setTransportName(smResponse.getSaiheTransportName());
                trackTableItemVo.setShippingMethod(smResponse.getName());
            }
            if(idMap.containsKey(transportId)){
                idMap.get(transportId).add(trackTableItemVo);
            }else {
                List<TrackTableItemVo> itemList=new ArrayList<>();
                itemList.add(trackTableItemVo);
                idMap.put(transportId,itemList);
            }
        }
        List<TrackTableVo> list = processingList(idMap, analysisLogistics);
        // 排序
        sortList(list,analysisLogistics.getOrderBy());
        analysisLogistics.setTotal(count);
        return BaseResponse.success(list,analysisLogistics);
    }

    @Override
    public void refreshBlankStatus() {
        Page page = new Page();
        page.initFromNum();
        page.setBoundary("track_number  > 0");
        page.setOrderBy("track_number asc");
        page.setPageSize(500);
        refreshBlankStatus(page);
    }

    /**
     * 前一天的包裹 上传追踪信息
     * @param time
     */
    @Override
    public void updateTrackingMoreByDate(Calendar time) {
        PackageTrackingRefreshRequest request = new PackageTrackingRefreshRequest();
        request.setDateDay(DateUtils.formatDate(time.getTime()));
        Page<PackageTrackingRefreshRequest> page = new Page<>();
        page.initFromNum();
        page.setPageSize(500);
        page.setT(request);
        page.setOrderBy("track_number ASC");
        page.setBoundary("track_number > 0");
        updateTrackingMoreByDate(page);
    }

    public void updateTrackingMoreByDate(Page<PackageTrackingRefreshRequest> page) {
        List<PackageTracking> packageTrackingList = packageTrackingDao.selectPackingTrackingForRefreshPage(page);
        while (!CollectionUtils.isEmpty(packageTrackingList)){

            page.setBoundary("track_number > '" + packageTrackingList.get(packageTrackingList.size()-1).getTrackNumber()+"'");
            packageTrackingList.clear();
            packageTrackingList = packageTrackingDao.selectPackingTrackingForRefreshPage(page);
        }
    }

    private void refreshBlankStatus(Page page) {
        List<PackageTracking> packageTrackingList = packageTrackingDao.refreshBlankStatus(page);
        while (!CollectionUtils.isEmpty(packageTrackingList)){
            for (PackageTracking packageTracking : packageTrackingList) {
                Object object = redisTemplate.opsForValue().get(RedisKey.STRING_SAIHE_TRANSPORT_IDKEY + packageTracking.getTransportId());
                if (null == object){
                    continue;
                }
                SaiheTransportVo  saiheTransportVo = (SaiheTransportVo)object;
                packageTracking.setTransportName(saiheTransportVo.getTransportName());
                refreshTracking(packageTracking);
            }
            page.setBoundary("track_number > '" + packageTrackingList.get(packageTrackingList.size()-1).getTrackNumber()+"'");
            packageTrackingList = packageTrackingDao.refreshBlankStatus(page);
        }
    }

    /**
     * 各种排序
     * @param list
     * @param orderBy
     */
    private void sortList(List<TrackTableVo> list, String orderBy) {
        if(!StringUtils.isBlank(orderBy) && orderBy.contains("a.avgOnlineD desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均上网时效进行降序排列
                    if (o1.getAvgOnline() < o2.getAvgOnline()) {
                        return 1;
                    }
                    if (o1.getAvgOnline() == o2.getAvgOnline()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.avgOnlineD asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均上网时效进行升序排列
                    if (o1.getAvgOnline() > o2.getAvgOnline()) {
                        return 1;
                    }
                    if (o1.getAvgOnline() == o2.getAvgOnline()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }

        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.avgSignedD desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均签收时效进行降序排列
                    if (o1.getAvgSigned() < o2.getAvgSigned()) {
                        return 1;
                    }
                    if (o1.getAvgSigned() == o2.getAvgSigned()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.avgSignedD asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均签收时效进行升序排列
                    if (o1.getAvgSigned() > o2.getAvgSigned()) {
                        return 1;
                    }
                    if (o1.getAvgSigned() == o2.getAvgSigned()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }

        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.avgD desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均总时效进行降序排列
                    if (o1.getAvg() < o2.getAvg()) {
                        return 1;
                    }
                    if (o1.getAvg() == o2.getAvg()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.avgD asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照平均总时效进行升序排列
                    if (o1.getAvg() > o2.getAvg()) {
                        return 1;
                    }
                    if (o1.getAvg() == o2.getAvg()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }

        if(StringUtils.isBlank(orderBy)||orderBy.contains("a.packCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照包裹数量进行升序排列
                    if (o1.getPackCount() > o2.getPackCount()) {
                        return 1;
                    }
                    if (o1.getPackCount() == o2.getPackCount()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }

        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.packCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    //按照包裹数量进行降序排列
                    if (o1.getPackCount() < o2.getPackCount()) {
                        return 1;
                    }
                    if (o1.getPackCount() == o2.getPackCount()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }

        //a.pendingCount desc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.pendingCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照查询中包裹百分比进行降序排列
                    if ((o1.getPendingCount()*1.0/o1.getPackCount()) < (o2.getPendingCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getPendingCount()*1.0/o1.getPackCount()) == (o2.getPendingCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.pendingCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照查询中包裹百分比进行升序排列
                    if ((o1.getPendingCount()*1.0/o1.getPackCount()) > (o2.getPendingCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getPendingCount()*1.0/o1.getPackCount()) == (o2.getPendingCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.notfoundCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.notfoundCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照查询不到包裹百分比进行降序排列
                    if ((o1.getNotfoundCount()*1.0/o1.getPackCount()) < (o2.getNotfoundCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getNotfoundCount()*1.0/o1.getPackCount()) == (o2.getNotfoundCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.notfoundCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照查询不到包裹百分比进行升序排列
                    if ((o1.getNotfoundCount()*1.0/o1.getPackCount()) > (o2.getNotfoundCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getNotfoundCount()*1.0/o1.getPackCount()) == (o2.getNotfoundCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.transitCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.transitCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照运输途中包裹百分比进行降序排列
                    if ((o1.getTransitCount()*1.0/o1.getPackCount()) <(o2.getTransitCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getTransitCount()*1.0/o1.getPackCount()) == (o2.getTransitCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.transitCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照运输途中包裹百分比进行升序排列
                    if ((o1.getTransitCount()*1.0/o1.getPackCount()) > (o2.getTransitCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getTransitCount()*1.0/o1.getPackCount()) == (o2.getTransitCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.pickupCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.pickupCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照到达待取包裹百分比进行降序排列
                    if ((o1.getPickupCount()*1.0/o1.getPackCount()) <(o2.getPickupCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getPickupCount()*1.0/o1.getPackCount()) ==(o2.getPickupCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.pickupCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照到达待取包裹百分比进行升序排列
                    if ((o1.getPickupCount()*1.0/o1.getPackCount()) >(o2.getPickupCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getPickupCount()*1.0/o1.getPackCount()) ==(o2.getPickupCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.deliveredCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.deliveredCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照成功签收包裹百分比进行降序排列
                    if ((o1.getDeliveredCount()*1.0/o1.getPackCount()) <(o2.getDeliveredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getDeliveredCount()*1.0/o1.getPackCount()) ==(o2.getDeliveredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.deliveredCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照成功签收包裹百分比进行升序排列
                    if ((o1.getDeliveredCount()*1.0/o1.getPackCount()) >(o2.getDeliveredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getDeliveredCount()*1.0/o1.getPackCount()) ==(o2.getDeliveredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.undeliveredCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.undeliveredCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照投递失败包裹百分比进行降序排列
                    if ((o1.getUndeliveredCount()*1.0/o1.getPackCount()) <(o2.getUndeliveredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getUndeliveredCount()*1.0/o1.getPackCount()) ==(o2.getUndeliveredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.undeliveredCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照投递失败包裹百分比进行升序排列
                    if ((o1.getUndeliveredCount()*1.0/o1.getPackCount()) >(o2.getUndeliveredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getUndeliveredCount()*1.0/o1.getPackCount()) ==(o2.getUndeliveredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.exceptionCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.exceptionCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照可能异常包裹百分比进行降序排列
                    if ((o1.getExceptionCount()*1.0/o1.getPackCount()) <(o2.getExceptionCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getExceptionCount()*1.0/o1.getPackCount()) ==(o2.getExceptionCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.exceptionCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照可能异常包裹百分比进行升序排列
                    if ((o1.getExceptionCount()*1.0/o1.getPackCount()) >(o2.getExceptionCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getExceptionCount()*1.0/o1.getPackCount()) ==(o2.getExceptionCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        //a.expiredCount asc
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.expiredCount desc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return 1;
                    }
                    if(o2.getPackCount()==0){
                        return -1;
                    }
                    //按照运输过久包裹百分比进行降序排列
                    if ((o1.getExpiredCount()*1.0/o1.getPackCount())<(o2.getExpiredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getExpiredCount()*1.0/o1.getPackCount())==(o2.getExpiredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        if(!StringUtils.isBlank(orderBy)&&orderBy.contains("a.expiredCount asc")) {
            Collections.sort(list, new Comparator<TrackTableVo>() {
                public int compare(TrackTableVo o1, TrackTableVo o2) {
                    if(o1.getPackCount()==0&&o2.getPackCount()==0){
                        return 0;
                    }
                    if(o1.getPackCount()==0){
                        return -1;
                    }
                    if(o2.getPackCount()==0){
                        return 1;
                    }
                    //按照运输过久包裹百分比进行升序排列
                    if ((o1.getExpiredCount()*1.0/o1.getPackCount())>(o2.getExpiredCount()*1.0/o2.getPackCount())) {
                        return 1;
                    }
                    if ((o1.getExpiredCount()*1.0/o1.getPackCount())==(o2.getExpiredCount()*1.0/o2.getPackCount())) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
    }

    /**
     * 统计TrackStatus 数量   计算：平均签收时效   平均上网时效   平均总时效
     * @param idMap
     * @return
     */
    private List<TrackTableVo> processingList(Map<Integer, List<TrackTableItemVo>> idMap  , AnalysisLogisticsRequest analysisLogistics){
        List<TrackTableVo> list = new ArrayList<>();

        for(Map.Entry<Integer, List<TrackTableItemVo>> entry:idMap.entrySet()){
            TrackTableVo trackTable=new TrackTableVo();
            trackTable.setTransportId(entry.getKey());
            Integer total=0;
            for(TrackTableItemVo item:entry.getValue()){
                trackTable.setTransportName(item.getTransportName());
                trackTable.setShippingMethod(item.getShippingMethod());
                Integer count=item.getCount()==null?0:item.getCount();
                total=total+count;
                if(StringUtils.isBlank(item.getTrackStatus())){
                    continue;
                }
                switch (item.getTrackStatus()){
                    case "pending"://查询中
                        trackTable.setPendingCount(count);
                        break;
                    case "notfound"://查询不到
                        trackTable.setNotfoundCount(count);
                        break;
                    case "transit"://运输途中
                        trackTable.setTransitCount(count);
                        break;
                    case "pickup"://到达待取
                        trackTable.setPickupCount(count);
                        break;
                    case "delivered"://成功签收
                        trackTable.setDeliveredCount(count);
                        break;
                    case "undelivered"://投递失败
                        trackTable.setUndeliveredCount(count);
                        break;
                    case "exception"://可能异常
                        trackTable.setExceptionCount(count);
                        break;
                    case "expired"://运输过久
                        trackTable.setExpiredCount(count);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getTrackStatus());
                }
            }
            //1平均签收时效
            Duration avgSignedDuration=getDuration(analysisLogistics.getT().getShipDateStart(),
                    analysisLogistics.getT().getShipDateEnd(),analysisLogistics.getT().getOrderSourceId(),analysisLogistics.getT().getCustomerId(),1,entry.getKey(),analysisLogistics.getT().getDestination());
            trackTable.setAvgSigned(avgSignedDuration.getDuration());
            trackTable.setAvgSignedD(avgSignedDuration.getInfo());
            //2平均上网时效
            Duration avgOnlineDuration=getDuration(analysisLogistics.getT().getShipDateStart(),
                    analysisLogistics.getT().getShipDateEnd(),analysisLogistics.getT().getOrderSourceId(),analysisLogistics.getT().getCustomerId(),2,entry.getKey(),analysisLogistics.getT().getDestination());
            trackTable.setAvgOnline(avgOnlineDuration.getDuration());
            trackTable.setAvgOnlineD(avgOnlineDuration.getInfo());
            //3平均总时效
            Duration avgDuration=getDuration(analysisLogistics.getT().getShipDateStart(),
                    analysisLogistics.getT().getShipDateEnd(),analysisLogistics.getT().getOrderSourceId(),analysisLogistics.getT().getCustomerId(),3,entry.getKey(),analysisLogistics.getT().getDestination());
            trackTable.setAvg(avgDuration.getDuration());
            trackTable.setAvgD(avgDuration.getInfo());

            trackTable.setPackCount(total);
            list.add(trackTable);
        }
        return list;
    }
    //获取时效数据信息
    public Duration getDuration(String startDay, String endDay,
                                Integer orderSourceId, Long customer, Integer durationType,
                                Integer transportId, String destination){

        //总时效
        long totalDuration=packageTrackingDao.totalTrackDuration(startDay,endDay,orderSourceId,customer,durationType,transportId,destination);

        //获取时效包裹总数
        Integer allCount=packageTrackingDao.countTrackDurationWithInDay(startDay,endDay,orderSourceId,customer,null,durationType,transportId,destination);

        //平均时效小时 平均时效分 平均时效秒

        long avgDuration=0,avgDurationD=0,avgDurationH=0,avgDurationM=0,avgDurationS=0;

        //平均时效
        if(allCount!=0) {
            avgDuration=totalDuration/allCount;
            long avgD=avgDuration;
            //天
            avgDurationD=avgDuration/24/3600;
            avgD=avgD-avgDurationD*3600*24;
            //小时
            avgDurationH=avgD/3600;
            avgD=avgD-avgDurationH*3600;
            //分
            avgDurationM=avgD/60;
            avgD=avgD-avgDurationM*60;
            //秒
            avgDurationS=avgD;
        }

        String res=avgDurationD+"d"+avgDurationH+"h<br/>"+allCount;
        Duration duration=new Duration();
        duration.setDuration(avgDuration);
        duration.setInfo(res);
        return duration;
    }
}