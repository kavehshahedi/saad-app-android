package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<HomeCardRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<Publication> publications;

    public HomeCardRecyclerAdapter(Context context, List<Publication> cardsData) {
        this.context = context;
        this.publications = cardsData;
    }

    @NonNull
    @Override
    public HomeCardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_category_card, parent, false);
        return new HomeCardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardRecyclerAdapter.ViewHolder holder, int position) {
        Publication currentItem = publications.get(position);

        holder.publicationName.setText(currentItem.getTitle());
        holder.description.setText(currentItem.getDescription());
        Picasso.get().load(currentItem.getImageUrl()).into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return publications.size();
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
