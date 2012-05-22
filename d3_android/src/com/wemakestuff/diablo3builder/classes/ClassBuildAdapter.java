
package com.wemakestuff.diablo3builder.classes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.R;
import com.wemakestuff.diablo3builder.classes.listener.OnLoadBuildListener;
import com.wemakestuff.diablo3builder.model.ClassBuild;

public class ClassBuildAdapter extends BaseAdapter
{

    private ArrayList<ClassBuild> items;
    private Context               context;
    private OnLoadBuildListener   loadListener;

    public ClassBuildAdapter(ArrayList<ClassBuild> items, Context context)
    {

        this.items = items;
        this.context = context;
    }

    public void setOnLoadBuildListener(OnLoadBuildListener loadListener)
    {

        this.loadListener = loadListener;
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

        final ClassBuild item = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.class_build_item, parent, false);

        TextView name = (TextView) v.findViewById(R.id.class_build_name);
        TextView className = (TextView) v.findViewById(R.id.class_build_class);

        name.setText(item.getName());

        className.setText("Class: " + item.getClassName());

        ImageButton delete = (ImageButton) v.findViewById(R.id.class_build_delete);
        ImageButton share = (ImageButton) v.findViewById(R.id.class_build_share);
        ImageButton load = (ImageButton) v.findViewById(R.id.class_build_load);

        OnClickListener deleteListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                loadListener.onDeleteBuild(item);
            }

        };
        delete.setOnClickListener(deleteListener);

        OnClickListener shareListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                loadListener.onShareBuild(item);
            }

        };
        share.setOnClickListener(shareListener);

        OnClickListener loadListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                ClassBuildAdapter.this.loadListener.onLoadBuild(item);
            }

        };
        load.setOnClickListener(loadListener);

        return v;
    }
}
