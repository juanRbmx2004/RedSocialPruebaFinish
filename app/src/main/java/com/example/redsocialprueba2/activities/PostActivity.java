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
import android.widget.TextView;
import android.widget.Toast;

import com.example.redsocialprueba2.R;
import com.example.redsocialprueba2.models.Post;
import com.example.redsocialprueba2.providers.AuthProvider;
import com.example.redsocialprueba2.providers.ImageProvider;
import com.example.redsocialprueba2.providers.PostProvider;
import com.example.redsocialprueba2.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialogPost1;
    private AlertDialog.Builder mBilderSelector;
    private CharSequence options[];

    private TextInputEditText EtxtPublicacion;
    private TextInputEditText EtxtDescripcion;
    private ImageView imgPc;
    private ImageView imgXbox;
    private ImageView imgNintendo;
    private ImageView imgPc2;
    private TextView txtCategory;
    private ImageView imgView1;
    private ImageView imgView2;
    private Button btnPublicarPost;
    private File mImageFile;
    private File mImageFile2;
    private ImageProvider mImageProvider;

    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE_2 = 2;
    private final int PHOTO_REQUEST_CODE = 3;
    private final int PHOTO_REQUEST_CODE_2 = 4;

    private String mCategory = null;
    private PostProvider mPostProvider;
    private String title = "";
    private String description = "";
    private AuthProvider mAuthProvider;
    private CircleImageView mCircleImgeBack;
    // PHOTO NUMBER 1
    private String mAbsolutePhotoPat;
    private String mPhotoPat;
    private File mPhotoFile;
    // PHOTO NUMBER 2
    private String mAbsolutePhotoPat2;
    private String mPhotoPat2;
    private File mPhotoFile2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        this.mProgressDialogPost1 = new ProgressDialog(this);
        mBilderSelector = new AlertDialog.Builder(this);
        mBilderSelector.setTitle("SELECCIONA UNA OPCION");
        options = new CharSequence[] {"Imagen De Galeria","Tomar Foto"};

        mCircleImgeBack = findViewById(R.id.Circle_img_back_Post);
        imgView1 = findViewById(R.id.imageViwPost1);
        imgView2 = findViewById(R.id.imageViwPost2);
        btnPublicarPost = findViewById(R.id.btn_publicar_post);
        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();
        mAuthProvider = new AuthProvider();

        EtxtPublicacion = findViewById(R.id.txt_ed_publicacion);
        EtxtDescripcion = findViewById(R.id.txt_ed_description);
        imgPc = findViewById(R.id.imgeViewPc);
        imgXbox = findViewById(R.id.imageViwXbox);
        imgNintendo = findViewById(R.id.imgeViewNistendo);
        imgPc2 = findViewById(R.id.imageViwPc2);
        txtCategory = findViewById(R.id.tv_category);

        mCircleImgeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "CHISMES";
                txtCategory.setText(mCategory);
            }
        });
        imgXbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "EVENTOS";
                txtCategory.setText(mCategory);
            }
        });
        imgNintendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "COLABORACIONES";
                txtCategory.setText(mCategory);
            }
        });
        imgPc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory = "OTROS";
                txtCategory.setText(mCategory);
            }
        });

        btnPublicarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPost();
            }
        });
        imgView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectOptionImg(1);
            }
        });
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOptionImg(2);
            }
        });
    }

    private void selectOptionImg(final int numberImage) {

        mBilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    if(numberImage == 1) {
                        openGallery(GALLERY_REQUEST_CODE);
                    }
                    else if(numberImage == 2){
                        openGallery(GALLERY_REQUEST_CODE_2);
                    }
                }
                else if(i==1){
                    if(numberImage == 1) {
                        takePhoto(PHOTO_REQUEST_CODE);
                    }
                    else if(numberImage == 2){
                        takePhoto(PHOTO_REQUEST_CODE_2);
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
                Toast.makeText(PostActivity.this,"Hubo un error con el archivo: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(photoFile!=null){
                Uri photoUri = FileProvider.getUriForFile(PostActivity.this,"com.example.redsocialprueba2",photoFile);
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
        if(requestCode==PHOTO_REQUEST_CODE) {
            mPhotoPat = "file: " + photoFile.getPath();
            mAbsolutePhotoPat = photoFile.getAbsolutePath();
        }
        else if(requestCode==PHOTO_REQUEST_CODE_2){
            mPhotoPat2 = "file: " + photoFile.getPath();
            mAbsolutePhotoPat2 = photoFile.getAbsolutePath();
        }
        return photoFile;
    }

    private void clickPost() {
        title = EtxtPublicacion.getText().toString();
        description = EtxtDescripcion.getText().toString();
        if(!title.isEmpty()&&!description.isEmpty()&&!mCategory.isEmpty()){
            //seleciono de la galeria
            if(mImageFile !=null&&mImageFile2 !=null){
                saveImage(mImageFile,mImageFile2);
            }
            // selecion de la camara
            else if(mPhotoFile!=null&&mPhotoFile2!=null){
                saveImage(mPhotoFile,mPhotoFile2);
            }
            else if(mImageFile!=null&&mPhotoFile2!=null){
                saveImage(mImageFile,mPhotoFile2);
            }
            else if(mPhotoFile!=null&&mImageFile2!=null){
                saveImage(mPhotoFile2,mImageFile2);
            }
            else {
                Toast.makeText(this,"Selecciona Una Imagen",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Completa Todos Los Campos",Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(File imageFile1, final File imageFile2) {
        ShowPDialog();
        mImageProvider.save(PostActivity.this,imageFile1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();

                            mImageProvider.save(PostActivity.this,imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImageFile) {
                                    if(taskImageFile.isSuccessful()){
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();
                                                Post post = new Post();
                                                post.setmImagen1(url);
                                                post.setmImager2(url2);
                                                post.setTitle(title);
                                                post.setDescription(description);
                                                post.setCategory(mCategory);
                                                post.setIdUser(mAuthProvider.getUid());
                                                post.setTimestamp(new Date().getTime());
                                                mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> taskSave) {
                                                        mProgressDialogPost1.dismiss();
                                                        if(taskSave.isSuccessful()){
                                                            clearForm();
                                                            Toast.makeText(PostActivity.this, "La informacion se Almaceno Exitosamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Toast.makeText(PostActivity.this, "No se Pudo Almacenar La Infromacion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else{
                                        mProgressDialogPost1.dismiss();
                                        Toast.makeText(PostActivity.this,"Imagen numero 2 no se guardo",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
                else {
                    mProgressDialogPost1.dismiss();
                    Toast.makeText(PostActivity.this, "Error De almacenamiento", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostActivity.this, "Error De almacenamiento: " +e, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearForm() {
        EtxtPublicacion.setText("");
        EtxtDescripcion.setText("");
        txtCategory.setText("CATEGORIAS");
        txtCategory.setText("");
        imgView1.setImageResource(R.drawable.ic_post_img);
        imgView2.setImageResource(R.drawable.ic_post_img);
        title="";
        description="";
        mCategory="";
        mImageFile=null;
        mImageFile2=null;
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
        if(requestCode==GALLERY_REQUEST_CODE&&resultCode==RESULT_OK){
            try {
                mPhotoFile = null;
                mImageFile = FileUtil.from(this,data.getData());
                imgView1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR", "ERROR: "+e.getMessage());
                Toast.makeText(this, "ERROR: "+e, Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==GALLERY_REQUEST_CODE_2&&resultCode==RESULT_OK){
            try {
                mPhotoFile2 = null;
                mImageFile2 = FileUtil.from(this,data.getData());
                imgView2.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR", "ERROR: "+e.getMessage());
                Toast.makeText(this, "ERROR: "+e, Toast.LENGTH_LONG).show();
            }
        }
        /**
         * selecion de la fotografia
         */

        if(requestCode==PHOTO_REQUEST_CODE&&resultCode==RESULT_OK){
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPat);
            Picasso.with(PostActivity.this).load(mPhotoFile).into(imgView1);
        }
        if(requestCode==PHOTO_REQUEST_CODE_2&&resultCode==RESULT_OK){
            mImageFile2 = null;
            mPhotoFile2 = new File(mAbsolutePhotoPat2);
            Picasso.with(PostActivity.this).load(mPhotoFile2).into(imgView2);
        }

    }
    private void ShowPDialog(){
        mProgressDialogPost1.setCancelable(false);
        mProgressDialogPost1.setTitle("Entrando!");
        mProgressDialogPost1.setMessage("Porfavor Espere!");
        mProgressDialogPost1.show();
    }
}