package com.akina.healthcenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.ViewHolder> {
    private List<DataObj> m_datalist;
    private LayoutInflater m_inflater;
    String m_datatype;

    CustomRVAdapter(Context context, String datatype) {
        this.m_inflater = LayoutInflater.from(context);
        this.m_datatype = datatype;

        switch (datatype)
        {
            case ("WEIGHT"):
                this.m_datalist = MainActivity.wList;
                break;
            case ("BLOODGLUCOSE"):
                this.m_datalist = MainActivity.bgList;
                break;
            case ("MOOD"):
                this.m_datalist = MainActivity.mList;
                break;

        }
    }

    public void removeItem(int position) {
        this.m_datalist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.m_datalist.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = m_inflater.inflate(R.layout.data_bar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataObj elem = m_datalist.get(position);

        DateFormat dateFormat = new SimpleDateFormat("d MMMM YYYY, h:mm a", Locale.ENGLISH);
        holder.m_date.setText(dateFormat.format(elem.m_time.getTimeInMillis()));
        switch(m_datatype)
        {
            case ("WEIGHT"):
                holder.m_icon.setVisibility(View.INVISIBLE);
                holder.m_data.setVisibility(View.VISIBLE);
                holder.m_data.setText(Integer.toString(elem.m_data));
                holder.m_unit.setVisibility(View.VISIBLE);
                holder.m_unit.setText("kg");
                break;
            case ("BLOODGLUCOSE"):
                holder.m_icon.setVisibility(View.INVISIBLE);
                holder.m_data.setVisibility(View.VISIBLE);
                holder.m_data.setText(Integer.toString(elem.m_data));
                holder.m_unit.setVisibility(View.VISIBLE);
                holder.m_unit.setText("mmol/L");
                break;
            case ("MOOD"):
                holder.m_icon.setVisibility(View.VISIBLE);
                holder.m_data.setVisibility(View.INVISIBLE);
                holder.m_unit.setVisibility(View.INVISIBLE);
                holder.m_unit.setText("");
                switch(elem.m_data)
                {
                    case 1:
                        holder.m_icon.setImageResource(R.mipmap.bad);
                        break;
                    case 2:
                        holder.m_icon.setImageResource(R.mipmap.not_so_good);
                        break;
                    case 3:
                        holder.m_icon.setImageResource(R.mipmap.ok);
                        break;
                    case 4:
                        holder.m_icon.setImageResource(R.mipmap.happy);
                        break;
                    case 5:
                        holder.m_icon.setImageResource(R.mipmap.excellent);
                        break;
                }

                break;

        }
    }

    @Override
    public int getItemCount() {
        return m_datalist.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView m_unit;
        TextView m_data;
        TextView m_date;
        ImageView m_icon;

        ViewHolder(View itemView) {
            super(itemView);

            m_unit = itemView.findViewById(R.id.unit_txt);
            m_data = itemView.findViewById(R.id.data_txt);
            m_date = itemView.findViewById(R.id.date_txt);
            m_icon = itemView.findViewById(R.id.obj_icon);
        }
    }
}