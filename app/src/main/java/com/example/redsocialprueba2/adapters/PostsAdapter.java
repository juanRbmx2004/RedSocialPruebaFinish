package com.example.redsocialprueba2.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostsAdapter extends FirestoreRecyclerAdapter<Post,PostsAdapter.ViewHolder> {

    Context contexto;

    public PostsAdapter(FirestoreRecyclerOptions<Post> options, Context contexto){
        super(options);
        this.contexto = contexto;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post post) {
        holder.textV_title.setText(post.getTitle());
        holder.textV_description.setText(post.getDescription());
        if(post.getmImagen1()!=null){
            if(!post.getmImagen1().isEmpty()){
                Picasso.with(contexto).load(post.getmImagen1()).into(holder.imgPost);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textV_title;
        TextView textV_description;
        ImageView imgPost;

        public ViewHolder(View view){
            super(view);
            textV_title = view.findViewById(R.id.titlePostCard);
            textV_description = view.findViewById(R.id.descriptionPostCard);
            imgPost = view.findViewById(R.id.imgPostCard);
        }
    }

}
