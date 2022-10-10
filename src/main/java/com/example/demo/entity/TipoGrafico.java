package com.example.demo.entity;


public enum TipoGrafico {
    RESOURCE_DOWNLOAD("Risorse piu scaricate"),
    RESOURCE_VIEW("Risorse piu visualizzate"),
    ORGANIZATION_VIEW("Organizzazioni piu visualizzate"),
    DATASET_VIEW("Dataset piu visualizzati"),
    PAGE_VIEW("Pagine piu visualizzate"),
    TEMI_VIEW("Temi piu visualizzati"),
    DATASET_TEMA("N. dataset per tema"),
    LICENZE_USED("Licenze più utilizzate"),
    FORMATI_USED("Formati più utilizzati"),
    ORGANIZATION_DATASET("N. dataset per organizzazione");

    String descrizione;

    TipoGrafico(String descrizione) {
    }

    public String getDescrizione() {
        return descrizione;
    }
}
