package org.cc.ems.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.cc.ems.entity.Emploee;
import org.cc.ems.persistence.MemoryDB;

/**
 * EmploeeDao的实现,将东西写到MemoryDB
 * @author cc
 *
 */
public class EmployeeDaoImpl4MemoryDB implements EmployeeDao {

	private MemoryDB<String,Emploee> db=new MemoryDB<String,Emploee>();

	@Override
	public boolean add(Emploee e) {
		db.getDb().put(e.getId(),e);
		return true;
	}

	@Override
	public boolean del(String id) {
		db.getDb().remove(id);
		return true;
	}

	@Override
	public boolean modify(Emploee e) {
		db.getDb().put(e.getId(),e);
		return true;
	}

	@Override
	public Emploee load(String id) {
		return db.getDb().get(id);
	}

	@Override
	public List<Emploee> list() {
		return new ArrayList<Emploee>(db.getDb().values());
	}
	
}
