package com.shenglin_feng.sms_relayer;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import java.io.IOException;
import java.net.*;

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        readSMS(context);
    }

    private void readSMS(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri=Uri.parse("content://sms/");
        Cursor cursor=resolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        httpGet(cursor.getString(cursor.getColumnIndex("body")));
        cursor.close();
    }

    private void httpGet(final String msg) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(MainActivity.sp.getString("editTextTextPersonName", "") + msg);
                    for (int i = 1; i <= 3; i++) {
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setConnectTimeout(5000);
                        conn.setRequestMethod("GET");
                        if (conn.getResponseCode() == 200) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
