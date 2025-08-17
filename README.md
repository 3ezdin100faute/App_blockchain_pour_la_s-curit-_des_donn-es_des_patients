# 🏥 App Blockchain pour la Sécurité des Données des Patients

## 📌 Description du projet
Ce projet a pour objectif de garantir la **sécurité et la traçabilité des données médicales des patients** à travers une application basée sur la **blockchain Ethereum**.  

Les ordonnances médicales sont validées via des **smart contracts Solidity**, ce qui permet de vérifier la compatibilité entre une maladie et un médicament avant de les enregistrer dans la blockchain.  
L’architecture repose sur un système de **microservices** afin de séparer les responsabilités (patient, docteur, pharmacie, infirmier, smart contract, intelligence artificielle).

---

## 🚀 Fonctionnalités principales
- 👨‍⚕️ **Gestion des patients** : enregistrement, modification et consultation sécurisée.  
- 📋 **Création d’ordonnances médicales** : ajout par les docteurs.  
- ✅ **Vérification de compatibilité maladie-médicament** via IA + blockchain.  
- 🔒 **Stockage sécurisé** des ordonnances validées dans la blockchain Ethereum.  
- 💊 **Consultation par les pharmacies** des ordonnances validées.  
- 🗄️ **Sauvegarde parallèle en base MySQL** pour consultation rapide.  

---

## 🏗️ Architecture
L’application repose sur une architecture distribuée composée de plusieurs microservices :

- **Backend (Spring Boot + Web3j)** : communication avec la blockchain.  
- **Frontend (Angular)** : interface utilisateur pour médecins, patients et pharmacies.  
- **Smart Contract (Solidity)** : logique métier sécurisée sur Ethereum.  
- **Base de données (MySQL)** : persistance des données hors blockchain.  
- **IA (Python Microservice)** : vérification automatique des compatibilités médicales.  

---

## 🛠️ Technologies utilisées
- **Backend :** Java 17, Spring Boot, Web3j  
- **Frontend :** Angular  
- **Blockchain :** Solidity, Remix IDE, Ganache, MetaMask  
- **Base de données :** MySQL  
- **IA :** Python (microservice de vérification compatibilité)  
- **Outils :** Docker, Maven, Postman  

---

## ⚙️ Installation & Exécution

### 1️⃣ Prérequis
- [Java 17](https://adoptopenjdk.net/)  
- [Node.js & Angular CLI](https://angular.io/cli)  
- [Ganache](https://trufflesuite.com/ganache/) ou un réseau Ethereum local/testnet  
- [MetaMask](https://metamask.io/)  
- [MySQL](https://www.mysql.com/)  

### 2️⃣ Lancer le backend
```bash
cd micro_smartcontract
mvn spring-boot:run
