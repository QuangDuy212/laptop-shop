package vn.hoidanit.laptopshop.service.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import jakarta.persistence.criteria.Predicate;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    public static Specification<Product> priceGreaterThanOrEqualTo(double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE),
                minPrice);
    }

    public static Specification<Product> priceLessThanOrEqualTo(double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Product_.PRICE),
                maxPrice);
    }

    public static Specification<Product> priceBetween(double start, double end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE),
                start, end);
    }

    public static Specification<Product> priceBetweenOr(List<Pair<Double, Double>> listMoney) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (listMoney != null) {
                for (Pair<Double, Double> pair : listMoney) {
                    predicates
                            .add(criteriaBuilder.between(root.get(Product_.PRICE), pair.getFirst(), pair.getSecond()));
                }
            }
            Predicate test = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            return test;

        };
    }

    public static Specification<Product> targetLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.TARGET),
                "%" + name + "%");
    }

    public static Specification<Product> factoryLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.FACTORY),
                "%" + name + "%");
    }

    public static Specification<Product> factoryIn(List<String> name) {
        return (root, query, criteriaBuilder) -> {
            return root.get(Product_.FACTORY).in(name);
        };
    }
}
