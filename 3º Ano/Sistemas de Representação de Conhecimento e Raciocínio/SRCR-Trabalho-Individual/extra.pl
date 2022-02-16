%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático Individual

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%- EXTRA

% Aqui encontram-se algoritmos que cujas respostas passam sempre por
% todos os nodos, aparecendo também diversas vezes a garagem e a 
% lixeira nos caminhos. É de notar que estes algoritmos apenas
% funcionam com grafos mais pequenos, tais como o apresentado aqui.
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais

:- set_prolog_flag(answer_write_options, [max_depth(0)]).
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).

:- style_check(-singleton).
:- style_check(-discontiguous).
:- dynamic processed/1.

%----------------------------------------------------------------------------------------------------------------%  
%============================================  Base de Conhecimento  ============================================\
%----------------------------------------------------------------------------------------------------------------% 

estimativa(a,5).
estimativa(b,4).
estimativa(c,4).
estimativa(d,3).
estimativa(e,7).
estimativa(f,4).
estimativa(g,2).
estimativa(s,10).
estimativa(t,0).

aresta(s,a,2).
aresta(a,b,2).
aresta(b,c,2).
aresta(c,d,3).
aresta(d,t,3).

aresta(s,e,2).
aresta(e,f,5).
aresta(f,g,2).
aresta(g,t,2).

aresta(e,g,15).
aresta(t,f,30).

% caixote(id, lat, lon, rua, totalLixos, totalOrganico, totalPapel, totalEmbalagens, totalVidro).
caixote(a, -9.149021, 38.711371, 'rua', 250, 60, 0, 20, 0).
caixote(b, -9.149021, 38.711371, 'rua', 500, 200, 0, 0, 0).
caixote(c, -9.149021, 38.711371, 'rua', 150, 0, 60, 0, 20).
caixote(d, -9.149021, 38.711371, 'rua', 250, 0, 8, 0, 0).
caixote(e, -9.149021, 38.711371, 'rua', 122, 0, 0, 45, 40).
caixote(f, -9.149021, 38.711371, 'rua', 600, 220, 0, 50, 0).
caixote(g, -9.149021, 38.711371, 'rua', 500, 0, 0, 0, 0).

garagem(s).
lixeira(t).

inicio(s).
fim(t).


%----------------------------------------------------------------------------------------------------------------%  
%===========================================  Predicados Auxiliares  ============================================\
%----------------------------------------------------------------------------------------------------------------% 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Vè se é membro, caso seja a garagem ou lixeira dá falso
member2(t, _):- false.
member2(s, _):- false.
member2(X, L) :- 
    X \= t,
    X \= s,
    member(X, L).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se é um nó válido
validNode(X) :- 
    caixote(X, Lat, Long, Rua, Lixo, Organico, Papel, Embalagem, Vidro),
    \+ processed(X).    

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Obter nodos
getNodes(Nodes) :- findall(Id, validNode(Id), Nodes).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Seleciona elemento
seleciona(E, [E|Xs], Xs).
seleciona(E, [X|Xs], [X|Ys]) :- seleciona(E, Xs, Ys).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Encontra adjacente com custo
adjacenteCusto(Nodo, NodoProx, Custo):-
    aresta(Nodo,NodoProx,Custo).
adjacenteCusto(Nodo, NodoProx, Custo):-
    aresta(NodoProx,Nodo,Custo).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se todos foram visitados
allVisitted(_, []).
allVisitted(Hist, [X|Xs]):-
    member(X, Hist),
    allVisitted(Hist, Xs).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Devolve o primeiro elemento de uma lista
head([],[]).
head([H|_],H).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Calcula o comprimento de uma lisya que se encontra num tuplo
lengthTuple( ([],_),0 ).
lengthTuple( ([_|L],_),N ) :-
    lengthTuple( (L,_),N1 ),
    N is N1+1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o segundo elemento de um tuplo
getSecondTuple((A,B), B). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o primeiro elemento de um tuplo
getFirstTuple((A,B), A). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Divide Listas (utilzado para merge sort)
divide([],[],[]).
divide([H],[H],[]).
divide([H1,H2|T],[H1|T1],[H2|T2]):- divide(T,T1,T2).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordenação de caminhos através de mergesort
% Número : serve para saber qual é o modo de pesquisa que se quer
% T : Tipo de lixo, usado nas funcionalidades uqe precisam do mesmo
mergesort(1,T,[],[]).
mergesort(1,T,[X],[X]).
mergesort(1,T,List,Sorted):- 
    divide(List,L1,L2),
    mergesort(1,T,L1,Sorted1),
    mergesort(1,T,L2,Sorted2),
    mergeTrashQuantity(T,Sorted1,Sorted2,Sorted).

mergesort(2,T,[],[]).
mergesort(2,T,[X],[X]).
mergesort(2,T,List,Sorted):- 
    divide(List,L1,L2),
    mergesort(2,T,L1,Sorted1),
    mergesort(2,T,L2,Sorted2),
    mergeAvg(T,Sorted1,Sorted2,Sorted).

mergesort(3,T,[],[]).
mergesort(3,T,[X],[X]).
mergesort(3,T,List,Sorted):- 
    divide(List,L1,L2),
    mergesort(3,T,L1,Sorted1),
    mergesort(3,T,L2,Sorted2),
    mergeTrashPoints(T,Sorted1,Sorted2,Sorted).

mergesort(4,T,[],[]).
mergesort(4,T,[X],[X]).
mergesort(4,T,List,Sorted):- 
    divide(List,L1,L2),
    mergesort(4,T,L1,Sorted1),
    mergesort(4,T,L2,Sorted2),
    mergeFastest(T,Sorted1,Sorted2,Sorted).

mergesort(5,T,[],[]).
mergesort(5,T,[X],[X]).
mergesort(5,T,List,Sorted):- 
    divide(List,L1,L2),
    mergesort(5,T,L1,Sorted1),
    mergesort(5,T,L2,Sorted2),
    mergeEficient(T,Sorted1,Sorted2,Sorted).

%----------------------------------------------------------------------------------------------------------------%  
%================================================  Depth Search  ================================================\
%----------------------------------------------------------------------------------------------------------------%  

% SkipNodes serve para não considerar algum ponto para o caminho, por default SkipNodes = []
dfSolve5(Path/Custo, SkipNodes) :-
    getNodes(Nodes),
    dfCost5(s, [s], Nodes, SkipNodes, Path, Custo).


dfCost5(t, Hist, Nodes,SkipNodes, Path, 0):-
    reverse(Hist,Path),
    allVisitted(Hist, Nodes).

dfCost5(Nodo, Hist, Nodes, SkipNodes, Path, Custo):-
    adjacenteCusto(Nodo, NodoProx, CustoMovimento),
    \+ member2(NodoProx, Hist),
    \+ member2(NodoProx, SkipNodes),
    dfCost5(NodoProx,[NodoProx|Hist], Nodes, SkipNodes, Path, Custo2),
    Custo is CustoMovimento + Custo2.

%----------------------------------------------------------------------------------------------------------------%
%================================================  Gready Search  ===============================================\
%----------------------------------------------------------------------------------------------------------------%

resolveGulosa2(Nodo, Caminho/Custo) :-
    getNodes(Nodes),
    estimativa(Nodo, Estimativa),
    agulosa2([[Nodo]/0/Estimativa], CaminhoInverso/Custo/_),
    allVisitted(CaminhoInverso, Nodes),
    reverse(CaminhoInverso, Caminho).

agulosa2(Caminhos, Caminho) :-
    obtem_melhor_g2(Caminhos, Caminho),
    Caminho = [Nodo|_]/_/_,fim(Nodo).

agulosa2(Caminhos, SolucaoCaminho) :-
    obtem_melhor_g2(Caminhos, MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expandeGulosa2(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    agulosa2(NovoCaminhos, SolucaoCaminho).

obtem_melhor_g2([Caminho], Caminho) :- !.

obtem_melhor_g2([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :-
	Est1 =< Est2, !,
	obtem_melhor_g2([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).

obtem_melhor_g2([_|Caminhos], MelhorCaminho) :- 
	obtem_melhor_g2(Caminhos, MelhorCaminho).

expandeGulosa2(Caminho, ExpCaminhos) :-
	findall(NovoCaminho, adjacenteG2(Caminho,NovoCaminho), ExpCaminhos).

adjacenteG2([Nodo|Caminho]/Custo/_, [ProxNodo,Nodo|Caminho]/NovoCusto/Est) :-
	adjacenteCusto(Nodo, ProxNodo, PassoCusto),\+ member2(ProxNodo, Caminho),
	NovoCusto is Custo + PassoCusto,
	estimativa(ProxNodo, Est).


%----------------------------------------------------------------------------------------------------------------%
%===================================================  A Star  ===================================================\
%----------------------------------------------------------------------------------------------------------------%

resolveAEstrela2(Nodo, Caminho/Custo) :-
    getNodes(Nodes),
    estimativa(Nodo, Estimativa),
    aestrela2([[Nodo]/0/Estima], CaminhoInverso/Custo/_),
    allVisitted(CaminhoInverso, Nodes),
    reverse(CaminhoInverso, Caminho).

aestrela2(Caminhos, Caminho) :-
	obtem_melhor2(Caminhos, Caminho),   
	Caminho = [Nodo|_]/_/_,fim(Nodo).

aestrela2(Caminhos, SolucaoCaminho) :-
    obtem_melhor2(Caminhos, MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expandeAEstrela2(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    aestrela2(NovoCaminhos, SolucaoCaminho).

obtem_melhor2([Caminho], Caminho) :- !.

obtem_melhor2([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :-
	Custo1 + Est1 =< Custo2 + Est2, !,
	obtem_melhor2([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).
	
obtem_melhor2([_|Caminhos], MelhorCaminho) :- 
	obtem_melhor2(Caminhos, MelhorCaminho).
    
expandeAEstrela2(Caminho, ExpCaminhos) :-
    findall(NovoCaminho, adjacenteG2(Caminho,NovoCaminho), ExpCaminhos). %adjacenteG/2 da Alínea anterior

adjacenteCusto3(Nodo, NodoProx, Custo):-
    aresta(Nodo,NodoProx,Custo),
    NodoProx \= s,
    NodoProx \= t,
    caixote(NodoProx, _, _, _, X, Y, W, Z, Glass), 
    W > 0.

adjacenteCusto3(Nodo, NodoProx, Custo):-
    aresta(NodoProx,Nodo,Custo),
    NodoProx \= s,
    NodoProx \= t,
    caixote(NodoProx, _, _, _, X, Y, W, Z, Glass), 
    W > 0.

adjacenteCusto3(Nodo, s, Custo):-
    aresta(s,Nodo,Custo).

adjacenteCusto3(Nodo, s, Custo):-
    aresta(Nodo,s,Custo).

adjacenteCusto3(Nodo, t, Custo):-
    aresta(t,Nodo,Custo).

adjacenteCusto3(Nodo, t, Custo):-
    aresta(Nodo,t,Custo).


%----------------------------------------------------------------------------------------------------------------%  
%  Pesquisa em profundidade - seletiva tipo de lixo                                                              \
%----------------------------------------------------------------------------------------------------------------%  

dfSolveByType(Path/Custo, Type) :-
    getNodes(Nodes),
    dfCostT(s, [s], Nodes, Type, Path, Custo).

dfCostT(t, Hist, Nodes,Type, Path, 0):-
    reverse(Hist,Path),
    findall(T, isTrashType(T, Type), K),
    allVisitted(Hist, K).

dfCostT(Nodo, Hist, Nodes, Type, Path, Custo):-
    adjacenteCusto(Nodo, NodoProx, CustoMovimento),
    \+ member2(NodoProx, Hist),
    dfCostT(NodoProx,[NodoProx|Hist], Nodes, Type, Path, Custo2),
    Custo is CustoMovimento + Custo2.


%----------------------------------------------------------------------------------------------------------------%  
%  Indicador produtividade : Quantidade recolhida / Pontos totais percorridos                                    \
%----------------------------------------------------------------------------------------------------------------%  

% Type - Tipo de lixo pretendido
% H - Caminho escolhido
% Q/Len - Quantidade lixo recolhido do tipo Type / Pontos totais de R
maxTrashQuantity(Type, H , Q/Len) :- 
    findall(Path, dfSolve5(Path/Cost, []), L), 
    mergesort(1,Type,L,R),
    head(R,H),
    countTrashQuantity(Type,H,Q),
    length(H, Len).  
   

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena os caminhos por mair quantidade de lixo de um tipo
mergeTrashQuantity(Type,X,[],X):- !.
mergeTrashQuantity(Type,[],X,X):- !.
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H1|T3]):-
    countTrashQuantity(Type,H1,TQ1),
    length(H1,L1),
    countTrashQuantity(Type,H2,TQ2),
    length(H2,L2),
    TQ1 / L1 >= TQ2 / L2,
    mergeTrashQuantity(Type,T1,[H2|T2],T3).
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    countTrashQuantity(Type,H1,TQ1),
    length(H1,L1),
    countTrashQuantity(Type,H2,TQ2),
    length(H2,L2),
    TQ1 / L1 < TQ2 / L2,
    mergeTrashQuantity(Type,[H1|T1],T2,T3).
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeTrashQuantity(Type,T1,T2,T3).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Conta a quantidade de lixo de um tipo num percurso
countTrashQuantity(_,[],0).
countTrashQuantity(lixo, [T|List], N) :- 
    caixote(T, _, _, _, X, _, _, _, _), 
    countTrashQuantity(lixo, List, N1),
    N is N1 + X.
countTrashQuantity(organico, [T|List], N) :-
    caixote(T, _, _, _, _, X, _, _, _), 
    countTrashQuantity(organico, List, N1),
    N is N1 + X.      
countTrashQuantity(papel, [T|List], N) :- 
    caixote(T, _, _, _, _, _, X, _, _), 
    countTrashQuantity(papel, List, N1),
    N is N1 + X. 
countTrashQuantity(embalagem, [T|List], N) :- 
    caixote(T, _, _, _, _, _, _, X, _), 
    countTrashQuantity(embalagem, List, N1),
    N is N1 + X.                                               
countTrashQuantity(vidro, [T|List], N) :- 
    caixote(T, _, _, _, _, _, _, _, X), 
    countTrashQuantity(vidro, List, N1),
    N is N1 + X.   
countTrashQuantity(Type, [T|List], N) :-
    \+ isTrashType(T, Type),
    countTrashQuantity(Type, List, N).


%----------------------------------------------------------------------------------------------------------------%  
%  Indicador produtividade : Menor quantidade percorrida entre pontos                                            \
%----------------------------------------------------------------------------------------------------------------%  

% H - Caminho escolhido
% A - Distância média entre dois pontos
distanceBetweenAVG(H,A) :- 
    findall((Path,Cost), dfSolve5(Path/Cost, []), L),
    mergesort(2,T,L,R),
    head(R,H),
    lengthTuple(H, Len),
    getSecondTuple(H,Cost),
    A is Cost / (Len - 1).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Organiza os caminhos de acordo com menor distância media entre pontos
mergeAvg(Type,X,[],X):- !.
mergeAvg(Type,[],X,X):- !.
mergeAvg(Type,[H1|T1],[H2|T2],[H1|T3]):-
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    lengthTuple(H1,L1),
    lengthTuple(H2,L2),
    C1 / (L1 - 1) =< C2 / (L2 - 1),
    mergeAvg(Type,T1,[H2|T2],T3).
mergeAvg(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    lengthTuple(H1,L1),
    lengthTuple(H2,L2),
    C1 / (L1 - 1) > C2 / (L2 - 1),
    mergeAvg(Type,[H1|T1],T2,T3).
mergeAvg(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeAvg(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%  
% Calcula o percurso com mais pontos de recolha de um determinado tipo em comparação com os nodos percorridos    \
%----------------------------------------------------------------------------------------------------------------%  

% Type - Tipo de lixo pretendido
% H - Caminho escolhido
% Q/Len - Pontos do tipo Type / Pontos totais de R
maxTrashPointsType(Type, H, Q/Len) :-                  
    findall(Path, dfSolve5(Path/Cost, []), L), 
    mergesort(3,Type,L,R),
    head(R,H),
    countTrashPoints(Type,H,Q),
    length(H, Len).  

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena por mais pontos de um determinado tipo
mergeTrashPoints(Type,X,[],X):- !.
mergeTrashPoints(Type,[],X,X):- !.
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H1|T3]):-
    length(H1,L1),
    countTrashPoints(Type,H1,TP1),
    length(H2,L2),
    countTrashPoints(Type,H2,TP2),
    TP1 / L1 >= TP2 / L2,
    mergeTrashPoints(Type,T1,[H2|T2],T3).
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    length(H1,L1),
    countTrashPoints(Type,H1,TP1),
    length(H2,L2),
    countTrashPoints(Type,H2,TP2),
    TP1 / L1 < TP2 / L2,
    mergeTrashPoints(Type,[H1|T1],T2,T3).
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeTrashPoints(Type,T1,T2,T3).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Conta pontos de recolha de um tipo num percurso
countTrashPoints(_,[],0).
countTrashPoints(Type, [T|List], N) :- 
    isTrashType(T, Type), 
    countTrashPoints(Type, List, N1), 
    N is N1 + 1. 
countTrashPoints(Type, [T|List], N) :- 
    \+ isTrashType(T, Type), 
    countTrashPoints(Type, List, N). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se um caixote tem o tipo pretendido
isTrashType(T, lixo) :-      caixote(T, _, _, _, X, _, _, _, _), X > 0.
isTrashType(T, organico) :-  caixote(T, _, _, _, _, X, _, _, _), X > 0.
isTrashType(T, papel) :-     caixote(T, _, _, _, _, _, X, _, _), X > 0.
isTrashType(T, embalagem) :- caixote(T, _, _, _, _, _, _, X, _), X > 0.
isTrashType(T, vidro) :-     caixote(T, _, _, _, _, _, _, _, X), X > 0.

isNotTrash(T) :- inicio(T).
isNotTrash(T) :- fim(T).


%----------------------------------------------------------------------------------------------------------------%
% Caminho mais rápido (menos distancia)                                                                          \
%----------------------------------------------------------------------------------------------------------------%

% H - Caminho escolhido com respetiva distância
fasttestPath(H) :- 
    findall((Path,Cost), dfSolve5(Path/Cost, []), L),
    mergesort(4,Type,L,R),
    head(R,H).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena caminhos por menor distancia
mergeFastest(Type,X,[],X):- !.
mergeFastest(Type,[],X,X):- !.
mergeFastest(Type,[H1|T1],[H2|T2],[H1|T3]):-
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    C1 =< C2,
    mergeFastest(Type,T1,[H2|T2],T3).
mergeFastest(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    C1 > C2,
    mergeFastest(Type,[H1|T1],T2,T3).
mergeFastest(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeFastest(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%
% Mais eficiente : Recolhe mais lixo por km percorrido                                                           \
%----------------------------------------------------------------------------------------------------------------%

% Type - Tipo de lixo pretendido
% P - Caminho escolhido
% Q/C - Quantidade de lixo recolhida / Distancia percorrida
% Res - Resultado da divisão Q/C
mostEfficient(Type,P,Q/C, Res) :- 
    findall((Path,Cost), dfSolve5(Path/Cost, []), L), 
    mergesort(5,Type,L,R),
    head(R,H),
    getFirstTuple(H,P),
    getSecondTuple(H,C),
    countTrashQuantity(Type,P,Q),
    Res is Q/C.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena caminhos por maior eficiencia
mergeEficient(Type,X,[],X):- !.
mergeEficient(Type,[],X,X):- !.
mergeEficient(Type,[H1|T1],[H2|T2],[H1|T3]):-
    getFirstTuple(H1,P1), 
    getFirstTuple(H2,P2), 
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    countTrashQuantity(Type,P1,TQ1),
    countTrashQuantity(Type,P2,TQ2),
    TQ1 / C1 >= TQ2 / C2,
    mergeEficient(Type,T1,[H2|T2],T3).
mergeEficient(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    getFirstTuple(H1,P1), 
    getFirstTuple(H2,P2), 
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    countTrashQuantity(Type,P1,TQ1),
    countTrashQuantity(Type,P2,TQ2),
    TQ1 / C1 < TQ2 / C2,
    mergeEficient(Type,[H1|T1],T2,T3).
mergeEficient(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeEficient(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%
% Circuio que passa menos vezes na lixeira e  garagem                                                            \
%----------------------------------------------------------------------------------------------------------------%

% R - Caminho escolhido
% Q/Len - Pontos do tipo Type / Pontos totais de R
minNotTrashPoints(R, Q/Len) :-                  
    findall(Path, dfSolve5(Path/Cost, []), L),       
    minNotTRASH(L, R), 
    countNotTrashPoints(R,Q),
    length(R,Len).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Encontra o caminho que passa menos vezes na garagem e lixeira
minNotTRASH([Path], Path) :- !, true.
minNotTRASH([Path | List], Path) :-
    length(Path,L1),
    countNotTrashPoints(Path, T1),
	minNotTRASH(List, NewPath),
    countNotTrashPoints(NewPath, T2),
    length(NewPath,L2),
	T1 / L1 =< T2 / L2, !.
minNotTRASH([_ | List], New) :-
	minNotTRASH(List, New).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Conta pontos que não são de recolha
countNotTrashPoints([],0).
countNotTrashPoints([T|List], N) :- 
    isNotTrash(T), 
    countNotTrashPoints(List, N1), 
    N is N1 + 1. 
countNotTrashPoints([T|List], N) :- 
    \+ isNotTrash(T), 
    countNotTrashPoints(List, N). 