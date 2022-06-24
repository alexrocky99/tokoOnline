package com.online.module.master.people.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import com.online.module.common.constant.Constants;
import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.common.paging.SearchObject;
import com.online.module.common.util.MathUtil;
import com.online.module.master.people.model.People;

@Repository("peopleDao")
public class PeopleDaoImpl extends GenericDaoHibernate<People, Long> implements PeopleDao, Serializable {

	private static final long serialVersionUID = 997587487495706296L;

	@Override
	public People getUserLogin(String username, String password) {
		StringBuilder sb = new StringBuilder();
		People people = new People();
		
		sb.append(" select people_id ");
		sb.append(" from mst_people mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
//		sb.append("     and mp.password = :password ");
		sb.append("     and (mp.email = :username or mp.people_number = :username) ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("username", username);
//		query.setParameter("password", password);
		
		Number result = (Number) query.getSingleResult();
		
		if (result != null) {
			people = findById(MathUtil.returnIdObjToLong(result.longValue()));
		}
		
		return people;
	}

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						sb.append(" and upper(mp.full_name) like (:name) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_EMAIL)) {
						sb.append(" and upper(mp.email) like upper(:email) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_GENDER)) {
						sb.append(" and mp.gender =like (:gender) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_PEOPLE_NUMBER)) {
						sb.append(" and upper(mp.people_number) like upper(:peopleNumber) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_RESPONSIBILITY)) {
						sb.append(" and mp.responsibility = :responsibility ");
					}
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void getQueryWhereString(Query query, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						query.setParameter("name", "%"+val+"%");
					}
					if (StringUtils.equals(col, Constants.WHERE_EMAIL)) {
						query.setParameter("email", "%"+val+"%");
					}
					if (StringUtils.equals(col, Constants.WHERE_GENDER)) {
						query.setParameter("gender", val);
					}
					if (StringUtils.equals(col, Constants.WHERE_PEOPLE_NUMBER)) {
						query.setParameter("peopleNumber", "%"+val+"%");
					}
					if (StringUtils.equals(col, Constants.WHERE_RESPONSIBILITY)) {
						query.setParameter("responsibility", val);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<People> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.people_id ");
		sb.append(" 	,mp.people_number ");
		sb.append(" 	,mp.full_name ");
		sb.append("     ,mp.identity_card ");
		sb.append("     ,mp.email ");
		sb.append("     ,mp.mobile ");
		sb.append("     ,mr.name responsibility_name ");
		sb.append(" from mst_people mp ");
		sb.append(" 	left join mst_responsibility mr on mr.responsibility_id = mp.responsibility_id ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by mp.full_name asc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<People> vo = new ArrayList<People>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				People data = new People();
				
				data.setPeopleId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setPeopleNumber(obj[1] != null ? (String) obj[1] : null);
				data.setFullName(obj[2] != null ? (String) obj[2] : null);
				data.setIdentityCard(obj[3] != null ? (String) obj[3] : null);
				data.setEmail(obj[4] != null ? (String) obj[4] : null);
				data.setMobile(obj[5] != null ? (String) obj[5] : null);
				data.setResponsibilityName(obj[6] != null ? (String) obj[6] : null);
				
				vo.add(data);
			}
		}
		
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select count(1) ");
		sb.append(" from mst_people mp ");
		sb.append(" 	left join mst_responsibility mr on mr.responsibility_id = mp.responsibility_id ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	@Override
	public Number getNexVal() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select max(people_id) + 1 from mst_people ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

}
