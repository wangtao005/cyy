package org.mics.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("文件上传响应实体")
public class FileVO {

    /**
     * 文件path
     */
    @ApiModelProperty("文件path")
    private String path;

    /**
     * 文件名字
     */
    @ApiModelProperty("文件名字")
    private String name;

    /**
     * 文件原名
     */
    @ApiModelProperty("文件原名")
    private String originalName;
}
