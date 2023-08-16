package com.ex.encrption.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EXUserResponse {
	
	List<Payload> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	
	

}
