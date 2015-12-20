package africa.simfy.com.androidassessment;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomList extends ListActivity {
    final Context context = this;
    ImageView iv;
    Integer[] imgId = {
            R.drawable.track1,
            R.drawable.track2,
            R.drawable.track3,
            R.drawable.track4,
            R.drawable.track5,
            R.drawable.track6,
            R.drawable.track7,
            R.drawable.track8,
            R.drawable.track9,
            R.drawable.track10,
    };

    Integer[] audioId = {
            R.raw.track1,
            R.raw.track2,
            R.raw.track3,
            R.raw.track4,
            R.raw.track5,
            R.raw.track6,
            R.raw.track7,
            R.raw.track8,
            R.raw.track9,
            R.raw.track10,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_list);
        setListAdapter(new MyAdapter(this,
                android.R.layout.simple_list_item_1, R.id.textView2,
                getResources().getStringArray(R.array.decriptions)));
    }

    private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflator.inflate(R.layout.list_items, parent, false);
            final String[] desc = getResources().getStringArray(R.array.decriptions);
            final String[] heading = getResources().getStringArray(R.array.heading);


            ImageView iv = (ImageView) row.findViewById(R.id.imageView1);
            TextView tv = (TextView) row.findViewById(R.id.textView2);

            tv.setText(desc[position]);
            iv.setImageResource(imgId[position]);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parentRow = (View) v.getParent();
                    ListView listview = (ListView) parentRow.getParent();
                    final int posi = listview.getPositionForView(parentRow);
                    int posiOneUp = posi +1;


                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.customalert);

                    dialog.setTitle(heading[posi]);

                    TextView msg = (TextView) dialog.findViewById(R.id.textViewMsg);
                    msg.setText(desc[posi]);
                    TextView titleMsg = (TextView) dialog.findViewById(R.id.textViewTitle);
                    titleMsg.setText(heading[posi]);



                    Button dialogButton = (Button) dialog.findViewById(R.id.buttonClose);
                    Button playbtn = (Button) dialog.findViewById(R.id.buttonPlay);

                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    //play btn
                    playbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaPlayer mp = MediaPlayer.create(CustomList.this, audioId[posi]);
                            mp.start();
                        }
                    });
                    dialog.show();

                }
            });

            return row;
        }
    }
}
