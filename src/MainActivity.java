package myapp.swanand.com.stacktop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import myapp.swanand.com.stacklist.R;


public class MainActivity extends AppCompatActivity {

    String json_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void getJson(View v)
    {
            new BackgroundTask().execute();
    }


    public void parseJson(View v)
    {



            Intent intent=new Intent(this,DisplayList.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent);

    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String json_url;
        String JSON_STRING;
        @Override
        protected void onPreExecute() {

            json_url="https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=False&answers=0&tagged=android&site=stackoverflow";

        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url =new URL(json_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder=new StringBuilder();

                while( (JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals(null))
            {
                Toast.makeText(getApplicationContext(),"No data recieved!",Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("JSON", result);
                json_string = result;
            }

        }

    }

}
