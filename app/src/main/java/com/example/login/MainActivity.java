package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText user,pass;
    private Button ini;
    private Button mbtnReset_password, btn_registro;
    private String correo= "";
    private String contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        //inicializamos
        user = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        ini = (Button)findViewById(R.id.btn_login);
        mbtnReset_password = (Button)findViewById(R.id.btnReset_password);
        btn_registro = (Button)findViewById(R.id.btn_registro);




        //boton registro
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,registroUsuarios.class));
            }
        });

        //Boton Reset
        mbtnReset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,restablecerPassword.class));

            }
        });
            //********************************//

        ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = user.getText().toString();
                contraseña= pass.getText().toString();

                if(!correo.isEmpty() && !contraseña.isEmpty()){

                    loginUser();

                }else{
                    Toast.makeText(MainActivity.this, "Complete los campos ", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

     private void loginUser(){

        mAuth.signInWithEmailAndPassword(correo,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,Menu.class));
                    Toast.makeText(MainActivity.this, "Bienvenido   "+ correo, Toast.LENGTH_LONG).show();
                    finish();;
                }else{
                    Toast.makeText(MainActivity.this, "Error. Compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,Menu.class));
            finish();
        }

    }
}