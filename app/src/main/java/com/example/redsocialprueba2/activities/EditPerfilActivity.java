package com.example.redsocialprueba2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.models.User;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.ImageProvider;
import com.example.redsocialprueba2.providers.UserProvider;
import com.example.redsocialprueba2.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPerfilActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialogEdit;

    private CircleImageView mCircleimgBack;
    private CircleImageView mCirclleImgPerfil;
    private ImageView mImgCover;
    private TextInputEditText txt_userEdit;
    private TextInputEditText txt_phoneEdit;
    private Button btn_EditPerfil;

    private AlertDialog.Builder mBilderSelector;
    private CharSequence options[];
    private final int GALLERY_REQUEST_PERFIL = 1;
    private final int GALLERY_REQUEST_COVER = 2;
    private final int PHOTO_REQUEST_PERFIL = 3;
    private final int PHOTO_REQUEST_COVER = 4;

    private ImageProvider mImageProvider;
    private UserProvider mUserProvider;
    private AuthProvider mAuthProvider;

    // PHOTO NUMBER 1
    private String mAbsolutePhotoPat;
    private String mPhotoPat;
    private File mPhotoFile;
    // PHOTO NUMBER 2
    private String mAbsolutePhotoPat2;
    private String mPhotoPat2;
    private File mPhotoFile2;

    private File mImageFile;
    private File mImageFile2;
    private String PhoneUser;
    private String NameUser;
    private String urlImagePerfil="";
    private String urlImagecover="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        this.mProgressDialogEdit = new ProgressDialog(this);
        mImageProvider = new ImageProvider();
        mUserProvider = new UserProvider();
        mAuthProvider = new AuthProvider();

        mBilderSelector = new AlertDialog.Builder(this);
        mBilderSelector.setTitle("SELECCIONA UNA OPCION");
        options = new CharSequence[] {"Imagen De Galeria","Tomar Foto"};

        mCircleimgBack = findViewById(R.id.circle_img_backEdit);
        mCirclleImgPerfil = findViewById(R.id.img_circle_perfil);
        mImgCover = findViewById(R.id.img_view_cover);

        txt_phoneEdit = findViewById(R.id.txt_ed_phoneEdit);
        txt_userEdit = findViewById(R.id.txt_ed_userEdit);

        btn_EditPerfil = findViewById(R.id.btn_edit_perfil);

        btn_EditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliclEditPerfil();
            }
        });

        mCirclleImgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOptionImg(1);
            }
        });

        mImgCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOptionImg(2);
            }
        });

        mCircleimgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUser();
    }

    private void getUser(){
        mUserProvider.getUser(mAuthProvider.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){

                    if(documentSnapshot.contains("userName")){
                        NameUser = documentSnapshot.getString("userName");
                        txt_userEdit.setText(NameUser);
                    }
                    if(documentSnapshot.contains("userPhone")){
                        PhoneUser = documentSnapshot.getString("userPhone");
                        txt_phoneEdit.setText(PhoneUser);
                    }
                    if(documentSnapshot.contains("image_perfil")){
                        urlImagePerfil = documentSnapshot.getString("image_perfil");
                        if(urlImagePerfil!=null&&!urlImagePerfil.isEmpty()){
                            Picasso.with(EditPerfilActivity.this).load(urlImagePerfil).into(mCirclleImgPerfil);
                        }
                    }
                    if(documentSnapshot.contains("image_cover")){
                        urlImagecover = documentSnapshot.getString("image_cover");
                        if(urlImagecover!=null&&!urlImagecover.isEmpty()){
                            Picasso.with(EditPerfilActivity.this).load(urlImagecover).into(mImgCover);
                            mImgCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    }
                }
            }
        });
    }

    private void cliclEditPerfil() {
         NameUser = txt_userEdit.getText().toString();
         PhoneUser = txt_phoneEdit.getText().toString();
        if(!NameUser.isEmpty()&&!PhoneUser.isEmpty()){
            if(mImageFile !=null&&mImageFile2 !=null){
                saveImageCoverAndPerfil(mImageFile,mImageFile2);
            }
            // selecion de la camara
            else if(mPhotoFile!=null&&mPhotoFile2!=null){
                Toast.makeText(this,"entra1",Toast.LENGTH_SHORT).show();
                saveImageCoverAndPerfil(mPhotoFile,mPhotoFile2);
            }
            else if(mImageFile!=null&&mPhotoFile2!=null){
                saveImageCoverAndPerfil(mImageFile,mPhotoFile2);
            }
            else if(mPhotoFile!=null&&mImageFile2!=null){
                saveImageCoverAndPerfil(mPhotoFile2,mImageFile2);
            }
            else if(mPhotoFile!=null){
                SaveImage(mPhotoFile,true);
            }
            else  if(mPhotoFile2!=null){
                SaveImage(mPhotoFile2,false);
            }
            else if(mImageFile!=null){
                SaveImage(mImageFile,true);
            }
            else if(mImageFile2!=null){
                SaveImage(mImageFile2,false);
            }
            else {
                User user = new User();
                user.setUserName(NameUser);
                user.setUserPhone(PhoneUser);
                user.setImage_perfil(urlImagePerfil);
                user.setImage_cover(urlImagecover);
                user.setId(mAuthProvider.getUid());
                updateInfo(user);
            }
        }
        else Toast.makeText(EditPerfilActivity.this,"Campos Vacios",Toast.LENGTH_SHORT).show();


    }

    private void saveImageCoverAndPerfil(File imageFile1, final File imageFile2) {
        ShowPDialog();
        mImageProvider.save(EditPerfilActivity.this,imageFile1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String urlProfile = uri.toString();

                            mImageProvider.save(EditPerfilActivity.this,imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImageFile) {
                                    if(taskImageFile.isSuccessful()){
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String urlCover = uri2.toString();
                                                User user = new User();
                                                user.setUserName(NameUser);
                                                user.setUserPhone(PhoneUser);
                                                user.setImage_perfil(urlProfile);
                                                user.setImage_cover(urlCover);
                                                user.setId(mAuthProvider.getUid());
                                                updateInfo(user);
                                            }
                                        });
                                    }
                                    else{
                                        mProgressDialogEdit.dismiss();
                                        Toast.makeText(EditPerfilActivity.this,"Imagen numero 2 no se guardo",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
                else {
                    mProgressDialogEdit.dismiss();
                    Toast.makeText(EditPerfilActivity.this, "Error De almacenamiento", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditPerfilActivity.this, "Error De almacenamiento: " +e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SaveImage(File image,boolean isPerfilImg){
        ShowPDialog();
        mImageProvider.save(EditPerfilActivity.this,image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();
                            User user = new User();
                            user.setUserName(NameUser);
                            user.setUserPhone(PhoneUser);
                            if(isPerfilImg){
                                user.setImage_perfil(url);
                                user.setImage_cover(urlImagecover);
                            }
                            else{
                                user.setImage_perfil(urlImagePerfil);
                                user.setImage_cover(url);
                            }
                            user.setId(mAuthProvider.getUid());
                            updateInfo(user);
                        }
                    });
                }
                else {
                    mProgressDialogEdit.dismiss();
                    Toast.makeText(EditPerfilActivity.this, "Error De almacenamiento", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditPerfilActivity.this, "Error De almacenamiento: " +e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateInfo(User user){
        if(mProgressDialogEdit.isShowing()){
            ShowPDialog();
        }
        mUserProvider.UpDate(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> taskActualizar) {
                mProgressDialogEdit.dismiss();
                if(taskActualizar.isSuccessful()){
                    Toast.makeText(EditPerfilActivity.this,"se actualizo correctamente",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditPerfilActivity.this,"no se actualizo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectOptionImg(final int numberImage) {

        mBilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    if(numberImage == 1) {
                        openGallery(GALLERY_REQUEST_PERFIL);
                    }
                    else if(numberImage == 2){
                        openGallery(GALLERY_REQUEST_COVER);
                    }
                }
                else if(i==1){
                    if(numberImage == 1) {
                        takePhoto(PHOTO_REQUEST_PERFIL);
                    }
                    else if(numberImage == 2){
                        takePhoto(PHOTO_REQUEST_COVER);
                    }
                }
            }
        });
        mBilderSelector.show();
    }

    private void takePhoto(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try {
                photoFile = createPohtoFile(requestCode);
            }catch (Exception e){
                Toast.makeText(EditPerfilActivity.this,"Hubo un error con el archivo: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(photoFile!=null){
                Uri photoUri = FileProvider.getUriForFile(EditPerfilActivity.this,"com.example.redsocialprueba2",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent,requestCode);
            }
        }
    }

    private File createPohtoFile(int requestCode) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photoFile = File.createTempFile(
                new Date() + "_photo",".jpg",storageDir
        );
        if(requestCode== PHOTO_REQUEST_PERFIL) {
            mPhotoPat = "file: " + photoFile.getPath();
            mAbsolutePhotoPat = photoFile.getAbsolutePath();
        }
        else if(requestCode== PHOTO_REQUEST_COVER){
            mPhotoPat2 = "file: " + photoFile.getPath();
            mAbsolutePhotoPat2 = photoFile.getAbsolutePath();
        }
        return photoFile;
    }

    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * seleccion de imagen de la galeria
         */
        if(requestCode==GALLERY_REQUEST_PERFIL&&resultCode==RESULT_OK){
            try {
                mPhotoFile = null;
                mImageFile = FileUtil.from(this,data.getData());
                mCirclleImgPerfil.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR", "ERROR: "+e.getMessage());
                Toast.makeText(this, "ERROR: "+e, Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==GALLERY_REQUEST_COVER&&resultCode==RESULT_OK){
            try {
                mPhotoFile2 = null;
                mImageFile2 = FileUtil.from(this,data.getData());
                mImgCover.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
                mImgCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }catch (Exception e){
                Log.d("ERROR", "ERROR: "+e.getMessage());
                Toast.makeText(this, "ERROR: "+e, Toast.LENGTH_LONG).show();
            }
        }
        /**
         * selecion de la fotografia
         */

        if(requestCode== PHOTO_REQUEST_PERFIL &&resultCode==RESULT_OK){
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPat);
            Picasso.with(EditPerfilActivity.this).load(mPhotoFile).into(mCirclleImgPerfil);
        }
        if(requestCode== PHOTO_REQUEST_COVER &&resultCode==RESULT_OK){
            mImageFile2 = null;
            mPhotoFile2 = new File(mAbsolutePhotoPat2);
            Picasso.with(EditPerfilActivity.this).load(mPhotoFile2).into(mImgCover);
        }

    }

    private void ShowPDialog(){
        mProgressDialogEdit.setCancelable(false);
        mProgressDialogEdit.setTitle("Entrando!");
        mProgressDialogEdit.setMessage("Porfavor Espere!");
        mProgressDialogEdit.show();
    }

}