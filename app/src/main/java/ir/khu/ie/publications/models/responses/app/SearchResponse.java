package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.responses.Response;

public class SearchResponse extends Response {

    @SerializedName("data")
    @Expose
    private List<Publication> data;

    public SearchResponse(String status, String message) {
        super(status, message);
    }

    public List<Publication> getData() {
        return data;
    }
}
