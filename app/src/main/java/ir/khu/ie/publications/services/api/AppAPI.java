package ir.khu.ie.publications.services.api;

import ir.khu.ie.publications.models.responses.app.GetCategoryResponse;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ir.khu.ie.publications.models.responses.app.GetPublicationResponse;
import ir.khu.ie.publications.models.responses.app.SearchResponse;
import ir.khu.ie.publications.models.responses.app.SetFavoriteResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppAPI {

    @FormUrlEncoded
    @POST("app/get-main-page")
    Call<GetMainPageResponse> getMainPage(@Field("retrieve_data_count") int retrieveDataCount);

    @FormUrlEncoded
    @POST("app/get-category")
    Call<GetCategoryResponse> getCategory(@Field("id") String categoryId);

    @FormUrlEncoded
    @POST("app/search")
    Call<SearchResponse> search(@Field("search_text") String searchText);

    @FormUrlEncoded
    @POST("app/get-publication")
    Call<GetPublicationResponse> getPublication(@Field("id") String publicationId);

    @FormUrlEncoded
    @POST("app/set-favorite")
    Call<SetFavoriteResponse> setFavorite(@Field("id") String publicationId, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("app/get-favorites")
    Call<SearchResponse> getFavorites(@Field("phone") String phone);
}
