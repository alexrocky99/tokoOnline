package com.online.module.master.seller.model;

import java.io.Serializable;
import java.util.Date;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seller extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5008903812422856531L;
	
	private Long sellerId;
	private String sellerName;
	private Date effectiveStartDate;
	private Date effectiveEndDate;

}
