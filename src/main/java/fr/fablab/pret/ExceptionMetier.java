package fr.fablab.pret;

/** Une règle du FabLab n'est pas respectée : le code est renvoyé tel quel au client, avec le statut HTTP. */
public abstract class ExceptionMetier extends RuntimeException {

    private final String code;
    private final int statut;

    protected ExceptionMetier(String code, int statut, String message) {
        super(message);
        this.code = code;
        this.statut = statut;
    }

    public String code() {
        return code;
    }

    public int statut() {
        return statut;
    }
}
