package com.online.module.master.product.tableModel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.online.module.master.product.model.ProductModal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModalTableModel<E> extends ListDataModel<ProductModal> implements SelectableDataModel<ProductModal> {

	public ProductModalTableModel(List<ProductModal> data) {
		super(data);
	}
	
	@Override
	public String getRowKey(ProductModal object) {
		return object.getSequence().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductModal getRowData(String rowKey) {
		List<ProductModal> list = (List<ProductModal>) getWrappedData();
		
		for (ProductModal productModal : list) {
			if (productModal.getSequence() == new Integer(rowKey).intValue()) {
				return productModal;
			}
		}
		
		return null;
	}

}
