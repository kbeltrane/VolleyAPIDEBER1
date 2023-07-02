package com.example.volleyapideber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btnbutton(View view){
        EditText user = findViewById(R.id.txtuser);
        EditText password = findViewById(R.id.txtpassword);
        Map<String, String> datos = new HashMap<>();
        datos.put("correo",user.getText().toString());
        datos.put("clave",password.getText().toString());
        String url="https://api.uealecpeterson.net/public/login";
        WebService ws= new WebService(url,datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView mensaje = findViewById(R.id.txtmensaje);
        JSONObject jsonResp = new JSONObject(result);
        if(jsonResp.has("error")) {
            mensaje.setText("Error:" + jsonResp.getString("error"));
        }else{
            Bundle b = new Bundle();
            b.putString("Token", jsonResp.getString("access_token"));
            Intent intent= new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}