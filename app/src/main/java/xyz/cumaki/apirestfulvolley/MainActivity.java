package xyz.cumaki.apirestfulvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btEnviar=findViewById(R.id.Enviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API();
            }
        });


    }

    public void  API() {
        TextView Txt = (TextView) findViewById(R.id.textView3);
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent i = new Intent(this, MainActivity2.class);
        String url ="https://api.uealecpeterson.net/public/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject resp = new JSONObject(response);
                            String Token = resp.get("access_token").toString();
                            i.putExtra("Token",Token);
                            startActivity(i);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Txt.setText("Error en la solicitud: " + error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                EditText Correo = findViewById(R.id.txtCorreo);
                EditText Clave = findViewById(R.id.txtContraseña);
                params.put("correo", Correo.getText().toString());
                params.put("clave", Clave.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

}