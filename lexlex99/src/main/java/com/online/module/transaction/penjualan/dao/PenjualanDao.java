package com.online.module.transaction.penjualan.dao;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.transaction.penjualan.model.Penjualan;

public interface PenjualanDao extends GenericDao<Penjualan, Long>, RetrieverDataPage<Penjualan> {

}
