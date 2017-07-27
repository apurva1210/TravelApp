package com.example.asus.touristinfoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class UserSessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context ctx;
    int PRIVATE_MODE=0;

    private static final String PREFENCE_NAME="Prefernces";

    private static final String IS_USER_LOGGED_IN="IsUserLoggedIn";

    static final String KEY_EMAIL="email",KEY_PASS="password";


    public UserSessionManager(Context ctx)
    {
        this.ctx=ctx;
        preferences=ctx.getSharedPreferences(PREFENCE_NAME,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public void createUserLoginSession(String email, String pass)
    {
        editor.putBoolean(IS_USER_LOGGED_IN,true);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASS,pass);
        editor.commit();
    }

    public boolean isUserLoggedIn()
    {
        return preferences.getBoolean(IS_USER_LOGGED_IN,false);
    }


    public boolean checkLogin()
    {
        if(!this.isUserLoggedIn())
        {
            Intent intent=new Intent(ctx,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            return true;
        }
        return false;
    }

    public void logoutUser()
    {
        editor.clear();
        editor.commit();
        Intent intent=new Intent(ctx,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }


}
