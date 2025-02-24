package com.example.booking4.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking4.R;
import com.example.booking4.databinding.ItemTimeBinding;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private final List<String> timeSlots;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public TimeAdapter(List<String> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeBinding binding;

        public TimeViewHolder(ItemTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String time) {
            binding.TextViewTime.setText(time);

            if (getAdapterPosition() == selectedPosition) {
                binding.TextViewTime.setBackgroundResource(R.drawable.white_bg);
                binding.TextViewTime.setTextColor(
                        ContextCompat.getColor(itemView.getContext(), R.color.black)
                );
            } else {
                binding.TextViewTime.setBackgroundResource(R.drawable.light_black_bg);
                binding.TextViewTime.setTextColor(
                        ContextCompat.getColor(itemView.getContext(), R.color.white)
                );
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

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimeBinding binding = ItemTimeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TimeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.bind(timeSlots.get(position));
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }
}
