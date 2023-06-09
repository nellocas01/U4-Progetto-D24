package com.example.u4Progettod24.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dispositivi")
@Data
@NoArgsConstructor
public class Dispositivo {
	@Id
	@GeneratedValue
	private UUID id;

	@Enumerated(EnumType.STRING)
	private Tipologia tipologia;

	@Enumerated(EnumType.STRING)
	private Prodotto prodotto;

	@ManyToOne
	@JoinColumn(name = "utente_id")
	private Utente utente;

	public Dispositivo(Tipologia tipologia, Prodotto prodotto) {
		super();
		this.tipologia = tipologia;
		this.prodotto = prodotto;
	}

}
