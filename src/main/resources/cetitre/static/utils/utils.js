/**
 * UTILITAIRES
 */

/**
 * valorise l'attribut d'un élément
 * @param elementId
 * @param attribut
 * @param valeur
 */
function set(elementId, attribut, valeur) {
    switch (attribut) {
        case 'textContent' :
            return document.getElementById(elementId).textContent = valeur;
            break;
        case 'value' :
            return document.getElementById(elementId).value = valeur;
            break;
        default:
            return document.getElementById(elementId).setAttribute(attribut, valeur);
    }
}

/**
 * récupère la valeur d'un attribut d'élément
 * @param elementId
 * @param attribut
 * @returns {string}
 */
function get(elementId, attribut) {
    switch (attribut) {
        case 'textContent' :
            return document.getElementById(elementId).textContent;
        case 'value' :
            return document.getElementById(elementId).value;
        default:
            return document.getElementById(elementId).getAttribute(attribut);
    }
}
