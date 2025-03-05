package store.service;

import store.model.domain.Receipt;
import store.model.domain.ShoppingCart;

import store.model.dto.FreeItem;

import java.util.List;

public class CheckoutService {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public Receipt checkout(ShoppingCart cart, boolean isMembership) {
        int totalAmount = calculateTotalAmount(cart);

        List<FreeItem> freeItems = applyPromotions(cart);
        int promotionDiscount = calculatePromotionDiscount(freeItems, cart);

        int membershipDiscount = isMembership
                ? Math.min((int) ((totalAmount - promotionDiscount) * MEMBERSHIP_DISCOUNT_RATE),
                MAX_MEMBERSHIP_DISCOUNT)
                : 0;

        int finalAmount = totalAmount - promotionDiscount - membershipDiscount;

        return new Receipt(cart.getPurchasedItems(), freeItems, totalAmount, promotionDiscount, membershipDiscount,
                finalAmount);
    }
}