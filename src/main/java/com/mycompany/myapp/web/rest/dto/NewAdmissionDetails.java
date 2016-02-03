package com.mycompany.myapp.web.rest.dto;

import com.mycompany.myapp.domain.AdmissionDetails;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.PrevSchoolInfo;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.User;

public class NewAdmissionDetails {
		
	  private Parent parent;
	  
	  private Student student;
	  
	  private PrevSchoolInfo prevSchoolInfo;
	  
	  private AdmissionDetails admissionDetails;
	  
	  public NewAdmissionDetails(NewAdmissionDetails newAdmissionDetails) {
	        
	        this.parent = newAdmissionDetails.getParent();
	        this.student = newAdmissionDetails.getStudent();
	        this.prevSchoolInfo = newAdmissionDetails.getPrevSchoolInfo();
	        this.admissionDetails = newAdmissionDetails.getAdmissionDetails();
	    }
	  
	
	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public PrevSchoolInfo getPrevSchoolInfo() {
		return prevSchoolInfo;
	}

	public void setPrevSchoolInfo(PrevSchoolInfo prevSchoolInfo) {
		this.prevSchoolInfo = prevSchoolInfo;
	}

	public AdmissionDetails getAdmissionDetails() {
		return admissionDetails;
	}

	public void setAdmissionDetails(AdmissionDetails admissionDetails) {
		this.admissionDetails = admissionDetails;
	}

	
	 
	
}
