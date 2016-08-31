package com.pctrade.price.classes;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.pctrade.price.entity.Product;
public class ReadCsv {

	public static List<Product> readCsvFillProtuct(String filePath, String uploadDate)
			throws NumberFormatException, IOException {

		List<Product> productsList = new ArrayList<Product>();
		System.out.println(" парам пам пам  !");			
		
		CSVReader reader = null;

		reader = new CSVReader(new FileReader(filePath));
		String[] arr;
		while ((arr = reader.readNext()) != null) {
			Product fillProduct = new Product();
			fillProduct.setArticleCode(Integer.valueOf(arr[0].trim()));
			fillProduct.setArticle(arr[1]);
			fillProduct.setPrice(Integer.parseInt(arr[2].trim()));
			fillProduct.setDate(uploadDate);
			productsList.add(fillProduct);
		}
		reader.close();
		return productsList;
	}
}
