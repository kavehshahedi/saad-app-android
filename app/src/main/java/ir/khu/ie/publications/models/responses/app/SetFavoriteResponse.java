package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.khu.ie.publications.models.responses.Response;

public class SetFavoriteResponse extends Response {

    @SerializedName("data")
    @Expose
    private final Data data;

    public SetFavoriteResponse(String status, String message, Data data) {
        super(status, message);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("status")
        @Expose
        private final boolean isBookmark;
        @SerializedName("favorite_publications")
        @Expose
        private final List<String> favoritePublications;

        public Data(boolean isBookmark, List<String> favoritePublications) {
            this.isBookmark = isBookmark;
            this.favoritePublications = favoritePublications;
        }

        public boolean isBookmark() {
            return isBookmark;
        }

        public List<String> getFavoritePublications() {
            return favoritePublications;
        }
    }
}
