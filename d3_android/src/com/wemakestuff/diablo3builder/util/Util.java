package com.wemakestuff.diablo3builder.util;

import java.lang.reflect.Field;

import android.util.Log;

import com.wemakestuff.diablo3builder.R;

public class Util
{
    // Look up the resource through reflection, it's a bit faster than
    // getResources().getIdentifier(...)
    public static int findImageResource(String name)
    {
        int iconImage = 0;

        try
        {
            Class res = R.drawable.class;
            Field field = res.getField(name);
            iconImage = field.getInt(null);
        }
        catch (Exception e)
        {
            Log.e("Image Loader", "Failed to get drawable id", e);
        }

        return iconImage;
    }
}
