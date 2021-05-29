package ir.khu.ie.publications.models.responses.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.responses.Response;

public class GetMainPageResponse extends Response {
    private Data data;

    public GetMainPageResponse(String status, String message) {
        super(status, message);
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final List<Slider> sliders;
        private final List<Category> categories;

        public Data(List<Slider> sliders, List<Category> categories) {
            this.sliders = sliders;
            this.categories = categories;
        }

        public List<Slider> getSliders() {
            return sliders;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public static class Slider {
            @SerializedName("ref_url")
            @Expose
            private final String sliderId;
            @SerializedName("image_url")
            @Expose
            private final String sliderImageUrl;

            public Slider(String sliderId, String sliderImage) {
                this.sliderId = sliderId;
                this.sliderImageUrl = sliderImage;
            }

            public String getSliderId() {
                return sliderId;
            }

            public String getSliderImageUrl() {
                return sliderImageUrl;
            }
        }

        public static class Category {
            @SerializedName("category_id")
            @Expose
            private final String categoryId;
            @SerializedName("category_name")
            @Expose
            private final String categoryName;
            @SerializedName("data")
            @Expose
            private final List<Publication> publications;

            public Category(String categoryId, String categoryName, List<Publication> publications) {
                this.categoryId = categoryId;
                this.categoryName = categoryName;
                this.publications = publications;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public List<Publication> getPublications() {
                return publications;
            }
        }
    }
}
