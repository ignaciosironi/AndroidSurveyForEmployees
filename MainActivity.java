package com.example.nacho.encuestadepuntaje;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton botonfeliz;
    private ImageButton  botonneutral;
    public static final String SHOULD_FINISH = "should_finish";
    public static Map<Integer, ListaOpcion> mapOpciones = new HashMap<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        if (getIntent().getBooleanExtra(SHOULD_FINISH, false)) {
            finish();
        }

            botonfeliz = (ImageButton) findViewById(R.id.caritafeliz);
            botonneutral = (ImageButton) findViewById(R.id.caritaneutral);
            mapOpciones = obtenerOpcionBD();
            botonfeliz.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {

                                                  int ID_OK = 1;
                                                  ListaOpcion o = mapOpciones.get(ID_OK);

                                                  if (o != null) {

                                                      String opcion = o.getDescripcion();
                                                      guardarDatos(ID_OK);
                                                  }


                                                  Intent intent = new Intent(MainActivity.this, FinalOKActivity.class);
                                                  Bundle bundle = new Bundle();
                                                  bundle.putInt("calificacion", ID_OK);
                                                  intent.putExtras(bundle);
                                                  startActivity(intent);

                                              }

                                          }

            );

            botonneutral.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {


                                                    int ID_NOK = 2;

                                                    Intent intent = new Intent(MainActivity.this, OpcionesActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("calificacion", ID_NOK);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                            }
            );

    }

        public Map<Integer, ListaOpcion> obtenerOpcionBD() {

        Map<Integer, ListaOpcion> Opcion = new HashMap<>();
      try{
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery("select * from Opcion");
            while (rs.next()){
                Opcion.put( rs.getInt("IDOpcion"), new ListaOpcion(rs.getInt("IDOpcion"),rs.getString("Descripcion"),rs.getBoolean("EstadoActividad"),rs.getInt("Nivel")));
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Opcion;

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
        finish();
    }

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

}