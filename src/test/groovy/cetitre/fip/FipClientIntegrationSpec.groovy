package cetitre.fip

import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Maybe
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class FipClientIntegrationSpec extends Specification {
    @Inject FipClient fipClient

    void "lecture stationId et levels"() {
        given:
        ReponseLiveMeta liveMeta = fipClient.morceauxLive().blockingGet()
        Step morceau = liveMeta.steps.get(liveMeta.levels[0].items[liveMeta.levels[0].position])
        println "En train de jouer ${morceau.title} de ${morceau.authors}."

        expect:
        liveMeta.stationId == 7
        liveMeta.levels[0].items.size() >= 5
        liveMeta.levels[0].position == 3
    }
}
