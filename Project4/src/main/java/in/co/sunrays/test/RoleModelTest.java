package in.co.sunrays.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.omg.CORBA.portable.ApplicationException;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.FacultyModel;
import in.co.sunrays.model.RoleModel;

public class RoleModelTest {
	public static void main(String[] args) throws Exception {
		 testadd();
		//testUpdate();
		// testdelete();
		// testSearch();
		 //testList();
		// FindByPk();
		// FindByName();
		
		
	}

	public static void testadd() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			bean.setName("ADMIN");
			bean.setDescription("ADMIN");
			bean.setCreatedBy("ADMIN");
			bean.setModifiedBy("ADMIN");
			bean.setCreatedDateTime(new Timestamp(new Date(0).getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date(0).getTime()));
			RoleModel model = new RoleModel();

			long pk = model.add(bean);
			RoleBean addedbean = RoleModel.findByPK(pk);
			if (addedbean == null) {
				System.out.println("test added fail");
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		System.out.println("add");
	}

	public static void testdelete() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(2);
			long pk = 2L;

			RoleModel model = new RoleModel();

			model.delete(bean);
			RoleBean deletedbean = RoleModel.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();

			System.out.println("Test Delete success");

		}
	}

	public static void testUpdate() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(1);
			bean.setName("rahul");
			bean.setDescription("dawer");
			RoleModel.update(bean);
			RoleBean updatedbean = RoleModel.findByPK(1L);
			if (!"rahul".equals(updatedbean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void FindByPk() throws Exception {
		RoleBean bean = new RoleBean();
		long pk = 1L;
		bean = RoleModel.findByPK(1);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDateTime());
		System.out.println(bean.getModifiedDateTime());
	}

	public static void FindByName() throws ApplicationException {
		try {
			RoleBean bean = new RoleBean();
			bean = RoleModel.findByName("vivek");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	

	public static void testSearch() {

		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("vivek");
			list = RoleModel.search(bean, 1, 3);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testList() throws Exception {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = RoleModel.list(1, 3);
			if (list.size() < 2) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}