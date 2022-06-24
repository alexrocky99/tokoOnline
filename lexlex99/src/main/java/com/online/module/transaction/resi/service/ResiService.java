package com.online.module.transaction.resi.service;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.transaction.resi.model.Resi;

public interface ResiService extends RetrieverDataPage<Resi> {

	public void save(Resi resi);
	public void update(Resi resi);
	public void delete(Resi resi);
	public Resi findById(Long resiId);
	
}
