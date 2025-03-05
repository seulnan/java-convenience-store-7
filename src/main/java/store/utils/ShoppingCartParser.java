package store.utils;

import store.exception.InvalidInputException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartParser {

    /**
     * ✅ 입력값을 검증하고 상품명-수량을 Map으로 반환
     */
    public static Map<String, Integer> parse(String input) {
        // ✅ 입력값이 비어있는 경우 예외 발생
        if (input.trim().isEmpty()) {
            throw new InvalidInputException("[ERROR] 입력값이 비어 있습니다. 올바른 형식으로 입력하세요.");
        }

        Map<String, Integer> parsedItems = new HashMap<>();
        String[] items = input.replaceAll("[\\[\\]]", "").split(",");

        for (String item : items) {
            String[] parts = item.split("-");
            if (parts.length != 2) {
                throw new InvalidInputException("[ERROR] 올바른 입력 형식이 아닙니다. 예: [콜라-3],[사이다-1]");
            }

            String productName = parts[0].trim();
            int quantity;
            try {
                quantity = Integer.parseInt(parts[1].trim());
                if (quantity <= 0) {
                    throw new InvalidInputException("[ERROR] 수량은 1 이상이어야 합니다.");
                }
            } catch (NumberFormatException e) {
                throw new InvalidInputException("[ERROR] 수량은 숫자로 입력해야 합니다.");
            }

            // ✅ Map에 상품명과 수량 저장
            parsedItems.put(productName, parsedItems.getOrDefault(productName, 0) + quantity);
        }

        return parsedItems;
    }
}