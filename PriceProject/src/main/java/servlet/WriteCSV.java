package servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class WriteCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;        
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

			request.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8"); 		
			HttpSession session = request.getSession(); 
			
			Statement st = null; 
			Connection cn = null; 
			PreparedStatement prst = null; 

			final String url = "jdbc:mysql://localhost/PC_TRADE"; 
			final String user = "root"; 
			final String password = "root"; 

				try { 
					Class.forName("com.mysql.jdbc.Driver"); 

					System.out.println("Driver Found"); 
				} catch (ClassNotFoundException e) { 

					System.out.println("Driver not Found:" + e); 
				} 

				try { 
					cn = (Connection) DriverManager.getConnection(url, user, password); 
					System.out.println("create statement * * *"); 
					st = (Statement) cn.createStatement(); 
					System.out.println("Connection Succesefully"); 
					String sql; 
					sql = "INSERT INTO PRICE_LIST" + "(ARTICLE_CODE, ARTICLE, PRICE, DATA) VALUES" + "(?,?,?,?)"; 
					prst = cn.prepareStatement(sql); // ! наш подгот запрос 
					//ResultSet rs = st.executeQuery("SELECT * FROM PRICE_LIST"); 
			
					prst.execute("DELETE FROM PRICE_LIST");

				} catch (SQLException e) { 
					System.out.println("Connection Failed:" + e); 
				} 					

			int count = 1; 
			int incorr = 0;
			ArrayList<String> exList = new ArrayList<String>(); 
			 
			try{ 
			//BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Monika\\Desktop\\priceSampleCSV.csv"), "UTF-8")); 
			
			BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream(getServletContext().getInitParameter("file-upload") + session.getAttribute("lastFileNameUpload")), "utf-8")); 
				for(String line; (line = fileOut.readLine()) != null; ){ 
			
					System.out.println(count + ": " + line); 
					count++; 
					String[] arr = line.split(";"); 
//				for(int i = 0; i < arr.length; i++){
//					if(arr[i].isEmpty()&&(i==0||i==2)){
//						arr[i] = "0";}
//					}else if(arr[i].isEmpty()){
//						arr[i] = " ";
//					}
//				}					
					if (arr.length < 3) {
						incorr++;						
					}
			System.out.println(arr.length);
					String date = (String)session.getAttribute("dateOfUpload");
					prst.setString(1,arr[0]); 
					prst.setString(2, arr[1]); 
					prst.setString(3, arr[2]); 
					prst.setString(4, date);			
			
					prst.execute(); 

					exList.add(line); 
					
					
					} 
				session.setAttribute("incorr", incorr);
				fileOut.close(); 
				st.close();
				prst.close();				
				cn.close();
				}catch(Exception ex) {				 
					System.out.println(ex); 					
					session.setAttribute("ex", ex.getClass());
				}

			session.setAttribute("fileList", exList); 
			String encodingURL = response.encodeRedirectURL("/csvReader.jsp"); 
			request.getRequestDispatcher(encodingURL).forward(request, response); 
		}
}
