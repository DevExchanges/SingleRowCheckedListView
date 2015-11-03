package info.devexchanges.singlchecklistview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Friend> {

    private List<Friend> myFriends;
    private Activity activity;
    private int selectedPosition = -1;

    public ListViewAdapter(Activity context, int resource, List<Friend> objects) {
        super(context, resource, objects);

        this.activity = context;
        this.myFriends = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setTag(position); // This line is important.

        holder.friendName.setText(getItem(position).getName());
        if (position == selectedPosition) {
            holder.checkBox.setChecked(true);
        } else holder.checkBox.setChecked(false);

        holder.checkBox.setOnClickListener(onStateChangedListener(holder.checkBox, position));

        return convertView;
    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
            }
        };
    }

    private static class ViewHolder {
        private TextView friendName;
        private CheckBox checkBox;

        public ViewHolder(View v) {
            checkBox = (CheckBox) v.findViewById(R.id.check);
            friendName = (TextView) v.findViewById(R.id.name);
        }
    }
}