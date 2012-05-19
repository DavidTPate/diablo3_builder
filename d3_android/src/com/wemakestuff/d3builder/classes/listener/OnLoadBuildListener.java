package com.wemakestuff.d3builder.classes.listener;

import com.wemakestuff.d3builder.model.ClassBuild;


public abstract class OnLoadBuildListener
{
    public abstract void onLoadBuild(ClassBuild build);
    public abstract void onShareBuild(ClassBuild build);
    public abstract void onDeleteBuild(ClassBuild build);
}
