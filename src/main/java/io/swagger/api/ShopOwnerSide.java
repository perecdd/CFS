package io.swagger.api;

import io.swagger.models.auth.In;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ShopOwnerSide {
    public static String port = "15676";
    public static String ip = "http://localhost"; // TODO: shopownerside
    public static HttpURLConnection con;

    public static boolean POST(String line, String way){
        try {
            URL url = new URL (ip + ":" + port + way);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
            out.println(line);

            int responseCode = con.getResponseCode();
            out.close();
            con.disconnect();

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject GET(JSONObject JSON, String way){
        try {
            URL url = new URL (ip + ":" + port + way);

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            if(JSON.containsKey("name")) con.setRequestProperty("name", (String) JSON.get("name"));
            if(JSON.containsKey("minPrice")) con.setRequestProperty("minPrice", String.valueOf((Integer) JSON.get("minPrice")));
            if(JSON.containsKey("maxPrice")) con.setRequestProperty("maxPrice", String.valueOf((Integer)JSON.get("maxPrice")));
            if(JSON.containsKey("companyID")) con.setRequestProperty("companyID", String.valueOf((Integer)JSON.get("companyID")));
            if(JSON.containsKey("count")) con.setRequestProperty("count", String.valueOf((Integer)JSON.get("count")));
            if(JSON.containsKey("productID")) con.setRequestProperty("productID", String.valueOf((Integer)JSON.get("productID")));

            con.setRequestMethod("GET");

            con.setDoOutput(true);
            con.setDoInput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());

            int responseCode = con.getResponseCode();
            con.disconnect();

            return jsonObject;
        }
        catch (Exception e){
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
