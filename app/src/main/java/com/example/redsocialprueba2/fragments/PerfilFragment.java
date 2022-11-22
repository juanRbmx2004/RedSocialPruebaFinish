package com.example.redsocialprueba2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.activities.EditPerfilActivity;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.UserProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private LinearLayout mLinearLayoutEdit;
    private View mView;
    private TextView txt_Username;
    private TextView txt_Userphone;
    private TextView txt_Email;
    private TextView txt_Post;
    private CircleImageView imgPerfil;
    private ImageView imgCover;


    private UserProvider mUserprovider;
    private AuthProvider mAuthProvider;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_perfil, container, false);
        mLinearLayoutEdit = mView.findViewById(R.id.LinerLayoutEditProfile);
        mLinearLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditPerfil();
            }
        });

        txt_Username = mView.findViewById(R.id.txt_v_nameUserPerfil);
        txt_Userphone = mView.findViewById(R.id.txt_v_numberPhone);
        txt_Email = mView.findViewById(R.id.txt_v_emailPerfil);
        txt_Post = mView.findViewById(R.id.txt_v_post);

        imgCover = mView.findViewById(R.id.img_view_coverPerfil);
        imgPerfil = mView.findViewById(R.id.img_circle_perfilPerfil);

        mUserprovider = new UserProvider();
        mAuthProvider = new AuthProvider();
        getUser();
        return mView;
    }

    private void goToEditPerfil() {
        Intent intent = new Intent(getContext(), EditPerfilActivity.class);
        startActivity(intent);
    }

    private void getUser(){
        mUserprovider.getUser(mAuthProvider.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    if(documentSnapshot.contains("email")){
                        String email = documentSnapshot.getString("email");
                        txt_Email.setText(email);
                    }
                    if(documentSnapshot.contains("userPhone")){
                        String phone = documentSnapshot.getString("userPhone");
                        txt_Userphone.setText(phone);
                    }
                    if(documentSnapshot.contains("userName")){
                        String name = documentSnapshot.getString("userName");
                        txt_Username.setText(name);
                    }
                    if(documentSnapshot.contains("image_perfil")){
                        String img_perfil = documentSnapshot.getString("image_perfil");
                        if(img_perfil!=null&&!img_perfil.isEmpty()){
                            Picasso.with(getContext()).load(img_perfil).into(imgPerfil);
                        }
                    }
                    if(documentSnapshot.contains("image_cover")){
                        String img_cover = documentSnapshot.getString("image_cover");
                        if(img_cover !=null&&!img_cover.isEmpty()){
                            Picasso.with(getContext()).load(img_cover).into(imgCover);
                            imgCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    }

                }
            }
        });
    }
}