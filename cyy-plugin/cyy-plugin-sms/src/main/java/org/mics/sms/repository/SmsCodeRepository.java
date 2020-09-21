package org.mics.sms.repository;

import java.util.List;

import org.mics.jpa.repository.BaseRepository;
import org.mics.sms.entity.SmsCodeDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeRepository extends BaseRepository<SmsCodeDO>{
	 /**
     * 根据phone和当天日期查找未删除的短信验证码发送记录
     * @param phone      手机号
     * @param createDate 当天日期
     * @return 验证码发送记录集合
     */
    @Query("select scd from SmsCodeDO scd where scd.phone = ?1 and scd.createDate like concat(?2, '%') ")
    List<SmsCodeDO> findByPhoneAndCreateDateLike(String phone, String createDate);

    /**
     * 根据手机号查找未删除的最后一条短信验证码记录
     * @param phone 手机号
     * @return 短信验证码记录
     */
	SmsCodeDO findTop1ByPhoneOrderByCreateDateDesc(String phone);

	/**
	 * 根据id查询短信记录
	 * @param id
	 * @return
	 */
	SmsCodeDO getById(String id);
}
