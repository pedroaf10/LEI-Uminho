-- | Este módulo define funções comuns da Tarefa 4 do trabalho prático.
module Tarefa4_2018li1g198 where

import LI11819
import Tarefa1_2018li1g198
import Tarefa2_2018li1g198
import Tarefa3_2018li1g198



-- * Testes
-- | Testes unitários da Tarefa 4.
--
-- Cada teste é um 'Estado'.
testesT4 :: [Estado]
testesT4 = [(Estado {mapaEstado = [[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]], jogadoresEstado = [Jogador {posicaoJogador = (8,1), direcaoJogador = D, vidasJogador = 5, lasersJogador = 5, choquesJogador= 5},Jogador {posicaoJogador = (1,5), direcaoJogador = D, vidasJogador = 5, lasersJogador = 5, choquesJogador = 5},Jogador {posicaoJogador = (1,12), direcaoJogador = B, vidasJogador = 5, lasersJogador = 5, choquesJogador = 5},Jogador {posicaoJogador = (6,7), direcaoJogador = E, vidasJogador = 5, lasersJogador = 5, choquesJogador = 5}], disparosEstado = [DisparoCanhao {jogadorDisparo = 1, posicaoDisparo = (1,9), direcaoDisparo = D},DisparoCanhao {jogadorDisparo = 1, posicaoDisparo = (1,8), direcaoDisparo = D},DisparoChoque {jogadorDisparo = 0, tempoDisparo = 4},DisparoChoque {jogadorDisparo = 2, tempoDisparo = 1}]}),(Estado [[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]] [(Jogador (8,1) D 5 5 5),(Jogador (2,5) D 5 5 5),(Jogador (1,12) B 5 5 5),(Jogador (6,7)  E 5 5 5)] [(DisparoLaser 1 (2,6) D)])]

-- * Funções principais da Tarefa 4.

-- | Avança o 'Estado' do jogo um 'Tick' de tempo.
--
-- __NB:__ Apenas os 'Disparo's afetam o 'Estado' do jogo com o passar do tempo.
--
-- __NB:__ Deve chamar as funções 'tickChoques', 'tickCanhoes' e 'tickLasers' pela ordem definida.
tick :: Estado -- ^ O 'Estado' anterior.
     -> Estado -- ^ O 'Estado' após um 'Tick'.
tick = tickChoques . tickCanhoes . tickLasers

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos dos tiros de 'Laser' disparados.
tickLasers :: Estado -> Estado
tickLasers (Estado m j d) =  Estado newm newj newd
   where newm = laserExplodeParedes (takeLasers d) m
         newj = laserTiraVidas (takeLasers d) j m
         newd = (laserExplodeCanhao d)

-- | Dado uma linha e uma coluna vê a 'Peca' nessa posição.
vePeca :: (Int,Int) -> Mapa -> Peca
vePeca (l,c) m = (!!) ( (!!) m l ) c

getLinha :: Int -> Mapa -> [Peca]
getLinha n m = m !! n
--           y esq  y dir  x linha
getLinhaNN :: Int -> Int -> Int -> Mapa -> [Peca]
getLinhaNN a b c m = take (b-a+1) (drop a (getLinha c m))

getColuna :: Int -> Mapa -> [Peca]
getColuna _ [] = []
getColuna n (m:ms) = m !! n : getColuna n ms
--            x_cim  x_bai  y_coluna
getColunaNN :: Int -> Int -> Int -> Mapa -> [Peca]
getColunaNN a b c m = take (b-a+1) (drop a (getColuna c m))

acertaLaserHor :: Disparo -> Jogador -> Mapa -> Bool
acertaLaserHor dl@(DisparoLaser n (x,y) E) jog@(Jogador (a,b) d v _ _) m
   | notBlocoIndest (getLinhaNN b y x m) && notBlocoIndest (getLinhaNN b y (x+1) m) = True
   | otherwise = False
acertaLaserHor dl@(DisparoLaser n (x,y) D) jog@(Jogador (a,b) d v _ _) m
   | notBlocoIndest (getLinhaNN y b x m) && notBlocoIndest (getLinhaNN y b (x+1) m) = True
   | otherwise = False

acertaLaserVer :: Disparo -> Jogador -> Mapa -> Bool
acertaLaserVer dl@(DisparoLaser n (x,y) C) jog@(Jogador (a,b) d v _ _) m
   | notBlocoIndest (getColunaNN a x y m) && notBlocoIndest (getColunaNN a x (y+1) m) = True
   | otherwise = False
acertaLaserVer dl@(DisparoLaser n (x,y) B) jog@(Jogador (a,b) d v _ _) m
   | notBlocoIndest (getColunaNN x a y m) && notBlocoIndest (getColunaNN x a (y+1) m) = True
   | otherwise = False

notBlocoIndest :: [Peca] -> Bool
notBlocoIndest [] = True
notBlocoIndest ((Bloco Indestrutivel):ps) = False
notBlocoIndest (p:ps) = notBlocoIndest ps

-- | Transforma as 'Peca's afetadas pelo 'DisparoLaser' em 'Vazia' á excessão dos 'Bloco Indestrutivel' que fazem terminar a função.

laserExplodeParedes :: [Disparo] -> Mapa -> Mapa
laserExplodeParedes ds m = foldl (flip laserExplodeParedesAux) m ds

-- | Função auxiliar da 'laserExplodeParedes'.
-- Quando recebe o 'DisparoLaser', verifica primeiro se as 'Peca's afetadas pelo 'DisparoLaser' tratam-se de um 'Bloco Indestrutivel'.
-- Se não forem, as 'Peca's afetadas pelo 'DisparoLaser' transformam-se em 'Vazia'

laserExplodeParedesAux :: Disparo -> Mapa -> Mapa
laserExplodeParedesAux (DisparoLaser j (x,y) C) m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Vazia) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Vazia) = m
   | vePeca (x,y) m == (Bloco Destrutivel) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = laserExplodeParedesAux (DisparoLaser j (x-1,y) C) mc
      where mc = alteraPeca (alteraPeca m  Vazia (x,y)) Vazia (x,y+1)
            m2 = alteraPeca m Vazia (x,y)
            m3 = alteraPeca m Vazia (x,y+1)
laserExplodeParedesAux (DisparoLaser j (x,y) D) m
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y+1) m == (Vazia) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Vazia) = m
   | vePeca (x,y+1) m == (Bloco Destrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = laserExplodeParedesAux (DisparoLaser j (x,y+1) D) md
      where md = alteraPeca (alteraPeca m  Vazia (x,y+1)) Vazia (x+1,y+1)
            m2 = alteraPeca m Vazia (x,y+1)
            m3 = alteraPeca m Vazia (x+1,y+1)
laserExplodeParedesAux (DisparoLaser j (x,y) B) m
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x+1,y) m == (Vazia) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Vazia) = m
   | vePeca (x+1,y) m == (Bloco Destrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = laserExplodeParedesAux (DisparoLaser j (x+1,y) B) mb
      where mb = alteraPeca (alteraPeca m  Vazia (x+1,y)) Vazia (x+1,y+1)
            m2 = alteraPeca m  Vazia (x+1,y)
            m3 = alteraPeca m  Vazia (x+1,y+1)
laserExplodeParedesAux (DisparoLaser j (x,y) E) m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Vazia) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Vazia) = m
   | vePeca (x,y) m == (Bloco Destrutivel) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Bloco Destrutivel) = m3
   | otherwise = laserExplodeParedesAux (DisparoLaser j (x,y-1) E) md
      where md = alteraPeca (alteraPeca m  Vazia (x,y)) Vazia (x+1,y)
            m2 = alteraPeca m  Vazia (x,y)
            m3 = alteraPeca m   Vazia (x+1,y)
-- | Altera a 'Peca' na posicão dada.
alteraPeca :: Mapa -> Peca -> (Int, Int) -> Mapa
alteraPeca m p (l,c) =
  take l m ++ [take c (m !! l) ++ [p] ++ drop (c + 1) (m !! l)] ++ drop (l + 1) m

-- | Caso um 'DisparoLaser' afete um 'Jogador', o 'jogadoresEstado' é atualizado.
-- Se nada acontecer, o 'jogadoresEstado' permanece o mesmo.
-- Se um 'DisparoLaser' não acertar em nenhum jogador, não acontece nada

laserTiraVidas :: [Disparo] -> [Jogador] -> Mapa -> [Jogador]
laserTiraVidas [] js m = js
laserTiraVidas _ [] _ = []
laserTiraVidas (d:ds) js m = laserTiraVidas ds (laserTiraVidasAux1 d js m) m

-- | Função auxiliar que dado um 'DisparoLaser' e uma lista de 'Jogador'es, vai alterar 'jogadoresEstado', caso forem atingidos pelo disparo.

laserTiraVidasAux1 :: Disparo -> [Jogador] -> Mapa -> [Jogador]
laserTiraVidasAux1 _ [] _ = []
laserTiraVidasAux1 d (j:js) m = laserTiraVidasAux2 d j m : laserTiraVidasAux1 d js m

-- | Função auxiliar da 'laserTiraVidasAux1' que dado um 'DisparoLaser' e um 'Jogador', vai alterar o número de 'Vida's desse jogador.

laserTiraVidasAux2 :: Disparo -> Jogador -> Mapa -> Jogador
laserTiraVidasAux2 disp@(DisparoLaser j (x1,y1) C) jog@(Jogador (x2,y2) d v l c) m
   | x1 >= x2 && abs (y1-y2) <= 1 && v > 0 && acertaLaserVer disp jog m = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
laserTiraVidasAux2 disp@(DisparoLaser j (x1,y1) D) jog@(Jogador (x2,y2) d v l c) m
   | abs (x1-x2) <= 1 && y1 <= y2 && v > 0 && acertaLaserHor disp jog m = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
laserTiraVidasAux2 disp@(DisparoLaser j (x1,y1) B) jog@(Jogador (x2,y2) d v l c) m
   | x1 <= x2 && abs (y1-y2) <= 1 && v > 0 && acertaLaserVer disp jog m = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
laserTiraVidasAux2 disp@(DisparoLaser j (x1,y1) E) jog@(Jogador (x2,y2) d v l c) m
   | abs (x1-x2) <= 1 && y1 >= y2 && v > 0 && acertaLaserHor disp jog m = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog

-- | Retorna os primeiros 'DisparoLaser's da lista 'Disparo'
removeLasers :: [Disparo] -> [Disparo]
removeLasers [] = []
removeLasers ((DisparoLaser jogador posicao direcao):ds) = removeLasers ds
removeLasers (d:ds) = d : removeLasers ds

-- | Retorna os últimos 'DisparoLaser's da lista 'Disparo'
takeLasers :: [Disparo] -> [Disparo]
takeLasers [] = []
takeLasers (las@(DisparoLaser jogador posicao direcao):ds) = las : takeLasers ds
takeLasers (d:ds) = takeLasers ds

-- | Quando um 'DisparoCanhao' é intercetado por um 'DisparoLaser', o 'DisparoCanhao' explode
laserExplodeCanhao :: [Disparo] -> [Disparo]
laserExplodeCanhao [] = []
laserExplodeCanhao ds = laserExplodeCanhaoAux1 (takeLasers ds) (removeLasers ds)

-- | Função auxiliar a 'laserExplodeCanhao' que ajuda a chamar as funções auxiliares 'laserExplodeCanhaoAux2' e 'laserExplodeCanhaoAux3' respetivamente.
laserExplodeCanhaoAux1 :: [Disparo] -> [Disparo] -> [Disparo]
laserExplodeCanhaoAux1 [] ds = ds
laserExplodeCanhaoAux1 (l:ls) ds = laserExplodeCanhaoAux1 (ls) (laserExplodeCanhaoAux2 l ds)

-- | Função auxiliar a 'laserExplodeCanhao' que ajuda a chamar a função auxiliar 'laserExplodeCanhaoAux3'
laserExplodeCanhaoAux2 :: Disparo -> [Disparo] -> [Disparo]
laserExplodeCanhaoAux2 _ [] = []
laserExplodeCanhaoAux2 las@(DisparoLaser j1 (x1,y1) d) (can@(DisparoCanhao j2 (x2,y2) _ ) : ds) = laserExplodeCanhaoAux3 las can ++ laserExplodeCanhaoAux2 las ds
laserExplodeCanhaoAux2 las@(DisparoLaser j1 (x1,y1) d) (cho@(DisparoChoque j2 n) : ds) = cho : laserExplodeCanhaoAux2 las ds

-- | Função auxiliar a 'laserExplodeCanhao'.
-- Dependendo da direção, verifica que existe algum 'DisparoCanhao' de outro 'Jogador'
laserExplodeCanhaoAux3 :: Disparo -> Disparo -> [Disparo]
laserExplodeCanhaoAux3 las@(DisparoLaser j1 (x1,y1) C) can@(DisparoCanhao j2 (x2,y2) _ )
   | x1 >= x2 && y1 == y2 && j1 /= j2 = []
   | otherwise = [can]
laserExplodeCanhaoAux3 las@(DisparoLaser j1 (x1,y1) D) can@(DisparoCanhao j2 (x2,y2) _ )
   | x1 == x2 && y1 <= y2 && j1 /= j2 = []
   | otherwise = [can]
laserExplodeCanhaoAux3 las@(DisparoLaser j1 (x1,y1) B) can@(DisparoCanhao j2 (x2,y2) _ )
   | x1 <= x2 && y1 == y2 && j1 /= j2 = []
   | otherwise = [can]
laserExplodeCanhaoAux3 las@(DisparoLaser j1 (x1,y1) E) can@(DisparoCanhao j2 (x2,y2) _ )
   | x1 == x2 && y1 >= y2 && j1 /= j2 = []
   | otherwise = [can]

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos das balas de 'Canhao' disparadas.

tickCanhoes :: Estado -> Estado
tickCanhoes (Estado m js ds) = Estado nM nJ nD
   where nM = canhaoExplodeParedes (takeCanhoes ds) m
         nJ = canhaoTiraVidas (takeCanhoes ds) js
         nD = moveCanhoes (intercessaoDisparos d1 d2 d3) ++ dropCanhoes ds
         d1 = paredeExplodeBalas (takeCanhoes ds) m
         d2 = jogadorExplodeBala (takeCanhoes ds) js
         d3 = balaExplodeBala (takeCanhoes ds) (takeCanhoes ds)

-- | De uma lista de 'Disparo's fica apenas com os últimos do tipo 'DisparoCanhao'.
dropCanhoes :: [Disparo] -> [Disparo]
dropCanhoes [] = []
dropCanhoes ((DisparoCanhao jogador posicao direcao):ds) = dropCanhoes ds
dropCanhoes (d:ds) = d : dropCanhoes ds

-- | A função 'moveCanhoes' faz recorrer á movimentação do 'DisparoCanhao' quando este é disparado pelo respetivo 'Jogador'
moveCanhoes :: [Disparo] -> [Disparo]
moveCanhoes [] = []
moveCanhoes ((DisparoCanhao j p d):ds) = (DisparoCanhao j (moveAux p d) d) : moveCanhoes ds

-- | De uma lista de 'Disparo's fica apenas com primeiros do tipo 'DisparoCanhao'.
takeCanhoes :: [Disparo] -> [Disparo]
takeCanhoes [] = []
takeCanhoes (can@(DisparoCanhao jogador posicao direcao):ds) = can : takeCanhoes ds
takeCanhoes (d:ds) = takeCanhoes ds

-- | Transforma em 'Vazia' os 'Bloco Destrutivel's do 'Mapa'.
canhaoExplodeParedes :: [Disparo] -> Mapa -> Mapa
canhaoExplodeParedes [] m = m
canhaoExplodeParedes (d:ds) m = canhaoExplodeParedes ds (canhaoExplodeParedesAux d m)

-- | Função auxiliar de 'canhaoExplodeParedes' que dado apenas um 'Disparo' retorna os seus efeitos no 'Mapa'.
canhaoExplodeParedesAux :: Disparo -> Mapa -> Mapa
canhaoExplodeParedesAux (DisparoCanhao j (x,y) C) m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Vazia) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Vazia) = m
   | vePeca (x,y) m == (Bloco Destrutivel) && vePeca (x,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = m1
      where m1 = alteraPeca (alteraPeca m Vazia (x,y)) Vazia (x,y+1)
            m2 = alteraPeca m Vazia (x,y)
            m3 = alteraPeca m Vazia (x,y+1)
canhaoExplodeParedesAux (DisparoCanhao j (x,y) D) m
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y+1) m == (Vazia) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Vazia) = m
   | vePeca (x,y+1) m == (Bloco Destrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y+1) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = m1
      where m1 = alteraPeca (alteraPeca m  Vazia (x,y+1)) Vazia (x+1,y+1)
            m2 = alteraPeca m Vazia (x,y+1)
            m3 = alteraPeca m Vazia (x+1,y+1)
canhaoExplodeParedesAux (DisparoCanhao j (x,y) B) m
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x+1,y) m == (Vazia) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Vazia) = m
   | vePeca (x+1,y) m == (Bloco Destrutivel) && vePeca (x+1,y+1) m == (Bloco Indestrutivel) = m2
   | vePeca (x+1,y) m == (Bloco Indestrutivel) && vePeca (x+1,y+1) m == (Bloco Destrutivel) = m3
   | otherwise = m1
      where m1 = alteraPeca (alteraPeca m  Vazia (x+1,y)) Vazia (x+1,y+1)
            m2 = alteraPeca m  Vazia (x+1,y)
            m3 = alteraPeca m  Vazia (x+1,y+1)
canhaoExplodeParedesAux (DisparoCanhao j (x,y) E) m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Vazia) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Vazia) = m
   | vePeca (x,y) m == (Bloco Destrutivel) && vePeca (x+1,y) m == (Bloco Indestrutivel) = m2
   | vePeca (x,y) m == (Bloco Indestrutivel) && vePeca (x+1,y) m == (Bloco Destrutivel) = m3
   | otherwise = m1
      where m1 = alteraPeca (alteraPeca m  Vazia (x,y)) Vazia (x+1,y)
            m2 = alteraPeca m  Vazia (x,y)
            m3 = alteraPeca m   Vazia (x+1,y)

-- | Reduz as 'Vidas' de um 'Jogador' cada vez que é atingido por um 'DisparoCanhao'.
canhaoTiraVidas :: [Disparo] -> [Jogador] -> [Jogador]
canhaoTiraVidas [] js = js
canhaoTiraVidas _ [] = []
canhaoTiraVidas (d:ds) js = canhaoTiraVidas ds (canhaoTiraVidasAux1 d js)

-- | Dado um 'DisparoCanhao' vê todos os 'Jogador'es afetados por ele.
canhaoTiraVidasAux1 :: Disparo -> [Jogador] -> [Jogador]
canhaoTiraVidasAux1 _ [] = []
canhaoTiraVidasAux1 d (j:js) = canhaoTiraVidasAux2 d j : canhaoTiraVidasAux1 d js

-- | Dado um 'DisparoCanhao' e um 'Jogador' analisa se o 'Jogador' foi atingido.
canhaoTiraVidasAux2 :: Disparo -> Jogador -> Jogador
canhaoTiraVidasAux2 (DisparoCanhao j (x1,y1) C) jog@(Jogador (x2,y2) d v l c)
   | x1 == x2+1 && abs (y1-y2) <= 1 && v > 0 = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
canhaoTiraVidasAux2 (DisparoCanhao j (x1,y1) D) jog@(Jogador (x2,y2) d v l c)
   | abs (x1-x2) <= 1 && y1 == y2-1 && v > 0 = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
canhaoTiraVidasAux2 (DisparoCanhao j (x1,y1) B) jog@(Jogador (x2,y2) d v l c)
   | x1 == x2-1 && abs (y1-y2) <= 1 && v > 0 = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog
canhaoTiraVidasAux2 (DisparoCanhao j (x1,y1) E) jog@(Jogador (x2,y2) d v l c)
   | abs (x1-x2) <= 1 && y1 == y2+1 && v > 0 = Jogador (x2,y2)  d (v-1) l c
   | otherwise = jog

-- | Cada vez que um 'Disparo' colide com alguma 'Peca' de 'Bloco Destrutivel' ou 'Bloco Indestrutivel' é destruído.
paredeExplodeBalas :: [Disparo] -> Mapa -> [Disparo]
paredeExplodeBalas [] _ = []
paredeExplodeBalas (d:ds) m = paredeExplodeBalasAux d m ++ paredeExplodeBalas ds m

-- | Dado um 'DisparoCanhao' verifica se colide com alguma 'Peca' de 'Bloco Destrutivel' ou 'Bloco Indestrutivel', em caso afirmativo é destruído.
paredeExplodeBalasAux :: Disparo -> Mapa -> [Disparo]
paredeExplodeBalasAux (DisparoCanhao j (x,y) C) m
   | vePeca (x,y) m == Vazia  && vePeca (x,y+1) m == Vazia = [DisparoCanhao j (x,y) C]
   | otherwise = []
paredeExplodeBalasAux (DisparoCanhao j (x,y) D) m
   | vePeca (x,y+1) m == (Vazia) && vePeca (x+1,y+1) m == Vazia = [DisparoCanhao j (x,y) D]
   | otherwise = []
paredeExplodeBalasAux (DisparoCanhao j (x,y) B) m
   | vePeca (x+1,y) m == Vazia && vePeca (x+1,y+1) m == Vazia = [DisparoCanhao j (x,y) B]
   | otherwise = []
paredeExplodeBalasAux (DisparoCanhao j (x,y) E) m
   | vePeca (x,y) m == Vazia && vePeca (x+1,y) m == Vazia = [DisparoCanhao j (x,y) E]
   | otherwise = []

-- | Cada vez que um 'Disparo' colide com algum 'Jogador' este é destruído.
jogadorExplodeBala :: [Disparo] -> [Jogador] -> [Disparo]
jogadorExplodeBala ds [] = ds
jogadorExplodeBala [] _ = []
jogadorExplodeBala (d:ds) js = jogadorExplodeBalaAux1 d js ++ jogadorExplodeBala ds js

-- | Dado um 'DisparoCanhao' verifica se colide com algum 'Jogador', em caso afirmativo é destruído.
jogadorExplodeBalaAux1 :: Disparo -> [Jogador] -> [Disparo]
jogadorExplodeBalaAux1 (DisparoCanhao j (x,y) d) []  = [DisparoCanhao j (x,y) d]
jogadorExplodeBalaAux1 d (j:js)
   | jogadorExplodeBalaBool d j = []
   | otherwise = jogadorExplodeBalaAux1 d js

-- | Dado um 'Disparo' e um 'Jogador' diz se colidem ou não.
jogadorExplodeBalaBool :: Disparo -> Jogador -> Bool
jogadorExplodeBalaBool disp@(DisparoCanhao j (x1,y1) C) jog@(Jogador (x2,y2) d v l c) =
   x1 == x2+1 && abs (y1-y2) <= 1 && v > 0
jogadorExplodeBalaBool disp@(DisparoCanhao j (x1,y1) D) jog@(Jogador (x2,y2) d v l c) =
   abs (x1-x2) <= 1 && y1 == y2-1 && v > 0
jogadorExplodeBalaBool disp@(DisparoCanhao j (x1,y1) B) jog@(Jogador (x2,y2) d v l c) =
   x1 == x2-1 && abs (y1-y2) <= 1 && v > 0
jogadorExplodeBalaBool disp@(DisparoCanhao j (x1,y1) E) jog@(Jogador (x2,y2) d v l c) =
   abs (x1-x2) <= 1 && y1 == y2+1 && v > 0

-- | Verifica se cada 'DisparoCanhao' é destruído por um outro.
balaExplodeBala :: [Disparo] -> [Disparo] -> [Disparo]
balaExplodeBala [] _ = []
balaExplodeBala (x:xs) ys = balaExplodeBalaAux x ys ++ balaExplodeBala xs ys

-- | Verifica se um 'DisparoCanhao' é destruído por um outro.
balaExplodeBalaAux :: Disparo -> [Disparo] -> [Disparo]
balaExplodeBalaAux d [] = [d]
balaExplodeBalaAux disp1@(DisparoCanhao j1 (x,y) d1) (disp2@(DisparoCanhao j2 (a,b) d2):ds)
   | (x,y) == (a,b) && j1 /= j2 = []
   | d1 == E && d2 == D && x == a && y == b-1 && j1 /= j2 = []
   | d1 == D && d2 == E && x == a && y == b+1 && j1 /= j2 = []
   | d1 == C && d2 == B && x == a-1 && y == b && j1 /= j2 = []
   | d1 == B && d2 == C && x == a+1 && y == b && j1 /= j2 = []
   | otherwise = balaExplodeBalaAux disp1 ds

-- | Faz a intercessão de 'Disparo's.
intercessaoDisparos :: [Disparo] -> [Disparo]  -> [Disparo] -> [Disparo]
intercessaoDisparos _ [] _ = []
intercessaoDisparos [] _ _ = []
intercessaoDisparos _ _ [] = []
intercessaoDisparos (x:xs) ys as
   | elem x ys && elem x as = x : intercessaoDisparos xs ys as
   | otherwise = intercessaoDisparos xs ys as

-- | Avança o 'Estado' do jogo um 'Tick' de tempo, considerando apenas os efeitos dos campos de 'Choque' disparados.
tickChoques :: Estado -> Estado
tickChoques (Estado m js ds) = Estado m js disp
   where disp = (takeLasers ds) ++ (takeCanhoes ds) ++ (tempoChoque (takeChoque ds))

-- | Dado uma lista de 'Disparo', fica apenas com os do tipo 'DisparoChoque'.
takeChoque :: [Disparo] -> [Disparo]
takeChoque [] = []
takeChoque (cho@(DisparoChoque jogador ticks):ds) = cho : takeChoque ds
takeChoque (d:ds) = takeChoque ds

-- | De 'Estado' para Estado, o tempo do 'DisparoChoque' vai perdendo um tick.
tempoChoque :: [Disparo] -> [Disparo]
tempoChoque [] = []
tempoChoque (d:ds) = tempoChoqueAux d ++ tempoChoque ds

-- | Função auxiliar da 'tempoChoque'
-- Á medida que o tempo vai passando, os 'Tick's do 'DisparoChoque' vão diminuindo.
-- Quando os ticks do 'TempoChoque' chegam a zero, o 'DisparoChoque' cessa.
tempoChoqueAux :: Disparo -> [Disparo]
tempoChoqueAux d@(DisparoChoque j t)
   | t /= 0 = [DisparoChoque j (t-1)]
   | otherwise = []
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- | Desenha na janela o 'Mapa'.
imprimeMapa:: Mapa -> IO()
imprimeMapa m = putStr(imprimeMapa2 m)

-- | Função auxiliar de 'imprimeMapa' que concatena uma 'Peca' comprimida em string ao resto da lista de 'Peca's do 'Mapa'.
imprimeMapa2:: Mapa -> String
imprimeMapa2 [] = []
imprimeMapa2 (m:ms) = concatMap comprimePeca m ++ "\n" ++ imprimeMapa2 ms

-- | Desenha todos os 'Jogador'es nas suas posições e respetivas caraterísticas no 'Mapa'.
imprimeJogadores :: [Jogador] -> IO()
imprimeJogadores jogadores = imprimeJogadoresAux jogadores 0

-- | Função auxiliar da 'imprimeJogadores' que dada a lista de 'Jogador'es e um índice, desenha no 'Mapa' o jogador com índice de 1 a 4, com todas as suas caraterísticas, como o número de vidas e a sua munição.
imprimeJogadoresAux :: [Jogador] -> Int -> IO()
imprimeJogadoresAux [] _ = putStrLn""
imprimeJogadoresAux (Jogador posicao direcao vidas laser choques:js) index = do
   putStr("Jogador " ++ show index ++ ":")
   putStr("\t" ++ "posicao: " ++ show posicao)
   putStr("\t" ++ "direcao: " ++ show direcao)
   putStr("\t" ++ "vidas  : " ++ show vidas)
   putStr("\t" ++ "laser  : " ++ show laser)
   putStrLn("\t" ++ "choques: " ++ show choques)
   imprimeJogadoresAux js (index + 1)

-- | Desenha no 'Mapa' todas as informações de cada tipo de 'Disparo' para cada 'Jogador'.
imprimeDisparos :: [Disparo] -> IO()
imprimeDisparos [] = putStrLn""
imprimeDisparos (DisparoCanhao jogador posicao direcao:ds) = do
  putStrLn("Jogador " ++ show jogador ++ ":\tCanhao\tposicao: " ++ show posicao ++ "\tdirecao: " ++ show direcao)
  imprimeDisparos ds
imprimeDisparos (DisparoLaser jogador posicao direcao:ds) = do
  putStrLn("Jogador " ++ show jogador ++ ":\tLaser\tposicao: " ++ show posicao ++ "\tdirecao: " ++ show direcao)
  imprimeDisparos ds
imprimeDisparos (DisparoChoque jogador ticks:ds) = do
  putStrLn("Jogador " ++ show jogador ++  ":\tChoque\tticks: " ++ show ticks)
  imprimeDisparos ds

-- | Desenha na janela o 'Mapa', os 'Jogador'es e suas respetivas caraterísticas, e os 'Disparo's e as suas respetivas informações, com os seus respetivos títulos.
imprimeEstado :: Estado -> IO()
imprimeEstado (Estado mapa jogadores disparos) = do
  putStrLn "\n=============== ESTADO ==============="
  putStrLn "\n**MAPA**"
  imprimeMapa mapa
  putStrLn "\n**JOGADORES**"
  imprimeJogadores jogadores
  putStrLn "\n**DISPAROS**"
  imprimeDisparos disparos
