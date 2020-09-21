package org.mics.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.mics.jpa.listener.EntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * 公共实体类
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity{
	/**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "org.mics.jpa.helper.CustomGenerationId")
    @Column(length = 32)
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "create_date", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createDate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(nullable = false, name = "modify_date", columnDefinition = "datetime COMMENT '更新时间'")
    private Date modifyDate;

    /**
     * 创建人
     */
    @Column(name = "creator", columnDefinition = "varchar(20) COMMENT '创建人'")
    private String createBy;

    /**
     * 更新人
     */
    @Column(name = "updater", columnDefinition = "varchar(20) COMMENT '更新人'")
    private String updateBy;

    /**
     * 软删除
     */
    @ColumnDefault("0")
    @Column(name = "bln_del", nullable = false, length = 1, columnDefinition = "BIT(1) COMMENT '删除标记'")
    private Boolean blnDel  = false;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Boolean getBlnDel() {
		return blnDel;
	}

	public void setBlnDel(Boolean blnDel) {
		this.blnDel = blnDel;
	}
    
    
}
