package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.RoleBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;
/**
 * JDBC Implement of role model
 * @author priyal
 *
 */
public class RoleModel {
	/**
	 * create id 
	 * @return pk
	 * @throws DatabaseException
	 */
	public static Integer nextPK() throws DatabaseException{
			
		//	log.debug("Model nextPk started");
			
			Connection conn=null;
			int pk=0;
			try{
				conn=JDBCDataSource.getConnection();
				PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_role");
				
				ResultSet rs=stmt.executeQuery();
				
				while(rs.next()){
					pk=rs.getInt(1);
				}
				rs.close();
			}catch(Exception e){
				//log.error("DataBase Exception",e);
				throw new DatabaseException("Exception: Exception in getting pk");
				
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
		//	log.debug("Model nextPK end");
			return pk+1;
		}
	 /**
		 * add new role 
		 * @param bean
		 * @return pk
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
	public static long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		RoleBean duplicateRole = findByName(bean.getName());

		if (duplicateRole != null) {
			throw new DuplicateRecordException("role already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("Insert INTO st_role values(?,?,?,?,?,?,?)");

			ps.setInt(1, nextPK());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDateTime());
			ps.setTimestamp(7, bean.getModifiedDateTime());
			ps.executeUpdate();

			conn.commit();

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}
	/**
	 * update role 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */

	public static void update(RoleBean bean) throws Exception {
		RoleBean duplicateRole = findByName(bean.getName());

		Connection conn = null;
		if (duplicateRole != null) {
			throw new DuplicateRecordException("role already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_role SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCreatedBy());
			ps.setString(4, bean.getModifiedBy());
			ps.setTimestamp(5, bean.getCreatedDateTime());
			ps.setTimestamp(6, bean.getModifiedDateTime());
			ps.setLong(7, bean.getId());
			
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
			throw new ApplicationException("Exception : Exception in update Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * delete role
	 * @param bean
	 * @throws ApplicationException
	 */
	public static void delete(RoleBean bean) throws Exception {
		RoleBean duplicateRole = findByName(bean.getName());
		Connection conn = null;
		if (duplicateRole != null) {
			throw new DuplicateRecordException("role already exist");
		}
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_role WHERE ID =?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			conn.close();

			System.out.println("delete");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * find role with the help of name
	 * @param name
	 * @return bean
	 * @throws ApplicationException
	 */
	public static RoleBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE name=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));

				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting user by emailid");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	/**
	 * find by role with the help of role
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static RoleBean findByPK(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

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
	 * search role
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like " + bean.getDescription() + "%");
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
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
	/**
	 *list of role
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List list(int pageNo, int pageSize) throws Exception {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role");

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
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: exception in getting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return list;

	}

	public List list() throws Exception {
		// TODO Auto-generated method stub
		return list(0, 0);
	}
}
