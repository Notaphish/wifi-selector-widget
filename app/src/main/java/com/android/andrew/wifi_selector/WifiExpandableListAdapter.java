package com.android.andrew.wifi_selector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiExpandableListAdapter extends BaseExpandableListAdapter {

    private final List<String> groups = Lists.newArrayList("Favourites", "Everything else");
    private Map<String, List<WifiConfigurationDecorator>> categoryToWifiDecorators;

    public WifiExpandableListAdapter(List<WifiConfigurationDecorator> incFavourites, List<WifiConfigurationDecorator> others) {
        super();
        categoryToWifiDecorators = new HashMap<>();
        categoryToWifiDecorators.put(groups.get(0), incFavourites);
        categoryToWifiDecorators.put(groups.get(1), others);

    }

    public WifiConfigurationDecorator getWifiDecorator(int groupPosition, int childPosition) {
        return getWifiDecoratorsForGroup(groupPosition).get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getWifiDecoratorsForGroup(groupPosition).size();
    }

    private List<WifiConfigurationDecorator> getWifiDecoratorsForGroup(int groupPosition) {
        return categoryToWifiDecorators.get(groups.get(groupPosition));
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int indexOfDecoratorWithinGroup) {
        return getWifiDecoratorsForGroup(groupPosition).get(indexOfDecoratorWithinGroup);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int indexOfDecoratorWithinGroup) {
        return indexOfDecoratorWithinGroup;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_header_fav, null);

        }
        TextView title = (TextView) convertView.findViewById(R.id.listViewHeader);
        title.setText(groups.get(groupPosition));

        return title;
    }

    @Override
    public View getChildView(int groupPosition, int indexOfDecoratorWithinGroup, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item, null);
        }

        TextView wifiName = (TextView) convertView.findViewById(R.id.listViewItem);
        wifiName.setText(getWifiDecoratorsForGroup(groupPosition).get(indexOfDecoratorWithinGroup).toString());
        return wifiName;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
