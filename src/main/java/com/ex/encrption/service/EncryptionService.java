package com.ex.encrption.service;

import com.ex.encrption.model.ListResponse;
import com.ex.encrption.model.Payload;
import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;

//encryption decryption using secrect key cipher
public interface EncryptionService {

	public String encrypt(String data);
	public String decrypt(String data);
	
	public TokenResponse encode(Token token);
	public Token decode (String token);

	public String encodeKey(String secretKey);
	public String decodeKey(String secretKey);
	public String encrypted(String encode, String encodeKey) throws Exception;
	public String decrypted(String decode, String encodeKey) throws Exception;
	
	//////////////////////payload encryption/////////////////////////
	
	public String encryptPayload(Payload payload) throws Exception;
	public Payload decryptPayload(String encoded);
	
	
}
