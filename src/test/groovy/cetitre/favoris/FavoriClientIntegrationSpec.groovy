package cetitre.favoris

import cetitre.Favori
import cetitre.Morceau
import groovy.util.logging.Slf4j
import io.micronaut.test.annotation.MicronautTest
import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.PgTransaction
import io.reactivex.Completable
import spock.lang.Specification

import javax.inject.Inject

@Slf4j
@MicronautTest
class FavoriClientIntegrationSpec extends Specification {

    void "pour voir"() {
        given:
        List<String> todos = favoriClient.exempleTodoList().blockingGet()
        log.debug "todos:" + todos
        expect:
        todos.size() > 0
    }

    void "récupérer les favoris de zoo"() {
        given:
        List<Favori> favoris = favoriClient.lesRecupererTousPour('zoo').blockingGet()
        log.debug "favoris: " + favoris
        expect:
        favoris.size() == 1
        favoris[0].morceau.titre == 'EL NINO'
    }

    void "ajouter un favori à djocal"() {
        given:
        Closure combien = { favoriClient.lesRecupererTousPour('djocal').blockingGet().size() }
        Integer avant = combien()
        log.debug "avant $avant"
        Integer apres

        PgTransaction txTest = pgPool.rxBegin().blockingGet()
        favoriClient.ajouterPour('djocal',
                new Morceau(uuid: UUID.randomUUID().toString(), titre: 'Alexandrie'),
                txTest).blockingGet()
        txTest.rollback()

        Integer commeavant = combien()

        expect:
        commeavant == avant
    }

    @Inject
    FavoriClient favoriClient

    @Inject
    PgPool pgPool
}
