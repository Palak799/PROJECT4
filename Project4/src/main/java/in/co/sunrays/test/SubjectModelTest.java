package in.co.sunrays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.MarksheetModel;
import in.co.sunrays.model.SubjectModel;

public class SubjectModelTest {

	public static SubjectModel model = new SubjectModel();

	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByName();
		// testSearch();
		//testList();
testFindByPK();
	}

	/**
	 * Tests add a Student
	 * 
	 * @throws Exception
	 */
	public static void testAdd() throws Exception {

		SubjectBean bean = new SubjectBean();

		// bean.setId(1L);
		bean.setSubjectId(1);
		bean.setSubjectName("maths");
		bean.setDescription("basic");
		bean.setCourseId(103);
		bean.setCourseName("b.a");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		System.out.println("add method call");
		System.out.println("data enter");
		model.add(bean);

	}

	public static void testDelete(){
		try{
			SubjectBean bean=new SubjectBean();
//			long pk=9L;
			bean.setId(3);
			model.delete(bean);
			System.out.println("Test Delete success");
			SubjectBean deletedBean=model.findByPk(3);
			
			if(deletedBean!=null){
				System.out.println("Test Delete fail");
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws Exception {
		try {
			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();
			bean.setId(1L);
			bean.setSubjectName("sanskrit");
			//bean.setCourseName("java basics");
			bean.setCourseId(1);
			bean.setSubjectId(1);
			model.update(bean);
			System.out.println("Test Update succ");
			SubjectBean updateBean = SubjectModel.findByPk(1L);

			if (!"sanskrit".equals(updateBean.getSubjectName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			SubjectBean bean = new SubjectBean();
			long pk = 4L;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getModifiedBy());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {
		try {
			SubjectBean bean = new SubjectBean();
			bean = model.findByName("sanskrit");
			if (bean != null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCourseName());
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
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			bean.setSubjectName("sanskrit");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testList() {

		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			list = model.list(1, 1);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()
					) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseName());
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
