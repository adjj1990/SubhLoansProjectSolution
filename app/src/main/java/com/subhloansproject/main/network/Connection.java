package com.subhloansproject.main.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Connection {

    public static boolean checkConnection(Context ctx)
    {
        ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;

        return true;
    }
    
    public String performGetCall(String uri){
        try {
            URL url = new URL(uri);
//            Log.e(">>>>>>requestURL>>",uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String result;

            StringBuilder sb = new StringBuilder();

            while((result = bufferedReader.readLine())!=null){
                sb.append(result);
            }

//            Log.e(">>>>>>requestURL>>",sb.toString());
            return sb.toString();
        } catch (Exception e) {
            //return null;
            return "{\"response\":\"ERROR\",\"msg\":\"Device busy. Please try again !\"}";
        }
    }

    public String performPostCall(String requestURL,
                                         HashMap<String, String> postDataParams, Context context) {

//        Log.e(">>>>>>requestURL>>",requestURL);
//        if (!postDataParams.isEmpty())
//            Log.e(">>>>>>requestData>>",postDataParams.toString());

        URL url;
        String response = "";

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "{\"response\":\"server\",\"code\":\""+responseCode+"\" }";

            }
//            Log.e(">>>>>response ",response + " -- " + responseCode);
        }catch (Exception e) {
            e.printStackTrace();
            response = "{\"response\":\"server\"}";
        }finally {
            return response;
        }


       // return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public String uploadFileToServer(String UPLOAD_URL,
                                            HashMap<String, String> postDataParams, Context context){

        String response ="";

        try {

            String charset = "UTF-8";

            MultipartUtility multipart = new MultipartUtility(UPLOAD_URL, charset);

//            Log.e(">>>>>>requestURL>>",UPLOAD_URL );
//            if (!postDataParams.isEmpty())
//                Log.e(">>>>>>requestData>>",postDataParams.toString());

            for(Map.Entry<String, String> entry : postDataParams.entrySet()){

                if (entry.getKey().equals("file")){
                    File uploadFile1 = new File(context.getCacheDir() +  File.separator + entry.getValue());
                    multipart.addFilePart(entry.getKey(), uploadFile1);

                }else if(entry.getKey().equals("chat_file")) {
                    Uri myUri = Uri.parse(entry.getValue());
                    multipart.addFilePart("file", new File(myUri.getPath()));
                }else{
                    multipart.addFormField(entry.getKey(), entry.getValue());
                }
            }

            response = multipart.finish();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.e(">>>>>>response ", response);

        return response.toString();
    }
}
