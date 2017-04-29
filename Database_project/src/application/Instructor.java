package application;

public class Instructor {
	String id, fname, lanme, sex, dob, employment, email, password, office, depcode;
	double salary;

	public Instructor(String id, String fname, String lanme, String sex, String dob, double salary, String employment,
			String email, String password, String office, String depcode) {
		super();
		this.id = id;
		this.fname = fname;
		this.lanme = lanme;
		this.sex = sex;
		this.dob = dob;
		this.salary = salary;
		this.employment = employment;
		this.email = email;
		this.password = password;
		this.office = office;
		this.depcode = depcode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLanme() {
		return lanme;
	}

	public void setLanme(String lanme) {
		this.lanme = lanme;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDepcode() {
		return depcode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", fname=" + fname + ", lanme=" + lanme + ", sex=" + sex + ", dob=" + dob
				+ ", salary=" + salary + ", employment=" + employment + ", email=" + email + ", password=" + password
				+ ", office=" + office + ", depcode=" + depcode + "]";
	}
	
	

}
