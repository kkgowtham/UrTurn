package org.urturn.com.urturn;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG ="Serivce";
    TextView textView;
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}
