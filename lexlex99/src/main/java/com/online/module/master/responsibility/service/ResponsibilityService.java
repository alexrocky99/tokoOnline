package com.online.module.master.responsibility.service;

import java.util.List;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.model.ResponsibilityDetail;

public interface ResponsibilityService extends RetrieverDataPage<Responsibility> {
	
	public List<Responsibility> getAllResponsibility();
	public void save(Responsibility responsibility);
	public void update(Responsibility responsibility);
	public Responsibility findById(Long responsibilityId);
	public List<ResponsibilityDetail> searchResponsibilityAllMenu(ResponsibilityDetail responsibilityDetail);
	public void deleteInsertResponsibilityMenu(ResponsibilityDetail responsibilityDetail, String user);

}
