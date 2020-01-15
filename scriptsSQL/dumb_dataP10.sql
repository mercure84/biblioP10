--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 12.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: livre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.livre VALUES (1523, '978-2013949675', 'Les fourberies de Scapin', 'Molière', NULL, 'Hachette Education', 'Bibliocollège', NULL, 7, 7, true);
INSERT INTO public.livre VALUES (6, '978-2011678409', 'Le Malade imaginaire', 'Molière', NULL, 'Hachette Education', 'Bibliocollègue', NULL, 1, 1, true);
INSERT INTO public.livre VALUES (1522, '978-2213706115', 'Devenir', 'Obama', 'Michelle', 'Fayard', 'Documents', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (4, '978-2501136341', 'Mes recettes healthy: BIM!', 'Geoffray', 'Thibault', 'Marabout', 'Cuisine', NULL, 4, 4, true);
INSERT INTO public.livre VALUES (15, '978-2213706023', 'Ce que je peux enfin vous dire', 'Royal', 'Segolène', 'Fayard', 'Documents', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1519, '978-2259253260', 'Non ce n''était pas mieux avant', 'Norberg', 'Johan', 'Plon', 'Hors collection', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (9, '978-2290019436', 'Le trône de fer : L''intégrale, tome 1', 'Martin', 'George R R', 'J''ai lu Librio', 'Semi-poche', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1516, '978-2501136341', 'Mes recettes healthy: BIM!', 'Geoffray', 'Thibault', 'Marabout', 'Cuisine', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1517, '978-2226257017', 'Sapiens: une brève histoire de l''humanité', 'Harari', 'Yuval Noah', 'Albin Michel', 'A.M. HORS COLL', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1525, '978-2226435897', 'L''outsider', 'King', 'Stephen', 'Albin Michel', 'AMS King', NULL, 3, 3, true);
INSERT INTO public.livre VALUES (7, '978-2246812432', 'L''amour après', 'Loridan-Ivens', 'Marceline', 'Grasset', 'Littérature française', NULL, 3, 3, true);
INSERT INTO public.livre VALUES (1515, '978-1096212454', 'Règles interdites', 'Byrd', 'Charlotte', 'Indépendant', 'Soirée interdite', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1531, '978-2211124485', 'L''Odyssee', 'Homère', NULL, 'EDL', 'Classiques abrégés', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1512, '978-2253237624', 'La jeune fille et la nuit', 'Musso', 'Guillaume', 'Livre de Poche', 'Littérature', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1528, '978-2253096405', 'Les souffrances du jeune Werther', 'Goethe', 'Johann', 'Livre de Poche', 'Le Livre de Poche Classique', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1534, '978-2841383764', 'L''Aquarium d''eau douce. Guide pratique du débutant', 'Louisy', 'Patrick', 'Ulmer', 'Médium', NULL, 4, 4, true);
INSERT INTO public.livre VALUES (1, '978-2709665469', 'Monsieur', 'James', 'E L', 'JC Lattes', 'Romans étranger', NULL, 4, 4, true);
INSERT INTO public.livre VALUES (1529, '978-2851815644', 'La Bonne Âme du Se-Tchouan', 'Brecht', 'Bertolt', 'L''Arche', 'Scène ouverte', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1532, '978-2246816812', 'L''empire et les cinq rois', 'Levy', 'Bernard-Henry', 'Grasset', 'essai français', NULL, 3, 3, true);
INSERT INTO public.livre VALUES (1511, '978-2253100492', 'Il est grand temps de rallumer les étoiles', 'Grimaldi', 'Virginie', 'Livre de Poche', 'Littérature', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (13, '978-2841389834', 'Petits aquariums récifaux', 'Cuquemelle', 'Jean-Louis', 'Ulmer', 'Aquariophilie-Terrariophilie', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (3, '978-2812305634', 'Créer un aquarium d''eau de mer', 'Collectif', NULL, 'Editions du Chêne', 'Hors collection', NULL, 4, 4, true);
INSERT INTO public.livre VALUES (1518, '978-2070584628', 'Harry Potter 1 : Harry Potter à l''école des sorciers', 'Rowling', 'J K', 'Folio Junior', 'Folio Junior', NULL, 1, 1, true);
INSERT INTO public.livre VALUES (1524, '978-2253002864', 'Au bonheur des dames', 'Zola', 'Emile', 'Livre de Poche', 'Livre de poche', NULL, 3, 3, true);
INSERT INTO public.livre VALUES (1514, '978-1097923397', 'Le Défloreur', 'Quinn', 'Victoria', 'Indépendant', 'des Fleurs', NULL, 2, 2, true);
INSERT INTO public.livre VALUES (1526, '978-2010009273', 'Le crime de l''Orient Express', 'Christie', 'Agatha', 'Livre de Poche', 'Livre de Poche Jeunesse', NULL, 1, 1, true);


--
-- Data for Name: membre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.membre VALUES (1, 'Marcesse', 'Julien', 'julien.marcesse@gmail.com', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-03-08', '0608051716', 'ADMIN');
INSERT INTO public.membre VALUES (26, 'Goofy', 'Dungo', 'mickey123@disney.fr', '$2a$10$HdqW8/ZkGWEXcSYIN.XStujw.qkdqGHLY3i0zYcG7FohXUtdXxS8.', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (27, 'Targaryen', 'Daeneris', 'daeneris@got.com', '$2a$10$3vHWSuhccJZcPx4vrQJMA.IyMFMo7dpUM0We8Xk8ei.f4iGwybBRW', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (24, 'Mouse', 'Mickey', 'mickey@disney.fr', '$2a$10$T/KgcFUb/QPFS9wj4R0EWuNvyuY61aweP1w8Lab4w5tXbY1BtJjwu', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (25, 'Duck', 'Donald', 'donald.disney@gmail.com', '$2a$10$A/njJc2dZzebSBs6IGX.ieqgyoO7zvVbpoaHp0bFU5UqGiOqRfoI2', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (28, 'Snow', 'Jon', 'jon.snow@got.fr', '$2a$10$cqKK6ptrygotxx71x3exQudCl.jrbjHyrnd1cRyEOqWKL4QV3OH82', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (29, 'Anderson', 'Thomas', 'neo@thematrix.fr', '$2a$10$r84fFo89AiMERVek9aG3FONqLIFuQ9Arr22oelgr4kbjf8e.Mpb82', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (34, 'Lapix', 'Anne-So', 'anneso@france2.com', '$2a$10$GpdCPesTzS9/bOgcvnSUSOj0ufzM8t8flkV4xY9CYUaZCzMcyIogS', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (35, 'The Corre', 'Chris', 'chris@poutre.fr', '$2a$10$5XLsYBIGxLRBf/j1gflr/udgpV4lCzHS2tJ7JJKnt6bBSrcXrnIw2', '2019-06-21', NULL, 'USER');
INSERT INTO public.membre VALUES (32, 'WC', 'canard', 'canardpc@lycos.fr', '$2a$10$MgoyzmGwTjkra3yt1chNtuuDfLifeaUbPHOEVw.E1OeTVHaVuGMVK', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (33, 'Calvi', 'Yves', 'calvi@yvesF2.com', '$2a$10$gTGuSfmEVaCi1fM3S5nWnu9lxkPy75vKkP6BNulWRqwzP61WzGM6K', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (3, 'Fasquel', 'Armelle', 'armelleF@gmail.com', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-01-10', '0603185232', 'USER');
INSERT INTO public.membre VALUES (4, 'Pagode', 'Lulu', 'lux@chatterie.net', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-05-18', '0632393534', 'USER');
INSERT INTO public.membre VALUES (5, 'De Bully', 'Mercire', 'mercure@yahoo.fr', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-05-05', '0704013365', 'USER');
INSERT INTO public.membre VALUES (10, 'Lampe', 'Genie', 'aladin@genie.us', '$2a$10$oQkwovwSDA6Lhx.ZWeIYYOtaOCH3p7KZLcC4/6buwFsyHBH3xDvAK', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (11, 'Test', 'TestPrenom', 'fakemail@fake.fr', '$2a$10$CdCsqPG5uLZDTp02xQbBxuMFK6QLHdp7TkCn/iWJ.lZidM.ySCSC.', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (8, 'Coursevent', 'Sylvanas', 'sylvanasC@gmail.com', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-04-05', '0611223344', 'USER');
INSERT INTO public.membre VALUES (9, 'Wrynn', 'Anduin', 'anduinwow@gmail.com', '$2a$10$DFI/SBgOWwY2fr05QBK6VeCB/MIySTqr7SWZHbUxw9nnidWTry/eS', '2019-03-04', '0707070808', 'USER');
INSERT INTO public.membre VALUES (12, 'Dussolier', 'André', 'andre@wanadoo.fr', '$2a$10$Ih3wkDEAa/fEzlpkkZqyjOgXqhuphbP7VoD0/Fl5VDcL.roxyZMUC', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (18, 'Kent', 'Clarke', 'clarke.kent@dailyplanet.net', '$2a$10$tTqApTH799ek7lK39s.JBOj4e3R8hKBnToa/n25/bZASxmDQ.M3Mi', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (19, 'Test', 'PrenomTest', 'testprenomnom@email.fr', '$2a$10$Nx4fgEIgTA0pXrw9S18CIutilFUxYOnRIVwxw2QwPQ6a9YoRFioVy', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (16, 'Murmevent', 'Tyrande', 'tyrmur@gmail.com', '$2a$10$zTEqmMguF485FmuyeWKkXeiFw/zGqGH3PYILlxW20YR66D9F0.Qei', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (23, 'Shakespear', 'Will', 'will@voila.fr', '$2a$10$Tp4VkIMs.wJ6e8m9chjs4.4x4b35oWUknkaMFDJhiXeS.jSIRHxQi', '2019-01-24', NULL, 'USER');
INSERT INTO public.membre VALUES (38, 'Duck', 'Donald', 'mickey23123@disney.fr', '$2a$10$TimtjtTCeHtqwrS2Z/aPlem/tBo3SzcBaTgStdAiRmtqYlzRgwD3u', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (41, 'Des Parfums', 'Princesse', 'princesse@yahoo.fr', '$2a$10$jpg1K9NbvjVog8S/rsD31OcGzVzjxpteWtaniJCmChyEwWrJuRo8C', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (42, 'Fortnite', 'Matti', 'fortnite@matti.fr', '$2a$10$ERUoe8z7Dwkd34izJRqpgenpK17zbtTRG9jeoNKilRHbS1k6RGvdu', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (44, 'Rigolo', 'Arnold', 'hokkos@gmail.com', '$2a$10$1XDlaUdNKqWjbpjCoC4CVeYZRqQbdrryfI74dD89JdqtMb3tVRV', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (46, 'Spaniel', 'Cocker', 'cocker@yahoo.fr', '$2a$10$fpWau3RZaWjf6ZSWtQS3dO/A2QzaE7LTbyuq9423pdZKD3Yw0xBpi', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (47, 'TestNom', 'TestPrenom', 'test@test.com', '$2a$10$dywVScxKGWf8mlppKqLIHeh/Jm/JXi/l74SIRFpEEwZuZwVP2OAua', NULL, NULL, 'USER');
INSERT INTO public.membre VALUES (6, 'Marant', 'Arnold', 'hokkos@live.fr', '$2a$10$dywVScxKGWf8mlppKqLIHeh/Jm/JXi/l74SIRFpEEwZuZwVP2OAua', '2019-05-26', '0606984325', 'USER');


--
-- Data for Name: emprunt; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: emprunt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.emprunt_id_seq', 1, true);


--
-- Name: livre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.livre_id_seq', 33, true);


--
-- Name: membre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.membre_id_seq', 48, false);


--
-- Name: reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservation_id_seq', 55, true);


--
-- PostgreSQL database dump complete
--

