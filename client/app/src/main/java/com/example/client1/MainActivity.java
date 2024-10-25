package com.example.client1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    Retrofit mRetrofit;
    private MyAPI mMyAPI;

    private TextView mListTv;
    private Button mGetButton;
    private Button mPostButton;
    private Button mPatchButton;
    private Button mDeleteButton;
    private Button mGetOneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListTv = findViewById(R.id.result1);

        mGetButton = findViewById(R.id.button1);
        mGetButton.setOnClickListener(this);
        mPostButton = findViewById(R.id.button2);
        mPostButton.setOnClickListener(this);
        mPatchButton = findViewById(R.id.button3);
        mPatchButton.setOnClickListener(this);
        mDeleteButton = findViewById(R.id.button4);
        mDeleteButton.setOnClickListener(this);
        mGetOneButton = findViewById(R.id.button5);
        mGetOneButton.setOnClickListener(this);

        setRetrofitInit();
    }

    private void setRetrofitInit(){
        Log.d(TAG, "initMyAPI : " + getString(R.string.baseUrl));

        mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = mRetrofit.create(MyAPI.class);
    }

    @Override
    public void onClick(View v){
        if (v == mGetButton){
            Log.d(TAG, "GET");
            Call<List<UserInfo>> getCall = mMyAPI.getAllUsers();

            getCall.enqueue(new Callback<List<UserInfo>>(){
                @Override
                public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response){
                    if (response.isSuccessful()){
                        mListTv.setText(response.body().toString());
                    }
                    else{
                        Log.d(TAG, "Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                    Log.d(TAG, "Fail msg : " + t.getMessage());
                }
            });
        } else if(v == mPostButton){
            Log.d(TAG, "POST");

            UserInfo item = new UserInfo();

            Call<UserInfo> postCall = mMyAPI.post_posts(item);
            postCall.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"등록 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.d(TAG,response.errorBody().toString());
                        Log.d(TAG,call.request().body().toString());
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        } else if(v == mPatchButton){
            Log.d(TAG,"PATCH");
            UserInfo item = new UserInfo();
            Call<UserInfo> patchCall = mMyAPI.patch_posts(1,item);
            patchCall.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"patch 성공");
                    }else{
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        } else if( v == mDeleteButton){
            Log.d(TAG,"DELETE");
            Call<UserInfo> deleteCall = mMyAPI.delete_posts(2);
            deleteCall.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"삭제 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        } else if (v == mGetOneButton){
            Log.d(TAG, "GET");
            Call<UserInfo> getOneCall = mMyAPI.get_post_pk(1);
            getOneCall.enqueue(new Callback<UserInfo>(){
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response){
                    if(response.isSuccessful()){
                        mListTv.setText(response.body().toString());
                    }
                    else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d(TAG, "Fail msg : " + t.getMessage());
                }
            });
        }

    }

    private Callback<String> mRetrofitCallback = new Callback<String>(){

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            Log.d(TAG, result);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    };

}