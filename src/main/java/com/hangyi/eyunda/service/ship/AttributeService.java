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

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydTypeAttrNameDao;
import com.hangyi.eyunda.dao.YydTypeAttrValueDao;
import com.hangyi.eyunda.data.ship.AttrNameData;
import com.hangyi.eyunda.data.ship.AttrValueData;
import com.hangyi.eyunda.domain.YydTypeAttrName;
import com.hangyi.eyunda.domain.YydTypeAttrValue;
import com.hangyi.eyunda.domain.enumeric.AttrTypeCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class AttributeService extends BaseService<YydTypeAttrName, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydTypeAttrNameDao attrNameDao;
	@Autowired
	YydTypeAttrValueDao attrValueDao;
	@Autowired
	TypeService typeService;

	@Override
	public PageHibernateDao<YydTypeAttrName, Long> getDao() {
		return (PageHibernateDao<YydTypeAttrName, Long>) attrNameDao;
	}

	public String getAttrValueByCode(String code) {
		YydTypeAttrValue attrValue = attrValueDao.getByCode(code);
		if (attrValue != null)
			return attrValue.getAttrValue();
		else
			return "";
	}

	public AttrNameData getTypeAttrNameData(Long id) {
		try {
			YydTypeAttrName yydTypeAttrName = attrNameDao.get(id);
			if (yydTypeAttrName != null) {
				AttrNameData attrNameData = new AttrNameData();
				CopyUtil.copyProperties(attrNameData, yydTypeAttrName);

				String attrValues = "";
				List<YydTypeAttrValue> l = attrValueDao.getAttrValuesBy(attrNameData.getAttrNameCode());
				for (YydTypeAttrValue e : l) {
					attrValues += e.getAttrValue() + "|";
				}
				if (!"".equals(attrValues))
					attrValues = attrValues.substring(0, attrValues.length() - 1);
				attrNameData.setAttrValues(attrValues);

				attrNameData.setTypeData(typeService.getTypeData(yydTypeAttrName.getAttrNameCode().substring(0, 4)));

				List<AttrValueData> attrValueDatas = new ArrayList<AttrValueData>();
				for (YydTypeAttrValue e : l) {
					AttrValueData attrValueData = new AttrValueData();
					CopyUtil.copyProperties(attrValueData, e);

					attrValueDatas.add(attrValueData);
				}

				attrNameData.setAttrValueDatas(attrValueDatas);

				return attrNameData;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public List<AttrNameData> getTypeAttrNameDatas(String typeCode) {
		List<AttrNameData> attrNameDatas = new ArrayList<AttrNameData>();
		try {
			List<YydTypeAttrName> typeAttrbutes = attrNameDao.getTypeAttrNames(typeCode);
			for (YydTypeAttrName typeAttrbute : typeAttrbutes)
				attrNameDatas.add(this.getTypeAttrNameData(typeAttrbute.getId()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return attrNameDatas;
	}

	public boolean saveAttribute(Long id, String attrName, String typeCode, String attrTypeCode, String attrValues) {
		try {
			YydTypeAttrName yydTypeAttrName = attrNameDao.get(id);
			if (yydTypeAttrName == null) { // 是添加
				yydTypeAttrName = new YydTypeAttrName();
				String attrNameCode = attrNameDao.getSpcCode(typeCode);
				yydTypeAttrName.setAttrNameCode(attrNameCode);
			}
			// 修改了类别
			if (!yydTypeAttrName.getAttrNameCode().startsWith(typeCode)) {
				String attrNameCode = attrNameDao.getSpcCode(typeCode);
				yydTypeAttrName.setAttrNameCode(attrNameCode);
			}

			yydTypeAttrName.setAttrType(AttrTypeCode.valueOf(attrTypeCode));
			yydTypeAttrName.setAttrName(attrName);

			attrNameDao.save(yydTypeAttrName);

			List<YydTypeAttrValue> lstAttrValue = attrValueDao.getAttrValuesBy(yydTypeAttrName.getAttrNameCode());
			for (YydTypeAttrValue eAttrValue : lstAttrValue)
				attrValueDao.delete(eAttrValue);

			String spcCode = attrValueDao.getSpcCode(yydTypeAttrName.getAttrNameCode());
			String[] avs = attrValues.split("\\|");
			if (avs != null && avs.length != 0) {
				for (String av : avs) {
					YydTypeAttrValue eAttrValue = new YydTypeAttrValue();

					eAttrValue.setAttrValueCode(spcCode);
					eAttrValue.setAttrValue(av);

					attrValueDao.save(eAttrValue);

					int nextNo = Integer.parseInt(spcCode.substring(spcCode.length() - 2, spcCode.length())) + 1;
					if (nextNo < 10)
						spcCode = spcCode.substring(0, spcCode.length() - 2) + "0" + nextNo;
					else
						spcCode = spcCode.substring(0, spcCode.length() - 2) + "" + nextNo;
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteAttribute(Long id) {
		try {
			YydTypeAttrName attrName = attrNameDao.get(id);

			if (attrName != null) {
				List<YydTypeAttrValue> l = attrValueDao.getAttrValuesBy(attrName.getAttrNameCode());
				for (YydTypeAttrValue e : l)
					attrValueDao.delete(e);

				attrNameDao.delete(attrName);

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
