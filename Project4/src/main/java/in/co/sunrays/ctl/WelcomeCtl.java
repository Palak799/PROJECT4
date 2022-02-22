package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.util.ServletUtility;
@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);

	}
    protected String getView() {
        return ORSView.WELCOME_VIEW;
    }
}
