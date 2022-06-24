package com.online.module.transaction.resi.dao;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.transaction.resi.model.Resi;

public interface ResiDao extends GenericDao<Resi, Long>, RetrieverDataPage<Resi> {

}
