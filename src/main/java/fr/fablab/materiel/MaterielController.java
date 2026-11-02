package fr.fablab.materiel;

import fr.fablab.pret.PretRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/materiels")
public class MaterielController {

    private final MaterielRepository materiels;
    private final PretRepository prets;

    public MaterielController(MaterielRepository materiels, PretRepository prets) {
        this.materiels = materiels;
        this.prets = prets;
    }

    @GetMapping
    public List<MaterielDisponibilite> lister() {
        return materiels.findAllByOrderByCategorieAscCodeAsc().stream()
                .map(m -> new MaterielDisponibilite(m.getCode(), m.getLibelle(), m.getCategorie(),
                        !prets.existsByMaterielAndDateRetourIsNull(m.getCode())))
                .toList();
    }

    public record MaterielDisponibilite(String code, String libelle, String categorie, boolean disponible) {
    }
}
