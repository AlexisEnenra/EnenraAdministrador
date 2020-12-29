package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class restablecerPassword extends AppCompatActivity {


    private EditText medit_Correo;
    private Button mButton_Reset;
    private String email="";
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_password);

        medit_Correo  = (EditText) findViewById (R.id.edit_Correo);
        mButton_Reset = (Button)findViewById(R.id.Button_Reset);
        mAuth= FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);


        mButton_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = medit_Correo.getText().toString();

                if(!email.isEmpty()){

                    mDialog.setMessage("Espere por favor..");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();

                    resetPassword();
                }else{
                    Toast.makeText(restablecerPassword.this, "Debe Ingresar El EMail", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void resetPassword() {

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(restablecerPassword.this, "Se ha Enviado un correo Para Restablecer su Contraseña", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(restablecerPassword.this, "ingrese un Correo Valido porfavor", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();



            }
        });

    }
}