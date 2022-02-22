package in.co.sunrays.model;

import java.util.List;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.bean.CollegeBean;
import in.co.sunrays.util.JDBCDataSource;
/**
* JDBC implements of college model
* @author priyal
*
*/
public class CollegeModel {
	private static Logger log=Logger.getLogger(CollegeModel.class);
	
	public static int nextPK() throws Exception {
		int pk = 0;
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_college");
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			pk = resultSet.getInt(1);
		}

		return pk + 1;
	}
	/**
	* add new college
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public static long add(CollegeBean bean) throws Exception{

		log.debug("Model add Started");
		System.out.println("addd");
		Connection conn=null;
		int pk=0;

		CollegeBean duplicateCollegeName=findByName(bean.getName());

		if(duplicateCollegeName!=null){
		throw new DuplicateRecordException("College Name Already Exists");

		}
		try{
		conn=JDBCDataSource.getConnection();

		System.out.println("addd 1");
		pk=nextPK();
		conn.setAutoCommit(false);
		PreparedStatement stmt=conn.prepareStatement("Insert into st_college values(?,?,?,?,?,?,?,?,?,?)");


		stmt.setInt(1, pk);
		stmt.setString(2,bean.getName());
		stmt.setString(3,bean.getAddress());
		stmt.setString(4,bean.getState());
		stmt.setString(5,bean.getCity());
		stmt.setString(6,bean.getPhoneNo());
		stmt.setString(7,bean.getCreatedBy());
		stmt.setString(8,bean.getModifiedBy());
		stmt.setTimestamp(9,bean.getCreatedDateTime());
		stmt.setTimestamp(10,bean.getModifiedDateTime());

		stmt.executeUpdate();
		conn.commit();
		stmt.close();


		}catch(Exception e){
		log.error("Database Exception..",e);
		e.printStackTrace();
		try{
		conn.rollback();
		}catch(Exception ex){
		ex.printStackTrace();
		throw new ApplicationException("Exception:add rollback exception"+ex.getMessage());
		}
		throw new ApplicationException("Exception : Exception in add College");
		}finally{
		JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;


		}

	/**
	* update college detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public static void update(CollegeBean bean) throws Exception {
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);

		PreparedStatement ps = conn.prepareStatement(
				"UPDATE st_college SET  Name=?, Address=?, State=?, City=?, Phone_No =?, Created_By=?, Modified_By=?, Created_DateTime=? ,Modified_DateTime =? WHERE Id=?");

		ps.setString(1, bean.getName());
		ps.setString(2, bean.getAddress());
		ps.setString(3, bean.getState());
		ps.setString(4, bean.getCity());
		ps.setString(5, bean.getPhoneNo());
		ps.setString(6, bean.getCreatedBy());
		ps.setString(7, bean.getModifiedBy());
		ps.setTimestamp(8, bean.getCreatedDateTime());
		ps.setTimestamp(9, bean.getModifiedDateTime());
		ps.setLong(10, bean.getId());

		ps.executeUpdate();
		conn.commit();
		ps.close();
		conn.close();
		System.out.println("update");

	}
	/**
	* delete college information
	* @param b
	* @throws DatabaseException
	*/

	public void delete(CollegeBean bean) throws ApplicationException{
		log.debug("Model Delete Started");
		Connection conn=null;
		try{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement stmt=conn.prepareStatement("delete from st_college where id=?");
		stmt.setLong(1,bean.getId());
		stmt.executeUpdate();
		conn.commit();
		stmt.close();

		}catch(Exception e){
		log.error("Database Exception..",e);
		try{
		conn.rollback();
		}catch(Exception ex){
		throw new ApplicationException("Exception:Delete RollBack Exception"+ex.getMessage());

		} throw new ApplicationException("Exception:Exception in delete college");
		}finally{ JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model delete end");

		}
	/**
	* find the infomation with the help of college name
	* @param name
	* @return bean
	* @throws ApplicationException
	*/
	public static CollegeBean findByName(String name) throws ApplicationException{
		log.debug("Model FindByName Started ");
		StringBuffer sql=new StringBuffer("Select * from st_college where name=?");

		CollegeBean bean=null;
		Connection conn=null;
		try{
		conn=JDBCDataSource.getConnection();
		PreparedStatement stmt=conn.prepareStatement(sql.toString());
		stmt.setString(1,name);

		ResultSet rs=stmt.executeQuery();

		while(rs.next()){
		bean=new CollegeBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setAddress(rs.getString(3));
		bean.setState(rs.getString(4));
		bean.setCity(rs.getString(5));
		bean.setPhoneNo(rs.getString(6));
		bean.setCreatedBy(rs.getString(7));
		bean.setModifiedBy(rs.getString(8));
		bean.setCreatedDateTime(rs.getTimestamp(9));
		bean.setModifiedDateTime(rs.getTimestamp(10));
		}rs.close();


		}catch(Exception e){
		log.error("Database Exception..",e);
		throw new ApplicationException("Exception:Exception in getting College by Name");


		}finally{
		JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName Ended");
		return bean;
		}
	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	public static CollegeBean findByPK(long pk) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_college WHERE ID=?");
		CollegeBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));

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
	* search college information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public static List search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_college WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" AND STATE like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
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
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
public List list() throws Exception{
	return list(0,0);
	
}
/**
* to show list of college
* @param pageNo
* @param pageSize
* @return list
* @throws ApplicationException
*/
	public static List list(int pageNo, int pageSize) throws Exception {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_college");

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
				CollegeBean bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: exception in getting list of users" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return list;
		}
}
