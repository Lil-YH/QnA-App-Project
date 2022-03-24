// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/select")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SelectServlet extends HttpServlet {

	// The doGet() runs once per HTTP GET request to this servlet.
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
		// Set the MIME type for the response message
		response.setContentType("text/html");
    	// Get a output writer to write the response message into the network socket
    	PrintWriter out = response.getWriter();

    	// Print an HTML page as the output of the query
    	out.println("<!DOCTYPE html>");
    	out.println("<html>");
    	out.println("<head><title>Query Response</title></head>");
    	out.println("<body>");

    	try (
        	// Step 1: Allocate a database 'Connection' object
        	Connection conn = DriverManager.getConnection(
            	"jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            	"myuser", "xxxx");   // For MySQL
            	// The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

        // Step 2: Allocate a 'Statement' object in the Connection
        Statement stmt = conn.createStatement();
        ) {
        // Step 3 & 4 of the database servlet
      	// Assume that the URL is http://ip-addr:pot/clicker/select?choice=x
      	// Assume that the questionNo is 2
    		String choice = request.getParameter("choice");
    		String sqlStr = "INSERT INTO responses (questionNo, choice) VALUES (2, '" + choice + "')";

    		int count = stmt.executeUpdate(sqlStr); 	// run the SQL statement

            out.println("<h2>You have submitted your answer.</h2>");
    	} catch(Exception ex) {
        	out.println("<p>Error: " + ex.getMessage() + "</p>");
        	out.println("<p>Check Tomcat console for details.</p>");
        	ex.printStackTrace();
      	}  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
    	out.println("</body></html>");
    	out.close();
	}
}