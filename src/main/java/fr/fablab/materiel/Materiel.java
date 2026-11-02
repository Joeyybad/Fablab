package fr.fablab.materiel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Materiel {

    @Id
    private String code;
    private String libelle;
    private String categorie;

    protected Materiel() {
    }

    public Materiel(String code, String libelle, String categorie) {
        this.code = code;
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCategorie() {
        return categorie;
    }
}
