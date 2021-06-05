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
import ir.khu.ie.publications.models.publications.PublicationInformation;

public class PublicationInformationRecyclerAdapter extends RecyclerView.Adapter<PublicationInformationRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<PublicationInformation> information;

    public PublicationInformationRecyclerAdapter(Context context, List<PublicationInformation> information) {
        this.context = context;
        this.information = information;
    }

    @NonNull
    @Override
    public PublicationInformationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_publication_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationInformationRecyclerAdapter.ViewHolder holder, int position) {
        holder.informationTitle.setText(information.get(position).getTitle());
        holder.informationValue.setText(information.get(position).getValue());

        if (position == information.size() - 1) holder.separatingLine.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView informationTitle;
        public TextView informationValue;
        public View separatingLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            informationTitle = itemView.findViewById(R.id.itemPublicationInformationTitle);
            informationValue = itemView.findViewById(R.id.itemPublicationInformationDesc);
            separatingLine = itemView.findViewById(R.id.itemPublicationSeparatingLine);
        }
    }
}
