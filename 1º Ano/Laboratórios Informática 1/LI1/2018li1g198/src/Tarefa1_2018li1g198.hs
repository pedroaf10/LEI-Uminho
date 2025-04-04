-- | Este módulo define funções comuns da Tarefa 1 do trabalho prático.
module Tarefa1_2018li1g198 where

import LI11819
import Tarefa0_2018li1g198
-- * Testes

-- | Testes unitários da Tarefa 1.
--
-- Cada teste é uma sequência de 'Instrucoes'.
testesT1 :: [Instrucoes]
testesT1 = [[Move D,Desenha,Roda,Move B,Move B,Move E,Move E,Move E,Move C,Move C,Move C,Move C,Move D,Move D,Move C,Move D,Move B,Move D,Move D,Move E,Move E,Move E,Move D,Move D,Move E,Move E,Desenha,Roda,Move D,MudaTetromino,Move D,Move D,Move B,Roda,Move B,Move B,Move B,Move E,Move C,Move D,Desenha,MudaParede,Move E,Move E,Move E,Roda,Roda,Roda,Roda,Move E,MudaTetromino,MudaTetromino,Move B,Move B,Move E,Move B,Move E,Move D,Move C,Move C,Desenha,Move C,Move C,Move C,Move C,Move C,Move E,Move E,Move E,Move D,Move B,Move E,Move E,Move D,Move B,Move C,Move D,Desenha,MudaTetromino,MudaTetromino,MudaParede,Move B,Move B,Desenha]]

-- * Funções principais da Tarefa 1.

-- | Aplica uma 'Instrucao' num 'Editor'.
--
--    * 'Move' - move numa dada 'Direcao'.
--
--    * 'MudaTetromino' - seleciona a 'Peca' seguinte (usar a ordem léxica na estrutura de dados),
--       sem alterar os outros parâmetros.
--
--    * 'MudaParede' - muda o tipo de 'Parede'.
--
--    * 'Desenha' - altera o 'Mapa' para incluir o 'Tetromino' atual, sem alterar os outros parâmetros.
instrucao :: Instrucao -- ^ A 'Instrucao' a aplicar.
          -> Editor    -- ^ O 'Editor' anterior.
          -> Editor    -- ^ O 'Editor' resultante após aplicar a 'Instrucao'.

-- | Dada uma 'Instrucao' movimenta o 'Tetromino' segundo uma 'Direcao'.
instrucao (Move C) (Editor pos dir tetromino parede mapa) = Editor (moveAux pos C) dir tetromino parede mapa
instrucao (Move B) (Editor pos dir tetromino parede mapa) = Editor (moveAux pos B) dir tetromino parede mapa
instrucao (Move E) (Editor pos dir tetromino parede mapa) = Editor (moveAux pos E) dir tetromino parede mapa
instrucao (Move D) (Editor pos dir tetromino parede mapa) = Editor (moveAux pos D) dir tetromino parede mapa

-- | Dada uma 'Instrucao' 'Roda', roda o 'Tetromino' 90 graus no sentido dos ponteiros do relógio e altera a sua 'Direcao'.
instrucao Roda (Editor pos C tetromino parede mapa) = Editor pos D tetromino parede mapa
instrucao Roda (Editor pos D tetromino parede mapa) = Editor pos B tetromino parede mapa
instrucao Roda (Editor pos B tetromino parede mapa) = Editor pos E tetromino parede mapa
instrucao Roda (Editor pos E tetromino parede mapa) = Editor pos C tetromino parede mapa

-- | Dada uma 'Instrucao' 'MudaTetromino', muda o 'Tetromino' para o 'Tetromino' seguinte.
instrucao MudaTetromino (Editor pos dir I parede mapa) = Editor pos dir J parede mapa
instrucao MudaTetromino (Editor pos dir J parede mapa) = Editor pos dir L parede mapa
instrucao MudaTetromino (Editor pos dir L parede mapa) = Editor pos dir O parede mapa
instrucao MudaTetromino (Editor pos dir O parede mapa) = Editor pos dir S parede mapa
instrucao MudaTetromino (Editor pos dir S parede mapa) = Editor pos dir T parede mapa
instrucao MudaTetromino (Editor pos dir T parede mapa) = Editor pos dir Z parede mapa
instrucao MudaTetromino (Editor pos dir Z parede mapa) = Editor pos dir I parede mapa

-- | Dada uma 'Instrucao' 'MudaParede', muda o tipo da 'Parede'.
instrucao MudaParede (Editor pos dir tetromino Indestrutivel mapa) = Editor pos dir tetromino Destrutivel mapa
instrucao MudaParede (Editor pos dir tetromino Destrutivel mapa) = Editor pos dir tetromino Indestrutivel mapa

-- | Dada uma 'Instrucao' 'Desenha', desenha o 'Tetromino' selecionado segundo a informação dada pelo 'Editor'.
instrucao Desenha (Editor pos dir tetromino parede mapa) =
  Editor pos dir tetromino parede (desenhaAux pos mapa (rodaMatrizAux (posicaoParaInteiro dir) (tetrominoParaMatriz tetromino)) parede)

-- | dada uma 'Direcao' dá um inteiro equivalente ao número de vezes que terá de rodar a matriz.
posicaoParaInteiro :: Direcao -> Int --
posicaoParaInteiro C = 0
posicaoParaInteiro D = 1
posicaoParaInteiro B = 2
posicaoParaInteiro E = 3

-- | roda uma matriz 'n' vezes.
rodaMatrizAux :: Int -> Matriz a -> Matriz a
rodaMatrizAux 0 m = m
rodaMatrizAux n m = rodaMatrizAux (n-1) (rodaMatriz m)

-- | Construi o 'Mapa' novo após ter havido alguma 'Intrucao' 'Desenha'.
desenhaAux :: Posicao -> Mapa -> Matriz Bool -> Parede -> Mapa
desenhaAux (0,_) xs [] _ = xs
desenhaAux (0,c) (x:xs) (y:ys) parede = desenhaLinhaAux c x y parede : desenhaAux (0,c) xs ys parede
desenhaAux (l,c) (x:xs) ys parede = x : desenhaAux (l-1,c) xs ys parede

-- | Desenha uma linha, dada uma coluna, uma linha do 'Mapa' (x:xs), uma linha do 'Tetromino' selecionado, e o tipo de 'Parede'.
desenhaLinhaAux :: Int -> [Peca] -> [Bool] -> Parede -> [Peca]
desenhaLinhaAux 0 xs [] parede = xs
desenhaLinhaAux 0 (x:xs) (y:ys) parede | y = Bloco parede : desenhaLinhaAux 0 xs ys parede
                                   | otherwise = x : desenhaLinhaAux 0 xs ys parede
desenhaLinhaAux coluna (x:xs) ys parede = x : desenhaLinhaAux (coluna-1) xs ys parede


-- | Consoante a 'Direcao' soma um vetor à 'Posicao' inicial.
moveAux :: Posicao -> Direcao -> Posicao
moveAux (x,y) C = (x-1,y)
moveAux (x,y) B = (x+1,y)
moveAux (x,y) E = (x,y-1)
moveAux (x,y) D = (x,y+1)

-- | Aplica uma sequência de 'Instrucoes' num 'Editor'.
--
-- __NB:__ Deve chamar a função 'instrucao'.
instrucoes :: Instrucoes -- ^ As 'Instrucoes' a aplicar.
           -> Editor     -- ^ O 'Editor' anterior.
           -> Editor     -- ^ O 'Editor' resultante após aplicar as 'Instrucoes'.
instrucoes [] (Editor pos dir tetromino parede mapa) = Editor pos dir tetromino parede mapa
instrucoes (x:xs) (Editor pos dir tetromino parede mapa) =
  instrucoes xs (instrucao x (Editor pos dir tetromino parede mapa))

-- | Cria um 'Mapa' inicial com 'Parede's nas bordas e o resto vazio.
mapaInicial :: Dimensao -- ^ A 'Dimensao' do 'Mapa' a criar.
            -> Mapa     -- ^ O 'Mapa' resultante com a 'Dimensao' dada.
mapaInicial (l,c) = [fazBorda c] ++ criaMapaInicialAux (l-2,c) ++ [fazBorda c]

-- | Cria um 'Mapa' com as bordas laterais.
criaMapaInicialAux :: Dimensao -> Mapa
criaMapaInicialAux (0,_) = []
criaMapaInicialAux (l,c) = criaLinhaMapa c : criaMapaInicialAux (l-1,c)

-- | Cria uma linha do 'Mapa' com as bordas laterais.
criaLinhaMapa :: Int -> [Peca]
criaLinhaMapa n = [Bloco Indestrutivel] ++ criaLinhaMapaAux (n-2) ++ [Bloco Indestrutivel]

-- | Cria uma linha do 'Mapa' só com 'Peca's 'Vazia's.
criaLinhaMapaAux :: Int -> [Peca]
criaLinhaMapaAux 0 = []
criaLinhaMapaAux n = Vazia : criaLinhaMapaAux (n-1)

-- | Cria uma linha do 'Mapa' só com 'Peca's 'Bloco Indestrutivel', serve para criar as bordas superiores e inferiores.
fazBorda :: Int -> [Peca]
fazBorda 0 = []
fazBorda n = Bloco Indestrutivel : fazBorda (n-1)

-- | Cria um 'Editor' inicial.
--
-- __NB:__ Deve chamar as funções 'mapaInicial', 'dimensaoInicial', e 'posicaoInicial'.
editorInicial :: Instrucoes  -- ^ Uma sequência de 'Instrucoes' de forma a poder calcular a  'dimensaoInicial' e a 'posicaoInicial'.
              -> Editor      -- ^ O 'Editor' inicial, usando a 'Peca' 'I' 'Indestrutivel' voltada para 'C'.
editorInicial ins = Editor (posicaoInicial ins) C I Indestrutivel (mapaInicial (dimensaoInicial ins))

-- | Constrói um 'Mapa' dada uma sequência de 'Instrucoes'.
--
-- __NB:__ Deve chamar as funções 'Instrucoes' e 'editorInicial'.
constroi :: Instrucoes -- ^ Uma sequência de 'Instrucoes' dadas a um 'Editor' para construir um 'Mapa'.
         -> Mapa       -- ^ O 'Mapa' resultante.
constroi ins = mapa
      where (Editor pos dir tetromino parede mapa) = instrucoes ins (editorInicial ins)
