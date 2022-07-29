/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.klijentskaaplikacija;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Iva
 */
public class Klijent {
    private static final String URI = "http://localhost:8080/CentralniServer/resources/";
    public static void main(String[] args) {
	Retrofit retrofit = new Retrofit.Builder()
					    .baseUrl(URI)
					    .addConverterFactory(ScalarsConverterFactory.create())
					    .build();
        Usluge usluge = retrofit.create(Usluge.class);
	int zahtev;
	Scanner ulaz = new Scanner(System.in);
	Call<String> call;
	Response<String> resp;
	String res;
	String naziv, adresa, svrha;
	int br, br2, br3, br4;
	try{
	    do{
	    System.out.println("Uneti novi zahtev: ");
	    zahtev = ulaz.nextInt();
	    ulaz.nextLine();
	    switch (zahtev) {
		    case 1:
			
			System.out.println("Uneti naziv mesta: ");
			naziv = ulaz.nextLine();
			System.out.println("Uneti postanski broj: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.createMesto(naziv, br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 2:
			System.out.println("Uneti naziv filijale: ");
			naziv = ulaz.nextLine();
			System.out.println("Uneti adresu filijale: ");
			adresa = ulaz.nextLine();
			System.out.println("Uneti mesto u kome je filijala: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.createFilijala(naziv, adresa, br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 3:
			System.out.println("Uneti ime komitenta: ");
			naziv = ulaz.nextLine();
			System.out.println("Uneti adresu komitenta: ");
			adresa = ulaz.nextLine();
			System.out.println("Uneti sediste: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.createKomitent(naziv, adresa, br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 4:
			System.out.println("Uneti komitenta cije se sediste menja: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Novo sediste: ");
			br2 = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.changeSediste(br, br2);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 5:
			System.out.println("Komitent koji otvara racun: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Mesto otvaranja: ");
			br2 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Dozvoljeni minus: ");
			br3 = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.createRacun(br, br2, br3);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 6:
			System.out.println("Racun koji se zatvara: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.shutDownRacun(br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 7: 
			System.out.println("Racun sa kog se prenosi: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Racun na koji se prenosi: ");
			br2 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("U filijali: ");
			br3 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Iznos: ");
			br4 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Svrha: ");
			svrha = ulaz.nextLine();
			call = usluge.createTransakcija(br, br2, br3, br4, svrha);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 8:
			System.out.println("Racun na koji se uplacuje: ");
			br2 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("U filijali: ");
			br3 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Iznos: ");
			br4 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Svrha: ");
			svrha = ulaz.nextLine();
			call = usluge.createUplata(br2, br3, br4, svrha);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 9:
			System.out.println("Racun sa kog se isplacuje: ");
			br2 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("U filijali: ");
			br3 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Iznos: ");
			br4 = ulaz.nextInt();
			ulaz.nextLine();
			System.out.println("Svrha: ");
			svrha = ulaz.nextLine();
			call = usluge.createIsplata(br2, br3, br4, svrha);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 10:
			call = usluge.getAllMesto();
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 11:
			call = usluge.getAllFilijala();
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 12:
			call = usluge.getAllKomitent();
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 13:
			System.out.println("Komitent cije racune zelite: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.getAllRacun(br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 14:
			System.out.println("Racun cije transakcije zelite: ");
			br = ulaz.nextInt();
			ulaz.nextLine();
			call = usluge.getAllTransakcija(br);
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 15:
			call = usluge.getBackUp();
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		    case 16:
			call = usluge.getDifference();
			resp = call.execute();
			if(resp.isSuccessful())
			    res = resp.body();
			else
			    res = resp.errorBody().string();
			System.out.println(res);
			break;
		}
	   
	    } while(zahtev != 0);
	} catch (IOException ex) {
	    Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
