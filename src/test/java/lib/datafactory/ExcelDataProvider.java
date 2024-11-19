package lib.datafactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataProvider implements MyDataProvider{

	@Override
	public Map<String, String> getTestData(Map<String,String> map) {
		
		String filePath = map.get("filePath");
		
		FileInputStream fis;
		XSSFWorkbook workbook=null;
		XSSFSheet sheet=null;
		
		Map<String,String> mapEachRecords=null;
		
		//List<Map<String,String>> lstAllRecords=null;
		try {
			fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			
			int columnCount = sheet.getRow(0).getLastCellNum();
			
			mapEachRecords = new LinkedHashMap();
			
			for(int i=1;i<lastRowNum;i++)
			{
				XSSFRow row = sheet.getRow(i);
				
				
				for(int j=0;j<columnCount;j++)
				{
					
					String cellValue = row.getCell(j).getStringCellValue();
					mapEachRecords.put(sheet.getRow(0).getCell(j).getStringCellValue(),cellValue);
					
				}
				
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapEachRecords;
	}

}
