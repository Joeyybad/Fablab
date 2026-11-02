package fr.fablab.web;

import fr.fablab.pret.ExceptionMetier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionErreurs {

    @ExceptionHandler(ExceptionMetier.class)
    public ResponseEntity<Erreur> metier(ExceptionMetier e) {
        return ResponseEntity.status(e.statut()).body(new Erreur(e.code(), e.getMessage()));
    }

    public record Erreur(String erreur, String message) {
    }
}
