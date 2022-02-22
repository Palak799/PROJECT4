package in.co.sunrays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.BaseBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.model.UserModel;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.DataValidator;
import in.co.sunrays.util.PropertyReader;
import in.co.sunrays.util.ServletUtility;
/**
 * change password operation functionality perform
 * @author priyal
 *
 */
@ WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {
    public static final String OP_CHANGE_MY_PROFILE = "Change Profile";
    private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

    
    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("ChangePasswordCtl Method validate Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        boolean pass = true;

        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            return pass;
        }
        if (DataValidator.isNull(request.getParameter("oldPassword"))) {
            request.setAttribute("oldPassword",PropertyReader.getValue("error.require", "Old Password"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword",PropertyReader.getValue("error.require", "New Password"));
            pass = false;
        }
        else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
        	request.setAttribute("newPassword", "Password should contain 8 letter with alphanumeric and special Character");
        	pass = false;
        }
        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }
        if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
           request.setAttribute("confirmPassword", PropertyReader.getValue("error.confirmpassword", "Password And Correct Password"));
            pass = false;
        }

        log.debug("ChangePasswordCtl Method validate Ended");
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("ChangePasswordCtl Method populatebean Started");

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        populateDTO(bean, request);
      log.debug("ChangePasswordCtl Method populatebean Ended");
        return bean;
    }

    /**
     * Contains Display logics
     */

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ServletUtility.forward(getView(), request, response);
    }
    /**
     * Contains Submit logics
     */

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        log.debug("ChangePasswordCtl Method doGet Started");
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();
        UserBean bean = (UserBean) populateBean(request); 
        UserBean UserBean = (UserBean) session.getAttribute("user");
        String newPassword = (String) request.getParameter("newPassword");
        long id = UserBean.getId();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            try {
                boolean flag = model.changePassword(id, bean.getPassword(),newPassword);
                if (flag == true) {
          //      	System.out.println("========>>>> find by login k ander");
                    bean = model.findByLogin(UserBean.getLogin());
                    session.setAttribute("user", bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
	                }
            } catch (ApplicationException e) {
            	e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;

            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage("Old Password is Invalid",request);
            }
        } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.debug("ChangePasswordCtl Method doGet Ended");
    }

    @Override
    protected String getView() {
        return ORSView.CHANGE_PASSWORD_VIEW;
    }

}
