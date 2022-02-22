package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;
/**
 * JDBC Implement of subject
 * @author priyal
 *
 */
public class SubjectModel {
	/**
	 * create id
	 * @return pk
	 * @throws DatabaseException
	 */
	public static int nextPK() throws Exception {
		int pk = 0;
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_subject");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			pk = resultSet.getInt(1);
		}

		return pk + 1;
	}
	/**
	 * add subject
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public static Long add(SubjectBean bean) throws Exception {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_subject VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, pk);
			ps.setString(2, bean.getSubjectName());
			ps.setLong(3, bean.getCourseId());
			ps.setLong(4,bean.getSubjectId());
			ps.setString(5, bean.getCourseName());
		ps.setString(6, bean.getDescription());
	
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDateTime());
			ps.setTimestamp(10, bean.getModifiedDateTime());
			ps.executeUpdate();

			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCourseId());
			// System.out.println(courseName);
			ps.close();
			conn.commit();
		} catch (Exception e) {
			// log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in the Rollback of Subject Model" + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in Add method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return (long) pk;

	}

	/**
	 * Delete a Subject
	 * 
	 * @param bean
	 * @throws ApplicationException
	 *
	 */
	public void delete(SubjectBean bean) throws ApplicationException{
	//	log.debug("Model Delete Started");
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_subject where id=?");
			stmt.setLong(1,bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
		//	log.error("Database Exception..",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:Delete RollBack Exception"+ex.getMessage());
			
			} throw new ApplicationException("Exception:Exception in delete Subject");	
			}finally{ JDBCDataSource.closeConnection(conn);
		
			}
		//log.debug("Model delete end");
		
		}
	
	
	  
	  /**
		 * Update a Subject
		 * 
		 * @param bean
		 * @throws Exception
		 * 
		 */

	public void update(SubjectBean bean) throws DuplicateRecordException, ApplicationException{
		
		//log.debug("Model update started");
		
		Connection conn=null;
		CourseModel cmodel=new CourseModel();
		CourseBean cBean = cmodel.findByPK(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());
		
		SubjectBean beanExist=findByName(bean.getSubjectName());
		
		if(beanExist!=null&&beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("Subject is already exist");
			
		}
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement stmt=conn.prepareStatement("Update st_subject set subject_name=?,course_id=?,subject_id=?,course_name=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			
			
			stmt.setString(1,bean.getSubjectName());
			stmt.setLong(2,bean.getCourseId());
			stmt.setLong(3,bean.getSubjectId());
			stmt.setString(4,bean.getCourseName());
			stmt.setString(5,bean.getDescription());
			stmt.setString(6,bean.getCreatedBy());
			stmt.setString(7,bean.getModifiedBy());
			stmt.setTimestamp(8,bean.getCreatedDateTime());
			stmt.setTimestamp(9,bean.getModifiedDateTime());
			stmt.setLong(10, bean.getId());
			
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
		//	log.error("Database Exception..",e);
		
			try{
				conn.rollback();
			}catch(Exception ex){
		ex.printStackTrace();
			throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			
			}
			throw new ApplicationException("Exception in updating Subject");
		}finally{
				JDBCDataSource.closeConnection(conn);
			}
		//	log.debug("Model update End");
		
		}

	/**
	 * Find User by Subject Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return bean
	 * @throws ApplicationException
	 * 
	 */

	public static SubjectBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_subject WHERE Subject_NAME=?");
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByName method End");
		return bean;
	}

	public static SubjectBean findByPk(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_subject WHERE ID=?");
		SubjectBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));	
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByPk method End");
		return bean;
	}

	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Subject with pagination
	 * 
	 * @return list : List of Users
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * 
	 * 
	 */

	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");
		System.out.println("model search" + bean.getId());

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND CourseID = " + bean.getCourseId());
			}

		
		}

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("sql is" + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
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

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Subject with pagination
	 * 
	 * @return list : List of Subject
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * 
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Subject Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_subject ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("database Exception....", e);
			e.printStackTrace();

			throw new ApplicationException("Exception in list Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Subject Model list method End");
		return list;
	}

}
