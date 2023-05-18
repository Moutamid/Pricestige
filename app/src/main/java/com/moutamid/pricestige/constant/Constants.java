package com.moutamid.pricestige.constant;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.pricestige.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Constants {
    static Dialog dialog;
    public static final String USER = "user";
    public static final String Result = "Result";
    public static final String model = "model";
    public static final String bookList = "bookList";
    public static final String amazon = "https://api.rainforestapi.com/request?api_key=D69F20A5195B4C44A338FD72E60457BA&type=search&amazon_domain=amazon.com&search_term=";
    public static final String ebay = "https://api.countdownapi.com/request?api_key=72F6C12E3FBC44DB89EB34A9508BD7E2&ebay_domain=ebay.com&search_term="; // memory+cards&type=search\n
    public static final String ebayProduct = "https://api.countdownapi.com/request?api_key=72F6C12E3FBC44DB89EB34A9508BD7E2&type=product&epid="; // memory+cards&type=search\n

    public static String ebayLink(String item) {
        if (item.endsWith(" ")){
            item = item.substring(0, item.length()-1);
        }
        if (item.contains(" ")){
            item = item.replace(" ", "+");
        }

        return ebay + item + "&type=search";
    }
    public static String ebayProductLink(String item) {
        return ebayProduct + item +"&ebay_domain=ebay.com";
    }

    public static String amazonLink(String item) {
        if (item.contains(" ")){
            item = item.replace(" ", "+");
        }
        return amazon + item;
    }

    public static void initDialog(Context context){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
    }

    public static void showDialog() {
        dialog.show();
    }

    public static void dismissDialog() {
        dialog.dismiss();
    }

    public static void checkApp(Activity activity) {
        String appName = "pricestige";

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference databaseReference() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Pricestige");
        db.keepSynced(true);
        return db;
    }

}
