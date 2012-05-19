
package com.wemakestuff.d3builder;

import com.wemakestuff.d3builder.model.ClassBuild;

public interface OnLoadBuildClickInterface
{
    public void onLoadBuildClick(ClassBuild build);
    
    public void onLoadBuildDismiss();

    public void onDeleteBuild(ClassBuild build);
}
