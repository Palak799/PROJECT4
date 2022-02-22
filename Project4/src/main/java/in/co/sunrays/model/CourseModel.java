package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;

/**
 * JDBC Implements of course model
 * @author priyal;
 *
 */
public class CourseModel {
	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException{
		//log.debug("Model nextPK Started");
		
		Connection conn=null;
		int pk=0;
		
		try{
			conn=JDBCDataSource.getConnection();
		    PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_course");
			
		    ResultSet rs=stmt.executeQuery();
		    
		    while(rs.next()){
		    	pk=rs.getInt(1);
		    }
		    stmt.close();
		    rs.close();
		}catch(Exception e){
			//log.error("Daatabase Exception..",e);
			throw new DatabaseException("Exception:Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
	//	log.debug("Model pk end");
		return pk+1;
	}
	
	/**
	 * add new course
	 * 
	 * @param b
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CourseBean bean) throws Exception{
		
		//log.debug("Model add Started");
		Connection conn=null;
		int pk=0;
		CourseBean beanExist=findByName(bean.getCourseName());
		
		if(beanExist!=null&&beanExist.getId()!=bean.getId()){
			throw new DuplicateRecordException("Course is already exist");
			
		}

		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk=nextPK();
			PreparedStatement stmt=conn.prepareStatement("Insert into st_course values(?,?,?,?,?,?,?,?)");
		
		stmt.setInt(1, pk);
		stmt.setString(2,bean.getCourseName());
		
		stmt.setString(3,bean.getDescription());
		stmt.setString(4,bean.getDuration());
		stmt.setString(5,bean.getCreatedBy());
		stmt.setString(6, bean.getModifiedBy());
		stmt.setTimestamp(7,bean.getCreatedDateTime());
		stmt.setTimestamp(8,bean.getModifiedDateTime());
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
		
	}catch(Exception e){
		//log.error("Database Exception..",e);
		e.printStackTrace();
		try{
			conn.rollback();
		}catch(Exception ex){
			throw new ApplicationException("Exception: add rollback exception"+ex.getMessage());
		}
		throw new ApplicationException("Exception: Exception in add college");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
	//	log.debug("Model add end");
		return pk;
	}

	public void delete(CourseBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception in Rollback Method" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Update a Course
	 * 
	 * @param bean
	 * @throws DuplicateRecordException
	 * 
	 */

	public static void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		
		  CourseBean beanExist = findByName(bean.getCourseName()); if(beanExist !=null &&
		  beanExist.getId()!=bean.getId()){ throw new
		  DuplicateRecordException("Course Already Exist"); }
		 
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COURSE SET COURSE_NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getCourseName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find User by Course
	 * 
	 * @param name : get parameter
	 * @return bean
	 * 
	 */

	public static CourseBean findByName(String coursename) throws ApplicationException{
		//log.debug("Model find by name started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where course_name=?");
		
		CourseBean bean=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1, coursename);
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CourseBean();
				
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
			//log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by Name");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
	//	log.debug("Model  findByName Ended");
		return bean;
	}
	/**
	 * Find User by Course
	 * 
	 * @param l : get parameter
	 * @return bean
	 * 
	 */

	public static CourseBean findByPK(long pk) throws ApplicationException{
	//	log.debug("Model find by pk started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where id=?");
		CourseBean bean=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1,pk);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
		//	log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by pk");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		//log.debug("Model  findByPK End");
		return bean;
		
		
	}
	/**
	 * Search Course
	 * 
	 * @param bean : Search Parameters
	 * 
	 */

	public List search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Course with pagination
	 * 
	 * @return list : List of Users
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 * 
	 */

	public static List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>. search k ander");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			/*
			 * if(bean.getName()!= null && bean.getName().length()>0){
			 * sql.append(" AND Name like '" + bean.getName() +"%'"); }
			 */
			/*
			 * if(bean.getDescription()!=null && bean.getName().length()>0){
			 * sql.append(" AND Description like '" + bean.getDescription() + "%'"); }
			 * if(bean.getDuration()!=null && bean.getDuration().length() >0){
			 * sql.append(" AND Duration like '" + bean.getDuration().length() + "%'"); }
			 */
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));

				bean.setDuration(rs.getString(4));

				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in the Search Method" + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("----------------------------------->>>>" + list.size());
		return list;
	}

	/**
	 * Get List of Course
	 * 
	 * @return list : List of Course
	 * 
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Course with pagination
	 * 
	 * @return list : List of Course
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 */

	public static List list(int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CourseBean bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDateTime(rs.getTimestamp(7));
				bean.setModifiedDateTime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
