package com.online.module.master.people.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.common.paging.SearchObject;
import com.online.module.master.people.dao.PeopleDao;
import com.online.module.master.people.model.People;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Transactional
@Service("peopleService")
public class PeopleServiceImpl implements PeopleService, Serializable {

	private static final long serialVersionUID = 7073792116465243922L;

	@Autowired
	@Qualifier("peopleDao")
	private PeopleDao peopleDao;
	
	@Override
	public People getUserLogin(String username, String password) {
		return peopleDao.getUserLogin(username, password);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<People> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return peopleDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return peopleDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(People people) {
		peopleDao.save(people);
	}

	@Override
	public void update(People people) {
		peopleDao.update(people);
	}

	@Override
	public People findById(Long peopleId) {
		return peopleDao.findById(peopleId);
	}

	@Override
	public Number getNexVal() {
		return peopleDao.getNexVal();
	}

}
