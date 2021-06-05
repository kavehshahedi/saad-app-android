package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.PublicationInformation;
import per.wsj.library.AndRatingBar;

public class PublicationCommentRecyclerAdapter extends RecyclerView.Adapter<PublicationCommentRecyclerAdapter.ViewHolder>{

    public final Context context;
    public final List<PublicationInformation> comments;

    public PublicationCommentRecyclerAdapter(Context context, List<PublicationInformation> comments){
        this.context = context;
        this.comments = comments;
    }
    @NonNull
    @Override
    public PublicationCommentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_publication_comment, parent, false);
        return new PublicationCommentRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationCommentRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        public ImageView commentImage;
        public TextView commentName;
        public AndRatingBar commentRate;
        public TextView commentDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentImage = itemView.findViewById(R.id.itemPublicationCommentImage);
            commentName = itemView.findViewById(R.id.itemPublicationCommentName);
            commentRate = itemView.findViewById(R.id.itemPublicationCommentRate);
            commentDesc = itemView.findViewById(R.id.itemPublicationCommentDesc);
        }
    }
}
