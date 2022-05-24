package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.SalaAdapter;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaConsultaActivity extends NewAppCompatActivity {


    EditText txtNumero;
    Button btnFiltrar;

    ListView lstSala;
    ArrayList<Sala> data = new ArrayList<Sala>();
    SalaAdapter adaptador;

    //Conexion al servicio Rest
    ServiceSala api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_consulta);

        txtNumero = findViewById(R.id.txtRegSalNumero);

        lstSala = findViewById(R.id.lstConsultaSala);
        adaptador = new SalaAdapter(this, R.layout.activity_sala_crud_item, data);
        lstSala.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceSala.class);

        btnFiltrar = findViewById(R.id.btnRegSalEnviar);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtNumero.getText().toString();
                consulta(filtro);
            }
        });
    }

    public void consulta(String filtro){

        Call<List<Sala>> call = api.consulta(filtro);
        call.enqueue(new Callback<List<Sala>>() {
            @Override
            public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                if (response.isSuccessful()){
                    List<Sala> lstSalida = response.body();
                    mensajeAlert("Resulta " + lstSalida.size() + " elementos");
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Sala>> call, Throwable t) {
                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}