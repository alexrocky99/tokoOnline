package com.online.module.common.util;

import java.sql.Timestamp;
import java.util.Date;

import com.online.module.common.constant.Constants;
import com.online.module.common.model.BaseEntity;

public class EntityUtil {
	
	public static <E extends BaseEntity> void setCreationInfo(E e, String createBy) {
		e.setCreateBy(createBy);
		e.setCreateOn(new Timestamp(new Date().getTime()));
		e.setRecordFlag(Constants.CONSTANT_YES);
	}
	
	public static <E extends BaseEntity> void setUpdateInfo(E e, String createBy) {
		e.setUpdateBy(createBy);
		e.setUpdateOn(new Timestamp(new Date().getTime()));
		e.setRecordFlag(Constants.CONSTANT_YES);
	}
	
	public static <E extends BaseEntity> void setUpdateDeleteWithFlag(E e, String createBy) {
		e.setUpdateBy(createBy);
		e.setUpdateOn(new Timestamp(new Date().getTime()));
		e.setRecordFlag(Constants.CONSTANT_NO);
	}
	
	public static <E extends BaseEntity> void setUpdatePenjualan(E e) {
		e.setUpdateBy(Constants.TRANSACTION);
		e.setUpdateOn(new Timestamp(new Date().getTime()));
		e.setRecordFlag(Constants.CONSTANT_YES);
	}

}
