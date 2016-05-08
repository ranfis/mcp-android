package com.mcp.mycareerplan;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mcp.mycareerplan.adapters.SelectionUniversityCustomAdapter;
import com.mcp.mycareerplan.api.Request;
import com.mcp.mycareerplan.api.RequestCiclos;
import com.mcp.mycareerplan.api.accounts.Userx;
import com.mcp.mycareerplan.api.ciclos.AsignaturasCritica;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class App extends Application {

    public static Userx currentUser;
    public static List<AsignaturasCritica> listAsignaturasCritica;

    public static final String URL_PHOTO_GENERIC = "http://stanlemmens.nl/wp/wp-content/uploads/2014/07/bill-gates-wealthiest-person.jpg";

    static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    static Context _context;
    static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "unifacilPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_BIRTH = "birthday";
    public static final String KEY_USER_OBJECT = "user_object";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_URL_PHOTO = "urlPhoto";
    public static final String PACKAGE_NAME = "com.mcp.mycareerplan";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public App() {
        super();
    }

    public static Request convertToObject(String content) {
        ObjectMapper mapper = new ObjectMapper();
        Request response = null;
        try{
            response = mapper.readValue(content, Request.class);
        } catch (JsonParseException e) {
            Log.e("JSONPARSE", e.getMessage());
        } catch (JsonMappingException e) {
            Log.e("JSONMAP", e.getMessage());
        } catch (IOException e) {
            Log.e("IOEX", e.getMessage());
        }
        return response;
    }


    public static void updatePhoto(ImageView imageview, String url, Activity activity) {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderWidthDp(0)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso
                .with(activity)
                .load(url)
                .fit()
                .centerInside()
                .placeholder(R.drawable.generic_person)
                .transform(transformation)
                .into(imageview);

    }

    public static String formatterMoney(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "<b>RD$</b> "+formatter.format(amount);
    }


    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0, isize = delimiters.length; i < isize; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }


    // Constructor
    public static void setSessionManager(Context context){
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public static void createLoginSession(Userx user){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, user.getNombre());
        editor.putString(KEY_LASTNAME, user.getApellidos());
        editor.putString(KEY_BIRTH, user.getFechanacimiento());
        editor.putString(KEY_URL_PHOTO, user.getUrl());
        editor.putString(KEY_EMAIL, user.getCorreo());

        Gson gson = new Gson();
        String userInJson = gson.toJson(user);
        editor.putString(KEY_USER_OBJECT, userInJson);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If true it will redirect user to login page
     * Else won't do anything
     * */
    public static void checkLogin(){
        // Check login status
        if(isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, DashboardActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);

            App.setUserDetails();
        }

    }



    /**
     * Get stored session data
     * */
    public static void setUserDetails(){
        Userx user = new Userx();
        user.setNombre(pref.getString(KEY_NAME, null));
        user.setCorreo(pref.getString(KEY_EMAIL, null));
        user.setApellidos(pref.getString(KEY_LASTNAME, null));
        user.setFechanacimiento(pref.getString(KEY_BIRTH, null));
        user.setUrl(pref.getString(KEY_URL_PHOTO, null));

        Gson gson = new Gson();
        String json = pref.getString(KEY_USER_OBJECT, "");
        Userx user2 = gson.fromJson(json, Userx.class);

        App.currentUser = user2;
    }

    /**
     * Clear session details
     * */
    public static void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loging Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public static boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


    //FOR TUTORIAL
    //////////////TUTORIAL PURPORSE
//    public static void showMaterialIntro(Activity activity, View view, String message) {
//        new MaterialIntroView.Builder(activity)
//                .enableDotAnimation(true)
//                .setFocusGravity(FocusGravity.CENTER)
//                .setFocusType(Focus.MINIMUM)
//                .setDelayMillis(200)
////                .setListener(this)
//                .enableFadeAnimation(true)
//                .performClick(true)
//                .setInfoText(message)
//                .setTarget(view)
//                .setUsageId("LALALLALA1") //THIS SHOULD BE UNIQUE ID FOR EVERY CALL
//                .show();
//    }

//    @Override
//    public void onUserClicked(String materialIntroViewId) {
//        if(materialIntroViewId == INTRO_FOCUS_1)
//            showIntro(button2,INTRO_FOCUS_2,"This intro view focus on minimum size", Focus.MINIMUM);
//        else if(materialIntroViewId == INTRO_FOCUS_2)
//            showIntro(button3,INTRO_FOCUS_3,"This intro view focus on normal size (avarage of MIN and ALL)", Focus.NORMAL);
//    }


    //////DIFERENT TUTORIAL FROM HERE
//    public static void loadTutorial(Activity activity) {
//        Intent mainAct = new Intent(activity, MaterialTutorialActivity.class);
//        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(activity.getApplicationContext()));
//        activity.startActivityForResult(mainAct, 0);
//
//    }
//
//    private static ArrayList<TutorialItem> getTutorialItems(Context context) {
//        TutorialItem tutorialItem1 = new TutorialItem("PRUEBA 1: TITULO", "PRUEBA SUBTITULO",
//                R.color.primary, R.drawable.generic_person,  R.drawable.bg_profile_card);
//
//
//        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
//        tutorialItems.add(tutorialItem1);
//
//        return tutorialItems;
//    }
}


