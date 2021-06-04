package ir.khu.ie.publications.models.publications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Publication {
    @SerializedName("_id")
    @Expose
    private final String id;
    @SerializedName("title")
    @Expose
    private final String title;
    @SerializedName("university_id")
    @Expose
    private final String universityId;
    @SerializedName("association_id")
    @Expose
    private final String associationId;
    @SerializedName("number")
    @Expose
    private final String number;
    @SerializedName("image_url")
    @Expose
    private final String imageUrl;
    @SerializedName("description")
    @Expose
    private final String description;
    @SerializedName("full_description")
    @Expose
    private final String fullDescription;
    @SerializedName("download_url")
    @Expose
    private final String downloadUrl;
    @SerializedName("released_at")
    @Expose
    private final Date releasedDate;
    @SerializedName("is_premium")
    @Expose
    private final boolean isPremium;
    @SerializedName("views_count")
    @Expose
    private final int viewCount;
    @SerializedName("downloads_count")
    @Expose
    private final int downloadCount;
    @SerializedName("size")
    @Expose
    private final float size;
    @SerializedName("rate")
    @Expose
    private final float rate;
    @SerializedName("is_editor_choice")
    @Expose
    private final boolean isEditorsChoice;
    @SerializedName("creators")
    @Expose
    private final Creators creators;

    public Publication(String id, String title, String universityId, String associationId, String number, String imageUrl, String description,
                       String fullDescription, String downloadUrl, Date releasedDate, boolean isPremium, int viewCount, int downloadCount,
                       float size, float rate, boolean isEditorsChoice, Creators creators) {
        this.id = id;
        this.title = title;
        this.universityId = universityId;
        this.associationId = associationId;
        this.number = number;
        this.imageUrl = imageUrl;
        this.description = description;
        this.fullDescription = fullDescription;
        this.downloadUrl = downloadUrl;
        this.releasedDate = releasedDate;
        this.isPremium = isPremium;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
        this.size = size;
        this.rate = rate;
        this.isEditorsChoice = isEditorsChoice;
        this.creators = creators;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUniversityId() {
        return universityId;
    }

    public String getAssociationId() {
        return associationId;
    }

    public String getNumber() {
        return number;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public float getSize() {
        return size;
    }

    public float getRate() {
        return rate;
    }

    public boolean isEditorsChoice() {
        return isEditorsChoice;
    }

    public Creators getCreators() {
        return creators;
    }

    public static class Creators {
        @SerializedName("association_name")
        @Expose
        private final String associationName;
        @SerializedName("university_name")
        @Expose
        private final String universityName;
        @SerializedName("ceo")
        @Expose
        private final String ceo;
        @SerializedName("cover_designer")
        @Expose
        private final String coverDesigner;
        @SerializedName("page_designer")
        @Expose
        private final String pageDesigner;

        public Creators(String associationName, String universityName, String ceo, String coverDesigner, String pageDesigner) {
            this.associationName = associationName;
            this.universityName = universityName;
            this.ceo = ceo;
            this.coverDesigner = coverDesigner;
            this.pageDesigner = pageDesigner;
        }

        public String getAssociationName() {
            return associationName;
        }

        public String getUniversityName() {
            return universityName;
        }

        public String getCeo() {
            return ceo;
        }

        public String getCoverDesigner() {
            return coverDesigner;
        }

        public String getPageDesigner() {
            return pageDesigner;
        }
    }
}
