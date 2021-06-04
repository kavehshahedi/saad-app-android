package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.khu.ie.publications.R;

public class PublicationInformationRecyclerAdapter extends RecyclerView.Adapter<PublicationInformationRecyclerAdapter.ViewHolder>{
    private final Context context;
    private final List<> informations;

    public PublicationInformationRecyclerAdapter(Context context, List<> informations){
        this.context = context;
        this.informations = informations;
    }

    @NonNull
    @Override
    public PublicationInformationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_publicationactivity_information, parent, false);
        return new PublicationInformationRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationInformationRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return informations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView informationTitle;
        public TextView informationDesc;
        public View seperatingLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            informationTitle = itemView.findViewById(R.id.itemPublicationInformationTitle);
            informationDesc = itemView.findViewById(R.id.itemPublicationInformationDesc);
            seperatingLine = itemView.findViewById(R.id.itemPublicationSeparatingLine);
        }
    }
}
