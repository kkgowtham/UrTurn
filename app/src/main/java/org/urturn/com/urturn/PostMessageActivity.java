package org.urturn.com.urturn;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.UUID;

public class PostMessageActivity extends AppCompatActivity {

    EditText postDescription;
    ImageView postImageView;
    DatabaseReference mDatabaseReference;
    private Uri mImageUri;
    String mDownloadUrl;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        postDescription=findViewById(R.id.description_edittext);
        postImageView=findViewById(R.id.post_imageview);
        String groupId=getIntent().getStringExtra("groupid");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("GroupsPosts/"+groupId);
        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfileChooser();
            }
        });

    }
    private void openfileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadImage()
    {
        final StorageReference storageReference = storage.getReference("GroupIcon");
        final StorageReference storageReference1=storageReference.child(UUID.randomUUID() +".jpg");
        storageReference1.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG",uri.toString());
                        mDownloadUrl = String.valueOf(uri);
                        String desc=postDescription.getText().toString().trim();
                        GroupAdminPostModel model=new GroupAdminPostModel();
                        if(!desc.equals("")&&!mDownloadUrl.equals("")) {
                            model.setDesc(desc);
                            model.setTimestamp(getDate());
                            model.setUrl(mDownloadUrl);
                            mDatabaseReference.push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Message Posted",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }}).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG",e.getMessage());
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(postImageView);
        }
    }


    public void postMessage(View view) {
        uploadImage();

    }
    public String getDate()
    {
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
}
