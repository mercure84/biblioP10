#Bienvenue dans mon Projet 10 du Parcours Développeur d'Application Java (OpenClassrooms).

Le projet est actuellement déployé sur mon serveur privé virtuel d'OVH. L'URL est la suivante : http://vps671888.ovh.net:8080/

Vous pouvez tester l'application sans risque avec le login test@test.com et le mdp : test.
 
Un jeu de données "ADMIN" peut être délivré pour accéder à la partie ADMIN de l'application et ainsi utiliser d'autres fonctionnalités.

##Si vous souhaitez installer le projet sur votre propre serveur, je vous propose de suivre les instructions suivantes :

Récupérer le dépôt git du projet : 

https://github.com/mercure84/biblioP10

**Il s'agit d'une application Spring Boot packagée avec Maven et qui peut être déployée sur un serveur Tomcat 9.**

Il faut importer l'ensemble du code source et le compiler avec maven : je vous conseille d'utiliser intelliJ pour cette opération, sinon se positionner à la racine du projet dans une console et lancer un "mvn package".
En packageant le projet, vous générez un fichier .jar que vous pouvez ensuite déployer sur une machine avec la commande :
java -jar fichierJar.jar

A noter que vous pouvez modifier les paramètres d'accès à la base de données dans le fichier application.properties et ainsi configurer votre propre base de données qui devra être gérée par PostGreSQL. Par défaut vous utiliserez la BDD logée sur mon serveur privé OVH.
Les fichiers .SQL permettent le cas échéant de créer une base de données.

Pour toute question, vous pouvez me contacter : julien.marcesse@gmail.com

Bonne utilisation !

Julien Marcesse
