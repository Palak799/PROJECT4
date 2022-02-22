package in.co.sunrays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.co.sunrays.bean.TimeTableBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.TimeTableModel;

public class TimeTableTest {
	public static TimeTableModel model = new TimeTableModel();

	public static void main(String[] args) throws Exception {
		 testAdd();
		// testDelete();
		// testUpdate();
		 //testFindByName();
		//testFindByPK(); // done
		// testSearch(); //done
		// testList(); //done

	}

	public static void testAdd() throws Exception {
		try {
			TimeTableBean bean = new TimeTableBean();
//			bean.setId(10);
			//bean.setSubjectName("sansksrit");
			//bean.setCourseName("java as");
			//bean.setSemester("1");
			
			bean.setCourseId(4);
			bean.setSubjectId(2);
			bean.setExamDate(new Date());
			bean.setExamTime("ejfiefek");
			
			long pk = model.add(bean);
			System.out.println("add tested ");
			TimeTableBean addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("fail to add");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws Exception {
		TimeTableBean bean = new TimeTableBean();
		try {
			// long pk = 1;
			bean.setId(8);
			model.delete(bean);
			System.out.println("Test delets success");
			TimeTableBean deletedBean = model.findByPK(8);
			if (deletedBean != null) {
				System.out.println("Test Delete fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			TimeTableBean bean = model.findByPK(2L);
			bean.setSubjectName("accounts");
			bean.setCourseName("java basics");
			model.update(bean);
			System.out.println("Test Update success");
			TimeTableBean updateBean = model.findByPK(2L);
			if (!"accounts".equals(updateBean.getSubjectName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			TimeTableBean bean = model.findByName("accounts");
			if (bean == null) {
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getCourseId());
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
			TimeTableBean bean = new TimeTableBean();
			long pk = 2L;
			bean = model.findByPK(2);
			if (bean == null) {
				System.out.println("Test Find By Pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getCourseId());
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
			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			bean.setSubjectName("CSP");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseName());
				//System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());
				//System.out.println(bean.getExamDate());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {

		try {
			TimeTableBean bean = new TimeTableBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimeTableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
		//		System.out.println(bean.getDescription());
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
