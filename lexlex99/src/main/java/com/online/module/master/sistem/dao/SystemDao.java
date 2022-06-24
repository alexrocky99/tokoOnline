package com.online.module.master.sistem.dao;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.sistem.model.System;

public interface SystemDao extends GenericDao<System, Long>, RetrieverDataPage<System> {
	
}
