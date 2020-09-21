package org.mics.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.jpa.entity.BaseEntity;

/**
 * 短信
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@Entity
@Table(name="t_sms_record")
public class SmsRecordDO extends BaseEntity{
	/**
	 * 电话
	 */
	@Column(name="mobile",length=24,nullable=false)
	private String mobile;
	
	/**
	 * 模板code
	 */
	@Column(name="template_code",length=48,nullable=false)
	private String templateCode;
	
	/**
	 * 模板参数
	 */
	@Column(name = "template_param", length = 512, nullable = false)
	private String templateParam;
	
	/**
	 * 状态 0未发送 1发送 2发送失败
	 */
	@Column(name="state")
	private int state=0;
	
}
