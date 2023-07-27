	package com.ex.encrption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;
import com.ex.encrption.service.EncryptionService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EncryptionController {

	@Autowired
	private EncryptionService encryptionService;

	@GetMapping("/encrypt")
	public String encrypt(@RequestParam("encrypt") String encrypt) {
		
//		System.out.println("*********** " + encrypt + " "+encryptionService.encrypt(encrypt));
		String result =encryptionService.encrypt(encrypt);
		
//		String decrypt=encryptionService.decrypt(result);
//		System.out.println("******decrypt "+decrypt);
		
		return result;
	}

	@GetMapping("/decrypt")
	public String decrypt(@RequestParam("decrypt") String decrypt) {
		return encryptionService.decrypt(decrypt);
	}
	
	
	@PostMapping("/token")
	public TokenResponse token(@RequestBody Token token) {
		return encryptionService.encode(token);
	}
	
	@GetMapping("/decodeToken")
	public Token decodeToken(@RequestParam String decodeToken ) {
		return encryptionService.decode(decodeToken);
	}

	String secretKey = "mustbe16byteskey";
	
	@PostMapping("/encode")
	public String encode(@RequestParam String encode) throws Exception {
		String encodeKey = encryptionService.encodeKey(secretKey);
		String encrypt = encryptionService.encrypted(encode, encodeKey);
		return encrypt;
	}
	
	@PostMapping("/decode")
	public String decode(@RequestParam String decode) throws Exception {
		String encodeKey = encryptionService.encodeKey(secretKey);
		String decrypt = encryptionService.decrypted(decode, encodeKey);
		return decrypt;
	}
}
