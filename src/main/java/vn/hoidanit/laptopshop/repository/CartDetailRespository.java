package vn.hoidanit.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartDetailRespository extends JpaRepository<CartDetail, Long> {

}
