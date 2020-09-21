package org.mics.token.jwt;

import java.util.Map;

import org.mics.token.enums.TokenEnum;

/**
 *  jwt第二部分
 * @author mics
 * @date 2020年6月9日
 * @version  1.0
 */
public class JwtPayLoad {
    /**
     * 用户id
     */
    private String userId;

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

    /**
     * 来源
     */
    private TokenEnum.TokenSource source;

    /**
     * 自定义字段
     * 如：部门id、机构id
     */
    private Map<String, Object> customFields;

    public JwtPayLoad() {
    }

    public JwtPayLoad(String userId, String account, String name, String avatar, TokenEnum.TokenSource source) {
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.avatar = avatar;
        this.source = source;
    }

    public JwtPayLoad(String userId, String account, String name, String avatar, TokenEnum.TokenSource source, Map<String, Object> customFields) {
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.avatar = avatar;
        this.source = source;
        this.customFields = customFields;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public TokenEnum.TokenSource getSource() {
		return source;
	}

	public void setSource(TokenEnum.TokenSource source) {
		this.source = source;
	}

	public Map<String, Object> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, Object> customFields) {
		this.customFields = customFields;
	}
    
    
}
