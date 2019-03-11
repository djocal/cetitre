package cetitre

import io.micronaut.context.ApplicationContext
import io.micronaut.http.MediaType
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MicronautTest
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BonjourControllerIntegrationSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "dis bonjour"() {
        given:
        String corps = client.toBlocking().retrieve("/bonjour")

        expect:
        corps == "Bonjour !"
    }

    void "réponse texte plein"() {
        when:
        HttpResponse reponseTextePlein = client.toBlocking().exchange("/bonjour")

        then:
        reponseTextePlein.status == HttpStatus.OK
        reponseTextePlein.contentType.get() == MediaType.TEXT_PLAIN_TYPE
        reponseTextePlein.contentLength == 9
    }
}
