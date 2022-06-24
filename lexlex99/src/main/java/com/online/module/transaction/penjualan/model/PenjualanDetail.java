package com.online.module.transaction.penjualan.model;

import java.io.Serializable;

import com.online.module.common.model.BaseEntity;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenjualanDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2742047327592203391L;

	private Long penjualanDetailId;
	private Penjualan penjualan;
	private Product product;
	private String isExtraBubbleWrap;
	private Long totalBuying;
	private String isRefund;
	private Double sellingPrice;
	private ProductModal productModal;
	private Double margin;
	
	// helper
	private Integer sequence;
	private String namaProduct;
	private String changeData;
	private Product productTemp;
	private Long totalBuyingTemp;
}
