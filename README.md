# Inversion de contrôle et Injection des dépendances
## Partie 1
### Création de l'interface IDao avec une méthode getDate
La première étape consiste à créer l'interface IDao. Cette interface définira la structure pour accéder aux données dans notre application.
```java 
package dao;

public interface IDao {
    double getData();
}
```
