package com.upedge.oms.modules.rules.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.rules.dao.OrderShipRuleCountryDao;
import com.upedge.oms.modules.rules.dao.OrderShipRuleDao;
import com.upedge.oms.modules.rules.dto.ShipRuleConditionDto;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.entity.OrderShipRuleCountry;
import com.upedge.oms.modules.rules.request.OrderShipRuleAddRequest;
import com.upedge.oms.modules.rules.request.OrderShipRuleUpdateRequest;
import com.upedge.oms.modules.rules.service.OrderShipRuleService;
import com.upedge.oms.modules.rules.vo.OrderShipRuleCountryVo;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class OrderShipRuleServiceImpl implements OrderShipRuleService {

    @Autowired
    private OrderShipRuleDao orderShipRuleDao;

    @Autowired
    OrderShipRuleCountryDao orderShipRuleCountryDao;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderShipRule record = new OrderShipRule();
        record.setId(id);
        return orderShipRuleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderShipRule record) {
        return orderShipRuleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderShipRule record) {
        return orderShipRuleDao.insert(record);
    }

    @Override
    public OrderShipRuleVo selectShipRuleById(Long id) {
        return orderShipRuleDao.selectShipRuleById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderShipRuleVo addShipRules(OrderShipRuleAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Long ruleId = IdGenerate.nextId();
        OrderShipRule rule = request.toOrderShipRule(session.getCustomerId());
        rule.setId(ruleId);
        orderShipRuleDao.insert(rule);
        saveOrderShipRuleCountries(request.getCountries(),ruleId);
        OrderShipRuleVo shipRuleVo = new OrderShipRuleVo();
        BeanUtils.copyProperties(rule,shipRuleVo);
        shipRuleVo.setCountries(request.getCountries());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderMatchShipRule(shipRuleVo);
            }
        });

        return shipRuleVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShipRules(OrderShipRuleUpdateRequest request, Long ruleId) {
        OrderShipRule entity=request.toOrderShipRule(ruleId);
        orderShipRuleDao.updateByPrimaryKeySelective(entity);
        orderShipRuleCountryDao.deleteByShipRuleId(ruleId);
        saveOrderShipRuleCountries(request.getCountries(),ruleId);
        entity = selectByPrimaryKey(ruleId);
        OrderShipRuleVo orderShipRuleVo = new OrderShipRuleVo();
        BeanUtils.copyProperties(entity,orderShipRuleVo);
        orderShipRuleVo.setCountries(request.getCountries());
        orderMatchShipRule(orderShipRuleVo);
    }

    public void saveOrderShipRuleCountries(List<OrderShipRuleCountryVo> countryVos, Long ruleId){
        List<OrderShipRuleCountry> countries = new ArrayList<>();
        for (OrderShipRuleCountryVo vo: countryVos) {
            OrderShipRuleCountry country = new OrderShipRuleCountry();
            BeanUtils.copyProperties(vo,country);
            country.setShipRuleId(ruleId);
            countries.add(country);
        }
        orderShipRuleCountryDao.insertByBatch(countries);
    }

    void orderMatchShipRule(OrderShipRuleVo orderShipRuleVo){
        List<OrderShipRuleCountryVo> countryVos = orderShipRuleVo.getCountries();
        if (orderShipRuleVo.getSequence() == 1){
            for (OrderShipRuleCountryVo countryVo : countryVos) {
                countryRuleMatchOrderShipMethod(orderShipRuleVo,countryVo.getAreaId());
            }
        }else {
            for (OrderShipRuleCountryVo countryVo : countryVos) {
                OrderShipRule orderShipRule = orderShipRuleDao.selectCountryTopRule(orderShipRuleVo.getCustomerId(), countryVo.getAreaId(), orderShipRuleVo.getShippingMethodId(), orderShipRuleVo.getSequence());
                if (null != orderShipRule){
                    OrderShipRuleVo ruleVo = new OrderShipRuleVo();
                    BeanUtils.copyProperties(orderShipRule,ruleVo);
                    countryRuleMatchOrderShipMethod(ruleVo, orderShipRuleVo.getId());
                }else {
                    countryRuleMatchOrderShipMethod(orderShipRuleVo, orderShipRuleVo.getId());
                }
            }
        }

    }

    void countryRuleMatchOrderShipMethod(OrderShipRuleVo orderShipRuleVo,Long areaId){
        List<Long> orderIds = orderService.selectUnPaidIdsByShipRule(orderShipRuleVo,areaId);
        for (Long orderId : orderIds) {
            orderService.matchShipRule(orderId,orderShipRuleVo);
        }
    }

    @Override
    public List<OrderShipRule> selectShipRulesByCondition(ShipRuleConditionDto shipRuleConditionDto) {
        return orderShipRuleDao.selectByCondition(shipRuleConditionDto);
    }

    @Override
    public List<OrderShipRuleVo> selectCustomerShipRules(Long customerId) {
        return orderShipRuleDao.selectCustomerShipRules(customerId);
    }

    /**
     *
     */
    public OrderShipRule selectByPrimaryKey(Long id){
        OrderShipRule record = new OrderShipRule();
        record.setId(id);
        return orderShipRuleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderShipRule record) {
        return orderShipRuleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderShipRule record) {
        return orderShipRuleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderShipRule> select(Page<OrderShipRule> record){
        record.initFromNum();
        return orderShipRuleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderShipRule> record){
        return orderShipRuleDao.count(record);
    }

}