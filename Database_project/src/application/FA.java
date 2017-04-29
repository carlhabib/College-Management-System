package application;

public class FA {
	String id, manager, fn, ln, term, year, percentage, salary, location;

	public FA(String id, String manager, String fn, String ln, String term, String year, String percentage,
			String salary, String location) {
		super();
		this.id = id;
		this.manager = manager;
		this.fn = fn;
		this.ln = ln;
		this.term = term;
		this.year = year;
		this.percentage = percentage;
		this.salary = salary;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public String getLn() {
		return ln;
	}

	public void setLn(String ln) {
		this.ln = ln;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "FA [id=" + id + ", manager=" + manager + ", fn=" + fn + ", ln=" + ln + ", term=" + term + ", year="
				+ year + ", percentage=" + percentage + ", salary=" + salary + ", location=" + location + "]";
	}
	

}
