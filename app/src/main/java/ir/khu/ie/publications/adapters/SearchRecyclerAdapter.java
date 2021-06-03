package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;
import per.wsj.library.AndRatingBar;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Publication> searchResults;

    public SearchRecyclerAdapter(Context context, List<Publication> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Publication publication = searchResults.get(position);

        holder.publicationName.setText(publication.getTitle());
        holder.universityName.setText(publication.getCreators().getUniversityName());
        holder.associationName.setText(publication.getCreators().getAssociationName());
        holder.rateBar.setRating(publication.getRate());

        Picasso.get().load(publication.getImageUrl()).into(holder.publicationImage);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView publicationImage;
        public AppCompatTextView publicationName, universityName, associationName;
        public AndRatingBar rateBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            publicationImage = itemView.findViewById(R.id.itemSearchResultPublicationImage);
            publicationName = itemView.findViewById(R.id.itemSearchResultPublicationName);
            universityName = itemView.findViewById(R.id.itemSearchResultPublicationUniversityName);
            associationName = itemView.findViewById(R.id.itemSearchResultPublicationAssociationName);
            rateBar = itemView.findViewById(R.id.itemSearchResultPublicationRate);
        }
    }
}
