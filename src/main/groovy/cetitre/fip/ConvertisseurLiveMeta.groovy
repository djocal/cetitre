package cetitre.fip

import cetitre.Morceau
import cetitre.PlayList

import javax.inject.Singleton

@Singleton
class ConvertisseurLiveMeta {

    static class LiveMetaCategory {
        static Step stepAt(ReponseLiveMeta liveMeta, Integer position) {
            return liveMeta.steps.get(liveMeta.levels[0].items[position])
        }
    }

    PlayList versPlayList(ReponseLiveMeta liveMeta) {
        Integer encours = liveMeta.levels[0].position
        Integer total = liveMeta.levels[0].items.size()

        use(LiveMetaCategory) {

            return total ? new PlayList(
                    encours: versMorceau(liveMeta.stepAt(encours)),
                    precedents: encours ? (0..(encours - 1)).collect {
                        versMorceau(liveMeta.stepAt(it))
                    } : [],
                    prochains: encours < total -1 ? ((encours + 1)..(liveMeta.levels[0].items.size() - 1)).collect {
                        versMorceau(liveMeta.stepAt(it))
                    } : []
            ) : new PlayList()
        }
    }

    protected Morceau versMorceau(Step step) {
        return new Morceau(
                uuid: step.uuid,
                titre: step.title,
                titreAlbum: step.titreAlbum,
                anneeEdition: step.anneeEditionMusique,
                auteurs: step.authors,
                visuelFip: step.visual,
                visuelYoutube: step.visuelYoutube,
                lienYoutube: step.lienYoutube)
    }
}
