package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.model.domain.Promotion;
import store.model.domain.Product;
import java.time.LocalDate;
import java.util.Optional;

public class PromotionCalculator {

    /**
     * ✅ 프로모션 적용 가능 여부 확인 후 증정 수량 계산
     */
    public static Optional<Integer> calculateFreeItems(Product product, int quantity) {
        Promotion promotion = product.getPromotion();

        // ✅ `DateTimes.now()`에서 날짜 부분만 추출하여 LocalDate로 변환
        LocalDate today = DateTimes.now().toLocalDate();

        // ✅ 오늘 날짜가 프로모션 기간 내에 포함되지 않으면 프로모션 적용 불가
        if (promotion == null || !promotion.isActive(today)) {
            return Optional.empty();
        }

        int buyCount = promotion.getBuyCount();
        int getCount = promotion.getGetCount();
        int freeQuantity = (quantity / buyCount) * getCount;

        return freeQuantity > 0 ? Optional.of(freeQuantity) : Optional.empty();
    }

    /**
     * ✅ 프로모션을 적용할 수 있다면, ProductService에서 프로모션 재고 확인 후 적용
     */
//    public static boolean applyPromotion(Product product, int quantity, ProductService productService) {
//        Optional<Integer> freeItemCount = calculateFreeItems(product, quantity);
//
//        if (freeItemCount.isEmpty()) {
//            return false; // 프로모션 적용 불가
//        }
//
//        int freeQuantity = freeItemCount.get();
//        int promotionStock = productService.getPromotionStock(product);
//
//        return freeQuantity <= promotionStock; // 프로모션 재고 부족
//    }

    public static boolean applyPromotion(Product product, int quantity) {
        return false;
    }
}