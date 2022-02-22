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
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.model.FacultyModel;
import in.co.sunrays.model.SubjectModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;

@WebServlet(name="FacultyCtl",urlPatterns={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl{
	 private static final long serialVersionUID=1L;
		
//		private static Logger log=Logger.getLogger(FacultyCtl.class);

			
		@Override
		protected void preload(HttpServletRequest request) {
			try {
			CollegeModel model = new CollegeModel();
			CourseModel model1 = new CourseModel();
			SubjectModel model2 = new SubjectModel();
			
				List l = model.list();
				List li = model1.list();
				List list = model2.list();
				request.setAttribute("collegeList", l);
				request.setAttribute("courseList", li);
				request.setAttribute("subjectList", list);
		     System.out.println("............"+l+".."+li+".."+list);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

			
			@Override
			protected boolean validate(HttpServletRequest request) {

			//	log.debug("FacultyCtl Method validate Started");
				
				boolean pass=true;
				String login = request.getParameter("login");
				
				if (DataValidator.isNull(request.getParameter("firstName"))) {
					request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
					pass = false;
					System.out.println("1"+pass);
				}else if (!DataValidator.isName(request.getParameter("firstName"))) {
					request.setAttribute("firstName", "please enter correct Name");
					pass = false;

				}
				if (DataValidator.isNull(request.getParameter("lastName"))) {
					request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
					pass = false;
					System.out.println("2"+pass);
				}else if (!DataValidator.isName(request.getParameter("lastName"))) {
					request.setAttribute("lastName", "please enter correct Name");
					pass = false;

				}
				if (DataValidator.isNull(request.getParameter("joiningDate"))) {
					request.setAttribute("joiningDate", PropertyReader.getValue("error.require", "Joining Date"));
					pass = false;
					System.out.println("3"+pass);
				}else if (!DataValidator.isDate(request.getParameter("joiningDate"))) {
					request.setAttribute("joiningDate", PropertyReader.getValue("error.date", "Joining Date"));
					pass = false;
				}
				if (DataValidator.isNull(request.getParameter("qualification"))) {
					request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
					pass = false;
					System.out.println("4"+pass);
				}
				if (DataValidator.isNull(request.getParameter("collegeId"))) {
					request.setAttribute("collegeId", PropertyReader.getValue("error.require", "college Name"));
					pass = false;
					System.out.println("5"+pass);
				}
				if (DataValidator.isNull(request.getParameter("courseId"))) {
					request.setAttribute("courseId", PropertyReader.getValue("error.require", "course Name"));
					pass = false;
					System.out.println("6"+pass);
				}
				if (DataValidator.isNull(request.getParameter("subjectId"))) {
					request.setAttribute("subjectId", PropertyReader.getValue("error.require", "subject Name"));
					pass = false;
					System.out.println("7"+pass);
				}
				if (DataValidator.isNull(login)) {
					request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
					pass = false;
					System.out.println("8"+pass);
				} else if (DataValidator.isEmail("login")) {
					request.setAttribute("login", PropertyReader.getValue("error.require", "Please enter vaild email id"));
					pass = false;
				}
				if (DataValidator.isNull(request.getParameter("gender"))) {
					request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
					pass = false;
					System.out.println("9"+pass);
				}
				if (DataValidator.isNull(request.getParameter("mobile"))) {
					request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile"));
					pass = false;
					System.out.println("10"+pass);
				}else if(!DataValidator.isPhoneNo(request.getParameter("mobile")))
				{
					  request.setAttribute("mobile", "Please Enter Valid Mobile Number");
					  pass=false;	
				    }


				if (DataValidator.isNull(request.getParameter("collegeId"))) {
					request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
					pass = false;
					System.out.println("11"+pass);
				}
				if (DataValidator.isNull(request.getParameter("courseId"))) {
					request.setAttribute("qualification", PropertyReader.getValue("error.require", "Course Name"));
					pass = false;
					System.out.println("12"+pass);
				}
				if (DataValidator.isNull(request.getParameter("subjectId"))) {
					request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
					pass = false;
					System.out.println("13"+pass);
				}

				
				
				System.out.println("1111111111111111111111111111111"+pass);
		//		log.debug("FacultyCtl Method validate Ended");
				return pass;
			
			}



			@Override
			protected BaseBean populateBean(HttpServletRequest request) {

			//	log.debug("FacultyCtl Method populatebean Started");
				 FacultyBean bean=new FacultyBean();
				
				 bean.setId(DataUtility.getLong(request.getParameter("id")));
				 bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
					bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
					bean.setGender(DataUtility.getString(request.getParameter("gender")));
					bean.setLoginId(DataUtility.getString(request.getParameter("login")));
					bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
					bean.setDateofjoining(DataUtility.getDate(request.getParameter("joiningDate")));
					bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
					bean.setCollegeid(DataUtility.getLong(request.getParameter("collegeId")));
					bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
					bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

				 populateDTO(bean,request);
			//	 log.debug("FacultyCtl Method populatebean Ended");
				 return bean;
			}


			/**
			 * Display Logics inside this method
			 */
			@Override
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		//		log.debug("Facultyctl do get Start");

				String op=DataUtility.getString(request.getParameter("operation"));
				
				FacultyModel model=new FacultyModel();
				
				long id=DataUtility.getLong(request.getParameter("id"));
				
				if(id>0||op!=null){
					FacultyBean bean;
					 try{
						 bean=model.findByPK(id);
						 ServletUtility.setBean(bean, request);
						 
					 }catch(ApplicationException e){
						 e.printStackTrace();
						// log.error(e);
						 ServletUtility.handleException(e, request,response);
						 return;
						 
					 }
				 }
				 ServletUtility.forward(getView(), request, response);
				// log.debug("Faculty ctl do get end");
			 }	
			
			/**
			 * Submit logic inside it
			 */
			@Override
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			//	log.debug("FacultyCtl Method doPost Started");
				 System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
				 String op=DataUtility.getString(request.getParameter("operation"));
				 
				 FacultyModel model=new FacultyModel();
				 long id=DataUtility.getLong(request.getParameter("id"));
				 if(OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)){
					 FacultyBean bean=(FacultyBean)populateBean(request);
					 
					 try{
						 if(id>0){
							 model.update(bean);
							 ServletUtility.setSuccessMessage("Data Successfully Update", request);
						 }else{
							 long pk;
							 try {
								 System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"+bean);
								 pk=model.add(bean);
								 System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnooo"+pk);
								ServletUtility.setSuccessMessage("Data Successfully saved", request);
								
							} catch (ApplicationException e) {
							//	log.error(e);
							//	ServletUtility.handleException(e, request, response);
								return;
							} catch (DuplicateRecordException e) {
								ServletUtility.setBean(bean, request);
								ServletUtility.setErrorMessage("Faculty  already exists", request);
							}					 
							 
						 }
						 
						 ServletUtility.setBean(bean, request);
						 
					 }catch(ApplicationException e){
						 e.printStackTrace();
					//	 log.error(e);
						 ServletUtility.handleException(e, request, response);
						 return;
					 }catch(DuplicateRecordException e){
						 ServletUtility.setBean(bean, request);
						 ServletUtility.getErrorMessage("Faculty Id already exists",request);
						 
					 } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }else if(OP_DELETE.equalsIgnoreCase(op)){
					 
					 FacultyBean bean=(FacultyBean)populateBean(request);
					 try{
						 model.delete(bean);
						 ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
						 return;
						 
					 }catch(ApplicationException e){
			//			 log.error(e);
						 ServletUtility.handleException(e, request, response);
						 return;
					 }
					 
				 }else if(OP_CANCEL.equalsIgnoreCase(op)){
					 
					 ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					 return;
				 }else if(OP_RESET.equalsIgnoreCase(op)){
						ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
						return;
						
					}
				 ServletUtility.forward(getView(), request, response);
				// log.debug("FacultyCtl Method doPost Ended");
			 }
			

			@Override
			protected String getView() {
				// TODO Auto-generated method stub
				return ORSView.FACULTY_VIEW;
			}

	}




