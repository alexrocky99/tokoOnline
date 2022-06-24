package com.online.module.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnModel implements Serializable {

	private static final long serialVersionUID = 370767287027488798L;

	private int row;
	private String header;
	private String property;
	private List<ColumnModel> columnModels;
	
	public ColumnModel() {
	}
}
