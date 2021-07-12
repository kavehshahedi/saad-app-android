package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;
import per.wsj.library.AndRatingBar;

public class PublicationCommentRecyclerAdapter extends RecyclerView.Adapter<PublicationCommentRecyclerAdapter.ViewHolder> {

    public final Context context;
    public final List<Publication.PublicationComment> comments;

    public PublicationCommentRecyclerAdapter(Context context, List<Publication.PublicationComment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public PublicationCommentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_publication_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationCommentRecyclerAdapter.ViewHolder holder, int position) {
        Publication.PublicationComment comment = comments.get(position);

        holder.commentName.setText(comment.getName());
        holder.commentMessage.setText(comment.getMessage());
        holder.commentRate.setRating(comment.getRate());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView commentName;
        public AndRatingBar commentRate;
        public TextView commentMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentName = itemView.findViewById(R.id.itemPublicationCommentName);
            commentRate = itemView.findViewById(R.id.itemPublicationCommentRate);
            commentMessage = itemView.findViewById(R.id.itemPublicationCommentDesc);
        }
    }
}
