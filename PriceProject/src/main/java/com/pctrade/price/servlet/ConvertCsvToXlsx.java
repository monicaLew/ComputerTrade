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
import org.apache.commons.io.FilenameUtils;
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
		OutputStream out = response.getOutputStream();
		request.setCharacterEncoding("UTF-8");
		try {
			InputStream filecontent = new FileInputStream("C:\\sample.csv"); // Initialize
																				// the
																				// input
																				// stream
																				// that
																				// accepts
																				// the
																				// user
																				// uploaded
																				// CSV
																				// file

			HttpSession session = request.getSession();
			response.setCharacterEncoding("UTF-8");
			// Check that we have a file upload request

			DiskFileItemFactory factory = new DiskFileItemFactory();
			List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);
			String outputtype = "1"; // default output type
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldname = item.getFieldName();
					if (fieldname.equals("element_2")) {
						outputtype = item.getString(); // identifies the type of
														// conversion required
					}
					// the value can be 1 for XLS conversion, 2 for XLSX
					// conversion

				} else {
					// The uploaded file is processed in this section
					String fieldname = item.getFieldName();
					String filename = FilenameUtils.getName(item.getName());
					filecontent = item.getInputStream();
					// Uploaded file is obtained into Inputstream object at this
					// step
				}
			}
			// We know the output type and have the file in the input stream
			// now. We can now convert CSV to XLSX and return response back to
			// server

			@SuppressWarnings("resource")    // зачем мне эклипс сказал это добавить
			CSVReader reader = new CSVReader(new InputStreamReader(filecontent)); // reads
																					// the
																					// input
																					// CSV
																					// file
																					// in
																					// the
																					// servlet
			String[] nextLine;
			int lnNum = 0;
			/*
			 * Initialize both XLS and XLSX formats to start with. Only one will
			 * be used
			 */
			HSSFWorkbook new_workbook = new HSSFWorkbook();
			HSSFSheet sheet = new_workbook.createSheet("CSV2XLS");
			XSSFWorkbook new_workbook_xlsx = new XSSFWorkbook();
			XSSFSheet sheet_xlsx = new_workbook_xlsx.createSheet("CSV2XLS");
			Row row;
			if (outputtype.equals("1")) {
				/* Set response type for XLS format */
				response.setContentType("application/vnd.ms-excel");
			}
			if (outputtype.equals("2")) {
				/* Set response type for XLSX format */
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}
			Map<String, Object[]> excel_data = new HashMap<String, Object[]>(); // create
																				// a
																				// map
																				// and
																				// define
																				// data
			/* Populate data into logical Map */
			while ((nextLine = reader.readNext()) != null) {
				lnNum++;
				excel_data.put(Integer.toString(lnNum), new Object[] { nextLine[0], nextLine[1] }); // !!!
																									// exception
																									// !!!
			}
			/* Ready to convert CSV to logical Excel object */
			Set<String> keyset = excel_data.keySet();
			int rownum = 0;
			for (String key : keyset) { // loop through the data and add them to
										// the cell
				if (outputtype.equals("1")) {
					row = sheet.createRow(
							rownum++); /*
										 * Create rows in the doucment using the
										 * right sheet object
										 */
				} else {
					row = sheet_xlsx.createRow(rownum++);
				}
				Object[] objArr = excel_data.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof Double)
						cell.setCellValue((Double) obj);
					else
						cell.setCellValue((String) obj);
				}
			}
			/* Write response to output stream */
			if (outputtype.equals("1")) {
				new_workbook.write(out);
			} else {
				new_workbook_xlsx.write(out);
			}
		} catch (Exception e) {
			System.out.println(e); /* Throw exceptions to log files */
		} finally {
			out.close();/* Close the output stream */
		}
	}

}
