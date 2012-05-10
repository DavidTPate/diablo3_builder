package com.pate.diablo.sectionlist;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EntrySkillAdapter extends ArrayAdapter<Item>
{

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater  vi;

    public EntrySkillAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item i = items.get(position);
        return i.inflate(context, i);
        
    }

}
