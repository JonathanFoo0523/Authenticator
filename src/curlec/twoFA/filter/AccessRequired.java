package curlec.twoFA.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LoginRequired
 */
@WebFilter(urlPatterns = {"/twoStepAuthSet", "/displayqrcode", "/index"})
public class AccessRequired implements Filter {

    /**
     * Default constructor. 
     */
    public AccessRequired() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("AccessRequired filter get called.");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getSession(false) == null) {
			System.out.println("Session not found.");
			res.sendRedirect("/Authenticator/login");
			return;
		}
		Boolean accessGranded = (Boolean) req.getSession(false).getAttribute("access");
		if (accessGranded == null) {
			System.out.println("Access not granded.");
			res.sendRedirect("/Authenticator/login");
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("AccessRequired filter init");
	}

}
