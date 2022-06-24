package com.online.module.transaction.resi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Resi extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4088779325508451090L;

	private Long receiptId;
	private Date receiptDate;
	private String paymentStatus;
	
	private List<ResiDetail> resiDetailList;
	
	private String paymentStatusCode;
	
}
