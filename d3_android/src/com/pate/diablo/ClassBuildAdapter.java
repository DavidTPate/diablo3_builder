
package com.pate.diablo;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pate.diablo.model.ClassBuild;

public class ClassBuildAdapter extends BaseAdapter
{

    private ArrayList<ClassBuild> items;
    private Context               context;

    public ClassBuildAdapter(ArrayList<ClassBuild> items, Context context)
    {

        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount()
    {

        return items.size();
    }

    @Override
    public ClassBuild getItem(int position)
    {

        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.class_build_item, parent, false);

        TextView name = (TextView) v.findViewById(R.id.class_build_name);
        TextView className = (TextView) v.findViewById(R.id.class_build_class);

        name.setText(getItem(position).getName());
        className.setText("Class: " + getItem(position).getClassName());

        ImageButton share = (ImageButton) v.findViewById(R.id.class_build_share);
        ImageButton delete = (ImageButton) v.findViewById(R.id.class_build_delete);

        OnClickListener shareListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Log.i("Class_Builds", "Share clicked!");
            }

        };
        share.setOnClickListener(shareListener);
        
        OnClickListener deleteListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Log.i("Class_Builds", "Share clicked!");
            }

        };
        delete.setOnClickListener(deleteListener);
        
        return null;
    }
}
