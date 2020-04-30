package com.dikamjitborah.hobarb.amechaniaapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddContacts extends AppCompatActivity {


    private static final int PICK_CONTACT_REQUEST1 = 1;
    private static final int PICK_CONTACT_REQUEST2 = 2;

    Button bb1, bb2;
    TextView tv1, tv2;
    static String s1, s2, s3, s4;
    String contactNumber;
    String contactName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);

        SharedPreferences sp1 = getSharedPreferences("key", 0);
        String tValue1 = sp1.getString("textvalue1"," ");

        SharedPreferences sp3 = getSharedPreferences("key", 0);
        String tValue3 = sp1.getString("textvalue3"," ");

        tv1.setText(tValue1);
        tv2.setText(tValue3);
        bb1 = findViewById(R.id.buttoncontact1);
        bb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST1);
            }
        });
        bb2 = findViewById(R.id.buttoncontact2);
        bb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST2);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_CONTACT_REQUEST1:
                    Cursor cursor = null;
                    try {

                        Uri uri = data.getData();

                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();

                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);

                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
                        SharedPreferences sp1 = getSharedPreferences("key", 0);
                        SharedPreferences.Editor sedt1 = sp1.edit();
                        sedt1.putString("textvalue1", contactName);
                        sedt1.commit();

                        SharedPreferences sp2 = getSharedPreferences("key", 0);
                        SharedPreferences.Editor sedt2 = sp2.edit();
                        sedt2.putString("textvalue2", contactNumber);
                        sedt2.commit();

                        sp1 = getSharedPreferences("key", 0);
                        String tValue1 = sp1.getString("textvalue1","");

                        sp2 = getSharedPreferences("key", 0);
                        String tValue2 = sp2.getString("textvalue2","");

                        s1 = tValue1;
                        tv1.setText(s1);
                        s2 = tValue2;

                    } catch (Exception e) {
                        e.printStackTrace();


                    }
                    break;

                case PICK_CONTACT_REQUEST2:
                    cursor = null;
                    try {

                        Uri uri = data.getData();

                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();

                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);

                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);

                        SharedPreferences sp3 = getSharedPreferences("key", 0);
                        SharedPreferences.Editor sedt3 = sp3.edit();
                        sedt3.putString("textvalue3", contactName);
                        sedt3.commit();

                        SharedPreferences sp4 = getSharedPreferences("key", 0);
                        SharedPreferences.Editor sedt4 = sp4.edit();
                        sedt4.putString("textvalue4", contactNumber);
                        sedt4.commit();

                        sp3 = getSharedPreferences("key", 0);
                        String tValue3 = sp3.getString("textvalue3","");

                        sp4 = getSharedPreferences("key", 0);
                        String tValue4 = sp4.getString("textvalue4","");

                        s3 = tValue3;
                        tv2.setText(s3);
                        s4 = tValue4;

                    } catch (Exception e) {
                        e.printStackTrace();


                    }
                    break;



            }
        }
    }


}