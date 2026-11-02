package fr.fablab.materiel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterielRepository extends JpaRepository<Materiel, String> {

    List<Materiel> findAllByOrderByCategorieAscCodeAsc();
}
