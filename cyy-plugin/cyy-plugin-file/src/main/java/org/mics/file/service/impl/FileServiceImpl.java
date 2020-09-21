package org.mics.file.service.impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.mics.file.bean.FileInfo;
import org.mics.file.config.DiskProperties;
import org.mics.file.config.OSSProperties;
import org.mics.file.enums.EnumFile.FileType;
import org.mics.file.exception.FileUploadException;
import org.mics.file.helper.FileUploadHelper;
import org.mics.file.service.FileService;
import org.mics.file.vo.FileVO;
import org.mics.lang.calculate.UUIDUtil;
import org.mics.lang.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
@Service
public class FileServiceImpl implements FileService{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	/*
	 * @Resource private FileUploadHelper fileUploadHelper ;
	 */
	
	@Override
	public FileVO upload(MultipartFile file) {
		/*
		 * LOGGER.info("文件上传-开始"); // 保存到硬盘 FileInfo fileInfo =
		 * fileUploadHelper.saveFileToDisk(file); // 保存相关数据到数据库 FileVO fileVO =
		 * saveFile(fileInfo); LOGGER.debug("文件上传-service-结束：[fileVO:{}]", fileVO);
		 * return fileVO;
		 */
		return null;
	}

	
	/*
	 * private FileInfo saveFileToDisk(File file) {
	 * 
	 * //文件信息 FileInfo fileInfo = getInfo(file);
	 * 
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
	 * 
	 * }
	 */
	/*
	 * private FileInfo getInfo(MultipartFile file) { //文件原始参数 String
	 * originalFilename = file.getOriginalFilename(); String ext =
	 * originalFilename.substring(originalFilename.lastIndexOf(".")+1); String
	 * fileType = ext.substring(1); Long size = file.getSize();
	 * 
	 * //文件存储参数 String uuid = UUIDUtil.randomUUID(); String prefixPath =
	 * env.getProperty("file-service.save.path"); String storeName = uuid + ext;
	 * String folder = DateUtils.getDateYMDS(new Date(),"yyyy-MM-dd"); String
	 * folderPath = serviceName + File.separator + folder + File.separator +
	 * storeName; String finalPath = prefixPath + folderPath;
	 * 
	 * return new FileInfo(); }
	 */
	@Override
	public String upload2OSS(MultipartFile file) {
		return null;
	}

}
