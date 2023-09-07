package com.ex.encrption.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HyperMessage {

    private String id; 
    private String message;
    private String date; 
    private String title;
	private String isLock;
}
	

