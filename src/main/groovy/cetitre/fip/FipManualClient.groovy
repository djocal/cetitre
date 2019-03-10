package cetitre.fip

import groovy.transform.CompileStatic
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import io.reactivex.Maybe

import javax.inject.Singleton

@CompileStatic
@Singleton
class FipManualClient {
    /**
     * @return https://www.fip.fr/livemeta/7
     */
    Maybe<Object> morceauxLive() {
        Flowable reponse = httpClient.retrieve(HttpRequest.GET('/'),
                Argument.of(Object))
        return reponse.firstElement() as Maybe<Object>
    }


    FipManualClient(@Client('https://www.fip.fr/livemeta/7') RxHttpClient httpClient) {
        this.httpClient = httpClient
    }

    final RxHttpClient httpClient
}
