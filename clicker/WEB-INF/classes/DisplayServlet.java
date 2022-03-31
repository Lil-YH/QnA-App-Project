import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/display")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class DisplayServlet extends HttpServlet {

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
    	out.println("<head><title>Display Response Data</title><link rel = 'stylesheet' href = 'tablestyle.css'></head>");
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
      	    // Assume that the URL is http://ip-addr:pot/clicker/display
      	    // Display responses in bar chart
    		String sqlStr = "SELECT choice, COUNT(*) as count FROM responses WHERE questionNo=2 GROUP BY choice ORDER BY choice ASC";

    		ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

            out.println("<table class = 'graph'>"
                        + "<thead>"
                        + "<tr>"
                        + "<th>Choice</th>"
                        + "<th>Count</th>"
                        + "</tr>"
                        + "</thead>");
            // Process the result set
            int count = 0;
            while(rset.next()) {
                out.println("<tr>"
                            + "<th scope='row'>" + rset.getString("choice") + "</td>"
                            + "<td><span>" + rset.getString("COUNT") + "</span></td>"
                            + "</tr>");
            } 
            out.println("</table>");

    	} catch(Exception ex) {
        	out.println("<p>Error: " + ex.getMessage() + "</p>");
        	out.println("<p>Check Tomcat console for details.</p>");
        	ex.printStackTrace();
      	}  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
    	out.println("</body></html>");
    	out.close();
	}
}