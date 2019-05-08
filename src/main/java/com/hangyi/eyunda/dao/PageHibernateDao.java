package com.hangyi.eyunda.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hangyi.eyunda.dao.PropertyFilter.MatchType;
import com.hangyi.eyunda.service.SequenceService;
import com.hangyi.eyunda.util.ReflectionUtil;

public class PageHibernateDao<T, PK extends Serializable> {

	protected Class<T> entityClass;
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected SequenceService sequenceService;

	@SuppressWarnings("unchecked")
	public PageHibernateDao() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return ((this.sessionFactory != null) ? this.sessionFactory : null);
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public T save(final T entity) {
		Assert.notNull(entity, "entity不能为空");

		try {
			Field field = entityClass.getField("id");
			Long longId = (Long) field.get(entity);
			if (longId == 0L) {
				field.set(entity, sequenceService
						.getNo(entityClass.getName().substring(entityClass.getName().lastIndexOf(".") + 1)));
			}
			getSession().save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	public void delete(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
	}

	public void delete(final PK id) {
		Assert.notNull(id, "id不能为空");
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public T get(final PK id, LockMode lockMode) {
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id, lockMode);
	}

	public List<T> getAll() {
		return find();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria();
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value, LockMode lockMode) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).setLockMode(lockMode).uniqueResult();
	}

	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <X> X findSQLUnique(final String sql, final Object... values) {
		Query query = createSQLQuery(sql, values);
		X x = (X) query.uniqueResult();
		// System.out.println(x.toString());
		return x;
	}

	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createSQLQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,可实现新的函数,执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize
	 * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyProperty(Object proxyProperty) {
		Hibernate.initialize(proxyProperty);
	}

	public void flush() {
		getSession().flush();
	}

	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/** 不管查出多少条记录，都只返回一条，并且不报告违反唯一约束 */
	@SuppressWarnings("unchecked")
	public T findUniqueIsSafe(final String queryString, final Object... values) {
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		queryObject.setMaxResults(1);
		List<T> result = queryObject.list();
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

	// -- 分页查询函数 --//
	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPage(final Page page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query query = createQuery(hql, values);

		long totalCount = countHqlResult(hql, values);
		page.setTotalCount(totalCount);

		setPageParameter(query, page);

		List result = query.list();
		page.setResult(result);

		return page;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPageBySQL(final Page page, final String sql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query query = createSQLQuery(sql, values);

		Long totalCount = countSQLResult(sql, values);
		page.setTotalCount(totalCount);

		setPageParameter(query, page);

		List result = query.list();
		page.setResult(result);

		return page;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPageDistinct(final Page page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query query = createQuery(hql, values);
		// 紧缩
		this.distinct(query);

		// 查全部
		List allResult = query.list();

		long totalCount = 0;
		if (!allResult.isEmpty())
			totalCount = allResult.size();
		page.setTotalCount(totalCount);

		List result = new ArrayList();
		if (!allResult.isEmpty()) {
			for (int i = (page.getPageNo() - 1) * page.getPageSize(); i < page.getPageNo() * page.getPageSize(); i++) {
				if (i < allResult.size()) {
					result.add(allResult.get(i));
				}
			}
		}
		page.setResult(result);

		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query query = createQuery(hql, values);

		long totalCount = countHqlResult(hql, values);
		page.setTotalCount(totalCount);

		setPageParameter(query, page);

		List result = query.list();
		page.setResult(result);

		return page;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPageByCriteria(final Page page, Criteria criteria) {
		Assert.notNull(page, "page不能为空");

		int totalCount = getTotalCount(criteria);
		page.setTotalCount(totalCount);

		setPageParameter(criteria, page);

		List result = criteria.list();
		page.setResult(result);

		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria criteria = createCriteria(criterions);

		int totalCount = getTotalCount(criteria);
		page.setTotalCount(totalCount);

		setPageParameter(criteria, page);

		List result = criteria.list();
		page.setResult(result);

		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameter(final Criteria criteria, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		criteria.setFirstResult(page.getFirst() - 1);
		criteria.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					criteria.addOrder(Order.asc(orderByArray[i]));
				} else {
					criteria.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return criteria;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		// select子句与order by子句会影响count查询,进行简单的排除.
		String fromHql = hql.replace("FROM ", "from ").replace("ORDER BY ", "order by ");

		String selectHql = StringUtils.substringBefore(fromHql, "from ").replace("SELECT ", "select ");
		selectHql = StringUtils.substringAfter(selectHql, "select ").replace("DISTINCT ", "distinct ").trim();
		if (selectHql.indexOf("distinct") >= 0) {
			selectHql = "select count(" + selectHql + ") ";
		} else {
			selectHql = "select count(*) ";
		}

		fromHql = "from " + StringUtils.substringAfter(fromHql, "from ");
		fromHql = StringUtils.substringBefore(fromHql, "order by ");

		String countHql = selectHql + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	protected Long countSQLResult(final String sql, final Object... values) {
		// select子句与order by子句会影响count查询,进行简单的排除.
		String fromSql = sql.replace("FROM ", "from ").replace("ORDER BY ", "order by ");

		fromSql = "from " + StringUtils.substringAfter(fromSql, "from ");
		fromSql = StringUtils.substringBefore(fromSql, "order by ");

		String countSql = "select count(*) " + fromSql;

		try {
			Object object = findSQLUnique(countSql, values);

			if (object != null)
				return Long.parseLong(object.toString());
			else
				return 0L;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countSql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getTotalCount(final Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtil.getFieldValue(impl, "orderEntries");
			ReflectionUtil.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
		}

		// 执行Count查询
		Long lngCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		int totalCount = (lngCount == null) ? 0 : lngCount.intValue();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);

		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		try {
			ReflectionUtil.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
		}

		return totalCount;
	}

	// -- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType
	 *            匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.isMultiProperty()) { // 只有一个属性需要比较的情况.
				Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(), filter.getPropertyValue(),
						filter.getMatchType());
				criterionList.add(criterion);
			} else {// 包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildPropertyFilterCriterion(param, filter.getPropertyValue(),
							filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
			final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		try {

			// 根据MatchType构造criterion
			if (MatchType.EQ.equals(matchType)) {
				criterion = Restrictions.eq(propertyName, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			} else if (MatchType.LE.equals(matchType)) {
				criterion = Restrictions.le(propertyName, propertyValue);
			} else if (MatchType.LT.equals(matchType)) {
				criterion = Restrictions.lt(propertyName, propertyValue);
			} else if (MatchType.GE.equals(matchType)) {
				criterion = Restrictions.ge(propertyName, propertyValue);
			} else if (MatchType.GT.equals(matchType)) {
				criterion = Restrictions.gt(propertyName, propertyValue);
			}
		} catch (Exception e) {
			throw ReflectionUtil.convertReflectionExceptionToUnchecked(e);
		}
		return criterion;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
