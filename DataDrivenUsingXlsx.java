package trakDataSetUpLibrary;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* This class read and write from/to excel
*
*/
public class DataDrivenUsingXlsx {
	

/****************** Read data from excel ***********************************/
		@SuppressWarnings("deprecation")
		public List<Object> getExcelData(String excelPath,String SheetName)
		{
				List<Object> outerAry = new ArrayList<Object>();
				try {
				/* incase of xlsx format*/
				//	XSSFWorkbook workbook = null;
				FileInputStream file = new FileInputStream(new File(excelPath));
				XSSFWorkbook workbook = new XSSFWorkbook (file);
				XSSFSheet sheet = workbook.getSheet(SheetName);
				String result;
				
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				List<Object> innerList = new ArrayList<Object>();
				while (cellIterator.hasNext())
				{
				Cell cell = cellIterator.next();
				cell.getColumnIndex();
				
				switch (cell.getCellType())
				{
				case Cell.CELL_TYPE_BOOLEAN:
				innerList.add(cell.getBooleanCellValue());
				break;
				case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)){
				innerList.add(cell.getDateCellValue());
				}
				else{
				result = new BigDecimal(cell.getNumericCellValue()).toPlainString();
				innerList.add(result);
				}
				break;
				case Cell.CELL_TYPE_STRING:
					if(cell.getStringCellValue().equals("null"))
					{System.out.println("Blank Value");
					innerList.add("");}
					else{
				innerList.add(cell.getStringCellValue());
				}
				break;
				case Cell.CELL_TYPE_BLANK:
					
					//innerList.add("");
					//cell.setCellType(Cell.CELL_TYPE_BLANK);
					
					//innerList.add(null);
				//cell=row.getCell(c, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK );
				break;
				case Cell.CELL_TYPE_ERROR:
				break;
				case Cell.CELL_TYPE_FORMULA:
					//innerList.add(cell.getCellFormula());
				innerList.add(cell.getStringCellValue());
				//innerList.add(cell.getNumericCellValue());
				break;
				
				}
				}
				System.out.println("");
				outerAry.add(innerList);
				}
				
				workbook.close();
				file.close();
				
				} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
				} catch (IOException ioe) {
				ioe.printStackTrace();
				}
				return outerAry;
		}//end of reading.

/*********************************** Write data to excel *********************************************/
@SuppressWarnings("resource")
public void setExcelData(String excelPath,String SheetName,String value,int rownum,int cellnum) {
				try{
				FileInputStream file = new FileInputStream(new File(excelPath));
				XSSFWorkbook workbook = new XSSFWorkbook (file);
				XSSFSheet sheet = workbook.getSheet(SheetName);
				
				Cell cell = null;
				cell = sheet.getRow(rownum).createCell(cellnum);
				cell.setCellValue(value);
				
				file.close();
				FileOutputStream out = new FileOutputStream(new File(excelPath));
				workbook.write(out);
				out.close();
				} catch (FileNotFoundException e) {
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
				
				}//end of writing.



}
