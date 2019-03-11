package prez

import cetitre.favoris.FavoriClient

class EtLeSql {

    FavoriClient enregistre_dans_PG

    /**
     * exemple compliqué {@link FavoriClient#ajouterPour(java.lang.String, cetitre.Morceau)} :
     *
     * pgPool.rxBegin              ==========> Single<PgTransaction>
     *
     * transaction.rxPreparedQuery ==========> Single<PgRowset>
     *
     * rowset.rowCount() == 1      ==========> fini avec succès. (void)
     */


    MicronautDeballagePriseEnMain conclusion
}
