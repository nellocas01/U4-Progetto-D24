package com.example.u4Progettod24.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.u4Progettod24.auth.payloads.AuthenticationSuccessfullPayload;
import com.example.u4Progettod24.entities.Utente;
import com.example.u4Progettod24.entities.UtenteService;
import com.example.u4Progettod24.entities.payloads.UtenteLoginPayload;
import com.example.u4Progettod24.entities.payloads.UtenteRegistrationPayload;
import com.example.u4Progettod24.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UtenteService utenteService;

	@PostMapping("/register")
	public ResponseEntity<Utente> register(@RequestBody @Validated UtenteRegistrationPayload body) {
		Utente createdUtente = utenteService.create(body);
		return new ResponseEntity<>(createdUtente, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UtenteLoginPayload body)
			throws NotFoundException {

		Utente utente = utenteService.findByEmail(body.getEmail());

		if (!body.getPassword().matches(utente.getPassword()))
			throw new UnauthorizedException("Credenziali non valide");

		String token = JWTTools.createToken(utente);

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
	}

}