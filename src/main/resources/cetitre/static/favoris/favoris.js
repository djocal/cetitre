recupereFavoris();

function recupereFavoris() {
    var urlParams = new URLSearchParams(window.location.search);
    var auditeur = urlParams.get("auditeur");

    var request = new XMLHttpRequest();
    request.open('GET', `/${auditeur}/favoris`);

    request.onload = function () {
        if (request.status == 200) {
            var favoris = JSON.parse(request.responseText);
            console.log("taille " + favoris.length);
            affiche(favoris);
        }
        if (request.status >= 400) {
            var tableFavoris = document.querySelector("table tbody");
            tableFavoris.innerHTML = "Ooops requête plantée !";
        }
    };
    request.send();
}

function affiche(favoris) {
    var tableFavoris = document.querySelector("table tbody");
    tableFavoris.innerHTML = '';
    favoris.forEach(function(favori) {
        console.log("type " + favori.ajouteLe);
        tableFavoris.innerHTML += `<tr><td>${favori.ajouteLe}</td>` +
            `<td><a href="${favori.morceau.lienYoutube}">${favori.morceau.titre}</a></td>` +
            `<td>${favori.morceau.auteurs}</td>` +
            `<td>${favori.morceau.titreAlbum}</td></tr>`;
    })
}
