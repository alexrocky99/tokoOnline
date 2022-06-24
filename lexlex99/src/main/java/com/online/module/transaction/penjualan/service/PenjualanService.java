package com.online.module.transaction.penjualan.service;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.transaction.penjualan.model.Penjualan;

public interface PenjualanService extends RetrieverDataPage<Penjualan> {

	public void save(Penjualan penjualan);
	public void update(Penjualan penjualan);
	public void delete(Penjualan penjualan);
	public Penjualan findById(Long penjualanId);
	
}
