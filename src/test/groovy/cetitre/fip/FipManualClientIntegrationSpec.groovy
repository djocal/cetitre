package cetitre.fip


import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class FipManualClientIntegrationSpec extends Specification {

    @Inject
    FipManualClient fipClient

    void "fip est la station 7"() {
        given:
        def liveMeta = fipClient.morceauxLive().blockingGet()
        Map steps = liveMeta.steps
        String cleEnTrainDetreJoue = liveMeta.levels[0].items[liveMeta.levels[0].position]
        Object entrainDetreJoue = liveMeta.steps.get(cleEnTrainDetreJoue)
        println "En train de jouer ${entrainDetreJoue.title} de ${entrainDetreJoue.authors}"

        expect:
        liveMeta.stationId == 7
        steps.keySet().size() >= 5
    }
}
