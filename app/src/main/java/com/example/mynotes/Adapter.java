package com.example.mynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<model> NoteList;
    List<model> newlist;

    public Adapter(Context context, Activity activity, List<model> noteList) {
        this.context = context;
        this.activity = activity;
        NoteList = noteList;
        newlist = new ArrayList<>(NoteList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layput,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.MyViewHolder holder, int position) {
        holder.title.setText(NoteList.get(position).getTitle());
        holder.description.setText(NoteList.get(position).getDescription());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,update.class);
                intent.putExtra("title",NoteList.get(position).getTitle());
                intent.putExtra("description",NoteList.get(position).getDescription());
                intent.putExtra("Id",NoteList.get(position).getId());
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return NoteList.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<model> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(newlist);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (model item:newlist)
                {
                    if (item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            NoteList.clear();
            NoteList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title ,description;
        RelativeLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            layout = itemView.findViewById(R.id.note_layout);
        }
    }

    public List<model> getList()
    {
        return NoteList;
    }
    public void removeItem(int position)
    {
        NoteList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(model item , int position)
    {
        NoteList.add(position,item);
        notifyItemInserted(position);

    }
}
