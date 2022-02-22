package in.co.sunrays.bean;
/**
 * marksheet JavaBean encapsulates marksheet attributes
 * @author Mayank
 *
 */
public class MarksheetBean extends BaseBean{

	/**
	 * rollNo of student
	 */
private String rollNo;
/**
 * Id of Studentd
 */
private long studentId;
/**
 * student name
 */
private String name;
/**
 * physics marks
 */
private Integer physics;
/**
 * chemistry
 */
private Integer chemistry;
/**
 * maths marks
 */
private Integer maths;
public String getRollNo() {
	return rollNo;
}
public void setRollNo(String rollNo) {
	this.rollNo = rollNo;
}
public long getStudentId() {
	return studentId;
}
public void setStudentId(long studentId) {
	this.studentId = studentId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getPhysics() {
	return physics;
}
public void setPhysics(Integer physics) {
	this.physics = physics;
}
public Integer getChemistry() {
	return chemistry;
}
public void setChemistry(Integer chemistry) {
	this.chemistry = chemistry;
}
public Integer getMaths() {
	return maths;
}
public void setMaths(Integer maths) {
	this.maths = maths;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	return rollNo;
}
}
