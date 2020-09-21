package org.mics.push.request;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mics.push.enums.PushEnums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 推送请求
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@ApiModel("推送请求")
public class BasePushRequest {

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容(必填)", required = true)
    private String text;

    /**
     * 设备类型
     */
    @NotNull(message = "设备类型不能为空")
    @ApiModelProperty(value = "设备类型(必填)(安卓:ANDROID, ios:IOS, 所有:ALL)", required = true)
    private PushEnums.DeviceType deviceType;

    /**
     * 推送目标类型
     */
    @NotNull(message = "推送目标类型不能为空")
    @ApiModelProperty(value = "推送目标类型类型(必填)(" +
            "*        //标签: 多个标签之间是 OR 的关系，即取并集。一次推送最多 20 个。" +
            "*        TAG," +
            "*        //多个标签之间是 AND 关系，即取交集。一次推送最多 20 个。" +
            "*        TAG_AND," +
            "*        //别名: 用别名来标识一个用户。一个设备只能绑定一个别名，但多个设备可以绑定同一个别名。一次推送最多 1000 个。" +
            "*        ALIAS," +
            "*        //注册ID: 设备标识。一次推送最多 1000 个。" +
            "*        REGISTRATION_ID," +
            "*        //用户分群ID: 一次只能推送一个" +
            "*        SEGMENT," +
            "*        //所有" +
            "*        ALL)", required = true)
    private PushEnums.PushAimsType pushAimsType;

    /**
     * 推送目标集合
     */
    @ApiModelProperty("推送目标集合(除推送目标类型为ALL时,其他类型至少为一个)")
    private List<String> pushAimsList;

    /**
     * 附加参数
     */
    @ApiModelProperty("附加参数")
    private Map<String, String> param;

    /**
     * 图片路径
     */
    @ApiModelProperty("图片路径-安卓(例如: http://218.246.202.93//resource//cms-web/20191121/3787578e18954b4da4e227af6bd1eecd.png)")
    private String bigPicPath;

    public Map<String, String> getParam() {
        return param = param == null ? Collections.emptyMap() : param;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PushEnums.DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(PushEnums.DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public PushEnums.PushAimsType getPushAimsType() {
		return pushAimsType;
	}

	public void setPushAimsType(PushEnums.PushAimsType pushAimsType) {
		this.pushAimsType = pushAimsType;
	}

	public List<String> getPushAimsList() {
		return pushAimsList;
	}

	public void setPushAimsList(List<String> pushAimsList) {
		this.pushAimsList = pushAimsList;
	}

	public String getBigPicPath() {
		return bigPicPath;
	}

	public void setBigPicPath(String bigPicPath) {
		this.bigPicPath = bigPicPath;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

    
    
}
