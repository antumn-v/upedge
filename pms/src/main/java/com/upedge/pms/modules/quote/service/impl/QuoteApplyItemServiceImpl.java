package com.upedge.pms.modules.quote.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.quote.dao.QuoteApplyItemDao;
import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;


@Service
public class QuoteApplyItemServiceImpl implements QuoteApplyItemService {

    @Autowired
    private QuoteApplyItemDao quoteApplyItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        QuoteApplyItem record = new QuoteApplyItem();
        record.setId(id);
        return quoteApplyItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(QuoteApplyItem record) {
        return quoteApplyItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(QuoteApplyItem record) {
        return quoteApplyItemDao.insert(record);
    }

    /**
     *
     */
    public QuoteApplyItem selectByPrimaryKey(Long id){
        QuoteApplyItem record = new QuoteApplyItem();
        record.setId(id);
        return quoteApplyItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(QuoteApplyItem record) {
        return quoteApplyItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(QuoteApplyItem record) {
        return quoteApplyItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<QuoteApplyItem> select(Page<QuoteApplyItem> record){
        record.initFromNum();
        return quoteApplyItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<QuoteApplyItem> record){
        return quoteApplyItemDao.count(record);
    }

}