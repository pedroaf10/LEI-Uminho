%---------------------- STATS ----------------------
%	Number of nodes: 31
%	Number of edges: 34
%	Number of edges (garbage deposit + garage): 96
%---------------------------------------------------


%---------------- Garagem e Lixeira ----------------
% garagem(s).
% lixeira(t).

%--------------------- caixote ---------------------
% caixote(id, lat, lon, rua, totalLixos, totalOrganico, totalPapel, totalEmbalagens, totalVidro
caixote(s, -9.149021, 38.711371, 'rua', 0, 0, 0, 0, 0).
caixote(t, -9.148458, 38.712066, 'rua', 0, 0, 0, 0, 0).
caixote(352, -9.144377, 38.706367, 'Av 24 de Julho', 610, 0, 0, 0, 0).
caixote(389, -9.142408, 38.706997, 'R Bernardino da Costa', 6320, 0, 240, 0, 0).
caixote(615, -9.144170, 38.707180, 'Tv dos Remolares', 2700, 0, 0, 0, 0).
caixote(662, -9.143759, 38.706580, 'Pc Duque da Terceira', 3320, 0, 760, 0, 0).
caixote(347, -9.145348, 38.707584, 'Tv de Sao Paulo', 1040, 0, 140, 0, 0).
caixote(633, -9.146220, 38.708270, 'Bc da Moeda', 180, 0, 0, 0, 0).
caixote(468, -9.146144, 38.707784, 'Tv Carvalho', 7590, 0, 6370, 6000, 3180).
caixote(634, -9.149564, 38.709006, 'Pto Galega', 240, 0, 0, 0, 0).
caixote(339, -9.142010, 38.707288, 'Lg Corpo Santo', 3440, 0, 240, 0, 0).
caixote(444, -9.151784, 38.708221, 'R Cais do Tojo', 3150, 0, 0, 0, 0).
caixote(402, -9.151251, 38.708775, 'Lg Conde-Barao', 11240, 0, 140, 140, 0).
caixote(457, -9.150464, 38.706801, 'R Instituto Industrial', 7960, 720, 0, 480, 560).
caixote(485, -9.147146, 38.707100, 'Pc Dom Luis I', 1480, 0, 280, 0, 0).
caixote(478, -9.150936, 38.707607, 'R Dom Luis I', 18290, 0, 480, 330, 0).
caixote(345, -9.149864, 38.709031, 'Bc Francisco Andre', 140, 0, 0, 0, 0).
caixote(640, -9.144764, 38.706922, 'Pc Ribeira Nova', 480, 0, 1100, 0, 0).
caixote(553, -9.146648, 38.708482, 'R Sao Paulo', 23460, 0, 760, 280, 0).
caixote(623, -9.144047, 38.705829, 'Cais do Sodre', 3240, 0, 140, 0, 0).
caixote(417, -9.149106, 38.709073, 'Tv Marques de Sampaio', 240, 0, 0, 0, 0).
caixote(435, -9.150184, 38.708911, 'R da Boavista', 12600, 90, 90, 90, 140).
caixote(440, -9.152060, 38.708282, 'Tv do Cais do Tojo', 140, 0, 0, 0, 0).
caixote(380, -9.142551, 38.707329, 'R Corpo Santo', 2480, 0, 0, 0, 0).
caixote(636, -9.150599, 38.709033, 'Bc da Boavista', 230, 0, 0, 0, 0).
caixote(493, -9.144930, 38.707281, 'R Ribeira Nova', 1700, 0, 1730, 0, 0).
caixote(381, -9.142766, 38.707084, 'Tv Corpo Santo', 2320, 0, 240, 0, 0).
caixote(448, -9.151389, 38.707928, 'Bqr do Duro', 2530, 0, 0, 0, 0).
caixote(645, -9.144889, 38.707624, 'Pc Sao Paulo', 1760, 0, 140, 0, 0).
caixote(465, -9.147044, 38.708019, 'R Moeda', 5720, 0, 0, 0, 0).
caixote(577, -9.143815, 38.707255, 'R Nova do Carvalho', 10920, 0, 860, 0, 0).
caixote(355, -9.143309, 38.708079, 'R do Alecrim', 5940, 0, 2370, 90, 0).
caixote(638, -9.144621, 38.707347, 'Tv Ribeira Nova', 180, 0, 0, 0, 0).

%---------------------- aresta ---------------------
% aresta(id1, id2, distancia)
aresta(352, 662, 73).
aresta(352, 615, 92).
aresta(345, 435, 38).
aresta(352, 485, 318).
aresta(339, 380, 60).
aresta(380, 381, 36).
aresta(389, 339, 55).
aresta(633, 553, 53).
aresta(444, 448, 54).
aresta(485, 465, 102).
aresta(352, 457, 679).
aresta(634, 435, 70).
aresta(478, 448, 61).
aresta(352, 623, 69).
aresta(417, 435, 121).
aresta(402, 448, 94).
aresta(645, 638, 43).
aresta(577, 355, 107).
aresta(352, 640, 75).
aresta(444, 440, 31).
aresta(640, 493, 44).
aresta(468, 645, 141).
aresta(457, 435, 234).
aresta(402, 435, 120).
aresta(347, 645, 51).
aresta(389, 381, 41).
aresta(468, 493, 146).
aresta(553, 465, 67).
aresta(347, 493, 57).
aresta(577, 638, 90).
aresta(615, 577, 40).
aresta(457, 478, 103).
aresta(435, 636, 48).
aresta(485, 478, 425).

%---------------- arestas adicionais ----------------
% aresta(id1, id2, distancia)
aresta(s, 493, 639).
aresta(s, 448, 461).
aresta(s, 465, 429).
aresta(t, 440, 577).
aresta(t, 435, 396).
aresta(s, 345, 273).
aresta(s, 389, 878).
aresta(t, 352, 773).
aresta(t, 640, 698).
aresta(t, 478, 562).
aresta(s, 339, 899).
aresta(s, 347, 583).
aresta(s, 380, 845).
aresta(t, 623, 842).
aresta(s, 435, 299).
aresta(s, 440, 479).
aresta(s, 381, 840).
aresta(s, 553, 413).
aresta(s, 577, 734).
aresta(s, 355, 731).
aresta(s, 457, 527).
aresta(t, 638, 671).
aresta(t, 465, 471).
aresta(t, 389, 873).
aresta(t, 347, 601).
aresta(s, 638, 659).
aresta(t, 380, 838).
aresta(t, 577, 739).
aresta(t, 355, 721).
aresta(t, 553, 442).
aresta(s, 640, 680).
aresta(t, 402, 476).
aresta(t, 634, 358).
aresta(s, 633, 461).
aresta(t, 381, 837).
aresta(t, 339, 888).
aresta(t, 457, 620).
aresta(s, 485, 513).
aresta(t, 444, 561).
aresta(s, 478, 465).
aresta(s, 444, 463).
aresta(s, 417, 252).
aresta(s, 468, 507).
aresta(s, 634, 267).
aresta(s, 402, 378).
aresta(t, 633, 485).
aresta(t, 636, 409).
aresta(t, 485, 564).
aresta(t, 615, 718).
aresta(t, 493, 656).
aresta(t, 662, 797).
aresta(t, 448, 559).
aresta(t, 645, 629).
aresta(s, 662, 787).
aresta(t, 345, 368).
aresta(t, 468, 536).
aresta(s, 645, 617).
aresta(s, 623, 822).
aresta(t, 417, 336).
aresta(s, 615, 709).
aresta(s, 352, 754).
aresta(s, 636, 311).

%-------------------- estimativa -------------------
% estimativa(id, distancia)
estimativa(352, 773).
estimativa(389, 873).
estimativa(615, 718).
estimativa(662, 797).
estimativa(347, 601).
estimativa(633, 485).
estimativa(468, 536).
estimativa(634, 358).
estimativa(339, 888).
estimativa(444, 561).
estimativa(402, 476).
estimativa(457, 620).
estimativa(485, 564).
estimativa(478, 562).
estimativa(345, 368).
estimativa(640, 698).
estimativa(553, 442).
estimativa(623, 842).
estimativa(417, 336).
estimativa(435, 396).
estimativa(440, 577).
estimativa(380, 838).
estimativa(636, 409).
estimativa(493, 656).
estimativa(381, 837).
estimativa(448, 559).
estimativa(645, 629).
estimativa(465, 471).
estimativa(577, 739).
estimativa(355, 721).
estimativa(638, 671).

% estimativa(id, distancia)
estimativa(s, 99).
estimativa(t, 0).

%----------------- garagemDistancia ----------------
% garagemDistancia(id, distancia)
garagemDistancia(352, 754).
garagemDistancia(389, 878).
garagemDistancia(615, 709).
garagemDistancia(662, 787).
garagemDistancia(347, 583).
garagemDistancia(633, 461).
garagemDistancia(468, 507).
garagemDistancia(634, 267).
garagemDistancia(339, 899).
garagemDistancia(444, 463).
garagemDistancia(402, 378).
garagemDistancia(457, 527).
garagemDistancia(485, 513).
garagemDistancia(478, 465).
garagemDistancia(345, 273).
garagemDistancia(640, 680).
garagemDistancia(553, 413).
garagemDistancia(623, 822).
garagemDistancia(417, 252).
garagemDistancia(435, 299).
garagemDistancia(440, 479).
garagemDistancia(380, 845).
garagemDistancia(636, 311).
garagemDistancia(493, 639).
garagemDistancia(381, 840).
garagemDistancia(448, 461).
garagemDistancia(645, 617).
garagemDistancia(465, 429).
garagemDistancia(577, 734).
garagemDistancia(355, 731).
garagemDistancia(638, 659).

%--------------- garagemLixoDistancia --------------
garagemLixoDistancia(99).