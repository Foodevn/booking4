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
import com.example.booking4.Models.SliderItems;
import com.example.booking4.databinding.ViewholderSliderBinding;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final List<SliderItems> sliderItems;
    private final ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<SliderItems> newItems = new ArrayList<>(sliderItems); // Sao chép danh sách gốc
            sliderItems.addAll(newItems); // Thêm bản sao vào cuối danh sách
            notifyDataSetChanged();
        }
    };

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderSliderBinding binding;

        public SliderViewHolder(ViewholderSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SliderItems sliderItem) {
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
        holder.bind(sliderItems.get(position));

        if (position == sliderItems.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
}

