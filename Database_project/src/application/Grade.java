package application;

public class Grade {
	String courseID, title, credits, term, sectionyear, grade;

	

	public Grade(String courseID, String title, String credits, String term, String sectionyear, String grade) {
		super();
		this.courseID = courseID;
		this.title = title;
		this.credits = credits;
		this.term = term;
		this.sectionyear = sectionyear;
		this.grade = grade;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSectionyear() {
		return sectionyear;
	}

	public void setSectionyear(String sectionyear) {
		this.sectionyear = sectionyear;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {
		return "Grade [courseID=" + courseID + ", title=" + title + ", credits=" + credits + ", term=" + term
				+ ", sectionyear=" + sectionyear + ", grade=" + grade + "]";
	}
	

}
