package com.example.shosho.dietfood.view;

import com.example.shosho.dietfood.model.PostOrderData;

public interface PostOrderView {
    void showPostOrderResult(PostOrderData postOrderData);

    void showPostOrderRe(String message);

    void showError();
}
