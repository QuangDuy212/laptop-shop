package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRespository;

    public CartService(CartRepository cartRespository) {
        this.cartRespository = cartRespository;
    }

    public Cart getCartByUser(User user) {
        return this.cartRespository.findByUser(user);
    }

    public void deleteCartById(long id) {
        this.cartRespository.deleteById(id);
    }

}
