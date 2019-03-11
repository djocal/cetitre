package cetitre

import cetitre.favoris.FavoriClient
import cetitre.fip.ConvertisseurLiveMeta
import cetitre.fip.FipClient
import cetitre.fip.ReponseLiveMeta
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.reactivex.Maybe
import io.reactivex.Single

import javax.inject.Inject

@CompileStatic
@Controller("/")
class PrincipalController {

    /**
     * http://localhost:8080/fip
     *
     * @return le morceau en cours, précédents et uivants de Radio Fip
     */
    @Get(uri = '/fip')
    Maybe<PlayList> fip() {
        return fipClient.morceauxLive().map { ReponseLiveMeta reponseLiveMeta ->
            return convertisseurLiveMeta.versPlayList(reponseLiveMeta)
        }
    }








    @Post(uri = "/{auditeur}/favoris")
    Single<String> ajouterAuxFavoris(String auditeur, @Body Morceau morceau) {
        return favoriClient.ajouterPour(auditeur, morceau).toSingleDefault('Ajouté !')
    }

    @Get(uri = "/{auditeur}/favoris")
    Single<List<Favori>> lesRecupererTousPour(String auditeur) {
        favoriClient.lesRecupererTousPour(auditeur)
    }

    @Inject
    FipClient fipClient

    @Inject
    ConvertisseurLiveMeta convertisseurLiveMeta

    @Inject
    FavoriClient favoriClient
}
