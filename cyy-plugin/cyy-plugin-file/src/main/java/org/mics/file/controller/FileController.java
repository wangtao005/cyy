package org.mics.file.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.mics.core.response.MultipleDataResponse;
import org.mics.core.response.OneDataResponse;
import org.mics.file.enums.EnumFile;
import org.mics.file.service.FileService;
import org.mics.file.vo.FileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
  
@Api(tags = "文件服务")
@RestController
@RequestMapping(value = "file")
public class FileController{ 
  @Resource
  private FileService fileService;
  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
  
  @ApiOperation("单文件上传")
  @PostMapping(value = "/upload") 
  public OneDataResponse<FileVO> upload(@RequestParam("file")MultipartFile file) {
	  FileVO fileVO = fileService.upload(file);
	  LOGGER.info("上传成功 fileVO:{}", fileVO); 
	  return new OneDataResponse<FileVO>(fileVO); 
 }
  
  
	  
  @ApiOperation("多文件上传")
  @PostMapping(value = "/multiUpload") 
  public MultipleDataResponse<FileVO> multiUpload(@RequestParam("file") MultipartFile[] files) {
	  List<FileVO> fileVOS = new ArrayList<>(); 
	  for (MultipartFile file : files) {
		  FileVO fileVO = fileService.upload(file);
		  fileVOS.add(fileVO); 
	  }
	  LOGGER.info("多文件上传结束"); 
	  return new MultipleDataResponse<FileVO>(fileVOS); 
  }
  
  
  @ApiOperation("上传图片到云服务")
  @PostMapping("upload2OSS") 
  public OneDataResponse<String> upload2OSS(@RequestBody MultipartFile file) {
	  LOGGER.debug("上传oss");
	  String path = fileService.upload2OSS(file);
	  LOGGER.debug("上传oss完成"); 
	  return new OneDataResponse<String>(path); 
  } 
}
