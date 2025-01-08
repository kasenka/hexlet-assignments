package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component // Для возможности автоматической инъекции
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO params) {
        return titleCont(params.getTitleCont())
                .and(priceLt(params.getPriceLt()))
                .and(priceGt(params.getPriceGt()))
                .and(categoryId(params.getCategoryId()))
                .and(ratingGt(params.getRatingGt()));
    }

    private Specification<Product> titleCont(String title) {
        return (root, query, cb) ->
                title == null ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%"+title.toLowerCase()+"%");
    }

    private Specification<Product> priceLt(Integer price) {
        return (root, query, cb) ->
                price == null ? cb.conjunction()
                        : cb.lt(root.get("price"), price);
    }

    private Specification<Product> priceGt(Integer price) {
        return (root, query, cb) ->
                price == null ? cb.conjunction()
                        : cb.gt(root.get("price"), price);
    }

    private Specification<Product> categoryId(Long id) {
        return (root, query, cb) ->
                id == null ? cb.conjunction()
                        : cb.equal(root.get("category").get("id"), id);
    }

    private Specification<Product> ratingGt(Double rating) {
        return (root, query, cb) ->
                rating == null ? cb.conjunction()
                        : cb.gt(root.get("rating"), rating);
    }

    // Остальные методы
}
// END
