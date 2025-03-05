package store.controller;


import store.model.domain.Receipt;
import store.model.domain.ShoppingCart;
import store.service.CheckoutService;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final ProductService productService;
    private final PromotionService promotionService;
    private final CheckoutService checkoutService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(ProductService productService, PromotionService promotionService,
                           CheckoutService checkoutService, InputView inputView, OutputView outputView) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.checkoutService = checkoutService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean continueShopping = true;

        while (continueShopping) {
            outputView.displayWelcomeMessage();
            productService.displayProducts();

            ShoppingCart cart = inputView.getUserShoppingCart();
            promotionService.applyPromotions(cart);

            boolean isMembership = inputView.askMembership();
            Receipt receipt = checkoutService.checkout(cart, isMembership);

            outputView.displayReceipt(receipt);

            continueShopping = inputView.askContinueShopping();
        }
    }
}