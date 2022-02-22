package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * course functionality ctl.to perform add,delete ,update operation
 * @author priyal
 *
 */
@WebServlet(name="CourseCtl",urlPatterns={"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{
	@Override
	protected boolean validate(HttpServletRequest request) {

		//log.debug("CourseCtl Method validate Started");
		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("courseName"))){
			request.setAttribute("courseName",PropertyReader.getValue("error.require","Course name"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("description"))){
			request.setAttribute("description",PropertyReader.getValue("error.require","Description"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("duration"))){
			request.setAttribute("duration",PropertyReader.getValue("error.require","Duration"));
			pass=false;
		}
		//log.debug("CourseCtl Method validate Ended");
		return pass;
	
	}



	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

	//	log.debug("CourseCtl Method populatebean Started");
		 CourseBean bean=new CourseBean();
		
		 bean.setId(DataUtility.getLong(request.getParameter("id")));
		 bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		 bean.setDescription(DataUtility.getString(request.getParameter("description")));
		 bean.setDuration(DataUtility.getString(request.getParameter("duration")));

		 populateDTO(bean,request);
		// log.debug("CourseCtl Method populatebean Ended");
		 return bean;
	}



   /**
	* Display Logics inside this method
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op=DataUtility.getString(request.getParameter("operation"));
		
		CourseModel model=new CourseModel();
		
		long id=DataUtility.getLong(request.getParameter("id"));
		
		if(id>0||op!=null){
			 CourseBean bean;
			 try{
				 bean=model.findByPK(id);
				 ServletUtility.setBean(bean, request);
				 
			 }catch(ApplicationException e){
				 e.printStackTrace();
			//	 log.error(e);
				 ServletUtility.handleException(e, request,response);
				 return;
				 
			 }
		 }
		 ServletUtility.forward(getView(), request, response);
	//	 log.debug("course ctl do get end");
	 }	
	
	
	/**
     * Submit logic inside it
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//log.debug("CourseCtl Method doPost Started");
		 
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
		 CourseModel model=new CourseModel();
		 long id=DataUtility.getLong(request.getParameter("id"));
		 if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
			 CourseBean bean=(CourseBean)populateBean(request);
			 
			 try{
				 if(id>0){
					 model.update(bean);
					 bean.setId(id);
					 ServletUtility.setSuccessMessage("Data Successfully Update", request);
					 ServletUtility.setBean(bean, request);
				 }else{
					 
					 try {
						 model.add(bean);
						ServletUtility.setSuccessMessage("Data Successfully saved", request);
						ServletUtility.setBean(bean, request);
					} catch (ApplicationException e) {
					//	log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setBean(bean, request);
						ServletUtility.setErrorMessage("course  already exists", request);
					}					 
					 
				 }
				 
				 
			 }catch(ApplicationException e){
				 e.printStackTrace();
				// log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(bean, request);
				 ServletUtility.getErrorMessage("Login Id already exists",request);
				 
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else if(OP_DELETE.equalsIgnoreCase(op)){
			 
			 CourseBean bean=(CourseBean)populateBean(request);
			 try{
				 model.delete(bean);
				 ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				 return;
				 
			 }catch(ApplicationException e){
		//		 log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }
			 
		 }else if(OP_CANCEL.equalsIgnoreCase(op)){
			 
			 ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			 return;
		 }else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
				
			}
		 ServletUtility.forward(getView(), request, response);
		// log.debug("CourseCtl Method doPost Ended");
	 }



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}
}
