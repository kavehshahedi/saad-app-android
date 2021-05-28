package ir.khu.ie.publications.models.adapters;

import java.util.ArrayList;

public class HomeCategoryModel {
    private String categoryId;
    private String categoryName;
    private ArrayList<HomeCardsModel> cardsList;

    public HomeCategoryModel(String id, String name, ArrayList<HomeCardsModel> cardsList) {
        categoryId = id;
        categoryName = name;
        this.cardsList = cardsList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<HomeCardsModel> getCardsList() {
        return cardsList;
    }

    public void setCardsList(ArrayList<HomeCardsModel> cardsList) {
        this.cardsList = cardsList;
    }
}
