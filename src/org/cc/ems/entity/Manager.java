package org.cc.ems.entity;

/**
 * 经理
 * @author cc
 *
 */
public class Manager extends Employee {
	
	private static String position="经理";
	private static double salary=3500;
	
	public Manager() {
	}
	
	public Manager(String id) {
		setId(id);
	}
	
	public Manager(String id,String name,int absenteeism) {
		super(id,name,position,absenteeism,salary);
	}
	
	public Manager(String id, String name, String position, int absenteeism, double salary){
		super(id,name,position,absenteeism,salary);
	}
	
	@Override
	public double getFinallySalary() {
		//在基本工资的基础上增加20%的工作餐，50%的岗位补助，500元住房补助
		return getSalary()*1.7+500;
	}

}
