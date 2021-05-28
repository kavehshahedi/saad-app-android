package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.adapters.HomeCardsModel;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<HomeCardRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<HomeCardsModel> cardsData;

    public HomeCardRecyclerAdapter(Context context, ArrayList<HomeCardsModel> cardsData) {
        this.context = context;
        this.cardsData = cardsData;
    }

    @NonNull
    @Override
    public HomeCardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_category_recycler, parent, false);
        return new HomeCardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardRecyclerAdapter.ViewHolder holder, int position) {
        HomeCardsModel currentItem = cardsData.get(position);

        holder.publicationName.setText(currentItem.getPublicationName());
        holder.description.setText(currentItem.getPublicationDescription());
        Picasso.get().load(currentItem.getImageUrl()).into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return cardsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView cardImage;
        public AppCompatTextView publicationName;
        public AppCompatTextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.itemHomeCategoryCardImage);
            publicationName = itemView.findViewById(R.id.itemHomeCategoryCardPublicationName);
            description = itemView.findViewById(R.id.itemHomeCategoryCardDescription);
        }
    }
}
