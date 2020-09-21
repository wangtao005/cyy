package org.mics.jpa.helper;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.mics.lang.calculate.SnowflakeIdWorker;
import org.springframework.stereotype.Component;

/**
 * 自定义id生成策略
 * @author mics
 * @date 2019年11月21日
 * @version  1.0
 */
@Component
public class CustomGenerationId  implements IdentifierGenerator{
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session,
			Object object) throws HibernateException {
		 synchronized (CustomGenerationId.class) {
			 return SnowflakeIdWorker.getInstance().nextId();
	     }
	}

}
