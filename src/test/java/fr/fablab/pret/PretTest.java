package fr.fablab.pret;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Les règles du prêt, sans Spring ni base : quelques millisecondes. */
class PretTest {

    private static final LocalDate LUNDI = LocalDate.of(2026, 11, 23);

    @Test
    void un_pret_de_7_jours_est_a_rendre_une_semaine_plus_tard() {
        Pret pret = Pret.creer("ADH-042", "IMP-3D-01", LUNDI, 7);

        assertThat(pret.getRetourPrevu()).isEqualTo(LocalDate.of(2026, 11, 30));
        assertThat(pret.estEnCours()).isTrue();
    }

    @Test
    void un_pret_peut_durer_14_jours() {
        Pret pret = Pret.creer("ADH-042", "IMP-3D-01", LUNDI, 14);

        assertThat(pret.getRetourPrevu()).isEqualTo(LUNDI.plusDays(14));
    }

    @Test
    void un_pret_de_0_jour_est_refuse() {
        assertThatThrownBy(() -> Pret.creer("ADH-042", "IMP-3D-01", LUNDI, 0))
                .isInstanceOf(DureeInvalide.class);
    }

    @Test
    void un_pret_non_rendu_apres_la_date_prevue_est_en_retard() {
        Pret pret = Pret.creer("ADH-042", "IMP-3D-01", LUNDI, 7);

        assertThat(pret.estEnRetard(LUNDI.plusDays(7))).isFalse();
        assertThat(pret.estEnRetard(LUNDI.plusDays(8))).isTrue();
    }

    @Test
    void un_pret_ne_se_rend_pas_deux_fois() {
        Pret pret = Pret.creer("ADH-042", "IMP-3D-01", LUNDI, 7);
        pret.rendre(LUNDI.plusDays(3));

        assertThatThrownBy(() -> pret.rendre(LUNDI.plusDays(4))).isInstanceOf(PretDejaRendu.class);
    }
}
