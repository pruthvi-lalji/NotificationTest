package com.example.notificationtest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String,Void,String> {

    //context
    Context context;
    String result;




    BackgroundTask(Context ctx) {
        this.context = ctx;


    }


    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        if(type.equals("token")){
           // String token_link = "http://10.0.2.2/andriodApp/token.php"; //Local Database
            String token_link = "https://brunelbetterapp.000webhostapp.com/token.php";
            try {
                String user = voids[1];
                String token = voids[2];
                URL url = new URL(token_link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + "&"
                        + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Reading the results
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                //Closing the connections
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        return null;
    }


//posing the results

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onPostExecute(String result) {
        MainActivity.textView.setText(this.result);
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}