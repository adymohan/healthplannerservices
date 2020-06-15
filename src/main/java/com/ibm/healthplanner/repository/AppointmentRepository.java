package com.ibm.healthplanner.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.healthplanner.model.Appointment;
import com.ibm.healthplanner.model.Name;


@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String>{

	public List<Appointment> findAppointmentByDoctorName(Name name);
	
	public List<Appointment> findAppointmentByDoctorNameAndAppointmentDate(Name name, LocalDate appointmentDate);
	
	public List<Appointment> findAppointmentByDoctorNameAndAppointmentDateAndSlot(Name name, LocalDate appointmentDate, String slot);
	
	public Optional<Appointment> findAppointmentByPatientName(Name name);
	
}
