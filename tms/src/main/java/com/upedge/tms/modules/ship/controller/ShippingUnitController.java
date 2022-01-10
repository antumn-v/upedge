package com.upedge.tms.modules.ship.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.tms.ShippingUnitInfoResponse;
import com.upedge.common.model.tms.ShippingUnitVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.excel.ExcelExport;
import com.upedge.common.utils.excel.ExcelField;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.tms.aspect.LocalLock;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.area.service.AreaService;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import com.upedge.tms.modules.ship.request.*;
import com.upedge.tms.modules.ship.response.ShippingUnitListResponse;
import com.upedge.tms.modules.ship.response.ShippingUnitUpdateResponse;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import com.upedge.tms.modules.ship.service.ShippingUnitService;
import com.upedge.tms.modules.ship.vo.ShipMethodCountryVo;
import com.upedge.tms.mq.TmsProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 运输单元表
 *
 * @author author
 */
@Api(tags = "运输单元")
@Slf4j
@RestController
@RequestMapping("/shippingUnit")
public class ShippingUnitController {
    @Autowired
    private ShippingUnitService shippingUnitService;
    @Autowired
    private ShippingMethodService shippingMethodService;
    @Autowired
    private AreaService areaService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Autowired
    TmsProducerService tmsProducerService;

//    @PostMapping("/add")
//    public BaseResponse addShipUnit(@RequestBody ShippingUnitAddRequest request) {
//        ShippingUnit shippingUnit = request.toShippingUnit();
//        shippingUnitService.insert(shippingUnit);
//        return BaseResponse.success();
//    }
    @ApiOperation("运费计算")
    @PostMapping("/freightCalculation")
    public BaseResponse freightCalculation(@RequestBody@Valid ShipUnitFreightCalculationRequest request){
        ShipDetail shipDetail = shippingUnitService.selectByCondition(request.getMethodId(),request.getAreaId(),request.getWeight());
        return BaseResponse.success(shipDetail);
    }


    @ApiOperation("excel导入运输单元")
    @PostMapping("/excelImport")
    public BaseResponse excelImportShipUnit(@RequestBody@Valid ShipUnitExcelImportRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return shippingUnitService.excelImportShipUnit(request,session);
    }

    @PostMapping("/methodCountryUnitList")
    public BaseResponse methodCountryUnitList(@RequestBody @Valid ShippingUnitListRequest request){
        if (request.getT() == null) {
            request.setT(new ShippingUnit());
        }
        ShippingUnit shippingUnit = request.getT();
        shippingUnit.setState(1);
        List<ShipMethodCountryVo> results = (List<ShipMethodCountryVo>) redisTemplate.opsForValue().get(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST);
        if (ListUtils.isEmpty(results)){
            results = shippingUnitService.selectMethodCountryUnitVo(request);
            redisTemplate.opsForValue().set(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST,results);
        }
        int total = results.size();
        request.setTotal((long) total);
        ShippingUnitListResponse res = new ShippingUnitListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }

    /**
     * 运输导入列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/import/list", method = RequestMethod.POST)
    public ShippingUnitListResponse importList(@RequestBody @Valid ShippingUnitListRequest request) {
        if (request.getT() == null) {
            request.setT(new ShippingUnit());
        }
        ShippingUnit shippingUnit = request.getT();
        shippingUnit.setState(0);
        List<ShippingUnit> results = shippingUnitService.select(request);
        Long total = shippingUnitService.count(request);
        request.setTotal(total);
        ShippingUnitListResponse res = new ShippingUnitListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }

    /**
     * 运输方式导入模板
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/import/template", method = RequestMethod.GET)
    public BaseResponse importFileTemplate(HttpServletResponse response) {
        try {
            String fileName = "import_template.xls";
            List<ShippingUnit> list = new ArrayList<>();
            //产品库详情 XSSFWorkbook
            ExcelExport excelExport = new ExcelExport(new HSSFWorkbook(), "运输方式数据", null,
                    ShippingUnit.class, ExcelField.Type.EXPORT, new String[0]);

            //创建一个样式
            CellStyle cellStyle = excelExport.getWorkbook().createCellStyle();
            DataFormat format = excelExport.getWorkbook().createDataFormat();
            //具体如何创建cell就省略了，最后设置单元格的格式这样写
            Sheet oneSheet = excelExport.getWorkbook().getSheetAt(0);
            cellStyle.setDataFormat(format.getFormat("@"));
            oneSheet.setColumnWidth(0, 5000);
            oneSheet.setDefaultColumnStyle(6, cellStyle);
            oneSheet.autoSizeColumn(1, true);

            List<String> methodNameList = shippingMethodService.listUseAllShippingMethodName();

            Sheet hidden = excelExport.getWorkbook().createSheet("hidden");
            Cell cell = null;
            for (int i = 0, length = methodNameList.size(); i < length; i++) {
                String name = methodNameList.get(i);
                Row row = hidden.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(name);
            }
            Name namedCell = excelExport.getWorkbook().createName();
            namedCell.setNameName("hidden");
            namedCell.setRefersToFormula("hidden!A1:A" + methodNameList.size());
            //加载数据,将名称为hidden的
            DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            CellRangeAddressList addressList = new CellRangeAddressList(1, 50000, 0, 0);
            HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
            //将第二个sheet设置为隐藏
            excelExport.getWorkbook().setSheetHidden(1, true);
            oneSheet.addValidationData(validation);
            excelExport.setDataList(list).write(response, fileName).close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
    }

    /**
     * 导入Excel数据
     */
    @LocalLock(key = "shippingUnitImport")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResponse importFile(@RequestBody List<ShippingUnit> list) {
        String key = RedisUtil.KEY_SHIP_UNIT_IMPORT;
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 10 * 60);
        Integer insertNumber = 0;
        //获取锁成功
        if (!flag) {
            log.debug("获取锁失败:{}", key);
            return new BaseResponse(ResultCode.FAIL_CODE, "操作中...");
        }
        log.debug("获取锁:{}", key);
        try {
//            ImportExcelUtil ei=new ImportExcelUtil(file,1,0);
//            List<ShippingUnit> list = ei.getDataList(ShippingUnit.class);
            List<ShippingUnit> newList = new ArrayList<>();
            for (ShippingUnit shippingUnit : list) {
                try {
                    if (!StringUtils.isBlank(shippingUnit.getMethodName()) &&
                            !StringUtils.isBlank(shippingUnit.getFromAreaId()) && !StringUtils.isBlank(shippingUnit.getToAreaId())) {
                        if (shippingUnit.getMethodName().equals("methodName")) {
                            continue;
                        }
                        ShippingMethod shippingMethod = shippingMethodService.
                                getShippingMethodByName(shippingUnit.getMethodName());
                        //运输方式能对应上，则匹配转换id
                        if (shippingMethod != null) {
                            shippingUnit.setMethodId(shippingMethod.getId());
                        } else {
                            continue;
                        }
                        shippingUnit.setCreateTime(new Date());
                        //待启用状态
                        shippingUnit.setState(0);

                        //传入的时国家名称 系统有匹配则转换id,没有匹配则传名字
                        Area fromRegion = areaService.getRegionByName(shippingUnit.getFromAreaId());
                        initRegion(fromRegion, shippingUnit, 1);
                        //传入的时国家名称 系统有匹配则转换id,没有匹配则传名字
                        Area toRegion = areaService.getRegionByName(shippingUnit.getToAreaId());
                        initRegion(toRegion, shippingUnit, 2);

                        //检查运输方式 起始地 起始重量是否有相同的记录
                        ShippingUnit exist = shippingUnitService.getShippingUnitByOption(shippingUnit.getMethodId(),
                                shippingUnit.getFromAreaId(), shippingUnit.getToAreaId(),
                                shippingUnit.getStartWeight(), shippingUnit.getEndWeight());
                        if (exist != null) {
                            continue;
                        }
                        newList.add(shippingUnit);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (newList.size() > 0) {
                insertNumber = shippingUnitService.insertBatch(newList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisUtil.unLock(redisTemplate, key);
            log.debug("释放锁:{}", key);
        }
        if (insertNumber > 0) {
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        }
        return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
    }

    public void initRegion(Area region, ShippingUnit shippingUnit, int flag) {
        if (region != null) {
            if (flag == 1) {
                shippingUnit.setFromAreaId(String.valueOf(region.getId()));
            } else {
                shippingUnit.setToAreaId(String.valueOf(region.getId()));
            }
        } else {
            if (flag == 1) {
                shippingUnit.setFromAreaId("未匹配-" + shippingUnit.getFromAreaId());
            } else {
                shippingUnit.setToAreaId("未匹配-" + shippingUnit.getToAreaId());
            }
        }
    }

    //启用所有待使用的运输单元
    @PostMapping(value = "/import/useAll")
    public BaseResponse useAll() {
        return shippingUnitService.useImportAll();
    }

    //移除所有待使用的运输单元
    @RequestMapping(value = "/import/removeAll", method = RequestMethod.POST)
    public BaseResponse removeAll() {
        return shippingUnitService.removeAllUseless();
    }


   /* //导出所有待启用的运输单元数据
    @RequestMapping(value = "/import/multiExport",method = RequestMethod.GET)
    public BaseResponse multiExport(HttpServletResponse response) {
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            String date=simpleDateFormat.format(new Date());
            String fileName =date+ "unit_data.xls";
            //导出所有待启用的运输单元
            List<ShippingUnit> list = shippingUnitService.multiExportShippingUnits();
            //产品库详情 XSSFWorkbook
            ExcelExport excelExport=new ExcelExport(new HSSFWorkbook(),"未启用运输单元数据",null,
                    ShippingUnit.class, ExcelField.Type.EXPORT, new String[0]);

            //创建一个样式
            CellStyle cellStyle = excelExport.getWorkbook().createCellStyle();
            DataFormat format = excelExport.getWorkbook().createDataFormat();
            //具体如何创建cell就省略了，最后设置单元格的格式这样写
            Sheet oneSheet = excelExport.getWorkbook().getSheetAt(0);
            cellStyle.setDataFormat(format.getFormat("@"));
            oneSheet.setColumnWidth(0, 1000);
            oneSheet.setDefaultColumnStyle(6, cellStyle);
            oneSheet.autoSizeColumn(1, true);

            excelExport.setDataList(list).write(response, fileName).close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
    }*/


    //导出所有待启用的运输单元list
    @RequestMapping(value = "/import/multiExport", method = RequestMethod.GET)
    public BaseResponse multiExport() {
        List<ShippingUnit> list = shippingUnitService.multiExportShippingUnits();
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, list);
    }


    //==================================================================

    /**
     * 运输单元列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/unit/infoList", method = RequestMethod.POST)
    public ShippingUnitListResponse infoList(@RequestBody @Valid ShippingUnitListRequest request) {
        if (request.getT() == null) {
            request.setT(new ShippingUnit());
        }
        ShippingUnit shippingUnit = request.getT();
        shippingUnit.setState(1);
        List<ShippingUnit> results = shippingUnitService.select(request);
        Long total = shippingUnitService.count(request);
        request.setTotal(total);
        ShippingUnitListResponse res = new ShippingUnitListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }


    /**
     * 单个运输单元查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unit/info/{id}", method = RequestMethod.POST)
    public ShippingUnitInfoResponse info(@PathVariable Long id) {
        ShippingUnit result = shippingUnitService.selectByPrimaryKey(id);
        ShippingUnitVo shippingUnitVo = new ShippingUnitVo();
        BeanUtils.copyProperties(result, shippingUnitVo);
        ShippingUnitInfoResponse res = new ShippingUnitInfoResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, shippingUnitVo, id);
        return res;
    }

    /**
     * 新增运输单元
     *
     * @param shippingUnits
     * @return
     */
    @ApiOperation("新增运输单元")
    @RequestMapping(value = "/unit/add", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid List<ShippingUnit> shippingUnits) {
        if (ListUtils.isNotEmpty(shippingUnits) && shippingUnits.size() > 0) {
            shippingUnitService.insertBatch(shippingUnits);
            redisTemplate.delete(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST);
        }
        return BaseResponse.success();
    }

    //    /**
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
//    @Permission(permission = "ship:shippingunit:del:id")
//    public ShippingUnitDelResponse del(@PathVariable Long id) {
//        shippingUnitService.deleteByPrimaryKey(id);
//        ShippingUnitDelResponse res = new ShippingUnitDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }
    @ApiOperation("修改运输单元")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public BaseResponse update(@PathVariable Long id, @RequestBody @Valid ShippingUnitUpdateRequest request) {
        ShippingUnit entity = request.toShippingUnit(id);
        try {
            shippingUnitService.updateByPrimaryKeySelective(entity);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        ShippingUnitUpdateResponse res = new ShippingUnitUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 运输导入列表删除 ，运输单元删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除运输单元")
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public BaseResponse del(@RequestBody ShipUnitDelRequest request) {
        try {
            shippingUnitService.delete(request);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        ShippingUnitUpdateResponse res = new ShippingUnitUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        return res;
    }


    /**
     * 运输单元开启
     */
    @ApiOperation("启用")
    @RequestMapping(value = "/open/{id}", method = RequestMethod.POST)
    public BaseResponse open(@PathVariable Long id) {
        ShippingUnit shippingUnit = shippingUnitService.selectByPrimaryKey(id);
        shippingUnit.setState(1);
        try {
            shippingUnitService.updateByPrimaryKeySelective(shippingUnit);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.success();

    }


    /**
     * 运输单元关闭
     */
    @ApiOperation("禁用")
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    public BaseResponse close(@PathVariable Long id) {
        ShippingUnit shippingUnit = shippingUnitService.selectByPrimaryKey(id);
        shippingUnit.setState(0);
        try {
            shippingUnitService.updateByPrimaryKeySelective(shippingUnit);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.success();
    }


    public static void main(String[] args) {
        System.out.println(DateUtils.formatDate(1641358800426L,"yyyy-MM-dd HH:mm:ss"));
    }
}
