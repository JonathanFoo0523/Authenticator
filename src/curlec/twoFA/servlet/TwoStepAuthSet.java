package curlec.twoFA.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;

import com.google.zxing.WriterException;

//import com.google.zxing.WriterException;

import curlec.twoFA.utils.*;

/**
 * Servlet implementation class TwoStepAuthSet
 */
@WebServlet("/twoStepAuthSet")
public class TwoStepAuthSet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwoStepAuthSet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = (String) request.getSession().getAttribute("id");
		String secretKey =  
				DbHelper.pullQuery("SELECT * FROM user WHERE id = " + Integer.parseInt(id)).get(0).get("AuthSecret");
		System.out.println(secretKey);
		if (secretKey == null) {
			System.out.println("setUpGet will be called");
			setUpGet(request, response);
			return;
		} else {
			changeGet(request, response);
			System.out.println("changeGet will be called");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = (String) request.getSession().getAttribute("id");
		String secretKey =  
				DbHelper.pullQuery("SELECT * FROM user WHERE id = " + Integer.parseInt(id)).get(0).get("AuthSecret");
		System.out.println(secretKey);
		if (secretKey == null) {
			System.out.println("setUpPost will be called");
			setUpPost(request, response);
			return;
		} else {
			changePost(request, response);
			System.out.println("changePost will be called");
			return;
		}
	}
	
	private void setUpGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String secretKey = AuthHelper.generateSecretKey();
		String username = (String) request.getSession().getAttribute("username");
		request.getSession().setAttribute("secretKey", secretKey);
		
		request.setAttribute("secretKey", secretKey);
		request.setAttribute("email", username + "@authenticator.com");
		request.setAttribute("issuer", "Authenticatorwebsite");
		
		System.out.println(secretKey);
		request.getRequestDispatcher("twoStepAuthSet.jsp").forward(request, response);
	}
	
	private void changeGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("twoStepAuth.jsp").forward(request, response);
	}
	
	private void setUpPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String code = request.getParameter("code");
		String secretKey = (String) request.getSession().getAttribute("secretKey");
		System.out.println(code);
		String ActualCode = AuthHelper.getCode(secretKey);
		request.setAttribute("secretKey", secretKey);
		int id = Integer.parseInt((String) request.getSession().getAttribute("id"));
		String username = (String) request.getSession().getAttribute("username");
		
		if (code.equals(ActualCode)) {
			DbHelper.pushQuery("UPDATE user SET AuthSecret=\""+ secretKey + "\" WHERE id=" + id);
			request.setAttribute("alert", "2-step Authentication Set Up Successfully!");
			request.setAttribute("alertType", 1);
			System.out.println("PROBLEM");
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		} else {
			request.setAttribute("alert", "Incorrect Key");
			request.setAttribute("alertType", 2);
			request.setAttribute("secretKey", secretKey);
			request.setAttribute("email", username + "@authenticator.com");
			request.setAttribute("issuer", "Authenticatorwebsite");
			System.out.println("Incorrect Key");
			request.getRequestDispatcher("twoStepAuthSet.jsp").include(request,response);
		}
	}
	
	private void changePost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt((String) request.getSession().getAttribute("id"));
		String username = (String) request.getSession().getAttribute("username");
		if (request.getParameter("submit_button").equals("change_phone")) {
			DbHelper.pushQuery("UPDATE user SET AuthSecret=NULL WHERE id=" + id);
			request.setAttribute("alert", "Old record deleted. Set up on your new phone.");
			request.setAttribute("alertType", 0);
			
			setUpGet(request, response);
		} else {
			DbHelper.pushQuery("UPDATE user SET AuthSecret=NULL WHERE id=" + id);
			request.setAttribute("alert", "Record Deleted");
			request.setAttribute("alertType", 0);
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		}
	}
	
}
