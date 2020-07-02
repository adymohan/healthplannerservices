package com.ibm.healthplanner.controller;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.healthplanner.model.GetPatientResponse;
import com.ibm.healthplanner.model.Name;
import com.ibm.healthplanner.model.GetDoctorResponse;
import com.ibm.healthplanner.model.Patient;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.ibm.healthplanner.exception.ResourceNotFoundException;
import com.ibm.healthplanner.model.Appointment;
import com.ibm.healthplanner.model.Doctor;
import com.ibm.healthplanner.model.GetAppointmentResponse;
import com.ibm.healthplanner.service.HealthPlannerService;


import io.swagger.annotations.Api;
@RestController("healthplanner")
@CrossOrigin(origins="*")
@RequestMapping(value={"/","/healthplanner"})
@Api(value="onlinestore", description="Operations pertaining to Health Advisor")
public class HealthPlannerController
{

	private static final Logger log = LoggerFactory.getLogger(HealthPlannerController.class);

	@Autowired
	HealthPlannerService healthplannerService;

	/*@GetMapping(value="/",produces = MediaType.APPLICATION_JSON_VALUE)
	public String get(){
		return "Please give url as Spring-Boot-Rest/patient/get";

	}*/

	/*This method is for registering new patient with the application*/
	@PostMapping(value="/create/patient",headers="Accept=application/json")
	public ResponseEntity<?> createPatient(@RequestBody Patient patient){

		log.debug("Creating Patient {}",patient.getName().getFirstName() + " " + patient.getName().getLastName());

		healthplannerService.createPatient(patient);
		return new ResponseEntity<>("Patient " +patient.getName().getFirstName() + " " + patient.getName().getLastName()
				+ " Created Successfully!!", HttpStatus.CREATED);
	}

	/*This method is for fetching details of patients registered with the application*/
	@GetMapping(value="/get/patient", headers="Accept=application/json")
	public GetPatientResponse getAllPatient() {		 
		GetPatientResponse patientList = healthplannerService.getAllPatient();
		return patientList;

	}

	/*This method is to find patient by their unique id*/
	@GetMapping(value="/get/patient/{id}", headers="accept=application/json")
	public Optional<Patient> findPatientById(@PathVariable("id") String id){
		Optional<Patient> patient= healthplannerService.findPatientById(id);
		return patient;
	}

	/*This method is to update patient details by their unique id*/
	@PutMapping(value="/update/patient/{id}", headers="Accept=application/json") 
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient currentUser,@PathVariable("id") String id) {

		log.info("Current PatientId is : {} ",currentUser.getId());
		healthplannerService.updatePatient(currentUser, id);
		return new ResponseEntity<Patient>(HttpStatus.OK); 
	}

	/*This method is to delete patient by their unique id*/
	@DeleteMapping(value="/delete/patient/{id}", headers ="Accept=application/json") 
	public ResponseEntity<Patient> deletePatient(@PathVariable("id") String id){

		log.info(" PatientId to delete is : {} ",id);		  
		healthplannerService.deletePatientById(id);
		return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT); 
	}

	/*This method is to register new doctor with the application*/
	@PostMapping(value="/create/doctor",headers="Accept=application/json")
	public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor){

		log.debug("Creating Doctor {}",doctor.getName().getFirstName() + " " + doctor.getName().getLastName());

		healthplannerService.createDoctor(doctor);
		return new ResponseEntity<>("Doctor " +doctor.getName().getFirstName() + " " + doctor.getName().getLastName()
				+ " Created Successfully!!", HttpStatus.CREATED);
	}

	/*This method is to get the details of the doctor registered with the application*/
	@GetMapping(value="/get/doctor", headers="Accept=application/json")
	public GetDoctorResponse getAllDoctor() {		 
		GetDoctorResponse doctorList = healthplannerService.getAllDoctors();
		return doctorList;

	}

	/*This method is to find doctor by their unique id*/
	@GetMapping(value="/get/doctor/id/{id}", headers="accept=application/json")
	public Optional<Doctor> findDoctorById(@PathVariable("id") String id){
		Optional<Doctor> doctor= healthplannerService.findDoctorById(id);
		return doctor;
	}

	/*This method is to find doctor by their specialization*/
	@GetMapping(value="/get/doctor/specialization/{specialization}", headers="accept=application/json")
	public GetDoctorResponse findDoctorBySpecialization(@PathVariable("specialization") String specialization){
		GetDoctorResponse doctor= healthplannerService.getDoctorBySpecialization(specialization);
		return doctor;
	}

	/*This method is to update doctor details by their unique id*/
	@PutMapping(value="/update/doctor/{id}", headers="Accept=application/json") 
	public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor currentUser,@PathVariable("id") String id) {

		log.info("Current DoctorId is : {} ",currentUser.getId());
		healthplannerService.updateDoctor(currentUser, id);
		return new ResponseEntity<Doctor>(HttpStatus.OK); 
	}

	@DeleteMapping(value="/delete/doctor/{id}", headers ="Accept=application/json") 
	public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") String id){

		log.info(" DoctorId to delete is : {} ",id);		  
		healthplannerService.deleteDoctorById(id);
		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT); 
	}

	/*This method is to delete doctor by their unique id*/
	@PostMapping(value="/create/appointment",headers="Accept=application/json")
	public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment){

		log.debug("Creating Appointment {}",appointment.getDoctorName().getFirstName() + " " + appointment.getDoctorName().getLastName());

		healthplannerService.createAppointment(appointment);
		return new ResponseEntity<>("Appointment for " +appointment.getDoctorName().getFirstName() + " " + appointment.getDoctorName().getLastName()
				+ " Created Successfully!!", HttpStatus.CREATED);
	}

	/*This method is to get list of all appointment registered with the application*/
	@GetMapping(value="/get/appointment", headers="accept=application/json")
	public GetAppointmentResponse getAllAppointment(){
		GetAppointmentResponse appointment= healthplannerService.getAllAppointments();
		if(appointment.getAppointments().isEmpty())
			throw new ResourceNotFoundException("No Appointments found !!!");
		return appointment;
	}

	/*This method is to find appointment by appointment id*/
	@GetMapping(value="/get/appointment/id/{id}", headers="accept=application/json")
	public Optional<Appointment> findAppointmentById(@PathVariable("id") String id){
		Optional<Appointment> appointment= healthplannerService.findAppointmentById(id);
		if(!appointment.isPresent())
			throw new ResourceNotFoundException("No Appointments found !!!");
		return appointment;
	}

	/*This method is to get appointment available by doctor name*/
	@GetMapping(value="/get/available/appointment/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}", headers="accept=application/json")
	public GetAppointmentResponse findAvailableAppointmentByDoctorName(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName){
		System.out.print("\n\n\nIn controller Helli \n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		GetAppointmentResponse appointment= healthplannerService.getAvailableAppointmentsByDoctorName(docName);
		System.out.print("\n\n\nIn controller done\n\n\n");
		if(appointment.getAppointments().isEmpty())
			throw new ResourceNotFoundException("No Appointments found by Dr "+ doctorFirstName + " " + doctorLastName);
		return appointment;
	}
	
	/*This method is to get appointment by doctor name and date*/
	@GetMapping(value="/get/apponitmentSchedule/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}/appointmentDate/{appointmentDate}", headers="accept=application/json")
	public GetAppointmentResponse getAppointmentScheduleByDoctorNameAndAppointmentDate(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName, 
			@DateTimeFormat(pattern="dd-MM-yyyy") @PathVariable("appointmentDate") String appointmentDate){
		System.out.print("\n\n\nIn controller\n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		LocalDate appDate = LocalDate.now();
		try {
			if(null != appointmentDate) {
				List<String> list = Lists.newArrayList(Splitter.on("-").split(appointmentDate));
				if(list.size() >= 3) {
					appDate = LocalDate.of(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(0)));
				}
			}
		} catch(Exception e){
			System.out.print("\n\n\nIn controller\n\n\n");
		} 
		GetAppointmentResponse appointment= healthplannerService.getAppointmentByDoctorNameAndAppointmentDate(docName, appDate);
		return appointment;
	}

	/*This method is to get appointment by doctor name and date and slot*/
	@GetMapping(value="/get/apponitmentSchedule/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}/appointmentDate/{appointmentDate}/slot/{slot}", headers="accept=application/json")
	public GetAppointmentResponse getAppointmentScheduleByDoctorNameAndAppointmentDateAndSlot(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName,
			@PathVariable("appointmentDate") String appointmentDate, 
			@PathVariable("slot") String slot){
		System.out.print("\n\n\nIn controller\n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		LocalDate appDate = LocalDate.now();
		try {
			if(null != appointmentDate) {
				List<String> list = Lists.newArrayList(Splitter.on("-").split(appointmentDate));
				if(list.size() >= 3) {
					appDate = LocalDate.of(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(0)));
				}
			}
		} catch(Exception e){
			System.out.print("\n\n\nIn controller exception\n\n\n");
		} 
		GetAppointmentResponse appointment= healthplannerService.getAppointmentByDoctorNameAndAppointmentDateAndSlot(docName, appDate, slot);
		return appointment;
	}

	/*This method is to delete appointment by appointment id*/
	@DeleteMapping(value="/delete/appointment/{id}", headers ="Accept=application/json") 
	public ResponseEntity<Doctor> deleteAppointmentById(@PathVariable("id") String id){

		log.info(" Appointment to delete is : {} ",id);		  
		healthplannerService.deleteAppointmentById(id);
		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT); 
	}

	/*This method is to delete appointment by doctor name*/
	@DeleteMapping(value="/delete/appointment/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}", headers ="Accept=application/json") 
	public ResponseEntity<Doctor> deleteAppointmentByDoctorName(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName){
		System.out.print("\n\n\nIn controller\n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		GetAppointmentResponse appointment= healthplannerService.getAppointmentByDoctorName(docName);
		List <Appointment> apps = appointment.getAppointments();
		if(!apps.isEmpty()) {
			Iterator<Appointment> itr = apps.iterator();
			while (itr.hasNext()) {
				Appointment app = (Appointment)itr.next();
				healthplannerService.deleteAppointmentById(app.getId());
				log.info(" AppointmentId to delete is : {} ",app.getId());		  
			}
		} else {
			throw new ResourceNotFoundException("No Appointments found by Dr "+ doctorFirstName + " " + doctorLastName );
		}
		System.out.print("\n\n\nIn controller done\n\n\n");

		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT); 
	}

	/*This method is to delete appointment by doctor name and appointment date*/
	@DeleteMapping(value="/delete/appointment/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}/appointmentDate/{appointmentDate}", headers ="Accept=application/json") 
	public ResponseEntity<Doctor> deleteAppointmentByDoctorNameAndAppointmentDate(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName,
			@PathVariable("appointmentDate") String appointmentDate){
		System.out.print("\n\n\nIn controller\n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		LocalDate appDate = LocalDate.now();
		try {
			if(null != appointmentDate) {
				List<String> list = Lists.newArrayList(Splitter.on("-").split(appointmentDate));
				if(list.size() >= 3) {
					appDate = LocalDate.of(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(0)));
				}
			}
		} catch(Exception e){
			System.out.print("\n\n\nIn controller\n\n\n");
		}
		GetAppointmentResponse appointment= healthplannerService.getAppointmentByDoctorNameAndAppointmentDate(docName, appDate);
		List <Appointment> apps = appointment.getAppointments();
		Iterator<Appointment> itr = apps.iterator();
		while (itr.hasNext()) {
			Appointment app = (Appointment)itr.next();
			healthplannerService.deleteAppointmentById(app.getId());
			log.info(" AppointmentId to delete is : {} ",app.getId());		  
		}
		System.out.print("\n\n\nIn controller done\n\n\n");

		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT); 
	}
	
	/*This method is to delete appointment by doctor name, date and slot*/
	@DeleteMapping(value="/delete/appointment/doctorFirstName/{doctorFirstName}/doctorLastName/{doctorLastName}/appointmentDate/{appointmentDate}/slot/{slot}", headers ="Accept=application/json") 
	public ResponseEntity<Doctor> deleteAppointmentByDoctorNameAndAppointmentDateAndSlot(@PathVariable("doctorFirstName") String doctorFirstName,
			@PathVariable("doctorLastName") String doctorLastName,
			@PathVariable("appointmentDate") String appointmentDate,
			@PathVariable("slot") String slot){
		System.out.print("\n\n\nIn controller\n\n\n");
		Name docName = new Name(doctorFirstName, doctorLastName); 
		LocalDate appDate = LocalDate.now();
		try {
			if(null != appointmentDate) {
				List<String> list = Lists.newArrayList(Splitter.on("-").split(appointmentDate));
				if(list.size() >= 3) {
					appDate = LocalDate.of(Integer.parseInt(list.get(2)), Integer.parseInt(list.get(1)), Integer.parseInt(list.get(0)));
				}
			}
		} catch(Exception e){
			System.out.print("\n\n\nIn controller\n\n\n");
		}
		GetAppointmentResponse appointment= healthplannerService.getAppointmentByDoctorNameAndAppointmentDateAndSlot(docName, appDate, slot);
		List <Appointment> apps = appointment.getAppointments();
		Iterator<Appointment> itr = apps.iterator();
		while (itr.hasNext()) {
			Appointment app = (Appointment)itr.next();
			healthplannerService.deleteAppointmentById(app.getId());
			log.info(" AppointmentId to delete is : {} ",app.getId());		  
		}
		System.out.print("\n\n\nIn controller done\n\n\n");
		
		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT); 
	}

}
