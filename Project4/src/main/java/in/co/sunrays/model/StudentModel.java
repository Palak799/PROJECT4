package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;
/**
 * JDBC Implement of student model
 * @author priyal
 *
 */
public class StudentModel {
	/**
	 * create id 
	 * @return pk
	 * @throws DatabaseException
	 */
	public static Integer nextPK() throws DatabaseException{
		//log.debug("Model nextPK Started");
		
		Connection conn=null;
		 int pk=0;
		 try{
			 conn=JDBCDataSource.getConnection();
			 PreparedStatement stmt=conn.prepareStatement("select max(id) from st_student");
			 ResultSet rs=stmt.executeQuery();
			 while(rs.next()){
				 pk=rs.getInt(1);
				 
			 }
			 rs.close();
		 }catch(Exception e){
			// log.error("Database Exception..",e);
			 throw new DatabaseException("Exception: Exception in getting PK");
			 
		 }finally{
			 JDBCDataSource.closeConnection(conn);
		 }
		// log.debug("Model nextPK End");
		 return pk+1;
	}
	/**
	 * add student
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public static long add(StudentBean bean) throws Exception{
	//	log.debug("Model add started");
		Connection conn=null;
		CollegeModel cModel=new CollegeModel();
		CollegeBean collegeBean=cModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		
		StudentBean duplicateName=findByEmailId(bean.getEmail());
		int pk=0;
		if (duplicateName!=null){
			throw new DuplicateRecordException("Email already exist");
		}
		try{
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			System.out.println(pk+" in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, pk);
			stmt.setLong(2,bean.getCollegeId());
			stmt.setString(3,bean.getCollegeName());
			stmt.setString(4,bean.getFirstName());
			stmt.setString(5,bean.getLastName());
			stmt.setDate(6,new java.sql.Date(bean.getDob().getTime()));
			stmt.setString(7,bean.getMobileNo());
			stmt.setString(8,bean.getEmail());
			stmt.setString(9,bean.getCreatedBy());
			stmt.setString(10,bean.getModifiedBy());
			stmt.setTimestamp(11,bean.getCreatedDateTime());
			stmt.setTimestamp(12,bean.getModifiedDateTime());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			//log.error("Database Exception..",e);
			e.printStackTrace();
			try{
				conn.rollback();
				
			}catch(Exception ex){
				ex.printStackTrace();
				throw new ApplicationException("Exception: add rollback exception "+ex.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add student");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
			
		}
	//	log.debug("Model add End");
		return pk;
	}
	/**
	 * update student
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static void update(StudentBean bean) throws Exception {
		StudentBean beanExist = findByEmailId(bean.getEmail());

		int pk=0;
		Connection conn = null;
		if (beanExist != null&&beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Emailid is already exist");
		}
		
		  CollegeModel cModel=new CollegeModel(); CollegeBean
		  collegeBean=cModel.findByPK(bean.getCollegeId());
		  bean.setCollegeName(collegeBean.getName());
		 
		try {
			conn = JDBCDataSource.getConnection();
			pk=nextPK();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("UPDATE st_student SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		
			    ps.setLong(1, bean.getCollegeId());
	            ps.setString(2, bean.getCollegeName());
	            ps.setString(3, bean.getFirstName());
	            ps.setString(4, bean.getLastName());
	            ps.setDate(5, new java.sql.Date(bean.getDob().getTime()));
	            ps.setString(6, bean.getMobileNo());
	            ps.setString(7, bean.getEmail());
	            ps.setString(8, bean.getCreatedBy());
	            ps.setString(9, bean.getModifiedBy());
	            ps.setTimestamp(10, bean.getCreatedDateTime());
	            ps.setTimestamp(11, bean.getModifiedDateTime());
	    		ps.setLong(12, bean.getId());

	            ps.executeUpdate();
			conn.commit();
			ps.close();
			conn.close();
			System.out.println("update");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * find student with the help of emailId
	 * @param email
	 * @return bean
	 * @throws ApplicationException
	 */
	public static StudentBean findByEmailId(String email) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE Email=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setCollegeId(rs.getLong(2));
                bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User byt email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
			}
	/** 
	 * delete student
	 * @param bean
	 * @throws ApplicationException
	 */

	public void delete(StudentBean bean) throws ApplicationException{
	//	log.debug("Model delete Started");
		
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_student where ID=?");
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
		//	log.error("Database exception..",e);
			try{
				conn.rollback();
				
			}catch(Exception ex){
				throw new ApplicationException("Exception: Delete rollback exception"+ex.getMessage());
				
			}throw new ApplicationException("Exception in delete Student");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Model delete end");
		
	}
	public static StudentBean findByRollNo(String rollNo) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE rollNo=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setCollegeId(rs.getLong(2));
                bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
      			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting student by rollNo");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}
	/**
	 * find student with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
		public static StudentBean findByPK(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE ID=?");
		StudentBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
                bean.setCollegeId(rs.getLong(2));
                bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
		/**
		 * search student
		 * @param bean
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */

	public static List search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
            }
            if (bean.getDob() != null && bean.getDob().getDate()> 0) {
                sql.append(" AND DOB = " + bean.getDob());
            }
            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
                sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
            }
            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
                sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
            }
            if (bean.getCollegeId()>0) {
                   
                sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
            }
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setCollegeId(rs.getLong(2));
                bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
                list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
public static List list() throws Exception
{
	return list(0,0);
	
}
/**
 * 
 * list of student
 * @param pageNo
 * @param pageSize
 * @return list
 * @throws ApplicationException
 */
	public static List list(int pageNo, int pageSize) throws Exception {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
                StudentBean bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setCollegeId(rs.getLong(2));
                bean.setCollegeName(rs.getString(3));
                bean.setFirstName(rs.getString(4));
                bean.setLastName(rs.getString(5));
                bean.setDob(rs.getDate(6));
                bean.setMobileNo(rs.getString(7));
                bean.setEmail(rs.getString(8));
                bean.setCreatedBy(rs.getString(9));
                bean.setModifiedBy(rs.getString(10));
                bean.setCreatedDateTime(rs.getTimestamp(11));
                bean.setModifiedDateTime(rs.getTimestamp(12));
                list.add(bean);
                }
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: exception in getting list of student");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return list;

	}
}

