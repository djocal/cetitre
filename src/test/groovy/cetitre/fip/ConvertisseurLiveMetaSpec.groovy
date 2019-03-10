package cetitre.fip

import cetitre.PlayList
import spock.lang.Specification

class ConvertisseurLiveMetaSpec extends Specification {
    private ReponseLiveMeta neilYoung = new ReponseLiveMeta(
            levels: [new Level(
                    items: ['a', 'b', 'c', 'd', 'e'],
                    position: 2
            )],
            steps: ['c': new Step(
                    title: 'A Man Needs A Maid'),
                    'a': new Step(
                            title: 'Out On The Weekend'
                    ),
                    'b': new Step(
                            title: 'Harvest'
                    ),
                    'd': new Step(
                            title: 'Heart Of Gold'
                    ),
                    'e': new Step(
                            title: 'Are You Ready For The Country'
                    )]
    )

    void "stepAt"() {
        when:
        Step morceauAt1
        use(ConvertisseurLiveMeta.LiveMetaCategory) {
            morceauAt1 = neilYoung.stepAt(1)
        }
        then:
        morceauAt1.title == 'Harvest'

    }

    void "vers playlist"() {
        when:
        PlayList playList = new ConvertisseurLiveMeta().versPlayList(neilYoung)

        then:
        playList.encours.titre == 'A Man Needs A Maid'
        playList.precedents*.titre == ['Out On The Weekend', 'Harvest']
        playList.prochains*.titre == ['Heart Of Gold', 'Are You Ready For The Country']
    }
}
