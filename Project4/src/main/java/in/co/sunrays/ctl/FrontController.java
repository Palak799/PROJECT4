package in.co.sunrays.ctl;

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
import javax.servlet.http.HttpSession;

import in.co.sunrays.util.ServletUtility;
/**
 * Front Functionality ctl. to perform session management operation
 * @author priyal
 *
 */
@WebFilter(urlPatterns={"/ctl/*","/doc/*"})
public class FrontController implements Filter {
	
	public void destroy(){
		
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter() of FrontController");
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		HttpSession session=request.getSession(true);
		
		String uri=request.getRequestURI();
		
		
		request.setAttribute("uri",uri);
		
		
		System.out.println("+++++++++++++++++++++++++++++++ FrontController"+uri);
		
		if(session.getAttribute("user")==null){
			request.setAttribute("msg", "Your session has been expired. Please re-Login. ");
			ServletUtility.forward(ORSView.LOGIN_VIEW,request,response);
			System.out.println("1234567898765444444444444444444444 frontcontroller");
		}else{
			chain.doFilter(req, resp);
		}
		
	}
	
	public void init(FilterConfig conf){
		
	}

	

}


