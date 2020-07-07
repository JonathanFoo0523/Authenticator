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
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("register.jsp").include(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("confirmation");
		
		ArrayList<HashMap<String, String>> results = 
				DbHelper.pullQuery("SELECT * FROM user WHERE username = \"" + username + "\"");
		if (!results.isEmpty()) {
			request.setAttribute("alert", "Username Already Taken");
			request.setAttribute("alertType", 2);
			request.getRequestDispatcher("register.jsp").include(request,response); 
			return;
		}
		if (!password.equals(confirmation)) {
			request.setAttribute("alert", "Password and Confirmation Don't Match");
			request.setAttribute("alertType", 2);
			request.getRequestDispatcher("register.jsp").include(request,response);
			return;
		}
		
		String hash = password;
		try {
			hash = PasswordHash.getSaltedHash(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Probelm generating hash");
		}
		
		DbHelper.pushQuery(String.format("INSERT INTO user (username, hash) VALUES('%s', '%s')", username, hash));
		ArrayList<HashMap<String, String>> newResults = 
				DbHelper.pullQuery("SELECT * FROM user WHERE username = \"" + username + "\"");
		String userid = newResults.get(0).get("id");
		System.out.println(userid + " registered");
		
		request.getSession().setAttribute("id", userid);
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("access", true);
		request.getSession().setMaxInactiveInterval(600);
		
		response.sendRedirect("/Authenticator/index");
	}

}
