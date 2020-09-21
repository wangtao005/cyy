package org.mics.order.repository;

import java.util.List;

import org.mics.jpa.repository.BaseRepository;
import org.mics.order.entity.OrderDO;
import org.mics.order.enums.OrderState;

public interface OrderRepository extends BaseRepository<OrderDO>{

	List<OrderDO> findByState(OrderState state);

}
