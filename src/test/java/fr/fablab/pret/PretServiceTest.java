package fr.fablab.pret;

import fr.fablab.materiel.MaterielRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PretServiceTest {

    private static final Clock LUNDI = Clock.fixed(Instant.parse("2026-11-23T09:00:00Z"), ZoneId.of("Europe/Paris"));

    @Mock
    PretRepository prets;
    @Mock
    MaterielRepository materiels;

    PretService service;

    @BeforeEach
    void preparer() {
        service = new PretService(prets, materiels, LUNDI);
    }

    @Test
    void emprunte_un_materiel_disponible() {
        when(materiels.existsById("IMP-3D-01")).thenReturn(true);
        when(prets.existsByMaterielAndDateRetourIsNull("IMP-3D-01")).thenReturn(false);
        when(prets.countByAdherentAndDateRetourIsNull("ADH-042")).thenReturn(0L);
        when(prets.save(any(Pret.class))).thenAnswer(i -> i.getArgument(0));

        Pret pret = service.emprunter("ADH-042", "IMP-3D-01", 7);

        assertThat(pret.getDebut()).isEqualTo(LocalDate.of(2026, 11, 23));
        assertThat(pret.getRetourPrevu()).isEqualTo(LocalDate.of(2026, 11, 30));
    }

    @Test
    void refuse_un_materiel_inconnu() {
        when(materiels.existsById("XXX")).thenReturn(false);

        assertThatThrownBy(() -> service.emprunter("ADH-042", "XXX", 7)).isInstanceOf(MaterielInconnu.class);
        verify(prets, never()).save(any(Pret.class));
    }

    @Test
    void refuse_un_materiel_deja_sorti() {
        when(materiels.existsById("IMP-3D-01")).thenReturn(true);
        when(prets.existsByMaterielAndDateRetourIsNull("IMP-3D-01")).thenReturn(true);

        assertThatThrownBy(() -> service.emprunter("ADH-042", "IMP-3D-01", 7)).isInstanceOf(MaterielIndisponible.class);
    }

    @Test
    void refuse_un_quatrieme_pret_en_cours() {
        when(materiels.existsById("PER-02")).thenReturn(true);
        when(prets.existsByMaterielAndDateRetourIsNull("PER-02")).thenReturn(false);
        when(prets.countByAdherentAndDateRetourIsNull("ADH-042")).thenReturn(3L);

        assertThatThrownBy(() -> service.emprunter("ADH-042", "PER-02", 7)).isInstanceOf(TropDePretsEnCours.class);
    }
}
