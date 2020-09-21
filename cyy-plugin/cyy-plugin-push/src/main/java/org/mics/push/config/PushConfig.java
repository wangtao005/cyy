package org.mics.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * push配置文件
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
@Component
@PropertySource("classpath:pushParameter.properties")
public class PushConfig {
    /**
     * 极光推送安卓appKey
     */
    @Value("${project.jpush.android.app-key}")
    private String androidAppKey;

    /**
     * 极光推送安卓masterSecret
     */
    @Value("${project.jpush.android.master-secret}")
    private String androidMasterSecret;

    /**
     * 极光推送ios appKey
     */
    @Value("${project.jpush.ios.app-key}")
    private String iosAppKey;

    /**
     * 极光推送ios masterSecret
     */
    @Value("${project.jpush.ios.master-secret}")
    private String iosMasterSecret;

    /**
     * 极光推送ios 提示音
     */
    @Value("${project.jpush.ios.sound}")
    private String sound;

    /**
     * 极光推送保存时间
     */
    @Value("${project.jpush.time-to-live}")
    private long timToLive;

	public String getAndroidAppKey() {
		return androidAppKey;
	}

	public void setAndroidAppKey(String androidAppKey) {
		this.androidAppKey = androidAppKey;
	}

	public String getAndroidMasterSecret() {
		return androidMasterSecret;
	}

	public void setAndroidMasterSecret(String androidMasterSecret) {
		this.androidMasterSecret = androidMasterSecret;
	}

	public String getIosAppKey() {
		return iosAppKey;
	}

	public void setIosAppKey(String iosAppKey) {
		this.iosAppKey = iosAppKey;
	}

	public String getIosMasterSecret() {
		return iosMasterSecret;
	}

	public void setIosMasterSecret(String iosMasterSecret) {
		this.iosMasterSecret = iosMasterSecret;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public long getTimToLive() {
		return timToLive;
	}

	public void setTimToLive(long timToLive) {
		this.timToLive = timToLive;
	}
    
    
}
