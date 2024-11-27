package com.example.reqresconsumapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reqresconsumapplication.modèle.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    // Constructeur
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getFirst_name() + " " + user.getLast_name());
        holder.email.setText(user.getEmail());

        // Charger l'image avec Glide (ou autre bibliothèque)
        Glide.with(context)
                .load(user.getAvatar())
                .circleCrop()
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Classe interne pour le ViewHolder
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        ImageView avatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
