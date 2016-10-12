package com.pctrade.price.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pctrade.price.entity.Product;

public class ReadExcel {

	public static List<Product> readExcelFillProduct(String filePath, String date) throws IOException {

		List<Product> productsList = new ArrayList<Product>();
		FileInputStream fis = new FileInputStream(new File(filePath));

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> iterator = sheet.rowIterator();
		while (iterator.hasNext()) {
			Product product = new Product();
			Row row = (Row) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			int index = 1;
			while (cellIterator.hasNext() && index < 4) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if (index == 2) {
						product.setArticle(cell.getStringCellValue());
					} else if (index == 4) {
						product.setDate(date);
					}
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (index == 1) {
						product.setArticleCode((int) cell.getNumericCellValue());
					} else if (index == 3) {
						product.setPrice((int) cell.getNumericCellValue());
					}
					break;
				}
				index++;
			}
			product.setDate(date);
			productsList.add(product);
		}
		iterator = null;
		fis.close();
		return productsList;
	}
}
