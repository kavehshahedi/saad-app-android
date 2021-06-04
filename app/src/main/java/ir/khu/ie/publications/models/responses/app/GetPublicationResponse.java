package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.responses.Response;

public class GetPublicationResponse extends Response {

    @SerializedName("data")
    @Expose
    private Publication data;

    public GetPublicationResponse(String status, String message) {
        super(status, message);
    }

    public Publication getData() {
        return data;
    }
}
