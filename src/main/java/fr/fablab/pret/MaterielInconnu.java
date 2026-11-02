package fr.fablab.pret;

public class MaterielInconnu extends ExceptionMetier {

    public MaterielInconnu(String code) {
        super("MATERIEL_INCONNU", 404, "Matériel inconnu : " + code);
    }
}
