package curlec.twoFA.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import curlec.twoFA.utils.AuthHelper;

/**
 * Servlet implementation class DisplayQRCode
 */
@WebServlet("/displayqrcode")
public class DisplayQRCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayQRCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("image/png");
		ServletOutputStream out = response.getOutputStream();
		
		String secretKey = request.getParameter("secretKey");
		String email = request.getParameter("email");
		String issuer = request.getParameter("issuer");
		
		String barCodeData = AuthHelper.getGoogleAuthenticatorBarCode(secretKey, email, issuer);

		BitMatrix matrix = null;
		try {
			matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
			        150, 150);
		} catch (WriterException e) {
			out.close();
			e.printStackTrace();
		}
		
		MatrixToImageWriter.writeToStream(matrix, "png", out);
		
		
	}

}
