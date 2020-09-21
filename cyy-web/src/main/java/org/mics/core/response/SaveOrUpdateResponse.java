package org.mics.core.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * 保存或者修改返回对象
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public class SaveOrUpdateResponse extends SimpleResponse {
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 6139444006533120545L;
	/**
     * 成功数据id
     */
	@ApiModelProperty(value = "成功数据id")
    private String id;

    public SaveOrUpdateResponse(String id) {
        super();
        this.id = id;
    }

    public SaveOrUpdateResponse(Integer code, String desc, String id) {
        super(code, desc);
        this.id = id;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
