package com.wemakestuff.diablo3builder.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wemakestuff.diablo3builder.R;

public class D3Application extends Application
{
    private static DataModel mDataModel;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static DataModel getInstance()
    {
        if (mDataModel == null)
        {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.classes)));

            String fakeJson = "";
            String line;
            try
            {
                line = reader.readLine();
                while (line != null)
                {
                    fakeJson = fakeJson + line;
                    line = reader.readLine();
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mDataModel = gson.fromJson(fakeJson, DataModel.class);
        }

        return mDataModel;

    }
}
