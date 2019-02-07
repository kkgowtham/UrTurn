package org.urturn.com.urturn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public class CreateGroupActivity extends AppCompatActivity {

    CircularImageView mGroupIcon;
    EditText mGroupName, mGroupDesc;
    DatabaseReference mDatabaseReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseUser user;
    private Uri mImageUri;
    String mDownloadUrl;
    private static final int PICK_IMAGE_REQUEST = 1;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Groups");
        try
        {
            String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String user = userMail.substring(0, userMail.indexOf('@'));
            databaseReference=FirebaseDatabase.getInstance().getReference("JoinedGroups").child(user);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        mGroupIcon = findViewById(R.id.group_icon_image);
        mGroupName = findViewById(R.id.group_name_et);
        mGroupDesc = findViewById(R.id.group_desc_et);
        mGroupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfileChooser();
            }
        });
    }

    public void createGroup(View view) {
        String groupId = UUID.randomUUID().toString();
        uploadImage(groupId);
    }

    private void uploadImage(final String groupId) {
        final StorageReference storageReference = storage.getReference("GroupIcon");
        final StorageReference storageReference1=storageReference.child(groupId+".jpg");
        storageReference1.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                            Log.d("TAG",uri.toString());
                            mDownloadUrl = String.valueOf(uri);
                            updateDataBase(groupId);

                    }}).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG",e.getMessage());
                                }
                            });
            }
        });

    }
    private void openfileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mGroupIcon);
        }
    }

    public void updateDataBase(String groupId)
    {
        String name = mGroupName.getText().toString();
        String desc = mGroupDesc.getText().toString();
        try {
            String currentUserId = user.getEmail();
            GroupModel model = new GroupModel(name, desc, mDownloadUrl, currentUserId, groupId);
            mDatabaseReference.push().setValue(model);
            databaseReference.child(model.getGroupId()).setValue(model);
            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
