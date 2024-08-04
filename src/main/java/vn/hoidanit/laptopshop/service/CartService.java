package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartRespository;

@Service
public class CartService {
    private final CartRespository cartRespository;

    public CartService(CartRespository cartRespository) {
        this.cartRespository = cartRespository;
    }

    public Cart getCartByUser(User user) {
        return this.cartRespository.findByUser(user);
    }

}
