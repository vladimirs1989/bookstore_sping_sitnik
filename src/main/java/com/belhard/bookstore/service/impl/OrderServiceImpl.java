package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.OrderDao;
import com.belhard.bookstore.dao.OrderItemDao;
import com.belhard.bookstore.dao.entity.Book;
import com.belhard.bookstore.dao.entity.Order;
import com.belhard.bookstore.dao.entity.OrderItem;
import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.dao.repository.OrderItemRepository;
import com.belhard.bookstore.dao.repository.OrderRepository;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.BookDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderItemDto;
import com.belhard.bookstore.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("orderService")

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, UserService userService, BookService bookService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userService = userService;
        this.bookService = bookService;
    }

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderDto> getAllOrders(Pageable pageable) {
        //return orderDao.getAllOrders().stream().map(this::mapToDto).collect(Collectors.toList());

        Iterable<Order> orders = orderRepository.findAllOrder(pageable);
        List<Order> orderList = new ArrayList<>();
        orders.forEach(orderList::add);
        return orderList.stream().map(entity -> mapToDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
//        Order order = orderDao.getOrderById(id);
//        return mapToDto(order);
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("No order with id: " + id);
        }
        return mapToDto(orderOptional.get());
    }

    private OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setTimestamp(order.getTimestamp());
        orderDto.setStatusDto(OrderDto.StatusDto.valueOf(order.getStatus().toString()));
        UserDto userDto = userService.getUserById(order.getUser().getId());
        orderDto.setUserDto(userDto);
        //List<OrderItem> items = orderItemDao.getByOrderId(order.getId());
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : items) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setPrice(item.getPrice());
            itemDto.setQuantity(item.getQuantity());
            BookDto bookDto = bookService.getBookById(item.getBook().getId());
            itemDto.setBookDto(bookDto);
            itemDtos.add(itemDto);
        }
        orderDto.setItems(itemDtos);
        return orderDto;
    }

    @Override
    //@Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderCost(orderDto);
        orderDto.setTotalCost(totalCost);

        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setTotalCost(orderDto.getTotalCost());
        User user = toUser(orderDto.getUserDto());
        entity.setUser(user);
        entity.setTimestamp(orderDto.getTimestamp());
        entity.setStatus(Order.Status.valueOf(orderDto.getStatusDto().toString()));
        orderRepository.save(entity);

        List<OrderItemDto> itemDtos = orderDto.getItems();
        for (OrderItemDto itemDto : itemDtos) {
            OrderItem item = new OrderItem();
            item.setOrder(entity);
            Book book = toBook(itemDto.getBookDto());
            item.setBook(book);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            //orderItemDao.createOrderItem(item);
            orderItemRepository.save(item);
        }
        return getOrderById(entity.getId());
    }

/*    private Order OrderDtoToOrder(OrderDto orderDto) {

        Order entity = new Order();

        entity.setId(orderDto.getId());
        entity.setTotalCost(orderDto.getTotalCost());
        User user = toUser(orderDto.getUserDto());
        entity.setUser(user);
        entity.setTimestamp(orderDto.getTimestamp());
        entity.setStatus(Order.Status.valueOf(orderDto.getStatusDto().toString()));

        return entity;
    }*/

    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        user.setRole(User.Roles.valueOf(userDto.getRole().toString()));
        return user;
    }


  /*  public List<OrderDto> getAllOrdersByUserId(Long id){
        UserDto userDto = userService.getUserById(id);
        List<OrderDto> orderDtos = getAllOrders(0,10);
        return  orderDtos.stream().filter(od->od.getUserDto().getId() == id).collect(Collectors.toList());
    }*/

    @Override
    //@Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderCost(orderDto);
        orderDto.setTotalCost(totalCost);

        Order entity = new Order();
        entity.setId(orderDto.getId());
        entity.setTotalCost(orderDto.getTotalCost());
        User user = toUser(orderDto.getUserDto());
        entity.setUser(user);
        entity.setTimestamp(orderDto.getTimestamp());
        entity.setStatus(Order.Status.valueOf(orderDto.getStatusDto().toString()));
        //orderDao.updateOrder(entity);
        orderRepository.save(entity);/*это не надо!!! но не создает*/

        //List<OrderItem> items = orderItemDao.getByOrderId(orderDto.getId());
        List<OrderItem> items = orderItemRepository.findByOrderId(orderDto.getId());
        for (OrderItem item : items) {
            //orderItemDao.deleteOrderItem(item.getId());
            orderItemRepository.delete(item);
        }

        List<OrderItemDto> itemDtos = orderDto.getItems();
        for (OrderItemDto itemDto : itemDtos) {
            OrderItem item = new OrderItem();
            item.setOrder(entity);
            Book book = toBook(itemDto.getBookDto());
            item.setBook(book);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            //orderItemDao.createOrderItem(item);
            orderItemRepository.save(item);

        }
        //orderDto.setItems(itemDtos);
        //orderRepository.save(entity);
        return getOrderById(orderDto.getId());

    }

    private BigDecimal calculateOrderCost(OrderDto orderDto) {
        List<OrderItemDto> items = orderDto.getItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            BigDecimal itemCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

/*    private OrderItem mapToEntity(OrderItemDto itemDto) {
        OrderItem item = new OrderItem();
        item.setOrder(orderDao.getOrderById(item.getOrder().getId()));
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        Book book = toBook(itemDto.getBookDto());
        item.setBook(book);
        return item;
    }*/

    private Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPages(bookDto.getPages());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        List<OrderItem> items = orderItemRepository.findByOrderId(id);
        items.forEach(i -> {
            orderItemRepository.delete(i);
        });
        orderRepository.delOrder(id);
    }
}