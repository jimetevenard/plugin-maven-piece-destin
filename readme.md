# Plugin Maven "Pièce du destin"

Une petite introduction au developpement de plugin Maven, qui complète par l'exemple la doc officille :  
<https://maven.apache.org/guides/introduction/introduction-to-plugins.html>

Le but d'un plugin Maven est d'injecter du code pour faire des action à différentes phases de votre build.  
Par exemple :
* executer des test de qualité de code (et empecher le build s'ils ne sont pas concluants)
* Modifier / précompiler votre code (Projet lombock, par exemple)
* executer des tests unitaires...


D'ailleurs, pour un faire un joli .jar avec Maven, vous utilisez sans doute le [Maven assembly plugin](http://maven.apache.org/plugins/maven-assembly-plugin/), qui est aussi un plugin.

# Voyons un peu ça :

Ce repo contient Deux projets :
* piece-destin-maven-plugin  
  Le Plugin Maven qui contient un [Mojo](https://maven.apache.org/developers/mojo-api-specification.html), 
  une classe Java qui hérite de [AbstractMojo](http://maven.apache.org/ref/3.5.2/maven-plugin-api/apidocs/org/apache/maven/plugin/AbstractMojo.html)
  et annotée [@Mojo](https://maven.apache.org/plugin-tools/maven-plugin-annotations/apidocs/org/apache/maven/plugins/annotations/Mojo.html)
* piece-destin-projet-test  
  Projet Maven quelconque qui a une dépendence de type `<plugin >` dans son POM vers le permier projet.

# Mise en oeuvre

1. Faire un `mvn install` de piece-destin-maven-plugin
2. Tenter un build de piece-destin-projet-test (`mvn compile, package, install peu importe...`)  
   Le plugin fait un bon vieux `Math.random` des familles,
   si vous avez de la chance, vous aurez un joli BUILD SUCCESS et vous irez au resto.  
   Sinon, vous avez un affreux BUILD FAILURE et vous êtes mûr pour le micro-ondes.

# Le MOJO, le coeur du plugin Maven

Les classes java utilisée par Maven sont des "Mojo" (Austin powers tribute !), c'est à dire un [Pojo](https://fr.wikipedia.org/wiki/Plain_old_Java_object) Java à la sauce Maven.

l'interface [Mojo](https://maven.apache.org/ref/3.0.5/maven-plugin-api/apidocs/org/apache/maven/plugin/Mojo.html) déclare une méthode unique :
`public void execute() throws MojoExecutionException, MojoFailureException` qui sera appellée au lancement.

Chaque Mojo peut être associé à une (ou plusieurs) étape(s) du [Cycle de vie Maven](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).  
Pour cela, chaque Mojo est identifié par son `goal`, une petite chaîne de caractère qui représente son but dans la vie :

    @Mojo(name = "goal-du-mojo")
    public class VotreMojo extends AbstractMojo {

        public void execute() throws bla,bla... {
	    
	    // du code qui fait des dingueries...

        }
    }


On peut associer chaque goal (qui correspond donc à un Mojo) à une phase de build.

Cette association peut se faire au niveau de votre plugin Maven (Dans ce cas le développeur qui utilise le plugin n'aura rien à faire)
ou au niveau du POM.XML du projet qui appelle le plugin (dans ce cas, le développeur du projet en question aura alors un controle plus
fin sur le fonctionnement du plugin).

Les deux sont d'ailleurs cumulables.

**Concrètement**, ici j'ai mappé dans le pom.xml de *piece-destin-projet-test* le goal "piece" à la phase "compile".

