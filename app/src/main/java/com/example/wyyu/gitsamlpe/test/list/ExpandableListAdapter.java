package com.example.wyyu.gitsamlpe.test.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/10/10.
 **/

class ExpandableListAdapter extends BaseExpandableListAdapter {

    static final String[] GROUP_NAME = new String[] { "西游记", "三国演义", "水浒传" };
    static final String[][] CHILD_NAME =
        new String[][] { { "孙悟空", "猪八戒", "沙悟净" }, { "刘备", "孙权", "曹操" }, { "宋江", "武松", "鲁智深" } };

    private Activity activity;

    ExpandableListAdapter(Activity activity) {
        this.activity = activity;
    }

    // 获取分组个数
    @Override public int getGroupCount() {
        return GROUP_NAME.length;
    }

    // 获取指定分组中的元素个数
    @Override public int getChildrenCount(int groupPosition) {
        return CHILD_NAME[groupPosition].length;
    }

    // 获取指定分组
    @Override public Object getGroup(int groupPosition) {
        return GROUP_NAME[groupPosition];
    }

    // 获取指定元素
    @Override public Object getChild(int groupPosition, int childPosition) {
        return CHILD_NAME[groupPosition][childPosition];
    }

    // 获取分组ID
    @Override public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 获取元素ID
    @Override public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 分组和元素是否有稳定ID，即数据的改变不会改变它们的ID
    @Override public boolean hasStableIds() {
        return true;
    }

    // 获取指定分组的视图
    @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
        ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                .inflate(R.layout.layout_expand_list_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.title = convertView.findViewById(R.id.expand_item_group_txt);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.title.setText(GROUP_NAME[groupPosition]);
        return convertView;
    }

    // 获取指定元素的视图
    @Override public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
        View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                .inflate(R.layout.layout_expand_list_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.info = convertView.findViewById(R.id.expand_item_child_txt);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.info.setText(CHILD_NAME[groupPosition][childPosition]);
        return convertView;
    }

    // 指定位置的元素是否可选中
    @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class GroupViewHolder {

        private TextView title;
    }

    private static class ChildViewHolder {

        private TextView info;
    }
}
