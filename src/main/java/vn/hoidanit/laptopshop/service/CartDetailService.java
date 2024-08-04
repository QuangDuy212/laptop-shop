package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;

@Service
public class CartDetailService {
    private final CartDetailRepository cartDetailRespository;

    public CartDetailService(CartDetailRepository cartDetailRespository) {
        this.cartDetailRespository = cartDetailRespository;
    }

    public List<CartDetail> getCartDetailsByCart(Cart cart) {
        return this.cartDetailRespository.findByCart(cart);
    }

    public Optional<CartDetail> getCartDetailById(long id) {
        return this.cartDetailRespository.findById(id);
    }

    public void deleteCartDetailById(long id) {
        this.cartDetailRespository.deleteById(id);
    }

}
