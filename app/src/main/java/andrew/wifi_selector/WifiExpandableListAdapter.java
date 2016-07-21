package andrew.wifi_selector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WifiExpandableListAdapter extends BaseExpandableListAdapter {

    public static final int FAVOURITE_GROUP = 0;
    public static final int KNOWN_NETWORK_GROUP = 1;

    private final List<String> groups;
    private Map<String, Set<WifiConfigurationDecorator>> groupToWifiDecorators;

    public WifiExpandableListAdapter(Context context, Set<WifiConfigurationDecorator> incFavourites, Set<WifiConfigurationDecorator> others) {
        super();
        groupToWifiDecorators = new HashMap<>();
        groups = Lists.newArrayList( context.getString( R.string.list_view_header_favourite), context.getString(R.string.list_view_header_known));

        groupToWifiDecorators.put(groups.get(FAVOURITE_GROUP), incFavourites );
        groupToWifiDecorators.put(groups.get(KNOWN_NETWORK_GROUP), others);
    }

    public boolean isFavouriteGroup(int group){
        return group == FAVOURITE_GROUP;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getWifiDecoratorsForGroup(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public WifiConfigurationDecorator getChild(int groupPosition, int indexOfDecoratorWithinGroup) {
        return FluentIterable.from( getWifiDecoratorsForGroup(groupPosition) ).get( indexOfDecoratorWithinGroup );
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
        wifiName.setText(getChild(groupPosition, indexOfDecoratorWithinGroup ).toString());
        return wifiName;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private Set<WifiConfigurationDecorator> getWifiDecoratorsForGroup(int groupPosition) {
        return groupToWifiDecorators.get(groups.get(groupPosition));
    }
}
