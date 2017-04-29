package application;

public class Course {
	private String courseid, title, level;
	private int credits;
	public Course(String courseid, int credits, String title, String level) {
		super();
		this.courseid = courseid;
		this.title = title;
		this.level = level;
		this.credits = credits;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	@Override
	public String toString() {
		return "Course [courseid=" + courseid + ", credits=" + credits + ", title=" + title + ", level=" + level + "]";
	}
	
	

}
