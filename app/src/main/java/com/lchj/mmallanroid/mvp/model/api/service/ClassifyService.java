package com.lchj.mmallanroid.mvp.model.api.service;

import com.lchj.mmallanroid.mvp.model.entity.BaseListRes;
import com.lchj.mmallanroid.mvp.model.entity.BaseResponse;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.lchj.mmallanroid.mvp.model.entity.Product;
import com.lchj.mmallanroid.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClassifyService {
    @GET("/manage/category/get_category.do")
    Observable<BaseResponse<List<Category>>> getOneClassify();

    @GET("/product/list.do")
    Observable<BaseResponse<BaseListRes<Product>>> getProductList(@Query("categoryId") int categoryId);


}
