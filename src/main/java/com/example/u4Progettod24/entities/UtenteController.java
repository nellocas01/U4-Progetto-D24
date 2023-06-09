package com.example.u4Progettod24.entities;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.u4Progettod24.entities.payloads.UtenteRegistrationPayload;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
	@Autowired
	private UtenteService utenteService;

	@GetMapping("")
	public List<Utente> getUtente() {
		return utenteService.find();
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUtente(@RequestBody @Validated UtenteRegistrationPayload body) {
		return utenteService.create(body);
	}

	@GetMapping("/{utenteId}")
	public Utente getUtente(@PathVariable UUID utenteId) throws Exception {
		return utenteService.findById(utenteId);
	}

	@PutMapping("/{utenteId}")
	public Utente updateUtente(@PathVariable UUID utenteId, @RequestBody Utente body) throws Exception {
		return utenteService.findByIdAndUpdate(utenteId, body);
	}

	@DeleteMapping("/{utenteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUtente(@PathVariable UUID utenteId) throws NotFoundException {
		utenteService.findByIdAndDelete(utenteId);
	}

}
