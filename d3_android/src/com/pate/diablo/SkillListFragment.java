package com.pate.diablo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.pate.diablo.sectionlist.EmptyItem;
import com.pate.diablo.sectionlist.EmptyItem.SkillType;
import com.pate.diablo.sectionlist.EntrySkillAdapter;
import com.pate.diablo.sectionlist.Item;
import com.pate.diablo.sectionlist.SectionItem;

public class SkillListFragment extends ListFragment
{
    static Context context;
    
    ArrayList<Item> items = new ArrayList<Item>();
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static SkillListFragment newInstance(String content, Context c) {
        context = c;
        SkillListFragment fragment = new SkillListFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
        items.add(new SectionItem("Left Click - Primary"));
        items.add(new EmptyItem("Choose Skill", 1, SkillType.Primary));
        
        items.add(new SectionItem("Right Click - Secondary"));
        items.add(new EmptyItem("Choose Skill", 2, SkillType.Secondary));
        
        items.add(new SectionItem("Action Bar Skills"));
        items.add(new EmptyItem("Choose Skill", 4 , SkillType.Defensive));
        items.add(new EmptyItem("Choose Skill", 9 , SkillType.Might));
        items.add(new EmptyItem("Choose Skill", 14, SkillType.Tactics));
        items.add(new EmptyItem("Choose Skill", 19, SkillType.Rage));
        
        items.add(new SectionItem("Passive Skills"));
        items.add(new EmptyItem("Choose Skill", 10, SkillType.Passive));
        items.add(new EmptyItem("Choose Skill", 20, SkillType.Passive));
        items.add(new EmptyItem("Choose Skill", 30, SkillType.Passive));

        EntrySkillAdapter adapter = new EntrySkillAdapter(context, items);

        setListAdapter(adapter);
        
        super.onCreate(savedInstanceState);
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
//            mContent = savedInstanceState.getString(KEY_CONTENT);
//        }
//        
//        
//
////        TextView text = new TextView(getActivity());
////        text.setGravity(Gravity.CENTER);
////        text.setText(mContent);
////        text.setTextSize(20 * getResources().getDisplayMetrics().density);
////        text.setPadding(20, 20, 20, 20);
////
////        LinearLayout layout = new LinearLayout(getActivity());
////        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
////        layout.setGravity(Gravity.CENTER);
////        layout.addView(text);
//
//        return layout;
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
