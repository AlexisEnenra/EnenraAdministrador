package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListarUsuarios extends AppCompatActivity {


    private List<usuarios>usuariosList=new ArrayList<usuarios>();
    ArrayAdapter<usuarios>usuariosArrayAdapter;
    EditText nomP,rutP,teleP;
    ListView list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayout ln;
    usuarios usuariosselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuario);

        ln =findViewById(R.id.ln);
        list=findViewById(R.id.lv_Listapersonas);

        nomP=findViewById(R.id.nombre);
        rutP=findViewById(R.id.apellido);
        teleP=findViewById(R.id.telefono);
        iniciarfireBase();
        mostrarDatos();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                usuariosselect=(usuarios)parent.getItemAtPosition(i);
                nomP.setText(usuariosselect.getNombre());
                rutP.setText(usuariosselect.getRut());
                teleP.setText(usuariosselect.getTelefono());
                ln.setVisibility(View.VISIBLE);
            }
        });


    }

    private void iniciarfireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference=firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulista,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_add: {
                insertar();
                break;
            }
            case R.id.icon_save:{
                usuarios p=new usuarios();
                p.setUid(usuariosselect.getUid());
                p.setNombre(nomP.getText().toString().trim());
                p.setRut(rutP.getText().toString().trim());
                p.setTelefono(teleP.getText().toString().trim());
                databaseReference.child("Usuarios").child(p.getUid()).setValue(p);
                Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();
                ln.setVisibility(View.GONE);

                break;
            }

            case R.id.icon_delete: {
                usuarios p=new usuarios();
                p.setUid(usuariosselect.getUid());
                databaseReference.child("Usuarios").child(p.getUid()).removeValue();
                Toast.makeText(this,"Eliminado",Toast.LENGTH_SHORT).show();
                ln.setVisibility(View.GONE);

                break;

            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void insertar() {

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(ListarUsuarios.this);
        View view=getLayoutInflater().inflate(R.layout.activity_insertar_usuarios,null);
        Button comentar=(Button)view.findViewById(R.id.btn_IngresarUsuario);

        final TextView nombre=(TextView) view.findViewById(R.id.nombre);
        final TextView apellido=(TextView) view.findViewById(R.id.apellido);
        final TextView telefon=(TextView) view.findViewById(R.id.telefono);

        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombr=nombre.getText().toString();
                String apellid=apellido.getText().toString();
                String telefo=telefon.getText().toString();


                usuarios p=new usuarios();
                p.setUid(UUID.randomUUID().toString());
                p.setNombre(nombr);
                p.setRut(apellid);
                p.setTelefono(telefo);
                databaseReference.child("Usuarios").child(p.getUid()).setValue(p);
                Toast.makeText(ListarUsuarios.this,"agregado",Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        });
    }

    public void mostrarDatos() {

        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuariosList.clear();
                for (DataSnapshot objSanptshot: snapshot.getChildren()){
                    usuarios p=objSanptshot.getValue(usuarios.class);
                    usuariosList.add(p);
                    usuariosArrayAdapter=new ArrayAdapter<usuarios>(ListarUsuarios.this,android.R.layout.simple_list_item_1,usuariosList);
                    list.setAdapter(usuariosArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}