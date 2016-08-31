package com.huice.cases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Case07_excel {
	public static void main(String[] args)throws IOException{
		String path ="test-data/";
		String fileName = "info";
		String fileType ="xls";
		int x = 4;
		int y = 2;
		int sheetName = 0;
		getdata(path, fileName, fileType,sheetName);
		getCellValue(path, fileName, fileType,sheetName,x,y);
	}
	
		
		private static void getdata(String path,String fileName,String fileType,int sheetName) throws IOException   
	    {    
	        InputStream file = new FileInputStream(path+fileName+"."+fileType);    
	        Workbook wb = null;    
	        if (fileType.equals("xls")) {    
	            wb = new HSSFWorkbook(file);    
	        }    
	        else if (fileType.equals("xlsx")) {    
	            wb = new XSSFWorkbook(file);    
	        }    
	        else {    
	            System.out.println("您输入的文件格式不对！");    
	        }    
	        Sheet name1 = wb.getSheetAt(sheetName);
			int rowNum = name1.getLastRowNum();
			HSSFRow row = (HSSFRow) name1.getRow(0);
			int colNum = row.getPhysicalNumberOfCells();
			for(int i = 1; i<=rowNum; i++){
				row = (HSSFRow) name1.getRow(i);
				int j = 0;
				while(j<colNum){
					System.out.println(row.getCell(j).toString());
					j++;
				}
			}
	    }   
		

		private static void getCellValue(String path, String fileName, String fileType,int sheetName, int x, int y) throws IOException{
			
				InputStream file = new FileInputStream(path+fileName+"."+fileType);
				Workbook workbook = null;
				if(fileType.equals("xls")){
					workbook = new HSSFWorkbook(file);
				}else if(fileType.equals("xlsx")){
					workbook = new XSSFWorkbook(file);
				}else{
					System.out.println("您输入的文件格式不对！");
				}
				HSSFSheet sht = (HSSFSheet) workbook.getSheetAt(sheetName);
				HSSFRow row = (HSSFRow) sht.getRow(x);
				HSSFCell cell = row.getCell(y);
				System.out.println("该单元格值："+cell.getStringCellValue());
				
		}   
}
