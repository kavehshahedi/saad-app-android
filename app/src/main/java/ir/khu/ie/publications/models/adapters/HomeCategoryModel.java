package ir.khu.ie.publications.models.adapters;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class HomeCategoryModel {
    private String CategoryId;
    private String CategoryName;
    private ArrayList<HomeCardsModel> CardsList;

    public HomeCategoryModel(String id,String name, ArrayList<HomeCardsModel> cardsList){
        CategoryId = id;
        CategoryName = name;
        CardsList = cardsList;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public ArrayList<HomeCardsModel> getCardsList() {
        return CardsList;
    }

    public void setCardsList(ArrayList<HomeCardsModel> cardsList) {
        CardsList = cardsList;
    }
}
