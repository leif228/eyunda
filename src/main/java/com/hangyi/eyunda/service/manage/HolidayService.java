package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydHolidayDao;
import com.hangyi.eyunda.data.manage.HolidayData;
import com.hangyi.eyunda.domain.YydHoliday;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HolidayService extends BaseService<YydHoliday, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydHolidayDao yydHolidayDao;

	@Override
	public PageHibernateDao<YydHoliday, Long> getDao() {
		return (PageHibernateDao<YydHoliday, Long>) yydHolidayDao;
	}

	public HolidayData getHoliday(Calendar date) {
		YydHoliday yydHoliday = yydHolidayDao.getHoliday(date);
		HolidayData holidayData = this.getHolidayData(yydHoliday);

		return holidayData;
	}

	public HolidayData getHolidayData(YydHoliday yydHoliday) {
		HolidayData holidayData = null;
		if (yydHoliday != null) {
			holidayData = new HolidayData();
			CopyUtil.copyProperties(holidayData, yydHoliday);
			// 节假日开始时间转换为String类型
			holidayData.setStartDate(CalendarUtil.toYYYY_MM_DD(yydHoliday.getStartDate()));
			// 节假日结束时间转换为String类型
			holidayData.setEndDate(CalendarUtil.toYYYY_MM_DD(yydHoliday.getEndDate()));
		}
		return holidayData;
	}

	public List<HolidayData> findHoliday() {
		List<HolidayData> holidayDatas = new ArrayList<HolidayData>();
		List<YydHoliday> yydHolidays = yydHolidayDao.findHoliday();
		for (YydHoliday yydHoliday : yydHolidays) {
			HolidayData holidayData = this.getHolidayData(yydHoliday);
			holidayDatas.add(holidayData);
		}
		return holidayDatas;
	}

	public void saveHoliday(HolidayData holiday) throws Exception {
		YydHoliday yydHoliday = yydHolidayDao.get(holiday.getId());

		if (yydHoliday == null) {
			yydHoliday = new YydHoliday();
		}

		yydHoliday.setHolidayName(holiday.getHolidayName());
		yydHoliday.setStartDate(CalendarUtil.parseYYYY_MM_DD(holiday.getStartDate()));
		yydHoliday.setEndDate(CalendarUtil.parseYYYY_MM_DD(holiday.getEndDate()));

		yydHolidayDao.save(yydHoliday);
	}

	public void deleteHoliday(HolidayData holiday) throws Exception {
		YydHoliday yydHoliday = yydHolidayDao.get(holiday.getId());
		yydHolidayDao.delete(yydHoliday);
	}

}
