package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.domain.ShoppingCart;
import store.service.ShoppingCartService;
import store.exception.InvalidInputException;

public class InputView {
    private final ShoppingCartService shoppingCartService;

    public InputView(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public ShoppingCart getUserShoppingCart() {
        System.out.println("\n구매할 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

        while (true) {
            try {
                String input = Console.readLine();
                return shoppingCartService.createShoppingCart(input); // ✅ ShoppingCartService 활용
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean askMembership() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return askYesOrNo();
    }

    public boolean askYesOrNo() {
        String input;
        while (true) {
            input = Console.readLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("[ERROR] Y 또는 N만 입력해주세요.");
        }
    }
}