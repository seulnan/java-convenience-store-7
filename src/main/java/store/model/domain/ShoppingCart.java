package store.model.domain;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 추가할 수량은 1개 이상이어야 합니다.");
        }

        // 기존 동일 상품이 있는지 확인 후 수량 증가
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void addFreeItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 추가할 증정 수량이 0개 이하일 수 없습니다.");
        }
        System.out.println(product.getName() + " " + quantity + "개가 무료로 증정되었습니다.");
        addProduct(product, quantity);
    }

    public void removeItem(Product product, int quantity) {
        if (!items.containsKey(product)) {
            throw new IllegalArgumentException("[ERROR] 해당 상품은 장바구니에 없습니다.");
        }
        int currentQuantity = items.get(product);
        if (quantity > currentQuantity) {
            throw new IllegalArgumentException("[ERROR] 제거할 수량이 장바구니에 있는 수량보다 많습니다.");
        }
        if (quantity == currentQuantity) {
            items.remove(product); // 수량이 0이면 장바구니에서 제거
        } else {
            items.put(product, currentQuantity - quantity);
        }
        System.out.println(product.getName() + " " + quantity + "개가 장바구니에서 제거되었습니다.");
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}