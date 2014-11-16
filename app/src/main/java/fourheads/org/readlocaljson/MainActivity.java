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

    JSONObject obj = null;
    String user;
    String pass;
    String urlRestful;
    String save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_user = (EditText)findViewById(R.id.editText_user);
        final EditText et_pass = (EditText)findViewById(R.id.editText_pass);
        Button button_connect = (Button)findViewById(R.id.button_connect);
        final CheckBox cb_save = (CheckBox)findViewById(R.id.checkBox_save);

        String json = loadJSON();
        Log.v("JSON leido", json);


        try {
            obj = new JSONObject(json);
            urlRestful = obj.getString("urlRestful");
            user = obj.getString("user");
            pass = obj.getString("pass");
            save = obj.getString("save");
            et_user.setText(user);
            et_pass.setText(pass);
            if (save.equals("true")){
                cb_save.setChecked(true);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        button_connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {

                    obj.put("user", et_user.getText());

                    if (cb_save.isChecked()){
                        obj.put("pass", et_pass.getText());
                        obj.put("save", "true");
                    } else {
                        obj.put("pass", "");
                        obj.put("save", "false");
                    }

                    FileOutputStream fos = openFileOutput("config.json", Context.MODE_PRIVATE);
                    fos.write(obj.toString().getBytes());
                    fos.close();

                    Log.v("nuevo JSON",obj.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (urlRestful.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "No ha configurado una URL. Hágalo desde el menu \"Settings\" y reinicie la aplicación";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
        });

        Log.v("JSON", json);

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

    public String loadJSON() {
        String json = null;
        try {
            FileInputStream fis = openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }
}
