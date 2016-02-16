package com.mcp.mycareerplan;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILogin {
    @POST("/logon/login") Call<ResponseBody> credentials(@Body String body);
}