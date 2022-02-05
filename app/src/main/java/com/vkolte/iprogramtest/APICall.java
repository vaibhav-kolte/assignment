package com.vkolte.iprogramtest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class APICall {
    private static final String TAG = "APICall";
    final RequestQueue queue;
    private APIResult apiResult;
    private Context context;

    public APICall(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void init(APIResult apiResult){
        this.apiResult = apiResult;
    }

    public void getImageData(){
        String url = "https://jsonplaceholder.typicode.com/photos";
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            Log.e(TAG, "onResponse: Array Length" + response.length());
                            ArrayList<CompareModel> compareModelList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                if(!TextUtils.isEmpty(""+jsonObject.getInt("id")) &&
                                        !TextUtils.isEmpty(jsonObject.getString("title")) &&
                                        !TextUtils.isEmpty(jsonObject.getString("url")) &&
                                        !TextUtils.isEmpty(jsonObject.getString("thumbnailUrl"))
                                ) {
                                    compareModelList.add(new CompareModel(jsonObject.getInt("id"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("url"),
                                            jsonObject.getString("thumbnailUrl"),
                                            false)
                                    );
                                }
                            }

                            apiResult.onSuccess(compareModelList);

                        } catch (Exception e) {
                            e.printStackTrace();
                            apiResult.onError();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.e(TAG, "onErrorResponse: " + error.toString());
                            apiResult.onError();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        queue.add(jsonArrayRequest);
    }

    public interface APIResult{
        public void onSuccess(ArrayList<CompareModel> compareModelArrayList);
        public void onError();
    }
}
