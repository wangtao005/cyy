package org.mics.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.mics.file.enums.EnumFile;
import org.mics.jpa.entity.BaseEntity;

@Entity
@Table(name = "t_file")
@Where(clause = "del_flag = 0")
public class FileDO extends BaseEntity{
	
	 /**
     * 文件名字
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) comment '文件名'")
    private String name;

    /**
     * 文件原名
     */
    @Column(name = "original_name", columnDefinition = "varchar(50) comment '文件原名'")
    private String originalName;

    /**
     * 地址
     */
    @Column(name = "path", nullable = false, columnDefinition = "varchar(100) comment '地址存储路径'")
    private String path;

    /**
     * 文件类型
     */
    @Column(name = "type", nullable = false, columnDefinition = "int(1) comment '文件类型'")
    private EnumFile.FileType type;

    /**
     * 文件大小
     */
    @Column(name = "size", columnDefinition = "bigint(10) comment '文件大小'")
    private long size;

    /**
     * 缩略图地址
     */
    @Column(name = "mini_path", columnDefinition = "varchar(100) comment '缩略图地址'")
    private String miniPath;
    
}
