package in.co.sunrays.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.CollegeModel;
import in.co.sunrays.model.RoleModel;
import in.co.sunrays.model.StudentModel;

public class StudentModelTest {
	public static void main(String[] args) throws Exception {
		// testadd();
		// testdelete();
		 //testUpdate();
		// FindByPk();
		// FindByEmailId();
		 testSearch();
		// testList();

	}

	public static void testadd() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setFirstName("anita");
			bean.setLastName("karodia");
			bean.setDob(sdf.parse("14/12/1998"));
			bean.setMobileNo("626777475");
			bean.setEmail("anita@gmail.com");
			bean.setCollegeId(5L);
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date(0).getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date(0).getTime()));

			long pk = StudentModel.add(bean);
			StudentBean addedbean = StudentModel.findByPK(pk);
			if (addedbean != null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testdelete() throws Exception {
		try {
			StudentBean bean = new StudentBean();
			long pk = 2L;
			bean.setId(2);
			StudentModel model = new StudentModel();
			model.delete(bean);
			StudentBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws Exception {
		try {
			StudentBean bean = StudentModel.findByPK(13L);
			bean.setCollegeId(9L);
			bean.setFirstName("yunika");
			bean.setLastName("gupta");
			StudentModel.update(bean);

			System.out.println("Test Update succ");
			StudentBean updateBean = StudentModel.findByPK(13L);

			if (!"yunika".equals(updateBean.getFirstName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void FindByPk() {
		try {
			StudentBean bean = StudentModel.findByPK(13);
			if (bean == null) {
				System.out.println("Test FindByPk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void FindByEmailId() throws ApplicationException {
		try {
			StudentBean bean = new StudentBean();
			bean = StudentModel.findByEmailId("palak@gmail.com");
			if (bean != null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public static void testSearch()
	{
		try {
			StudentBean bean = new StudentBean();
			ArrayList<StudentBean> list = new ArrayList<StudentBean>();
			list = (ArrayList<StudentBean>) StudentModel.search(bean, 1, 5);
			bean.setCollegeName("davv");
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator<StudentBean> it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
                System.out.println("id  "+ bean.getId());
                System.out.println("fname   " + bean.getFirstName());
                System.out.println("last   " + bean.getLastName());
                System.out.println("date  " + bean.getDob());
                System.out.println( "mobile  " +bean.getMobileNo());
                System.out.println("email  " +bean.getEmail());
                System.out.println("colleg " +bean.getCollegeId());
                System.out.println(bean.getCreatedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	public static void testList() throws Exception
	{
		try {
			StudentBean bean = new StudentBean();
            List list = new ArrayList();
            list = StudentModel.list(1, 12);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (StudentBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getFirstName());
                System.out.println(bean.getLastName());
                System.out.println(bean.getDob());
                System.out.println(bean.getMobileNo());
                System.out.println(bean.getEmail());
                System.out.println(bean.getCollegeId());
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
