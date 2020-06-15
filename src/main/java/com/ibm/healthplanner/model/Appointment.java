package com.ibm.healthplanner.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@Document(collection = "appointments")
public class Appointment  extends BaseEntity{
	
	public enum Slots {Slot1, Slot2, Slot3};
	
	@JsonProperty("patientName")
	private Name patientName;
	
	@JsonProperty("doctorName")
	private Name doctorName;
	private LocalDate appointmentDate;
	private String slot;
	
	public Appointment (String slot, LocalDate appointmentDate, Name patientName, Name doctorName) {
		this.slot = slot;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.appointmentDate = appointmentDate;
	}
	
	public Appointment() {
		super();
	}

	public Name getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(Name doctorName) {
		this.doctorName = doctorName;
	}

	public Name getPatientName() {
		return patientName;
	}

	public void setPatientName(Name patientName) {
		this.patientName = patientName;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}	
	
}
