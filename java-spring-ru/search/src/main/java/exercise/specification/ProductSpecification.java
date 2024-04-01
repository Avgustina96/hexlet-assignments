
package exercise.specification;
import exercise.dto.ProductParamsDTO;
import exercise.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component // Для возможности автоматической инъекции
public class ProductSpecification {
    // Генерация спецификации на основе параметров внутри DTO
    // Для удобства каждый фильтр вынесен в свой метод
    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryID(params.getCategoryId())
                .and(withPriceGt(params.getPriceGt()))
                .and(withPriceLt(params.getPriceLt()))
                .and(withRatingGt(params.getRatingGt()))
                .and(withTitleCont(params.getTitleCont()));
    }

    private Specification<Product> withCategoryID(Long categoryID) {
        return (root, query, cb) -> categoryID == null ? cb.conjunction() : cb.equal(root.get("category").get("id"),
                categoryID);
    }

    private Specification<Product> withPriceGt(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("priceGt"), price);
    }

    private Specification<Product> withPriceLt(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("priceLt"), price);
    }

    private Specification<Product> withRatingGt(Double rating) {
        return (root, query, cb) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("ratingGt"), rating);
    }

    private Specification<Product> withTitleCont(String title) {
        return (root, query, cb) -> title == null ? cb.conjunction() : cb.equal(root.get("title"), title);
    }

}