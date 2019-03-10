package cetitre

import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.time.LocalDateTime

@CompileStatic
@ToString
class PlayList {

    Morceau encours
    List<Morceau> precedents
    List<Morceau> prochains

}

@CompileStatic
@ToString
class Morceau {
    String uuid

    String titre
    String titreAlbum
    String anneeEdition
    String auteurs
    String visuelFip
    String visuelYoutube
    String lienYoutube
}

@CompileStatic
@ToString
class Favori {
    UUID id
    LocalDateTime ajouteLe
    Morceau morceau
}