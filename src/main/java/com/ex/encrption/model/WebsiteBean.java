package com.ex.encrption.model;

import java.util.List;

import org.springframework.stereotype.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteBean {
	
	String id;
	
	String name;
	
	Boolean isUsed;
	
	List<String> usedBy;
	
	Integer type;
	
	String parentWebId;

}
