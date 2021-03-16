# chatroom
Réaliser une Chat Room ou les clients se connectent au serveur, envoient leurs ID (String). 
Envoient ensuite des messages qui sont diffusés à tous les autres Clients Connectés.

Exemple d'échange :

Aloga>id="Alpha"
serveur> Bienvenue Alpha vous êtes bien connectés : "Beta" et "Psi" sont aussi connectés
Alpha>Hello EveryBody 

beta> Alpha : Hello EveryBody 
psi>   Alpha : Hello EveryBody

En envoyant le message "Quit" le client se déconnecte et le serveur le supprime de la liste.
