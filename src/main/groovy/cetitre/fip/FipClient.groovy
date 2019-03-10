package cetitre.fip

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe

@CompileStatic
@Client('https://www.fip.fr/livemeta/7')
interface FipClient {

    @Get('/')
    Maybe<ReponseLiveMeta> morceauxLive()
}
