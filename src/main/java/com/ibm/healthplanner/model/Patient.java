package com.ibm.healthplanner.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@Document(collection = "patients")
public class Patient  extends BaseEntity{
	
	@JsonProperty("patientName")
	private Name name;
	
	@JsonProperty("postalAddress")
	private PostalAddress address;
	
	private String gender;
	
	private Date dateOfBirth;
	
	private String mailId;
	private String phone;
	
	@JsonInclude(value=Include.NON_NULL)
	@JsonProperty("maritalStatus")
	private String maritalStatus = null;
	
	private List<String> medHistory;;
	
	private double height;
	private double weight;
	private double bmi;
	
	@JsonProperty("diet")
	private String diet;
	
	@JsonProperty("activity")
	private String activity; 
	
	@JsonProperty("tobacoUse")
	private String tobacoUse;
	
	@JsonProperty("alchoholUse")
	private String alchoholUse;
	
	@JsonProperty("caffineUse")
	private String caffineUse;
	
	@JsonProperty("allergies")
	private String allergies;
	
	@JsonProperty("allergicFrom")
	private List<String> allergicFrom;
	
	public Patient(){
		
	}
	public Patient(Name name, PostalAddress address, String gender,Date dateOfBirth, String mailId,
			String phone, String maritalStatus, List<String> medHistory, double height, double weight, 
			double bmi, String diet, String activity, String tobacoUse, String alchoholUse, String caffineUse, 
			String allergies, List<String> allergicfrom ){
		
		super();
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.mailId = mailId;
		this.phone = phone;
		this.maritalStatus = maritalStatus;
		this.medHistory = medHistory;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.diet = diet;
		this.activity = activity;
		this.tobacoUse = tobacoUse;
		this.alchoholUse = alchoholUse;
		this.caffineUse = caffineUse;
		this.allergies = allergies;
		this.allergicFrom = allergicfrom;
	}
	
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
		//System.out.println("PatientName : "+name.toString() +"FirstName: "+name.getFirstName());
	}
	public PostalAddress getAddress() {
		return address;
	}
	public void setAddress(PostalAddress address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public List<String> getMedHistory() {
		return medHistory;
	}
	public void setMedHistory(List<String> medHistory) {
		this.medHistory = medHistory;
	}
	
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double weight, double height) {
		this.bmi = this.getWeight()/(this.getHeight() * this.getHeight());
	}
		
	public String getDiet() {
		return diet;
	}
	public void setDiet(String diet) {
		this.diet = diet;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getTobacoUse() {
		return tobacoUse;
	}
	public void setTobacoUse(String tobacoUse) {
		this.tobacoUse = tobacoUse;
	}
	public String getAlchoholUse() {
		return alchoholUse;
	}
	public void setAlchoholUse(String alchoholUse) {
		this.alchoholUse = alchoholUse;
	}
	public String getCaffineUse() {
		return caffineUse;
	}
	public void setCaffineUse(String caffineUse) {
		this.caffineUse = caffineUse;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public List<String> getAllergicFrom() {
		return allergicFrom;
	}
	public void setAllergicFrom(List<String> allergicfrom) {
		this.allergicFrom = allergicfrom;
	}
	/*private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }*/
	
	/*@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Patient {\n");
	    
	    sb.append("    name: ").append(toIndentedString(name)).append("\n");
	    sb.append("    address: ").append(toIndentedString(address)).append("\n");
	    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
	    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
	    sb.append("    mailId: ").append(toIndentedString(mailId)).append("\n");
	    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
	    sb.append("    maritalStatus: ").append(toIndentedString(maritalStatus)).append("\n");
	    sb.append("    medHistory: ").append(toIndentedString(medHistory)).append("\n");
	    
	    sb.append("}");
	    return sb.toString();
	  }*/
	
}
