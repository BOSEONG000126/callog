package org.techtown.callog;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.techtown.callog.databinding.CalendarDiaryBinding;

import java.util.Calendar;


public class DiaryFragment extends Fragment {

    private TextView dateTextView;
    private String day;
    private CalendarDiaryBinding binding;
    private ImageView picture;
    private String contensoutput;

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        day = getArguments().getString("date");// 프래그먼트1에서 받아온 값 넣기
        binding.toolbar.inflateMenu(R.menu.menu_toolbar);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.it_menu_item_1:

                    Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                    bundle.putString("date",day);//번들에 넘길 값 저장

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    calendar_diary_edit calendar_diary_edit = new calendar_diary_edit();//프래그먼트2 선언
                    calendar_diary_edit.setArguments(bundle);
                    transaction.replace(R.id.container, calendar_diary_edit);
                    transaction.commit();
                    return true;
                case R.id.it_menu_item_2:
                    // Save profile changes
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CalendarDiaryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (getArguments() != null)
        {
            day = getArguments().getString("date");// 프래그먼트1에서 받아온 값 넣기
            binding.dateTextView.setText(day);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        picture = binding.PictureOut;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storagereference = storage.getReference();
        StorageReference pathReference = storagereference.child(email + "." + day);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DiaryFragment.this).load(uri).into(picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });









        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}

