package com.example.u4Progettod24.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Dispositivo {
	@ManyToOne
	private Utente idUtente;

	@Enumerated(EnumType.STRING)
	private Tipologia tipologia;

	@Enumerated(EnumType.STRING)
	private Prodotto prodotto;

	public Dispositivo(Utente idUtente, Tipologia tipologia, Prodotto prodotto) {
		super();
		this.idUtente = idUtente;
		this.tipologia = tipologia;
		this.prodotto = prodotto;
	}

}
