package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Libro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLibro {

    @POST("libro")
    public Call<Libro> insertLibro(@Body Libro obj);
}
