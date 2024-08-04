package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.repository.CartDetailRespository;

@Service
public class CartDetailService {
    private final CartDetailRespository cartDetailRespository;

    public CartDetailService(CartDetailRespository cartDetailRespository) {
        this.cartDetailRespository = cartDetailRespository;
    }

    public List<CartDetail> getCartDetailsByCart(Cart cart) {
        return this.cartDetailRespository.findByCart(cart);
    }

}
