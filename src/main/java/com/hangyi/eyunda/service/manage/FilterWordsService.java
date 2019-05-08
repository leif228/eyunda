package com.hangyi.eyunda.service.manage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydFilterWordsDao;
import com.hangyi.eyunda.data.manage.FilterWordsData;
import com.hangyi.eyunda.domain.YydFilterWords;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class FilterWordsService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydFilterWordsDao yydFilterWordsDao;

	private static final String DEFAULT_FILTER_WORDS = "共产党,中共,法轮功,script";
	private static final String SEPARATOR = ",";
	private String[] filterWords = null;

	private String[] initFilterWords() {
		List<YydFilterWords> fws = yydFilterWordsDao.getAll();
		if (fws.isEmpty()) {
			YydFilterWords fw = new YydFilterWords();
			fw.setFilterWords(DEFAULT_FILTER_WORDS);
			yydFilterWordsDao.save(fw);

			return DEFAULT_FILTER_WORDS.split(SEPARATOR);
		} else {
			YydFilterWords fw = fws.get(0);
			String strFilterWords = fw.getFilterWords();

			return strFilterWords.split(SEPARATOR);
		}
	}

	/* 显示敏感词汇 */
	public FilterWordsData getFilterWordsData() {
		if (filterWords == null)
			filterWords = this.initFilterWords();

		FilterWordsData data = new FilterWordsData();
		List<YydFilterWords> list = yydFilterWordsDao.getAll();
		if (!list.isEmpty()) {
			YydFilterWords yydFilterWords = list.get(0);
			CopyUtil.copyProperties(data, yydFilterWords);
		}

		return data;
	}

	/* 保存敏感词汇 */
	public boolean saveFilterWords(FilterWordsData filterWordsData) {
		try {
			YydFilterWords yydFilterWords = yydFilterWordsDao.get(filterWordsData.getId());

			if (yydFilterWords == null)
				yydFilterWords = new YydFilterWords();
			yydFilterWords.setFilterWords(filterWordsData.getFilterWords());

			yydFilterWordsDao.save(yydFilterWords);

			filterWords = null;

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String removeFilterWords(String source) {
		if (filterWords == null)
			filterWords = this.initFilterWords();

		String dest = source;
		for (String fw : filterWords)
			// 使用大小写不敏感替换方式
			if (source.matches("(?i)" + fw))
				dest = dest.replaceAll("(?i)" + fw, "");

		return dest;
	}

}
