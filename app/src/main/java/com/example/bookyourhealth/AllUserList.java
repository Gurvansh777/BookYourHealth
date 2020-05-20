package com.example.bookyourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllUserList extends AppCompatActivity {
    DatabaseHelper dbh;
    List<Integer> userIdList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_list);

        dbh = new DatabaseHelper(AllUserList.this);


        Cursor cAllUsers;

        cAllUsers = dbh.getAllUsers();

       if(cAllUsers.getCount() > 0){
           List<HashMap<String, String>> aList = new ArrayList
                   <HashMap<String, String>>();
           for (int i = 0; i < cAllUsers.getCount(); i++) {
               cAllUsers.moveToNext();
               userIdList.add(cAllUsers.getInt(0));
               HashMap<String, String> hm = new HashMap<String, String>();
               hm.put("userDetail", "User Name: " +cAllUsers.getString(6) + "\nUser Type: " + cAllUsers.getString(3));
               aList.add(hm);
           }

           String[] from = {"userDetail"};
           int[] to = {R.id.tvAllUsers};

           SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
                   aList, R.layout.activity_all_user_list_view, from, to);

           ListView lvAllUserList = findViewById(R.id.lvAllUserList);

           lvAllUserList.setAdapter(adapter);

           lvAllUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intentToEdit = new Intent(AllUserList.this, EditProfile.class);
                   intentToEdit.putExtra("userIdToUpdate", userIdList.get(position));
                   intentToEdit.putExtra("fromWhere", "edit");
                   startActivity(intentToEdit);
               }
           });



       }


    }
}
