package com.example.booking4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking4.Models.Cast;
import com.example.booking4.databinding.ViewholderCastBinding;

import java.util.ArrayList;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.ViewHolder> {
    private final ArrayList<Cast> cast;
    private Context context;

    public CastListAdapter(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderCastBinding binding;

        public ViewHolder(ViewholderCastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cast castItem) {
            if (context != null) {
                Glide.with(context)
                        .load(castItem.getPicUrl())
                        .into(binding.actorImage);
            }
            binding.nameTxt.setText(castItem.getActor());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderCastBinding binding = ViewholderCastBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cast.get(position));
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }
}
