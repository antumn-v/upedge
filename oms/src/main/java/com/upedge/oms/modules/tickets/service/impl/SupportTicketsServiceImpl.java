package com.upedge.oms.modules.tickets.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.config.HostConfig;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.*;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.tickets.dao.SupportTicketsCountDao;
import com.upedge.oms.modules.tickets.dao.SupportTicketsDao;
import com.upedge.oms.modules.tickets.dao.SupportTicketsMessageDao;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import com.upedge.oms.modules.tickets.entity.SupportTicketsMessage;
import com.upedge.oms.modules.tickets.request.*;
import com.upedge.oms.modules.tickets.response.SupportTicketsInfoResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsListResponse;
import com.upedge.oms.modules.tickets.service.SupportTicketsService;
import com.upedge.oms.modules.tickets.vo.CustomerTicketVo;
import com.upedge.oms.modules.tickets.vo.SupportTicketsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class SupportTicketsServiceImpl implements SupportTicketsService {

    @Autowired
    private SupportTicketsDao supportTicketsDao;
    @Autowired
    private SupportTicketsMessageDao supportTicketsMessageDao;
    @Autowired
    private SupportTicketsCountDao supportTicketsCountDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        SupportTickets record = new SupportTickets();
        record.setId(id);
        return supportTicketsDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SupportTickets record) {
        return supportTicketsDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SupportTickets record) {
        return supportTicketsDao.insert(record);
    }

    @Override
    public BaseResponse customerTicketList(CustomerTicketListRequest request) {
        request.initFromNum();
        List<CustomerTicketVo> ticketVos = supportTicketsDao.selectCustomerTicketList(request);
        if(ListUtils.isNotEmpty(ticketVos)){
            Date date = new Date();
            ticketVos.forEach(customerTicketVo -> {
                customerTicketVo.setProcessTime(DateUtils.getDistanceOfTwoDate(customerTicketVo.getCreateTime(),date));
            });
        }
        Long count = supportTicketsDao.selectCustomerTicketCount(request);
        request.setTotal(count);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,ticketVos,request);
    }

    /**
     *
     */
    public SupportTickets selectByPrimaryKey(Long id) {
        return supportTicketsDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(SupportTickets record) {
        return supportTicketsDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(SupportTickets record) {
        return supportTicketsDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<SupportTickets> select(Page<SupportTickets> record) {
        record.initFromNum();
        return supportTicketsDao.select(record);
    }

    /**
     *
     */
    public long count(Page<SupportTickets> record) {
        return supportTicketsDao.count(record);
    }

    /**
     * SupportTickets列表
     *
     * @param request
     * @return
     */
    @Override
    public SupportTicketsListResponse adminList(SupportTicketsListRequest request) {
        request.initFromNum();
        List<SupportTickets> results = supportTicketsDao.select(request);
        Long total = supportTicketsDao.count(request);
        request.setTotal(total);
        List<SupportTicketsVo> supportTicketsVoList = new ArrayList<>();
        Set<Long> orderIds = new HashSet<>();
        Set<Long> ticketIds = new HashSet<>();
        results.forEach(supportTickets -> {
            SupportTicketsVo supportTicketsVo = new SupportTicketsVo();
            BeanUtils.copyProperties(supportTickets, supportTicketsVo);
            //查询处理时效 当前距离创建时间过去天数/7天 百分百====改成天数
            Integer passDay = 0;
            if (supportTickets.getState() == 1) {
                passDay = (int) DateTools.getDistanceOfTwoDay(supportTickets.getCreateTime(), supportTickets.getFinishTime());
            } else {
                passDay = (int) DateTools.getDistanceOfTwoDay(supportTickets.getCreateTime(), new Date());
            }
            orderIds.add(supportTickets.getOrderId());
            supportTicketsVo.setPercentDay(passDay);
            ticketIds.add(supportTickets.getId());
            supportTicketsVoList.add(supportTicketsVo);
        });


        CompletableFuture<Void> orderNumberFuture = CompletableFuture.runAsync(() -> {
            //店铺订单Number
            Map<Long, List<StoreOrderRelate>> listMap = orderService.listOrderNumber(orderIds);
            supportTicketsVoList.forEach(supportTicketsVo -> {
                List<StoreOrderRelate> list = listMap.get(supportTicketsVo.getOrderId());
                if (list != null) {
                    StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
                    for (StoreOrderRelate relate : list) {
                        stringJoiner.add(relate.getPlatOrderName());
                    }
                    supportTicketsVo.setOrderNumber(stringJoiner.toString());
                }
            });
        }, threadPoolExecutor);


        CompletableFuture<Void> msgCountFuture = CompletableFuture.runAsync(() -> {
            //统计app发送的未读消息
            List<SupportTicketsVo> msgCountList = supportTicketsMessageDao.listMsgCountByTicketIds(ticketIds);
            Map<Long, Integer> msgCountMap = new HashMap<>();
            msgCountList.forEach(supportTicketsVo -> {
                msgCountMap.put(supportTicketsVo.getId(), supportTicketsVo.getMsgCount());
            });
            supportTicketsVoList.forEach(supportTicketsVo -> {
                Integer msgCount = msgCountMap.get(supportTicketsVo.getId());
                msgCount = msgCount == null ? 0 : msgCount;
                supportTicketsVo.setMsgCount(msgCount);
            });
        }, threadPoolExecutor);

        //客户经理
        CompletableFuture<Void> manageFuture = CompletableFuture.runAsync(() -> {
            for (SupportTicketsVo supportTicketsVo : supportTicketsVoList) {
              /*  BaseResponse baseResponse = umsFeignClient.getCustomerManager(supportTicketsVo.getCustomerId());
                if (baseResponse.getCode() == ResultCode.SUCCESS_CODE){
                    ManagerInfoVo managerInfoVo =   JSON.parseObject(JSON.toJSONString( baseResponse.getData()),ManagerInfoVo.class);
                    if (managerInfoVo != null){
                        supportTicketsVo.setManagerCode(managerInfoVo.getManagerCode());
                    }
                }*/
                String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(supportTicketsVo.getCustomerId()));
                supportTicketsVo.setManagerCode(managerCode);
            }
        }, threadPoolExecutor);

        //  客户信息
        CompletableFuture<Void> customerFuture = CompletableFuture.runAsync(() -> {
            for (SupportTicketsVo supportTicketsVo : supportTicketsVoList) {
                BaseResponse customerResponse = umsFeignClient.customerInfo(supportTicketsVo.getCustomerId());
                if (customerResponse.getCode() == ResultCode.SUCCESS_CODE){
                    CustomerVo customerVo =   JSON.parseObject(JSON.toJSONString( customerResponse.getData()),CustomerVo.class);
                    if (customerVo != null){
                        supportTicketsVo.setCustomerLoginName(customerVo.getLoginName());
                        supportTicketsVo.setCustomerName(customerVo.getUsername());
                    }
                }
            }
        }, threadPoolExecutor);


        try {
            CompletableFuture.allOf(orderNumberFuture, msgCountFuture,manageFuture,customerFuture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new SupportTicketsListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTicketsVoList, request);
    }

    /**
     * 开启support tickets
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse openTicket(OpenTicketRequest request, Session session) {
        //一个订单只能有一个开启状态的ticket
        SupportTickets ticket = supportTicketsDao.selectOpenTicketByOrderId(request.getOrderId());
        Order upedgeOrder = orderDao.selectByPrimaryKey(request.getOrderId());
        if (upedgeOrder == null || upedgeOrder.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "The order does not exist or the order has not been paid");
        }
        BaseResponse customerInfoResponse = umsFeignClient.customerInfo(upedgeOrder.getCustomerId());
        //CustomerVo customerVo = JSONObject.parseObject(customerInfoResponse.getData().toString()).toJavaObject(CustomerVo.class);
        Object data = customerInfoResponse.getData();
        CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(data), CustomerVo.class);
        ManagerInfoVo managerInfoVo = omsRedisService.getCustomerManager(upedgeOrder.getManagerCode(), upedgeOrder.getCustomerId());
        if (null == ticket) {

            ticket = new SupportTickets();
            ticket.setId(IdGenerate.nextId());
            ticket.setCustomerName(customerVo.getUsername());
            ticket.setOrderId(request.getOrderId());
            ticket.setCustomerId(upedgeOrder.getCustomerId());
            ticket.setManagerCode(managerInfoVo.getManagerCode());
            ticket.setManagerCustomerId(managerInfoVo.getCustomerId());
            ticket.setManagerName(managerInfoVo.getManagerName());
            ticket.setManagerCode(managerInfoVo.getManagerCode());
            //0:processing  1:solved
            ticket.setState(0);
            ticket.setDescribes(request.getMsg());
            int timesCount = supportTicketsDao.countTicketByOrderId(request.getOrderId());
            ticket.setTimesCount(timesCount + 1);
            //0:app 1:admin
            ticket.setLastSource(1);
            ticket.setCreateTime(new Date());
            supportTicketsDao.insert(ticket);
            SupportTicketsCount supportTicketsCount = new SupportTicketsCount();
            supportTicketsCount.setId(IdGenerate.nextId());
            supportTicketsCount.setTicketId(ticket.getId());
            supportTicketsCount.setANum(0);
            supportTicketsCount.setBNum(0);
            supportTicketsCount.setMessageAll(1);
            supportTicketsCount.setManagerCode(managerInfoVo.getManagerCode());
            supportTicketsCount.setCustomerId(ticket.getCustomerId());
            supportTicketsCountDao.insert(supportTicketsCount);
        }else {
            supportTicketsCountDao.addMessageAllByTicketId(ticket.getId(),ticket.getManagerCode());
        }

        SupportTicketsMessage ticketsMessage = new SupportTicketsMessage();
        ticketsMessage.setTicketId(ticket.getId());
        ticketsMessage.setMessage(request.getMsg());
        ticketsMessage.setSenderUserId(session.getId());
        //admin发送消息
        if (ticket.getManagerCustomerId().equals(session.getCustomerId())) {
            ticketsMessage.setSenderCustomerId(ticket.getManagerCustomerId());
            ticketsMessage.setReceiverCustomerId(ticket.getCustomerId());
            ticketsMessage.setReceiverUserId(customerVo.getId());
        } else {
            //app发送
            ticketsMessage.setSenderCustomerId(ticket.getCustomerId());
            ticketsMessage.setReceiverCustomerId(ticket.getManagerCustomerId());
            ticketsMessage.setReceiverUserId(managerInfoVo.getCustomerSignupUserId());
        }
        ticketsMessage.setSendTime(new Date());
        //0:app 1:admin
        ticketsMessage.setSource(session.getApplicationId());
        //0 消息未读 1消息已读
        ticketsMessage.setState(0);
        //客户消息回复标记 0:未回复 1:12小时内回复 2:24小时内回复 3:24外回复
        ticketsMessage.setFlag(0);
        supportTicketsMessageDao.insert(ticketsMessage);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,ticket);
    }

    /**
     * 关闭 support tickets
     *
     * @param ticketId
     * @param session
     * @return
     */
    @Override
    public BaseResponse closeTicket(Long ticketId, Session session) {

        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(ticketId);
        if (supportTickets == null || supportTickets.getState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket不存在!");
        }
        if (supportTickets.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket已关闭!");
        }
        //获取后台操作人信息
        String userCode = String.valueOf(session.getId());

        //更新supportTickets状态
        SupportTickets tickets = new SupportTickets();
        tickets.setId(ticketId);
        //0:prossing  1:solved
        tickets.setState(1);
        tickets.setFinishTime(new Date());
        supportTicketsDao.updateByPrimaryKeySelective(tickets);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 发送文本消息
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse sendTextMsg(SendTextMsgRequest request, Session session) {
        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(request.getTicketId());
        if (supportTickets == null || supportTickets.getState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket不存在!");
        }
        if (supportTickets.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket已关闭!");
        }
        Order upedgeOrder = orderDao.selectByPrimaryKey(supportTickets.getOrderId());
        if (upedgeOrder == null || upedgeOrder.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "The order does not exist or the order has not been paid");
        }
        BaseResponse customerInfoResponse = umsFeignClient.customerInfo(upedgeOrder.getCustomerId());
        //CustomerVo customerVo = JSONObject.parseObject(customerInfoResponse.getData().toString()).toJavaObject(CustomerVo.class);
        Object data = customerInfoResponse.getData();
        CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(data), CustomerVo.class);
        ManagerInfoVo managerInfoVo = omsRedisService.getCustomerManager(upedgeOrder.getManagerCode(), upedgeOrder.getCustomerId());


        SupportTicketsMessage supportTicketsMessage = new SupportTicketsMessage();
        supportTicketsMessage.setTicketId(request.getTicketId());
        supportTicketsMessage.setState(0);
        supportTicketsMessage.setSource(session.getApplicationId());
        supportTicketsMessage.setSenderUserId(session.getId());
        //admin发送消息
        int msgType = 0;
        if (supportTickets.getManagerCustomerId().equals(session.getCustomerId())) {
            supportTicketsMessage.setSenderCustomerId(session.getCustomerId());
            supportTicketsMessage.setReceiverCustomerId(supportTickets.getCustomerId());
            supportTicketsMessage.setReceiverUserId(customerVo.getId());
            msgType = 2;
        } else {
            //app发送
            supportTicketsMessage.setFlag(0);
            supportTicketsMessage.setSenderCustomerId(session.getCustomerId());
            supportTicketsMessage.setReceiverCustomerId(supportTickets.getManagerCustomerId());
            supportTicketsMessage.setReceiverUserId(managerInfoVo.getCustomerSignupUserId());
            msgType = 1;
        }
        Date currDate = new Date();
        supportTicketsMessage.setMessage(request.getMessage());
        supportTicketsMessage.setSendTime(currDate);
        supportTicketsMessage.setId(IdGenerate.nextId());
        //新增一条消息
        supportTicketsCountDao.addMessageAllByTicketId(request.getTicketId(),null);
        supportTicketsMessageDao.insert(supportTicketsMessage);


        //记录admin回复消息时效数
        recordReplyData(request.getTicketId(),msgType);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTicketsMessage);
    }

    /**
     * 记录admin回复消息时效数
     *
     * @param ticketId
     * @param msgType
     */
    @Transactional
    public void recordReplyData(Long ticketId, int msgType) {
        Date currDate = new Date();
        //查询上一条admin消息
        SupportTicketsMessage adminMsg = supportTicketsMessageDao.queryPrevAdminMsg(currDate, ticketId);
        Date startDate = null;
        if (adminMsg != null) {
            startDate = adminMsg.getSendTime();
        }
        //查询上一条admin消息到该条消息之间的客户消息列表  计算该回复与客户消息的时间差
        List<SupportTicketsMessage> userMsgList = supportTicketsMessageDao.
                queryNearestUserMsg(startDate, currDate, ticketId);
        if (CollectionUtils.isEmpty(userMsgList)){
            userMsgList =  supportTicketsMessageDao.selectNearestUserMsg(ticketId);
        }
        int replyIn12 = 0;
        int replyIn24 = 0;
        int flag = 0;
        for (SupportTicketsMessage userMsg : userMsgList) {
            Date time = userMsg.getSendTime();
            Long costTime = currDate.getTime() - time.getTime();
            //客户消息回复标记 0:未恢复 1:12小时内回复 2:24小时内回复 3:24外回复
            if (costTime > 24 * 3600 * 1000) {
                replyIn24++;
                if (msgType == 2){
                    replyIn24--;
                    //标记为24小时外已回复
                    supportTicketsMessageDao.markReplyFlag(userMsg.getId(), 3);
                }

            } else if (costTime < 24 * 3600 * 1000 && costTime > 12 * 3600 * 1000) {
                replyIn12++;
                if (msgType == 2){
                    replyIn12--;
                    //标记为24小时内已回复
                    supportTicketsMessageDao.markReplyFlag(userMsg.getId(), 2);
                }
                //12小时 24小时内回复

            } else {
                if (msgType == 2){
                    //12小时内回复
                    supportTicketsMessageDao.markReplyFlag(userMsg.getId(), 1);
                }
            }
        }
        //更新数据 当前客户为回复消息总数-12小时、24小时内回复 = 未回复消息数
        supportTicketsCountDao.updateSupportTicketsCount(ticketId, replyIn12, replyIn24);

    }

    /**
     * 发送图片消息
     *
     * @param file
     * @param ticketId
     * @param session
     * @return
     */
    @Override
    @Transactional
    public BaseResponse sendImgMsg(MultipartFile file, Long ticketId, Session session) {

//        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(ticketId);
//        if (supportTickets == null || supportTickets.getState() == null) {
//            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket不存在!");
//        }
//        if (supportTickets.getState() != 0) {
//            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket已关闭!");
//        }
//
//        File folder = new File(imgPath);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//
//        if (file.isEmpty()) {
//            return new BaseResponse(ResultCode.FAIL_CODE, "文件为空!");
//        } else {
//
//            //保存时的文件名
//            String originalFilename = file.getOriginalFilename();
//            String dateName = IdGenerate.nextId() + originalFilename.substring(originalFilename.lastIndexOf("."));
//            //保存文件的绝对路径;
//            String realPath = imgPath;
//            String filePath = realPath + dateName;
//            File newFile = new File(filePath);
//            //MultipartFile的方法直接写文件
//            try {
//                String imgPath="/root/files/image/ticket/";
//                String urlPrefix = HostConfig.HOST + "/oms/image/ticket/";
//                String url = FileUtil.uploadImage(base64String,urlPrefix,imgPath);
//                //上传文件
//                file.transferTo(newFile);
//                //数据库存储的相对路径
//                String url = urlPrefix + dateName;
//                SupportTicketsMessage supportTicketsMessage = new SupportTicketsMessage();
//                supportTicketsMessage.setTicketId(ticketId);
//                supportTicketsMessage.setState(0);
//                supportTicketsMessage.setSenderUserId(session.getId());
//                int msgType = 0;
//                //admin发送消息
//                if (supportTickets.getManagerCustomerId().equals(session.getCustomerId())) {
//                    supportTicketsMessage.setSenderCustomerId(supportTickets.getManagerCustomerId());
//                    supportTicketsMessage.setReceiverCustomerId(supportTickets.getCustomerId());
//                    msgType = 2;
//                } else {
//                    //app发送
//                    supportTicketsMessage.setFlag(0);
//                    supportTicketsMessage.setSenderCustomerId(supportTickets.getCustomerId());
//                    supportTicketsMessage.setReceiverCustomerId(supportTickets.getManagerCustomerId());
//                    msgType = 1;
//                }
//                String message = "<img style='max-width:400px;max-height:400px;width:auto;height:auto' src='" + url + "' />";
//                supportTicketsMessage.setMessage(message);
//                supportTicketsMessage.setSendTime(new Date());
//                supportTicketsMessage.setId(IdGenerate.nextId());
//                supportTicketsMessage.setSource(session.getApplicationId());
//                //新增一条消息
//                supportTicketsCountDao.addMessageAllByTicketId(ticketId,null);
//                supportTicketsMessageDao.insert(supportTicketsMessage);
//                //记录admin回复消息时效数
//                recordReplyData(ticketId, msgType);
//                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTicketsMessage);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
//
//            }
//        }
        return BaseResponse.failed();
    }

    /**
     * 发送图片消息
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public BaseResponse sendBase64ImgMsg(SendImgMsgRequest request, Session session) {
        Long ticketId = request.getTicketId();
        String base64String = request.getBase64String();

//        OutputStream os = null;

        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(ticketId);
        if (supportTickets == null || supportTickets.getState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket不存在!");
        }
        if (supportTickets.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "supportTicket已关闭!");
        }

        try {
//            //保存时的文件名
//            String dateName = IdGenerate.nextId() + "." + type.toLowerCase();
//            //保存文件的绝对路径;
//            String realPath = imgPath;
//            String filePath = realPath + dateName;
//            File imageFile = new File(filePath);
//            //把图片转换成二进制
//            byte[] bytes1 = decoder.decode(base64String.replaceAll(" ", "+"));
////            byte[] bs = decoder.decode(bytes1);
//            os = new FileOutputStream(imageFile);
//            os.write(bytes1);
//            //数据库存储的相对路径

            String imgPath="/root/files/image/ticket/";
            String urlPrefix = HostConfig.HOST + "/oms/image/ticket/";
            String url = FileUtil.uploadImage(base64String,urlPrefix,imgPath);

            SupportTicketsMessage supportTicketsMessage = new SupportTicketsMessage();
            supportTicketsMessage.setTicketId(ticketId);
            supportTicketsMessage.setState(0);
            supportTicketsMessage.setSenderUserId(session.getId());
            int msgType = 0;
            //admin发送消息
            if (supportTickets.getManagerCustomerId().equals(session.getCustomerId())) {
                supportTicketsMessage.setSenderCustomerId(supportTickets.getManagerCustomerId());
                supportTicketsMessage.setReceiverCustomerId(supportTickets.getCustomerId());
                msgType = 2;
            } else {
                //app发送
                supportTicketsMessage.setFlag(0);
                supportTicketsMessage.setSenderCustomerId(supportTickets.getCustomerId());
                supportTicketsMessage.setReceiverCustomerId(supportTickets.getManagerCustomerId());
                msgType = 1;
            }
            String message = "<img style='max-width:400px;max-height:400px;width:auto;height:auto' src='" + url + "' />";
            supportTicketsMessage.setMessage(message);
            supportTicketsMessage.setSendTime(new Date());
            supportTicketsMessage.setId(IdGenerate.nextId());
            supportTicketsMessage.setSource(session.getApplicationId());
            //新增一条消息
            supportTicketsCountDao.addMessageAllByTicketId(ticketId,null);
            supportTicketsMessageDao.insert(supportTicketsMessage);
            //记录admin回复消息时效数
            recordReplyData(ticketId, msgType);
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTicketsMessage);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        }
//        finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.getLocalizedMessage();
//                }
//            }
//        }
    }

    @Override
    public Long processingCount(CustomerTicketListRequest request) {
        return supportTicketsDao.selectCustomerTicketCount(request);
    }

    public String getSubUtilSimple(String soap) {
        String rgex = "data:image/(.*?);base64";
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * 进入tickets详情 标记消息为已读
     */
    @Transactional(readOnly = false)
    @Override
    public SupportTicketsInfoResponse ticketsInfo(Long id, Session session) {
        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(id);
        //更新未读消息设置为已读
        supportTicketsMessageDao.markReadMsg(supportTickets.getId(),
                supportTickets.getCustomerId(), new Date());
        return new SupportTicketsInfoResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTickets, id);
    }
}