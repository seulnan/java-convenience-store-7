package store.view;

import store.model.domain.Receipt;
import store.model.dto.FreeItem;
import store.model.dto.PurchaseItem;

public class OutputView {
    public void displayWelcomeMessage() {
        System.out.println("안녕하세요. W 편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void displayReceipt(Receipt receipt) {
        System.out.println("\n=========== W 편의점 =============");

        System.out.println("상품명  수량  금액");
        for (PurchaseItem item : receipt.getPurchasedItems()) {
            System.out.printf("%s %d %d%n", item.getProductName(), item.getQuantity(), item.getTotalPrice());
        }

        if (!receipt.getFreeItems().isEmpty()) {
            System.out.println("=========== 증정 =============");
            for (FreeItem item : receipt.getFreeItems()) {
                System.out.printf("%s %d%n", item.getProductName(), item.getQuantity());
            }
        }

        System.out.println("==============================");
        System.out.printf("총구매액: %,d%n", receipt.getTotalAmount());
        System.out.printf("행사할인: -%,d%n", receipt.getPromotionDiscount());
        System.out.printf("멤버십할인: -%,d%n", receipt.getMembershipDiscount());
        System.out.printf("내실돈: %,d%n", receipt.getFinalAmount());
        System.out.println();
    }
    public void askPromotionConfirmation(String productName, int freeQuantity) {
        System.out.println("현재 " + productName + "은(는) " + freeQuantity + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }

    public void suggestAdditionalPurchase(String productName, int requiredForPromotion) {
        System.out.println("현재 " + productName + "을(를) " + requiredForPromotion + "개 더 구매하면 혜택을 받을 수 있습니다.");
    }

    public void askNonPromotionPurchase(String productName, int nonPromoQuantity) {
        System.out.println("현재 " + productName + " " + nonPromoQuantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }
}