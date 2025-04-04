-- | Este módulo define funções genéricas sobre vetores e matrizes, que serão úteis na resolução do trabalho prático.
module Tarefa0_2018li1g198 where

import LI11819

-- * Funções não-recursivas.

-- | Um 'Vetor' é uma 'Posicao' em relação à origem..
type Vetor = Posicao
-- ^ <<http://oi64.tinypic.com/mhvk2x.jpg vetor>>

-- ** Funções sobre vetores

-- *** Funções gerais sobre 'Vetor'es.

-- | Soma dois 'Vetor'es.
somaVetores :: Vetor -> Vetor -> Vetor
somaVetores (a,b) (c,d) = (a+c,b+d)

-- | Subtrai dois 'Vetor'es.
subtraiVetores :: Vetor -> Vetor -> Vetor
subtraiVetores (a,b) (c,d) = (a-c,b-d)

-- | Multiplica um escalar por um 'Vetor'.
multiplicaVetor :: Int -> Vetor -> Vetor
multiplicaVetor a (x,y) = (a*x,a*y)

-- | Roda um 'Vetor' 90º no sentido dos ponteiros do relógio, alterando a sua direção sem alterar o seu comprimento (distância à origem).
--
-- <<http://oi65.tinypic.com/2j5o268.jpg rodaVetor>>
rodaVetor :: Vetor -> Vetor
rodaVetor (a,b) = (b,-a)

-- | Espelha um 'Vetor' na horizontal (sendo o espelho o eixo vertical).
--
-- <<http://oi63.tinypic.com/jhfx94.jpg inverteVetorH>>
inverteVetorH :: Vetor -> Vetor
inverteVetorH (a,b) = (a,-b)

-- | Espelha um 'Vetor' na vertical (sendo o espelho o eixo horizontal).
--
-- <<http://oi68.tinypic.com/2n7fqxy.jpg inverteVetorV>>
inverteVetorV :: Vetor -> Vetor
inverteVetorV (a,b) = (-a,b)

-- *** Funções do trabalho sobre 'Vetor'es.

-- | Devolve um 'Vetor' unitário (de comprimento 1) com a 'Direcao' dada.
direcaoParaVetor :: Direcao -> Vetor
direcaoParaVetor C = (-1,0)
direcaoParaVetor D = (0,1)
direcaoParaVetor B = (1,0)
direcaoParaVetor E = (0,-1)

-- ** Funções sobre listas

-- *** Funções gerais sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Verifica se o indice pertence à lista.
eIndiceListaValido :: Int -> [a] -> Bool
eIndiceListaValido x xs = length xs > x && x >=0

-- ** Funções sobre matrizes.

-- *** Funções gerais sobre matrizes.

-- | Uma matriz é um conjunto de elementos a duas dimensões.
--
-- Em notação matemática, é geralmente representada por:
--
-- <<https://upload.wikimedia.org/wikipedia/commons/d/d8/Matriz_organizacao.png matriz>>
type Matriz a = [[a]]

-- | Calcula a dimensão de uma matriz.
--
-- __NB:__ Note que não existem matrizes de dimensão /m * 0/ ou /0 * n/, e que qualquer matriz vazia deve ter dimensão /0 * 0/.
dimensaoMatriz :: Matriz a -> Dimensao
dimensaoMatriz [] = (0,0)
dimensaoMatriz ([]:m) = (0,0)
dimensaoMatriz a = (length a, length (head (a)))

-- | Verifica se a posição pertence à matriz.
ePosicaoMatrizValida :: Posicao -> Matriz a -> Bool
ePosicaoMatrizValida (l,c) m = (l < length m) && (l >= 0) && (c >= 0) && (c < length (head (m)))
-- | Verifica se a posição está numa borda da matriz.
eBordaMatriz :: Posicao -> Matriz a -> Bool
eBordaMatriz (l,c) m = ( l == 0)
                    || ( c == 0)
                    || l == (length m) -1
                    || c == (length (head m)) -1

-- *** Funções do trabalho sobre matrizes.

-- | Converte um 'Tetromino' (orientado para cima) numa 'Matriz' de 'Bool'.
--
-- <<http://oi68.tinypic.com/m8elc9.jpg tetrominos>>
tetrominoParaMatriz :: Tetromino -> Matriz Bool
tetrominoParaMatriz I = [[False,True,False,False],[False,True,False,False],[False,True,False,False],[False,True,False,False]]

tetrominoParaMatriz J = [[False,True,False],[False,True,False],[True,True,False]]

tetrominoParaMatriz L = [[False,True,False],[False,True,False],[False,True,True]]

tetrominoParaMatriz O = [[True,True],[True,True]]

tetrominoParaMatriz S = [[False,True,True],[True,True,False],[False,False,False]]

tetrominoParaMatriz T =[[False,False,False],[True,True,True],[False,True,False]]

tetrominoParaMatriz Z =[[True,True,False],[False,True,True],[False,False,False]]

-- * Funções recursivas.

-- ** Funções sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Devolve o elemento num dado índice de uma lista.
encontraIndiceLista :: Int -> [a] -> a
encontraIndiceLista 0 (x:xs) = x
encontraIndiceLista a (x:xs) = encontraIndiceLista (a-1) xs

-- | Modifica um elemento num dado índice.
--
-- __NB:__ Devolve a própria lista se o elemento não existir.
atualizaIndiceLista :: Int -> a -> [a] -> [a]
atualizaIndiceLista index number [] = []
atualizaIndiceLista 0 number (x:xs) = (number:xs)
atualizaIndiceLista index number (x:xs) = [x] ++ atualizaIndiceLista (index-1) number (xs)

-- ** Funções sobre matrizes.

-- | Roda uma 'Matriz' 90º no sentido dos ponteiros do relógio.
--
-- <<http://oi68.tinypic.com/21deluw.jpg rodaMatriz>>
rodaMatriz :: Matriz a -> Matriz a
rodaMatriz [] = []
rodaMatriz ([]:xs) = []
rodaMatriz m = reverse (map head m) : rodaMatriz (map tail m)

-- | Inverte uma 'Matriz' na horizontal.
--
-- <<http://oi64.tinypic.com/iwhm5u.jpg inverteMatrizH>>
inverteMatrizH :: Matriz a -> Matriz a
inverteMatrizH [] = []
inverteMatrizH (x:xs) = reverse x : inverteMatrizH xs

-- | Inverte uma 'Matriz' na vertical.
--
-- <<http://oi64.tinypic.com/11l563p.jpg inverteMatrizV>>
inverteMatrizV :: Matriz a -> Matriz a
inverteMatrizV [] = []
inverteMatrizV m = reverse m

-- | Cria uma nova 'Matriz' com o mesmo elemento.
criaMatriz :: Dimensao -> a -> Matriz a
criaMatriz (0,0) _ = []
criaMatriz (1,y) n = [replicate y n]
criaMatriz (x,y) n = [replicate y n] ++ criaMatriz ((x-1),y) n

-- | Devolve o elemento numa dada 'Posicao' de uma 'Matriz'.
encontraPosicaoMatriz :: Posicao -> Matriz a -> a
encontraPosicaoMatriz (0,y) m = encontraIndiceLista (y) (head m)
encontraPosicaoMatriz (x,y) m = encontraPosicaoMatriz (x-1, y) (tail m)

-- | Modifica um elemento numa dada 'Posicao'
--
-- __NB:__ Devolve a própria 'Matriz' se o elemento não existir.
atualizaPosicaoMatriz :: Posicao -> a -> Matriz a -> Matriz a
atualizaPosicaoMatriz (l,c) e [] = []
atualizaPosicaoMatriz (0,c) e m = (atualizaIndiceLista (c) e (head m)):(tail m)
atualizaPosicaoMatriz (l,c) e m = (head m):(atualizaPosicaoMatriz (l-1,c) e (tail m))
