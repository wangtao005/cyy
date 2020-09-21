package org.mics.core.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页查询")
public class PageQuery {

	
	@ApiModelProperty("开始位置（从0开始）")
	private int pageIndex;
	@ApiModelProperty("每页多少条")
	private int pageSize = 10;
	@ApiModelProperty("默认参数")
	private String key;
	
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 页数
	 * author zls
	 * 2020年6月30日
	 */
	@ApiModelProperty(hidden = true)
	public int getPageNo() {
		return pageIndex/pageSize;
	}
	
}
