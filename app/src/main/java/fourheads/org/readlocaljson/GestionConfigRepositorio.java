package fourheads.org.readlocaljson;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by leandro on 16/11/14.
 */
public class GestionConfigRepositorio{

    public GestionConfig recuperarConfiguracion(){

        GestionConfig gestionConfig = new GestionConfig();

        String json = leerJSON();

        try {

            JSONObject obj = new JSONObject(json);

            String urlRestful = obj.getString("urlRestful");
            String user = obj.getString("user");
            String pass = obj.getString("pass");
            String save = obj.getString("save");

            gestionConfig.setUrlRestful(urlRestful);
            gestionConfig.setUser(user);
            gestionConfig.setPass(pass);
            gestionConfig.setSave(Boolean.valueOf(save));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return gestionConfig;
    }

    public void guardarConfiguracion(){

    }

    private String leerJSON(){

        String json = null;

        Activity a = new Activity();
        try {
            FileInputStream fis = a.openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"false\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }
}
