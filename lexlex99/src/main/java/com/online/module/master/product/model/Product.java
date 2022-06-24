package com.online.module.master.product.model;

import java.awt.Image;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5929093475563404404L;
	
	private Long productId;
	private String productCode;
	private String sku;
	private String productName;
	private String barcode;
	private String description;
	private Integer totalStock;
	private BigDecimal price;
	private String status;

	private List<ProductImage> productImageList = new ArrayList<ProductImage>();
	private List<ProductModal> productModalList = new ArrayList<ProductModal>();

	// helper
	private byte[] barcodeByte;
	private Image barcodeImage;
	
}
