package com.online.module.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GenericDaoHibernate<T, ID extends Serializable> implements GenericDao<T, ID> {

	private Class<T> persistentClass;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public T findById(ID id) {
		return (T) getSession().load(getPersistentClass(), id);
	}

	@Override
	public T getById(ID id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	@Override
	public T save(T entity) {
		try {
			getSession().save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T entity) {
		return (T) getSession().merge(entity);
	}

	@Override
	public void update(T entity) {
		try {
			getSession().update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(T entity) {
		try {
			getSession().delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void evict(T entity) {
		try {
			getSession().evict(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public void rollback() {
		getSession().getTransaction().rollback();
	}

}
