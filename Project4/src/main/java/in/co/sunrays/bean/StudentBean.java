package in.co.sunrays.bean;

import java.util.Date;
/**
 * student JavaBean encapsulates student attributes
 * @author Mayank
 *
 */
public class StudentBean extends BaseBean {
	/**
	 * name of student
	 */
private String firstName;
/**
 * last Name of student
 */
private String lastName;
/**
 * date of birth of student
 */
private Date dob;
/**
 * mobile no of student
 */
private String mobileNo;
/**
 * email id of student
 */
private String email;
/**
 * college id of student
 */
private long collegeId;

/**
 *accessor
 */
private String collegeName;
public String getFirstName() {
	return firstName;
}
/**
 * @param firstName
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public long getCollegeId() {
	return collegeId;
}
public void setCollegeId(long collegeId) {
	this.collegeId = collegeId;
}
public String getCollegeName() {
	return collegeName;
}
public void setCollegeName(String collegeName) {
	this.collegeName = collegeName;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	return firstName+""+lastName;
}
}
