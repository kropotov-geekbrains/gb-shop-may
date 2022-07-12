package ru.gb.gbshopmay.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.gbapimay.order.dto.OrderDto;
import ru.gb.gbshopmay.dao.AddressDao;
import ru.gb.gbshopmay.dao.ManufacturerDao;
import ru.gb.gbshopmay.entity.Order;

@Mapper
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);
    OrderDto toOrderDto(Order order);

}
