package com.example.shosho.dietfood.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.shosho.dietfood.api.Client;
import com.example.shosho.dietfood.api.Service;
import com.example.shosho.dietfood.model.PostOrderResponse;
import com.example.shosho.dietfood.model.StoreDataResponse;
import com.example.shosho.dietfood.model.User;
import com.example.shosho.dietfood.model.register.RegisterResponse;
import com.example.shosho.dietfood.view.PostOrderView;
import com.example.shosho.dietfood.view.RegisterView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostOrderPresenter {
    Context context;
    PostOrderView postOrderView;

    public PostOrderPresenter(Context context, PostOrderView postOrderView) {
        this.context = context;
        this.postOrderView = postOrderView;
    }

    public void getPostOrder(User user)
    {
        HashMap<String,String> hashMap=new HashMap<>(  );
        hashMap.put( "user_token",user.getUserToken());
        hashMap.put( "phone",user.getPhone() );

        Service service= Client.getClient().create( Service.class );
        Call<PostOrderResponse> call=service.getPostOrderData( hashMap );
        call.enqueue( new Callback<PostOrderResponse>() {
            @Override
            public void onResponse(Call<PostOrderResponse> call, Response<PostOrderResponse> response) {
                if(response.isSuccessful())
                {
                       postOrderView.showPostOrderResult(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<PostOrderResponse> call, Throwable t) {
                postOrderView.showError(  );
            }
        } );
}

    public void getPostOrderResult(User user)
    {
        HashMap<String,String> hashMap=new HashMap<>(  );
        hashMap.put( "user_token",user.getUserToken());
        hashMap.put( "phone",user.getPhone() );
        hashMap.put( "street1",user.getAddress() );
        hashMap.put( "city",user.getCity() );
        hashMap.put( "state",user.getState() );
        hashMap.put( "lat",user.getLat() );
        hashMap.put( "lng",user.getLng() );

        Service service= Client.getClient().create( Service.class );
        Call<StoreDataResponse> call=service.getPostOrder( hashMap );

        call.enqueue( new Callback<StoreDataResponse>() {
            @Override
            public void onResponse(Call<StoreDataResponse> call, Response<StoreDataResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        postOrderView.showPostOrderRe(response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<StoreDataResponse> call, Throwable t) {
                postOrderView.showError(  );
            }
        } );
    }
}
