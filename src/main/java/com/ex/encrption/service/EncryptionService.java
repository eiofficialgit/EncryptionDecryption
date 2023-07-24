package com.ex.encrption.service;

import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;

//encryption decryption using secrect key cipher
public interface EncryptionService {

	public String encrypt(String data);
	public String decrypt(String data);
	
	public TokenResponse encode(Token token);
	public Token decode (String token);
}
