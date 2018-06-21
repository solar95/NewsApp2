package com.example.solar.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class NewsAdapter  extends ArrayAdapter<NewsArticle> {

    public NewsAdapter(Context context, List<NewsArticle> n) {
        super(context, 0, n);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View lv = convertView;

        if (lv == null) {
            lv = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        NewsArticle current = getItem(position);

        TextView title = lv.findViewById(R.id.title);
        title.setText(current.getTitle());

        TextView date = lv.findViewById(R.id.date);
        date.setText(current.getDate());

        TextView section = lv.findViewById(R.id.section);
        section.setText(current.getSection());

        TextView author = lv.findViewById(R.id.author);
        author.setText(current.getAuthor());
        
        TextView link = lv.findViewById(R.id.link);
        link.setText(current.getLink());

        return lv;
    }
}
