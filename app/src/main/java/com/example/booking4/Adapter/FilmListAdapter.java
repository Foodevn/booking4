package com.example.booking4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.booking4.Activity.FilmDetailActivity;
import com.example.booking4.Models.Film;
import com.example.booking4.databinding.ViewholderFlimBinding;

import java.util.ArrayList;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    private final ArrayList<Film> items;
    private Context context;

    public FilmListAdapter(ArrayList<Film> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderFlimBinding binding;

        public ViewHolder(ViewholderFlimBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Film film) {
            binding.nameTxt.setText(film.getTitle());

            RequestOptions requestOptions = new RequestOptions()
                    .transform(new CenterCrop(), new RoundedCorners(30));

            Glide.with(context)
                    .load(film.getPoster())
                    .apply(requestOptions)
                    .into(binding.pic);

            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(context, FilmDetailActivity.class);
                intent.putExtra("object", film);
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderFlimBinding binding = ViewholderFlimBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
