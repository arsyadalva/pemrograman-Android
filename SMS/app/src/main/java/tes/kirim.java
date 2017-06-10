package tes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.danang_laptop.sms.R;


public class kirim extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_kirim);

        final EditText edit_Psn = (EditText)findViewById(R.id.edit_Psn);
        final EditText key = (EditText)findViewById(R.id.key);
        final TextView hsl_Enkrip = (TextView)findViewById(R.id.hsl_Enkrip);
        final EditText edit_Telp = (EditText)findViewById(R.id.edit_Telp);
        final Button btn_Kirim = (Button)findViewById(R.id.btn_Kirim);
        final Button btn_Enkrip = (Button)findViewById(R.id.btn_Enkrip);

        btn_Enkrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pesan = edit_Psn.getText().toString();
                String strKey=key.getText().toString().trim();
                String chiperTextDES= KriptoDES.enkrip(pesan, strKey);
                if(strKey.length()==8){
                    hsl_Enkrip.setText(chiperTextDES);

                }else {
                    final AlertDialog salah = new AlertDialog.Builder(kirim.this).create();
                    salah.setTitle("Error Message !");
                    salah.setMessage("Key Atau Kunci DES Harus 8 Karakter !");
                    salah.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface coba, int arg1) {
                            salah.cancel();
                        }
                    });
                    salah.show();
                }
            }
        });

        btn_Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noHP= edit_Telp.getText().toString();
                String pesan = hsl_Enkrip.getText().toString();
                if(noHP.length()>0){
                    kirimSMS(noHP, pesan);
                    //edit_Psn.setText(pesan);
                }else {
                    final AlertDialog salah = new AlertDialog.Builder(kirim.this).create();
                    salah.setTitle("Error Message !");
                    salah.setMessage("Nomor Telepon Penerima Pesan Masih Kosong !");
                    salah.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface coba, int arg1) {
                            salah.cancel();
                        }
                    });
                    salah.show();
                }
            }
        });
    }
    public void kirimSMS(String noHP, String pesan){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        //ketika SMS SENT
        registerReceiver(new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS SENT", Toast.LENGTH_LONG).show();

                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "ERROR ", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "Tidak Ada SIGNAL", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "PDU NULL", Toast.LENGTH_LONG).show();
                        break;
                    case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "ERROR, Jaringan tidak tersedia", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG).show();
                }
            }
        }, new IntentFilter(SENT));

        //ketika SMS DELIVERED
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS TERKIRIM ", Toast.LENGTH_LONG).show();

                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS TIDAK DAPAT TERKIRIM", Toast.LENGTH_LONG).show();
                        break;


                    default:
                        Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_LONG).show();
                }

            }

        }, new IntentFilter(DELIVERED));

        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(noHP, null, pesan, sentPI, deliveredPI);

    }

}
