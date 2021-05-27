package ir.khu.ie.publications.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.adapters.HomeCategoryModel;

public class HomeCategoryRecyclerAdapter extends RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ViewHolder> {

    private ArrayList<HomeCategoryModel> CategoriesList;

    @NonNull
    @Override
    public HomeCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_categoryrecycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryRecyclerAdapter.ViewHolder holder, int position) {
        HomeCategoryModel currentItem = CategoriesList.get(position);

        holder.CategoryName.setText(currentItem.getCategoryName());
        //holder.CardsRecycler.setrecy(currentItem.getCardsRecyclerView());
    }

    @Override
    public int getItemCount() {
        return CategoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CategoryName;
        public RecyclerView CardsRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryName = itemView.findViewById(R.id.Home_RecyclerCategoryText);
            CardsRecycler = itemView.findViewById(R.id.Home_CardsRecylcerView);
        }
    }

    public HomeCategoryRecyclerAdapter(ArrayList<HomeCategoryModel> arrayList){
        CategoriesList = arrayList;
    }
}
