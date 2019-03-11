package cetitre.favoris

import cetitre.Favori
import cetitre.Morceau
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactiverse.pgclient.Row
import io.reactiverse.pgclient.impl.ArrayTuple
import io.reactiverse.reactivex.pgclient.PgPool
import io.reactiverse.reactivex.pgclient.PgRowSet
import io.reactiverse.reactivex.pgclient.PgTransaction
import io.reactiverse.reactivex.pgclient.Tuple
import io.reactivex.Completable
import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

import static io.reactiverse.reactivex.pgclient.Tuple.*

@CompileStatic
@Slf4j
@Singleton
class FavoriClient {

    Single<List<String>> exempleTodoList() {
        pgPool.rxQuery("select * from todo").map { PgRowSet rowSet ->
            rowSet.delegate.collect { Row row -> row.getString("item") }
        }
    }



    Single<List<Favori>> lesRecupererTousPour(String auditeur) {
        pgPool.rxPreparedQuery('select * from favori where auditeur = $1 order by ajoute_le', of(auditeur))
                .map { PgRowSet rowSet ->
            rowSet.delegate.collect { Row row ->
                new Favori(
                        id: row.getUUID('favori_id'),
                        ajouteLe: row.getLocalDateTime('ajoute_le'),
                        morceau: new Morceau(
                                uuid: row.getString('morceau_id'),
                                titre: row.getString('titre'),
                                lienYoutube: row.getString('lien_youtube'),
                                auteurs: row.getString('auteurs'),
                                titreAlbum: row.getString('titre_album')
                        )
                )
            }
        }
    }




    Completable ajouterPour(String auditeur, Morceau morceauFavori, PgTransaction transactionParente = null) {
        log.debug ">ajouterPour $auditeur transaction auto ${transactionParente == null}"
        PgTransaction transactionEffective

        (transactionParente ? Single.just(transactionParente) : pgPool.rxBegin()
        ).flatMap { PgTransaction transaction ->
            transactionEffective = transaction
            Tuple params = Tuple.of(
                    UUID.randomUUID(), auditeur,
                    morceauFavori.uuid, morceauFavori.titre, morceauFavori.lienYoutube)
            params.addString morceauFavori.auteurs
            params.addString morceauFavori.titreAlbum
            transactionEffective.rxPreparedQuery ("""insert into favori (favori_id, auditeur,
                        morceau_id, titre, lien_youtube,
                        auteurs, titre_album)
                    values (\$1, \$2,
                        \$3, \$4, \$5,
                        \$6, \$7)""", params)
        }.flatMapCompletable { PgRowSet rowSet ->
            log.debug "requête exécutée."
            Boolean succes = rowSet.rowCount() == 1
            if (!transactionParente) { // autocommit
                log.debug "transaction commit auto."
                transactionEffective.commit()
            }
            return succes ? Completable.complete() :
                    Completable.error(new UnsupportedOperationException("ajout ko pour $auditeur"))
        }
    }


    @Inject
    PgPool pgPool
}
