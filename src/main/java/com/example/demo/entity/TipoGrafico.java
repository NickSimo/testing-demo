package com.example.demo.entity;


public enum TipoGrafico {
	RESOURCE_DOWNLOAD ("Risorse piu scaricate"),
	RESOURCE_VIEW ("Risorse piu visualizzate"),
	ORGANIZATION_VIEW ("Organizzazioni piu visualizzate"),
	DATASET_VIEW ("Dataset piu visualizzati"),
	PAGE_VIEW ("Pagine piu visualizzate");

	String descrizione;

	TipoGrafico(String descrizione) {
	}

	public String getDescrizione() {
		return descrizione;
	}
}
