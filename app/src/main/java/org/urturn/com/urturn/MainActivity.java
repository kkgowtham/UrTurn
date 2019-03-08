package org.urturn.com.urturn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="MainActivity";
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav_main);
        bottomNavigationView.setSelectedItemId(R.id.blood_main_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new BloodNotificationFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.blood_main_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new BloodNotificationFragment()).commit();
                        break;
                    case R.id.home_main_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new HomeFragment()).commit();
                        break;
                    case R.id.groups_main_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new MyGroups()).commit();
                        break;
                    case R.id.profile_main_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new ProfileFragment()).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main_page,new BloodNotificationFragment()).commit();
                        break;
                }
                return true;
            }
        });
        /*Button signout_button=findViewById(R.id.button_signout);
        signout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,GoogleSignInActivity.class));
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(this,GoogleSignInActivity.class));
        }
    }

}
