package com.pctrade.price.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;

public class ConvertCsvToXlsx extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			@SuppressWarnings("resource")
			InputStream filecontent = new FileInputStream("C:\\priceList.txt");

			DiskFileItemFactory factory = new DiskFileItemFactory();
			List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);
			String outputType = "1";
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldname = item.getFieldName();
					if (fieldname.equals("2")) {
						outputType = item.getString();
					}
				} else {
					filecontent = item.getInputStream();
				}
			}

			CSVReader reader = new CSVReader(new InputStreamReader(filecontent));
			String[] nextLine;
			int lnNum = 0;

			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet("CSV2XLS");
			XSSFWorkbook workBookXlsx = new XSSFWorkbook();
			XSSFSheet sheetXlsx = workBookXlsx.createSheet("CSV2XLS");
			Row row;
			if (outputType.equals("1")) {
				/* XLS format */
				response.setContentType("application/vnd.ms-excel");
			}
			if (outputType.equals("2")) {
				/* XLSX format */
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}
			Map<String, Object[]> excelData = new HashMap<String, Object[]>();
			while ((nextLine = reader.readNext()) != null) {
				lnNum++;
				excelData.put(Integer.toString(lnNum),
						new Object[] { nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4] });
			}
			reader.close();
			filecontent.close();

			Set<String> keyset = excelData.keySet();
			int rowNum = 0;
			for (String key : keyset) {
				if (outputType.equals("1")) {
					row = sheet.createRow(rowNum++);
				} else {
					row = sheetXlsx.createRow(rowNum++);
				}
				Object[] objArr = excelData.get(key);
				int cellNum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellNum++);
					if (obj instanceof Double)
						cell.setCellValue((Double) obj);
					else
						cell.setCellValue((String) obj);
				}
			}
			if (outputType.equals("1")) {
				workBook.write(out);
			} else {
				workBookXlsx.write(out);
			}
		} catch (Exception e) {
			session.setAttribute("exception", "Convert fail: " + e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		} finally {
			out.close();
		}
		String encodingURL = response.encodeRedirectURL("/index.jsp"); // никуда
																		// не
																		// хочет
																		// идти
		request.getRequestDispatcher(encodingURL).forward(request, response);
	}
}
