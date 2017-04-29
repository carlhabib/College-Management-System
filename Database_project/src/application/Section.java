package application;

public class Section {

	private String crn, courseid, term, sched, startTime, endTime, taughtBy, room;
	int year, capacity, actual , remaining;

	public Section(String crn, String courseid, String term, int year, String sched, String startTime,
			String endTime, int capacity, int actual, int remaining, String taughtBy, String room) {
		super();
		this.crn = crn;
		this.courseid = courseid;
		this.term = term;
		this.year = year;
		this.sched = sched;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.actual = actual;
		this.remaining = remaining;
		this.taughtBy = taughtBy;
		this.room = room;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getSched() {
		return sched;
	}

	public void setSched(String sched) {
		this.sched = sched;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public String getTaughtBy() {
		return taughtBy;
	}

	public void setTaughtBy(String taughtBy) {
		this.taughtBy = taughtBy;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Section [crn=" + crn + ", courseid=" + courseid + ", term=" + term + ", year=" + year + ", sched="
				+ sched + ", startTime=" + startTime + ", endTime=" + endTime + ", capacity=" + capacity + ", actual="
				+ actual + ", remaining=" + remaining + ", taughtBy=" + taughtBy + ", room=" + room + "]";
	}

	
	
}



