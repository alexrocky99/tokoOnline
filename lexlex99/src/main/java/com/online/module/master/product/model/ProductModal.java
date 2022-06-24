package com.online.module.master.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModal extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8758213352448379856L;
	
	private Long productModalId;
	private Product product;
	private String productFrom;
	private BigDecimal currentPrice;
	private Integer currentStock;
	
	private List<ProductModalDetail> productModalDetailList = new ArrayList<ProductModalDetail>();

	// helper
	private BigDecimal updatePrice;
	private Integer updateStock;
	private Integer sequence;
	private Boolean isModalDetail = false;
	private String productCode;
	
}
