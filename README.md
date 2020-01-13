# Welcome on doc !
Vous trouverez sur cette petite documentation complémentaire à la java doc, les instructions necessaires à la mise en oeuvre des serveurs d'identification
Par serveur d'identification on parle en fait de l'ensemble des servers du TPs.
Pareil pour requette d'identification qui comprends également les requetes d'ajout, modification, suppression ...

# Fonctionnement


## Comment demarrer les serveurs

1. Vérifier l'environement d'éxecution

Etant donné qu'il s'agit d'un projet Maven, n'oublié pas d'importer les dépendances avant de vouloir lancer les programmes principaux.

Assurer vous d'abord de ne pas avoir les ports suivant d'ouvert sur la machine qui va lancer les serveurs :
   * `28414` : port du serveur Checker en UDP et TCP
   * `28415` : port du serveur Manager en TCP
   * `3244` : port du serveur de log
   
2.  Lancer le serveur de log

Lancer le main `MainLog` du `src`

3. Lancer ensuite le main `Main` du `src`

Ce programme va successivement démmarer les trois serveurs necessaires pour traiter les demandes tel que specifiées dans le TP

    Les services sont désormais fonctionnelles, vous pouvez y acceder en utlisant les deux clients mis à votre disposition dans le paquetage client
 
## Utilliser les clients

### Avec TCP
Lancer le programme `ClientTCP` ou `ClientManager` du paquetage `Clients`.

### Avec UDP
Lancer le programme `ClientTCP` du paquetage `Clients`.