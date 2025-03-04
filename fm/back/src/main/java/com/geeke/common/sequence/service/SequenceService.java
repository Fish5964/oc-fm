package com.geeke.common.sequence.service;

import com.geeke.common.persistence.DataEntity;
import com.geeke.common.sequence.dao.SequenceDao;
import com.geeke.common.sequence.entity.Sequence;
import com.geeke.sys.dao.CodeRuleDao;
import com.geeke.sys.dao.CompanyCodeRuleDao;
import com.geeke.utils.FreeMarkers;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 业务编号Service
 * @author lys
 * @version 2021-04-30
 */
 
@Service("sequenceService")
@Transactional(readOnly = true)
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;

	@Autowired
	private CompanyCodeRuleDao companyCodeRuleDao;
	
	@Autowired CodeRuleDao codeRuleDao;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取编号
	 * @param ruleCode    规则代码
	 * @param bizObject	     业务对象
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public String generate( String ruleCode, Object bizObject) {
		return generate(null, ruleCode, bizObject);
	}
	
	/**
	 * 获取编号
	 * @param companyId	       公司Id  公司Id为空时，使用统一的序号
	 * @param ruleCode    规则代码
	 * @param bizObject	     业务对象
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public String generate(String companyId, String ruleCode, Object bizObject) {
		String ruleDef = null;
		// 公司id不为空，使用获取公司的编号规则表达式
		if(StringUtils.isNotBlank(companyId)) {
			ruleDef = companyCodeRuleDao.getRuleDefByCode(companyId, ruleCode);
		}
		// 公司Id为空，或公司编号规则不存在，获取系统统一的规则表达式
		if(ruleDef == null) {
			ruleDef = codeRuleDao.getRuleDefByCode(ruleCode);
		}
		
		if(ruleDef == null) {
			return null;
		}
		DataEntity entity = (DataEntity) bizObject;
		if (StringUtils.isNotBlank(ruleDef) && ruleDef.indexOf("createDate") >= 0) {
			entity.setCreateDate(simpleDateFormat.format(new Date()));
		}
		return doGenerate(companyId, ruleCode, ruleDef, entity);
	}
	
	/**
	 * 生成编号
	 * @param companyId			 公司Id
	 * @param ruleCode    规则代码
	 * @param templateString	规则表达式
	 * @param bizObject			业务对象
	 * @return
	 */
	private String doGenerate(String companyId, String ruleCode, String templateString, DataEntity bizObject) {
		// 公司Id为空时，使用统一的序号（companyId为0）
		String theCompanyId = StringUtils.isBlank(companyId) ? "0" : companyId;
		Map<String, Object> model = Maps.newHashMap();
		model.put("biz", bizObject);
		model.put("seq", 0);
		String prefix = FreeMarkers.renderString(templateString, model);
		Sequence sequence = sequenceDao.getByPrefix(theCompanyId, ruleCode, prefix);
		if(sequence == null) {
			sequence = insertSeq(theCompanyId, ruleCode, prefix);
		}
		
		model.put("seq", sequence.getVal());
		String seqNo = FreeMarkers.renderString(templateString, model);
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append(sequence.getVal());
		sequenceDao.nextSeqById(sequence);
		return builder.toString();
	}
	
	
	private synchronized Sequence insertSeq(String companyId, String ruleCode, String prefix) {
		Sequence sequence = sequenceDao.getByPrefix(companyId, ruleCode, prefix);
		if(sequence == null) {
			sequence = new Sequence();
			sequence.setCompanyId(companyId);
			sequence.setCode(ruleCode);
			sequence.setPrefix(prefix);
			sequence.preInsert();
			sequenceDao.insert(sequence);
			
			sequence = sequenceDao.getByPrefix(companyId, ruleCode, prefix);	// 增加数据库锁
		}
		return sequence;
	}
}