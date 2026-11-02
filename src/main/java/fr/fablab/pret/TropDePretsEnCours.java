package fr.fablab.pret;

public class TropDePretsEnCours extends ExceptionMetier {

    public TropDePretsEnCours(String adherent) {
        super("TROP_DE_PRETS_EN_COURS", 409, "L'adhérent " + adherent + " a déjà " + PretService.PRETS_EN_COURS_MAX + " prêts en cours");
    }
}
