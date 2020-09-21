package org.mics.file.request;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件上传请求
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
@ApiModel(value = "文件上传请求")
public class OneFileUploadRequest {
	
	@ApiModelProperty(value = "文件")
	@NotNull(message = "文件不能为空")
	private MultipartFile file;
	
	
}
