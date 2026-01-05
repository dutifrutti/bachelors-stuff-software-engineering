package com.example.labaratorinis6;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.labaratorinis6.AccountingSystem.*;
import com.example.labaratorinis6.REST.RESTControl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.labaratorinis6.REST.RESTControl.address;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    public void returnCategories(View v) throws IOException {
//        GetCategories getCategories = new GetCategories ();
//        getCategories.execute();
//    }
//    private final class GetCategories extends AsyncTask<String,String,String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String output = "";
//            String url = address + "/labaratorinis5_war_exploded/category/list";
//            try {
//                return RESTControl.sendGet(url);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "ERORAS!!!!!!!!!!!!";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            System.out.println("GAVAU IS INTERNETU:" + result);
//            if (result != null) {
//                try {
//                    Type listType = new TypeToken<ArrayList<Category>>() {
//                    }.getType();
//                    final List<Category> catData = new Gson().fromJson(result, listType);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, "negerai su duomenim kazkas", Toast.LENGTH_LONG);
//                }
//            } else
//                Toast.makeText(MainActivity.this, "if result null failed", Toast.LENGTH_LONG);
//
//        }
//    }



        public void connectToAccSis (View v){
            System.out.println("buttton pressed");
            EditText login = findViewById(R.id.loginField);
            String dataToSend = "{\"login\":\"" + login.getText().toString() + "\"}";
            UserLogin userlogin = new UserLogin();
            userlogin.execute(dataToSend);
        }

    private final class UserLogin extends AsyncTask<String,String,String> {
        @Override
        public void onPreExecute (){
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Validating login data", Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... params) {

            String url = address + "/labaratorinis5_war_exploded/user";
            String dataToSend = params[0];

            System.out.println("SENT: " + dataToSend);
            try {

                return RESTControl.sendPost(url, dataToSend);
            } catch (Exception e) {
                e.printStackTrace();
                return "Error while getting data from web";
            }
        }
        @Override
        protected void onPostExecute (String result){
            super.onPostExecute(result);
            System.out.println("RECEIVED: " + result);
            if (result != null && !result.equals("Wrong credentials")){
                try {
                    Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"Wrong data",Toast.LENGTH_LONG).show();
                }
            }
            else
            Toast.makeText(MainActivity.this,"Wrong data",Toast.LENGTH_LONG).show();
        }



    }

}
