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
    	out.println("<head><title>Response Submission</title></head>");
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
      	// Assume that the URL is http://ip-addr:pot/clicker/select?questionNo=8&choice=Z&username=admin
      	// Assume that the questionNo is 8
        // sqlStr = INSERT INTO responses VALUES (questionNo, 'choice', 'username')
    		String qNumber = request.getParameter("questionNo");
            String choice = request.getParameter("choice");
            String username = request.getParameter("username");
            String sqlStr = "INSERT INTO responses VALUES (" + qNumber + ", '" + choice + "', '" + username + "')";
            String sqlExistStr = "SELECT 1 FROM responses WHERE username = '" + username + "' AND questionNo = " + qNumber ;
            String sqlUpdateStr = "UPDATE responses SET choice = '" + choice + "' WHERE username = '" + username + "' AND questionNo = " + qNumber;

            ResultSet rset = stmt.executeQuery(sqlExistStr);

            int count;

            if (rset.next() == false) {
                count = stmt.executeUpdate(sqlStr);     // run the SQL statement
                out.println("<h2>You have submitted your answer.</h2>");
            } else {
                count = stmt.executeUpdate(sqlUpdateStr);
                out.println("<h2>You have updated your answer.</h2>");
            }

    	} catch(Exception ex) {
        	out.println("<p>Error: " + ex.getMessage() + "</p>");
        	out.println("<p>Check Tomcat console for details.</p>");
        	ex.printStackTrace();
      	}  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
    	out.println("</body></html>");
    	out.close();
	}
}