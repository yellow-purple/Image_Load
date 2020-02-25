package org.techtown.image_load.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;


import org.techtown.image_load.DatabaseHelper;
import org.techtown.image_load.R;
import org.techtown.image_load.activity.RecyclerViewActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static ListAdapter adapter;
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> img_items=new ArrayList<>();
    private ArrayList <String> ImgUrl;
    private DatabaseHelper sampledb=new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = this.findViewById(R.id.listview);
        editText = this.findViewById(R.id.search_imege);
        button=this.findViewById(R.id.add_btn);

        items.addAll(sampledb.getListName());
        for(String s:items) {
            img_items.addAll(sampledb.getListUrl(s));
        }

        adapter = new ListAdapter(this, 0, items, img_items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ImgUrl=new ArrayList<>();
                String name=items.get(position);
                ImgUrl.addAll(sampledb.getAllUrl(name));
                Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent.putExtra("img",ImgUrl);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] str=new String[1];
                str[0]=editText.getText().toString();
                NetworkTask networkTask=new NetworkTask();
                networkTask.execute(str);

            }
        });


    }

    private class ListAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;
        private ArrayList<String> imgitems;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<String> objects, ArrayList<String> img_objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.imgitems= img_objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            ImageView imageView = (ImageView)v.findViewById(R.id.imageview);
            Glide.with(parent)
                    .load(imgitems.get(position))
                    .into(imageView);

            TextView textView = (TextView)v.findViewById(R.id.textview);
            textView.setText(items.get(position));

            return v;
        }
    }

    private class NetworkTask extends AsyncTask<String[], Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String[]... str) {

            DatabaseHelper Sampledb=new DatabaseHelper(getApplicationContext());
            Sampledb.get_img(str[0]);

            return null;
        }


        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            items.addAll(sampledb.getListName());
            for(String s:items) {
                img_items.addAll(sampledb.getListUrl(s));
            }

            adapter.notifyDataSetChanged();

        }
    }
}