package com.ex.encrption.service;
 
import com.ex.encrption.model.DepositWithdraw;
import com.ex.encrption.model.HyperMessage;
import com.ex.encrption.model.Payload;
import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;
import com.ex.encrption.model.WebsiteBean;
import com.ex.encrption.model.validationModel;

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
	public String encryptValidationPayload(validationModel payload) throws Exception;
	public validationModel decryptValidationPayload(String encoded);
	public String encryptDepositWithdraw(DepositWithdraw payload) throws Exception;
	public DepositWithdraw decryptDepositWithdraw(String encoded);
	public String encryptWebsite(WebsiteBean webbean) throws Exception;
	public WebsiteBean decryptWebsite(String encoded);
	
    ////////////////////////////////////encript&decriptHypermessage////////
    public String encrptyHperMessage(HyperMessage encriptHyperMessagePayload) throws Exception;
    public HyperMessage decryptHyperMessage(String encoded);
}	

