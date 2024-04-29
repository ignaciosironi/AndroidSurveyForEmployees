package com.example.nacho.encuestadepuntaje;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Opciones2Activity extends AppCompatActivity {

    private final int ID_PROCESO_ADMIN = 6;
    private final int ID_FALTA_HERRAMIENTAS = 7;
    private final int ID_AUSENCIA_LIDERAZGO = 8;
    private final int ID_PROBLEMAS_INTERPERSONALES = 9;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones2);


        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Opciones2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },20000);

        final RadioButton radioButton1Opciones2 = findViewById(R.id.radioButton1Opciones2);
        final RadioButton radioButton2Opciones2 = findViewById(R.id.radioButton2Opciones2);
        final RadioButton radioButton3Opciones2 = findViewById(R.id.radioButton3Opciones2);
        final RadioButton radioButton4Opciones2 = findViewById(R.id.radioButton4Opciones2);

        Map<Integer, ListaOpcion> mapOpciones=MainActivity.mapOpciones;

        ListaOpcion o = mapOpciones.get(ID_PROCESO_ADMIN);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton1Opciones2.setText(opcion);
            }
        }

        o = mapOpciones.get(ID_FALTA_HERRAMIENTAS);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton2Opciones2.setText(opcion);
            }
        }

        o = mapOpciones.get(ID_AUSENCIA_LIDERAZGO);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton3Opciones2.setText(opcion);
            }
        }

        o = mapOpciones.get(ID_PROBLEMAS_INTERPERSONALES);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton4Opciones2.setText(opcion);
            }
        }





        radioButton1Opciones2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guardarDatos(ID_PROCESO_ADMIN);

                timer.cancel();
                startActivity(new Intent(Opciones2Activity.this, FinalActivity.class));
            }
        });

        radioButton2Opciones2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos(ID_FALTA_HERRAMIENTAS);

                timer.cancel();
                startActivity(new Intent(Opciones2Activity.this, FinalActivity.class));
            }
        });

        radioButton3Opciones2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              guardarDatos(ID_AUSENCIA_LIDERAZGO);

                timer.cancel();
                startActivity(new Intent(Opciones2Activity.this, FinalActivity.class));
            }
        });

        radioButton4Opciones2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guardarDatos(ID_PROBLEMAS_INTERPERSONALES);

                timer.cancel();
                startActivity(new Intent(Opciones2Activity.this, FinalActivity.class));
            }
        });

    }

    public Connection conexionBD() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.61:1433;databaseName=Engagement;user=sa;password=2016ospat;");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }


    public void guardarDatos (int IDOpcion) {
        try {

            PreparedStatement pst=conexionBD().prepareStatement ("insert into Resultado (Fecha, Opcion) values(?,?)");

            pst.setTimestamp(1, new Timestamp(System.currentTimeMillis() ) );
            pst.setInt(2,IDOpcion);
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"REGISTRO AGREGADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.SHOULD_FINISH, true);
        startActivity(intent);

    }

}
