package com.wemakestuff.d3builder.model;

import android.app.Application;

public class D3Application extends Application
{
    public static DataModel dataModel;
    
    public static void setDataModel(DataModel dm)
    {
        dataModel = dm;
    }
}
