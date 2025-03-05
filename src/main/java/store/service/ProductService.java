package store.service;

import java.util.Optional;
import store.model.domain.Product;
import store.model.domain.Promotion;
import store.utils.FileParser;
import java.util.List;

public class ProductService {
    private final List<Product> products;

    public ProductService() {
        this.products = FileParser.parseProducts("products.md", List.of()); // ✅ Promotion 리스트 전달
    }

    public void displayProducts() {
        products.forEach(System.out::println);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

//    public int getPromotionStock(Product product) {
//        Promotion promotion = product.getPromotion();
//        if (promotion == null) {
//            return 0; // 프로모션이 없으면 프로모션 재고 없음
//        }
//        return product.getStock(); // 기본적으로 재고를 프로모션 재고로 활용
//    }
}