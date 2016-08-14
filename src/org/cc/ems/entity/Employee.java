package org.cc.ems.entity;

/**
 * The base class.
 * 
 * @author cc
 *
 */
public abstract class Employee {

	/**编号**/
	private String id;
	/**姓名**/ 
	private String name;
	/**员工职务**/ 
	private String position;
	/**旷工天数**/ 
	private int absenteeism;
	/**基本工资**/ 
	private double salary;

	public Employee() {
	}
	
	public Employee(String id, String name, String position, int absenteeism, double salary) {
		setId(id);
		setName(name);
		setPosition(position);
		setAbsenteeism(absenteeism);
		setSalary(salary);
	}

	/**
	 * 计算最终工资
	 * @return
	 */
	public abstract double getFinallySalary();
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Employee){
			Employee e=(Employee) obj;
			return e.getId().equalsIgnoreCase(this.getId());
		}
		return false;
	}
	
	/*------------ getter and setter below ---------------------------*/
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getAbsenteeism() {
		return absenteeism;
	}

	public void setAbsenteeism(int absenteeism) {
		this.absenteeism = absenteeism;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
