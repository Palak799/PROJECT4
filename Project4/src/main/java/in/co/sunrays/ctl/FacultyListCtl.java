package in.co.sunrays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.model.FacultyModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

/**
 * faculty list functionality ctl.To perform show,search and delete operation
 * 
 * @author priyal
 *
 */

@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(FacultyListCtl.class);

	protected void preload(HttpServletRequest request) {
		CollegeModel model = new CollegeModel();
		CourseModel model1 = new CourseModel();
		try {
			List list = model.list();
			List list1 = model1.list();
			request.setAttribute("collegeList", list);
			request.setAttribute("courseList", list1);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("Faculty Ctl populateBean start");
		log.debug("Faculty Ctl populateBean start");
		FacultyBean bean = new FacultyBean();
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLoginId(DataUtility.getString(request.getParameter("login")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setCollegeid(DataUtility.getLong(request.getParameter("collegeId")));
		populateDTO(bean, request);
		log.debug("Faculty Ctl populateBean end");
		return bean;

	}

	/**
	 * contain display logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do get start");
		System.out.println("Faculty Ctl do get start");
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
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
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Faculty Ctl do get end");
	}

	/**
	 * Contains submit logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do post start");
		List list;
		List next;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String op = DataUtility.getString(request.getParameter("operation"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				System.out.println("search");
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					System.out.println("search......");
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				System.out.println("kiljjj");
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("hi" + ids);
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyBean deleteBean = new FacultyBean();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}
			}
			bean = (FacultyBean) populateBean(request);
			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setBean(bean, request);
			next = model.search(bean, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("NO Record Found", request);

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

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("Faculty Ctl do post end");
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}

}
