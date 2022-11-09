package com.example.gestiondepharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.DeviceCallback;

public class MainActivity extends AppCompatActivity {

    Bluetooth bluetooth;
    private final static int REQUEST_ENABLE_BT = 1111;
    private final static String ADDRR = "00:13:EF:00:96:DB";
    TextView name, name2 , temmp ,hume;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        temmp = findViewById(R.id.temp);

        bluetooth = new Bluetooth(context);

        bluetooth.setDeviceCallback(new DeviceCallback() {
            @Override public void onDeviceConnected(BluetoothDevice device) {

            }
            @Override public void onDeviceDisconnected(BluetoothDevice device, String message) {}

            @Override public void onMessage(byte[] message) {
                String s = new String(message);
                Log.d("tag",s);
                if(s.length() <= 2){
                    temmp.setText("Température "+s+" C");
                }else{
                    if(s.equals("9170146153")){

                        name.setText("Name et Prenom : GHZELI AEK");
                        name2.setText("Promo : 2MSIR G15");
                    }else{ if(s.equals("252360153")) {
                        name.setText("Name et Prenom : FEREDJ Younes");
                        name2.setText("Promo : 3LMI");
                    }
                    }

                }

            }
            @Override public void onError(int errorCode) {}
            @Override public void onConnectError(BluetoothDevice device, String message) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bluetooth.onStart();
        if(bluetooth.isEnabled()){
            bluetooth.connectToAddress(ADDRR);
        } else {
            bluetooth.showEnableDialog(MainActivity.this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetooth.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bluetooth.onActivityResult(requestCode, resultCode);
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    bluetooth.connectToAddress(ADDRR);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(bluetooth.isConnected()){
            bluetooth.disconnect();
        }
    }

    public void sendMessage(String msg){
        bluetooth.send(msg);
    }



}