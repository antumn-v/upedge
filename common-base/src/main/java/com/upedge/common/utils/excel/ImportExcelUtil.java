package com.upedge.common.utils.excel;

import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.ObjectUtils;
import com.upedge.common.utils.ReflectUtils;
import com.upedge.common.utils.callback.MethodCallback;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by cjq on 2018/12/25.
 */
public class ImportExcelUtil implements Closeable {
    private static Logger log = LoggerFactory.getLogger(ImportExcelUtil.class);
    private Workbook wb;
    private Sheet sheet;
    private int headerNum;
    private Set<Class<?>> fieldTypes;

    private static NumberFormat numberFormat= NumberFormat.getNumberInstance();
    static {
        numberFormat.setGroupingUsed(false);
    }

    public ImportExcelUtil(File file) throws InvalidFormatException, IOException {
        this((File)file, 0, Integer.valueOf(0));
    }

    public ImportExcelUtil(File file, int headerNum) throws InvalidFormatException, IOException {
        this((File)file, headerNum, Integer.valueOf(0));
    }

    public ImportExcelUtil(File file, int headerNum, Object sheetIndexOrName) throws InvalidFormatException, IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndexOrName);
    }

    public ImportExcelUtil(MultipartFile multipartFile, int headerNum, Object sheetIndexOrName) throws InvalidFormatException, IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndexOrName);
    }

    public ImportExcelUtil(String fileName, InputStream is, int headerNum, Object sheetIndexOrName) throws InvalidFormatException, IOException {
        this.fieldTypes = new HashSet<>();
        if(StringUtils.isBlank(fileName)) {
            throw new ExcelException("导入文档为空!");
        } else {
            if(fileName.toLowerCase().endsWith("xls")) {
                this.wb = new HSSFWorkbook(is);
            } else {
                if(!fileName.toLowerCase().endsWith("xlsx")) {
                    throw new ExcelException("文档格式不正确!");
                }

                this.wb = new XSSFWorkbook(is);
            }

            this.setSheet(sheetIndexOrName, headerNum);
            log.debug("Initialize success.");
        }
    }

    private void addAnnotation(List<Object[]> annotationList, ExcelField ef, Object fOrM, ExcelField.Type type, String... groups) {
        if(ef != null && (ef.type() == ExcelField.Type.ALL || ef.type() == type)) {
            if(groups != null && groups.length > 0) {
                boolean inGroup = false;
                String[] var7 = groups;
                int var8 = groups.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String g = var7[var9];
                    if(inGroup) {
                        break;
                    }

                    String[] var11 = ef.groups();
                    int var12 = var11.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        String efg = var11[var13];
                        if(StringUtils.equals(g, efg)) {
                            inGroup = true;
                            annotationList.add(new Object[]{ef, fOrM});
                            break;
                        }
                    }
                }
            } else {
                annotationList.add(new Object[]{ef, fOrM});
            }
        }

    }

    public Workbook getWorkbook() {
        return this.wb;
    }

    public void setSheet(Object sheetIndexOrName, int headerNum) {
        if(!(sheetIndexOrName instanceof Integer) && !(sheetIndexOrName instanceof Long)) {
            this.sheet = this.wb.getSheet(ObjectUtils.toString(sheetIndexOrName));
        } else {
            this.sheet = this.wb.getSheetAt(ObjectUtils.toInteger(sheetIndexOrName).intValue());
        }

        if(this.sheet == null) {
            throw new ExcelException("没有找到‘" + sheetIndexOrName + "’工作表!");
        } else {
            this.headerNum = headerNum;
        }
    }

    public Row getRow(int rownum) {
        Row row = this.sheet.getRow(rownum);
        if(row == null) {
            return null;
        } else {
            short cellNum = 0;
            short emptyNum = 0;
            Iterator it = row.cellIterator();

            while(it.hasNext()) {
                ++cellNum;
                Cell cell = (Cell)it.next();
                if(StringUtils.isBlank(cell.toString())) {
                    ++emptyNum;
                }
            }

            if(cellNum == emptyNum) {
                return null;
            } else {
                return row;
            }
        }
    }

    public int getDataRowNum() {
        return this.headerNum;
    }

    public int getLastDataRowNum() {
        return this.sheet.getLastRowNum() + 1;
    }

    public int getLastCellNum() {
        Row row = this.getRow(this.headerNum);
        return row == null?0:row.getLastCellNum();
    }

    public Object getCellValue(Row row, int column) {
        if(row == null) {
            return row;
        } else {
            Object val = "";

            try {
                Cell e = row.getCell(column);

//                if(e.getCellType()==Cell.CELL_TYPE_NUMERIC){
//                    String value=numberFormat.format(e.getNumericCellValue());
//                    System.out.println(value);
//                }

                if(e != null) {
                    if(e.getCellType() == 0) {
//                        Double val1 = Double.valueOf(e.getNumericCellValue());
//                        if(HSSFDateUtil.isCellDateFormatted(e)) {
//                            val = DateUtil.getJavaDate(((Double)val1).doubleValue());
//                        } else if(((Double)val1).doubleValue() % 1.0D > 0.0D) {
//                            val = (new DecimalFormat("0.00")).format(val1);
//                        } else {
//                            val = (new DecimalFormat("0")).format(val1);
//                        }
                        val=numberFormat.format(e.getNumericCellValue());


                    } else if(e.getCellType() == 1) {
                        val = e.getStringCellValue();
                    } else if(e.getCellType() == 2) {
                        try {
                            val = e.getStringCellValue();
                        } catch (Exception var8) {
                            FormulaEvaluator evaluator = e.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                            evaluator.evaluateFormulaCell(e);
                            CellValue cellValue = evaluator.evaluate(e);
                            switch(cellValue.getCellType()) {
                                case 0:
                                    val = Double.valueOf(cellValue.getNumberValue());
                                    break;
                                case 1:
                                    val = cellValue.getStringValue();
                                    break;
                                case 2:
                                case 3:
                                default:
                                    val = e.getCellFormula();
                                    break;
                                case 4:
                                    val = Boolean.valueOf(cellValue.getBooleanValue());
                                    break;
                                case 5:
                                    val = ErrorEval.getText(cellValue.getErrorValue());
                            }
                        }
                    } else if(e.getCellType() == 4) {
                        val = Boolean.valueOf(e.getBooleanCellValue());
                    } else if(e.getCellType() == 5) {
                        val = Byte.valueOf(e.getErrorCellValue());
                    }
                }

                return val;
            } catch (Exception var9) {
                return val;
            }
        }
    }

    public <E> List<E> getDataList(Class<E> cls, String... groups) throws InstantiationException, IllegalAccessException {
        return this.getDataList(cls, false, groups);
    }

    public <E> List<E> getDataList(Class<E> cls, final boolean isThrowException, String... groups) throws InstantiationException, IllegalAccessException {
        return this.getDataList(cls, new MethodCallback() {
            public Object execute(Object... params) {
                if(isThrowException) {
                    Exception ex = (Exception)params[0];
                    int rowNum = ((Integer)params[1]).intValue();
                    int columnNum = ((Integer)params[2]).intValue();
                    throw new ExcelException("Get cell value [" + rowNum + "," + columnNum + "]", ex);
                } else {
                    return null;
                }
            }
        }, groups);
    }

    public <E> List<E> getDataList(Class<E> cls, MethodCallback exceptionCallback, String... groups) throws InstantiationException, IllegalAccessException {
        ArrayList annotationList = ListUtils.newArrayList();
        Field[] fs = cls.getDeclaredFields();
        Field[] ms = fs;
        int dataList = fs.length;

        int i;
        int j;
        int os;
        ExcelField ef;
        for(i = 0; i < dataList; ++i) {
            Field e = ms[i];
            ExcelFields row = (ExcelFields)e.getAnnotation(ExcelFields.class);
            if(row != null && row.value() != null) {
                ExcelField[] sb = row.value();
                j = sb.length;

                for(os = 0; os < j; ++os) {
                    ef = sb[os];
                    this.addAnnotation(annotationList, ef, e, ExcelField.Type.IMPORT, groups);
                }
            }

            ExcelField var28 = (ExcelField)e.getAnnotation(ExcelField.class);
            this.addAnnotation(annotationList, var28, e, ExcelField.Type.IMPORT, groups);
        }

        Method[] var21 = cls.getDeclaredMethods();
        Method[] var22 = var21;
        i = var21.length;

        for(int var24 = 0; var24 < i; ++var24) {
            Method var26 = var22[var24];
            ExcelFields var29 = (ExcelFields)var26.getAnnotation(ExcelFields.class);
            if(var29 != null && var29.value() != null) {
                ExcelField[] var31 = var29.value();
                os = var31.length;

                for(int var34 = 0; var34 < os; ++var34) {
                    ExcelField column = var31[var34];
                    this.addAnnotation(annotationList, column, var26, ExcelField.Type.IMPORT, groups);
                }
            }

            ExcelField var32 = (ExcelField)var26.getAnnotation(ExcelField.class);
            this.addAnnotation(annotationList, var32, var26, ExcelField.Type.IMPORT, groups);
        }

        Collections.sort(annotationList, new Comparator() {
            @Override
            public int compare(Object ob1, Object ob2) {
                Object[] o1= (Object[]) ob1;
                Object[] o2= (Object[]) ob2;
                return (new Integer(((ExcelField)o1[0]).sort())).compareTo(new Integer(((ExcelField)o2[0]).sort()));
            }
        });
        ArrayList var23 = ListUtils.newArrayList();

        for(i = this.getDataRowNum(); i < this.getLastDataRowNum(); ++i) {
            Object var25 = cls.newInstance();
            Row var27 = this.getRow(i);
            if(var27 != null) {
                StringBuilder var30 = new StringBuilder();

                for(j = 0; j < annotationList.size(); ++j) {
                    Object[] var33 = (Object[])annotationList.get(j);
                    ef = (ExcelField)var33[0];
                    int var35 = ef.column() != -1?ef.column():j;
                    Object val = this.getCellValue(var27, var35);
                    if(val != null) {
                        Class valType;
                        if(StringUtils.isNotBlank(ef.dictType())) {
                            try {
                                valType = Class.forName("com.jeesite.modules.sys.utils.DictUtils");
                                val = valType.getMethod("getDictValue", new Class[]{String.class, String.class, String.class}).invoke((Object)null, new Object[]{ef.dictType(), val.toString(), ""});
                            } catch (Exception var19) {
                                log.info("Get cell value [" + i + "," + var35 + "] error: " + var19.toString());
                                val = null;
                            }
                        }

                        valType = Class.class;
                        if(var33[1] instanceof Field) {
                            valType = ((Field)var33[1]).getType();
                        } else if(var33[1] instanceof Method) {
                            Method mthodName = (Method)var33[1];
                            if("get".equals(mthodName.getName().substring(0, 3))) {
                                valType = mthodName.getReturnType();
                            } else if("set".equals(mthodName.getName().substring(0, 3))) {
                                valType = ((Method)var33[1]).getParameterTypes()[0];
                            }
                        }

                        String var36;
                        try {
                            if(StringUtils.isNotBlank(ef.attrName())) {
                                if(ef.fieldType() != Class.class) {
                                    this.fieldTypes.add(ef.fieldType());
                                    val = ef.fieldType().getMethod("getValue", new Class[]{String.class}).invoke((Object)null, new Object[]{val});
                                }
                            } else if(val != null) {
                                if(valType == String.class) {
                                    var36 = String.valueOf(val.toString());
                                    if(StringUtils.endsWith(var36, ".0")) {
                                        val = StringUtils.substringBefore(var36, ".0");
                                    } else {
                                        val = String.valueOf(val.toString());
                                    }
                                } else if(valType == Integer.class) {
                                    val = Integer.valueOf(Double.valueOf(val.toString()).intValue());
                                } else if(valType == Long.class) {
                                    val = Long.valueOf(Double.valueOf(val.toString()).longValue());
                                } else if(valType == Double.class) {
                                    val = Double.valueOf(val.toString());
                                } else if(valType == Float.class) {
                                    val = Float.valueOf(val.toString());
                                } else if(valType == Date.class) {
                                    if(val instanceof String) {
                                        val = DateUtils.parseDate(val);
                                    } else if(val instanceof Double) {
                                        val = DateUtil.getJavaDate(((Double)val).doubleValue());
                                    }
                                } else if(ef.fieldType() != Class.class) {
                                    this.fieldTypes.add(ef.fieldType());
                                    val = ef.fieldType().getMethod("getValue", new Class[]{String.class}).invoke((Object)null, new Object[]{val.toString()});
                                } else {
                                    Class var37 = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + valType.getSimpleName() + "Type"));
                                    this.fieldTypes.add(var37);
                                    val = var37.getMethod("getValue", new Class[]{String.class}).invoke((Object)null, new Object[]{val.toString()});
                                }
                            }
                        } catch (Exception var20) {
                            log.info("Get cell value [" + i + "," + var35 + "] error: " + var20.toString());
                            val = null;
                            exceptionCallback.execute(new Object[]{var20, Integer.valueOf(i), Integer.valueOf(var35)});
                        }

                        if(StringUtils.isNotBlank(ef.attrName())) {
                            ReflectUtils.invokeSetter(var25, ef.attrName(), val);
                        } else if(var33[1] instanceof Field) {
                            ReflectUtils.invokeSetter(var25, ((Field)var33[1]).getName(), val);
                        } else if(var33[1] instanceof Method) {
                            var36 = ((Method)var33[1]).getName();
                            if("get".equals(var36.substring(0, 3))) {
                                var36 = "set" + StringUtils.substringAfter(var36, "get");
                            }

                            ReflectUtils.invokeMethod(var25, var36, new Class[]{valType}, new Object[]{val});
                        }
                    }

                    var30.append(val + ", ");
                }

                var23.add(var25);
                log.debug("Read success: [" + i + "] " + var30.toString());
            }
        }

        return var23;
    }

    public void close() {
        Iterator it = this.fieldTypes.iterator();

        while(it.hasNext()) {
            Class clazz = (Class)it.next();

            try {
                clazz.getMethod("clearCache", new Class[0]).invoke((Object)null, new Object[0]);
            } catch (Exception var4) {
                ;
            }
        }

    }
}
