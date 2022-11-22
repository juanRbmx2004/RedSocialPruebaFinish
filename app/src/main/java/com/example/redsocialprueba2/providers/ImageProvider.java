package com.example.redsocialprueba2.providers;

import android.content.Context;

import com.example.redsocialprueba2.utils.CompressorBitmapImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class ImageProvider {

    StorageReference mStorage;

    public ImageProvider(){
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file){
        byte[] imageBite = CompressorBitmapImage.getImage(context,file.getPath(),500,500);
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(new Date()+".jpg");
        mStorage = storage;
        UploadTask task = storage.putBytes(imageBite);
        return task;
    }

    public StorageReference getStorage(){
        return mStorage;
    }
}
