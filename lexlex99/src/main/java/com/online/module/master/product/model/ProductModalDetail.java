package com.online.module.master.product.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModalDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4977817134182987944L;
	
	private Long productModalDetailId;
	private ProductModal productModal;
	private BigDecimal price;
	private Integer stock;
	
	// helper
	private String createOnStr;

}
