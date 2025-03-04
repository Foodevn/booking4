package com.example.booking4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.booking4.Models.SliderItem;
import com.example.booking4.databinding.ViewholderSliderBinding;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final List<SliderItem> sliderItems;
    private final List<SliderItem> fakesliderItems=new ArrayList<>();
    private Context context;

    public SliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;
        fakesliderItems.add(sliderItems.get(sliderItems.size() - 1));
        fakesliderItems.addAll(sliderItems);
        fakesliderItems.add(sliderItems.get(0));

    }



    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderSliderBinding binding;

        public SliderViewHolder(ViewholderSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SliderItem sliderItem) {
            if (context != null) {
                Glide.with(context)
                        .load(sliderItem.getImage())
                        .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(60)))
                        .into(binding.imageSlide);
            }
        }
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderSliderBinding binding = ViewholderSliderBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        int virtualPosition = position % sliderItems.size();
        SliderItem sliderItem = sliderItems.get(virtualPosition);

        Glide.with(context)
                .load(sliderItem.getImage())
                .transform(new CenterCrop(), new RoundedCorners(60))
                .into(holder.binding.imageSlide);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}

