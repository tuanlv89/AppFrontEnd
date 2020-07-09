package com.example.foodordering.retrofit;

import com.example.foodordering.model.brainTree.BrainTreeToken;
import com.example.foodordering.model.brainTree.BrainTreeTransaction;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IBrainTreeAPI {
    @GET("checkouts/new")
    Observable<BrainTreeToken> getToken();

    @POST("checkouts")
    @FormUrlEncoded
    Observable<BrainTreeTransaction> submitPayment(
            @Field("amount") String amount,
            @Field("payment_method_nonce") String nonce
    );
}
