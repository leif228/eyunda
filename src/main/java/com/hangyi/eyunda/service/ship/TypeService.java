package com.hangyi.eyunda.service.ship;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.dao.YydTypeAttrNameDao;
import com.hangyi.eyunda.dao.YydTypeDao;
import com.hangyi.eyunda.data.ship.TypeData;
import com.hangyi.eyunda.domain.YydType;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class TypeService extends BaseService<YydType, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydTypeDao typeDao;
	@Autowired
	private YydTypeAttrNameDao attrNameDao;
	@Autowired
	private YydShipDao shipDao;

	@Override
	public PageHibernateDao<YydType, Long> getDao() {
		return (PageHibernateDao<YydType, Long>) typeDao;
	}

	public TypeData getTypeDataByName(String typeName) {
		YydType yydType = typeDao.getByName(typeName);
		if (yydType != null)
			return this.getTypeData(yydType.getTypeCode());

		return null;
	}

	public TypeData getTypeData(String typeCode) {
		TypeData typeData = null;
		YydType yydType = typeDao.getByCode(typeCode);
		if (yydType != null) {
			typeData = new TypeData();
			CopyUtil.copyProperties(typeData, yydType);

			if ("00".equals(typeCode.substring(2, 4))) {
				List<TypeData> childrenDatas = new ArrayList<TypeData>();
				List<YydType> children = typeDao.getChildren(typeCode);
				for (YydType child : children) {
					TypeData childData = new TypeData();
					CopyUtil.copyProperties(childData, child);
					childrenDatas.add(childData);
				}
				typeData.setChildrenDatas(childrenDatas);
			} else {
				String prtCode = typeCode.substring(0, 2) + "00";
				typeData.setParent(this.getTypeData(prtCode));
			}
		}
		return typeData;
	}

	public List<TypeData> getTypeDatas() {
		List<TypeData> typeDatas = new ArrayList<TypeData>();
		Page<YydType> page = typeDao.findPage();
		if (page != null && !page.getResult().isEmpty()) {
			List<YydType> types = (List<YydType>) page.getResult();
			for (YydType type : types) {
				TypeData typeData = this.getTypeData(type.getTypeCode());
				typeDatas.add(typeData);
			}
		}
		return typeDatas;
	}

	public List<TypeData> getUncleDatas() {
		List<TypeData> uncleDatas = new ArrayList<TypeData>();
		List<YydType> uncles = typeDao.getUncles();
		if (!uncles.isEmpty()) {
			for (YydType uncle : uncles) {
				TypeData uncleData = this.getTypeData(uncle.getTypeCode());
				uncleDatas.add(uncleData);
			}
		}
		return uncleDatas;
	}

	public boolean save(String prtTypeCode, String typeCode, String typeName) throws Exception {
		YydType yydType = null;
		if ("".equals(typeCode)) { // 是添加
			yydType = new YydType();

			yydType.setTypeName(typeName);

			String code = "";
			if ("".equals(prtTypeCode)) // 为第一级类别
				code = typeDao.getSpcUncleCode();
			else
				// 为第二级类别
				code = typeDao.getSpcChildCode(prtTypeCode);
			yydType.setTypeCode(code);
		} else {
			// 是修改
			yydType = typeDao.getByCode(typeCode);

			if (yydType == null)
				throw new Exception("错误！修改的类别没有找到。");

			yydType.setTypeName(typeName);

			if (typeCode.substring(2, 4).equals("00")) { // 为第一级类别
				if (!"".equals(prtTypeCode))
					throw new Exception("错误！不能为第一级类别选择上级类别。");
				else
					;// typeCode不变
			} else {// 为第二级类别
				if ("".equals(prtTypeCode))
					throw new Exception("错误！必须为第二级类别选择上级类别。");
				else {
					// 如果改变了上级类别
					if (!prtTypeCode.substring(0, 2).equals(typeCode.substring(0, 2))) {
						String code = typeDao.getSpcChildCode(prtTypeCode);
						yydType.setTypeCode(code);
						// !!!这儿改typeCode，需要更新类别属性、船舶、合同对象相应的typeCode
					}
				}
			}
		}

		typeDao.save(yydType);

		return true;
	}

	public boolean deleteType(String typeCode) throws Exception {
		YydType type = typeDao.getByCode(typeCode);
		if (type != null) {
			if (!attrNameDao.getTypeAttrNames(typeCode).isEmpty())
				throw new Exception("错误！该类别存在类别属性信息。");
			if (!shipDao.findBy("shipType", typeCode).isEmpty())
				throw new Exception("错误！该类别存在船舶信息。");
			if (!typeDao.getChildren(typeCode).isEmpty())
				throw new Exception("错误！该类别存在下级类别。");

			typeDao.delete(type);
			return true;
		}
		return false;
	}

}
