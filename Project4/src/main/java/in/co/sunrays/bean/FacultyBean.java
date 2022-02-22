package in.co.sunrays.bean;

import java.util.Date;

public class FacultyBean extends BaseBean{
	private String firstName;

    /**
     * Last Name of Faculty
     */
	private String lastName;

    /**
     * Gender of Faculty
     */
	private String Gender;

    /**
     * Login ID of Faculty
     */
	private String loginId;
	
	/**
     * Date of Joining of Faculty
     */

	private Date dateofjoining;

    /**
     * Qualification of Faculty
     */
	private String qualification;


    /**
     * Mobile No of Faculty
     */
	private String mobileno;

    /**
     * CollegeID of Faculty
     */
	private long collegeid;

    /**
     * College Name of Faculty
     */
	private String collegeName;

    /**
     * CourseID of Faculty
     */
	private long courseId;

    /**
     * Course Name of Faculty
     */
	private String courseName;

    /**
     * SubjectID of Faculty
     */
	private long subjectId;

    /**
     * Subject Name of Faculty
     */
	private String subjectName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getDateofjoining() {
		return dateofjoining;
	}

	public void setDateofjoining(Date dateofjoining) {
		this.dateofjoining = dateofjoining;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public long getCollegeid() {
		return collegeid;
	}

	public void setCollegeid(long collegeid) {
		this.collegeid = collegeid;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+""+lastName;
	}
	
   

}
