
package com.wemakestuff.diablo3builder.global;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.wemakestuff.diablo3builder.model.ClassBuild;

public class Funcs
{

    public static void shareBuild(Context context, String subject, String content)
    {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(intent, "Share using..."));
    }

    public static ArrayList<ClassBuild> getClassAvailableBuilds(Context context)
    {

        ArrayList<ClassBuild> b = new ArrayList<ClassBuild>();

        SharedPreferences valVals = context.getSharedPreferences("saved_build_value", Context.MODE_PRIVATE);
        SharedPreferences clssVals = context.getSharedPreferences("saved_build_class", Context.MODE_PRIVATE);
        SharedPreferences followerVals = context.getSharedPreferences("saved_build_follower", Context.MODE_PRIVATE);

        Map<String, String> urls = (Map<String, String>) valVals.getAll();
        Map<String, String> classes = (Map<String, String>) clssVals.getAll();
        Map<String, String> followers = (Map<String, String>) followerVals.getAll();

        for (Map.Entry<String, String> pairs : urls.entrySet())
        {
            if (classes.containsKey(pairs.getKey()) || followers.containsKey(pairs.getKey()))
            {
                ClassBuild c = new ClassBuild(pairs.getKey(), classes.get(pairs.getKey()), pairs.getValue(), followers.get(pairs.getKey()));
                b.add(c);
            }
        }

        return b;
    }
}
