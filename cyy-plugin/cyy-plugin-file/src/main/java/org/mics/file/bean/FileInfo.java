package org.mics.file.bean;

/**
 * 文件相关信息
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
public class FileInfo {
    /**
     * 文件原始名称
     */
    String originalName;

    /**
     * 文件原始名称
     */
    String ext;

    /**
     * 文件类型 jpg、png。。。
     */
    String fileType;

    /**
     * 文件存储前缀
     */
    String prefixPath;

    /**
     * 文件存储时名称
     */
    String storeName;

    /**
     * 文件夹名
     */
    String folder;

    /**
     * 文件存储半路径
     */
    String folderPath;

    /**
     * 文件存储全路径
     */
    String finalPath;

    /**
     * 文件大小
     */
    long size;

    /**
     * 缩略图
     */
    String miniPath;
}
