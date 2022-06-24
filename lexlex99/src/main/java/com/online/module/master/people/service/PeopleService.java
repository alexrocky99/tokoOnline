package com.online.module.master.people.service;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.people.model.People;

public interface PeopleService extends RetrieverDataPage<People> {

	public People getUserLogin(String username, String password);
	public void save(People people);
	public void update(People people);
	public People findById(Long peopleId);
	public Number getNexVal();
	
}
