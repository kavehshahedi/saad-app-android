package ir.khu.ie.publications.models.adapters;

public class HomeCardsModel {
    private String imageUrl;
    private String publicationName;
    private String publicationDescription;

    public HomeCardsModel(String url,String name,String description){
        imageUrl = url;
        publicationName = name;
        publicationDescription = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getPublicationDescription() {
        return publicationDescription;
    }

    public void setPublicationDescription(String publicationDescription) {
        this.publicationDescription = publicationDescription;
    }
}
