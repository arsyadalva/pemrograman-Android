package tes;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.danang_laptop.sms.R;

import java.util.ArrayList;
import java.util.List;

public class baca extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_baca);

        final EditText edtKey = (EditText)findViewById(R.id.edtKey);
        final Button btnDes =(Button)findViewById(R.id.btnDes);
        final ListView lvDes = (ListView)findViewById(R.id.lvDes);

        btnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                List<String> msgs = getSMS(edtKey.getText().toString().trim());
                if (msgs.isEmpty()) {
                    msgs.add("KEY TIDAK COCOK");
                }
                ArrayAdapter<String> smsAdapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_1, msgs);
                lvDes.setAdapter(smsAdapter);


            }

        });

    }
    public List<String>  getSMS(String strkey) {
        List<String> list = new ArrayList<String>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = null;
        try{
            c = getApplicationContext().getContentResolver().query(uri, null, null ,null,null);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            for (boolean hasData = c.moveToFirst(); hasData; hasData = c.moveToNext()) {

                final String noHP = c.getString(c.getColumnIndex("address"));

                final String msg = c.getString(c.getColumnIndexOrThrow("body"));

                String plainTeks= KriptoDES.dekrip(msg, strkey);
                if(!plainTeks.equalsIgnoreCase("ERROR")){
                    list.add("SMS dari: " + noHP + "\nText: " + plainTeks);

                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        c.close();
        return list;
    }

}
