package org.cc.ems.service;

import java.util.List;

import org.cc.ems.entity.Emploee;

/**
 * EmploeeService
 * @author cc
 *
 */
public interface EmployeeService {

	/**
	 * 增加一个雇员
	 * @param e
	 * @return
	 */
	public boolean add(Emploee e);
	
	/**
	 * 删除一个雇员
	 * @param e
	 * @return
	 */
	public boolean del(String id);
	
	/**
	 * 批量删除雇员
	 * @param eIds
	 * @return
	 */
	public boolean del(List<String> eIds);
	
	/**
	 * 修改雇员信息
	 * @param e
	 * @return
	 */
	public boolean modify(Emploee e);
	
	/**
	 * 加载一个雇员信息
	 * @param e
	 * @return
	 */
	public Emploee load(String id);

	/**
	 * 列出所有雇员
	 * @return
	 */
	public List<Emploee> list();
}
