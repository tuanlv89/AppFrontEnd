package com.example.foodordering.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.foodordering.R;
import com.example.foodordering.SplashScreen;
import com.example.foodordering.dialog.ProgressLoading;
import com.example.foodordering.utils.Utils;
import com.example.foodordering.view.SignIn;
import com.example.foodordering.view.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    public final String STORAGE_PATH = "image/";
    public final int REQUEST_CODE_PICK_IMAGE = 1412;

    TextView btnSignIn, btnSignUp;


    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        initView(view);
        /*if(Utils.isOnline) {
            ProgressLoading.show(getContext());
        }*/
        //uploadProfile();
        
        //Click Events
        //imgAvatar.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);


        return view;
    }

    private void uploadProfile() {
    }

    private void displayAvatarImage() {

    }

    private void initView(View view) {
        btnSignIn = view.findViewById(R.id.sign_in);
        btnSignUp = view.findViewById(R.id.sign_up);
        ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadImageToServer() {

    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.sign_in:
                Intent signInIntent = new Intent(getContext(), SignIn.class);
                startActivity(signInIntent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.slide_out_left);
                break;
            case R.id.sign_up:
                Intent signUpIntent = new Intent(getContext(), SignUp.class);
                startActivity(signUpIntent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.slide_out_left);
                break;
        }

    }

    private void shareLink() {
        int applicationNameId = getContext().getApplicationInfo().labelRes;
        final String appPackageName = getContext().getPackageName();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
        String text = "Install this cool application: ";
        //String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
        /*demo*/
        String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
        i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
        startActivity(Intent.createChooser(i, "Share link:"));
    }

    private void sendEmail() {
        Intent intentSendMail = new Intent(Intent.ACTION_SENDTO);
        intentSendMail.setType("text/plain");
        intentSendMail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_title));
        intentSendMail.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));
        intentSendMail.setData(Uri.parse("mailto:levantuan1110@gmail.com"));
        intentSendMail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intentSendMail, "Send mail..."));
    }

    private void changePassword() {

    }

    private void changeUserName() {

    }

    private void displayUserName() {

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_PICK_IMAGE);
    }

    private void logOut() {
        new AlertDialog.Builder(getContext()).setMessage("You want to log out?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), SplashScreen.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

}
