package com.example.booking4.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking4.R;
import com.example.booking4.databinding.ItemDateBinding;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private final List<String> timeSlots;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public DateAdapter(List<String> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateBinding binding;

        public DateViewHolder(ItemDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String date) {
            String[] dateParts = date.split("/");
            if (dateParts.length == 3) {
                binding.dayTxt.setText(dateParts[0]);
                binding.datMonthTxt.setText(dateParts[1] + " " + dateParts[2]);

                if (getAdapterPosition() == selectedPosition) {
                    binding.mailLayout.setBackgroundResource(R.drawable.white_bg);
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.light_black_bg);
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                }

                binding.getRoot().setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        lastSelectedPosition = selectedPosition;
                        selectedPosition = position;
                        notifyItemChanged(lastSelectedPosition);
                        notifyItemChanged(selectedPosition);
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateBinding binding = ItemDateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        holder.bind(timeSlots.get(position));
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }
}
