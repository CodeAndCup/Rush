# Rush Plugin

Un plugin Minecraft recréant le mini-jeu **Rush** inspiré du design de **Funcraft**.

## 📋 Description

Rush est un mini-jeu PvP stratégique où deux équipes (Violette et Cyan) s'affrontent pour détruire le lit adverse et éliminer tous les joueurs ennemis. Ce plugin reproduit fidèlement le gameplay et le design du mode Rush de Funcraft.

## ✨ Fonctionnalités

### Système de Jeu
- **2 équipes** : Équipe Violette (Rouge) et Équipe Cyan (Bleu)
- **Protection du lit** : Tant que votre lit est intact, vous pouvez réapparaître
- **Destruction du lit** : Une fois détruit, vous ne pouvez plus réapparaître
- **Objectif** : Détruire le lit adverse et éliminer tous les joueurs ennemis
- **Bordure du monde** : Map limitée de 300x300 blocs centrée en (1024, 64, 1024)

### Phases de Jeu
1. **WAITING** : Attente des joueurs
2. **STARTING** : Démarrage de la partie
3. **PLAYING** : Partie en cours
4. **FINISH** : Fin de partie avec affichage des résultats

### Système de Points
- **Victoire** : +150 points bonus
- **Performance** : Calcul basé sur le temps de jeu, les kills et les morts
- **Formule** : `(temps + kills - morts) / 2 + bonus victoire`

### Magasins NPC
Le plugin inclut 5 villageois marchands :
- **Speed** : Potions de vitesse
- **Block** : Blocs de construction
- **Food** : Nourriture
- **Weapons** : Armes
- **Armors** : Armures

### Fonctionnalités Spéciales
- **Boussole** : Pointe vers les ennemis
- **Chemin** : Système de navigation
- **Scoreboard personnalisé** : Affichage des informations en temps réel
- **Préférences joueur** : Menu de configuration personnel
- **Sélecteur d'équipe** : Interface graphique pour choisir son équipe
- **TNT amélioré** : Détruit les lits et le grès, durabilité modifiée pour certains blocs
- **Blocs renforcés** : Durabilité augmentée à 300 pour la laine, verre, coffres piégés, bannières, lanternes marines et lits

### Base de Données
- Support **MySQL** et **SQLite**
- Pool de connexions **HikariCP**
- Sauvegarde des statistiques joueurs
- Configuration flexible via `config.yml`

### Intégration CloudNet
- Support de **CloudNet v3.4.3**
- Création automatique de nouveaux serveurs Rush
- Gestion dynamique des instances

## 🔧 Configuration Requise

### Serveur
- **Minecraft** : 1.8.8 (Spigot)
- **Java** : 8 ou supérieur

### Dépendances
- **NametagEdit** : 4.5.20 (pour les préfixes d'équipe)
- **Lombok** : 1.18.22
- **HikariCP** : 4.0.3
- **MySQL Connector** : 8.0.33 (si MySQL)
- **SQLite JDBC** : 3.42.0.0 (si SQLite)

## 📥 Installation

1. Téléchargez le fichier `Rush-1.0-SNAPSHOT.jar`
2. Placez-le dans le dossier `plugins/` de votre serveur
3. Installez **NametagEdit** dans le dossier `plugins/`
4. Démarrez le serveur pour générer les fichiers de configuration
5. Configurez `config.yml` selon vos besoins
6. Redémarrez le serveur

## ⚙️ Configuration

### config.yml

```yaml
# Configuration du jeu
game:
  max-players-per-team: 10

# Configuration de la base de données
# Type: MYSQL ou SQLITE
database:
  type: "SQLITE"

  # Paramètres MySQL (utilisés uniquement si type = MYSQL)
  mysql:
    host: "localhost"
    port: 3306
    database: "rush"
    username: "root"
    password: ""

  # Paramètres SQLite (utilisés uniquement si type = SQLITE)
  sqlite:
    filename: "rush_data.db"

  # Paramètres du pool de connexions
  pool:
    max-pool-size: 10
    min-idle: 2
    max-lifetime: 1800000
    connection-timeout: 5000
```

### Paramètres modifiables
- **max-players-per-team** : Nombre maximum de joueurs par équipe (défaut: 10)
- **database.type** : Type de base de données (MYSQL ou SQLITE)
- **database.mysql.*** : Paramètres de connexion MySQL
- **database.sqlite.filename** : Nom du fichier SQLite
- **database.pool.*** : Configuration du pool de connexions HikariCP

## 🎮 Commandes

| Commande | Alias | Description |
|----------|-------|-------------|
| `/admin` | `/debug` | Commandes administrateur |
| `/rush` | `/info`, `/information` | Informations sur le plugin |

## 🏗️ Structure du Projet

```
fr.perrier.rush
├── Main.java                 # Classe principale
├── api/                      # API interne
│   └── menu/                 # Système de menus
├── commands/                 # Commandes
│   ├── Admin.java
│   └── Rush.java
├── database/                 # Gestion base de données
│   └── DatabaseManager.java
├── game/                     # Logique de jeu
│   ├── BedEvents.java        # Gestion des lits et TNT
│   ├── GameStatus.java       # États de la partie
│   ├── Run.java              # Boucle de jeu
│   ├── Starting.java         # Démarrage
│   ├── Waiting.java          # Attente
│   ├── Win.java              # Victoire
│   └── npc/                  # Villageois marchands
│       ├── ForceVillagerTrade.java
│       └── type/
│           ├── Armors.java
│           ├── Block.java
│           ├── Food.java
│           ├── Speed.java
│           └── Weapons.java
├── listener/                 # Événements
│   ├── game/                 # Événements de jeu
│   │   ├── Chemin.java
│   │   ├── Compass.java
│   │   └── Kills.java
│   └── global/               # Événements globaux
│       ├── Cancel.java
│       ├── JoinAndLeave.java
│       ├── ScoreBoard.java
│       └── Tchat.java
├── menu/                     # Interfaces graphiques
│   ├── Preferences.java
│   └── TeamSelector.java
├── scoreboard/               # Système de scoreboard
│   ├── ObjectiveSign.java
│   ├── PersonalScoreboard.java
│   ├── Reflection.java
│   ├── ScoreboardManager.java
│   ├── ScoreboardTeam.java
│   ├── TeamHandler.java
│   └── VObjective.java
└── utils/                    # Utilitaires
    ├── BDD.java              # Gestion BDD
    ├── BedLocation.java      # Gestion positions des lits
    ├── BorderScreen.java     # Bordure écran
    ├── Heads.java            # Têtes personnalisées
    ├── InventoryUpdate.java  # MAJ inventaires
    ├── ItemBuilder.java      # Construction d'items
    ├── LocationUtils.java    # Utilitaires de position
    ├── ReflectionUtils.java  # Réflexion Java
    ├── Stuff.java            # Équipement
    └── Teams.java            # Gestion des équipes
```

## 🎨 Caractéristiques Techniques

### Optimisations
- **Pool de threads** : 16 threads pour les tâches asynchrones
- **Executor mono-thread** : Pour les tâches séquentielles
- **HikariCP** : Pool de connexions haute performance
- **Scoreboard custom** : Utilisation de packets NMS pour de meilleures performances

### Modification des Blocs
Le plugin modifie la durabilité de certains blocs via NMS (v1_8_R3) :
- Lanternes marines (sea_lantern) : 300
- Laine (wool) : 300
- Coffres piégés (trapped_chest) : 300
- Bannières (wall_banner, standing_banner) : 300
- Verre teinté (stained_glass) : 300
- Lits (bed) : 300

### Environnement
- Monde généré en dimension **THE_END** pour un terrain plat
- Suppression automatique des entités au démarrage
- Gestion de la bordure du monde

## 👤 Équipes

### Équipe Violette (Rose)
- Couleur : `§5` (Violet)
- Préfixe : `§5Violet `

### Équipe Cyan (Bleue)
- Couleur : `§3` (Cyan)
- Préfixe : `§3Cyan `

### États Spéciaux
- **Mort Violet** : `§5§o` (joueur mort, lit détruit)
- **Mort Cyan** : `§3§o` (joueur mort, lit détruit)
- **En attente** : `§7` (avant la partie)

## 📊 Système de Statistiques

Le plugin enregistre :
- Points des joueurs
- Nombre de kills
- Nombre de morts
- Temps de jeu
- Lits détruits par équipe

## 🔨 Compilation

```bash
mvn clean package
```

Le JAR sera généré dans `target/Rush-1.0-SNAPSHOT.jar`

## 📝 Version

**Version actuelle** : 1.0.6  
**Auteur** : PerrierBottle  
**Minecraft** : 1.8.8

## 📜 Crédits

- **Design original** : Funcraft (serveur Minecraft)
- **Développeur** : PerrierBottle
- Plugin recréé en respectant le gameplay et le design du mode Rush de Funcraft

## ⚠️ Notes

- Ce plugin est conçu pour fonctionner sur Spigot 1.8.8
- L'utilisation de NMS (net.minecraft.server.v1_8_R3) le rend spécifique à cette version
- NametagEdit est **obligatoire** pour le bon fonctionnement des préfixes d'équipe

## 🐛 Support

Ce projet est une archive et n'inclut pas de support officiel.
Vous avez liberté de l'utiliser et de le modifier à votre convenance.

---

© 2025 CupCode / PerrierBottle . Tous droits réservés.

*Pour information la map utilisée dans par ce plugin n'est pas disponible au téléchargement ici.*