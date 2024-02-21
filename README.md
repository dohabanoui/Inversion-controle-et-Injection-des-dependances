# Inversion de contrôle et Injection des dépendances
## Ajout de dépendances

Pour pouvoir utiliser le framework Spring dans notre projet, nous devons ajouter les dépendances appropriées à notre fichier de configuration Maven (`pom.xml`). Voici comment ajouter les dépendances nécessaires pour Spring Core, Spring Context et Spring Beans :

```xml
<dependencies>
    <!-- Spring Core -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>6.1.3</version>
    </dependency>

    <!-- Spring Context -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.3</version>
    </dependency>

    <!-- Spring Beans -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>6.1.3</version>
    </dependency>
</dependencies>
```

## Partie 1
### Création de l'interface IDao avec une méthode getDate
La première étape consiste à créer l'interface IDao. Cette interface définira la structure pour accéder aux données dans notre application.
```java 
package dao;

public interface IDao {
    double getData();
}
```
### Création d'une implémentation de l'interface IDao

Nous avons créé une implémentation de l'interface `IDao` que nous avons précédemment définie. Cette implémentation, nommée `DaoImpl`, fournit une méthode `getData()` qui récupère des données à partir d'une source.

```java
package dao;

public class DaoImpl implements IDao {// version base de données
    public double getData() {
        System.out.println("version base de données ");
        double data = 10.0;
        return data;
    }
}
```
### Création de l'interface IMetier avec une méthode calcul

Nous avons créé une nouvelle interface appelée `IMetier`. Cette interface définit un besoin fonctionnel dans notre système, en déclarant une méthode `calcul()` qui sera utilisée pour effectuer des calculs dans le métier de notre application.

```java
package metier;

public interface IMetier {
    // on déclare le besoin fonctionnel
    double calcul();
}
```


### Création d'une implémentation de l'interface IMetier en utilisant le couplage faible

Nous avons implémenté la classe `MetierImpl` qui réalise les opérations métier de notre application en utilisant le couplage faible. Cela signifie que la classe `MetierImpl` dépend uniquement de l'interface `IDao` et non d'une implémentation spécifique de cette interface.

La classe `MetierImpl` comprend la :

- **Dépendance de l'interface IDao :** Nous avons déclaré une variable `dao` de type `IDao` dans la classe `MetierImpl`. Cela permet à la classe `MetierImpl` d'être indépendante de toute implémentation concrète de l'interface `IDao`, ce qui favorise le couplage faible.


```java
package metier;

import dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao; // Couplage faible : la classe MetierImpl dépend directement de l'interface

    @Override
    public double calcul() {
        double data = dao.getData();
        double res = data * 23;
        return res;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
}
```
### Faire l'injection des dépendances

#### Par instanciation statique

L'**instanciation statique** est une méthode simple pour injecter des dépendances en créant explicitement des instances des classes nécessaires dans le code.
Nous créons une instance de DaoImpl2 directement dans la méthode main(). Ensuite, nous créons une instance de MetierImpl et injectons la dépendance dao en utilisant la méthode setDao().
```java
package presentation;

import ext.DaoImpl2;
import metier.MetierImpl;

// La couche qui va subir la maintenance
public class pres1 {
    public static void main(String[] args) {
        DaoImpl2 dao = new DaoImpl2(); // Instanciation statique
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao); // Injection de dépendance
        System.out.println("Résultat: " + metier.calcul());
    }
}
```
#### Par instanciation dynamique

L'**instanciation dynamique** est une méthode permettant de créer des instances de classes.Cela permet de modifier le comportement de l'application sans avoir à modifier le code source.

Nous utilisons l'instanciation dynamique pour charger et créer des instances des classes `IDao` et `IMetier` à partir d'un fichier de configuration externe (`config.txt`).

```java
package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class pres2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClassName = scanner.nextLine();
        Class<?> cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.getConstructor().newInstance();

        String metierClassName = scanner.nextLine();
        Class<?> cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor().newInstance();

        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
        setDao.invoke(metier, dao);

        System.out.println("Résultat: " + metier.calcul());
    }
}
```
##### En utilisant le Framework Spring

Dans cette section, nous utilisons le Framework Spring pour réaliser l'injection de dépendances dans notre application.

###### Version XML

Le Framework Spring permet une configuration des dépendances à l'aide de fichiers XML.
Dans ce fichier de configuration XML, nous définissons des beans pour les classes DaoImpl et MetierImpl, avec l'injection de dépendance de DaoImpl dans MetierImpl.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dao" class="dao.DaoImpl"></bean>
    <bean id="metier" class="metier.MetierImpl">
        <property name="dao" ref="dao"></property>
    </bean>
</beans>
```
Ensuite nous chargeons la configuration Spring à partir du fichier config.xml et récupérons un bean de type IMetier à partir du contexte Spring. Spring se charge alors d'instancier et d'injecter les dépendances appropriées pour nous.
```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresAbecSpringXML {
    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = springContext.getBean(IMetier.class);
        System.out.println("result = " + metier.calcul());
    }
}
```
##### Version annotations

Nous explorons l'utilisation des annotations dans le Framework Spring pour la configuration des dépendances.

Nous utilisons les annotations `@Component` et `@Repository` pour marquer la classe `DaoImpl` en tant que composant géré par Spring. De même, nous utilisons les annotations `@Component` et `@Service` pour marquer la classe `MetierImpl` en tant que service géré par Spring.

```java
package dao;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class DaoImpl implements IDao {
    public double getData() {
        System.out.println("version base de données ");
        double data = 10.0;
        return data;
    }
}
```

Dans MetierImpl, nous utilisons l'annotation `@Service `.
```java
package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetierImpl implements IMetier {
    private IDao dao;

    @Autowired
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double data = dao.getData();
        double res = data * 23;
        return res;
    }
}
```
Dans PresVersionSpringAnnotations, nous utilisons AnnotationConfigApplicationContext pour scanner les packages dao et metier et détecter automatiquement les composants annotés.
Cela nous permet d'injecter automatiquement les dépendances et de créer les instances nécessaires sans avoir besoin d'une configuration XML explicite.
```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresVersionSpringAnnotations {
    public static void main(String[] args) {
        ApplicationContext springContext = new AnnotationConfigApplicationContext("dao", "metier");
        IMetier metier = springContext.getBean(IMetier.class);
        System.out.println("result = " + metier.calcul());
    }
}
```










```

