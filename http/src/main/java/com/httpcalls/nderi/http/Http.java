package com.httpcalls.nderi.http;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;

public class Http {
    //new SendRequest().execute();
    JSONObject postDataParams = new JSONObject();
    String _url = "";
    String response;
    int timeout;
    String requestmethod;
    HttpURLConnection conn;
    public String message;
    public String error_message;
    boolean isComplete;
   public Http(String url, int timeout, String requestmethod){
       isComplete = false;
        this._url = url;
        this.timeout = timeout;
        this.requestmethod = requestmethod;
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL(_url);
                Log.e("params",postDataParams.toString());

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(timeout );
                conn.setRequestMethod(requestmethod);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    message = "" + responseCode;
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                //  Toast.makeText(MainActivity.this, "Error"+ e.getMessage(),Toast.LENGTH_LONG);
                error_message = e.getMessage().toString();
                return new String("Exception: " + e.getMessage());

            }
        }

        @Override
        protected void onPostExecute(String result) {
            isComplete = true;
            response = result;
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public  String getResponse(){

        return  response;
    }

    public String setValue(String name , String value){
        try{
            postDataParams.put(name, value);

        }catch (Exception e){
           // Toast.makeText(Http.this, e.getMessage(), Toast.LENGTH_SHORT);
            return  e.getMessage();
        }
        return null;
    }



    public void execute(){
        new SendRequest().execute();
    }

}
