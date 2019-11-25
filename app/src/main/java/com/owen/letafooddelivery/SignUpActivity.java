package com.owen.letafooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.owen.letafooddelivery.Model.User;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText etPasswordSign, etPhoneSign, etNameSign;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNameSign = findViewById(R.id.et_UserName);
        etPasswordSign = findViewById(R.id.et_signup_password);
        etPhoneSign = findViewById(R.id.signUp_et_phoneNumber);

        btnSignUp = findViewById(R.id.btn_SignUp);


        database = FirebaseDatabase.getInstance();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final DatabaseReference table_user = database.getReference("user");

                mdialog = new ProgressDialog(SignUpActivity.this);
                mdialog.setMessage("Please wait,,,,,,");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        //check if user already exit
                        if (dataSnapshot.child(etPhoneSign.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "USer Exist,  Login", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            mdialog.dismiss();
                            User user = new User(etNameSign.getText().toString(),etPasswordSign.getText().toString());
                            table_user.child(etPhoneSign.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign up success", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
            }
        });


    }
}
