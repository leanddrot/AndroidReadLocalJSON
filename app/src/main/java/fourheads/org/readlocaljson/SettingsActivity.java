package fourheads.org.readlocaljson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText editTextUrl = (EditText)findViewById(R.id.editText_settings_url);
        Button buttonSave = (Button)findViewById(R.id.button_guardar);
        final Activity activity = this;

        final GestionConfigRepositorio gestionConfigRepositorio = new GestionConfigRepositorio();
        final GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(activity);

        editTextUrl.setText(config.getUrlRestful());

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final GestionConfig config = gestionConfigRepositorio.recuperarConfiguracion(activity);
                config.setUrlRestful(editTextUrl.getText().toString());
                gestionConfigRepositorio.guardarConfiguracion(activity, config);

                finish();
            }
        });

    }

}
