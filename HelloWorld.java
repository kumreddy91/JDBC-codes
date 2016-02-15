package company.helloworld.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Initializing results
		String top10results = "Failed to get results from JDBC";
		PrintWriter out = response.getWriter();
		//get results from selectRows object
		try {
			//if success replace top10results with query data
			top10results = new selectRows().queryEndUserCountry();
		} catch (SQLException e) {
			System.out.println("Error in query");
			top10results = "Communication error at Database";
		}catch (NullPointerException e) {
			System.out.println("Null pointer exception");}
		//print results on web bage
		out.println("<html><body><h1 align = 'center'>Bookings Analysis</h1>"
				+"<br><br>"+top10results+
				"<br></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
