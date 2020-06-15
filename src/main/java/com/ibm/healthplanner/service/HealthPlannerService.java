package com.ibm.healthplanner.service;

import java.time.LocalDate;
import java.util.Optional;

import com.ibm.healthplanner.model.GetPatientResponse;
import com.ibm.healthplanner.model.Name;
import com.ibm.healthplanner.model.GetDoctorResponse;
import com.ibm.healthplanner.model.Patient;
import com.ibm.healthplanner.model.Appointment;
import com.ibm.healthplanner.model.Doctor;
import com.ibm.healthplanner.model.GetAppointmentResponse;

public interface HealthPlannerService {
	
	public void createPatient(Patient patient);
	
	public GetPatientResponse getAllPatient();
	
	public Optional<Patient> findPatientById(String id);
	
	public void updatePatient(Patient patient, String id);
	
	public void deletePatientById(String id);
	
	public void createDoctor(Doctor user);
	
	public GetDoctorResponse getAllDoctors();
	
	public Optional<Doctor> findDoctorById(String id);
	
	public void updateDoctor(Doctor doctor, String id);
	
	public void deleteDoctorById(String id);
	
	public GetDoctorResponse getDoctorBySpecialization(String specilization);
	
	public void createAppointment(Appointment app);
	
	public GetAppointmentResponse getAllAppointments();
	
	public Optional<Appointment> findAppointmentById(String id);
	
	public void deleteAppointmentById(String id);
	
	public GetAppointmentResponse getAppointmentByDoctorName(Name name);
	
	public GetAppointmentResponse getAppointmentByDoctorNameAndAppointmentDate(Name name, LocalDate appointmentDate);
	
	public GetAppointmentResponse getAppointmentByDoctorNameAndAppointmentDateAndSlot(Name name, LocalDate appointmentDate, String slot);
	
	public GetAppointmentResponse getAvailableAppointmentsByDoctorName(Name name);
	
}
