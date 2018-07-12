package com.sikefeng.chinaren.api;


import com.sikefeng.chinaren.entity.model.ImageListData;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.entity.model.NoteData;
import com.sikefeng.chinaren.entity.model.NoteListData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NoteApiService {

    @POST("notebook/note_save")
    Observable<NoteData> saveNote(@Body NoteBean noteBean);

    @POST("notebook/note_update")
    Observable<NoteData> updateNote(@Body NoteBean noteBean);

    @GET("notebook/note_get")
    Observable<NoteData> getNote(@Query("id") String id);

    @GET("notebook/note_delete")
    Observable<NoteData> deleteNote(@Query("id") String id);

    @GET("notebook/note_findlist")
    Observable<NoteListData> findList(@Query("userid") String userid, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize, @Query("orderBy") String orderBy);

    @GET("image/image_findlist")
    Observable<ImageListData> imageList(@Query("pageNo") String pageNo, @Query("pageSize") String pageSize, @Query("orderBy") String orderBy);

}
