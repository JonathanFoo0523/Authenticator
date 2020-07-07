package curlec.twoFA.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import curlec.twoFA.utils.*;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login doGet called");
		request.getRequestDispatcher("login.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login doPost called");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ArrayList<HashMap<String, String>> results = 
				DbHelper.pullQuery("SELECT * FROM user WHERE username = \"" + username + "\"");
		
		if (results.size() != 1) {
			request.setAttribute("alert", "Invalid username");
			request.setAttribute("alertType", 2);
			request.getRequestDispatcher("login.jsp").include(request,response);
			return;
		}
		
		Boolean passwordTrue = false;
		try {
			passwordTrue = PasswordHash.check(password,results.get(0).get("hash"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Check Password has failed.");
			e.printStackTrace();
		}
		
		if (!passwordTrue) {
			request.setAttribute("alert", "Incorrect Password");
			request.setAttribute("alertType", 2);
			request.getRequestDispatcher("login.jsp").include(request,response);
			return;
		}
		
		String id = results.get(0).get("id");
		String secretKey =  results.get(0).get("AuthSecret");
		if (secretKey == null) {
			request.getSession().setAttribute("id", results.get(0).get("id"));
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("access", true);
			request.getSession().setMaxInactiveInterval(600);
			response.sendRedirect("/Authenticator/index");
			return;
		} else {
			request.getSession().setAttribute("id", results.get(0).get("id"));
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("auth", true);
			request.getSession().setMaxInactiveInterval(180);
			System.out.println(request.getSession().getAttributeNames());
			response.sendRedirect("/Authenticator/authlogin");
			return;
		}
		
	}

}
