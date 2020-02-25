package org.techtown.image_load.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.image_load.DatabaseHelper;
/*SplashActivity
*
* 로딩의 개념
* 홈페이지에서 이미지 url을 받아와서 데이터 베이스에 저장
* url을 다 받아왔으면 다음의 ListActivity로 화면전환
* 데이터 베이스로는 내장DB인 SQLite를 사용 */
public class SplashActivity extends AppCompatActivity {

    String[] content={"dog","cat","hippo","baby pig","goose","parrot","fox","wolf","rabit","elephant","horse","panda"};


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        NetworkTask networkTask = new NetworkTask();
        networkTask.execute(content);//AsyncTask class NetworkTask 실행
    }




    //Network와 연결해야하는 일은 thread처리 해줘야 하기때문에 AsyncTask사용
    //https://itmining.tistory.com/7
    //공부한 홈페이지
    private class NetworkTask extends AsyncTask<String[], Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(String[]... str) {
            //DatabaseHelper class
            DatabaseHelper Sampledb=new DatabaseHelper(getApplicationContext());
            Sampledb.get_img(str[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Intent intent=new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}