var playlist;

/**
 * au démarrage appelle la playlist Fip
 */
recuperePlaylist();

/**
 * récupère la playlist Fip.
 */
function recuperePlaylist() {
    set("encours.titreAuteurs", "textContent", "(bougez pas je me renseigne...)");
    var request = new XMLHttpRequest();
    request.open('GET', '/fip');

    request.onload = function () {
        if (request.status == 200) {
            playlist = JSON.parse(request.responseText);
            affiche(playlist);
        }
        if (request.status >= 400) {
            set("encours.titreAuteurs", "textContent", "oops, site web injoignable, rafraichissez pour voir?")
        }
    };
    request.send();
    return false;
}

/**
 * affiche la
 * @param playlist (appelé à réception du résultat au démarrage)
 */
function affiche(playlist) {
    var visuelFip = playlist.encours.visuelFip.startsWith("http") ? playlist.encours.visuelFip : "fip-quadri-filet.png"
    set("encours.visuelfip", "src", visuelFip);
    set("encours.titreAuteurs", "textContent",
        playlist.encours.titre + (playlist.encours.auteurs ? " de " + playlist.encours.auteurs : ""));
    set("encours.lienyoutube", "href", playlist.encours.lienYoutube);
    ajouterFavori.classList.replace("apres", "avant");
}

/**
 * ajoute le morceau aux favoris de l'auditeur
 */
function ajouteAuFavoris() {
    var champAuditeur = document.getElementById('auditeur');
    if (champAuditeur.value.length < 2) {
        champAuditeur.classList.add('invalide');
    } else {
        champAuditeur.classList.remove('invalide');

        var url = '/' + champAuditeur.value + '/favoris';
        var morceau = playlist.encours;
        var request = new XMLHttpRequest();
        request.open('POST', url, true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.onreadystatechange = function () {
            console.log('change !' + request.status);
            if (request.status == 200) {
                var ajouterFavori = document.getElementById("ajouterFavori");
                ajouterFavori.classList.replace("avant", "apres");
            }
            if (request.status > 400) {
                document.getElementById("ajouterFavori").classList.add('invalide');
            }
        }
        request.send(JSON.stringify(morceau));
    }

    return false;
}
