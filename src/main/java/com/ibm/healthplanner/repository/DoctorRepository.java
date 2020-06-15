package com.ibm.healthplanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.healthplanner.model.Doctor;
import com.ibm.healthplanner.model.Name;


@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String>{

	public Optional<Doctor> findDoctorByName(Name name);
	
	public List<Doctor> findDoctorBySpecialization(String specialization);

}
