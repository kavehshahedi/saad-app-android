package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.responses.Response;

public class SearchResponse extends Response {

    @SerializedName("data") @Expose
    private Data data;

    public SearchResponse(String status, String message) {
        super(status, message);
    }

    public Data getData() {
        return data;
    }

    public class Data {
        private final List<Publication> publications;

        public Data(List<Publication> publications) {
            this.publications = publications;
        }

        public List<Publication> getPublications() {
            return publications;
        }
    }
}
