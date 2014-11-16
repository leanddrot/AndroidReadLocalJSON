package fourheads.org.readlocaljson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_user = (EditText)findViewById(R.id.editText_user);
        final EditText et_pass = (EditText)findViewById(R.id.editText_pass);
        final Button button_connect = (Button)findViewById(R.id.button_connect);
        final CheckBox cb_save = (CheckBox)findViewById(R.id.checkBox_save);
        final Activity activity = this;

        final GestionConfigRepositorio gestionConfigRepositorio = new GestionConfigRepositorio();
        final GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(this);

        et_user.setText(config.getUser());
        et_pass.setText(config.getPass());
        cb_save.setChecked(config.getSave());

        button_connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(activity);

                if (et_pass.getText().toString().isEmpty()){

                    mostrarMensaje("El campo \"pass\" no puede quedar en blanco");
                    return;
                }

                if (config.getUrlRestful().isEmpty()){

                    mostrarMensaje("No ha configurado una URL. Hágalo desde el menu \"Settings\"");
                    return;
                }

                config.setUser(et_user.getText().toString());


                if (cb_save.isChecked()){
                    config.setPass(et_pass.getText().toString());
                    config.setSave(true);
                } else {
                    config.setPass("");
                    config.setSave(false);
                }
                Log.v("user",config.getUser());
                Log.v("pass",config.getPass());
                Log.v("save",config.getSave().toString());
                Log.v("url",config.getUrlRestful());

                gestionConfigRepositorio.guardarConfiguracion(activity, config);


            }
        });

    }

    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent("android.intent.action.SETTINGS");
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
