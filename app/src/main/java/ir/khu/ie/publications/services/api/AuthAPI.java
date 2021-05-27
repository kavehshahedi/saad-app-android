package ir.khu.ie.publications.services.api;

import ir.khu.ie.publications.models.responses.auth.GetAccountResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPI {

    @FormUrlEncoded
    @POST("auth/get-account")
    Call<GetAccountResponse> getAccount(@Field("phone") String phone, @Field("access_token") String accessToken);
}
