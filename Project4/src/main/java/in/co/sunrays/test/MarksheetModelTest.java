package in.co.sunrays.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.model.MarksheetModel;
import in.co.sunrays.model.StudentModel;

public class MarksheetModelTest {
	public static void main(String[] args) throws Exception {
		//	testadd();
		// testdelete();
		//	testUpdate();
		// FindByPk();
		// FindByRollNo();
		// testSearch();
		 testList();

	}

	public static void testadd() throws Exception {
		try {
			MarksheetBean bean = new MarksheetBean();
			bean.setRollNo("112");
			bean.setName("sakshi");
			bean.setPhysics(67);
			bean.setChemistry(65);
			bean.setMaths(77);
			bean.setStudentId(13L);
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDateTime(new Timestamp(new Date(0).getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date(0).getTime()));

			long pk = MarksheetModel.add(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testdelete() throws Exception {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 5L;
			bean.setId(5);
			MarksheetModel model = new MarksheetModel();
			model.delete(bean);
			MarksheetBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws Exception {
		try {
			MarksheetBean bean = MarksheetModel.findByPK(2L);

			bean.setName("palak");
			bean.setChemistry(78);
			bean.setMaths(99);
			 bean.setStudentId(13);
			MarksheetModel.update(bean);
			System.out.println("Test Update succ");
			MarksheetBean updateBean = MarksheetModel.findByPK(2L);

			if (!"palak".equals(updateBean.getName())) {
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
			MarksheetBean bean = MarksheetModel.findByPK(3);
			if (bean == null) {
				System.out.println("Test FindByPk fail");
			}
            System.out.println(bean.getId());
            System.out.println(bean.getRollNo());
            System.out.println(bean.getName());
            System.out.println(bean.getPhysics());
            System.out.println(bean.getChemistry());
            System.out.println(bean.getMaths());
		
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void FindByRollNo() throws ApplicationException {
		try {
			MarksheetBean bean = new MarksheetBean();
			bean = MarksheetModel.findByRollNo("102");
			if (bean != null) { 
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
            System.out.println(bean.getRollNo());
            System.out.println(bean.getName());
            System.out.println(bean.getPhysics());
            System.out.println(bean.getChemistry());
            System.out.println(bean.getMaths());

				} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public static void testSearch()
	{
		try {
			MarksheetBean bean = new MarksheetBean();
			ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
			list = (ArrayList<MarksheetBean>) MarksheetModel.search(bean, 1, 5);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator<MarksheetBean> it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getRollNo());
             //   System.out.println(bean.getStudentId());
                System.out.println(bean.getName());
                System.out.println(bean.getPhysics());
                System.out.println(bean.getChemistry());
                System.out.println(bean.getMaths());
              //  System.out.println(bean.getCreatedBy());
               //System.out.println(bean.getModifiedBy());
                //System.out.println(bean.getCreatedDateTime());
               // System.out.println(bean.getModifiedDateTime());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	public static void testList() throws Exception
	{
		try {
			MarksheetBean bean = new MarksheetBean();
            List list = new ArrayList();
            list = MarksheetModel.list(1, 5);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (MarksheetBean) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getRollNo());
                System.out.println(bean.getStudentId());
                System.out.println(bean.getName());
                System.out.println(bean.getPhysics());
                System.out.println(bean.getChemistry());
                System.out.println(bean.getMaths());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getCreatedDateTime());
                System.out.println(bean.getModifiedDateTime());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
	}


}
