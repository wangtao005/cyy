package org.mics.core.page;

/**
 * 分页信息
 * @author mics
 * @date 2020年6月17日
 * @version  1.0
 */
public class PageInfo {

    /**
     * 当前页数
     */
    private int pageNo;

    /**
     * 每一页数据条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总数据数
     */
    private Long totalCount;

    
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
    
    

}
