package com.demo.util;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yang
 * @TODO 读取excel工具类
 * @date 2018/3/7 11:45
 **/
public class POIExcelUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /*
     * @todo 兼容xls、xlsx格式
     * @param file 传入的文件
     * @author tangyuan
     * @date 2018/3/7 9:40
     */
    public static Workbook getWorkBook(File file) throws Exception {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;

        //获取excel文件的io流
        InputStream inputStream =  new BufferedInputStream(new FileInputStream(file));
        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
        if (fileName.endsWith(xls)) {
            //2003
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(xlsx)) {
            //2007
            workbook = new XSSFWorkbook(inputStream);
        }

        return workbook;
    }
    public static Workbook getWorkBook(InputStream inputStream ,String fileName) throws Exception {
      
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook =  WorkbookFactory.create(inputStream);

        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        CellType cellType = cell.getCellTypeEnum();  
        /** 处理数字型的,自动去零 */
        if (CellType.NUMERIC == cellType) {
            //是否是日期
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                short format = cell.getCellStyle().getDataFormat();
                SimpleDateFormat sdf = null;
                if (format == 14 || format == 31 || format == 57 || format == 58
                        || (176 <= format && format <= 178) || (182 <= format && format <= 196)
                        || (210 <= format && format <= 213) || (208 == format)) { // 日期
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    cellValue = sdf.format(date);
                } else if (format == 20 || format == 32 || format == 183 || (200 <= format && format <= 209)) { // 时间
                    sdf = new SimpleDateFormat("HH:mm");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    cellValue = sdf.format(date);
                }
            } else {
               /* cellValue = getRightStr(cell.getNumericCellValue()
                        + "");*/
            	cellValue=cell.getNumericCellValue()+"";
            }
        }
        /** 处理字符串型 */
        else if (CellType.STRING == cellType) {
            cellValue = StringUtils.trim(cell.getStringCellValue());
        }
        /** 处理布尔型 */
        else if (CellType.BOOLEAN == cellType) {
            cellValue = cell.getBooleanCellValue() + "";
        }   /** 处理公式 */
        else if (CellType.FORMULA == cellType) {
            try {
                cellValue = getRightStr(cell.getNumericCellValue()
                        + "");
            } catch (IllegalStateException e) {
                cellValue = String.valueOf(cell.getRichStringCellValue());
            }
        }
        /** 其它的,非以上几种数据类型 */
        else {
            cellValue = cell.toString() + "";
        }
        return cellValue;
    }

    /*
     * @todo  正确地处理整数后自动加零的情况
     * @param
     * @return
     * @author tangyuan
     * @date 2018/3/13 16:19
     */
    private static String getRightStr(String sNum) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        String resultStr = decimalFormat.format(new BigDecimal(sNum));
        if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
            resultStr = resultStr.substring(0, resultStr.indexOf("."));
            resultStr = StringUtils.trim(resultStr);
        }
        return resultStr;
    }

    /**
     * @param file 传入的文件
     * @return 数据集合，每一项为字符串数组
     * @todo 读取excle 文件
     * @author tangyuan
     * @date 2018/3/7 11:45
     */
    public static List<String[]> readExcel(File file) throws Exception {
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //System.out.println("sheet总数: "+workbook.getNumberOfSheets());
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表,可能有多张工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
               // System.out.println("第"+sheetNum+"个sheet工作表的名字: "+sheet.getSheetName());
                //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
                
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
               // System.out.println("sheet的开始行: "+firstRowNum);
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
               // System.out.println("sheet的结束行: "+lastRowNum);
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                   
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                   
                    //列的总数
                  /*  getPhysicalNumberOfCells 是获取不为空的列个数。
                    getLastCellNum 是获取最后一个不为空的列是第几个。*/
                    int lastCellNum = row.getLastCellNum();
                   // System.out.println("sheet的当前行: "+rowNum+"  ; 当前行的开始列: "+firstCellNum+"   ;  当前行的结束列: "+lastCellNum);
                    
                    //excle有时会读出firstnum=-1的情形
                    if (firstCellNum >= 0 && lastCellNum >= firstCellNum) {
                        String[] cells = new String[lastCellNum];
                        if(cells!=null){
                        //循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                           // System.out.print(cells[cellNum]+"  ;  ");  
                        }
                        list.add(cells);
                       // System.out.println();
                        }
                    }
                }
            
            }
        }
        return list;
    }
    public static List<String[]> readExcel( InputStream inputStream ,String fileName) throws Exception {
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(inputStream,fileName);
        //System.out.println("sheet总数: "+workbook.getNumberOfSheets());
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表,可能有多张工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
               // System.out.println("第"+sheetNum+"个sheet工作表的名字: "+sheet.getSheetName());
                //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
            
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
               // System.out.println("sheet的开始行: "+firstRowNum);
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
               // System.out.println("sheet的结束行: "+lastRowNum);
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum ; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                   
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                   
                    //列的总数
                  /*  getPhysicalNumberOfCells 是获取不为空的列个数。
                    getLastCellNum 是获取最后一个不为空的列是第几个。*/
                    int lastCellNum = row.getLastCellNum();
                   // System.out.println("sheet的当前行: "+rowNum+"  ; 当前行的开始列: "+firstCellNum+"   ;  当前行的结束列: "+lastCellNum);
                    
                    //excle有时会读出firstnum=-1的情形
                    if (firstCellNum >= 0 && lastCellNum >= firstCellNum) {
                        String[] cells = new String[lastCellNum];
                        if(cells!=null){
                        //循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                           // System.out.print(cells[cellNum]+"  ;  ");  
                        }
                        
                        list.add(cells);
                       // System.out.println();
                        }
                    }
                }
           
                break;
            }
        }
        return list;
    }
    /**
     * @todo 保存单个文件
     * @param file
     * @param request
     * @return 文件保存路径
     * @author tangyuan
     * @date 2018/3/7 10:53
     */
    public String saveFile(MultipartFile file, HttpServletRequest request) throws Exception {
        String filePath = "failSave";
        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;
            File serverFile = null;
            try {
                String realPath = request.getSession().getServletContext()
                        .getRealPath(File.separator);
                File dir = new File(realPath + File.separator + "tmpFiles");
                filePath = dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename() + +new Date().getTime();
                if (!dir.exists()){
                    dir.mkdirs();
                }
                serverFile = new File(filePath);
                in = file.getInputStream();
                out = new FileOutputStream(serverFile);
                //1M
                byte[] b = new byte[1024 * 1024];
                int len = 0;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.close();
                in.close();

            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }

                if (in != null) {
                    in.close();
                    in = null;
                }

            }

        }
        return filePath;
    }
 
    public static void main(String[] args) throws Exception {
    	File file =new File("C:\\Users\\Administrator\\Desktop\\001001093001定额站111.xls");
    	readExcel(file);
    }
}
