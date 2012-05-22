package com.wemakestuff.diablo3builder.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class Replacer
{
    public enum D3Color {
        DIABLO_GREEN(0xFF01F301),
        DIABLO_GOLD(0xFFD49E43);
        
        private int mColor;
        
        D3Color(int color)
        {
            mColor = color;
        }
        
        int getColor()
        {
            return mColor;
        }
    }
    
    public static CharSequence replace(CharSequence source, String regex, D3Color color)
    {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return doReplace(source, matcher, color);
    }

    private static CharSequence doReplace(CharSequence mSource, Matcher mMatcher, D3Color mColor)
    {
        
        int mAppendPosition = 0;
        SpannableStringBuilder buffer = new SpannableStringBuilder();
        while (mMatcher.find())
        {
            buffer.append(mSource.subSequence(mAppendPosition, mMatcher.start()));

            SpannableString text = new SpannableString(mMatcher.group());
            text.setSpan(new ForegroundColorSpan(mColor.getColor()), 0, text.length(), 0);
            buffer.append(text);

            mAppendPosition = mMatcher.end();
        }
        buffer.append(mSource.subSequence(mAppendPosition, mSource.length()));

        return buffer;
    }

}
