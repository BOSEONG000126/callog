package org.techtown.callog;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class calendar_diary_edit extends Fragment {
    private ImageView imageView;
    private ProgressBar progressBar;
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;
    private EditText Intext;
    private String day;
    private TextView dateTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.calendar_diary_edit, container, false);
        Button strBtn = rootView.findViewById(R.id.button2);
        progressBar = rootView.findViewById(R.id.progressBar);
        imageView = rootView.findViewById(R.id.PictureOut);
        Intext = rootView.findViewById(R.id.ContentsOutput2);
        dateTextView=rootView.findViewById(R.id.dateTextView);

        if (getArguments() != null)
        {
            day = getArguments().getString("date");// 프래그먼트1에서 받아온 값 넣기
            dateTextView.setText(day);
        }





        //프로그래스바 숨기기
        progressBar.setVisibility(View.INVISIBLE);
        //이미지 버튼 클릭
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                activityResult.launch(galleryIntent);
            }
        });
        //저장버튼 클릭
        strBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지가 있을때
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    //이미지가 없을때
                }
            }
        });

        return rootView;
    }
    //사진 가져오기
    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null)
                    {
                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                    }
                }
            });
    //파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();  //사용자 프로필 가져오기
        if (user != null) {
            String email = user.getEmail();
            //StorageReference fileRef =reference.child(System.currentTimeMillis() + "." + email + getFileExtension(uri));
            StorageReference fileRef =reference.child(email + "." + day);
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                        public void onSuccess(Uri uri){
                            String intext = Intext.getText().toString();
                            Model model =new Model(uri.toString() , intext); //uri
                            String modelld = root.push().getKey(); //키

                            root.child(modelld).setValue(model);
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

    }
    //파일타입 가져오기
    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));

    }
}
