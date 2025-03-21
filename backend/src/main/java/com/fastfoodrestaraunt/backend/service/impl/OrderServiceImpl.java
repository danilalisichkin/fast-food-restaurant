package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.order.OrderAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.order.OrderDto;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.enums.Role;
import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.core.enums.sort.OrderSortField;
import com.fastfoodrestaraunt.backend.core.mappers.OrderItemMapper;
import com.fastfoodrestaraunt.backend.core.mappers.OrderMapper;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.entity.Cart;
import com.fastfoodrestaraunt.backend.entity.CartItem;
import com.fastfoodrestaraunt.backend.entity.Order;
import com.fastfoodrestaraunt.backend.entity.OrderItem;
import com.fastfoodrestaraunt.backend.entity.User;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.OrderItemRepository;
import com.fastfoodrestaraunt.backend.repository.OrderRepository;
import com.fastfoodrestaraunt.backend.security.utils.ContextUtils;
import com.fastfoodrestaraunt.backend.service.CartService;
import com.fastfoodrestaraunt.backend.service.MailService;
import com.fastfoodrestaraunt.backend.service.OrderService;
import com.fastfoodrestaraunt.backend.service.UserService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.CartValidator;
import com.fastfoodrestaraunt.backend.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderValidator orderValidator;

    private final CartValidator cartValidator;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private final CartService cartService;

    private final MailService mailService;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<OrderDto> getPageOfOrders(
            Integer offset, Integer limit, OrderSortField sortBy, Sort.Direction sortOrder, Status status) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        String userIdentifier = ContextUtils.getUserId();
        List<String> userRoles = ContextUtils.getRoles();
        boolean isAdmin = userRoles.contains("ROLE_" + Role.ADMIN.name());

        Page<Order> orders;

        if (!isAdmin) {
            User user = userService.getUserEntityByIdentifier(userIdentifier);
            orders = status == null
                    ? orderRepository.findAllByUser(pageRequest, user)
                    : orderRepository.findAllByUserAndStatus(pageRequest, user, status);
        } else {
            orders = status == null
                    ? orderRepository.findAll(pageRequest)
                    : orderRepository.findAllByStatus(pageRequest, status);
        }

        return pageMapper.pageToPageDto(
                orderMapper.entityPageToDtoPage(orders));
    }

    @Override
    public OrderDto getOrder(Long id) {
        return orderMapper.entityToDto(
                getOrderEntity(id));
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderAddingDto addingDto) {
        Cart cart = cartService.getCartEntity(addingDto.cartId());

        cartValidator.validateCartNotEmpty(cart);

        Order newOrder = orderMapper.dtoToEntity(addingDto);

        User user = cart.getUser();
        newOrder.setUser(user);

        List<CartItem> cartItems = cart.getItems();
        List<OrderItem> orderItems = orderItemMapper.cartItemListToEntityList(cartItems);
        orderItems.forEach(orderItem -> orderItem.setOrder(newOrder));
        newOrder.setItems(orderItems);

        newOrder.setStatus(Status.NEW);

        Order savedOrder = orderRepository.save(newOrder);
        orderItemRepository.saveAll(orderItems);
        cartService.deleteCart(cart.getId());

        mailService.sendOrderStatusChangedMessage(
                user.getEmail(),
                savedOrder.getId().toString(),
                Status.NEW.name());

        return orderMapper.entityToDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long id, Status status) {
        Order orderToUpdate = getOrderEntity(id);

        orderValidator.validateOrderStatusChanging(orderToUpdate, status);

        orderToUpdate.setStatus(status);
        if (status == Status.DELIVERED) {
            orderToUpdate.setCompletedAt(LocalDateTime.now());
        }

        if (status.equals(Status.DELIVERED)) {
            mailService.sendOrderCompleteMessage(
                    orderToUpdate.getUser().getEmail(),
                    orderToUpdate.getId().toString());
        } else {
            mailService.sendOrderStatusChangedMessage(
                    orderToUpdate.getUser().getEmail(),
                    orderToUpdate.getId().toString(),
                    status.name());
        }

        return orderMapper.entityToDto(
                orderRepository.save(orderToUpdate));
    }

    @Override
    public Order getOrderEntity(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("order with id=%s not found", id)));
    }
}
