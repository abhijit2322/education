package com.abhijit.videostreamingactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.view.View;
import android.view.Window;
import android.widget.VideoView.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    VideoView myVideoView;
    Button signin;
    View v;
    String fdirpath="";

    int vurlc=0;
    String[] itemList = new String[100];
    Integer[] itemimage = new Integer[100];
    ListView list;
    String[] web = {
            "Java",
            "C++",
            "C#",
            "HTML",
            "CSS"
    } ;
    Integer[] imageId = {
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
    };



    private static final String TAG = "AbijitMainVideoList";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private String mCustomToken;
   // private TokenBroadcastReceiver mTokenReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
       // myVideoView = this.findViewById(R.id.videofox1);
        Intent intent=getIntent();
        String content_type= intent.getStringExtra("content_type");
        if(content_type.contains("education")){
            fdirpath="video/edu";
        }
        else if (content_type.contains("entertainment"))
        {
            fdirpath="video/ent";
        }
        else
        {
            fdirpath="video";
        }
/*
        CustomList listAdapter = new
                CustomList(MainActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });

*/

        signin=this.findViewById(R.id.button2);

        //FirebaseStorage storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });





        /*
        MediaController mc = new MediaController(this);
        myVideoView.setMediaController(mc);
        final String urlStream = "https://firebasestorage.googleapis.com/v0/b/fir-video-867f4.appspot.com/o/video%2F7896.mp4?alt=media&token=c82c6995-81a6-48ed-ad39-e7cbb7e3616d";
        myVideoView.start();
        myVideoView.findFocus();
        runOnUiThread(new Runnable() { @Override public void run() { myVideoView.setVideoURI(Uri.parse(urlStream)); } });

         */
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  registerReceiver(mTokenReceiver, TokenBroadcastReceiver.getFilter());
    }


    @Override
    protected void onPause() {
        super.onPause();
       // unregisterReceiver(mTokenReceiver);
    }

    // [END on_start_check_user]
    private void startSignIn() {
        // Initiate sign in with custom token
        System.out.println("Abhijit the list of Item>>>>  >>>>>startSignIn");

        // [START sign_in_custom]
        String email="abhijit.golo@gmail.com";
        String password="Golo@123";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Abhijit the list of Item>>>>  >>>>>>>>>>>>>>  signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Abhijit the list of Item>>>>  >>>>>>>>>>>>>> signInWithEmail:failure"+task.getException());
                           /* Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
        /*
        mAuth.signInWithCustomToken(mCustomToken)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Abhijit the list of Item>>>>  >>>>>>>>>>>>>>>>signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Abhijit the list of Item>>>>>>>>>>>>>>>signInWithCustomToken:failure"+task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });*/
        // [END sign_in_custom]
    }

    @Override
    public void onClick(View v) {
      //  awesomeButtonClicked();
        /*int i = v.getId();
        if (i == R.id.button2) { */
            System.out.println("Abhijit the Button pressed on Onlick>>>>>>>Failed User ID: ");

        //}
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
          //  ((TextView) findViewById(R.id.textSignInStatus)).setText(
           //         "User ID: " + user.getUid());
            System.out.println("Abhijit the list of Item>>>>>>>>>>>>>>>>>>User ID: " + user.getUid());
            listAllFiles();


        } else {
           // ((TextView) findViewById(R.id.textSignInStatus)).setText(
           //         "Error: sign in failed.");
            System.out.println("Abhijit the list of Item>>>>>>>>>>>>>>>Failed User ID: ");
        }
    }
    public void voltarhomefox(View v){
       // Intent intent = new Intent(MainActivity.this, HomeActivity.class);
       // startActivity(intent);
        System.out.println("Abhijit the Button Pressed>>>>>>>>>>>>>>Failed User ID: ");
        startSignIn();
    }
    public void FillList(String itemname[],Integer itemImage[])
    {
        System.out.println("Abhijit the FillList   >>>>>>>>>>>>>>");
        if(itemname==null)
            System.out.println("Abhijit the FillList   >>>>>>>>>>>>>> itemName is NULL");
        if(itemImage==null)
            System.out.println("Abhijit the FillList   >>>>>>>>>>>>>> itemImage is NULL");

        CustomList listAdapter = new
                CustomList(MainActivity.this, itemname, itemImage);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + itemList[+position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,VideoShow.class);
                intent.putExtra("Video_url", itemList[+position]);
                startActivity(intent);
                finish();

            }
        });
    }

    public void listAllFiles() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // [START storage_list_all]
        System.out.println("Abhijit the list of Item>>>>>>>>>>>>>>>>>>>>[START storage_list_all]>>>>>>>>>>>>>>>>>>>");
        StorageReference listRef = storage.getReference().child(fdirpath);//"video");



        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                            System.out.println("Abhijit the list of Item:"+prefix.getPath());
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.

                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png' in uri
                                    System.out.println(uri.toString());
                                    itemList[vurlc]=uri.toString();

                                    if(fdirpath.contains("video/edu")) {
                                        System.out.println("education icon .................");
                                        itemimage[vurlc] = R.drawable.idea;
                                    }

                                    else if(fdirpath.contains("video/ent")) {
                                        itemimage[vurlc] = R.drawable.ent3_icon;
                                    }
                                    else {
                                        itemimage[vurlc] = R.drawable.audiance;
                                    }

                                    vurlc++;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                            //System.out.println("Abhijit the URL LIST---->>")"
                            //System.out.println("Abhijit the list of Item>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getPath()+" "+item.getBucket()+"Get Download url: "+item.getDownloadUrl());

                        }
                        System.out.println("Abhijit the URL LIST---->>");
                        vurlc=0;
                        FillList(itemList,itemimage);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                        System.out.println("Abhijit the list of Item> Failure>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e.getStackTrace());
                    }
                });
        // [END storage_list_all]
    }


}
