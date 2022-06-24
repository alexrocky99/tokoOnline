package com.online.module.master.responsibility.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;

import org.primefaces.model.TreeNode;
import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.menu.model.Menu;
import com.online.module.master.menu.service.MenuService;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.model.ResponsibilityDetail;
import com.online.module.master.responsibility.service.ResponsibilityService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsibilityEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -3470318055891848378L;
	private static final Logger logger = Logger.getLogger(ResponsibilityEditBean.class);
	
	private ResponsibilityService responsibilityService;
	private MenuService menuService;
	
	private Responsibility responsibility;
	@SuppressWarnings("rawtypes")
	private TreeNode root;
	@SuppressWarnings("rawtypes")
	private TreeNode[] selectedNodes;
	
	private String actionMode;
	private String editId;
	private int indexCounter;
	private int parentIndex;
	
	private List<ResponsibilityDetail> responsibilityMenuList;
	
	@PostConstruct
	public void init() {
		super.init();
		
		checkNewOrEdit();
	}
	
	private void checkNewOrEdit() {
		try {
			editId = facesUtil.retrieveRequestParam("editId");
			
			if (StringUtils.isBlank(editId)) {
				handleNew();
				
				searchResponsibilityMenuAndPopulateTree(null);
			} else {
				handleEdit(editId);
				
				searchResponsibilityMenuAndPopulateTree(new Long(editId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void searchResponsibilityMenuAndPopulateTree(Long responsibilityId) throws Exception {
		try {
			ResponsibilityDetail responsibilityDetail = new ResponsibilityDetail();
			
			responsibilityDetail.setResponsibilityId(responsibilityId);
			responsibilityMenuList = responsibilityService.searchResponsibilityAllMenu(responsibilityDetail);
			responsibilityMenuList = setRealChildListSize(responsibilityMenuList);
			
			indexCounter = 0;
			root = new DefaultTreeNode("root", null);
			root.setExpanded(true);
			
			populateTree(responsibilityMenuList, root);
		} catch (Exception e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Operation Failed : " + e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateTree(List<ResponsibilityDetail> responsibilityMenuListRaw, TreeNode rootNode) throws Exception {
		try {
			for (int i = 0; (responsibilityMenuListRaw != null && i < responsibilityMenuListRaw.size()); i++) {
				ResponsibilityDetail data = responsibilityMenuListRaw.get(i);
				
				TreeNode treeNode = new DefaultTreeNode(data, rootNode);
				treeNode.setExpanded(true);
				
				if (data.getCheck()) {
					treeNode.setSelected(true);
				} else {
					// do nothing
				}
				
				populateTree(data.getChildResponsibilityMenu(), treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<ResponsibilityDetail> setRealChildListSize(List<ResponsibilityDetail> initialList) {
		if (initialList != null) {
			for (int a = 0; a < initialList.size(); a++) {
				ResponsibilityDetail aVo = initialList.get(a);
				List<ResponsibilityDetail> aNextList = aVo.getChildResponsibilityMenu();
				int aSize = 0;
				if (aNextList != null) {
					aSize += aNextList.size();
					for (int b = 0; b < aNextList.size(); b++) {
						ResponsibilityDetail bVo = aNextList.get(b);
						List<ResponsibilityDetail> bNextList = bVo.getChildResponsibilityMenu();
						int bSize = 0;
						if (bNextList != null) {
							aSize += bNextList.size();
							bSize += bNextList.size();
							for (int c = 0; c < bNextList.size(); c++) {
								ResponsibilityDetail cVo = bNextList.get(c);
								List<ResponsibilityDetail> cNextList = cVo.getChildResponsibilityMenu();
								int cSize = 0;
								if (cNextList != null) {
									aSize += cNextList.size();
									bSize += cNextList.size();
									cSize = cNextList.size();
									for (int d = 0; d < cNextList.size(); d++) {
										ResponsibilityDetail dVo = cNextList.get(d);
										List<ResponsibilityDetail> dNextList = dVo.getChildResponsibilityMenu();
										int dSize = 0;
										if (dNextList != null) {
											aSize += dNextList.size();
											bSize += dNextList.size();
											cSize += dNextList.size();
											dSize = dNextList.size();
											for (int e = 0; e < dNextList.size(); e++) {
												ResponsibilityDetail eVo = dNextList.get(e);
												List<ResponsibilityDetail> eNextList = eVo.getChildResponsibilityMenu();
												int eSize = 0;
												if (eNextList != null) {
													aSize += eNextList.size();
													bSize += eNextList.size();
													cSize += eNextList.size();
													dSize += eNextList.size();
													eSize = eNextList.size();
												}
												eVo.setChildResponsibilityMenuSize(eSize);
												dNextList.set(e, eVo);
											}
										}
										dVo.setChildResponsibilityMenuSize(dSize);
										cNextList.set(d, dVo);
									}
								}
								cVo.setChildResponsibilityMenuSize(cSize);
								bNextList.set(c, cVo);
							}
						}
						bVo.setChildResponsibilityMenuSize(bSize);
						aNextList.set(b, bVo);
					}
				}
				aVo.setChildResponsibilityMenuSize(aSize);
				initialList.set(a, aVo);
			}
		}
		return initialList;
	}
	
	private void handleNew() {
		actionMode = Constants.ACTION_MODE_ADD;
		
		responsibility = new Responsibility();
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseIdLong = Long.parseLong(editId);
		
		responsibility = responsibilityService.findById(parseIdLong);
	}
	
	private Boolean isValidate() {
		boolean flag = true;
		
		if (StringUtils.isEmpty(responsibility.getName())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formResponsbilityName") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		return flag;
	}
	
	@SuppressWarnings("rawtypes")
	private TreeNode[] addElement(TreeNode[] org, TreeNode added) {
		TreeNode[] result = Arrays.copyOf(org, org.length + 1);
		result[org.length] = added;
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private void extractTreeSelectable(TreeNode rootNode, int i) throws Exception {
		try {
			if (i == 0) {
				selectedNodes = new TreeNode[0];
			}
			for (int j = 0; (rootNode != null && j < rootNode.getChildCount()); j++) {
				TreeNode responsibilityMenuTree = (TreeNode) rootNode.getChildren().get(j);
				
				if (responsibilityMenuTree.isSelected()) {
					selectedNodes = addElement(selectedNodes, responsibilityMenuTree);
				}
				extractTreeSelectable(responsibilityMenuTree, 1);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void save() {
		try {
			if (isValidate()) {
				List<ResponsibilityDetail> newResponsibilityMenuList = new ArrayList<ResponsibilityDetail>();
				extractTreeSelectable(getRoot(), 0);
				
				if (getSelectedNodes() != null) {
					for (TreeNode tn : getSelectedNodes()) {
						ResponsibilityDetail responsibilityMenu = (ResponsibilityDetail) tn.getData();
						newResponsibilityMenuList.add(responsibilityMenu);
					}
				}
				
				ResponsibilityDetail dataTemp = new ResponsibilityDetail();
				dataTemp.setResponsibilityId(responsibility.getResponsibilityId());
				dataTemp.setChildResponsibilityMenu(newResponsibilityMenuList);
				
				responsibilityService.deleteInsertResponsibilityMenu(dataTemp, facesUtil.retrieveUserLogin());
				
				Set<Long> requiredParentId = new HashSet<Long>();
				Set<Long> existingParentId = new HashSet<Long>();
				
				if (newResponsibilityMenuList != null && newResponsibilityMenuList.size() > 0) {
					for (ResponsibilityDetail dtlVo : newResponsibilityMenuList) {
						Menu menu = menuService.findById(dtlVo.getMenuId());
						
						if (menu.getParentId() != null) {
							requiredParentId.add(menu.getParentId());
						}
						
						if (menu.getMenuLevel() != null && menu.getMenuLevel().longValue() == 1) {
							existingParentId.add(menu.getMenuId());
						}
					}
				}
				
				if (existingParentId != null && existingParentId.size() > 0) {
					for (Long id : existingParentId) {
						requiredParentId.remove(id);
					}
				}
				
				if (newResponsibilityMenuList != null && newResponsibilityMenuList.size() > 0) {
					for (ResponsibilityDetail dtlVo : newResponsibilityMenuList) {
						Menu menu = menuService.findById(dtlVo.getMenuId());
						ResponsibilityDetail newData = new ResponsibilityDetail();
						
						newData.setMenu(menu);
						newData.setResponsibility(responsibility);
						EntityUtil.setCreationInfo(newData, facesUtil.retrieveUserLogin());
						
						responsibility.getResponsibilityDetailList().add(newData);
					}
				}
				
				for (Long id : requiredParentId) {
					Menu menu = menuService.findById(id);
					ResponsibilityDetail newData = new ResponsibilityDetail();
					
					newData.setMenu(menu);
					newData.setResponsibility(responsibility);
					EntityUtil.setCreationInfo(newData, facesUtil.retrieveUserLogin());
					
					responsibility.getResponsibilityDetailList().add(newData);
				}
				
				if (responsibility.getResponsibilityId() != null) {
					EntityUtil.setUpdateInfo(responsibility, facesUtil.retrieveUserLogin());
					responsibilityService.update(responsibility);
				} else {
					EntityUtil.setCreationInfo(responsibility, facesUtil.retrieveUserLogin());
					responsibilityService.save(responsibility);
				}
				
				facesUtil.redirect("/pages/master/responsibility/" + Constants.NAVIGATE_RESPONSIBILITY);
			}
		} catch (Exception e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Failed : " + e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			logger.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void selectUnselectTreeNode(TreeNode rootNode, TreeNode paramNode, boolean selected) {
		try {
			for (int i = 0; (rootNode != null && i < rootNode.getChildCount()); i++) {
				TreeNode responsibilityMenuTree = (TreeNode) rootNode.getChildren().get(i);
				if (responsibilityMenuTree.equals(paramNode)) {
					responsibilityMenuTree.setSelected(selected);
					if (responsibilityMenuTree.getChildren() != null && responsibilityMenuTree.getChildCount() > 0) {
						setAllChild(responsibilityMenuTree,selected);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void setAllChild(TreeNode theNode, boolean selected) {
		for (int i = 0; (theNode != null && i < theNode.getChildCount()); i++) {
			TreeNode childNode = (TreeNode) theNode.getChildren().get(i);
			childNode.setSelected(selected);
			setAllChild(childNode, selected);
		}
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		selectUnselectTreeNode(getRoot(), event.getTreeNode(), true);
	}
	
	public void onNodeUnselect(NodeUnselectEvent event) {
		selectUnselectTreeNode(getRoot(), event.getTreeNode(), false);
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/responsibility/" + Constants.NAVIGATE_RESPONSIBILITY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}

}
