package com.ex.encrption.controller;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ex.encrption.model.DepositWithdraw;
import com.ex.encrption.model.EncodedPayload;
import com.ex.encrption.model.HyperMessage;
import com.ex.encrption.model.Payload;
import com.ex.encrption.model.Token;
import com.ex.encrption.model.TokenResponse;
import com.ex.encrption.model.WebsiteBean;
import com.ex.encrption.model.validationModel;
import com.ex.encrption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class EncryptionController {

	@Autowired
	private EncryptionService encryptionService;

	@GetMapping("/encrypt")
	public String encrypt(@RequestParam("encrypt") String encrypt) {

//		System.out.println("*********** " + encrypt + " "+encryptionService.encrypt(encrypt));
		String result = encryptionService.encrypt(encrypt);

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
	public Token decodeToken(@RequestParam String decodeToken) {
		return encryptionService.decode(decodeToken);
	}

	String secretKey = "mustbe16byteskey";

	@GetMapping("/encode")
	public String encode(@RequestParam("encode") String encode) throws Exception {
		String encodeKey = encryptionService.encodeKey(secretKey);
		String encrypt = encryptionService.encrypted(encode, encodeKey);
		return encrypt;
	}

	@GetMapping("/decode")
	public String decode(@RequestParam("decode") String decode) throws Exception {
		String encodeKey = encryptionService.encodeKey(secretKey);
		String decrypt = encryptionService.decrypted(decode, encodeKey);
		return decrypt;
	}

	//////////////////////// payload encryption//////////////////////

	@PostMapping("/encryptPayload")
	public String encryptPayload(@RequestBody Payload payload) throws Exception {
		return encryptionService.encryptPayload(payload);
	}

	@PostMapping("/decryptPayload")
	public Payload decryptPayload(@RequestBody EncodedPayload encode) {
		String encoded = encode.getPayload();
		return encryptionService.decryptPayload(encoded);
	}

	@PostMapping("/decryptListPayload")
	public Object decryptListPayload(@RequestBody EncodedPayload encode) throws JsonProcessingException {
		String encoded = encode.getPayload();
		Payload decryptPayload = encryptionService.decryptPayload(encoded);
		String payload = decryptPayload.getPayload();
		String[] stringArray = payload.split("EXUser");
		List<String> stringList = Arrays.asList(stringArray);
//		ObjectMapper objectMapper = new ObjectMapper();
//		Payload readValue = objectMapper.readValue(payload, Payload.class);
		return stringList;
	}
	
	@PostMapping("/encryptValidation")
	public String encryptValidation(@RequestBody validationModel payload) throws Exception {
		return encryptionService.encryptValidationPayload(payload);
	}
	
	@PostMapping("/decryptValidation")
	public validationModel decryptValidation(@RequestBody EncodedPayload encode) {
		String encoded = encode.getPayload();
		return encryptionService.decryptValidationPayload(encoded);
	}
	
	
	@PostMapping("/encryptDepositWithdraw")
	public String encryptDepositWithdraw(@RequestBody DepositWithdraw payload) throws Exception {
		return encryptionService.encryptDepositWithdraw(payload);
	}
	
	
	@PostMapping("/decryptDepositWithdraw")
	public DepositWithdraw decryptDepositWithdraw(@RequestBody EncodedPayload encode) {
		String encoded = encode.getPayload();
		return encryptionService.decryptDepositWithdraw(encoded);
	}
	
	
	@PostMapping("/encryptWebsiteBean")
	public String encryptWebsiteBean(@RequestBody WebsiteBean webbean) throws Exception {
		return encryptionService.encryptWebsite(webbean);
	}
	
	@PostMapping("/decryptWebsiteBean")
	public WebsiteBean decryptWebsiteBean(@RequestBody EncodedPayload encode) {
		String encoded = encode.getPayload();
		return encryptionService.decryptWebsite(encoded);
	}
	
    ////////////////////////////////////encrypt&decryptHypermessage//////////////////////////
	
    @PostMapping("/encriptHyperMessage")
    public String encrptHyperMessage(@RequestBody HyperMessage encriptHyperMessagePayload) throws Exception{
    return encryptionService.encrptyHperMessage(encriptHyperMessagePayload);
    }

    @PostMapping("/decryptHyperMessage")
    public HyperMessage decryptHyperMessage(@RequestBody EncodedPayload encode) {
    String encoded = encode.getPayload();
    return encryptionService.decryptHyperMessage(encoded);
    }
    }
