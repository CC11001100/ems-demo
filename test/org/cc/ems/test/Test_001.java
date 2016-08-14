package org.cc.ems.test;

import java.util.List;

import org.cc.ems.entity.CommonEmployee;
import org.cc.ems.entity.Employee;
import org.cc.ems.service.EmployeeService;
import org.cc.ems.util.XMLUtil;
import org.junit.Ignore;
import org.junit.Test;

public class Test_001 {

	@Ignore
	@Test
	public void test_001(){
		
//		Employee e1=new CommonEmployee("A1001","张三",0);
//		
//		EmployeeService service=new EmployeeServiceImpl();
//		service.add(e1);
//		
//		System.out.println(service.load("A1001").getName());
		
	}
	
	@Ignore
	@Test
	public void test_002(){
		
		List<Employee> list=XMLUtil.getInstance().read("d:/emploeeDB.xml");
		System.out.println(list.size());
		
		XMLUtil.getInstance().save(list,"d:/emploeeDB.xml");
		
		
	}
	
	
}
