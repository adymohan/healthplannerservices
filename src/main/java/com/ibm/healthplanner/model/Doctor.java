package com.ibm.healthplanner.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@Document(collection = "doctors")
public class Doctor  extends BaseEntity{
	
	@JsonProperty("doctorName")
	private Name name;
	
	private String specialization;	
	private String roomNo;
	
	private BigDecimal price;
	private String slot1;
	private String slot2;
	private String slot3;
	
	public Doctor(){
		
	}
	public Doctor(Name name, String specialization, String slot1, String slot2, String slot3, BigDecimal price, String roomNo){
		
		super();
		this.name = name;
		this.price = price!=null? price:new BigDecimal("800");
		this.specialization = specialization;
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.slot3 = slot3;
		this.roomNo = roomNo;
	}
	
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getSlot1() {
		return slot1;
	}
	public void setSlot1(String slot1) {
		this.slot1 = slot1;
	}
	public String getSlot2() {
		return slot2;
	}
	public void setSlot2(String slot2) {
		this.slot2 = slot2;
	}
	public String getSlot3() {
		return slot3;
	}
	public void setSlot3(String slot3) {
		this.slot3 = slot3;
	}

	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	
}