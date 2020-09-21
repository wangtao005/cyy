package org.mics.file.helper;

import java.io.File;
import java.io.IOException;

import org.mics.file.bean.FileInfo;
import org.mics.file.config.DiskProperties;
import org.mics.file.config.OSSProperties;
import org.mics.file.exception.FileUploadException;

/**
 * 文件上传帮助类
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
public class FileUploadHelper {
	private OSSProperties ossProperties;
	private DiskProperties diskProperties;
	
	
	public OSSProperties getOssProperties() {
		return ossProperties;
	}

	public void setOssProperties(OSSProperties ossProperties) {
		this.ossProperties = ossProperties;
	}

	public DiskProperties getDiskProperties() {
		return diskProperties;
	}

	public void setDiskProperties(DiskProperties diskProperties) {
		this.diskProperties = diskProperties;
	}
	
	private FileInfo saveFileToDisk(File file) {
		/*
		 * //验证文件类型 validateFileType(fileType, fileInfo.getExt().substring(1));
		 * 
		 * //保存文件 File finalPath = new File(fileInfo.getFinalPath()); File parentFile =
		 * finalPath.getParentFile();
		 * 
		 * //文件读写 validateParentFile(parentFile, fileInfo.getFinalPath());
		 * 
		 * //保存文件到磁盘 try { file.transferTo(finalPath); } catch (IOException e) {
		 * LOGGER.error("保存文件异常 finalPathString:{}", fileInfo.getFinalPath()); throw new
		 * FileUploadException("保存文件异常"); } return fileInfo;
		 */
		return null;
	}
	
}
