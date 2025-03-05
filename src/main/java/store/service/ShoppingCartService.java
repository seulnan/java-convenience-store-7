package store.service;

import store.model.domain.ShoppingCart;
import store.model.domain.Product;
import store.utils.ShoppingCartParser;
import store.exception.InvalidInputException;

import java.util.Map;
import java.util.Optional;

public class ShoppingCartService {
    private final ProductService productService;

    public ShoppingCartService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * ✅ 장바구니에 상품 추가
     */
    public ShoppingCart createShoppingCart(String input) {
        Map<String, Integer> parsedItems = ShoppingCartParser.parse(input);
        ShoppingCart cart = new ShoppingCart();

        for (Map.Entry<String, Integer> entry : parsedItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            // ✅ 상품을 ProductService에서 조회
            Optional<Product> productOpt = productService.findProductByName(productName);
            if (productOpt.isEmpty()) {
                throw new InvalidInputException("[ERROR] 존재하지 않는 상품입니다: " + productName);
            }

            cart.addProduct(productOpt.get(), quantity);
        }

        return cart;
    }
}