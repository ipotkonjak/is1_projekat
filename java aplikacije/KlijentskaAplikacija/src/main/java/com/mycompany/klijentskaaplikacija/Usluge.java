/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.klijentskaaplikacija;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * @author Iva
 */
public interface Usluge {
    @POST("mesto/{Naziv}/{PostBr}")
    Call<String>  createMesto(@Path("Naziv") String naziv, @Path("PostBr") int postBr);
    @GET("mesto")
    Call<String> getAllMesto() ;
    @POST("filijala/{Naziv}/{Adresa}/{IdMes}")
    Call<String> createFilijala(@Path("Naziv") String naziv, @Path("Adresa") String adr, @Path("IdMes") int idMes);
    @GET("filijala")
    Call<String> getAllFilijala();
    @POST("komitent/{Naziv}/{Adresa}/{Sediste}")
    Call<String> createKomitent(@Path("Naziv") String naziv, @Path("Adresa") String adr, @Path("Sediste") int sediste);
    @PUT("komitent/{IdKom}/{NovoSediste}")
    Call<String> changeSediste(@Path("IdKom") int idKom, @Path("NovoSediste") int novo);
    @GET("komitent")
    Call<String> getAllKomitent();
    @POST("racun/{idK}/{idMes}/{Minus}")

    Call<String> createRacun(@Path("idK") int IdK, @Path("idMes") int IdMes, @Path("Minus") int dozvMinus);
    @PUT("racun/{idR}")
    Call<String> shutDownRacun(@Path("idR") int IdR);
    @GET("racun/{idK}")
    Call<String> getAllRacun(@Path("idK") int IdK);
    @POST("transakcija/{idRPos}/{idRPri}/{idFil}/{Iznos}/{Svrha}")
    Call<String> createTransakcija(@Path("idRPos") int IdR1, @Path("idRPri") int IdR2,@Path("idFil") int IdFil, @Path("Iznos") int iznos, @Path("Svrha") String svrha);
    @POST("transakcija/uplata/{idR}/{idFil}/{Iznos}/{Svrha}")
    Call<String> createUplata(@Path("idR") int IdR,@Path("idFil") int IdFil, @Path("Iznos") int iznos, @Path("Svrha") String svrha);
    @POST("transakcija/isplata/{idR}/{idFil}/{Iznos}/{Svrha}")
    Call<String> createIsplata(@Path("idR") int IdR,@Path("idFil") int IdFil, @Path("Iznos") int iznos, @Path("Svrha") String svrha);
    @GET("transakcija/{idR}")
    Call<String> getAllTransakcija(@Path("idR") int IdR);

    @GET("podaci/kopije")
    Call<String> getBackUp();
    @GET("podaci/razlika")
    Call<String> getDifference();


}
