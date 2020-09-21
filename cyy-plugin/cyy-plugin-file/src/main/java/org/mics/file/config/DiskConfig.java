package org.mics.file.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 保存磁盘
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "cyy.plugin.file.disk")
public class DiskConfig {
	
	
}
