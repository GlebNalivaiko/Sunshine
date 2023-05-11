package by.sunshine.service;

import by.sunshine.converter.UserConverter;
import by.sunshine.dto.AuthorizationRequestUser;
import by.sunshine.dto.RegistrationRequestUser;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.Order;
import by.sunshine.entity.OrderCart;
import by.sunshine.entity.Product;
import by.sunshine.entity.User;
import by.sunshine.repository.RoleRepository;
import by.sunshine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartService cartService;


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(RegistrationRequestUser requestUser, UserDto userDto) {
        User user = createUser(requestUser, userDto);
        user.setRole(roleRepository.findById(1).orElseThrow());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        User updateUser = userRepository.save(user);
        updateUserDto(user, userDto);
        return updateUser;
    }

    public Optional<User> findByEmailAndPassword(AuthorizationRequestUser requestUser) {
        User user = userRepository.findByEmail(requestUser.getEmail()).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveProductToCart(UserDto userDto, Long productId) {
        userDto.getProductIds().add(productId);
        cartService.saveProductToCart(userDto.getCartId(), productId);
    }

    public void deleteProductFromCart(UserDto userDto, Long productId) {
        userDto.getProductIds().remove(productId);
        cartService.deleteProductFromCart(userDto.getCartId(), productId);
    }

    public void updateUserDto(User user, UserDto userDto) {
        userDto.setProductIds(user.getCart().getProducts().stream().map(Product::getId).collect(Collectors.toSet()));
        userDto.setCartId(user.getCart().getId());
        userDto.setOrderCartId(user.getOrderCart().getId());
        userDto.setId(user.getId());
        userDto.setOrderIds(user.getOrderCart().getOrders().stream().map(Order::getId).collect(Collectors.toSet()));
        userDto.setName(user.getName());
    }

    private User createUser(RegistrationRequestUser requestUser, UserDto userDto) {
        User user = new User();
        user.setCart(cartService.createCart(userDto.getProductIds()));
        user.setOrderCart(new OrderCart());
        user.setName(requestUser.getName());
        user.setEmail(requestUser.getEmail());
        return user;
    }
}
