package in.co.sunrays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * course list functionality ctl.Toperform search and delete,show list operation
 * 
 * @author priyal
 *
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {


	@Override
	protected void preload(HttpServletRequest request) {
	// TODO Auto-generated method stub
			CourseModel model=new CourseModel();
			try {
				List list=model.list();
				request.setAttribute("courseList", list);
			} catch (Exception e) {
			//	log.error(e);
			}
		}
	




	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

//	log.debug("course list populate bean start");
	CourseBean bean=new CourseBean();
	
	bean.setId(DataUtility.getLong(request.getParameter("courseId")));
//	bean.setCourseName(DataUtility.getString(request.getParameter("courseId")));
	bean.setDescription(DataUtility.getString(request.getParameter("description")));
	bean.setDuration(DataUtility.getString(request.getParameter("duration")));
	
	//log.debug("course list populate bean end");
	populateDTO(bean, request);
	return bean;

	}




	/**
	 * Display Logics inside this method
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//log.debug("course list do get start");
	int pageNo=1;
	int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
	
	CourseBean bean=(CourseBean) populateBean(request);
	CourseModel model=new CourseModel();
	
	List list=null;
	List next=null;
	try{
		list=model.search(bean,pageNo,pageSize);
		next=model.search(bean, pageNo+1, pageSize);
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		if (next == null || next.size() == 0) {
			request.setAttribute("nextListSize", "0");
		} else {
			request.setAttribute("nextListSize", next.size());
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

		
	}catch(ApplicationException e){
	//	log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	}catch(Exception e){
		
	}
	//log.debug("course list doget end");


}


	/**
	 * Submit logic inside it
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//log.debug("CourseListCtl doPost Start");
	List list=null;
	List next=null;
	int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
	int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
	pageNo=(pageNo==0)?1:pageNo;
	
	pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page size")):pageSize;
	
	CourseBean bean=(CourseBean)populateBean(request);
	String op=DataUtility.getString(request.getParameter("operation"));
	CourseModel model=new CourseModel();
	String[] ids = request.getParameterValues("ids");
	
	try{
		if(OP_SEARCH.equalsIgnoreCase(op)||OP_NEXT.equalsIgnoreCase(op)||OP_PREVIOUS.equalsIgnoreCase(op)){
			System.out.println("CourseSearch working");
			if(OP_SEARCH.equalsIgnoreCase(op)){
				pageNo=1;
				System.out.println("CourseSearch working....");
			}else if(OP_NEXT.equalsIgnoreCase(op)){
				pageNo++;
			}else if(OP_PREVIOUS.equalsIgnoreCase(op)&&pageNo>1){
				pageNo--;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}else if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				CourseBean deletebean = new CourseBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					model.delete(deletebean);
					ServletUtility.setSuccessMessage("Data Delete Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
			System.out.println(bean.getId()+"----------------------------"+bean.getDuration());
			
			bean = (CourseBean) populateBean(request);
			list=model.search(bean, pageNo, pageSize);
			ServletUtility.setList(list,request);
			next = model.search(bean, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if(list==null||list.size()==0&&!OP_DELETE.equalsIgnoreCase(op)){
				ServletUtility.setErrorMessage("No record Found", request);
				
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
			} else {
				request.setAttribute("nextListSize", next.size());
			}


			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		
		}catch(ApplicationException e){
	//		log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}catch(Exception e){
			e.printStackTrace();
		}
	
	//	log.debug("CourseListCtl doGet End");


}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_LIST_VIEW;
	}
	
	

}
