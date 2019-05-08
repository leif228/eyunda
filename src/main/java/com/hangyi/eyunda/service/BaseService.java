package com.hangyi.eyunda.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.PropertyFilter;
import com.hangyi.eyunda.dao.PropertyFilter.MatchType;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class BaseService<T, PK extends Serializable> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public abstract PageHibernateDao<T, PK> getDao();

	public String getIdName() {
		return getDao().getIdName();
	}

	public T save(final T entity) {
		return (T) getDao().save(entity);
	}

	public void delete(final T entity) {
		getDao().delete(entity);
	}

	public void delete(final PK id) {
		getDao().delete(id);
	}

	public T get(final PK id) {
		return (T) getDao().get(id);
	}

	public T get(final PK id, LockMode lockMode) {
		return (T) getDao().get(id, lockMode);
	}

	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		return getDao().isPropertyUnique(propertyName, newValue, oldValue);
	}

	public T findUniqueBy(final String propertyName, final Object value) {
		return (T) getDao().findUniqueBy(propertyName, value);
	}

	public T findUniqueBy(final String propertyName, final Object value, LockMode lockMode) {
		return (T) getDao().findUniqueBy(propertyName, value, lockMode);
	}

	public <X> X findUnique(final String hql, final Object... values) {
		return (X) getDao().findUnique(hql, values);
	}

	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) getDao().findUnique(hql, values);
	}

	public T findUnique(final Criterion... criterions) {
		return (T) getDao().findUnique(criterions);
	}

	public T findUniqueIsSafe(final String queryString, final Object... values) {
		return (T) getDao().findUniqueIsSafe(queryString, values);
	}

	public List<T> getAll() {
		return getDao().getAll();
	}

	public List<T> getAll(String orderBy, boolean isAsc) {
		return getDao().getAll(orderBy, isAsc);
	}

	public List<T> findBy(final String propertyName, final Object value) {
		return getDao().findBy(propertyName, value);
	}

	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		return getDao().findBy(propertyName, value, matchType);
	}

	public List<T> find(List<PropertyFilter> filters) {
		return getDao().find(filters);
	}

	public List<T> findByIds(List<PK> ids) {
		return getDao().findByIds(ids);
	}

	public <X> List<X> find(final String hql, final Object... values) {
		return getDao().find(hql, values);
	}

	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return getDao().find(hql, values);
	}

	public List<T> find(final Criterion... criterions) {
		return getDao().find(criterions);
	}

	public Page<T> getAll(final Page<T> page) {
		return getDao().getAll(page);
	}

	public Page findPage(final Page page, final String hql, final Object... values) {
		return getDao().findPage(page, hql, values);
	}

	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		return getDao().findPage(page, hql, values);
	}

	public Page<T> findPageByCriteria(final Page page, Criteria c) {
		return getDao().findPageByCriteria(page, c);
	}

	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		return getDao().findPage(page, criterions);
	}

	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		return getDao().findPage(page, filters);
	}

	public int batchExecute(final String hql, final Object... values) {
		return getDao().batchExecute(hql, values);
	}

	public int batchExecute(final String hql, final Map<String, ?> values) {
		return getDao().batchExecute(hql, values);
	}

	public Query createQuery(final String queryString, final Object... values) {
		return getDao().createQuery(queryString, values);
	}

	public Query createQuery(final String queryString, final Map<String, ?> values) {
		return getDao().createQuery(queryString, values);
	}

	public Criteria createCriteria(final Criterion... criterions) {
		return getDao().createCriteria(criterions);
	}
	
	public int getTotalCount(final Criteria c) {
		return getDao().getTotalCount(c);
	}
	
	public Query distinct(Query query) {
		return getDao().distinct(query);
	}

	public Criteria distinct(Criteria criteria) {
		return getDao().distinct(criteria);
	}

}
