package com.ex.encrption.service.impl;

import java.nio.charset.StandardCharsets; 
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.ex.encrption.model.Crypt;
import com.ex.encrption.model.DepositWithdraw;
import com.ex.encrption.model.HyperMessage;
import com.ex.encrption.model.Payload;
import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;
import com.ex.encrption.model.WebsiteBean;
import com.ex.encrption.model.validationModel;
import com.ex.encrption.service.EncryptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EncryptionServiceImpl implements EncryptionService {

	private static final String AES_KEY = "TOKEN_SECURITY_MOGLIX_AES_KEY_IN_JWT";
	private static final String secret_key="qwerty@123";

	@Override
	public String encrypt(String data) {
		AES aes = new AES(AES_KEY);
		return aes.encrypt(data);
	}

	@Override
	public String decrypt(String data) {
		AES aes = new AES(AES_KEY);
		return aes.decrypt(data);
	}

	// lets create class called AES

	private class AES {

		private SecretKeySpec secretKey;
		private byte[] key;

		AES(String secret) {
			MessageDigest sha = null;
			try {
				key = secret.getBytes(StandardCharsets.ISO_8859_1);
				sha = MessageDigest.getInstance("SHA-1");
				key = sha.digest(key);
				key = Arrays.copyOf(key, 16);
				secretKey = new SecretKeySpec(key, "AES");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String encrypt(String strToEncrypt) {
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				return Base64.getEncoder()
						.encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.ISO_8859_1)));

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		String decrypt(String strToDecrypt) {
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public TokenResponse encode(Token token) {
		AES aes = new AES(AES_KEY);
		Claims claims = Jwts.claims();
		claims.put("userId",token.getUserId());
		//we are encypting the userRefer using cipher
		claims.put("username",aes.encrypt(token.getusername()));
		claims.put("password",aes.encrypt(token.getpassword()));
		claims.put("expirationTime",token.getExpiryInMinutes());
		
		String tokenResponse = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+60*60*60*1000))
				.signWith(SignatureAlgorithm.HS512,secret_key.getBytes(StandardCharsets.UTF_8))
				.compact();
		return new TokenResponse(tokenResponse);
	}

	@Override
	public Token decode(String tokenSource) {
		AES aes = new AES(AES_KEY);
		Claims body=Jwts.parser()
				.setSigningKey(secret_key.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(tokenSource)
				.getBody();
		
		Token token= new Token();
		token.setUserId(Long.valueOf(String.valueOf(body.get("userId"))));
		token.setusername(aes.decrypt(String.valueOf(body.get("username"))));
		token.setpassword(aes.decrypt(String.valueOf(body.get("password"))));
		token.setExpiryInMinutes(Integer.parseInt(String.valueOf(body.get("expirationTime"))));
		return token;
	}
	

	private static final String ALGO = "AES";

	// Default uses ECB PKCS5Padding"

	public  String encrypted(String Data, String secret) throws Exception {
		Key key = generateKey(secret);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}

	public  String decrypted(String strToDecrypt, String secret) {
		try {
			Key key = generateKey(secret);
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	private  Key generateKey(String secret) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
		Key key = new SecretKeySpec(decoded, ALGO);
		return key;
	}

	public  String decodeKey(String str) {
		byte[] decoded = Base64.getDecoder().decode(str.getBytes());
		return new String(decoded);
	}

	public String encodeKey(String str) {
		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
		return new String(encoded);
	}
	
	////////////////////////////payload encryption////////////////////////////
	
	private static final String SECRET_KEY = "bXVzdGJlMTZieXRlc2tleQ=="; // Base64 encoded "mustbe16byteskey"

	public String encryptPayload(Payload payload) throws Exception {
		String payloadJson = new ObjectMapper().writeValueAsString(payload);
		return Crypt.encrypt(payloadJson, SECRET_KEY);
	}

	public Payload decryptPayload(String encryptedPayload) {
		try {
			String decryptedJson = Crypt.decrypt(encryptedPayload, SECRET_KEY);
			return new ObjectMapper().readValue(decryptedJson, Payload.class);
		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting payload: " + e.getMessage());
		}
	}

	@Override
	public String encryptValidationPayload(validationModel payload) throws Exception {
		String payloadJson = new ObjectMapper().writeValueAsString(payload);
		return Crypt.encrypt(payloadJson, SECRET_KEY);
	}

	@Override
	public validationModel decryptValidationPayload(String encoded) {
		try {
			String decryptedJson = Crypt.decrypt(encoded, SECRET_KEY);
			return new ObjectMapper().readValue(decryptedJson, validationModel.class);
		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting payload: " + e.getMessage());
		}
	}
	
	
	@Override
	public String encryptDepositWithdraw(DepositWithdraw payload) throws Exception {
		String payloadJson = new ObjectMapper().writeValueAsString(payload);
		return Crypt.encrypt(payloadJson, SECRET_KEY);
	}
	

	@Override
	public DepositWithdraw decryptDepositWithdraw(String encoded) {
		try {
			String decryptedJson = Crypt.decrypt(encoded, SECRET_KEY);
			return new ObjectMapper().readValue(decryptedJson, DepositWithdraw.class);
		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting payload: " + e.getMessage());
		}
	}

	@Override
	public String encryptWebsite(WebsiteBean webbean) throws Exception {
		String payloadJson = new ObjectMapper().writeValueAsString(webbean);
		return Crypt.encrypt(payloadJson, SECRET_KEY);
	}

	@Override
	public WebsiteBean decryptWebsite(String encoded) {
		try {
			String decryptedJson = Crypt.decrypt(encoded, SECRET_KEY);
			return new ObjectMapper().readValue(decryptedJson, WebsiteBean.class);
		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting payload: " + e.getMessage());
		}
	}
	
   //////////////////////////encrypt&decryptHypermessage//////////////////////////////////

   @Override
   public String encrptyHperMessage(HyperMessage encriptHyperMessagePayload) throws Exception {
   String payloadJson = new ObjectMapper().writeValueAsString(encriptHyperMessagePayload);
   return Crypt.encrypt(payloadJson, SECRET_KEY);
   }

    @Override
    public HyperMessage decryptHyperMessage(String encoded) {
    try {
         String decryptedJson = Crypt.decrypt(encoded, SECRET_KEY);
         return new ObjectMapper().readValue(decryptedJson, HyperMessage.class);
        } 
    catch (Exception e) {
          throw new RuntimeException("Error while decrypting payload: " + e.getMessage());
}
}
}
