import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class Utility {
	public static String getData() {
		String value = null;
		Workbook wb = null;
		try {

			FileInputStream fis = new FileInputStream("Resources//Amazon.xlsx");

			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);

		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);

		value = cell.getStringCellValue();
		return value;
	}

	public static void writeDataToFile(List<WebElement> suggestion) throws IOException {
		FileWriter writer = new FileWriter("Resources//suggestion.txt"); 
		for(WebElement ele : suggestion) {
		  writer.write(ele.getText() + System.lineSeparator());
		}
		writer.close();
	}
}
