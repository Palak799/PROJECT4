package in.co.sunrays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.FacultyBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.FacultyModel;

public class FacultyModelTest {
	public static void main(String[] args) throws Exception {
		 testadd();
		//testUpdate();
		// testdelete();
		 //testSearch();
		//testList();
		// FindByPk();
		// FindByEmail();
	}

	public static void testadd() throws Exception {
		try {
			FacultyBean bean = new FacultyBean();
			bean.setFirstName("vipin");
			bean.setLastName("gupta");
			bean.setGender("female");
			bean.setLoginId("vipin@gmail.com");
			bean.setDateofjoining(new Date());
			bean.setQualification("msc");
			bean.setMobileno("79990191");
			bean.setCollegeid(6);
			bean.setCollegeName("bhu");
			bean.setCourseId(1);
			bean.setCourseName("java basics");
			bean.setSubjectId(1);
			bean.setSubjectName("computer");

			bean.setCreatedBy("admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			FacultyModel model = new FacultyModel();

			long pk = model.add(bean);
			FacultyBean addedbean = FacultyModel.findByPK(pk);
			if (addedbean == null) {
				System.out.println("test added fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		System.out.println("add");
	}

	public static void testdelete() throws Exception {
		try {
			FacultyBean bean = new FacultyBean();
			bean.setId(1);
			long pk = 1L;

			FacultyModel model = new FacultyModel();

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

	public static void testUpdate() throws Exception {
		try {
			FacultyBean bean = new FacultyBean();
			bean.setId(1);
			bean.setFirstName("lakshya");
			bean.setCollegeid(9);	
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setGender("female");	
			bean.setMobileno("7999970191");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("Admin");
			bean.setModifiedBy("admin");
			
			//bean.setCourseName("java basics");
			bean.setDateofjoining(new Date());
			FacultyModel.update(bean);
			FacultyBean updatedbean = FacultyModel.findByPK(1L);
			if (!"lakshya".equals(updatedbean.getFirstName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();

		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void FindByPk() throws Exception {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 1L;
			bean = FacultyModel.findByPK(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDateofjoining());
			System.out.println(bean.getQualification());
			System.out.println(bean.getLoginId());
			System.out.println(bean.getMobileno());
			System.out.println(bean.getCollegeid());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void FindByEmail() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = FacultyModel.findByEmail("palak@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDateofjoining());
			System.out.println(bean.getQualification());
			System.out.println(bean.getLoginId());
			System.out.println(bean.getMobileno());
			System.out.println(bean.getCollegeid());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			bean.setFirstName("palak");
			list = FacultyModel.search(bean, 1, 1);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getGender());
				System.out.println(bean.getDateofjoining());
				System.out.println(bean.getQualification());
				System.out.println(bean.getLoginId());
				System.out.println(bean.getMobileno());
				System.out.println(bean.getCollegeid());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() throws Exception {

		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			list = FacultyModel.list(1, 1);
			if (list.size() > 0) {

				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (FacultyBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getFirstName());
					System.out.println(bean.getLastName());
					System.out.println(bean.getGender());
					System.out.println(bean.getDateofjoining());
					System.out.println(bean.getQualification());
					System.out.println(bean.getLoginId());
					System.out.println(bean.getMobileno());
					System.out.println(bean.getCollegeid());
					System.out.println(bean.getCollegeName());
					System.out.println(bean.getCourseId());
					System.out.println(bean.getCourseName());
					System.out.println(bean.getSubjectId());
					System.out.println(bean.getSubjectName());
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

}
