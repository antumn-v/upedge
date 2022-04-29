package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiGetWareHouseResponse;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiWareHouse;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.WareHouseList;
import com.upedge.thirdparty.saihe.service.SaiheService;
import com.upedge.tms.modules.ship.dao.AdminWarehouseDao;
import com.upedge.tms.modules.ship.entity.AdminWarehouse;
import com.upedge.tms.modules.ship.response.AdminWarehouseUpdateResponse;
import com.upedge.tms.modules.ship.service.AdminWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdminWarehouseServiceImpl implements AdminWarehouseService {

    @Autowired
    private AdminWarehouseDao adminWarehouseDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        AdminWarehouse record = new AdminWarehouse();
        record.setId(id);
        return adminWarehouseDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AdminWarehouse record) {
        return adminWarehouseDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AdminWarehouse record) {
        return adminWarehouseDao.insert(record);
    }

    /**
     *
     */
    public AdminWarehouse selectByPrimaryKey(Integer id){
        return adminWarehouseDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AdminWarehouse record) {
        return adminWarehouseDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AdminWarehouse record) {
        return adminWarehouseDao.updateByPrimaryKey(record);
    }

    @Override
    public List<AdminWarehouse> allUseWarehouses() {
        List<AdminWarehouse> results = adminWarehouseDao.allUseWarehouses();
        return results;
    }

    /**
    *
    */
    public List<AdminWarehouse> select(Page<AdminWarehouse> record){
        record.initFromNum();
        return adminWarehouseDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AdminWarehouse> record){
        return adminWarehouseDao.count(record);
    }

    @Override
    public AdminWarehouseUpdateResponse enableWarehouse(Integer id) {
        AdminWarehouse adminWarehouse=adminWarehouseDao.selectByPrimaryKey(id);
        if(adminWarehouse==null){
            return new AdminWarehouseUpdateResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        adminWarehouseDao.updateWarehouseState(id,1);
        return new AdminWarehouseUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public AdminWarehouseUpdateResponse disableWarehouse(Integer id) {
        AdminWarehouse adminWarehouse=adminWarehouseDao.selectByPrimaryKey(id);
        if(adminWarehouse==null){
            return new AdminWarehouseUpdateResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        adminWarehouseDao.updateWarehouseState(id,0);
        return new AdminWarehouseUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 同步赛盒仓库
     * @return
     */
    @Override
    @Transactional
    public AdminWarehouseUpdateResponse refreshSaihe() {
        //获取仓库列表
        List<AdminWarehouse> warehouseList=new ArrayList<>();
        ApiGetWareHouseResponse apiGetWareHouseResponse=
                SaiheService.getWareHouseList();
        if(!apiGetWareHouseResponse.getGetWareHouseListResult().getStatus().equals("OK")){
            String msg=apiGetWareHouseResponse.getGetWareHouseListResult().getMsg();
            return new AdminWarehouseUpdateResponse(ResultCode.FAIL_CODE,msg);
        }
        WareHouseList wareHouseList=apiGetWareHouseResponse.getGetWareHouseListResult().getWareHouseList();
        if(wareHouseList==null||wareHouseList.getApiWareHouse()==null){
            return new AdminWarehouseUpdateResponse(ResultCode.FAIL_CODE,"数据为空!");
        }
        List<ApiWareHouse> wareHouses=wareHouseList.getApiWareHouse();
        for(ApiWareHouse a:wareHouses){
            AdminWarehouse warehouse=new AdminWarehouse();
            warehouse.setId(a.getID());
            warehouse.setState(1);
            warehouse.setWarehouseEname(a.getWareHouseName());
            warehouse.setWarehouseName(a.getWareHouseName());
            warehouse.setWarehouseType(a.getWareHouseType());
            warehouseList.add(warehouse);
        }
        if(warehouseList.size()>0) {
            adminWarehouseDao.saveByBatch(warehouseList);
        }
        return new AdminWarehouseUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}