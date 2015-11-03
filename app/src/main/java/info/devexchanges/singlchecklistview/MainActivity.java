package info.devexchanges.singlchecklistview;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Friend> friendArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friendArrayList = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.list_item);

        //set ListView header
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.header_listview, listView, false);
        listView.addHeaderView(header, null, false);

        readDataFromAssets();

        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.item_listview, friendArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setTitle("Friend Details");
                dialog.setCancelable(true);

                TextView friendID = (TextView) dialog.findViewById(R.id.position);
                TextView friendName = (TextView) dialog.findViewById(R.id.name);

                friendID.setText("Position: " + (position + 1));
                friendName.setText("Name: " + ((Friend) parent.getItemAtPosition(position)).getName());

                dialog.show();
            }
        };
    }

    private void readDataFromAssets() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("friend.txt")));

            // do reading, usually loop until end of file reading
            String line;
            while ((line = reader.readLine()) != null) {
                friendArrayList.add(new Friend(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}