package application;

public class Staff {
	String staffid, fname, lname, sex, dob, employment, email, password, depid;
	int admin;
	double salary;

	public Staff(String staffid, String fname, String lname, String sex, String dob, double salary, String employment,
			String email, String password, int admin, String depid) {
		super();
		this.staffid = staffid;
		this.fname = fname;
		this.lname = lname;
		this.sex = sex;
		this.dob = dob;
		this.salary = salary;
		this.employment = employment;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.depid = depid;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	@Override
	public String toString() {
		return "Staff [staffid=" + staffid + ", fname=" + fname + ", lname=" + lname + ", sex=" + sex + ", dob=" + dob
				+ ", salary=" + salary + ", employment=" + employment + ", email=" + email + ", password=" + password
				+ ", admin=" + admin + ", depid=" + depid + "]";
	}
	
}
