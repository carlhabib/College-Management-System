package application;

public class Student {
	String id, fname, lname, sex, dob, minor, email, password, program, advisor;
	double cgpa, majorgpa;

	public Student(String id, String fname, String lname, String sex, String dob, double cgpa, double majorgpa,
			String minor, String email, String password, String program, String advisor) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.sex = sex;
		this.dob = dob;
		this.cgpa = cgpa;
		this.majorgpa = majorgpa;
		this.minor = minor;
		this.email = email;
		this.password = password;
		this.program = program;
		this.advisor = advisor;
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

	public double getCgpa() {
		return cgpa;
	}

	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}

	public double getMajorgpa() {
		return majorgpa;
	}

	public void setMajorgpa(double majorgpa) {
		this.majorgpa = majorgpa;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
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

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getAdvisor() {
		return advisor;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", fname=" + fname + ", lname=" + lname + ", sex=" + sex + ", dob=" + dob
				+ ", cgpa=" + cgpa + ", majorgpa=" + majorgpa + ", minor=" + minor + ", email=" + email + ", password="
				+ password + ", program=" + program + ", advisor=" + advisor + "]";
	}
	

}
