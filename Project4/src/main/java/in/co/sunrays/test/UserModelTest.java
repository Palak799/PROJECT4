package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.model.FacultyModel;
import in.co.sunrays.model.UserModel;

public class UserModelTest {

	public static void main(String[] args) throws Exception {
	//	 testadd();
		 //testUpdate();
		 //testdelete();
		// testSearch();
		// testList();
		// FindByPk();
		//testAuthenticate();
	//	testresetPassword();
		testchangePassword(); 
		//FindByLogin();
	}

	public static void testadd() throws Exception {

		try {
			UserBean bean = new UserBean();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setFirstName("palak");
			bean.setLastName("palak");
			bean.setLogin("palak@gmail.com");
			bean.setPassword("123");
			bean.setMobileNo("966585587");
			bean.setDob(sdf.parse("1/07/1999"));
			bean.setRoleId(6);
			bean.setUnSuccessfulLogin(15);
			bean.setGender("male");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			long pk = UserModel.add(bean);
			UserBean addedbean = UserModel.findByPK(pk);
			if (addedbean != null) {
				System.out.println("test added fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		System.out.println("add");
	}
	public static void testchangePassword() throws Exception {

		try {
			UserBean bean =UserModel.findByLogin("palakkarodia@gmail.com");
			String oldPassword = bean.getPassword();
			bean.setId(1L);
			bean.setPassword("12345");
			bean.setNewpassword("Rays@123");
			 bean.setConfirmPassword("Rays@123");
			String newPassword = bean.getPassword();
			try {
				UserModel.changePassword(1L, oldPassword, newPassword);
				System.out.println("password has been change successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {

			UserModel model = new UserModel();
			UserBean bean = model.findByPK(4L);

			bean.setFirstName("lakshya");
			bean.setLastName("anita");
			bean.setLogin("anita@gmail.com");
			bean.setPassword("12345");
			bean.setDob(sdf.parse("31/01/1992"));
			bean.setMobileNo("6265777475");
			bean.setRoleId(5L);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("Male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setRegisteredIp("33A");
			bean.setLastLoginIp("LastSd");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			model.update(bean);

			UserBean updatedbean = UserModel.findByPK(4L);
			if (!"anita".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void FindByLogin() {
		try {
			UserBean bean = new UserBean();
			bean = UserModel.findByLogin("palak@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By Login fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
			System.out.println(bean.getRegisteredIp());
			System.out.println(bean.getLastLoginIp());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void FindByPk() throws Exception {
		try {
			UserBean bean = new UserBean();
			long pk = 0L;
			bean = UserModel.findByPK(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
			System.out.println(bean.getRegisteredIp());
			System.out.println(bean.getLastLoginIp());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testdelete() throws Exception {
		try {
			UserBean bean = new UserBean();
			bean.setId(60);
			long pk = 1L;

			UserModel model = new UserModel();

			model.delete(bean);
			FacultyBean deletedbean = FacultyModel.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		System.out.println("Test Delete success");

	}

	public static void testSearch() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
		//	bean.setFirstName("deepika");
			list = UserModel.search(bean, 1, 5);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				
				  System.out.println(bean.getId()); System.out.println(bean.getFirstName());
				  System.out.println(bean.getLastName()); System.out.println(bean.getLogin());
				  System.out.println(bean.getPassword()); System.out.println(bean.getDob());
				  System.out.println(bean.getMobileNo());
				 
				System.out.println(bean.getRoleId());
				
				
				/*
				 * System.out.println(bean.getUnSuccessfulLogin());
				 * System.out.println(bean.getGender());
				 * System.out.println(bean.getLastLogin()); System.out.println(bean.getLock());
				 * System.out.println(bean.getRegisteredIp());
				 * System.out.println(bean.getLastLoginIp());
				 * 
				 * System.out.println(bean.getCreatedBy());
				 * System.out.println(bean.getModifiedBy());
				 * System.out.println(bean.getCreatedDateTime());
				 * System.out.println(bean.getModifiedDateTime());
				 */
				 
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testList() throws Exception {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = UserModel.list(1, 1);
			if (list.size() > 0) {

				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (UserBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getFirstName());
					System.out.println(bean.getLastName());
					System.out.println(bean.getLogin());
					System.out.println(bean.getPassword());
					System.out.println(bean.getDob());
					System.out.println(bean.getMobileNo());
					System.out.println(bean.getRoleId());
					System.out.println(bean.getUnSuccessfulLogin());
					System.out.println(bean.getGender());
					System.out.println(bean.getLastLogin());
					System.out.println(bean.getLock());
					System.out.println(bean.getRegisteredIp());
					System.out.println(bean.getLastLoginIp());
					System.out.println(bean.getCreatedBy());
					System.out.println(bean.getModifiedBy());
					System.out.println(bean.getCreatedDateTime());
					System.out.println(bean.getModifiedDateTime());

				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	

	public static void testforgetPassword() throws ApplicationException {
		try {
			boolean b = UserModel.forgetPassword("vivekrathod6265@gmail.com");

			System.out.println("Sucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		//catch (ApplicationException e) {
//			e.printStackTrace();
		//}
	}
	public static void testAuthenticate() throws Exception
	{
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		bean = model.authenticate("palak@gmail.com", "12345");
		if(bean!=null)
		{
			System.out.println("done");
		}else
		{
			System.out.println("errr");
		}
	}

	public static void testresetPassword() throws Exception {
		try{
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		
			bean = model.findByLogin("palak@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}


}