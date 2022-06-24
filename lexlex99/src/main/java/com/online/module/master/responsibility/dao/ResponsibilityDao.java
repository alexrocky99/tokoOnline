package com.online.module.master.responsibility.dao;

import java.util.List;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.model.ResponsibilityDetail;

public interface ResponsibilityDao extends GenericDao<Responsibility, Long>, RetrieverDataPage<Responsibility> {

	public List<Responsibility> getAllResponsibility();

	public List<ResponsibilityDetail> searchResponsibilityAllMenu(ResponsibilityDetail responsibilityDetail);
	
	public void deleteInsertResponsibilityMenu(ResponsibilityDetail responsibilityDetail, String user);
	
}
