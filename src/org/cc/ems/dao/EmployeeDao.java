package org.cc.ems.dao;

import java.util.List;

import org.cc.ems.entity.Emploee;

/**
 * EmploeeDao
 * @author cc
 *
 */
public interface EmployeeDao {

	/**
	 * 添加
	 * @param e
	 * @return
	 */
	public boolean add(Emploee e);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean del(String id);
	
	/**
	 * 修改
	 * @param e
	 * @return
	 */
	public boolean modify(Emploee e);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Emploee load(String id);
	
	/**
	 * 列出所有雇员
	 * @return
	 */
	public List<Emploee> list();
}
