package ir.khu.ie.publications.services.api;

import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ir.khu.ie.publications.models.responses.app.GetPublicationResponse;
import ir.khu.ie.publications.models.responses.app.SearchResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppAPI {

    @FormUrlEncoded
    @POST("app/get-main-page")
    Call<GetMainPageResponse> getMainPage(@Field("retrieve_data_count") int retrieveDataCount);

    @FormUrlEncoded
    @POST("app/search")
    Call<SearchResponse> search(@Field("search_text") String searchText);

    @FormUrlEncoded
    @POST("app/get-publication")
    Call<GetPublicationResponse> getPublication(@Field("id") String publicationId);
}
