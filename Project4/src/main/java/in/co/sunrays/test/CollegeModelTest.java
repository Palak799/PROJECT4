package in.co.sunrays.test;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;

import com.mysql.jdbc.PreparedStatement;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.StudentModel;
import in.co.sunrays.util.JDBCDataSource;

public class CollegeModelTest {
	public static void main(String[] args) throws Exception {
//		testadd();
//	testUpdate();
		// testdelete();
		 testFindByName();
//	  testFindByPK();
		// testSearch();
		// testList();
	}

	public static void testadd() throws Exception {
		CollegeBean bean = new CollegeBean();
		bean.setName("davv");
		bean.setAddress("-khargone");
		bean.setState("mp");
		bean.setCity("indore");

		bean.setPhoneNo("07282267165");

		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date(0).getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date(0).getTime()));
		CollegeModel m = new CollegeModel();
		m.add(bean);

	}

	public static void testUpdate() throws Exception {
		try {
			CollegeModel model = new CollegeModel();

			CollegeBean bean = model.findByPK(3L);
			bean.setName("llll");
			bean.setAddress("lig");
			model.update(bean);
			System.out.println("Test Update success");
			CollegeBean updateBean = model.findByPK(3L);
			if (!"llll".equals(updateBean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testdelete() throws Exception {
		CollegeBean bean = new CollegeBean();
		bean.setId(2);
		CollegeModel model = new CollegeModel();

		model.delete(bean);
		System.out.println("Test Delete success");

	}

	public static void testFindByName() throws Exception {

		try {
			CollegeBean bean = CollegeModel.findByName("maths");
			if (bean == null) {
				System.out.println("Test Find By Name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDateTime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			long pk = 0L;
			CollegeBean bean = CollegeModel.findByPK(3);
			if (bean == null) {
				System.out.println("fcvxv");
			}
			System.err.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("vivek");
			bean.setId(2);
			list = CollegeModel.search(bean, 1, 2);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() throws Exception {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = CollegeModel.list(1, 2);
			if (list.size() < 2) {
				System.out.println("test listr fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}