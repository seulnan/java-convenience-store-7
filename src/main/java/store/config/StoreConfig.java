package store.config;

import store.controller.StoreController;
import store.service.*;
import store.view.InputView;
import store.view.OutputView;

public class StoreConfig {
    public StoreController storeController() {
        return new StoreController(productService(), promotionService(), checkoutService(), inputView(), outputView());
    }

    public ProductService productService() {
        return new ProductService(promotionService());
    }

    public PromotionService promotionService() {
        return new PromotionService(inputView(), outputView());
    }

    public CheckoutService checkoutService() {
        return new CheckoutService();
    }

    public InputView inputView() {
        return new InputView(new ShoppingCartService(productService()));
    }

    public OutputView outputView() {
        return new OutputView();
    }
}