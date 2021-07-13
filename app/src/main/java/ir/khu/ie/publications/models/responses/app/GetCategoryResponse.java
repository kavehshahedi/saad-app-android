package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.khu.ie.publications.models.responses.Response;

public class GetCategoryResponse extends Response {

    @SerializedName("data")
    @Expose
    private final GetMainPageResponse.Data.Category data;

    public GetCategoryResponse(String status, String message, GetMainPageResponse.Data.Category data) {
        super(status, message);
        this.data = data;
    }

    public GetMainPageResponse.Data.Category getData() {
        return data;
    }
}
