package ir.khu.ie.publications.services.api;

import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppAPI {

    @FormUrlEncoded
    @POST("app/get-main-page")
    Call<GetMainPageResponse> getMainPage(@Field("retrieve_data_count") int retrieveDataCount);
}
