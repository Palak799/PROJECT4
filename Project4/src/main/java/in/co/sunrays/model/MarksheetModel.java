package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.bean.MarksheetBean;
import in.co.sunrays.bean.StudentBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.JDBCDataSource;
/**
 * JDBC Implements of marksheet model
 * @author priyal
 *
 */
public class MarksheetModel {

	/**
	 * add new id
	 * @return pk
	 * @throws DatabaseException
	 */
	public static int nextPK() throws Exception {
		int pk = 0;
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_marksheet");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			pk = resultSet.getInt(1);
		}

		return pk + 1;
	}
	/**
	 * add new marksheet
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		StudentModel sModel=new StudentModel();
		StudentBean studentbean=sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName()+""+studentbean.getLastName());
		
		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
		int pk = 0;
		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("role already exist");
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();
				conn.setAutoCommit(false);

				PreparedStatement ps = conn.prepareStatement("Insert INTO st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, pk);
				ps.setString(2, bean.getRollNo());
				ps.setLong(3, bean.getStudentId());
				ps.setString(4, bean.getName());
				ps.setInt(5, bean.getPhysics());
				ps.setInt(6, bean.getChemistry());
				ps.setInt(7, bean.getMaths());
				ps.setString(8, bean.getCreatedBy());
				ps.setString(9, bean.getModifiedBy());
				ps.setTimestamp(10, bean.getCreatedDateTime());
				ps.setTimestamp(11, bean.getModifiedDateTime());
				ps.executeUpdate();
				conn.commit();

				ps.close();
				conn.close();

			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (Exception ex) {
					throw new ApplicationException("Exception : add marksheet exception " + ex.getMessage());
				}
				throw new ApplicationException("Exception : Exception in add Role");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		return pk;

	}
	/**
	 * update marksheet information
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static long update(MarksheetBean bean) throws Exception {
		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());
		int pk = 0;
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_marksheet SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getRollNo());
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDateTime());
			ps.setTimestamp(10, bean.getModifiedDateTime());
			ps.setLong(11,bean.getId());

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
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}
	/**
	 * delete marksheet information
	 * @param bean
	 * @throws ApplicationException
	 */
	public static void delete(MarksheetBean bean) throws Exception {
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_marksheet WHERE ID =?");
			ps.setLong(1, bean.getId());
			System.out.println("delete");
			ps.executeUpdate();
			conn.commit();
			ps.close();
			conn.close();
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
	 * find information with the help of rollno
	 * @param rollNo
	 * @return bean
	 * @throws ApplicationException
	 */

	public static MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet WHERE roll_No=?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting marksheet by rollNO");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	/**
	 * find information with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static MarksheetBean findByPK(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet WHERE ID=?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean= new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	/**
	 * search marksheet
	 * @param marksheet
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */	
	public static List search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != null && bean.getPhysics() > 0) {
				sql.append(" AND PHYSICS like '" + bean.getPhysics() + "%'");
			}
			if (bean.getChemistry() != null && bean.getChemistry() > 0) {
				sql.append(" AND CHEMISTRY like '" + bean.getChemistry() + "%'");
			}
			if (bean.getMaths() != null && bean.getMaths() > 0) {
				sql.append(" AND maths = '" + bean.getMaths());
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
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
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
	public List list() throws Exception {
		System.out.println("marksheet model Method List(0) Started");
		return list(0,0);
	}
	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	
	public static List list(int pageNo, int pageSize) throws Exception {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet");

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
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDateTime(rs.getTimestamp(10));
				bean.setModifiedDateTime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: exception in getting list of Matrksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return list;

	}
	/**
	 * get merit list
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total from `ST_MARKSHEET` order by total desc");

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
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
