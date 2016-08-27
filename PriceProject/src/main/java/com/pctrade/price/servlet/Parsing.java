package com.pctrade.price.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

@WebServlet("/parse")
public class Parsing extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final String INSERT_RECORDS = "INSERT INTO PRODUCT(`ARTICLE_CODE`, `ARTICLE`, `PRICE`, `UPLOAD_DATE`) VALUES (?,?,?,?)";
	final String GET_COUNT = "SELECT * FROM PRODUCT";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		final String filePath = getServletContext().getInitParameter("file-upload")
				+ session.getAttribute("lastFileNameUpload");

		session.setAttribute("path", filePath);
		DaoProductImpl daoProductImpl = new DaoProductImpl();
		daoProductImpl.deleteTable();

		FileInputStream fis = new FileInputStream(new File(filePath));
		System.out.println("FileInputStream Object created..! ");

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		System.out.println("XSSFWorkbook Object created..! ");

		XSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println("XSSFSheet Object created..! ");

		Iterator ite = sheet.rowIterator();
		System.out.println("Row Iterator invoked..! ");

		String date = (String) session.getAttribute("dateOfUpload");

		Product product = new Product();
		int incorrect = 0;
		while (ite.hasNext()) {
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
						int i = (int) cell.getNumericCellValue();
						product.setArticleCode((int) cell.getNumericCellValue());
					} else if (index == 3) {
						int i = (int) cell.getNumericCellValue();
						product.setPrice((int) cell.getNumericCellValue());
					}
					break;
				}
				index++;
			}
			product.setDate(date);
			daoProductImpl.createProduct(product);

			if (index < 4) {
				incorrect++;
			}
		}
		session.setAttribute("incorrect", incorrect);
		ite = null;
		fis.close();

		String encodeURL = response.encodeURL("/next.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}
}
