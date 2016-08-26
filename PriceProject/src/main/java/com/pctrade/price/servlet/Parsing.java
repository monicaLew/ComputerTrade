package com.pctrade.price.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
		final String user = "root";
		final String password = "root";
		// final String filePath =
		// "c:\\MARINA\\Soft\\apache-tomcat-8.0.36\\webapps\\data\\";
		HttpSession session = request.getSession();
		final String filePath = getServletContext().getInitParameter("file-upload")
				+ session.getAttribute("lastFileNameUpload");

		session.setAttribute("path", filePath);

		Connection cn = null;
		PreparedStatement prepStmt = null;
		Statement stmt = null;
		int count = 0;
		ArrayList<String> mylist = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Driver Found");
		} catch (ClassNotFoundException e) {

			System.out.println("Driver not Found:" + e);
		}
		try {
			cn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/PC_TRADE", user, password);
			System.out.println("Connection :: [" + cn + "]");
			prepStmt = cn.prepareStatement(INSERT_RECORDS);
			stmt = cn.createStatement();
			//ResultSet result = stmt.executeQuery("SET AUTOCOMMIT = 0");
			// while(result.next()) {
			// int val = result.getInt(1);
			// System.out.println("* * * * *");
			// System.out.println(val);
			// count = val+1;
			// }
			// prepStmt.setInt(1,count);              // ааа, это идет подсчет строк и вставл номер строки в ее начало

			FileInputStream fis = new FileInputStream(new File(filePath));
			System.out.println("FileInputStream Object created..! ");

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			System.out.println("XSSFWorkbook Object created..! ");

			XSSFSheet sheet = workbook.getSheetAt(0);
			System.out.println("XSSFSheet Object created..! ");

			Iterator ite = sheet.rowIterator();
			System.out.println("Row Iterator invoked..! ");

			String date = (String) session.getAttribute("dateOfUpload");
			stmt.execute("DELETE FROM PRODUCT"); // ВЫНЕСТИ В ДАО ?

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
						prepStmt.setString(index, cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC: // handle double data
						int i = (int) cell.getNumericCellValue();
						prepStmt.setInt(index, (int) cell.getNumericCellValue());
						break;
					}
					index++;
				}
				prepStmt.setString(4, date);
				prepStmt.executeUpdate();
				if (index < 4) {
					incorrect++;
				}
			}
			session.setAttribute("incorrect", incorrect);
			ite = null;
			fis.close();
			prepStmt.close();
			cn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		String encodeURL = response.encodeURL("/next.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}
}
