package org.cc.ems.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.cc.ems.entity.Employee;
import org.cc.ems.util.XMLUtil;

/**
 * EmploeeService
 * @author cc
 *
 */
public class EmployeeService {

	private List<Employee> list=XMLUtil.getInstance().read("d:/employeeDB.xml");
	private static EmployeeService instance;
	
	//删除回收站，以便进行撤销删除(undo del)
	private Stack<List<Employee>> trash=new Stack<List<Employee>>();
	
	private EmployeeService() {
	}
	
	public synchronized static EmployeeService getInstance(){
		synchronized (EmployeeService.class) {
			if(instance==null) instance=new EmployeeService();
		}
		return instance;
	}
	
	public void add(Employee e){
		list.add(e);
	}
	
	public void del(String s){
		for(Iterator<Employee> iter=list.iterator();iter.hasNext();){
			Employee e=iter.next();
			if(e.getId().equals(s)){
				iter.remove();
				return;
			}
		}
	}
	
	public void modify(Employee e){
		for(int i=0;i<list.size();i++){
			if(e.getId().equals(list.get(i))){
				list.remove(i);
				list.add(e);
			}
		}
	}
	
	public Employee get(String e){
		for(int i=0;i<list.size();i++){
			if(e.equalsIgnoreCase(list.get(i).getId())) return list.get(i);
		}
		return null;
	}
	
	public List<Employee> list(){
		return list;
	}
	
	public String[][] toArrayData(){
		return list2Array(list);
	}
	
	//根据名字过滤
	public List<Employee> filter(String keywork){
		List<Employee> res=new ArrayList<Employee>();
		for(int i=0;i<list.size();i++){
			Employee e=list.get(i);
			if(e.getName().indexOf(keywork)!=-1) res.add(e);
		}
		return res;
	}
	
	public String[][] list2Array(List<Employee> list){
		String data[][]=new String[list.size()][5];
		for(int i=0;i<list.size();i++){
			Employee e=list.get(i%list.size());
			data[i][0]=e.getId();
			data[i][1]=e.getName();
			data[i][2]=e.getPosition();
			data[i][3]=Integer.toString(e.getAbsenteeism());
			data[i][4]=String.format("￥%.2f",e.getFinallySalary());
		}
		return data;
	}
	
	public String[] getHeaders(){
		return new String[]{"编号","姓名","职位","旷工","工资"};
	}

	public void save() {
		XMLUtil.getInstance().save(list,"d:/employeeDB.xml");
	}
	
}
