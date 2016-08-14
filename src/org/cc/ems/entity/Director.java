package org.cc.ems.entity;

/**
 * 董事
 * @author cc
 *
 */
public class Director extends Employee {
	
	private static String position="董事";
	private static double salary=5500;
	
	public Director() {
	}
	
	public Director(String id) {
		setId(id);
	}
	
	public Director(String id,String name,int absenteeism) {
		super(id,name,position,absenteeism,salary);
	}
	
	public Director(String id, String name, String position, int absenteeism, double salary){
		super(id,name,position,absenteeism,salary);
	}
	
	@Override
	public double getFinallySalary() {
		//在基本工资的基础上增加8%的工作餐，30%的岗位补助，2000元住房补助，3000元投资补助
		return getSalary()*1.38+2000+3000-salary/30*getAbsenteeism();
	}

}
