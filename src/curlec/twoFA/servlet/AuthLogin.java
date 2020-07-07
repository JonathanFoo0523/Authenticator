package curlec.twoFA.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import curlec.twoFA.utils.AuthHelper;
import curlec.twoFA.utils.DbHelper;

/**
 * Servlet implementation class AuthLogin
 */
@WebServlet("/authlogin")
public class AuthLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("AuthLogin doGet get called");
		if (request.getSession(false).getAttribute("auth") == null) {
			response.sendRedirect("/Authenticator/login");
			return;
		}
		System.out.println(request.getSession(false));
		request.setAttribute("username", request.getSession().getAttribute("username"));
		request.getRequestDispatcher("loginAuth.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("AuthLogin doPost get called");
		String id = (String) request.getSession().getAttribute("id");
		System.out.println(request.getSession().getAttributeNames());
		String secretKey =  
				DbHelper.pullQuery("SELECT * FROM user WHERE id = " + Integer.parseInt(id)).get(0).get("AuthSecret");
		String key = request.getParameter("code");
		
		if (key.equals(AuthHelper.getTOTPCode(secretKey))) {
			request.getSession().setAttribute("access", true);
			response.sendRedirect("/Authenticator/index");
			return;
		} else {
			request.setAttribute("alert", "Incorrect Key");
			request.setAttribute("alertType", 2);
			request.getRequestDispatcher("loginAuth.jsp").include(request,response); 
			return;
		}
	}

}
