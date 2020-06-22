package com.ibm.healthplanner.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.healthplanner.exception.ResourceExistsException;
import com.ibm.healthplanner.exception.ResourceNotFoundException;
import com.ibm.healthplanner.model.Appointment;
import com.ibm.healthplanner.model.Doctor;
import com.ibm.healthplanner.model.GetAppointmentResponse;
import com.ibm.healthplanner.model.GetDoctorResponse;
import com.ibm.healthplanner.model.GetPatientResponse;
import com.ibm.healthplanner.model.Name;
import com.ibm.healthplanner.model.Patient;
import com.ibm.healthplanner.repository.PatientRepository;
import com.ibm.healthplanner.repository.AppointmentRepository;
import com.ibm.healthplanner.repository.DoctorRepository;

@Service
@Transactional
public class HealthPlannerServiceImpl implements HealthPlannerService {
	
	private static final Logger log = LoggerFactory.getLogger(HealthPlannerServiceImpl.class);
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;

	/*This method is for registering new patient with the application*/
	public void createPatient(Patient user) {
	
		Optional<Patient> patient = patientRepository.findPatientByName(user.getName());
		if(!patient.isPresent()) {

		    String id = "P" + "-" + System.currentTimeMillis();
		    user.setId(id);
			user.setBmi(user.getWeight(), user.getHeight());
			log.info("BMI for the patient is {} ", user.getBmi());
			
			/*if((user.getAllergies()).equalsIgnoreCase("yes")) {
				user.setAllergicFrom(user.getAllergicFrom());
			}*/
					
			patientRepository.save(user);
		} else {
			log.info ("Patient Already exists!!");
			throw new ResourceExistsException("User "+ user.getName().getFirstName() + " " +user.getName().getLastName() 
							+ " Already exists!!");
		}
	}
   
	/*This method is for fetching details of patients registered with the application*/
	public GetPatientResponse getAllPatient() {
		
		GetPatientResponse patietData = new GetPatientResponse();
		patietData.setPatients((List<Patient>) patientRepository.findAll());
		return patietData;
	}

	/*This method is to find patient by their unique id*/
	 public Optional<Patient> findPatientById(String id) { 
		  return patientRepository.findById(id); 
	 }
	 
	 /*This method is to update patient details by their unique id*/
	public void updatePatient(Patient newPatient, String id) {
		
		Optional<Patient> patient = findPatientById(id); 
		Patient ptn = new Patient();
		  if(patient.isPresent()) {  
			  
			  ptn = patient.get();
			  ptn.setId(id);
			  ptn.setName(newPatient.getName());
			  ptn.setAddress(newPatient.getAddress());
			  ptn.setGender(newPatient.getGender());
			  ptn.setDateOfBirth(newPatient.getDateOfBirth());
			  ptn.setMailId(newPatient.getMailId());
			  ptn.setPhone(newPatient.getPhone());
			  ptn.setMaritalStatus(newPatient.getMaritalStatus());
			  ptn.setMedHistory(newPatient.getMedHistory());
			  ptn.setWeight(newPatient.getWeight());
			  ptn.setHeight(newPatient.getHeight());
			  ptn.setBmi(newPatient.getWeight(), newPatient.getHeight());
			  ptn.setDiet(newPatient.getDiet());
			  ptn.setActivity(newPatient.getActivity());
			  ptn.setTobacoUse(newPatient.getTobacoUse());
			  ptn.setAlchoholUse(newPatient.getAlchoholUse());
			  ptn.setCaffineUse(newPatient.getCaffineUse());
			  ptn.setAllergies(newPatient.getAllergies());
			  ptn.setAllergicFrom(newPatient.getAllergicFrom());
		  } else {
			  throw new ResourceNotFoundException("Patient ID "+ id + " does not exits");
		  }
		  patientRepository.save(ptn);	
	}
	
	/*This method is to delete patient by their unique id*/
	public void deletePatientById(String id) { 
		  
		  Optional<Patient> user = findPatientById(id);
		  if (user.isPresent()) { 
			  patientRepository.deleteById(id);
		  } else {
			  throw new ResourceNotFoundException("Patient ID "+ id + " does not exits!!");
		  }
	}
	
	/*This method is to register new doctor with the application*/ 
	public void createDoctor(Doctor user) {
		Optional<Doctor> doctor = doctorRepository.findDoctorByName(user.getName());
		if(!doctor.isPresent()) {

		    String id = "D" + "-" + System.currentTimeMillis();
		    user.setId(id);
			
			doctorRepository.save(user);
		} else {
			log.info ("User Already exists!!");
			throw new ResourceExistsException("Doctor "+ user.getName().getFirstName() + " " +user.getName().getLastName() 
							+ " Already exists!!");
		}
	}
	
	
	/*This method is to get the details of the doctor registered with the application*/
	public GetDoctorResponse getAllDoctors() {
	  	GetDoctorResponse doctorData = new GetDoctorResponse();
	  	doctorData.setDoctors((List<Doctor>) doctorRepository.findAll());
		return doctorData;
	}
	
	/*This method is to find doctor by their unique id*/
	public Optional<Doctor> findDoctorById(String id){ 
		return doctorRepository.findById(id); 
	}
	
	/*This method is to update doctor details by their unique id*/
	public void updateDoctor(Doctor newDoctor, String id) {
		
		Optional<Doctor> doctor = findDoctorById(id); 
		Doctor dct = new Doctor();
		if(doctor.isPresent()) {  
		  
			  dct = doctor.get();
			  dct.setId(newDoctor.getId());
			  dct.setName(newDoctor.getName());
			  dct.setSlot1(newDoctor.getSlot1());
			  dct.setSlot2(newDoctor.getSlot2());
			  dct.setSlot3(newDoctor.getSlot3());
			  dct.setSpecialization(newDoctor.getSpecialization());
		} else {
			  throw new ResourceNotFoundException("Doctor ID "+ newDoctor.getId() + " does not exits");
		}
		doctorRepository.save(dct);	
	}
  
	/*This method is to delete doctor by their unique id*/
	public void deleteDoctorById(String id) { 
		  
		Optional<Doctor> user = findDoctorById(id);
		if (user.isPresent()) { 
			Doctor doc = user.get();
			GetAppointmentResponse appointment= getAppointmentByDoctorName(doc.getName());
			List <Appointment> apps = appointment.getAppointments();
			Iterator<Appointment> itr = apps.iterator();
			while (itr.hasNext()) {
				Appointment app = (Appointment)itr.next();
				deleteAppointmentById(app.getId());
				log.info(" Appointment Id to delete is : {} ",app.getId());		  
			}
			  doctorRepository.deleteById(id);
		} else {
			  throw new ResourceNotFoundException("Doctor ID "+ id + " does not exits!!");
		}
	}

	/*This method is to find doctor by their specialization*/
	public GetDoctorResponse getDoctorBySpecialization(String specilization) {
		GetDoctorResponse doctorData = new GetDoctorResponse();
		List<Doctor> doctors = (List<Doctor>) doctorRepository.findDoctorBySpecialization(specilization);
		doctorData.setDoctors(doctors);
	  	return doctorData;
	}

	/*This method is to create appointment with doctor by a patient*/
	public void createAppointment(Appointment app) {
		
		List<Appointment> appointment = appointmentRepository.findAppointmentByDoctorNameAndAppointmentDateAndSlot(app.getDoctorName(), app.getAppointmentDate(), app.getSlot());
		if(appointment.isEmpty() && doctorRepository.findDoctorByName(app.getDoctorName()).isPresent() && patientRepository.findPatientByName(app.getPatientName()).isPresent()
				&& (app.getSlot().equals("Slot1") || app.getSlot().equals("Slot2") || app.getSlot().equals("Slot3"))) {
		    String id = "A" + "-" + System.currentTimeMillis();
		    app.setId(id);
			appointmentRepository.save(app);
		} else if (!doctorRepository.findDoctorByName(app.getDoctorName()).isPresent()){
			log.info ("Doctor is invalid!!");
			throw new ResourceExistsException("Doctor "+ app.getDoctorName().getFirstName() + " " +app.getDoctorName().getLastName() 
							+ " is invalid!!");
		} else if (!patientRepository.findPatientByName(app.getPatientName()).isPresent()){
			log.info ("Patient is invalid!!");
			throw new ResourceExistsException("Patient "+ app.getPatientName().getFirstName() + " " +app.getPatientName().getLastName() 
							+ " is invalid!!");
		} else if (!app.getSlot().equals("Slot1") && !app.getSlot().equals("Slot2") && !app.getSlot().equals("Slot3")){
			log.info ("Invalid Slot !! Values allowed are Slot1 or Slot2 or Slot3");
			throw new ResourceExistsException("Slot is invalid!! Values allowed are Slot1 or Slot2 or Slot3");
		} else {
			log.info ("Appointment Already exists!!");
			throw new ResourceExistsException("Doctor "+ app.getDoctorName().getFirstName() + " " +app.getDoctorName().getLastName() 
							+ " is not available at the requested time!!");
		}
	}

	/*This method is to find appointment by appointment id*/
	public Optional<Appointment> findAppointmentById(String id) {
		
		return appointmentRepository.findById(id); 
	}

	/*This method is to delete appointment by appointment id*/
	public void deleteAppointmentById(String id) {
		Optional<Appointment> app = findAppointmentById(id);
		  if (app.isPresent()) { 
			  appointmentRepository.deleteById(id);
		  } else {
			  throw new ResourceNotFoundException("Appointment ID "+ id + " does not exits!!");
		  }
	}

	/*This method is to get list of all appointment registered with the application*/
	public GetAppointmentResponse getAllAppointments() {
		GetAppointmentResponse appointmentData = new GetAppointmentResponse();
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
		appointmentData.setAppointments(appointments);
	  	return appointmentData;
	}
	
	/*This method is to get list of all appointment by doctor name*/
	public GetAppointmentResponse getAppointmentByDoctorName(Name name) {
		GetAppointmentResponse appointmentData = new GetAppointmentResponse();
		System.out.print("Name = "+name);
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAppointmentByDoctorName(name);
		if(!appointments.isEmpty()) {
			appointmentData.setAppointments(appointments);
		} else {
			throw new ResourceNotFoundException("No Appointments found by Dr "+ name.getFirstName() + " " + name.getLastName());
		}
	  	return appointmentData;
	}

	/*This method is to get list of all appointment by doctor name and appointment date*/
	public GetAppointmentResponse getAppointmentByDoctorNameAndAppointmentDate(Name name, LocalDate appointmentDate) {
		GetAppointmentResponse appointmentData = new GetAppointmentResponse();
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAppointmentByDoctorNameAndAppointmentDate(name, appointmentDate);
		if(!appointments.isEmpty()) {
			appointmentData.setAppointments(appointments);
		} else {
			throw new ResourceNotFoundException("No Appointments found by Dr "+ name.getFirstName() + " " + name.getLastName() + " on "+ appointmentDate.toString() );
		}
	  	return appointmentData;
	}
	 
	/*This method is to get list of all appointment by doctor name and appointment date and slot allocated*/
	public GetAppointmentResponse getAppointmentByDoctorNameAndAppointmentDateAndSlot(Name name, LocalDate appointmentDate, String slot) {
		GetAppointmentResponse appointmentData = new GetAppointmentResponse();
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAppointmentByDoctorNameAndAppointmentDateAndSlot(name, appointmentDate, slot);
		if(!appointments.isEmpty()) {
			appointmentData.setAppointments(appointments);
		} else {
			throw new ResourceNotFoundException("No Appointments found by Dr "+ name.getFirstName() + " " + name.getLastName() + " on "+ appointmentDate.toString() +" for "+ slot);
		}
	  	return appointmentData;
	}
	
	/*This method is to get list of all available appointment by doctor name*/
	public GetAppointmentResponse getAvailableAppointmentsByDoctorName(Name name) {
		GetAppointmentResponse appointmentData = new GetAppointmentResponse();
		//check for valid doctor
		if(doctorRepository.findDoctorByName(name).isPresent()) {
			List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAppointmentByDoctorName(name);
			List<Appointment> allPossibleAppointments = allPossibleAppointmentsByDoctorName(name);
			if(appointments.isEmpty()) {
				appointmentData.setAppointments(allPossibleAppointments);
			} else if(!appointments.isEmpty() && appointments.size() < 6) {
				Iterator<Appointment> itrBooked = appointments.iterator();
				while (itrBooked.hasNext()) {
					Appointment bookedAppointment = (Appointment) itrBooked.next();
					Iterator<Appointment> itrAllPossible = allPossibleAppointments.iterator();
					int ctr = 0;
					int flag = 0 ;
					while (itrAllPossible.hasNext() && flag == 0) {
						Appointment possibleAppointment = (Appointment) itrAllPossible.next();
						if (possibleAppointment.getAppointmentDate().getMonthValue() == bookedAppointment.getAppointmentDate().getMonthValue() 
								&& possibleAppointment.getAppointmentDate().getDayOfMonth() == bookedAppointment.getAppointmentDate().getDayOfMonth()
								&& possibleAppointment.getAppointmentDate().getYear() == bookedAppointment.getAppointmentDate().getYear() 
								&& possibleAppointment.getSlot().equalsIgnoreCase(bookedAppointment.getSlot())) {
							allPossibleAppointments.remove(ctr);
							flag = 1;
							System.out.println("\\n\\n\\nRemoving existing appointment ... now count is ..." + allPossibleAppointments.size() + "  "+ ctr );
						} else {			
							ctr = ctr + 1 ;
							System.out.println("\\n\\n\\n... now count is ...  "+ ctr );
						}
					}
				}
				appointmentData.setAppointments(allPossibleAppointments);
			} else {
				throw new ResourceNotFoundException("\\n\\n\\nNo Available Slots for today and tomorrow !!");
			}
		
		}
		else {
			log.info ("Doctor is invalid!!");
			throw new ResourceExistsException("Doctor "+ name.getFirstName() + " " +name.getLastName() 
							+ " is invalid!!");
		}
	  	return appointmentData;
	}
	
	/*This method is to check doctor availability by doctor name*/
	public List<Appointment> allPossibleAppointmentsByDoctorName(Name name) {
		System.out.println("\n\n\nChecking all possible Appointments for today and tomorrow for a Doctor");
		List<Appointment> allPossibleAppointments = new ArrayList<Appointment>();
		Appointment app1 = new Appointment("Slot1", LocalDate.now().plusDays(1), new Name(), name);
		Appointment app2 = new Appointment("Slot2", LocalDate.now().plusDays(1), new Name(), name);
		Appointment app3 = new Appointment("Slot3", LocalDate.now().plusDays(1), new Name(), name);
		Appointment app5 = new Appointment("Slot1", LocalDate.now().plusDays(2), new Name(), name);
		Appointment app6 = new Appointment("Slot2", LocalDate.now().plusDays(2), new Name(), name);
		Appointment app7 = new Appointment("Slot3", LocalDate.now().plusDays(2), new Name(), name);
		
		allPossibleAppointments.add(app1);
		allPossibleAppointments.add(app2);
		allPossibleAppointments.add(app3);
		allPossibleAppointments.add(app5);
		allPossibleAppointments.add(app6);
		allPossibleAppointments.add(app7);
		System.out.println("\\n\\n\\nCollected  all possible Appointments for today and tomorrow for a Doctor");
		return allPossibleAppointments;
	}
}
