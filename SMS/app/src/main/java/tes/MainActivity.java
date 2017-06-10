package tes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.danang_laptop.sms.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        final Button btn_Tulis = (Button)findViewById(R.id.btn_Tulis);
        final Button btn_Baca = (Button)findViewById(R.id.btn_Baca);

        btn_Tulis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), kirim.class);
                startActivity(intent);
            }
        });
        btn_Baca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), baca.class);
                startActivity(intent);
            }
        });

    }



}
