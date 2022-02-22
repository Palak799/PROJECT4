package in.co.sunrays.bean;

import java.sql.Timestamp;
import java.util.Date;
/**
 * user JavaBean encapsulates user attributes
 * @author priyal
 *
 */
public class UserBean extends BaseBean {
public static final String ACTIVE="Active";
public static final  String INACTIVE="Inactive";
/**
 * first name of user
 */
private String firstName;
/**
 * last name of user
 */
private String lastName;
/**
 * login id name of user
 */
private String login;
/**
 * password of user
 */
private String password;
/**
 *  password of user
 */
private String newpassword;
/**
 * confirm password of user
 */
private String confirmPassword;
/**
 * date of birth of user
 */
private Date dob;
/**
 * mobile no of user
 */
private String mobileNo;
/**
 * role id of user
 */
private long roleId;
/**
 * unsuccessfull login of user
 */
private int unSuccessfulLogin;
/**
 * gender of user
 */
private String gender;
/**
 * last login of user
 */
private Timestamp lastLogin;
/**
 * lock
 */
private String lock = INACTIVE;
/**
 * user register ip
 */
private String registeredIp;
/**
 * user last login ip
 */
private String lastLoginIp;
/**
 * accessor
 */
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
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
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
public long getRoleId() {
	return roleId;
}
public void setRoleId(long roleId) {
	this.roleId = roleId;
}
public int getUnSuccessfulLogin() {
	return unSuccessfulLogin;
}
public void setUnSuccessfulLogin(int unSuccessfulLogin) {
	this.unSuccessfulLogin = unSuccessfulLogin;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public Timestamp getLastLogin() {
	return lastLogin;
}
public void setLastLogin(Timestamp lastLogin) {
	this.lastLogin = lastLogin;
}
public String getLock() {
	return lock;
}
public void setLock(String lock) {
	this.lock = lock;
}
public String getRegisteredIp() {
	return registeredIp;
}
public void setRegisteredIp(String registeredIp) {
	this.registeredIp = registeredIp;
}
public String getLastLoginIp() {
	return lastLoginIp;
}
public void setLastLoginIp(String lastLoginIp) {
	this.lastLoginIp = lastLoginIp;
}
public String getNewpassword() {
	return newpassword;
}
public void setNewpassword(String newpassword) {
	this.newpassword = newpassword;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	return firstName +""+lastName;
}
}
