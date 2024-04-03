package exercise.specification;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryId(params.getCategoryId())
                .and(withPriceGt(params.getPriceGt()))
                .and(withPriceLt(params.getPriceLt()))
                .and(withRatingGt(params.getRatingGt()))
                .and(withTitleCont(params.getTitleCont()));
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null
                ? cb.conjunction()
                : cb.equal(root.get("category").get("id"), categoryId);
    }

    private Specification<Product> withPriceLt(Integer price) {
        return (root, query, cb) -> price == null
                ? cb.conjunction()
                : cb.lessThan(root.get("price"), price);
    }

    private Specification<Product> withPriceGt(Integer price) {
        return (root, query, cb) -> price == null
                ? cb.conjunction()
                : cb.greaterThan(root.get("price"), price);
    }

    private Specification<Product> withRatingGt(Double rating) {
        return (root, query, cb) -> rating == null
                ? cb.conjunction()
                : cb.greaterThan(root.get("rating"), rating);
    }

    private Specification<Product> withTitleCont(String substring) {
        return (root, query, cb) -> substring == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%" + substring + "%");
    }
}

