package org.cc.ems.entity;

/**
 * 普通员工
 * @author cc
 *
 */
public class CommonEmployee extends Employee {

	private static String position="普通员工";
	
	public CommonEmployee() {
	}
	
	public CommonEmployee(String id) {
		setId(id);
	}
	
	public CommonEmployee(String id,String name,int absenteeism,double salary) {
		super(id,name,position,absenteeism,salary);
	}

	public CommonEmployee(String id, String name, String position, int absenteeism, double salary){
		super(id,name,position,absenteeism,salary);
	}
	
	@Override
	public double getFinallySalary() {
		//在基本工资的基础上增加10%的工作餐，50%的岗位补助，200元住房补助
		return getSalary()*1.6+200-getSalary()/30*getAbsenteeism();
	}

}
