package com.ex.encrption.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdraw {
	
	private String password;
	private List<Payload> transactions;

}
