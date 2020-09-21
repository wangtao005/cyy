package org.mics.file.config;

import org.mics.file.service.impl.FileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oss配置
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "cyy.plugin.file.oss")
public class OSSConfig {
	private final OSSProperties ossProperties;

	OSSConfig(OSSProperties ossProperties) {
		this.ossProperties = ossProperties;
	}

	@Bean
	@ConditionalOnMissingBean()
	public FileServiceImpl createFileService() {
		FileServiceImpl fileServiceImpl = new FileServiceImpl();
		//fileServiceImpl.setOssProperties(ossProperties);
		return fileServiceImpl;
	}
	
}
