package com.online.module.menuSession.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.common.util.MathUtil;
import com.online.module.menuSession.model.MenuSession;

@SuppressWarnings("deprecation")
@Repository("menuSessionDao")
public class MenuSessionDaoImpl extends GenericDaoHibernate<Object, Serializable>
	implements MenuSessionDao{
	
	private static final Logger logger = Logger.getLogger(MenuSessionDaoImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public List<MenuSession> retrieveAllMenuAllowed(String userLogin) {
		String hqlStr = " select mm.menu_id "
				+ " 	,mm.name "
				+ "     ,mm.action "
				+ "     ,mm.parent_id "
				+ "     ,mm.description "
				+ "     ,mm.fontawesome "
				+ "     ,mm.menu_level "
				+ "     ,mm.menu_order "
				+ " from mst_menu mm "
				+ " 	inner join mst_responsibility_detail mrd on mrd.menu_id = mm.menu_id "
				+ "     inner join mst_responsibility mr on mr.responsibility_id = mrd.responsibility_id "
				+ "     inner join mst_people mp on mp.responsibility_id = mr.responsibility_id "
				+ " where 1 = 1 "
				+ " 	and mm.record_flag = 'Y' "
				+ " 	and mp.people_number = :userLogin "
				+ " order by menu_level, menu_order ";
		
		SQLQuery query = getSession().createSQLQuery(hqlStr);
		query.setParameter("userLogin", userLogin);
		
		List result = query.list();
		List<MenuSession> vo = new ArrayList<MenuSession>();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			MenuSession data = new MenuSession();
			
			data.setMenuId(obj[0] != null ? (MathUtil.returnIdObjToLong(obj[0])) : null);
			data.setMenuName(obj[1] != null ? (String) obj[1] : null);
        	data.setMenuAction(obj[2]!= null ? request.getContextPath() + (String)obj[2] : null);
        	data.setParentId(obj[3] != null ? (MathUtil.returnIdObjToLong(obj[3])) : null);
        	data.setDescription(obj[4] != null ? (String) obj[4] : null);
        	data.setFontawesome(obj[5] != null? (String) obj[5] :null);
        	data.setMenuLevel(obj[6] != null ? ((Number) obj[6]).intValue() : null);
        	data.setMenuOrder(obj[7] != null ? ((Number) obj[7]).intValue() : null);
        	
        	vo.add(data);
		}
		
		return vo;
	}

	public static Logger getLogger() {
		return logger;
	}

}
