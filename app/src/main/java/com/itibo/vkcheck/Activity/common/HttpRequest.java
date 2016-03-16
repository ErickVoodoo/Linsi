package com.itibo.vkcheck.Activity.common;

import android.os.AsyncTask;
import android.util.Log;

import com.itibo.vkcheck.Activity.models.RequestModel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by erick on 27.8.15.
 */
public class HttpRequest extends AsyncTask<RequestModel, Integer, String>{
    String bodyString = "";
    String result = "";
    String responseLine = "";
    URL url = null;

    @Override
    protected String doInBackground(RequestModel... model) {
        for(RequestModel requestModel : model){
            try
            {
                if(requestModel.getBody() != null){
                    for(Map.Entry entry: requestModel.getBody().entrySet()){
                        bodyString += entry.getKey().toString() + "=" + entry.getValue() + "&";
                    }
                }

                if(requestModel.getMethod().equals("GET") || requestModel.getMethod().equals("get")){
                    url = new URL(requestModel.getUrl() + ((bodyString.length() > 0) ? bodyString.substring(0, bodyString.length() - 1) : ""));
                } else {
                    url = new URL(requestModel.getUrl());
                }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod(requestModel.getMethod());

                if(requestModel.getHeaders() != null) {
                    for (Map.Entry entry : requestModel.getHeaders().entrySet()) {
                        connection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
                    }
                }

                connection.setRequestProperty("Content-Length", String.valueOf(((bodyString.length() > 0) ? bodyString.substring(0, bodyString.length() - 1) : "0")));
                connection.setConnectTimeout(10000);
                if(requestModel.getMethod().equals("POST") || requestModel.getMethod().equals("post")){
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes((bodyString.length() > 0) ? bodyString.substring(0, bodyString.length() - 1) : "");
                    outputStream.flush();
                    outputStream.close();
                }

                int response = connection.getResponseCode();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((responseLine = bufferedReader.readLine()) != null){
                   result +=  responseLine;
                }

                Log.e("CODE", String.valueOf(response));
                Log.e("RESPONSE", result);

                bufferedReader.close();

                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

    }

    /*HOW TO USE*/
    /*

        HttpRequest request = new HttpRequest();

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", Values.API_HEADER_ACCEPT);

        Map<String, String> body = new HashMap<>();
        body.put("app_key[secret_word]", Values.API_SECRET_WORD);

        RequestModel model = new RequestModel();
        model.setUrl("http://192.168.92.35/app_keys");
        model.setMethod("POST");
        model.setHeaders(headers);
        model.setBody(body);

        request.execute(model);


        PATCH REQUEST:
        add header : X-HTTP-Method-Override: PATCH
    */
}
