package in.co.sunrays.ctl;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.RoleModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * role list functionality controller. to show list and search of role operation
 * @author PRIYAL
 *
 */
@ WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl{
private static Logger log=Logger.getLogger(RoleListCtl.class);

	
   	protected void preload(HttpServletRequest request) {
   		RoleModel model=new RoleModel();
   		try {
   			List list=model.list();
   			request.setAttribute("roleList", list);
   		} catch (Exception e) {
		// TODO: handle exception
	}
}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		RoleBean bean=new RoleBean();
		bean.setId(DataUtility.getLong(request.getParameter("name")));
//		bean.setName(DataUtility.getString(request.getParameter("name")));
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.debug("RoleListCtl doGet Start");
	List list=null;
	List next=null;
	int pageNo=1;
	int pageSize=DataUtility.getInt(PropertyReader.getValue("page size"));
	RoleBean bean=(RoleBean)populateBean(request);
	String op=DataUtility.getString(request.getParameter("operation"));
	RoleModel model=new RoleModel();
	
	try{
		list=model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo + 1, pageSize);
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		
		if(list==null||list.size()==0){
			ServletUtility.setErrorMessage("No Record Found", request);
		}if (next == null || next.size() == 0) {
			request.setAttribute("nextListSize", 0);

		} else {
			request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		
	}catch(ApplicationException e){
		log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	}
	log.debug("RoleListCtl doGet End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.debug("RoleListCtl doPost Start");
	List list=null;
	List next=null;
	int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
	int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
	
	pageNo=(pageNo==0)?1:pageNo;
	pageSize=(pageNo==0)?DataUtility.getInt(PropertyReader.getValue("pageSize")):pageSize;
	
	RoleBean bean=(RoleBean)populateBean(request);
	String op=DataUtility.getString(request.getParameter("operation"));
	String[] ids = request.getParameterValues("ids");
	RoleModel model=new RoleModel();
	
	try{
		if(OP_SEARCH.equalsIgnoreCase(op)||"Next".equalsIgnoreCase(op)||"Previous".equalsIgnoreCase(op)){
			
			if(OP_SEARCH.equalsIgnoreCase(op)){
				pageNo=1;
			}else if(OP_NEXT.equalsIgnoreCase(op)){
				pageNo++;
			}else if(OP_PREVIOUS.equalsIgnoreCase(op)&&pageNo>1){
				pageNo--;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		} else if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				RoleBean deletebean = new RoleBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					model.delete(deletebean);
					ServletUtility.setSuccessMessage("Data Delete Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		bean = (RoleBean) populateBean(request);
		list=model.search(bean, pageNo, pageSize);
		ServletUtility.setList(list, request);
		if(list==null||list.size()==0){
			ServletUtility.setErrorMessage("No record found", request);
		}if (next == null || next.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
			request.setAttribute("nextListSize", 0);

		} else {
			request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}catch(ApplicationException e){
		log.error(e);
		ServletUtility.handleException(e, request, response);
		return;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	log.debug("RoleListCtl doPost End");
	
	}

	@Override
	protected String getView() {
		
		return ORSView.ROLE_LIST_VIEW;
	}

}
