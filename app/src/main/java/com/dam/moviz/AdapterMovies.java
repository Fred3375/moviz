package com.dam.moviz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdapterMovies extends FirestoreRecyclerAdapter<ModelMovie, AdapterMovies.MoviesViewHolder> {

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAffiche;
        private TextView tvTitre, tvSynopsis;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAffiche = itemView.findViewById(R.id.ivAffiche);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvSynopsis = itemView.findViewById(R.id.tvSynopsis);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION && movieClickListener != null){
                            DocumentSnapshot movieSnapshot = getSnapshots().getSnapshot(pos);
                            movieClickListener.onItemClick(movieSnapshot, pos);
                        }
                    }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    private OnItemClickListener movieClickListener;

    public void setOnItemClickListener(OnItemClickListener movieClickListener){
        this.movieClickListener = movieClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MoviesViewHolder holder, int position, @NonNull ModelMovie model) {
        String afficheUrl = model.getAffiche();
        String titre = model.getTitre();
        String synopsis = model.getSysnopsis();

        holder.tvTitre.setText(titre);
        holder.tvSynopsis.setText(synopsis);

        // Glide
        RequestOptions errorManagement = new RequestOptions()
                .centerCrop() // center / crop pour les images de remplacement
                .error(R.mipmap.ic_launcher) // cas erreur
                .placeholder(R.mipmap.ic_launcher); // cas pas d'image
        Context context = holder.ivAffiche.getContext();
        Glide.with(context)
                .load(afficheUrl)
                .apply(errorManagement)
                .fitCenter()
                .override(150, 150)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivAffiche);

    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        Log.i("TAG", "onError: " + e.getMessage());
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterMovies(@NonNull FirestoreRecyclerOptions<ModelMovie> options) {
        super(options);
    }

}
