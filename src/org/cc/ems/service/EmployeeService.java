package org.cc.ems.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.cc.ems.entity.Employee;
import org.cc.ems.util.XMLUtil;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * EmploeeService
 * @author cc
 *
 */
public class EmployeeService {

	private String DEFAULT_PATH="d:/employeeDB.xml";
	private List<Employee> list;
	private static EmployeeService instance;
	
	//删除回收站，以便进行撤销删除(undo del)
	private Stack<List<Employee>> trash=new Stack<List<Employee>>();
	
	private EmployeeService() {
		if(new File(DEFAULT_PATH).exists()){
			list=XMLUtil.getInstance().read(DEFAULT_PATH);
		}else{
			try {
				//如果读取不到默认的话，就从自己的jar包中拿一个备份的
				InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("org/cc/ems/resource/employeeDB.xml");
				BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
				list=XMLUtil.getInstance().read(reader);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
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
	public List<Employee> filter(String keyword){
		List<Employee> res=new ArrayList<Employee>();
		try {
			for(int i=0;i<list.size();i++){
				Employee e=list.get(i);
				//名字匹配或者拼音匹配也可以 使用了Jpinyin类库
				if(e.getName().indexOf(keyword)!=-1 || 
						PinyinHelper.convertToPinyinString(e.getName(),"",PinyinFormat.WITHOUT_TONE).indexOf(keyword)!=-1) res.add(e);
			}
		} catch (PinyinException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public String[][] list2Array(List<Employee> list){
		String data[][]=new String[list.size()][6];
		for(int i=0;i<list.size();i++){
			Employee e=list.get(i%list.size());
			data[i][0]=e.getId();
			data[i][1]=e.getName();
			data[i][2]=e.getPosition();
			data[i][3]=Integer.toString(e.getAbsenteeism());
			data[i][4]=String.format("￥%.2f",e.getSalary());
			data[i][5]=String.format("￥%.2f",e.getFinallySalary());
		}
		return data;
	}
	
	public String[] getHeaders(){
		return new String[]{"编号","姓名","职位","旷工（天）","基本工资","实发工资"};
	}

	public void save() {
		XMLUtil.getInstance().save(list,DEFAULT_PATH);
	}
	
}
