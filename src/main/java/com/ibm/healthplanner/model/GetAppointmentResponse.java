package com.ibm.healthplanner.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAppointmentResponse {
	  
	 @JsonProperty("appointments")
	  private List<Appointment> appointments = new ArrayList<Appointment>();

	  public GetAppointmentResponse appointments(List<Appointment> appointments) {
	    this.appointments = appointments;
	    return this;
	  }

	  public GetAppointmentResponse addAppointment(Appointment appointments) {
	    this.appointments.add(appointments);
	    return this;
	  }

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	  
	  

}
