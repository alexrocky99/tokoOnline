package com.online.module.master.product.model;

import java.io.Serializable;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImage extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3198469809870213178L;
	
	private Long productImageId;
	private Product product;
	private byte[] imageFile;
	private String imageName;

}
