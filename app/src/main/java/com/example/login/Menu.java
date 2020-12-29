package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button btn_crudUsuario,btn_listar,btn_crudMantencion,btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        btn_crudMantencion=(Button)findViewById(R.id.btn_crudMantencion);
        btn_listar=(Button)findViewById(R.id.btn_listar);
        btn_salir=(Button)findViewById(R.id.btn_salir);


            btn_listar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Menu.this,AgregarUsuarios.class);
                    startActivity(intent);
                }
            });


        btn_crudMantencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Menu.this,ListarUsuarios.class);
                startActivity(intent);
            }
        });


            btn_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    startActivity(new Intent(Menu.this,MainActivity.class));
                    finish();
                }
            });


         }

    }
