package org.cc.ems.service;

import java.util.List;

import org.cc.ems.dao.EmployeeDao;
import org.cc.ems.dao.EmployeeDaoImpl4MemoryDB;
import org.cc.ems.entity.Emploee;

/**
 * 实现类
 * @author cc
 *
 */
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao dao;
	
	public EmployeeServiceImpl() {
		dao=new EmployeeDaoImpl4MemoryDB();
	}
	
	@Override
	public boolean add(Emploee e) {
		return dao.add(e);
	}

	@Override
	public boolean del(String id) {
		return dao.del(id);
	}

	@Override
	public boolean del(List<String> eIds) {
		for(String id:eIds){
			if(!dao.del(id)) return false;
		}
		return true;
	}

	@Override
	public boolean modify(Emploee e) {
		return dao.add(e);
	}

	@Override
	public Emploee load(String id) {
		return dao.load(id);
	}

	@Override
	public List<Emploee> list() {
		return dao.list();
	}


}
