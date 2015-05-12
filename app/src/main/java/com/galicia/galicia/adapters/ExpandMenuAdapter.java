package com.galicia.galicia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.galicia.galicia.R;
import com.galicia.galicia.untils.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 08.05.2015.
 */
public class ExpandMenuAdapter extends BaseExpandableListAdapter {
    private List<ArrayList<String>> mDataList;
    private List<String> itemNames;
    private Context mContext;
    private ExpandableListView mParentViewGroup;

    public ExpandMenuAdapter(final Context mContext, final List<String> names, List<ArrayList<String>> mDataList) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.itemNames = names;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeaderHolder holder;
        if (convertView == null){
            mParentViewGroup = (ExpandableListView) parent;
            convertView = LayoutInflater.from(mContext).inflate(R.layout.title_exp_menu, null);
            holder = new HeaderHolder();
            holder.header = (TextView) convertView.findViewById(R.id.tvTitleExpMEnu);
            convertView.setTag(holder);
        } else {
            holder = (HeaderHolder) convertView.getTag();
        }

        holder.header.setText(itemNames.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        return null;
        ChildrenHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_menu, null);
            holder = new ChildrenHolder();
            holder.hList = (HorizontalListView) convertView.findViewById(R.id.hlvHorizontalMenu);
            convertView.setTag(holder);
        } else {
            holder = (ChildrenHolder) convertView.getTag();
        }
        HorizontalMenuAdapter adapter = new HorizontalMenuAdapter(mContext, mDataList.get(groupPosition));
        holder.hList.setAdapter(adapter);
        return convertView;
    }

    @Override
    public int getGroupCount() {
        return itemNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class HeaderHolder {
        TextView header;
    }

    private class ChildrenHolder {
        HorizontalListView hList;
    }
}
