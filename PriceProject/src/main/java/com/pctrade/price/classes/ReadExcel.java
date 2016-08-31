package com.pctrade.price.classes;

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
		System.out.println("FileInputStream Object created..! ");

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		System.out.println("XSSFWorkbook Object created..! ");

		XSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println("XSSFSheet Object created..! ");

		Iterator<Row> ite = sheet.rowIterator();
		System.out.println("Row Iterator invoked..! ");

		while (ite.hasNext()) {
			Product product = new Product();
			Row row = (Row) ite.next();
			System.out.println("Row value fetched..! ");
			Iterator<Cell> cellIterator = row.cellIterator();
			System.out.println("Cell Iterator invoked..! ");
			int index = 1;
			while (cellIterator.hasNext() && index < 4) {
				System.out.println("getting cell value..! 1");

				Cell cell = cellIterator.next();
				System.out.println("getting cell value..! 2");

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: // handle string columns
					if (index == 2) {
						product.setArticle(cell.getStringCellValue());
					} else if (index == 4) {
						product.setDate(date);
					}
					break;
				case Cell.CELL_TYPE_NUMERIC: // handle double data
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
		ite = null;
		fis.close();
		return productsList;
	}
}
