package com.example.u4Progettod24.entities;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.u4Progettod24.entities.payloads.UtenteRegistrationPayload;
import com.example.u4Progettod24.exceptions.BadRequestException;

@Service
public class UtenteService {
	@Autowired
	private UtenteRepository utenteRepo;

	public Utente create(UtenteRegistrationPayload u) {
		utenteRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});
		Utente newUtente = new Utente(u.getNome(), u.getCognome(), u.getEmail());
		return utenteRepo.save(newUtente);
	}

	public Page<Utente> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return utenteRepo.findAll(pageable);
	}

	public Utente findById(UUID id) throws NotFoundException {
		return utenteRepo.findById(id).orElseThrow(() -> new NotFoundException());
	}

	public Utente findByEmail(String email) throws NotFoundException {
		return utenteRepo.findByEmail(email).orElseThrow(() -> new NotFoundException());
	}

	public Utente findByIdAndUpdate(UUID id, Utente u) throws NotFoundException {
		Utente found = this.findById(id);

		found.setId(id);
		found.setNome(u.getNome());
		found.setCognome(u.getCognome());
		found.setEmail(u.getEmail());
		// found.setUtenti(u.getUtenti());

		return utenteRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Utente found = this.findById(id);
		utenteRepo.delete(found);
	}
}
