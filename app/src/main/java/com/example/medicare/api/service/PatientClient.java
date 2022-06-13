package com.example.medicare.api.service;

import com.example.medicare.api.model.Patient;
import com.example.medicare.model.Doctor;
import com.example.medicare.model.HeureDePrise;
import com.example.medicare.model.Journal;
import com.example.medicare.model.Medication;
import com.example.medicare.model.Modele;
import com.example.medicare.model.Prescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface PatientClient {

    @GET("api/patients/find/all")
    Call<List<Patient>> patientsForUser();

    @GET("api/doctors/find/all")
    Call<List<Doctor>> findAllDoctors();

    @POST("api/patient/create")
    Call<Patient> createPatient(@Body Patient patient);

    @GET("api/patients/find/username/{id}")
    Call<Patient> findPatientByid(@Path("id") Integer id);

    @POST("api/doctor/create")
    Call<Doctor> createDoctor(@Body Doctor doctor);

    @GET("api/prescription/find/doctor-patient/{docusername}/{patusername}")
    Call<List<Prescription>> findPrescriptionsByDoctorToPatient(@Path("docusername") String docusername,
                                                                @Path("patusername") String patusername);

    @GET("api/prescription/find/medications/{prescription}")
    Call<List<Medication>> medicationByPrescriptions(@Path("prescription") Integer prescription);

    @GET("api/modele/find/medications/{modeleId}")
    Call<List<Medication>> medicationByModele(@Path("modeleId") Integer modeleId);

    @GET("/api/models/find/all")
    Call<List<Modele>> findAllModels();

    @POST("/api/doctor/{docUsername}/patient/{patUsername}/{presID}/medication")
    Call<Medication>  createMedication(
            @Path("docUsername") String docUsername,
            @Path("patUsername") String patUsername,
            @Path("presID") String presID,
             @Body Medication medication);



    @POST("/api/doctor/{docUsername}/patient/{patUsername}/prescription")
    Call<Prescription>  createPrescription(@Path("docUsername") String docter, @Path("patUsername") String patUserName,@Body Prescription prescription);

    @GET("api/journals/{patient}/{date}")
    Call<List<Journal>> findJournalByPatientAndDate(@Path("patient") String patient, @Path("date") String date);

    @POST("api/patient/medication/heure/{medId}")
    Call<HeureDePrise> createHeureDePrise(@Path("medId") String medId,@Body HeureDePrise heureDePrise);

    @POST("/api/prescription/create/modele/{prescriptionId}")
     Call<Modele> createModele(@Path("prescriptionId") String prescriptionId,
                               @Body Modele modele);


}
