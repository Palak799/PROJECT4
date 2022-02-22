package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.ctl.ORSView;
import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.RoleModel;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * login functionality controller. perform login operation
 * @author priyal
 *
 */
@WebServlet(name ="/LoginCtl", urlPatterns={"/LoginCtl"})
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID=1L;
	public static final String OP_REGISTER="Register";
	public static final String OP_SIGN_IN="SignIn";
	public static final String OP_SIGN_UP="SignUp";
	public static final String OP_LOG_OUT="logout";
	
	private static Logger log=Logger.getLogger(LoginCtl.class);
	
	
	protected boolean validate(HttpServletRequest request){
		
		System.out.println("validate method of Loginctl");
		log.debug("LoginCtl method validate started");
		boolean pass=true;
		
		String op=request.getParameter("operation");
		
		if(OP_SIGN_UP.equals(op)||OP_LOG_OUT.equals(op)){
			System.out.println("pass validate");
			return pass;
			
			
		}
		String login=request.getParameter("login");
		
	if(DataValidator.isNull(login)){
		request.setAttribute("login", PropertyReader.getValue("error.require","Login Id"));
		pass=false;
	}else if(!DataValidator.isEmail(login)){
		request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
        pass = false;
		
	}
	if(DataValidator.isNull(request.getParameter("password"))){
		request.setAttribute("password", PropertyReader.getValue("error.require","Password"));
		pass=false;
		
		}
	log.debug("LoginCtl Method validate Ended");
	return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		System.out.println("populatebean method of Loginctl");
		log.debug("LoginCtl Method populatebean started");
		
		UserBean bean=new UserBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		
		log.debug("LoginCtl Method popuplatebean Ended");
		return bean;
	}
	
	/**
	 * Display Login form
	 *
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		System.out.println("doGet method of Loginctl");
		HttpSession session=request.getSession(true);
		
		log.debug("Method doGet Started");
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserModel model=new UserModel();
		RoleModel role=new RoleModel();
		
	long id=DataUtility.getLong(request.getParameter("id"));
		
		if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();
			System.out.println("log out button");
			session.invalidate();
			ServletUtility.setSuccessMessage("LogOut Successfully", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;

		}

		
		if(id>0){
			UserBean userbean;
			try{
				userbean=model.findByPK(id);
				ServletUtility.setBean(userbean,request);
				
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doGet Ended");
	}
	
	/**
	 * Submitting or login action performing
	 *
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	
	System.out.println("doPost method of Loginctl");
	HttpSession session=request.getSession(true);
	log.debug("Method doPost Started");
	
	String op=DataUtility.getString(request.getParameter("operation"));
	
	UserModel model=new UserModel();
	RoleModel role=new RoleModel();

	//long id=DataUtility.getLong(request.getParameter("id"));
	
	if(OP_SIGN_IN.equalsIgnoreCase(op)){
		System.out.println("signin in dopost method of Loginctl");
		UserBean bean=(UserBean)populateBean(request);
		try{
			bean=model.authenticate(bean.getLogin(), bean.getPassword());
			
			if(bean!=null){
				System.out.println("bean!=null");
				session.setAttribute("user", bean);
				long rollId=bean.getRoleId();
				
				RoleBean rolebean=role.findByPK(rollId);
				if(rolebean!=null){
					System.out.println("rolebean !=null");
					session.setAttribute("role", rolebean.getName());
					
				}
				String uri = (String)request.getParameter("uri");
				
				System.out.println(">>>>>>>>>>>>>>>>>>>>>"+uri);
				
				if (uri== null || "null".equalsIgnoreCase(uri)) {
					System.out.println("uri==null");
					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;
				} else {
					System.out.println();
					if (rolebean.getId() == 1) 
					{
						System.out.println("rolebean.getId");
						ServletUtility.redirect(uri, request, response);
					} else {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					}


					return;
				}

				
			}else{
				System.out.println("else of dopost");
				bean=(UserBean)populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
			}
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		 }
		
			
		}else if(OP_SIGN_UP.equalsIgnoreCase(op)){
			System.out.println("signup of dopost");
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
			
		}
	
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Ended");
	}
			@Override
			protected String getView() {
				System.out.println("getView of loginctl");
			return ORSView.LOGIN_VIEW;
}
}


