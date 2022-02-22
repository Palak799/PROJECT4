package in.co.sunrays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.model.SubjectModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * subject functionality controller.to perform add,delete and update operation.
 * @author priyal
 *
 */
@WebServlet(name ="SubjectClt" ,urlPatterns = "/ctl/SubjectCtl")

public class SubjectCtl extends BaseCtl{
private static final long serialVersionUID=1L;
	
	private static Logger log=Logger.getLogger(SubjectCtl.class);
	
	protected void preload(HttpServletRequest request) {
		CourseModel model = new CourseModel();
		try {
			List list = model.list();
			request.setAttribute("courseList", list);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	protected boolean validate(HttpServletRequest request){
		
		log.debug("SubjectCtl Method validate Started");
		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("courseId"))){
			request.setAttribute("courseId",PropertyReader.getValue("error.require","Course Name"));
			pass=false;
			
		}
		if(DataValidator.isNull(request.getParameter("courseName"))){
			request.setAttribute("courseName",PropertyReader.getValue("error.require","Course Name"));
			pass=false;
			
		}
		if(DataValidator.isNull(request.getParameter("subjectName"))){
			request.setAttribute("subjectName",PropertyReader.getValue("error.require","Subject Name"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("description"))){
			request.setAttribute("description",PropertyReader.getValue("error.require","Description"));
			pass=false;
		}
		
		
		log.debug("SubjectCtl Method validate Ended");
		return pass;
	}
	 
	 protected BaseBean populateBean(HttpServletRequest request){
		 
		 log.debug("SubjectCtl Method populatebean Started");
		 SubjectBean bean=new SubjectBean();
		 
		 bean.setId(DataUtility.getLong(request.getParameter("id")));
		 bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		 bean.setDescription(DataUtility.getString(request.getParameter("description")));
		 bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		 
		 bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
//		 bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		 
		 populateDTO(bean,request);
		 log.debug("SubjectCtl Method populatebean Ended");
		 return bean;
		 
	 }
	 /**
	 * Display Logics inside this method
	 */
	 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		 
		 log.debug("subjectctl do get started");
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
		 SubjectModel model = new SubjectModel();
		 
		 long id=DataUtility.getLong(request.getParameter("id"));
		 
		 if(id>0||op!=null){
			 SubjectBean bean;
			 try{
				 bean=model.findByPk(id);
				 ServletUtility.setBean(bean, request);
				 
			 }catch(ApplicationException e){
				 log.error(e);
				 e.printStackTrace();
				 ServletUtility.handleException(e, request,response);
				 return;
				 
			 }
		 }
		 ServletUtility.forward(getView(), request, response);
		 log.debug("subjectctl do get end");
	 }
	 
	 /**
	  * Submit logic inside it
	  */
	 protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		 
		 log.debug("subjectctl Method doPost Started");
		 
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
		 long id=DataUtility.getLong(request.getParameter("id"));
		 
		 SubjectModel model=new SubjectModel();
		 
		 if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
			 
			 SubjectBean bean=(SubjectBean)populateBean(request);
			 
			 try{
				 if(id>0){
					 model.update(bean);
					 ServletUtility.setSuccessMessage("Data is successfully update", request);
					 
				 }else{
					 long pk;
					  try{
					pk= model.add(bean);
//					 bean.setId(pk);
					 ServletUtility.setSuccessMessage("Data is successfully saved", request);
			 }catch(ApplicationException e){
				 e.printStackTrace();
				 log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(bean, request);
				 ServletUtility.setErrorMessage("Subject already exists",request);
			 }
					  
				 }
					 ServletUtility.setBean(bean, request);
				 
			 }catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("subject id already exists", request);
			}
		 }else if(OP_DELETE.equalsIgnoreCase(op)){
			 
			 SubjectBean bean=(SubjectBean)populateBean(request);
			 try{
				 model.delete(bean);
				 ServletUtility.redirect(getView(), request, response);
				 
				 
			 }catch(ApplicationException e){
				 log.error(e);
				 ServletUtility.handleException(e, request, response);
				 return;
			 }
		 }else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			 
		 }else if(OP_CANCEL.equalsIgnoreCase(op)){
			 
			 ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			 return;
		 }
		 ServletUtility.forward(getView(), request, response);
		
		
		 
		 log.debug("SubjectCtl Method doPost End");
	 }

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}


}

	
	
	
	
