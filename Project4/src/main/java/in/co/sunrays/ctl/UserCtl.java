package in.co.sunrays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.model.RoleModel;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * user functionality controller.to perform add,delete and update operation
 * @author priyal
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();

		try {
			List l = model.list();
			request.setAttribute("roleList", l);
			System.out.println("ole data" + l.size()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    /* (non-Javadoc)
     * @see in.co.sunrays.ctl.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

    //    log.debug("UserCtl Method validate Started");
        boolean pass = true;

        long id = DataUtility.getInt(request.getParameter("id"));
        
        String login = request.getParameter("login");
        String dob = request.getParameter("dob");

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }else if (!DataValidator.isValidName(request.getParameter("firstName"))) {
        	request.setAttribute("firstName",PropertyReader.getValue("First name should be in albhabet", "Invalid First"));
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }else if (!DataValidator.isValidName(request.getParameter("lastName"))) {
            request.setAttribute("lastName",PropertyReader.getValue("Last name should be in alphabet", "Invalid Last"));
            pass = false;
        }
        if (DataValidator.isNull(login)) {
            request.setAttribute("login",PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        } else if (!DataValidator.isEmail(login)) {
            request.setAttribute("login",PropertyReader.getValue("error.email", "Invalid "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password",PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }else if (!DataValidator.isPassword(request.getParameter("password"))) {
        	request.setAttribute("password","Password should contain 8 letter with alpha-numeric and special Character"
);
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require","Mobile No"));
			pass = false ;
        }else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
        	request.setAttribute("mobile","Mobile No. must be 10 Digit and No. Series start with 6-9"
);
			pass = false ;
		}
        
        
        if (DataValidator.isNull(request.getParameter("roleList"))) {
			request.setAttribute("rolename", PropertyReader.getValue("error.require"," Role ID"));
			pass = false ;
        }

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender",PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(dob)) {
            request.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isvalidateAge(dob)) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Maximum Age 18 year"));
            pass = false;
        }
        if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
        	request.setAttribute("confirmPassword", PropertyReader.getValue("Password and confirm should be Same", "Password and Correct Password"));
             pass = false;
        }
       // log.debug("UserCtl Method validate Ended");
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

     //   log.debug("UserCtl Method populatebean Started");
        UserBean bean = new UserBean();
        long id = DataUtility.getInt(request.getParameter("id"));
        
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));
        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
        
        bean.setRoleId(DataUtility.getLong(request.getParameter("roleList")));
        
        populateDTO(bean, request);
      //  log.debug("UserCtl Method populatebean Ended");
        return bean;
    }
    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
      //  log.debug("UserCtl Method doGet Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        
        UserModel model = new UserModel();
        
        if (id > 0 || op != null) {
      //      System.out.println("in id > 0  condition");
            UserBean bean;
            try {
                bean = UserModel.findByPK(id)
;
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
          //      log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
  //      log.debug("UserCtl Method doGet Ended");
    }

    /**
     * Contains Submit logics
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
//        log.debug("UserCtl Method doPost Started");
       
        String op = DataUtility.getString(request.getParameter("operation"));
          long id = DataUtility.getLong(request.getParameter("id"));
        
        UserModel model = new UserModel();
   
        if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
        	UserBean bean = (UserBean) populateBean(request);

            try {
                if (id > 0) {
                    UserModel.update(bean);
                } else {
                    long pk = UserModel.add(bean);
              //    bean.setId(pk);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("User is successfully saved",request);
            } catch (ApplicationException e) {
            //    log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists",request);
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        	else if ( OP_RESET.equalsIgnoreCase(op) ) {
            ServletUtility.redirect(ORSView.USER_CTL, request, response);
            return;
        }
        	else if (OP_CANCEL.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;
            }
      
        ServletUtility.forward(getView(), request, response);
        //log.debug("UserCtl Method doPostEnded");
    }

    @Override
    protected String getView() {
        return ORSView.USER_VIEW;
    }

}


