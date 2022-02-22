package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * forget password ctl.To perform password send in email
 * @author priyal
 *
 */


@WebServlet(name="ForgetPasswordCtl",urlPatterns={"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl{
public static Logger log=Logger.getLogger(ForgetPasswordCtl.class);
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method Validate Started");
		
		boolean pass=true;
		String login=request.getParameter("login");
		
		if(DataValidator.isNull(login)){
			request.setAttribute("login",PropertyReader.getValue("error.require","Email Id"));
			pass=false;
		}else if(!DataValidator.isEmail(login)){
			request.setAttribute("login",PropertyReader.getValue("error.email","login"));
			pass=false;
		}
		log.debug("ForgetPasswordCtl Method validate Ended");
		
		
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl method populatedBean Started");
		
		UserBean bean=new UserBean();
		
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		
		log.debug("ForgetPasswordCtl method populate bean Ended");
		
		return  bean;
	}

	/**
	 * Display Concept are there
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("ForgetPasswordCtl method doGet Started");
		
		ServletUtility.forward(getView(), request, response);
	}
	
	
	/**
	 * Submit Concepts
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("ForgetPasswordCtl method doPost Started");
	
		String op=DataUtility.getString(request.getParameter("operation"));
		
		UserBean bean=(UserBean)populateBean(request);
		
		UserModel model=new UserModel();
		
		
		if(OP_GO.equalsIgnoreCase(op)){
			
			try{
				model.forgetPassword(bean.getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your EmailId", request);
				
			}catch(RecordNotFoundException e){
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
		}
		log.debug("ForgetPasswordCtl Method doPost End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

	
}


	

