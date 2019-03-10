package cetitre.fip

import groovy.transform.CompileStatic

@CompileStatic
class ReponseLiveMeta {

    /**
     * détails des morceaux par clé de morceau
     */
    Map<String, Step> steps

    /**
     * identifie la radio, 7 pour FIP
     */
    Integer stationId

    /**
     * les clés de morceaux dans l'ordre de passage
     */
    List<Level> levels
}

@CompileStatic
class Step {
    /**
     * la clé du morceau diffusé chez FIP
     */
    String stepId

    /**
     * d'autres clés
     */
    String uuid
    String songId

    String title
    String titreAlbum
    String anneeEditionMusique
    String authors
    String visual
    String visuelYoutube
    String lienYoutube
}

@CompileStatic
class Level {
    /**
     * liste des clés dans l'ordre de passage
     */
    List<String> items

    /**
     * position du morceau en train d'être joué
     */
    Integer position
}

