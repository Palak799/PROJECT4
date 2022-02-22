package in.co.sunrays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import org.apache.log4j.Logger;

import in.co.sunrays.bean.CourseBean;
import in.co.sunrays.bean.SubjectBean;
import in.co.sunrays.bean.TimeTableBean;
import in.co.sunrays.exception.ApplicationException;
import in.co.sunrays.exception.DatabaseException;
import in.co.sunrays.exception.DuplicateRecordException;
import in.co.sunrays.util.DataUtility;
import in.co.sunrays.util.JDBCDataSource;
/**
 * JDBC Implements of timetable
 * @author  priyal
 *
 */
public class TimeTableModel {
	private static Logger log = Logger.getLogger(TimeTableModel.class);

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement("Select max(id) from st_timetable");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception:Exception is getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * add timetable
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 */
	public long add(TimeTableBean bean) throws Exception {

		System.out.println("ggggggggggggggggggggggggggggg" + bean.getCourseId() + "" + bean.getCourseName() + ""
				+ bean.getSubjectId() + "" + bean.getSubjectName());
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		java.util.Date d = bean.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);

		// get course Name and Subject Name by id
		CourseModel Cmodel = new CourseModel();
		CourseBean Cbean = null;
		Cbean = Cmodel.findByPK(bean.getCourseId());
		bean.setCourseName(Cbean.getCourseName());

		SubjectModel Smodel = new SubjectModel();
		SubjectBean Sbean = Smodel.findByPk(bean.getSubjectId());
		bean.setSubjectName(Sbean.getSubjectName());

		TimeTableBean duplicateSubName = findByName(bean.getSubjectName());

		if (duplicateSubName != null) {
			throw new DuplicateRecordException("SubName Already Exists");

		}
		try {
			conn = JDBCDataSource.getConnection();

			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn
					.prepareStatement("Insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

			stmt.setLong(1, pk);
			stmt.setString(2, bean.getCourseName());
			stmt.setLong(3, bean.getCourseId());
			stmt.setString(4, bean.getSubjectName());
			stmt.setLong(5, bean.getSubjectId());
			stmt.setDate(6, date);
			stmt.setString(7, bean.getExamTime());
			stmt.setString(8, bean.getSemester());
			stmt.setString(9, bean.getDescription());
			stmt.setString(10, bean.getCreatedBy());
			stmt.setString(11, bean.getModifiedBy());
			stmt.setTimestamp(12, bean.getCreatedDateTime());
			stmt.setTimestamp(13, bean.getModifiedDateTime());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;

	}

	/**
	 * delete timetable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model Delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_timetable where id=?");
			stmt.setLong(1, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete RollBack Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Model delete end");

	}

	public TimeTableBean findByName(String subname) throws ApplicationException {
		log.debug("Model FindByName Started ");
		StringBuffer sql = new StringBuffer("Select * from st_timetable where subJECT_name=?");
		System.out.println("Model FindByName Started");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, subname);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting TimeTable by SubName");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		System.out.println("Model  findByName Ended");
		return bean;
	}

	/**
	 * find time table by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");

		StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");

		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception:Exception in getting TimeTable by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return bean;

	}

	/**
	 * update timetable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void update(TimeTableBean bean) throws DuplicateRecordException, ApplicationException {

		java.util.Date d = bean.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);

		log.debug("Model update started");
		System.out.println("Model update started");
		Connection conn = null;

		TimeTableBean beanExist = findByName(bean.getSubjectName());

		/*
		 * if(beanExist!=null&&beanExist.getId()!=bean.getId()){ throw new
		 * DuplicateRecordException("SubName is already exist");}
		 */

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(
					"Update st_timetable set Course_Name=?,Course_Id=?,Subject_name=?,subject_id,Exam_Date=?,Exam_Time=?,Semester=?,DESCRIPTION =?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			System.out.println("Model update gooooooo");

			stmt.setString(1, bean.getCourseName());
			stmt.setLong(2, bean.getCourseId());
			stmt.setString(3, bean.getSubjectName());
			stmt.setLong(4, bean.getSubjectId());
			stmt.setDate(5, date);
			stmt.setString(6, bean.getExamTime());
			stmt.setString(7, bean.getSemester());
			stmt.setString(8, bean.getDescription());
			stmt.setString(9, bean.getCreatedBy());
			stmt.setString(10, bean.getModifiedBy());
			stmt.setTimestamp(11, bean.getCreatedDateTime());
			stmt.setTimestamp(12, bean.getModifiedDateTime());
			stmt.setLong(13, bean.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
		System.out.println("Model update End");
	}

	/**
	 * search time table
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TimeTable WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getSubjectId() != 0 && bean.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID like '" + bean.getSubjectId() + "%'");
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getCourseId() != 0 && bean.getCourseId() > 0) {
				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzz    " + bean.getCourseId());
				sql.append(" AND COURSE_ID = " + bean.getCourseId());

			}
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + bean.getSemester() + "%'");
			}

			if ((bean.getExamDate() != null) && (bean.getExamDate().getDate() > 0)) {
				Date date = new Date(bean.getExamDate().getTime());
				System.out.println(">>>>" + date);
				sql.append(" AND EXAM_DATE = '" + date + "'");
			}
			if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
				sql.append(" AND Exam_Time like '" + bean.getExamTime() + "%'");
			}
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public List search(TimeTableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * list of time table
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from St_TimeTable");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TimeTableBean bean = new TimeTableBean();
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	/**
	 * @param CourseId
	 * @param ExamDate
	 * @return tbean
	 * @throws ApplicationException
	 */
	public static TimeTableBean checkByCourseName(long CourseId, java.util.Date ExamDate) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		System.out.println("jjjj" + CourseId + ",,," + ExamDate);
		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, Exdate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseName(rs.getString(2));
				tbean.setCourseId(rs.getLong(3));
				tbean.setSubjectName(rs.getString(4));
				tbean.setSubjectId(rs.getLong(5));
				tbean.setExamDate(rs.getDate(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setSemester(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByCourseName..." + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param ExamDAte
	 * @return tbean
	 * @throws ApplicationException
	 */
	public static TimeTableBean checkBySubjectName(long CourseId, long SubjectId, java.util.Date ExamDAte)
			throws ApplicationException {
		System.out.println("jjjj" + CourseId + "kj" + SubjectId + ",,," + ExamDAte);
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setDate(3, ExDate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseName(rs.getString(2));
				tbean.setCourseId(rs.getLong(3));
				tbean.setSubjectName(rs.getString(4));
				tbean.setSubjectId(rs.getLong(5));
				tbean.setExamDate(rs.getDate(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setSemester(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * 
	 * 
	 */
	public static TimeTableBean checkBysemester(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte)
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		System.out.println("jjkkk" + CourseId + "jjj" + SubjectId + "kk" + semester + "kk" + ExamDAte);
		Date ExDate = new Date(ExamDAte.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBject_ID=? AND" + " EXAM_DATE=? AND SEMESTER=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseName(rs.getString(2));
				tbean.setCourseId(rs.getLong(3));
				tbean.setSubjectName(rs.getString(4));
				tbean.setSubjectId(rs.getLong(5));
				tbean.setExamDate(rs.getDate(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setSemester(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
		}
		return tbean;
	}

	/**
	 * @param ExamTime
	 * @param CourseId
	 * @param SubjectId
	 * @param semester
	 * @param ExamDAte
	 * @return tbean
	 * @throws ApplicationException
	 */
	public static TimeTableBean checkByExamTime(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte,
			String ExamTime) throws ApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		TimeTableBean tbean = null;
		Date ExDate = new Date(ExamDAte.getTime());
		StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUBject_ID=? AND"
				+ "  AND EXAM_DATE=? AND EXAM_TIME=? AND SEMESTER=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			ps.setString(5, ExamTime);
			rs = ps.executeQuery();

			while (rs.next()) {
				tbean = new TimeTableBean();
				tbean.setId(rs.getLong(1));
				tbean.setCourseName(rs.getString(2));
				tbean.setCourseId(rs.getLong(3));
				tbean.setSubjectName(rs.getString(4));
				tbean.setSubjectId(rs.getLong(5));
				tbean.setExamDate(rs.getDate(6));
				tbean.setExamTime(rs.getString(7));
				tbean.setSemester(rs.getString(8));
				tbean.setDescription(rs.getString(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByexamTime..." + e.getMessage());
		}
		return tbean;
	}

}
