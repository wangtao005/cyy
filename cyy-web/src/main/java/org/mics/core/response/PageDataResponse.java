package org.mics.core.response;

import java.util.List;

import org.mics.core.page.PageInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回分页对象
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
public class PageDataResponse<T> extends SimpleResponse {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 7778043747688517109L;

	/**
     * 分页信息
     */
	@ApiModelProperty(value = "分页信息")
    private PageInfo page;

    /**
     * 列表数据
     */
	@ApiModelProperty(value = "列表数据")
    private List<T> data;

    public PageDataResponse(PageInfo page, List<T> data) {
        super();
        this.page = page;
        this.data = data;
    }

	public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
    
    
}
