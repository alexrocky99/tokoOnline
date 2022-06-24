package com.online.module.transaction.penjualan.tableModel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.online.module.transaction.penjualan.model.PenjualanDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenjualanDetailTableModel<E> extends ListDataModel<PenjualanDetail> implements SelectableDataModel<PenjualanDetail> {

	public PenjualanDetailTableModel(List<PenjualanDetail> data) {
		super(data);
	}
	
	@Override
	public String getRowKey(PenjualanDetail object) {
		return object.getSequence().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PenjualanDetail getRowData(String rowKey) {
		List<PenjualanDetail> list = (List<PenjualanDetail>) getWrappedData();
		
		for (PenjualanDetail penjualanDetail : list) {
			if (penjualanDetail.getSequence() == new Integer(rowKey).intValue()) {
				return penjualanDetail;
			}
		}
		
		return null;
	}

}
