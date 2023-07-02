package com.example.volleyapideber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView mensaje = findViewById(R.id.txtmensaje);

        Bundle bundle = getIntent().getExtras();
        JSONObject jsonDatos = new JSONObject();
        try {
            if (bundle != null) {
                jsonDatos.put("fuente", "1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.uealecpeterson.net/public/productos/search";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonDatos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String pro = "";
                        try {
                            JSONArray productos = response.getJSONArray("productos");
                            for (int i = 0; i < productos.length(); i++) {
                                JSONObject producto = productos.getJSONObject(i);
                                pro += "\n Codigo de Barra: " + producto.getString("barcode") + "\n Descripcion: " + producto.getString("descripcion")
                                        + "\n Precio: " + producto.getString("precio_unidad");
                            }
                            mensaje.setText(pro);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mensaje.setText("Error: " + error.networkResponse.statusCode);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + bundle.getString("Token"));
                return headers;
            }
        };
        queue.add(request);
    }
}