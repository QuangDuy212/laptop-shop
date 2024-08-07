package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.CartService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    private final ProductService productService;
    private final UserService userService;
    private final CartDetailService cartDetailService;
    private final CartService cartService;
    private final UploadService uploadService;

    public ItemController(ProductService productService, UserService userService, CartDetailService cartDetailService,
            CartService cartService, UploadService uploadService) {
        this.productService = productService;
        this.userService = userService;
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
        this.uploadService = uploadService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session, 1);
        return "redirect:/";
    }

    @PostMapping("/add-product-from-view-detail")
    public String addProductToCartFromDetail(HttpServletRequest request, @RequestParam("id") long id,
            @RequestParam("quantity") long quantity) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long idUser = (long) session.getAttribute("id");
        user.setId(idUser);
        Cart cart = this.cartService.getCartByUser(user);
        List<CartDetail> cartDetails = cart != null ? cart.getCartDetails() : new ArrayList<CartDetail>();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long idUser = (long) session.getAttribute("id");
        user.setId(idUser);
        Cart cart = this.cartService.getCartByUser(user);
        List<CartDetail> cartDetails = cart != null ? cart.getCartDetails() : new ArrayList<CartDetail>();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String checkout(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long idUser = (long) session.getAttribute("id");
        user.setId(idUser);
        Cart cart = this.cartService.getCartByUser(user);
        List<CartDetail> cartDetails = cart != null ? cart.getCartDetails() : new ArrayList<CartDetail>();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        this.productService.handlePlaceOrder(user, session, receiverName, receiverAddress, receiverPhone, totalPrice);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanksPage(Model model) {
        return "client/cart/thanks";
    }

    @GetMapping("/account")
    public String getAccountPage(Model model,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.getUserById(id);
        model.addAttribute("id", id);
        model.addAttribute("newUser", user);
        return "client/account/show";
    }

    @PostMapping("/account/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User user,
            @RequestParam("avatarAccountFile") MultipartFile file,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            currentUser.setRole(this.userService.getRoleByName(user.getRole().getName()));
            String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
            if (avatar != "") {
                currentUser.setAvatar(avatar);
            }
            this.userService.handleSaveUser(currentUser);
            session.setAttribute("avatar", currentUser.getAvatar());
            session.setAttribute("fullName", currentUser.getFullName());
        }
        return "redirect:/account";
    }

}
