package com.example.dryhunch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/getPrediction")
    Call<pred_response> get_pred(@Body pred_request predRequest);
}
