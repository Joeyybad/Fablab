package fr.fablab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/** Le contexte Spring démarre, migrations Flyway comprises, sur la base H2 du profil h2. */
@SpringBootTest
@ActiveProfiles("h2")
class FablabApplicationTest {

    @Test
    void le_contexte_demarre() {
    }
}
