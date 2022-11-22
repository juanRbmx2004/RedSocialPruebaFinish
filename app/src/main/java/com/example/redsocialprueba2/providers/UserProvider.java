package com.example.redsocialprueba2.providers;

import com.example.redsocialprueba2.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserProvider {
    CollectionReference mCollection;

    public UserProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<DocumentSnapshot> getUser(String id){
        return mCollection.document(id).get();
    }

    public Task<Void> create(User user){
        return mCollection.document(user.getId()).set(user);
    }

    public Task<Void> UpDate(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",user.getUserName());
        map.put("userPhone",user.getUserPhone());
        map.put("timestamp", new Date().getTime());
        map.put("image_perfil",user.getImage_perfil());
        map.put("image_cover",user.getImage_cover());
        return mCollection.document(user.getId()).update(map);
    }
}
