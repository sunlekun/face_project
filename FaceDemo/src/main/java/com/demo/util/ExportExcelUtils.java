package com.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

 

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * Created by 单身狗 on 2017/5/24. 导出excel工具类
 *
 */
public class ExportExcelUtils {
	// private static org.slf4j.Logger log =
	// LoggerFactory.getLogger(ExportExcelUtils.class);
	private static final String module = ExportExcelUtils.class.getName(); 

	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook wb;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	public void setSheetColumWidth(int columNum,int width){
		this.sheet.setColumnWidth(columNum,width);
	    this.sheet.setDefaultColumnWidth(width);
	}

	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;

	/**
	 * 当前行号
	 */
	private int rownum;

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	public ExportExcelUtils(String title, List<String> headerList) {
		initialize(title, headerList);
	}

	/**
	 * 初始化函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export"+title);
		this.styles = createStyles(wb);
		
		// Create title
	/*	if (StringUtils.isNotBlank(title)) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList
							.size() - 1));
		}*/
		// Create header
		if (headerList == null) {
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length == 2) {
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch()
						.createCellComment(
								new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3,
										(short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(headerList.get(i));
			}
		//	((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
		 
		//	sheet.autoSizeColumn(i,true);
		}
		for (int i = 0; i < headerList.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
		}
		// log.debug("Initialize success.");
	}

	/**
	 * 创建表格样式
	 *
	 * @param wb
	 *            工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style = wb.createCellStyle();
		/*style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);*/
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		//titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		/*style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);*/
		//style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		//style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		//style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		//style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		/*style.setAlignment(CellStyle.ALIGN_LEFT);*/
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		/*style.setAlignment(CellStyle.ALIGN_CENTER);*/
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		/*style.setAlignment(CellStyle.ALIGN_RIGHT);*/
		styles.put("data3", style);
		
		//整行标为红色
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setFillForegroundColor(IndexedColors.PINK.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("redData", style);
		//橙色
		style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
       // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("orangeData", style);
		//黄色
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("yellowData", style);
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		// style.setWrapText(true);
		
		/*style.setAlignment(CellStyle.ALIGN_CENTER);*/
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		/*headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);*/
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		/*style.setBorderRight(CellStyle.BORDER_THIN);*/
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		/*style.setBorderLeft(CellStyle.BORDER_THIN);*/
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		/*style.setBorderTop(CellStyle.BORDER_THIN);*/
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		/*style.setBorderBottom(CellStyle.BORDER_THIN);*/
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		return styles;
	}

	/**
	 * 添加一行
	 * 
	 * @return 行对象
	 */
	public Row addRow() {
		return sheet.createRow(rownum++);
	}

	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val) {
		return this.addCell(row, column, val, 0, Class.class);
	}
	
	/**
	 * 添加一个红色背景的单元格
	 * @param row
	 * @param column
	 * @param val
	 * @return
	 */
	public Cell addRedCell(Row row, int column, Object val) {
        return this.addColorCell(row, column, val, 0, Class.class,"redData");
    }
    public Cell addOrangeCell(Row row, int column, Object val) {
        return this.addColorCell(row, column, val, 0, Class.class,"orangeData");
    }
	public Cell addYellowCell(Row row, int column, Object val) {
		return this.addColorCell(row, column, val, 0, Class.class,"yellowData");
	}

	private boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @param align
	 *            对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align,
			Class<?> fieldType) {
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"
				+ (align >= 1 && align <= 3 ? align : ""));
		try {
			if (val == null ||"".equals(val)) {
				cell.setCellValue("");
			}
			else if (val instanceof String) {
			
				cell.setCellValue((String) val);
				/*if(StringUtils.isNumeric((String)val)&&! ((String) val).startsWith("0")&& ((String) val).length()!=1){
					cell.setCellValue(Integer.parseInt((String)val));
				}else if(isDouble((String)val)&&! ((String) val).startsWith("0")&& ((String) val).length()!=1){
					cell.setCellValue(Double.parseDouble((String)val));
				}else{
					cell.setCellValue((String) val);
				}*/
				
				
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
				style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class) {
					cell.setCellValue((String) fieldType.getMethod("setValue",
							Object.class).invoke(null, val));
				} else {
					cell.setCellValue((String) Class
							.forName(
									this.getClass()
											.getName()
											.replaceAll(
													this.getClass()
															.getSimpleName(),
													"fieldtype."
															+ val.getClass()
																	.getSimpleName()
															+ "Type"))
							.getMethod("setValue", Object.class)
							.invoke(null, val));
				}
			}
		} catch (Exception ex) {
	/*		log.info("Set cell value [" + row.getRowNum() + "," + column
					+ "] error: " + ex.toString());*/
			System.out.println("Set cell value [" + row.getRowNum() + "," + column
					+ "] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}

	/**
	 * 添加一个红色背景的单元格
	 * @param row
	 * @param column
	 * @param val
	 * @param align
	 * @param fieldType
	 * @return
	 */
	public Cell addColorCell(Row row, int column, Object val, int align,
            Class<?> fieldType,String color) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("redData");
        if(color!=null&&!"".equals(color.trim())) {
             style = styles.get(color);
        }
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
            
                if(StringUtils.isNumeric((String)val)){
                    cell.setCellValue(Integer.parseInt((String)val));
                }else if(isDouble((String)val)){
                    cell.setCellValue(Double.parseDouble((String)val));
                }else{
                    cell.setCellValue((String) val);
                }
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue",
                            Object.class).invoke(null, val));
                } else {
                    cell.setCellValue((String) Class
                            .forName(
                                    this.getClass()
                                            .getName()
                                            .replaceAll(
                                                    this.getClass()
                                                            .getSimpleName(),
                                                    "fieldtype."
                                                            + val.getClass()
                                                                    .getSimpleName()
                                                            + "Type"))
                            .getMethod("setValue", Object.class)
                            .invoke(null, val));
                }
            }
        } catch (Exception ex) {
           System.out.println("Set cell value [" + row.getRowNum() + "," + column
                    + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }
	
	/**
	 * 输出到文件
	 * 
	 * @param name
	 *            输出文件名
	 */
	public ExportExcelUtils writeFile(String name)
			throws FileNotFoundException, IOException {
		File file = new File(name);
        if (file.exists()) {
            file.delete();
        }
        
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		os.flush();
		os.close();
		return this;
	}

	  public ExportExcelUtils writeFile( String realPath,String fileName) throws IOException {
	        String seperator = File.separator;
	       // String realPath = request.getSession().getServletContext().getRealPath(seperator) + seperator + fileName;
	        File file = new File(realPath + seperator + fileName);
	        if (file.exists()) {
	            file.delete();
	        }
	         
	        try {
	            FileOutputStream fos = new FileOutputStream(realPath + seperator + fileName);
	            this.write(fos);
	    		
	            System.out.println("写入成功");
	            fos.flush();
	            fos.close();
	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return this;
	    }

 

	 

	/**
	 * 输出数据流
	 *
	 * @param os
	 *            输出数据流
	 */
	public ExportExcelUtils write(OutputStream os) throws IOException {
		wb.write(os);
		return this;
	}

	/**
	 * 清理临时文件
	 */
	public ExportExcelUtils dispose() {
		wb.dispose();
		return this;
	}
	 public void addDataList1(List<List<String>> dataList) {
		 for (int i = 0; i < dataList.size(); i++) {
				Row row = addRow();
				for (int j = 0; j < dataList.get(i).size(); j++) {
					addCell(row, j, dataList.get(i).get(j));
				}
			}
	    }
	 
	 public void addDataList(List<String[]> dataList) {
		 for (int i = 0; i < dataList.size(); i++) {
				Row row = addRow();
				for (int j = 0; j < dataList.get(i).length; j++) {
					addCell(row, j, dataList.get(i)[j]);
				}
			}
	    }
	/**
	 * 导出测试
	 */

	public static void main(String[] args) throws Throwable {

		List<String> headerList = new ArrayList<String>();
		for (int i = 1; i <= 10; i++) {
			headerList.add("表头" + i);
		}

		List<String> dataRowList = new ArrayList<String>();
		for (int i = 1; i <= headerList.size(); i++) {
			dataRowList.add("数据" + i);
		}

		List<List<String>> dataList = new ArrayList<List<String>>();
		for (int i = 1; i <= 1000000; i++) {
			dataList.add(dataRowList);
		}

		ExportExcelUtils ee = new ExportExcelUtils("表格标题", headerList);

		for (int i = 0; i < dataList.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < dataList.get(i).size(); j++) {
				ee.addCell(row, j, dataList.get(i).get(j));
			}
		}

		ee.writeFile("C:\\Users\\Administrator\\Desktop\\user2.xlsx");

		ee.dispose();

		System.out.println("Export success.");

	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	public void mergeCell(int sheet, int firstRow, int lastRow, int firstCol,
			int lastCol) {
		Sheet mSheet = wb.getSheetAt(sheet);
		CellRangeAddress address = new CellRangeAddress(firstRow, lastRow,
				firstCol, lastCol);
		mSheet.addMergedRegion(address);
	}

	/**
	 * 跨行 合并单元格
	 * 
	 * @param rownum
	 *            起始行
	 * @param domListSize
	 *            跨行
	 * @param col
	 *            列
	 */
	public void mergeRow(int rownum, int domListSize, int col) {
		mergeCell(0, rownum - domListSize + 1, rownum, col, col);
	}

}
