package org.mics.token.model;

import java.io.Serializable;

/**
 * 当前用户
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;
    

	public CurrentUser(String userId, String account, String name, String avatar) {
		this.id = userId;
		this.account = account;
		this.name = name;
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
    
}
