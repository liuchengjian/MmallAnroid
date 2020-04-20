package com.lchj.mmallanroid.mvp.model.api.service;

import com.lchj.mmallanroid.mvp.model.entity.BaseResponse;
import com.lchj.mmallanroid.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("/user/login.do")
    Observable<BaseResponse<User>> login(@Field("username") String username,
                                         @Field("password") String password);


    @FormUrlEncoded
    @POST("/user/register.do")
    Observable<BaseResponse> register(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("email") String email,
                                      @Field("phone") String phone,
                                      @Field("question") String question,
                                      @Field("answer") String answer,
                                      @Field("role") int role);
}
