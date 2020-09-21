package org.mics.email.repository;

import java.util.List;

import org.mics.email.entity.EmailCodeDO;
import org.mics.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRepository  extends BaseRepository<EmailCodeDO>{

	/**
     * 根据邮箱和当天日期查找未删除的短信验证码发送记录
     * @param phone      手机号
     * @param createDate 当天日期
     * @return 验证码发送记录集合
     */
    @Query("select ecd from EmailCodeDO ecd where ecd.email = ?1 and ecd.createDate like concat(?2, '%') ")
	List<EmailCodeDO> findByEmailAndCreateDateLike(String email, String createDate);

	 /**
     * 根据邮箱查找未删除的最后一条短信验证码记录
     * @param phone 手机号
     * @return 短信验证码记录
     */
	EmailCodeDO findTop1ByEmailOrderByCreateDateDesc(String email);

	/**
	 * 根据id查询短信记录
	 * @param id
	 * @return
	 */
	EmailCodeDO getById(String id);
}
