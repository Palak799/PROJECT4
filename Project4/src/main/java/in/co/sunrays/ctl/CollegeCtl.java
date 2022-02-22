package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * college functionality ctl. To perform add,delete ,update operation
 * @author priyal
 * 
 */

@WebServlet("/ctl/CollegeCtl")
public class CollegeCtl extends BaseCtl {
	
	private static final long serialVersionUID=1L;
	
	private static Logger log=Logger.getLogger(CollegeCtl.class);
	
	protected boolean validate(HttpServletRequest request){
		
		log.debug("CollegeCtl Method validate Started");
		System.out.println("CollegeCtl Method validate Started");
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("name"))){
			request.setAttribute("name",PropertyReader.getValue("error.require","Name"));
			pass=false;
		}else if (!DataValidator.isValidName(request.getParameter("name"))) {
        	request.setAttribute("name",PropertyReader.getValue(" name should be in albhabet", "Invalid First"));
            pass = false;
		}
		if(DataValidator.isNull(request.getParameter("address"))){
			request.setAttribute("address",PropertyReader.getValue("error.require","Address"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("state"))){
			request.setAttribute("state",PropertyReader.getValue("error.require","State"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("city"))){
			request.setAttribute("city",PropertyReader.getValue("error.require","City"));
			pass=false;
			
		}
		if(DataValidator.isNull(request.getParameter("phoneNo"))){
			request.setAttribute("phoneNo",PropertyReader.getValue("error.require","Phone No"));
			pass=false;
			
		}else if(!DataValidator.isPhoneLength(request.getParameter("phoneNo"))){
			 request.setAttribute("phoneNo", "Please Enter Valid Mobile Number");
			 pass=false;	
		}else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Please Enter Valid Mobile Number");
			pass = false;
		}
		log.debug("CollegeCtl Method validate Ended");
		return pass;
	}
	 
	 protected BaseBean populateBean(HttpServletRequest request){
		 System.out.println("CollegeCtl Method populatebean Started");
		 log.debug("CollegeCtl Method populatebean Started");
		 CollegeBean bean=new CollegeBean();
		 
		 bean.setId(DataUtility.getLong(request.getParameter("id")));
		 bean.setName(DataUtility.getString(request.getParameter("name")));
		 bean.setAddress(DataUtility.getString(request.getParameter("address")));
		 bean.setState(DataUtility.getString(request.getParameter("state")));
		 
		 bean.setCity(DataUtility.getString(request.getParameter("city")));
		 bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		 
		 populateDTO(bean,request);
		 log.debug("CollegeCtl Method populatebean Ended");
		 return bean;
		 
	 }
	 
	 
	 /* *
	  * Display Logics inside this method
	  * 
	  */
	  
 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		 
		 log.debug("college ctl do get started");
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
		 CollegeModel model = new CollegeModel();
		 CollegeBean bean=new CollegeBean();
		 long id=DataUtility.getLong(request.getParameter("id"));
		 
		 if(id>0){
			 
			 try{
				 bean=model.findByPK(id);
				 ServletUtility.setBean(bean, request);
				 
			 }catch(ApplicationException e){
				 log.error(e);
				 ServletUtility.handleException(e, request,response);
				 return;
				 
			 }
		 }
		 
		 ServletUtility.forward(getView(), request, response);
		 log.debug("college ctl do get end");
	 }
	 
	 /**
	  * Submit logic inside it
	  */
	  
	
	 
 protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		 
		 log.debug("CollegeCtl Method doPost Started");
		 
		 String op=DataUtility.getString(request.getParameter("operation"));
		 CollegeBean bean1 =new CollegeBean();
		 CollegeModel model=new CollegeModel();
		 long id=DataUtility.getLong(request.getParameter("id"));
		 if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
			 
			 CollegeBean bean=(CollegeBean)populateBean(request);
			 
			 try{
				 if(id>0){
					 model.update(bean);
					 ServletUtility.setSuccessMessage("Data is successfully update", request);
			 }else{
				 long pk=model.add(bean);
//				 bean.setId(pk);
				 ServletUtility.setSuccessMessage("Data is successfully saved", request);
			 }
		ServletUtility.setBean(bean, request); //values present even after saving data
				 
				 
			 }catch(ApplicationException e){
				 e.printStackTrace();
				 log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(bean, request);
				 ServletUtility.getErrorMessage("College Name already exists",request);
				 
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else if(OP_CANCEL.equalsIgnoreCase(op)){
			 
			 ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			 return;
		 }
		 
		 ServletUtility.forward(getView(), request, response);
		 log.debug("CollegeCtl Method doPost End");
		 
	 }

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}
}