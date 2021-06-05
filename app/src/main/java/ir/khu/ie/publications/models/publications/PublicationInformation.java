package ir.khu.ie.publications.models.publications;

public class PublicationInformation {

    private final String title;
    private final String value;

    public PublicationInformation(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}
