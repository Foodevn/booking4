package com.example.booking4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.booking4.Adapter.CastListAdapter;
import com.example.booking4.Adapter.CategoryEachFilmAdapter;
import com.example.booking4.Models.Film;
import com.example.booking4.databinding.ActivityFilmDetailBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class FilmDetailActivity extends AppCompatActivity {
    private ActivityFilmDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFilmDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setVariable();
    }

    private void setVariable() {
        Film item = getIntent().getParcelableExtra("object");

        if (item == null) return;

        RequestOptions requestOptions = new RequestOptions().transform(
                new CenterCrop(),
                new GranularRoundedCorners(0f, 0f, 50f, 50f)
        );

        Glide.with(this)
                .load(item.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);

        binding.titleTxt.setText(item.getTitle());
        binding.imdbTxt.setText("IMDB " + item.getImdb());
        binding.movieTimeTxt.setText(item.getYear() + " - " + item.getTime());
        binding.movieSummaryTxt.setText(item.getDescription());

        binding.backBtn.setOnClickListener(v -> finish());

        binding.buyTicketBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SeatListActivity.class);
            intent.putExtra("film", item);
            startActivity(intent);
        });

        float radius = 10f;

        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        binding.blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(decorView.getBackground())
                .setBlurRadius(radius);

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

        if (item.getGenre() != null) {
            binding.genreView.setAdapter(new CategoryEachFilmAdapter(item.getGenre()));
            binding.genreView.setLayoutManager(new LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false
            ));
        }

        if (item.getCasts() != null) {
            binding.castListView.setLayoutManager(new LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false
            ));
            binding.castListView.setAdapter(new CastListAdapter(item.getCasts()));
        }
    }
}
