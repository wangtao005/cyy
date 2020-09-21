package org.mics.jpa.listener;

import org.mics.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;


/**
 * 实体监听事件
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public class EntityListener extends AuditingEntityListener {
    /**
     * 保存操作前动作
     * @param entity
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        //初始化数据
        entity.setBlnDel(false);
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
    }

    /**
     * 更新操作前动作
     * @param entity
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        //更新数据
        entity.setModifyDate(new Date());
    }
}
