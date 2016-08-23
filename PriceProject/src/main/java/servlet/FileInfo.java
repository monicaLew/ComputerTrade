package servlet;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;


public class FileInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String INSERT_FILE_INFO = "INSERT INTO FILE_INFO" + "(FILE_NAME, SIZE_KB, UPLOAD_DATE) VALUES" + "(?,?,?)";
    final String GET_FILE_INFO = "SELECT * FROM FILE_INFO";

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String user = "root";
		final String password = "root";	
		 request.setCharacterEncoding("UTF-8");	
		 HttpSession session = request.getSession();
		 response.setContentType("text/html");
	     response.setCharacterEncoding("UTF-8");
		
	     Connection con = null;
	     PreparedStatement prepStmt = null;
	     Statement stmt = null;  

	        try {
				Class.forName("com.mysql.jdbc.Driver");	
				
				System.out.println("Driver Found");
			} catch (ClassNotFoundException e) {
				
				System.out.println("Driver not Found:" + e);
			}
	        try{            
	        	con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/PC_TRADE", user, password);
	            System.out.println("Connection :: ["+con+"]");
	            prepStmt = con.prepareStatement(INSERT_FILE_INFO);
	            stmt = con.createStatement();
	            //ResultSet result = stmt.executeQuery(GET_COUNT);
	     
	            String fileName = (String)session.getAttribute("lastFileNameUpload");	          
	            String faleSize = String.valueOf((Integer)session.getAttribute("fileSize"));
	            String date = (String)session.getAttribute("dateOfUpload");
	            System.out.println(date);
	            //stmt.execute("DELETE FROM FILE_INFO");    
	            
	            prepStmt.setString(1, fileName);
	            prepStmt.setString(2, faleSize);
	            prepStmt.setString(3, date);	     
	     
	            prepStmt.executeUpdate();
	     
         prepStmt.close();
         con.close();

  }catch(Exception e){
      e.printStackTrace();            
  }       	     	     
	     System.out.println(" you were here  0_o");		
		
		 String encodeURL = response.encodeURL("/lastUploadFile.jsp");
         request.getRequestDispatcher(encodeURL).forward(request, response);
	}

}
