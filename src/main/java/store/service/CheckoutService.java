package store.service;

import store.model.domain.Receipt;
import store.model.domain.ShoppingCart;
import store.model.domain.Product;
import store.model.dto.PurchaseItem;
import store.model.dto.FreeItem;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class CheckoutService {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public Receipt checkout(ShoppingCart cart, boolean isMembership) {
        Map<Product, Integer> items = cart.getItems();

        if (items.isEmpty()) {
            throw new IllegalStateException("[ERROR] 장바구니가 비어 있습니다. 구매할 상품을 추가하세요.");
        }

        List<PurchaseItem> purchasedItems = items.entrySet().stream()
                .map(entry -> new PurchaseItem(
                        entry.getKey().getName(),
                        entry.getValue(),
                        entry.getKey().getPrice() * entry.getValue()))
                .collect(Collectors.toList());

        int totalAmount = purchasedItems.stream()
                .mapToInt(PurchaseItem::getTotalPrice)
                .sum();

        List<FreeItem> freeItems = items.entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();

                    boolean isPromotionApplied = PromotionCalculator.applyPromotion(product, quantity);

                    if (isPromotionApplied) {
                        return new FreeItem(product.getName(), quantity / product.getPromotion().getBuyCount());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        int promotionDiscount = freeItems.stream()
                .mapToInt(item -> {
                    Product product = items.keySet().stream()
                            .filter(p -> p.getName().equals(item.getProductName()))
                            .findFirst()
                            .orElse(null);
                    return (product != null ? product.getPrice() : 0) * item.getQuantity();
                })
                .sum();

        int discountBase = Math.max(0, totalAmount - promotionDiscount);
        int membershipDiscount = isMembership
                ? Math.min((int) (discountBase * MEMBERSHIP_DISCOUNT_RATE), MAX_MEMBERSHIP_DISCOUNT)
                : 0;

        int finalAmount = totalAmount - promotionDiscount - membershipDiscount;

        return new Receipt(purchasedItems, freeItems, totalAmount, promotionDiscount, membershipDiscount , finalAmount);
    }
}