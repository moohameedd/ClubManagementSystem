-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 17 déc. 2025 à 15:01
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `club`
--

-- --------------------------------------------------------

--
-- Structure de la table `activitee`
--

CREATE TABLE `activitee` (
  `id_activitee` int(11) NOT NULL,
  `type_activitee` varchar(200) NOT NULL,
  `date_activitee` date NOT NULL,
  `id_club` int(11) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `activitee`
--

INSERT INTO `activitee` (`id_activitee`, `type_activitee`, `date_activitee`, `id_club`, `id_user`) VALUES
(1, 'contest for beginners', '2025-12-20', 1, 2),
(2, 'workshop(prefix-sum/sufix-sum)', '2025-12-27', 1, 1),
(3, 'tech conference event', '2025-12-21', 2, 3),
(4, 'live music event', '2025-12-24', 3, 4),
(5, 'culturel event ', '2025-12-26', 4, 5);

-- --------------------------------------------------------

--
-- Structure de la table `club`
--

CREATE TABLE `club` (
  `id_club` int(11) NOT NULL,
  `nom_club` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `club`
--

INSERT INTO `club` (`id_club`, `nom_club`) VALUES
(1, 'IPS (isamm problem solving)'),
(2, 'IMC (isamm microsoft club)'),
(3, 'Club musique'),
(4, 'Orenda');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `email` varchar(300) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(100) NOT NULL,
  `id_club` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id_user`, `nom`, `prenom`, `email`, `password`, `role`, `id_club`) VALUES
(1, 'ben ali', 'ahmed', 'ahmed@gmail.com', '1234', 'member', 1),
(2, 'ferchichi', 'mohamed', 'hama.ferchichi321@gmail.com', '0000', 'admin', 1),
(3, 'mansour', 'youssef', 'youssef@gmail.com', '1234', 'member', 2),
(4, 'khemiri', 'omar', 'omar@gmail.com', '1234', 'member', 3),
(5, 'saidi', 'nada', 'nada@gmail.com', '1234', 'member', 4);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activitee`
--
ALTER TABLE `activitee`
  ADD PRIMARY KEY (`id_activitee`),
  ADD KEY `id_club` (`id_club`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `club`
--
ALTER TABLE `club`
  ADD PRIMARY KEY (`id_club`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `id_club` (`id_club`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activitee`
--
ALTER TABLE `activitee`
  MODIFY `id_activitee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `club`
--
ALTER TABLE `club`
  MODIFY `id_club` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activitee`
--
ALTER TABLE `activitee`
  ADD CONSTRAINT `activitee_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activitee_ibfk_2` FOREIGN KEY (`id_club`) REFERENCES `club` (`id_club`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`id_club`) REFERENCES `club` (`id_club`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
