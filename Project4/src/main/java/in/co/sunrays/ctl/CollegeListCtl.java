package in.co.sunrays.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 *college list ctl.to perform search and show list operation

 * @author priyal
 *
 */
@WebServlet(name="CollegeListCtl",urlPatterns={"/ctl/CollegeListCtl"})
public class CollegeListCtl extends BaseCtl {
	private static Logger log=Logger.getLogger(CollegeListCtl.class);
	
	
	protected void preload(HttpServletRequest request){
		CollegeModel model=new CollegeModel();
		try {
			List list=model.list();
			request.setAttribute("collegeList", list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		log.debug("college list populate bean start");
		CollegeBean bean=new CollegeBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("name")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		log.debug("college list populate bean end");
		
		System.out.println("college list populate Bean" + bean);
		populateDTO(bean, request);
		
		return bean;
	}
	
	
	/**
	 * Display Logics inside this method
	 */
	
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		System.out.println("college list do get start");
		log.debug("college list do get start");
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CollegeBean bean=(CollegeBean)populateBean(request);
		CollegeModel model=new CollegeModel();
		List list;
		List next;
		
		try{
			list=model.search(bean,pageNo,pageSize);
			next=model.search(bean, pageNo + 1, pageSize);
			
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

			
		}catch(ApplicationException e){
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("college list do get end");
	}
	
	/**
	 * Submit logic inside it
	 */
	 
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
			
			log.debug("CollegeListCtl doPost Start");
			List list=null;
//			List next=null;
			
			int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
			pageNo=(pageNo==0)?1:pageNo;
			
			pageSize=(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
			
			CollegeBean bean=(CollegeBean)populateBean(request);
			String op=DataUtility.getString(request.getParameter("operation"));
			CollegeModel model=new CollegeModel();
			String[] ids = request.getParameterValues("ids");
			
			try{
				if(OP_SEARCH.equalsIgnoreCase(op)||OP_NEXT.equalsIgnoreCase(op)||OP_PREVIOUS.equalsIgnoreCase(op)){
					
					if(OP_SEARCH.equalsIgnoreCase(op)){
						pageNo=1;
					}else if(OP_NEXT.equalsIgnoreCase(op)){
						pageNo++;
					}else if(OP_PREVIOUS.equalsIgnoreCase(op)&&pageNo>1){
						pageNo--;
					}
				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
					return;
				} else if (OP_RESET.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				}else if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						CollegeBean deletebean = new CollegeBean();
						for (String id : ids) {
							deletebean.setId(DataUtility.getInt(id));
							model.delete(deletebean);
							ServletUtility.setSuccessMessage("Data Delete Successfully", request);
						}
					} else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}
				}

					list=model.search(bean, pageNo, pageSize);
					ServletUtility.setBean(bean,request);
					
					List next = model.search(bean, pageNo + 1, pageSize);
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
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}catch(Exception e){
					
				}
			
					
					
			
				log.debug("CollegeListCtl doGet End");
			}
		


		@Override
		protected String getView() {
			// TODO Auto-generated method stub
			return ORSView.COLLEGE_LIST_VIEW;
		}
}