package com.example.nacho.encuestadepuntaje;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import net.sourceforge.jtds.jdbc.DateTime;

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

public class OpcionesActivity extends AppCompatActivity {

    private final int ID_TEMAS_PERSONALES = 3;
    private final int ID_TEMAS_LABORALES = 4;
    private final int ID_OTROS = 5;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);


        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(OpcionesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },20000);



        final int valorRecibido = getIntent().getExtras().getInt("calificacion");
        final RadioButton radioButton1 = findViewById(R.id.radioButton1);
        final RadioButton radioButton2 = findViewById(R.id.radioButton2);
        final RadioButton radioButton3 = findViewById(R.id.radioButton3);
        Map<Integer, ListaOpcion> mapOpciones=MainActivity.mapOpciones;

        ListaOpcion o = mapOpciones.get(ID_TEMAS_PERSONALES);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton1.setText(opcion);
            }
        }

        o = mapOpciones.get(ID_TEMAS_LABORALES);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton2.setText(opcion);
            }
        }

        o = mapOpciones.get(ID_OTROS);

        if (o !=null)
        {
            String opcion = o.getDescripcion();
            if (opcion !=null){
                radioButton3.setText(opcion);
            }
        }

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos(ID_TEMAS_PERSONALES);
                timer.cancel();
                startActivity(new Intent(OpcionesActivity.this, FinalPersonalesActivity.class));
                finish();
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                startActivity(new Intent(OpcionesActivity.this, Opciones2Activity.class));
                finish();
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos(ID_OTROS);

                timer.cancel();
                startActivity(new Intent(OpcionesActivity.this, FinalOtrosActivity.class));
                finish();
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
