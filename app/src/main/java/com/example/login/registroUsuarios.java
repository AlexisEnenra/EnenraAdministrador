package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registroUsuarios extends AppCompatActivity {

    private EditText mEditTextNombre;
    private EditText mEditTextcorreo;
    private EditText mEditTexttelefono;
    private EditText mEditTextclave;
    private Button mButtonRegistrar;

// variables de los datos a registrar

    private String name ="";
    private String correo ="";
    private String telefono ="";
    private String clave ="";


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        mEditTextNombre = (EditText)findViewById(R.id.editTextNombre);
        mEditTextcorreo = (EditText)findViewById(R.id.editTextCorreo);
        mEditTexttelefono = (EditText)findViewById(R.id.editTextTelefono);
        mEditTextclave = (EditText)findViewById(R.id.editTextClave);
        mButtonRegistrar = (Button)findViewById(R.id.btn_registrar);

        mButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name =mEditTextNombre.getText().toString();
                correo =mEditTextcorreo.getText().toString();
                telefono =mEditTexttelefono.getText().toString();
                clave =mEditTextclave.getText().toString();

                if (!name.isEmpty() && !correo.isEmpty() && !telefono.isEmpty() && !clave.isEmpty()){

                    if (clave.length() >=6 ){
                        registerUser();

                    }else{
                        Toast.makeText(registroUsuarios.this, "La Clave debe ser min 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(registroUsuarios.this, "Debe Completar Los Campos", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }


    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Map<String,Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("correo", correo);
                    map.put("telefono", telefono);
                    map.put("clave", clave);


                    String id =mAuth.getCurrentUser().getUid();

                    mDatabase.child("Clientes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){
                                Toast.makeText(registroUsuarios.this, "se a registrado con exito", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registroUsuarios.this,Menu.class));

                            }else{
                                Toast.makeText(registroUsuarios.this, "error al registrar", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(registroUsuarios.this, " no se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}