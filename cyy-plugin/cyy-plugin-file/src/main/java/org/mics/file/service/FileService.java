package org.mics.file.service;

import org.mics.file.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
public interface FileService {
	
	/**
	 * 上传文件
	 * @param file 文件
	 * @return 返回上传文件
	 */
	FileVO upload(MultipartFile file);
	
	/**
	 * 上传到oss服务器
	 * @param file 文件
	 * @param serviceName
	 * @return
	 */
	String upload2OSS(MultipartFile file);

}
