package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import org.apache.log4j.Logger;

import in.co.sunrays.bean.UserBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.exception.RecordNotFoundException;
import in.co.sunrays.util.EmailBuilder;
import in.co.sunrays.util.EmailMessage;
import in.co.sunrays.util.EmailUtility;
import in.co.sunrays.util.JDBCDataSource;

public class UserModel {
private static Logger log= Logger.getLogger(UserModel.class);
	
	private long Roleid;

	public long getRoleid() {
		return Roleid;
	}

	public void setRoleid(long Roleid) {
		this.Roleid = Roleid;
	}
	
	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public static Integer nextPK() throws DatabaseException{
		log.debug("Model nextPK Started");
		Connection conn=null;
		int pk=0;
		
		try{
			conn=   JDBCDataSource.getConnection();	
		
		PreparedStatement stmt =conn.prepareStatement("Select max(id) from st_user");
		
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			pk=(int)rs.getLong(1);
		}
		rs.close();
		}catch(Exception e){
			log.error("DatabaseException",e);
			throw new DatabaseException("Exception: Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	/**
	 * add user
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static long add(UserBean bean) throws ApplicationException, DuplicateRecordException{
		
		log.debug("Model add started");
		Connection conn=null;
		int pk=0;
		
		UserBean existbean=findByLogin(bean.getLogin());
		
		if(existbean!=null){
			throw new DuplicateRecordException("Login id already exist");
			
		}
		

	try{
		conn= JDBCDataSource.getConnection();
	
		
		pk=nextPK();
		System.out.println(pk+ "in ModelJDBC");
		conn.setAutoCommit(false);
		
		PreparedStatement stmt=conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		stmt.setInt(1, pk);
		stmt.setString(2,bean.getFirstName());
		stmt.setString(3,bean.getLastName());
		stmt.setString(4,bean.getLogin());
		stmt.setString(5, bean.getPassword());
		stmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		stmt.setString(7,bean.getMobileNo());
		stmt.setLong(8, bean.getRoleId());
		stmt.setInt(9, bean.getUnSuccessfulLogin());
		stmt.setString(10,bean.getGender());
		stmt.setTimestamp(11,bean.getLastLogin());
		stmt.setString(12,bean.getLock());
		stmt.setString(13, bean.getRegisteredIp());
		stmt.setString(14,bean.getLastLoginIp());
		stmt.setString(15,bean.getCreatedBy());
		stmt.setString(16,bean.getModifiedBy());
		stmt.setTimestamp(17,bean.getCreatedDateTime());
		stmt.setTimestamp(18,bean.getModifiedDateTime());
		System.out.println(bean.getCreatedDateTime()+"ajay123456777777777777777777");
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database Exception..",e);
		try{
			conn.rollback();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("Exception:add roll back Exception"+ex.getMessage());
			
		}
		throw new ApplicationException("Exception in add user");
		
	}	finally{
		JDBCDataSource.closeConnection(conn);
	}
		log.debug("Model add end");
		return pk;
		
	}
	
	/**
	 * delete user
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(UserBean bean) throws ApplicationException{
		
		log.debug("Model delete started");
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_user where id=?");
			
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
			
			
		}catch(Exception e){
			log.error("Database Exception ",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:Delete rollback Exception"+ex.getMessage());
				
			}throw new ApplicationException("Exception:Exception in delete user");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}
	
	
	/**
	 * find user by login
	 * 
	 * @param login
	 * @return bean
	 * @throws ApplicationException
	 */
	public static UserBean findByLogin(String login) throws ApplicationException{
		
		log.debug("Model findByLogin Started");
		Connection conn=null;
		UserBean bean=null;
		
		StringBuffer sql=new StringBuffer("Select * from st_user where login=?");
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1, login);
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				bean=new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));
				
				
			}rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception",e);
			throw new ApplicationException ("Exception:Exception in getting User by login");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}
	
	/**
	 * find user by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static UserBean findByPK(long pk) throws ApplicationException{
		log.debug("Model findByPK started");
		StringBuffer sql=new StringBuffer("select * from st_user where id=?");
		
		UserBean bean=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				bean=new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));
				
				
				
			}rs.close();
		}catch(Exception e){
			
			 e.printStackTrace();
	            log.error("Database Exception..", e);
	            throw new ApplicationException("Exception : Exception in getting User by pk");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model findByPK End");
	        return bean;
		
	}
	
	
	/**
	 * update user
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
public static void update(UserBean bean) throws ApplicationException, DuplicateRecordException{
		
		log.debug("Model update Started");
		Connection conn=null;
		
		UserBean beanExist = findByLogin(bean.getLogin());
		
		System.out.println("cccccccccccccccccccccccccccccccccc update in model"+bean.getPassword());
		
		  if (beanExist!=null&&!(beanExist.getId()==bean.getId())){ throw new
		  DuplicateRecordException("Login Id already exist"); }
		 try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement stmt=conn.prepareStatement("update st_user set first_name=?,last_name=?,"
					+ "login=?,password=?,dob=?,mobile_no=?,role_id=?,"
					+ "gender=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

		stmt.setString(1, bean.getFirstName());
		stmt.setString(2,bean.getLastName());
		stmt.setString(3, bean.getLogin());
		stmt.setString(4,bean.getPassword());
		stmt.setDate(5, new java.sql.Date( bean.getDob().getTime()));
		stmt.setString(6, bean.getMobileNo());
		stmt.setLong(7, bean.getRoleId());
	//	stmt.setInt(8, bean.getUnSuccessfulLogin());
		stmt.setString(8,bean.getGender());
	//	stmt.setTimestamp(10,bean.getLastLogin());
	//	stmt.setString(11,bean.getLock());
	//	stmt.setString(12, bean.getRegisteredIp());
	//	stmt.setString(13,bean.getLastLoginIp());
		stmt.setString(9,bean.getCreatedBy());
		stmt.setString(10,bean.getModifiedBy());
		stmt.setTimestamp(11,bean.getCreatedDateTime());
		stmt.setTimestamp(12,bean.getModifiedDateTime());
		stmt.setLong(13, bean.getId());
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database Exception..",e);
		try{
			
			conn.rollback();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("Exception:Delete rollback Exception"+ex.getMessage());
			
		}
		throw new ApplicationException("Exception in updating user");
		
	}	finally{
		JDBCDataSource.closeConnection(conn);
	}
		log.debug("Model update end");
		
	}
	
	
	
	public List search(UserBean bean) throws ApplicationException {
	        return search(bean, 0, 0);
	    }
	 
	
	/**
	 * search user
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List search(UserBean bean,int pageNo,int pageSize) throws ApplicationException{
		 log.debug("Model search started");
		 StringBuffer sql=new StringBuffer("select * from st_user where 1=1");
		 
//		 System.out.println(bean.getFirstName());
		 
		  if (bean != null) {
	            if (bean.getId() > 0) {
	                sql.append(" And id = " + bean.getId());
	            }
	            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
	                sql.append(" And first_name like '" + bean.getFirstName() + "%'");
	            }
	            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
	                sql.append(" And last_name like '" + bean.getLastName() + "%'");
	            }
	            if (bean.getLogin() != null && bean.getLogin().length() > 0) {
	                sql.append(" And login like '" + bean.getLogin() + "%'");
	            }
	            if (bean.getPassword() != null && bean.getPassword().length() > 0) {
	                sql.append(" And password like '" + bean.getPassword() + "%'");
	            }
	            if (bean.getDob() != null && bean.getDob().getDate() > 0) {
	                sql.append(" And dob = " + bean.getGender());
	            }
	            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
	                sql.append(" And mobile_no = " + bean.getMobileNo());
	            }
	            if (bean.getRoleId() > 0) {
	                sql.append(" AND role_id = " + bean.getRoleId());
	            }
	            if (bean.getUnSuccessfulLogin() > 0) {
	                sql.append(" And Unsuccessfull_login = "
	                        + bean.getUnSuccessfulLogin());
	            }
	            if (bean.getGender() != null && bean.getGender().length() > 0) {
	                sql.append(" And gender like '" + bean.getGender() + "%'");
	            }
	            if (bean.getLastLogin() != null
	                    && bean.getLastLogin().getTime() > 0) {
	                sql.append(" And last_login = " + bean.getLastLogin());
	            }
	            /*if (bean.getRegisteredIP() != null
	                    && bean.getRegisteredIP().length() > 0) {
	                sql.append(" And registered_ip like '" + bean.getRegisteredIP()
	                        + "%'");
	            }
	            if (bean.getLastLoginIP() != null
	                    && bean.getLastLoginIP().length() > 0) {
	                sql.append(" And last_login_ip like '" + bean.getLastLoginIP()
	                        + "%'");
	            }*/
	            
	            if(pageSize>0){
	            	pageNo=(pageNo-1)*pageSize;
	            	sql.append(" Limit " + pageNo + "," + pageSize);
	            }
		  }
	            
	            System.out.println(sql);
	            List list=new ArrayList();
	            Connection conn=null;
	            
	            try{
	            	conn=JDBCDataSource.getConnection();
	            	PreparedStatement stmt=conn.prepareStatement(sql.toString());
	            	ResultSet rs=stmt.executeQuery();
	            	
	            	while(rs.next()){
	    				bean=new UserBean();
	    				bean.setId(rs.getLong(1));
	    				bean.setFirstName(rs.getString(2));
	    				bean.setLastName(rs.getString(3));
	    				bean.setLogin(rs.getString(4));
	    				bean.setPassword(rs.getString(5));
	    				bean.setDob(rs.getDate(6));
	    				bean.setMobileNo(rs.getString(7));
	    				bean.setRoleId(rs.getLong(8));
	    				bean.setUnSuccessfulLogin(rs.getInt(9));
	    				bean.setGender(rs.getString(10));
	    				bean.setLastLogin(rs.getTimestamp(11));
	    				bean.setLock(rs.getString(12));
	    				bean.setRegisteredIp(rs.getString(13));
	    				bean.setLastLoginIp(rs.getString(14));
	    				bean.setCreatedBy(rs.getString(15));
	    				bean.setModifiedBy(rs.getString(16));
	    				bean.setCreatedDateTime(rs.getTimestamp(17));
	    				bean.setModifiedDateTime(rs.getTimestamp(18));
	    				
	    				list.add(bean);
	    				
	    			}rs.close();
	    		}catch(Exception e){
	    	            log.error("Database Exception..", e);
	    	            throw new ApplicationException("Exception : Exception in getting User by pk");
	    	        } finally {
	    	            JDBCDataSource.closeConnection(conn);
	    	        }
	    	
	            log.debug("Model search End");
	            return list;
	            	
	 }
	 public List list() throws ApplicationException {
	        return list(0, 0);
	    }
	 
	 /**
		 * list of user
		 * 
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
	 public static List list(int pageNo,int pageSize) throws ApplicationException{
		
		 log.debug("Model list started");
		 ArrayList list=new ArrayList();
		 StringBuffer sql =new StringBuffer("Select * from st_user ");
		 
		 if(pageSize>0){
			 pageNo=(pageNo-1)*pageSize;
			 sql.append(" limit "+pageNo+","+pageSize);
		 }
		 
		 
		 Connection conn=null;
		 try{
			 conn=JDBCDataSource.getConnection();
			 PreparedStatement stmt=conn.prepareStatement(sql.toString());
			 ResultSet rs=stmt.executeQuery();
			 
			 while(rs.next()){
				 
				UserBean bean=new UserBean();
 				bean.setId(rs.getLong(1));
 				bean.setFirstName(rs.getString(2));
 				bean.setLastName(rs.getString(3));
 				bean.setLogin(rs.getString(4));
 				bean.setPassword(rs.getString(5));
 				bean.setDob(rs.getDate(6));
 				bean.setMobileNo(rs.getString(7));
 				bean.setRoleId(rs.getLong(8));
 				bean.setUnSuccessfulLogin(rs.getInt(9));
 				bean.setGender(rs.getString(10));
 				bean.setLastLogin(rs.getTimestamp(11));
 				bean.setLock(rs.getString(12));
 				bean.setRegisteredIp(rs.getString(13));
 				bean.setLastLoginIp(rs.getString(14));
 				bean.setCreatedBy(rs.getString(15));
 				bean.setModifiedBy(rs.getString(16));
 				bean.setCreatedDateTime(rs.getTimestamp(17));
 				bean.setModifiedDateTime(rs.getTimestamp(18));
 				
 				list.add(bean);
 				
 			}rs.close();
 		}catch(Exception e){
 	            log.error("Database Exception..", e);
 	            throw new ApplicationException("Exception : Exception in getting User");
 	        } finally {
 	            JDBCDataSource.closeConnection(conn);
 	        }
 	
         log.debug("Model list End");
         return list;
     
		  
	 }
	 /**
		 * authenticate user
		 * 
		 * @param login
		 * @param password
		 * @return bean
		 * @throws ApplicationException
		 */
	 public UserBean authenticate(String login,String password) throws ApplicationException{
		 System.out.println("User model authenticate() run");
		 log.debug("Model authenticate Started");
		 Connection conn=null;
		 StringBuffer sql=new StringBuffer("select * from st_user where login=? and password=?");
		 UserBean bean=null;
		 
		 try{
			 conn=JDBCDataSource.getConnection();
			 PreparedStatement stmt=conn.prepareStatement(sql.toString());
			 stmt.setString(1, login);
			 stmt.setString(2, password);
			 ResultSet rs=stmt.executeQuery();
			 
			 	while(rs.next()){
				 	bean=new UserBean();
				  	bean.setId(rs.getLong(1));
				 	bean.setFirstName(rs.getString(2));
	 				bean.setLastName(rs.getString(3));
	 				bean.setLogin(rs.getString(4));
	 				bean.setPassword(rs.getString(5));
	 				bean.setDob(rs.getDate(6));
	 				bean.setMobileNo(rs.getString(7));
	 				bean.setRoleId(rs.getLong(8));
	 				bean.setUnSuccessfulLogin(rs.getInt(9));
	 				bean.setGender(rs.getString(10));
	 				bean.setLastLogin(rs.getTimestamp(11));
	 				bean.setLock(rs.getString(12));
	 				bean.setRegisteredIp(rs.getString(13));
	 				bean.setLastLoginIp(rs.getString(14));
	 				bean.setCreatedBy(rs.getString(15));
	 				bean.setModifiedBy(rs.getString(16));
	 				bean.setCreatedDateTime(rs.getTimestamp(17));
	 				bean.setModifiedDateTime(rs.getTimestamp(18));

			 }
		 }catch(Exception e){
			 log.error("Database Exception",e);
			 throw new ApplicationException("Exception:Exception in getting role");
			 
		 }finally{
			 JDBCDataSource.closeConnection(conn);
		 }
		 log.debug("Model authenticate End");
		 return bean;
	 }
	 
	 
	 /**
		 * lock time table
		 * 
		 * @param login
		 * @return true or false
		 * @throws ApplicationException,RecordNotFoundException
		 */
	 public boolean lock(String login) throws RecordNotFoundException, ApplicationException{
		 
		 log.debug("Model lock started");
		 boolean flag=false;
		 UserBean beanExist=null;
		 try{
			 beanExist=findByLogin(login);
			 if(beanExist!= null){
				 beanExist.setLock(UserBean.ACTIVE);
				 update(beanExist);
				 flag=true;
			 }else{
				 throw new RecordNotFoundException("Login Id not exist");
			 }
		 }catch(DuplicateRecordException e){
			 log.error("Application Exception ", e);
	            throw new ApplicationException("Database Exception");

		 }
		 log.debug("Service lock end");
		 return flag;
	 }
	 /**
		 * get role
		 * 
		 * @param bean
		 * @return list
		 * @throws ApplicationException
		 */
	 public List getRoles(UserBean bean) throws ApplicationException{
		 log.debug("Model get Roles Started");
		 
		 
		 StringBuffer sql = new StringBuffer("select * from st_user where role_id=?");
	        Connection conn = null;
	        List list = new ArrayList();
	        try {

	            conn = JDBCDataSource.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(sql.toString());
	            stmt.setLong(1, bean.getRoleId());
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                bean = new UserBean();
	                bean.setId(rs.getLong(1));
	                bean.setFirstName(rs.getString(2));
	                bean.setLastName(rs.getString(3));
	                bean.setLogin(rs.getString(4));
	                bean.setPassword(rs.getString(5));
	                bean.setDob(rs.getDate(6));
	                bean.setMobileNo(rs.getString(7));
	                bean.setRoleId(rs.getLong(8));
	                bean.setUnSuccessfulLogin(rs.getInt(9));
	                bean.setGender(rs.getString(10));
	                bean.setLastLogin(rs.getTimestamp(11));
	                bean.setLock(rs.getString(12));
	                bean.setRegisteredIp(rs.getString(13));
	                bean.setLastLoginIp(rs.getString(14));
	                bean.setCreatedBy(rs.getString(15));
	                bean.setModifiedBy(rs.getString(16));
	                bean.setCreatedDateTime(rs.getTimestamp(17));
	                bean.setModifiedDateTime(rs.getTimestamp(18));

	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException("Exception : Exception in get roles");

	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model get roles End");
	        return list;
	    }

	 /**
		 * change password
		 * 
		 * @param id
		 * @param oldPassword
		 * @param newPassword
		 * @return true and false
		 * @throws RecordNotFoundException
		 * @throws ApplicationException
		 */   
	 public static boolean changePassword(long id, String oldPassword,String newPassword) throws RecordNotFoundException,ApplicationException {

	        log.debug("model changePassword Started");
	        boolean flag = false;
	        UserBean beanExist = null;

	        beanExist = findByPK(id)
;
	        System.out.println(beanExist);
	        if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
	        	System.out.println("in if con");
	            beanExist.setPassword(newPassword);
	            try {
	                update(beanExist);
	            } catch (DuplicateRecordException e) {
	                log.error(e);
	                throw new ApplicationException("LoginId is already exist");
	            }
	            
	            flag = true;
	            
	        	} else {
	            throw new RecordNotFoundException("Login not exist");
	        	}

	        HashMap<String, String> map = new HashMap<String, String>();

	        map.put("login", beanExist.getLogin());
	        map.put("password", beanExist.getPassword());
	        map.put("firstName", beanExist.getFirstName());
	        map.put("lastName", beanExist.getLastName());

	        String message = EmailBuilder.getChangePasswordMessage(map);

	        EmailMessage msg = new EmailMessage();

	        msg.setTo(beanExist.getLogin());
	        msg.setSubject("Rays Ors Password has been changed Successfully.");
	        msg.setMessage(message);
	        msg.setMessageType(EmailMessage.HTML_MSG);

	        EmailUtility.sendMail(msg);

	        log.debug("Model changePassword End");
	        return flag;

	    }


	    public UserBean updateAccess(UserBean bean) throws ApplicationException {
	        return null;
	    }

	    /**
		 * register user
		 * 
		 * @param bean
		 * @return pk
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
	    public long registerUser(UserBean bean) throws ApplicationException,DuplicateRecordException {

	        log.debug("Model add Started");

	        System.out.println("888888888888888888888888888888888888888 registeruser of usermodel");
	        long pk = add(bean);
	        System.out.println("999999999999999999999999999999999999999 registeruser of usermodel");

	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("login", bean.getLogin());
	        map.put("password", bean.getPassword());

	        System.out.println("77777777777777777777777777777777777 registeruser of usermodel");
	        String message = EmailBuilder.getUserRegistrationMessage(map);

	        EmailMessage msg = new EmailMessage();
	        
	        System.out.println("00000000000000000000000000000000000 registeruser of usermodel");
	        msg.setTo(bean.getLogin());
	        System.out.println("yyyyyyyyyyyyyyyyy");
	        msg.setSubject("Registration is successfull for Ors Project SunilOS");
	        msg.setMessage(message);
	        msg.setMessageType(EmailMessage.HTML_MSG);
	        System.out.println("zzzzzzzzzzzzzzzzzzzz registeruser of usermodel");
	        EmailUtility.sendMail(msg);
	        System.out.println("11111111111111111111111111111111111111 registeruser of usermodel");
	        return pk;
	    }
	  
	    public static boolean resetPassword(UserBean bean) {
			return false;
		}

	
  
	    /**
		 * forgetpassword
		 * 
		 * @param login
		 * @return true and false
		 * @throws RecordNotFoundException
		 * @throws ApplicationException
		 */
	    public static boolean forgetPassword(String login) throws ApplicationException,RecordNotFoundException {
	        
	    	UserBean userData = new UserBean();
	    	
	    	userData = findByLogin(login);
	        boolean flag = false;

	        if (userData == null) {
	            throw new RecordNotFoundException("Email ID does not exists!");

	        }

	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("login", userData.getLogin());
	        map.put("password", userData.getPassword());
	        map.put("firstName", userData.getFirstName());
	        map.put("lastName", userData.getLastName());
	        String message = EmailBuilder.getForgetPasswordMessage(map);
	        EmailMessage msg = new EmailMessage();
	        msg.setTo(login);
	        msg.setSubject("Password reset");
	        msg.setMessage(message);
	        msg.setMessageType(EmailMessage.HTML_MSG);
	        EmailUtility.sendMail(msg);
	        flag = true;

	        return flag;
	    }
  }