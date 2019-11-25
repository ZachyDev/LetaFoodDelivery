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

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etPassword, etPhone;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        etPhone = findViewById(R.id.login_et_phoneNumber);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mdialog = new ProgressDialog(LoginActivity.this);
                        mdialog.setMessage("Please wait,,,,,,");
                        mdialog.show();
                        //get user information and check if he  exists or not
                        if (dataSnapshot.child(etPhone.getText().toString()).exists()) {
                            mdialog.dismiss();
                            User user = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(etPassword.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "Signed in successsfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Signed failed !!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            mdialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User Not Found in Database", Toast.LENGTH_LONG).show();
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
