# Activité pratique N°3 : Spring MVC, Thymeleaf et Spring Security

**Gestion de produits : application Web JEE avec Spring MVC, Spring Data JPA, Thymeleaf et Spring Security**

| Informations | Détails |
|---|---|
| **Module** | Java avancé |
| **Réalisé par** | Mina ELBILALY |
| **Date** | Novembre 2026 |
| **Environnement** | Visual Studio Code, JDK 17, Maven, Git / GitHub |
| **Technologies** | Spring Boot 3.5, Spring MVC, Spring Data JPA, Hibernate, Thymeleaf, Bootstrap, Spring Security, Spring Validation, H2, MySQL, Lombok |

---

# Sommaire

1. [Objectif du TP](#1-objectif-du-tp)
2. [Outils et technologies utilisés](#2-outils-et-technologies-utilisés)
3. [Création du projet](#3-création-du-projet)
4. [Architecture du projet](#4-architecture-du-projet)
5. [Entité JPA Product](#5-entité-jpa-product)
6. [Interface ProductRepository](#6-interface-productrepository)
7. [Test de la couche DAO](#7-test-de-la-couche-dao)
8. [Désactivation de la protection par défaut de Spring Security](#8-désactivation-de-la-protection-par-défaut-de-spring-security)
9. [Contrôleur Spring MVC et vues Thymeleaf](#9-contrôleur-spring-mvc-et-vues-thymeleaf)
10. [Sécurisation de l'application avec Spring Security](#10-sécurisation-de-lapplication-avec-spring-security)
11. [Fonctionnalités supplémentaires](#11-fonctionnalités-supplémentaires)
12. [Conclusion](#12-conclusion)

---

# 1. Objectif du TP

L'objectif de ce TP est de développer une **application Web JEE** de gestion de produits, basée sur Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf et Spring Security.

Contrairement au TP précédent, où les résultats s'affichaient dans le terminal, l'application dispose maintenant d'une **interface Web** : les produits sont affichés dans un navigateur, et l'accès aux pages est protégé par une authentification.

Les objectifs de ce TP sont :

- Créer un projet Spring Boot avec les dépendances Spring Web, Spring Data JPA, H2, MySQL, Thymeleaf, Lombok, Spring Security et Spring Validation.
- Créer l'entité JPA `Product` et l'interface `ProductRepository`.
- Tester la couche DAO.
- Créer un contrôleur Spring MVC et des vues Thymeleaf pour afficher, supprimer et ajouter des produits.
- Utiliser un **template** (Thymeleaf Layout) avec **Bootstrap**.
- Valider les données saisies dans le formulaire.
- Sécuriser l'application avec Spring Security (authentification et rôles).
- Ajouter des fonctionnalités supplémentaires : recherche, édition et pagination.

**Principe général :** l'application suit le modèle **MVC** (Modèle - Vue - Contrôleur). Le contrôleur reçoit les requêtes HTTP, utilise le repository pour accéder aux données, puis transmet le résultat à une vue Thymeleaf qui génère la page HTML.

# 2. Outils et technologies utilisés

Pour réaliser ce TP, j'ai utilisé les outils et technologies suivants :

| Outil / Technologie | Rôle |
|---|---|
| **Visual Studio Code** | Écrire et organiser le code Java et les pages HTML. |
| **JDK 17** | Compiler et exécuter le programme Java. |
| **Maven** | Gérer les bibliothèques et les dépendances du projet. |
| **Spring Boot 3.5** | Faciliter la création et le démarrage de l'application. |
| **Spring MVC** | Gérer les requêtes HTTP grâce aux contrôleurs. |
| **Spring Data JPA / Hibernate** | Simplifier l'accès à la base de données. |
| **Thymeleaf** | Générer les pages HTML dynamiques côté serveur. |
| **Thymeleaf Layout Dialect** | Créer une page template réutilisable. |
| **Bootstrap** | Mettre en forme les pages (boutons, tableaux, formulaires). |
| **Spring Security** | Authentifier les utilisateurs et gérer leurs droits d'accès. |
| **Spring Validation** | Contrôler les données saisies dans les formulaires. |
| **H2 Database** | Base de données en mémoire utilisée pour les tests. |
| **MySQL** | Base de données relationnelle utilisable en production. |
| **Lombok** | Réduire le code répétitif dans les classes Java. |
| **Git / GitHub** | Enregistrer et partager le code du projet. |

# 3. Création du projet

J'ai créé un projet Maven nommé `product-mvc`, configuré comme un projet Spring Boot grâce à son fichier `pom.xml`.

J'ai choisi les dépendances suivantes :

| Dépendance | Utilité |
|---|---|
| `spring-boot-starter-web` | Permet de développer une application Web avec Spring MVC. |
| `spring-boot-starter-data-jpa` | Permet de manipuler les données avec JPA. |
| `h2` | Fournit la base de données H2 en mémoire. |
| `mysql-connector-j` | Fournit le pilote pour se connecter à MySQL. |
| `spring-boot-starter-thymeleaf` | Permet de créer les vues avec Thymeleaf. |
| `thymeleaf-layout-dialect` | Permet d'utiliser une page template. |
| `spring-boot-starter-security` | Permet de sécuriser l'application. |
| `thymeleaf-extras-springsecurity6` | Permet d'utiliser Spring Security dans les pages Thymeleaf. |
| `spring-boot-starter-validation` | Permet de valider les données des formulaires. |
| `lombok` | Génère automatiquement certains éléments du code Java. |
| `bootstrap` et `bootstrap-icons` (WebJars) | Fournissent le style des pages. |

### Fichier pom.xml

Voici le fichier `pom.xml` complet :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.16</version>
        <relativePath/>
    </parent>

    <groupId>ma.tp</groupId>
    <artifactId>product-mvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>product-mvc</name>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>nz.net.ultraq.thymeleaf</groupId>
            <artifactId>thymeleaf-layout-dialect</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap</artifactId>
            <version>5.3.3</version>
        </dependency>
        <dependency>
            <groupId>org.webjars</groupId>
            <artifactId>bootstrap-icons</artifactId>
            <version>1.11.3</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

**Explication :** Maven utilise ce fichier pour télécharger et gérer automatiquement les bibliothèques nécessaires au projet. Les versions des bibliothèques Spring sont gérées par le parent `spring-boot-starter-parent`.
<img width="890" height="646" alt="image" src="https://github.com/user-attachments/assets/79507dc6-11f9-41ea-8828-a1120ccd753b" />


*Figure 1 : Fichier `pom.xml` avec les dépendances du projet.*

---

# 4. Architecture du projet

J'ai organisé le projet en plusieurs dossiers selon le modèle MVC.

Voici sa structure :

```text
product-mvc/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── ma/tp/productmvc/
        │       ├── ProductMvcApplication.java
        │       ├── entities/
        │       │   └── Product.java
        │       ├── repository/
        │       │   └── ProductRepository.java
        │       ├── web/
        │       │   ├── ProductController.java
        │       │   └── SecurityController.java
        │       └── security/
        │           └── SecurityConfig.java
        │
        └── resources/
            ├── application.properties
            └── templates/
                ├── template1.html
                ├── products.html
                ├── newProduct.html
                ├── editProduct.html
                ├── login.html
                └── notAuthorized.html
```

### Rôle des fichiers

| Fichier | Rôle |
|---|---|
| `ProductMvcApplication.java` | Démarre l'application et teste la couche DAO. |
| `Product.java` | Représente un produit dans la base de données. |
| `ProductRepository.java` | Fournit les méthodes pour manipuler les produits. |
| `ProductController.java` | Contrôleur Spring MVC : traite les requêtes sur les produits. |
| `SecurityController.java` | Affiche les pages de connexion et d'accès refusé. |
| `SecurityConfig.java` | Configure Spring Security (utilisateurs, rôles, accès). |
| `template1.html` | Page template commune (barre de navigation, styles). |
| `products.html` | Vue qui affiche la liste des produits. |
| `newProduct.html` | Vue du formulaire d'ajout d'un produit. |
| `editProduct.html` | Vue du formulaire de modification d'un produit. |
| `login.html` | Page de connexion. |
| `notAuthorized.html` | Page affichée en cas d'accès refusé. |
| `application.properties` | Contient la configuration de la base de données. |

**Explication :** Cette organisation sépare les responsabilités : les données (`entities`, `repository`), le traitement des requêtes (`web`), la sécurité (`security`) et l'affichage (`templates`).

<img width="320" height="527" alt="image" src="https://github.com/user-attachments/assets/e3fc92f1-84cd-4154-b892-82de59b54c7b" />


*Figure 2 : Arborescence du projet dans Visual Studio Code.*

---

# 5. Entité JPA Product

### 5.1. Présentation

J'ai créé une classe appelée `Product` qui représente un produit dans mon application.

Grâce à l'annotation `@Entity`, cette classe est associée à une table dans la base de données.

Elle contient quatre attributs :

| Attribut | Type | Description |
|---|---|---|
| `id` | Long | Identifiant unique du produit. |
| `name` | String | Nom du produit (3 à 50 caractères). |
| `price` | double | Prix du produit (positif ou nul). |
| `quantity` | int | Quantité disponible (au moins 1). |

### 5.2. Code Product.java

```java
package ma.tp.productmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom doit contenir entre 3 et 50 caractères")
    private String name;

    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    private double price;

    @Min(value = 1, message = "La quantité doit être au moins égale à 1")
    private int quantity;
}
```

### 5.3. Explication des annotations

| Annotation | Explication |
|---|---|
| `@Entity` | Indique que la classe représente une entité de la base de données. |
| `@Id` | Définit l'identifiant comme clé primaire. |
| `@GeneratedValue` | Permet de générer automatiquement l'identifiant. |
| `@NotEmpty` | Interdit un champ vide (validation). |
| `@Size` | Limite le nombre de caractères du champ (validation). |
| `@Min` | Impose une valeur minimale (validation). |
| `@Data` | Génère les getters, setters et autres méthodes utiles. |
| `@NoArgsConstructor` | Génère un constructeur sans paramètres. |
| `@AllArgsConstructor` | Génère un constructeur avec tous les attributs. |
| `@Builder` | Facilite la création des objets avec le modèle Builder. |

**Explication :** Les annotations de validation (`@NotEmpty`, `@Size`, `@Min`) seront utilisées plus tard pour contrôler les données saisies dans le formulaire d'ajout.

<img width="693" height="650" alt="image" src="https://github.com/user-attachments/assets/aa6e3bb2-616f-41a5-ac15-81d80763ced0" />


*Figure 3 : Classe Product avec les annotations JPA, Lombok et de validation.*

---

# 6. Interface ProductRepository

### 6.1. Présentation

Après la création de l'entité Product, j'ai créé une interface appelée `ProductRepository`.

Elle hérite de `JpaRepository`, fourni par Spring Data JPA, et fournit automatiquement les opérations d'accès aux données.

### 6.2. Code ProductRepository.java

```java
package ma.tp.productmvc.repository;

import ma.tp.productmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### 6.3. Explication

Dans `JpaRepository<Product, Long>` :

- `Product` représente l'entité que je veux manipuler.
- `Long` représente le type de son identifiant.

Spring Data JPA fournit automatiquement les méthodes suivantes :

| Méthode | Rôle |
|---|---|
| `save()` | Ajouter ou modifier un produit. |
| `findAll()` | Consulter tous les produits. |
| `findById()` | Consulter un produit par son identifiant. |
| `deleteById()` | Supprimer un produit par son identifiant. |

Je n'ai donc pas besoin d'écrire leur implémentation.
<img width="789" height="336" alt="image" src="https://github.com/user-attachments/assets/73f8e893-3497-4d87-8c60-24200a1320dc" />


*Figure 4 : Interface ProductRepository.*

---

# 7. Test de la couche DAO

### 7.1. Configuration de la base de données

J'ai d'abord configuré la connexion à la base H2 dans le fichier `application.properties`, situé dans le dossier `resources`.

```properties
spring.application.name=product-mvc

# Base H2 en mémoire
spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuration JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

# --- Configuration MySQL (alternative à H2) ---
# spring.datasource.url=jdbc:mysql://localhost:3306/products-db?createDatabaseIfNotExist=true
# spring.datasource.username=root
# spring.datasource.password=
# spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

| Propriété | Explication |
|---|---|
| `spring.datasource.url` | Définit l'adresse de la base H2 (en mémoire : les données disparaissent à l'arrêt). |
| `spring.jpa.hibernate.ddl-auto=update` | Permet à Hibernate de créer ou de mettre à jour les tables. |
| `spring.jpa.show-sql=true` | Affiche les requêtes SQL dans le terminal. |
| Lignes commentées | Permettent de passer à MySQL en les activant et en commentant la partie H2. |

<img width="539" height="113" alt="image" src="https://github.com/user-attachments/assets/f04408d3-c9d1-490a-a9a3-4d0ad2fb3671" />


*Figure 5 : Fichier application.properties.*

### 7.2. Code de test dans ProductMvcApplication.java

Pour tester la couche DAO, j'ai utilisé un `CommandLineRunner` qui s'exécute au démarrage de l'application.

```java
package ma.tp.productmvc;

import ma.tp.productmvc.entities.Product;
import ma.tp.productmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {

            // Ajout de produits
            productRepository.save(new Product(null, "Ordinateur", 8000, 10));
            productRepository.save(new Product(null, "Imprimante", 1500, 25));
            productRepository.save(new Product(null, "Smartphone", 5000, 30));
            productRepository.save(new Product(null, "Souris", 100, 80));

            // Consultation de tous les produits
            System.out.println("===== TOUS LES PRODUITS =====");
            productRepository.findAll().forEach(p -> System.out.println(p));

            // Consultation d'un produit
            System.out.println("===== PRODUIT (id = 1) =====");
            Product product = productRepository.findById(1L).orElse(null);
            if (product != null) {
                System.out.println("Id       : " + product.getId());
                System.out.println("Nom      : " + product.getName());
                System.out.println("Prix     : " + product.getPrice());
                System.out.println("Quantité : " + product.getQuantity());
            }
        };
    }
}
```

**Résultat :** Au démarrage, quatre produits sont enregistrés, puis la liste complète et le produit n° 1 sont affichés dans le terminal.

<img width="926" height="663" alt="image" src="https://github.com/user-attachments/assets/6eb21df6-df13-49d3-9623-3e11b4166b7c" />


*Figure 6 : Résultat du test de la couche DAO.*

---

# 8. Désactivation de la protection par défaut de Spring Security

### 8.1. Présentation

Dès que la dépendance Spring Security est ajoutée, **toutes les pages sont protégées** : Spring génère une page de connexion et un mot de passe aléatoire affiché dans le terminal.

Pour développer et tester les pages sans cette contrainte, j'ai d'abord **désactivé cette protection** en créant une classe de configuration qui autorise toutes les requêtes.

### 8.2. Code SecurityConfig.java (version provisoire)

```java
package ma.tp.productmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(ar -> ar.anyRequest().permitAll());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
```

### 8.3. Explication

| Instruction | Explication |
|---|---|
| `@EnableWebSecurity` | Active la configuration personnalisée de Spring Security. |
| `SecurityFilterChain` | Définit les règles de sécurité appliquées aux requêtes. |
| `anyRequest().permitAll()` | Autorise l'accès à toutes les pages sans authentification. |
| `csrf.disable()` | Désactive la protection CSRF (provisoire, pour les tests). |

**Remarque :** cette configuration est **provisoire**. Elle sera remplacée à l'étape 10 par une vraie configuration de sécurité.
<img width="771" height="662" alt="image" src="https://github.com/user-attachments/assets/bdbb5185-0f45-43f6-9fc7-08321f58a2cd" />


*Figure 7 : Classe SecurityConfig avec `permitAll()`.*

---

# 9. Contrôleur Spring MVC et vues Thymeleaf

### 9.1. Présentation

Dans cette étape, j'ai créé le contrôleur et les vues pour :

- afficher la liste des produits ;
- supprimer un produit ;
- utiliser une page template basée sur Thymeleaf Layout et Bootstrap ;
- saisir et ajouter un produit avec validation du formulaire.

Les URL commencent par `/user` pour la consultation et `/admin` pour les opérations de modification. Ce choix servira à l'étape 10 pour donner des droits différents aux utilisateurs.

**Remarque sur les captures :** les boutons d'administration (menu **Nouveau produit**, colonne **Actions**, bouton **Supprimer**) ne s'affichent que pour un utilisateur ayant le rôle `ADMIN`. Les captures des figures 10 à 15 ont donc été réalisées une fois l'application sécurisée (étape 10), connectée avec l'utilisateur `admin`.

### 9.2. Code ProductController.java

```java
package ma.tp.productmvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.tp.productmvc.entities.Product;
import ma.tp.productmvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private ProductRepository productRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    // Afficher la liste des produits
    @GetMapping("/user/index")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("productList", products);
        return "products";
    }

    // Supprimer un produit
    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/admin/newProduct")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "newProduct";
    }

    // Enregistrer un nouveau produit (avec validation)
    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newProduct";
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }
}
```

### 9.3. Explication des annotations

| Annotation | Explication |
|---|---|
| `@Controller` | Indique que la classe est un contrôleur Spring MVC. |
| `@AllArgsConstructor` | Lombok génère le constructeur qui injecte le repository. |
| `@GetMapping` | Associe une méthode à une requête HTTP GET. |
| `@PostMapping` | Associe une méthode à une requête HTTP POST (envoi d'un formulaire). |
| `@RequestParam` | Récupère un paramètre de l'URL (par exemple l'identifiant du produit). |
| `@Valid` | Déclenche la validation des attributs de l'objet `Product`. |
| `BindingResult` | Contient les erreurs de validation éventuelles. |
| `Model` | Transmet les données du contrôleur vers la vue. |

<img width="792" height="660" alt="image" src="https://github.com/user-attachments/assets/f8542736-0b5e-40f6-a023-8e6702e7137b" />


*Figure 8 : Classe ProductController.*

### 9.4. Page template (Thymeleaf Layout et Bootstrap)

Pour que toutes les pages aient le même aspect, j'ai créé une **page template** `template1.html`. Elle contient la barre de navigation. Chaque autre page « hérite » de ce template et ne définit que son contenu.

**Fichier :** `src/main/resources/templates/template1.html`

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des produits</title>
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.3.3/css/bootstrap.min.css}">
    <link rel="stylesheet" th:href="@{/webjars/bootstrap-icons/1.11.3/font/bootstrap-icons.css}">
    <script th:src="@{/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js}"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" th:href="@{/}">Gestion des produits</a>

        <ul class="navbar-nav me-auto">
            <li class="nav-item" sec:authorize="hasRole('USER')">
                <a class="nav-link" th:href="@{/user/index}">Produits</a>
            </li>
            <li class="nav-item" sec:authorize="hasRole('ADMIN')">
                <a class="nav-link" th:href="@{/admin/newProduct}">Nouveau produit</a>
            </li>
        </ul>

        <ul class="navbar-nav" sec:authorize="isAuthenticated()">
            <li class="nav-item d-flex align-items-center me-3 text-white">
                <i class="bi bi-person-circle me-1"></i>
                <span sec:authentication="name"></span>
            </li>
            <li class="nav-item">
                <form th:action="@{/logout}" method="post">
                    <button type="submit" class="btn btn-light btn-sm">Déconnexion</button>
                </form>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <section layout:fragment="content1"></section>
</div>

</body>
</html>
```

| Élément | Explication |
|---|---|
| `xmlns:layout` | Active le Thymeleaf Layout Dialect. |
| `xmlns:sec` | Active les attributs Spring Security (`sec:authorize`, `sec:authentication`). |
| `th:href="@{...}"` | Construit une URL correcte à partir du contexte de l'application. |
| `layout:fragment="content1"` | Zone qui sera remplacée par le contenu de chaque page. |
| `sec:authorize` | Affiche l'élément seulement si l'utilisateur a le rôle demandé. |
| Classes `navbar`, `container`, `btn` | Classes Bootstrap pour la mise en forme. |

<img width="857" height="667" alt="image" src="https://github.com/user-attachments/assets/b4817baf-e4cb-4fbe-afd2-ae7dcd797b48" />


*Figure 9 : Fichier template1.html.*

### 9.5. Affichage de la liste des produits

**Fichier :** `src/main/resources/templates/products.html`

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
      layout:decorate="~{template1}">
<head>
    <title>Liste des produits</title>
</head>
<body>
<div layout:fragment="content1">
    <div class="card">
        <div class="card-header">Liste des produits</div>
        <div class="card-body">

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Prix</th>
                        <th>Quantité</th>
                        <th sec:authorize="hasRole('ADMIN')">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr th:each="p : ${productList}">
                        <td th:text="${p.id}"></td>
                        <td th:text="${p.name}"></td>
                        <td th:text="${p.price}"></td>
                        <td th:text="${p.quantity}"></td>
                        <td sec:authorize="hasRole('ADMIN')">
                            <a class="btn btn-danger btn-sm"
                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')"
                               th:href="@{/admin/delete(id=${p.id})}">
                                <i class="bi bi-trash"></i> Supprimer
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
    </div>
</div>
</body>
</html>
```

| Élément | Explication |
|---|---|
| `layout:decorate="~{template1}"` | Cette page utilise le template `template1.html`. |
| `th:each="p : ${productList}"` | Parcourt la liste envoyée par le contrôleur et crée une ligne par produit. |
| `th:text="${p.name}"` | Affiche la valeur de l'attribut dans la cellule. |
| `class="table table-striped"` | Classes Bootstrap pour un tableau à lignes alternées. |

**Résultat :** En ouvrant `http://localhost:8080/user/index`, la liste des quatre produits s'affiche dans un tableau.

<img width="883" height="491" alt="image" src="https://github.com/user-attachments/assets/1cdf3707-48b1-4058-af83-72adf7919503" />
<img width="1303" height="476" alt="image" src="https://github.com/user-attachments/assets/8f5f0ace-1fff-491a-900b-a351cdfa0514" />



*Figure 10 : Page d'affichage de la liste des produits.*

### 9.6. Suppression d'un produit

Chaque ligne du tableau contient un bouton **Supprimer**. Il appelle l'URL `/admin/delete?id=...`, et le contrôleur exécute `deleteById(id)`. Une fenêtre de confirmation JavaScript (`confirm`) évite les suppressions accidentelles. Après la suppression, l'utilisateur est redirigé vers la liste (`redirect:/user/index`).

**Remarque :** pour simplifier, la suppression est déclenchée par un lien (requête GET). Dans une application en production, une suppression devrait plutôt utiliser une requête POST, protégée par le jeton CSRF.

<img width="1303" height="476" alt="image" src="https://github.com/user-attachments/assets/af0bb206-38dc-4541-9553-7df8738459bd" />


*Figure 11 : Fenêtre de confirmation avant la suppression.*

<img width="1271" height="413" alt="image" src="https://github.com/user-attachments/assets/c416258d-4c47-417b-97ad-f31b61536080" />


*Figure 12 : Liste des produits après la suppression d'un produit.*

### 9.7. Saisie et ajout d'un produit avec validation

**Fichier :** `src/main/resources/templates/newProduct.html`

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{template1}">
<head>
    <title>Nouveau produit</title>
</head>
<body>
<div layout:fragment="content1">
    <div class="col-md-6 offset-md-3">
        <div class="card">
            <div class="card-header">Nouveau produit</div>
            <div class="card-body">

                <form th:action="@{/admin/saveProduct}" method="post" th:object="${product}">

                    <div class="mb-3">
                        <label class="form-label">Nom</label>
                        <input type="text" class="form-control" th:field="*{name}">
                        <span class="text-danger" th:errors="*{name}"></span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Prix</label>
                        <input type="number" step="0.01" class="form-control" th:field="*{price}">
                        <span class="text-danger" th:errors="*{price}"></span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Quantité</label>
                        <input type="number" class="form-control" th:field="*{quantity}">
                        <span class="text-danger" th:errors="*{quantity}"></span>
                    </div>

                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
```

| Élément | Explication |
|---|---|
| `th:object="${product}"` | Associe le formulaire à l'objet `Product` envoyé par le contrôleur. |
| `th:field="*{name}"` | Lie le champ de saisie à l'attribut `name` de l'objet. |
| `th:errors="*{name}"` | Affiche le message d'erreur de validation du champ, s'il existe. |

**Fonctionnement :** quand je clique sur **Enregistrer**, Spring crée l'objet `Product` à partir des valeurs saisies. L'annotation `@Valid` vérifie les contraintes (`@NotEmpty`, `@Size`, `@Min`). S'il y a des erreurs, le formulaire est réaffiché avec les messages. Sinon, le produit est enregistré.

<img width="983" height="494" alt="image" src="https://github.com/user-attachments/assets/55a6b436-b311-4a29-bdb9-1e71fdfd45e9" />


*Figure 13 : Formulaire de saisie d'un nouveau produit.*

<img width="618" height="447" alt="image" src="https://github.com/user-attachments/assets/26916225-a959-41e4-ba87-f7f5b866af51" />


*Figure 14 : Messages d'erreur affichés lorsque les données saisies sont invalides.*

<img width="1036" height="470" alt="image" src="https://github.com/user-attachments/assets/f83820a9-d9c5-4b98-9b3e-8dffa2c02709" />


*Figure 15 : Liste des produits après l'ajout d'un nouveau produit.*

---

# 10. Sécurisation de l'application avec Spring Security

### 10.1. Présentation

Dans cette étape, j'ai remplacé la configuration provisoire par une **vraie configuration de sécurité** :

- les utilisateurs doivent **se connecter** pour accéder aux pages ;
- deux utilisateurs sont définis avec des **rôles** différents ;
- les pages `/user/**` sont accessibles aux utilisateurs ayant le rôle `USER` ;
- les pages `/admin/**` (ajout, suppression, modification) sont réservées au rôle `ADMIN`.

| Utilisateur | Mot de passe | Rôles |
|---|---|---|
| `user1` | `1234` | USER |
| `admin` | `1234` | USER, ADMIN |

### 10.2. Code SecurityConfig.java (version finale)

```java
package ma.tp.productmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER", "ADMIN")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll());

        http.authorizeHttpRequests(ar -> ar
                .requestMatchers("/login", "/webjars/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated());

        http.exceptionHandling(eh -> eh.accessDeniedPage("/notAuthorized"));

        return http.build();
    }
}
```

### 10.3. Explication

| Élément | Explication |
|---|---|
| `BCryptPasswordEncoder` | Chiffre les mots de passe : ils ne sont jamais stockés en clair. |
| `InMemoryUserDetailsManager` | Définit les utilisateurs et leurs rôles en mémoire. |
| `formLogin(...loginPage("/login"))` | Utilise ma page de connexion personnalisée. |
| `requestMatchers("/admin/**").hasRole("ADMIN")` | Réserve les pages `/admin` aux administrateurs. |
| `anyRequest().authenticated()` | Toute autre page exige une connexion. |
| `accessDeniedPage("/notAuthorized")` | Page affichée quand un utilisateur n'a pas les droits. |
| CSRF | Je n'ai plus désactivé la protection CSRF : Thymeleaf ajoute automatiquement le jeton dans les formulaires (`th:action`). |


### 10.4. Contrôleur de sécurité

**Fichier :** `src/main/java/ma/tp/productmvc/web/SecurityController.java`

```java
package ma.tp.productmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }
}
```

### 10.5. Pages de connexion et d'accès refusé

**Fichier :** `src/main/resources/templates/login.html`

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{template1}">
<head>
    <title>Connexion</title>
</head>
<body>
<div layout:fragment="content1">
    <div class="col-md-4 offset-md-4">
        <div class="card">
            <div class="card-header">Connexion</div>
            <div class="card-body">

                <div class="alert alert-danger" th:if="${param.error}">
                    Nom d'utilisateur ou mot de passe incorrect.
                </div>

                <form th:action="@{/login}" method="post">
                    <div class="mb-3">
                        <label class="form-label">Nom d'utilisateur</label>
                        <input type="text" name="username" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mot de passe</label>
                        <input type="password" name="password" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-primary">Se connecter</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
```

**Fichier :** `src/main/resources/templates/notAuthorized.html`

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{template1}">
<head>
    <title>Accès refusé</title>
</head>
<body>
<div layout:fragment="content1">
    <div class="alert alert-danger">
        <h4>Accès refusé</h4>
        <p>Vous n'avez pas les droits nécessaires pour accéder à cette page.</p>
        <a class="btn btn-primary" th:href="@{/}">Retour à l'accueil</a>
    </div>
</div>
</body>
</html>
```

### 10.6. Mise à jour de la vue products.html

La liste affiche déjà les boutons d'administration seulement aux utilisateurs ayant le rôle `ADMIN`, grâce à `sec:authorize="hasRole('ADMIN')"`.


# 11. Fonctionnalités supplémentaires

J'ai ajouté trois fonctionnalités : la **recherche** de produits, l'**édition** (modification) d'un produit et la **pagination** de la liste.

### 11.1. Recherche des produits

**Repository.** J'ai ajouté une méthode de recherche par nom dans `ProductRepository`. Elle ignore la différence entre majuscules et minuscules, et retourne une page de résultats :

```java
package ma.tp.productmvc.repository;

import ma.tp.productmvc.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainsIgnoreCase(String keyword, Pageable pageable);
}
```

**Contrôleur.** La méthode `index` reçoit maintenant le mot-clé et le numéro de page. Elle remplace la version de l'étape 9 :

```java
@GetMapping("/user/index")
public String index(Model model,
                    @RequestParam(name = "keyword", defaultValue = "") String keyword,
                    @RequestParam(name = "page", defaultValue = "0") int page,
                    @RequestParam(name = "size", defaultValue = "5") int size) {

    Page<Product> pageProducts = productRepository
            .findByNameContainsIgnoreCase(keyword, PageRequest.of(page, size));

    model.addAttribute("productList", pageProducts.getContent());
    model.addAttribute("pages", new int[pageProducts.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    return "products";
}
```

Imports à ajouter dans le contrôleur :

```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
```

**Vue.** J'ai ajouté un formulaire de recherche au-dessus du tableau dans `products.html` :

```html
<form method="get" th:action="@{/user/index}" class="row g-2 mb-3">
    <div class="col-auto">
        <input type="text" name="keyword" class="form-control"
               placeholder="Rechercher un produit..." th:value="${keyword}">
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary">
            <i class="bi bi-search"></i> Rechercher
        </button>
    </div>
</form>
```

**Résultat :** en saisissant un mot dans le champ puis en cliquant sur **Rechercher**, seuls les produits dont le nom contient ce mot sont affichés.

<img width="870" height="240" alt="image" src="https://github.com/user-attachments/assets/1a967dca-5f1b-47ea-87c2-f81aa25880ad" />


*Figure 21 : Résultat de la recherche d'un produit par son nom.*

### 11.2. Pagination

Pour ne pas afficher tous les produits sur une seule page, j'ai ajouté une **pagination** (5 produits par page). Le contrôleur ci-dessus envoie déjà le nombre de pages (`pages`) et la page courante (`currentPage`).

Dans `products.html`, sous le tableau, j'ai ajouté la barre de pages :

```html
<ul class="nav nav-pills">
    <li th:each="p, status : ${pages}">
        <a th:href="@{/user/index(page=${status.index}, keyword=${keyword})}"
           th:text="${status.index + 1}"
           th:class="${status.index == currentPage} ? 'btn btn-primary ms-1' : 'btn btn-outline-primary ms-1'">
        </a>
    </li>
</ul>
```

Je mets aussi à jour le bouton **Supprimer** pour revenir à la même page et conserver la recherche :

```html
<a class="btn btn-danger btn-sm"
   onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')"
   th:href="@{/admin/delete(id=${p.id}, keyword=${keyword}, page=${currentPage})}">
    <i class="bi bi-trash"></i> Supprimer
</a>
```

et la méthode `delete` du contrôleur :

```java
@GetMapping("/admin/delete")
public String delete(@RequestParam(name = "id") Long id,
                     @RequestParam(name = "keyword", defaultValue = "") String keyword,
                     @RequestParam(name = "page", defaultValue = "0") int page) {
    productRepository.deleteById(id);
    return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
}
```

| Élément | Explication |
|---|---|
| `PageRequest.of(page, size)` | Demande une page précise avec un nombre d'éléments par page. |
| `Page<Product>` | Contient les produits de la page, et le nombre total de pages. |
| `new int[totalPages]` | Tableau utilisé par Thymeleaf pour créer un bouton par page. |
| `th:class="... ? ... : ..."` | Met en évidence la page courante. |



### 11.3. Édition et mise à jour d'un produit

**Contrôleur.** J'ai ajouté deux méthodes : l'une affiche le formulaire pré-rempli, l'autre enregistre les modifications.

```java
// Afficher le formulaire de modification
@GetMapping("/admin/editProduct")
public String editProduct(Model model, @RequestParam(name = "id") Long id) {
    Product product = productRepository.findById(id).orElse(null);
    if (product == null) {
        throw new RuntimeException("Produit introuvable");
    }
    model.addAttribute("product", product);
    return "editProduct";
}

// Enregistrer les modifications (avec validation)
@PostMapping("/admin/updateProduct")
public String updateProduct(@Valid Product product, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "editProduct";
    }
    productRepository.save(product);
    return "redirect:/user/index";
}
```

La méthode `save()` met à jour le produit, car l'identifiant existe déjà (il est envoyé par un champ caché du formulaire).

**Vue.** Fichier `src/main/resources/templates/editProduct.html` :

```html
<!DOCTYPE html>
<html lang="fr"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{template1}">
<head>
    <title>Modifier un produit</title>
</head>
<body>
<div layout:fragment="content1">
    <div class="col-md-6 offset-md-3">
        <div class="card">
            <div class="card-header">Modifier le produit</div>
            <div class="card-body">

                <form th:action="@{/admin/updateProduct}" method="post" th:object="${product}">

                    <input type="hidden" th:field="*{id}">

                    <div class="mb-3">
                        <label class="form-label">Nom</label>
                        <input type="text" class="form-control" th:field="*{name}">
                        <span class="text-danger" th:errors="*{name}"></span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Prix</label>
                        <input type="number" step="0.01" class="form-control" th:field="*{price}">
                        <span class="text-danger" th:errors="*{price}"></span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Quantité</label>
                        <input type="number" class="form-control" th:field="*{quantity}">
                        <span class="text-danger" th:errors="*{quantity}"></span>
                    </div>

                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
```

**Bouton Modifier.** Dans `products.html`, j'ai ajouté le bouton dans la colonne **Actions**, à côté du bouton **Supprimer** :

```html
<a class="btn btn-warning btn-sm"
   th:href="@{/admin/editProduct(id=${p.id})}">
    <i class="bi bi-pencil"></i> Modifier
</a>
```


### 11.4. Récapitulatif des fonctionnalités

| Fonctionnalité | URL | Rôle requis |
|---|---|---|
| Afficher la liste (avec recherche et pagination) | `/user/index` | USER |
| Supprimer un produit | `/admin/delete` | ADMIN |
| Afficher le formulaire d'ajout | `/admin/newProduct` | ADMIN |
| Enregistrer un nouveau produit | `/admin/saveProduct` | ADMIN |
| Afficher le formulaire de modification | `/admin/editProduct` | ADMIN |
| Enregistrer les modifications | `/admin/updateProduct` | ADMIN |
| Page de connexion | `/login` | Aucun |

---

# 12. Conclusion

Ce TP m'a permis de mettre en pratique la création d'une **application Web complète** avec Spring Boot, du stockage des données jusqu'à l'interface utilisateur.

J'ai appris à :

- créer un projet Spring Boot avec plusieurs dépendances (Web, JPA, Thymeleaf, Security, Validation) ;
- utiliser Spring MVC pour traiter les requêtes HTTP avec un contrôleur ;
- créer des vues dynamiques avec Thymeleaf et une page template avec Bootstrap ;
- valider les données saisies grâce aux annotations de Spring Validation ;
- sécuriser l'application avec Spring Security, en distinguant les droits des utilisateurs (`USER` et `ADMIN`) ;
- ajouter des fonctionnalités de recherche, de pagination et de modification.

**En conclusion, Spring Boot associé à Spring Data JPA, Thymeleaf et Spring Security permet de développer rapidement une application Web structurée, sécurisée et facile à maintenir, en séparant clairement les données, le traitement et l'affichage.**

---

# Dépôt du travail

**Dépôt GitHub :** https://github.com/minaelbilaly/product-mvc
