package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.pctrade.price.dao.DaoException;
import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;
import com.pctrade.price.utils.HttpUtils;

public class ReadOutputFromDbExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING_TYPE = "UTF-8";
	private static final String ERROR_NAME_DAO = "/errorPages/errorDao.jsp";
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		HttpSession session = request.getSession();
		HttpUtils.Encode(request, response, ENCODING_TYPE);
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment; filename=priceList.xls");

		String[] nextLine;
		int lnNum = 0;
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("CSV2XLS");
		Row row;
		DaoProduct daoProduct = new DaoProductImpl();
		List<Product> productsList;
		try {
			productsList = daoProduct.showAllProductList();
		
		Map<String, String[]> excelData = new HashMap<String, String[]>();

		for (Product product : productsList) {
			nextLine = product.toString().split(",");
			lnNum++;
			excelData.put(Integer.toString(lnNum), nextLine);
		}
		Set<String> keyset = excelData.keySet();
		int rowNum = 0;
		for (String key : keyset) {
			row = sheet.createRow(rowNum++);
			String[] objArr = excelData.get(key);
			int cellNum = 0;
			for (String obj : objArr) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(obj);
			}
		}
		workBook.write(response.getOutputStream());		
	
	} catch (DaoException e) {
		session.setAttribute("exception", e);
		HttpUtils.forward(ERROR_NAME_DAO, request, response);
	}
	}
}
