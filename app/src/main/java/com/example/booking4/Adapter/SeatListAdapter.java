package com.example.booking4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking4.Models.Seat;
import com.example.booking4.R;
import com.example.booking4.databinding.SeatItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SeatListAdapter extends RecyclerView.Adapter<SeatListAdapter.SeatViewHolder> {
    private final List<Seat> seatList;
    private final Context context;
    private final SelectedSeat selectedSeat;
    private final ArrayList<String> selectedSeatName = new ArrayList<>();

    public SeatListAdapter(List<Seat> seatList, Context context, SelectedSeat selectedSeat) {
        this.seatList = seatList;
        this.context = context;
        this.selectedSeat = selectedSeat;
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        private final SeatItemBinding binding;

        public SeatViewHolder(SeatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeatItemBinding binding = SeatItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SeatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);

        holder.binding.seat.setText(seat.getName());

        switch (seat.getStatus()) {
            case AVAILABLE:
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_available);
                holder.binding.seat.setTextColor(context.getColor(R.color.white));
                break;
            case SELECTED:
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_selected);
                holder.binding.seat.setTextColor(context.getColor(R.color.black));
                break;
            case UNAVAILABLE:
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_unavailable);
                holder.binding.seat.setTextColor(context.getColor(R.color.grey));
                break;
        }

        holder.binding.seat.setOnClickListener(v -> {
            switch (seat.getStatus()) {
                case AVAILABLE:
                    seat.setStatus(Seat.SeatStatus.SELECTED);
                    selectedSeatName.add(seat.getName());
                    notifyItemChanged(position);
                    break;
                case SELECTED:
                    seat.setStatus(Seat.SeatStatus.AVAILABLE);
                    selectedSeatName.remove(seat.getName());
                    notifyItemChanged(position);
                    break;
                default:
                    break;
            }

            String selected = String.join(",", selectedSeatName);
            selectedSeat.Return(selected, selectedSeatName.size());
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public interface SelectedSeat {
        void Return(String selectedName, int num);
    }
}
