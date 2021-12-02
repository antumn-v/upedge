package com.upedge.ums.modules.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.config.RocketMqConfig;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.PayOrderMethod;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.RocketMqDelayLevelEnum;
import com.upedge.common.enums.TransactionType;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalOrder.PaypalOrderItem;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.async.MqAsync;
import com.upedge.ums.enums.PaypalPaymentIntent;
import com.upedge.ums.enums.PaypalPaymentMethod;
import com.upedge.ums.modules.account.dao.*;
import com.upedge.ums.modules.account.entity.*;
import com.upedge.ums.modules.account.service.PaypalService;
import com.upedge.ums.modules.log.dao.MqMessageLogDao;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class PaypalServiceImpl implements PaypalService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RechargeBenefitsMapper rechargeBenefitsMapper;

    @Autowired
    RechargeRequestLogMapper rechargeRequestLogMapper;

    @Autowired
    PaypalPaymentDao paypalPaymentDao;

    @Autowired
    RechargeLogMapper rechargeLogMapper;

    @Autowired
    RechargeRecordDao rechargeRecordDao;

    @Autowired
    AccountLogDao accountLogDao;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PaymentLogDao paymentLogDao;

    @Autowired
    MqMessageLogDao mqMessageLogDao;

    @Autowired
    MqAsync mqAsync;


    public static String clientId = "AbhXmp75BILbqaOawMwjpa2K327_ca8sUUKmKEhgJue7pG1fc-Ef1zs24Sym65mdLLC-py2R6EfB9HAI";
    public static String clientSecret = "EO_MqAw4vx0tNdocO1grgItr8Kjo_dsGZJGFv576HGb2cMqIyDneohJyOYVOkmhtmTyHBMCskrKt5OSJ";
    public static String mode = "live";
    public static String url = "https://api.paypal.com";


    @Override
    public String getPaypalOrderUrl(PaypalOrder paypalOrder) {
        log.warn("开始：{}", System.currentTimeMillis());
        if (null == paypalOrder.getId()
        || null == paypalOrder.getOrderType()
        || null == paypalOrder.getSession()
        || null == paypalOrder.getAccountId()
        || null == paypalOrder.getAmount()
        ){
            return null;
        }

        BigDecimal amount = paypalOrder.getAmount();
        BigDecimal fixfee = paypalPaymentDao.selectFixFeeByAmount(amount, Constant.PAYPAL_FEE_PERCENTAGE);

        try {
            log.warn("创建paypal订单,start：{}", System.currentTimeMillis());
            Payment payment = createPayment(
                    amount.add(fixfee).doubleValue(),
                    fixfee,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    paypalOrder);
            log.warn("创建paypal结束,end：{}", System.currentTimeMillis());
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    String url = links.getHref();
                    String token = url.substring(url.indexOf("token=")+ 6);

                    log.warn("交易ID：admin：{},paypal:{},token:{}",paypalOrder.getId(),payment.getId(),token);

                    String key = RedisKey.HASH_PAYPAL_TOKEN + token;

                    redisTemplate.opsForHash().put(key, "paymentId",payment.getId());
                    redisTemplate.opsForHash().put(key, "order",paypalOrder);
                    Message message = new Message();
                    message.setDelayTimeLevel(RocketMqDelayLevelEnum.TEN_MINUTE.getLevel());
                    message.setBody(JSON.toJSONBytes(payment.getId()));
                    message.setKeys(key);
                    message.setTags(String.valueOf(paypalOrder.getOrderType()));
                    message.setTopic(RocketMqConfig.TOPIC_PAYPAL_VERIFICATION);
                    mqAsync.sendMessage(message);
                    log.warn("结束：{}", System.currentTimeMillis());
                    return url;
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public PaypalPayment executePayment(PaypalExecuteRequest request)  {
        PaypalOrder paypalOrder = request.getPaypalOrder();

        String paymentId = request.getPaymentId();
        String payerId = request.getPayerId();
        String token = request.getToken();
        log.warn("paypal Id:{},交易信息：{}",paymentId,paypalOrder);

        if(null == paypalOrder){
            return null;
        }

        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        try {
            payment =  payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return null;
        }

        Session session = paypalOrder.getSession();

        PaypalPayment paypalPayment = toPaypalPayment(paypalOrder.getId(),payment);
        paypalPayment.setToken(token);
        paypalPayment.setUserId(session.getId());
        paypalPayment.setCustomerId(session.getCustomerId());
        paypalPayment.setAccountId(session.getAccountId());
        paypalPayment.setOrderType(paypalOrder.getOrderType());
        paypalPaymentDao.insert(paypalPayment);
        BigDecimal fee = new BigDecimal(paypalPayment.getFixFee());
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setId(paypalOrder.getId());
        paymentLog.setAmount(new BigDecimal(paypalPayment.getAmount()).subtract(fee));
        paymentLog.setFee(fee);
        paymentLog.setPayType(PayOrderMethod.PAYPAL);
        if(paypalPayment.getState().equals("completed")){
            paymentLog.setPayStatus(1);
        }else {
            paymentLog.setPayStatus(0);
        }
        paymentLog.setCustomerId(session.getCustomerId());
        paymentLog.setAccountId(session.getAccountId());
        paymentLog.setOrderType(paypalOrder.getOrderType());
        paymentLog.setCreateTime(new Date());
        paymentLog.setUpdateTime(new Date());
        paymentLogDao.insert(paymentLog);
        return paypalPayment;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean paypalPayOrders(PaymentDetail paymentDetail) {

        Iterator<TransactionDetail> iterator = paymentDetail.getOrderTransactions().iterator();
        while (iterator.hasNext()){
            TransactionDetail next = iterator.next();
            Long orderId = next.getOrderId();
            Integer count = accountLogDao.selectAccountLog(orderId,paymentDetail.getOrderType());
            if (count > 0){
                iterator.remove();
            }
        }

        TransactionType transactionType = TransactionType.PAYPAL_PAY_STOCK;

        Long paymentId = paymentDetail.getPaymentId();

        PaymentLog paymentLog = paymentLogDao.selectByPrimaryKey(paymentId);

        Integer orderType = paymentDetail.getOrderType();

        PaypalPayment payment = paypalPaymentDao.selectByPrimaryKey(paymentId);
        Long accountId = payment.getAccountId();
        Long customerId = payment.getCustomerId();

        Date date = new Date();

        List<AccountLog> accountLogs = new ArrayList<>();

        List<RechargeRecord> records = new ArrayList<>();

        List<TransactionDetail> details = paymentDetail.getOrderTransactions();

        Integer i = 1;

        for (TransactionDetail detail: details) {
            AccountLog accountLog = new AccountLog();
            accountLog.setBalance(detail.getAmount());
            accountLog.setCreateTime(date);
            accountLog.setTransactionType(transactionType.getTransactionType());
            accountLog.setOrderType(transactionType.getOrderType());
            accountLog.setPayMethod(transactionType.getPayMethod());
            accountLog.setTransactionId(detail.getOrderId());
            accountLog.setCustomerId(customerId);
            accountLog.setAccountId(accountId);
            accountLogs.add(accountLog);
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setOrderType(orderType);
            rechargeRecord.setCreateTime(date);
            rechargeRecord.setAmount(detail.getAmount());
            rechargeRecord.setRechargeId(1L);
            rechargeRecord.setSource(0);
            rechargeRecord.setOrderId(detail.getOrderId());
            rechargeRecord.setCustomerId(paymentDetail.getCustomerId());
            rechargeRecord.setSeq(i++);
            records.add(rechargeRecord);
        }

        accountLogDao.insertByBatch(accountLogs);

        rechargeRecordDao.insertByBatch(records);

        /**
         * 隊列計算   客户每日支付订单数据
         */
        CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest = new CustomerOrderDailyCountUpdateRequest();
        customerOrderDailyCountUpdateRequest.setCustomerId(paymentDetail.getCustomerId());
        customerOrderDailyCountUpdateRequest.setPaymentId(paymentDetail.getPaymentId());
        customerOrderDailyCountUpdateRequest.setOrderType(orderType);
        customerOrderDailyCountUpdateRequest.setPayTime(paymentDetail.getPayTime());
        accountLogDao.insertByBatch(accountLogs);
        redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE,customerOrderDailyCountUpdateRequest);

        return true;
    }


    @Override
    public  Payment getPaymentDetail(String paymentId)  {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        try {
            return Payment.get(apiContext, paymentId);
        } catch (PayPalRESTException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static Payment createPayment(
            Double total,
            BigDecimal fixFee,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            PaypalOrder paypalOrder) throws PayPalRESTException {

        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Details details = new Details();
        details.setShipping(String.valueOf(paypalOrder.getShipPrice()));
        details.setSubtotal(String.valueOf(paypalOrder.getProductAmount().subtract(paypalOrder.getDischargeAmount())));
        details.setTax(fixFee.toString());
        details.setShippingDiscount(String.valueOf(paypalOrder.getDischargeAmount()));
//        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setDescription(getPaypalOrderDescrption(paypalOrder.getOrderType(),paypalOrder.getId(),paypalOrder.getAmount(),fixFee));
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paypalOrder.getFailedUrl());
        redirectUrls.setReturnUrl(paypalOrder.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);

        List<PaypalOrderItem> orderItems = paypalOrder.getItems();
        if(ListUtils.isNotEmpty(orderItems)){
            ItemList itemList = new ItemList();
            List<Item> items = new ArrayList<>();
            orderItems.forEach(orderItem -> {
                Item item = new Item(orderItem.getOrderId() + "#" + orderItem.getItemId(),
                        String.valueOf(orderItem.getQuantity()),
                        "0",
                        "USD");
                if (StringUtils.isBlank(orderItem.getSku())){
                    item.setSku(System.currentTimeMillis() + "");
                }else {
                    item.setSku(orderItem.getSku());
                }
                items.add(item);
            });
            itemList.setItems(items);
            transaction.setItemList(itemList);
        }

        return payment.create(apiContext);
    }

    public static PaypalPayment toPaypalPayment(Long id, Payment payment) {
        PaypalPayment appPaypalPayment = new PaypalPayment();
        appPaypalPayment.setId(id);
        appPaypalPayment.setPaymentId(payment.getId());
        appPaypalPayment.setPayerEmail(payment.getPayer().getPayerInfo().getEmail());
        appPaypalPayment.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
        Transaction transaction = payment.getTransactions().get(0);
        Amount amount = transaction.getAmount();
        appPaypalPayment.setAmount(amount.getTotal());
        appPaypalPayment.setCurrency(transaction.getAmount().getCurrency());
        appPaypalPayment.setPayerName(payment.getPayer().getPayerInfo().getFirstName() + " " + payment.getPayer().getPayerInfo().getLastName());
        appPaypalPayment.setPayeeEmail(transaction.getPayee().getEmail());
        appPaypalPayment.setMerchantId(transaction.getPayee().getMerchantId());
        Sale sale = transaction.getRelatedResources().get(0).getSale();
        appPaypalPayment.setState(sale.getState());

        appPaypalPayment.setSaleId(sale.getId());
        appPaypalPayment.setCreateTime(sale.getCreateTime());
        appPaypalPayment.setUpdateTime(sale.getUpdateTime());
        if (sale.getTransactionFee() != null){
            appPaypalPayment.setFixFee(sale.getTransactionFee().getValue());
        } else{
            appPaypalPayment.setFixFee("0.00");
        }
        appPaypalPayment.setPaymentMethod(payment.getPayer().getPaymentMethod());

        return appPaypalPayment;
    }

    public static String getPaypalOrderDescrption(Integer type, Long id, BigDecimal amount, BigDecimal fixfee) {
        String orderType = null;
        switch (type) {
            case 0:
                orderType = "recharge";
                break;
            case 1:
                orderType = "normal";
                break;
            case 2:
                orderType = "stock";
                break;
            case 3:
                orderType = "wholesale";
                break;
        }
        String description = "Pay SourcinBox " + orderType + " order amount: " + amount + ", PayPal Fee: " + fixfee +
                ", Transaction ID: " + id;
        return description;
    }

    public static void main(String[] args) {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        try {
            System.out.println(Payment.get(apiContext, "PAYID-MAEOK2I78M86278K61986229"));
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

}
