package com.somoplay.screenshow.webrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.LoginActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreDishList;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JudyShuai on 15-07-02.
 */
public class LoginRequest {
    private Context context=null;
    private LoginActivity loginActivity = null;

    private static final String url = "http://eadate.com:8080/screenshow/restful/admin/search";
    private String email_login;
    private String password;
    private String jsonString;
    private StoreDishList storeDishList;
    private String email;
    private int adminId;
    private String loginStatus;

    public LoginRequest(LoginActivity loginActivity) {
        super();
        this.loginActivity = loginActivity;
    }

    //get Strings from service:

    public void generateObjectFromJson(String jsonString) {

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
//                int id = dataObj.getInt("id");
//                String user_name = dataObj.getString("user_name");

            String type=jsonObject.getString("type");
            if(type.equals("dictionary")){
                JSONObject data = jsonObject.getJSONObject("data");
                email = data.getString("email");
                loginStatus= jsonObject.getString("status");
                adminId=data.getInt("admin_id");

                SharedPreferences sharedPref = AppController.getInstance()
                        .getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(Constants.KEY_ADMIN_ID, adminId);
                editor.commit();



            }
            else if(type.equals("message")){
                loginStatus = jsonObject.getString("status");

            }
            this.loginActivity.afterGetStatus(loginStatus);
//            JSONObject data=jsonObject.getJSONObject("data");
//                 email = data.getString("email");
//                String email_is_verified = dataObj.getString("email_is_verified");
//                online = jsonObject.getString("online");
//                active = jsonObject.getString("active");
//                banned = jsonObject.getString("banned");
//                deleted = jsonObject.getString("deleted");
//                updated_ts = jsonObject.getString("updated_ts");
//                created_ts = jsonObject.getString("created_ts");
//                type = jsonObject.getString("type");
//                service = jsonObject.getString("service");
//                  loginStatus= jsonObject.getString("status");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendStringRequest(final String email_login,final String password) {
        this.email_login=email_login;
        this.password=password;

        String searchUrl = "http://eadate.com:8080/screenshow/restful/admin/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        jsonString=response;
                        new ParseJson().execute();


//                        StoreDishList storeDishList =generateObjectFromJson(response);
//                        storeShowActivity.afterGettingStoreDishData(storeDishList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("+++++++++++++++++++++++++++++++Error ["+error+"]");
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "login");
                params.put("email", email_login);
                params.put("password",password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReq);
    }

    class ParseJson extends AsyncTask<Void, Void, Void> {

        //        StoreTypeName storeTypeName;
        @Override
        protected Void doInBackground(Void... params) {
            generateObjectFromJson(jsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

}
