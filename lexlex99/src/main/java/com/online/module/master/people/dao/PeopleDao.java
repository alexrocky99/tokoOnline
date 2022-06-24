package com.online.module.master.people.dao;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.people.model.People;

public interface PeopleDao extends GenericDao<People, Long>, RetrieverDataPage<People> {

	public People getUserLogin(String username, String password);
	
	public Number getNexVal();
	
}
