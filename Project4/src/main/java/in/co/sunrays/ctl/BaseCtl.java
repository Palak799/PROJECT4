package in.co.sunrays.ctl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.ServletUtility;
/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 * @author priyal
 *
 */
public abstract class BaseCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_RESET = "Reset";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CHANGE_MY_PROFILE = "MyProfile";

public static final String MSG_SUCCESS="success";
	
	/**
	 * Error message key constant
	 */
	public static final String MSG_ERROR="Error";
	
	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request){
	
		System.out.println("validate method in BaseCtl");
	return true;
	}
	
	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected void preload(HttpServletRequest request){
		
		System.out.println("preload method in BaseCtl");
		
	}
	
	/**
	 * Populates bean object from request parameters
	 *
	 * @param request
	 * @return
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		System.out.println("-------populate method in BaseCtl----");
		return null;
	}
	
	/**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	
	protected BaseBean populateDTO(BaseBean dto,HttpServletRequest request){
	
		System.out.println("populateDTO method in BaseCtl");
		String createdBy=request.getParameter("createdBy");
		String modifiedBy=null;
		
		UserBean userbean=(UserBean)request.getSession().getAttribute("user");
		
		if(userbean==null){
			createdBy="root";
			modifiedBy="root";
			
		}else{
			modifiedBy=userbean.getLogin();
			if("null".equalsIgnoreCase(createdBy)||DataValidator.isNull(createdBy)){
				createdBy=modifiedBy;
			}
		}
			dto.setCreatedBy(createdBy);
			dto.setModifiedBy(modifiedBy);
			
			long cdt=DataUtility.getLong(request.getParameter("createdDateTime"));
			
			if(cdt>0){
				dto.setCreatedDateTime(DataUtility.getTimestamp(cdt));
		  }else{
			dto.setCreatedDateTime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDateTime(DataUtility.getCurrentTimestamp());
	    return dto;
	}
	protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		System.out.println("service method in baseCtl");
		preload(request);    // Load the preloaded data required to display at HTML form
		
		String op=DataUtility.getString(request.getParameter("operation"));
		System.out.println(op);
		
		
		/*Check if operation is not DELETE, VIEW, CANCEL,RESET and NULL then
			 perform input data validation*/

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op)&& !OP_RESET.equalsIgnoreCase(op)){
				
    
			//	 Check validation, If fail then send back to page with error messages
			
		
			if(!validate(request)){
				System.out.println("if !validate of basectl");
					BaseBean bean=(BaseBean)populateBean(request);
					ServletUtility.setBean(bean,request);
					ServletUtility.forward(getView(),request,response);
					return;
				}
		}
			super.service(request, response);
	}
	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return
	 */
	protected abstract String getView();
}
