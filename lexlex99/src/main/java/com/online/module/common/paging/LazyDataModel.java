package com.online.module.common.paging;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * Custom lazy loading DataModel to deal with huge datasets
 */
@SuppressWarnings("serial")
public abstract class LazyDataModel<T> extends DataModel<T> implements SelectableDataModel<T>, Serializable {

    private int rowIndex = -1;

    private int rowCount;

    private int pageSize;

    private List<T> data;

    public LazyDataModel() {
        super();
    }

    public boolean isRowAvailable() {
        if (data == null) {
            return false;
        }

        return rowIndex >= 0 && rowIndex < data.size();
    }

    public int getRowCount() {
        return rowCount;
    }

    public T getRowData() {
        return data.get(rowIndex);
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        int oldIndex = this.rowIndex;

        if (rowIndex == -1 || pageSize == 0) {
            this.rowIndex = -1;
        }
        else {
            this.rowIndex = (rowIndex % pageSize);
        }

        if (data == null) {
            return;
        }

        DataModelListener[] listeners = getDataModelListeners();
        if (listeners != null && oldIndex != this.rowIndex) {
            Object rowData = null;
            if (isRowAvailable()) {
                rowData = getRowData();
            }

            DataModelEvent dataModelEvent = new DataModelEvent(this, rowIndex, rowData);
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].rowSelected(dataModelEvent);
            }
        }
    }

    public List<T> getWrappedData() {
        return data;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setWrappedData(Object list) {
        this.data = (List) list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Lazy loading is not implemented.");
    }

    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        throw new UnsupportedOperationException("Lazy loading is not implemented.");
    }

    public T getRowData(String rowKey) {
        throw new UnsupportedOperationException(
                getMessage("getRowData(String rowKey) must be implemented by %s when basic rowKey algorithm is not used [component=%s,view=%s]."));
    }

    private String getMessage(String format) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String viewId = facesContext.getViewRoot().getViewId();
        UIComponent component = UIComponent.getCurrentComponent(facesContext);
        String clientId = component == null ? "<unknown>" : component.getClientId(facesContext);
        return String.format(format, getClass().getName(), clientId, viewId);
    }
}
