package com.online.module.transaction.resi.model;

import java.io.Serializable;

import com.online.module.common.model.BaseEntity;
import com.online.module.master.seller.model.Seller;
import com.online.module.transaction.penjualan.model.Penjualan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResiDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -256815659822697947L;
	
	private Long receiptDetailId;
	private Resi resi;
	private Penjualan penjualan;
	private Seller seller;
	private String receiptNumber;
	private String expedision;
	private Double shippingCosts;

}
