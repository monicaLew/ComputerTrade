package com.pctrade.price.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class UpdateExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			String date = (String) session.getAttribute("dateOfUpload");
			String filePath = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");

			FileInputStream fis = new FileInputStream(new File(filePath));
			System.out.println("FileInputStream Object created..! ");

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			System.out.println("XSSFWorkbook Object created..! ");

			XSSFSheet sheet = workbook.getSheetAt(0);
			System.out.println("XSSFSheet Object created..! ");

			Iterator<Row> ite = sheet.rowIterator();
			System.out.println("Row Iterator invoked..! ");

			DaoProduct daoProductImpl = new DaoProductImpl();
			List<Product> productsList = new ArrayList<Product>();

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
			daoProductImpl.updateProductTable(productsList);

			session.setAttribute("justfileList", productsList);
		} catch (Exception e) {
			session.setAttribute("exception", e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
		String encodeURL = response.encodeURL("/result.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}
}
