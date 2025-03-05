package store.service;

import store.model.domain.Promotion;
import store.model.domain.Product;
import store.model.domain.ShoppingCart;
import store.utils.FileParser;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PromotionService {
    private final List<Promotion> promotions;
    private final InputView inputView;
    private final OutputView outputView;
    private final ProductService productService;

    public PromotionService(ProductService productService, InputView inputView, OutputView outputView) {
        this.promotions = FileParser.parsePromotions("promotions.md");
        this.productService = productService; // ✅ 명시적으로 ProductService 주입
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void applyPromotions(ShoppingCart cart) {
        LocalDate today = LocalDate.now();

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            Promotion promotion = product.getPromotion();

            if (promotion == null || promotion.isActive(today)) {
                continue;
            }

            int buyCount = promotion.getBuyCount();
            int getCount = promotion.getGetCount();

            // 프로모션 적용 가능한 추가 증정 개수 계산
            int freeQuantity = (quantity / buyCount) * getCount;
            if (freeQuantity > 0) {
                int availableStock = product.getStock();
                freeQuantity = Math.min(freeQuantity, availableStock);

                if (freeQuantity > 0) {
                    outputView.askPromotionConfirmation(product.getName(), freeQuantity);
                    boolean confirm = inputView.askYesOrNo();

                    if (confirm) {
                        cart.addFreeItem(product, freeQuantity);
                        product.reduceStock(freeQuantity);
                    }
                }
            }

            // 추가 구매 안내
            int requiredForPromotion = buyCount - (quantity % buyCount);
            if (requiredForPromotion > 0 && requiredForPromotion != buyCount) {
                outputView.suggestAdditionalPurchase(product.getName(), requiredForPromotion);
            }

            // 프로모션 재고 부족 시 정가 결제 여부 확인
            int promoApplicableQuantity = (quantity / buyCount) * buyCount;
            int nonPromoQuantity = quantity - promoApplicableQuantity;
            if (nonPromoQuantity > 0) {
                outputView.askNonPromotionPurchase(product.getName(), nonPromoQuantity);
                boolean confirm = inputView.askYesOrNo();

                if (!confirm) {
                    cart.removeItem(product, nonPromoQuantity);
                }
            }
        }
    }
}