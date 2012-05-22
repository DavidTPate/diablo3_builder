
package com.wemakestuff.diablo3builder;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wemakestuff.diablo3builder.model.ClassBuild;

public class ClassBuildAdapter extends BaseAdapter
{

    private ArrayList<ClassBuild>    items;
    private Context                  context;
    private OnLoadBuildClickListener listener;

    public ClassBuildAdapter(ArrayList<ClassBuild> items, Context context, OnLoadBuildClickListener listener)
    {

        this.items = items;
        this.context = context;
        this.listener = listener;
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

                SharedPreferences valVals = context.getSharedPreferences("saved_build_value", Context.MODE_PRIVATE);
                SharedPreferences.Editor valEdit = valVals.edit();

                SharedPreferences clssVals = context.getSharedPreferences("saved_build_class", Context.MODE_PRIVATE);
                SharedPreferences.Editor clssEdit = clssVals.edit();

                SharedPreferences followerVals = context.getSharedPreferences("saved_build_follower", Context.MODE_PRIVATE);
                SharedPreferences.Editor followEdit = followerVals.edit();

                valEdit.remove(item.getName());
                clssEdit.remove(item.getName());
                followEdit.remove(item.getName());

                valEdit.commit();
                clssEdit.commit();
                followEdit.commit();
                listener.onDeleteBuild(item);
            }

        };
        delete.setOnClickListener(deleteListener);

        OnClickListener shareListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                String subject = context.getString(R.string.Check_Out_My) + " " + item.getClassName() + " " + context.getString(R.string.Build);
                String classes = item.getUrl();
                String followers = item.getFollowersUrl();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(android.content.Intent.EXTRA_TEXT, subject + " " + classes + (followers != null ? " and my followers: " + followers : ""));
                context.startActivity(Intent.createChooser(intent, "Share using"));
                listener.onLoadBuildDismiss();
            }

        };
        share.setOnClickListener(shareListener);

        OnClickListener loadListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                listener.onLoadBuildClick(item);
                listener.onLoadBuildDismiss();
            }

        };
        load.setOnClickListener(loadListener);

        return v;
    }
}
