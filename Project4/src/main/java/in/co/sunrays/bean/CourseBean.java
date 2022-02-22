package in.co.sunrays.bean;
/**
 * course JavaBean encapsulates course attributes
 * @author priyal
 *
 */
public class CourseBean extends BaseBean {
	private String courseName;

    /**
     * Description of Course
     */
	private String description;

    /**
     * Duration of Course
     */
	
	private String duration;
	

    /**
     * Getter and Setter of Course
     */
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}


    public String getKey() {
        return id+"";
    }

   
    public String getValue() {
        return courseName;
    }
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
