package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRespository;
import vn.hoidanit.laptopshop.repository.CartRespository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRespository cartRespository;
    private final CartDetailRespository cartDetailRespository;
    private final UserService userService;
    private final CartService cartService;
    private final CartDetailService cartDetailService;

    public ProductService(ProductRepository productRepository, CartRespository cartRespository,
            CartDetailRespository cartDetailRespository, UserService userService, CartService cartService,
            CartDetailService cartDetailService) {
        this.productRepository = productRepository;
        this.cartRespository = cartRespository;
        this.cartDetailRespository = cartDetailRespository;
        this.userService = userService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        Product productSave = this.productRepository.save(product);
        return productSave;
    }

    public Product getProductById(long id) {
        Optional<Product> product = this.productRepository.findTop1ById(id);
        return product.get();
    }

    public void deleteAProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user have cart? not -> add new
            Cart cart = this.cartRespository.findByUser(user);
            if (cart == null) {
                // add new cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRespository.save(otherCart);
            }

            // save cart_detail
            // find product by id
            Optional<Product> productOptional = this.productRepository.findTop1ById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                CartDetail oldDetail = this.cartDetailRespository.findByCartAndProduct(cart, realProduct);
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);

                    int sum = cart.getSum() + 1;
                    this.cartDetailRespository.save(cd);
                    cart.setSum(sum);
                    this.cartRespository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRespository.save(oldDetail);
                }
            }
        }
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetail = this.cartDetailService.getCartDetailById(cartDetailId);
        if (cartDetail.isPresent()) {
            Cart cart = cartDetail.get().getCart();
            long cartId = cart.getId();
            this.cartDetailService.deleteCartDetailById(cartDetailId);
            if (cart.getSum() > 1) {
                int sum = cart.getSum() - 1;
                cart.setSum(sum);
                session.setAttribute("sum", sum);
                this.cartRespository.save(cart);
            } else {
                session.setAttribute("sum", 0);
                this.cartService.deleteCartById(cartId);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRespository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRespository.save(currentCartDetail);
            }
        }
    }
}
