package com.online.module.transaction.penjualan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Penjualan extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6115309792048435023L;
	
	private Long penjualanId;
	private String invoice;
	private Date transactionDate;
	private String platform;
	private String expedision;
	private String receiptNumber;
	private String status;
	private String typePenjualan;
	private String customerName;
	
	private List<PenjualanDetail> penjualanDetailList = new ArrayList<PenjualanDetail>();
	
}