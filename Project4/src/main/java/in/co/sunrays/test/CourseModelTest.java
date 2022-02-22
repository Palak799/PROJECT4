package in.co.sunrays.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.CourseModel;
import in.co.sunrays.util.JDBCDataSource;

public class CourseModelTest {
	public static void main(String[] args) throws Exception {
		// testadd();
	//	testUpdate();
		// testdelete();
	//	 testFindByName();
	//	 testFindByPK();
		//testSearch();
	testList();
	}

	public static void testadd() throws Exception {
		CourseBean bean = new CourseBean();
		bean.setCourseName("llll");
		bean.setDescription("jacjefauih");
		bean.setDuration("2");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date(0).getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date(0).getTime()));
		CourseModel m = new CourseModel();
		m.add(bean);

	}

	public static void testUpdate() throws Exception {
		try {
			CourseBean bean = CourseModel.findByPK(2L);
			bean.setCourseName("java basics");
			bean.setDuration("6");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			CourseModel.update(bean);
			System.out.println("Test Update succ");
			CourseBean updateBean = CourseModel.findByPK(2L);
			if (!"java basics".equals(updateBean.getCourseName())) {
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testdelete() throws Exception {
		CourseBean bean = new CourseBean();
		bean.setId(2);
		CourseModel model = new CourseModel();

		model.delete(bean);
		System.out.println("Test Delete success");

	}

	public static void testFindByName() throws Exception {

		try {
			CourseBean bean = CourseModel.findByName("java basics");
			if (bean == null) {
				System.out.println("Test Find By Name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
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
			CourseBean bean = CourseModel.findByPK(4);
			if (bean == null) {
				System.out.println("fcvxv");
			}
			System.err.println(bean.getId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			
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
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			bean.setCourseName("vivek");
			bean.setId(2);
			list = CourseModel.search(bean, 1, 2);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());

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
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			list = CourseModel.list(1, 5);
			if (list.size() < 2) {
				System.out.println("test listr fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());

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
