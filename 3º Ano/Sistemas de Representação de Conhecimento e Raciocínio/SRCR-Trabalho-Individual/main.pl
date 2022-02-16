%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático Individual

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%- MAIN

% Aqui encontram-se a implementação dos algoritmos e funcionalidades
% propostas no enuncidado do trabalho prático individual.
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Includes
:- include('dataset.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais
:- set_prolog_flag(answer_write_options, [max_depth(0)]).
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( all_warnings,off ).

:- style_check(-singleton).
:- style_check(-discontiguous).
:- dynamic processed/1.

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
% Predicado para obter o primeiro elemento de um tuplo
getFirstTuple((A,B), A). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o segundo elemento de um tuplo
getSecondTuple((A,B), B). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o primeiro elemento de um triplo
getFirstTriplet((A,B,C), A). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o segundo elemento de um triplo
getSecondTriplet((A,B,C), B). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado para obter o terceiro elemento de um triplo
getThirdTriplet((A,B,C), C). 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Divide Listas (utilzado para merge sort)
divide([],[],[]).
divide([H],[H],[]).
divide([H1,H2|T],[H1|T1],[H2|T2]):- divide(T,T1,T2).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Soma quantidade de caminhos 
soma(Caminho, Tipo, Quantidade):- 
    somaAux(Caminho, Tipo, 0, Quantidade).

somaAux([], _, Quantidade, Quantidade).
somaAux([X|Xs], Tipo, Quantidade, QuantidadeFinal):-
    getTrashByType(X, Tipo, QuantidadeNodo),
    Quantidade2 is Quantidade + QuantidadeNodo,
    somaAux(Xs, Tipo, Quantidade2, QuantidadeFinal).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Devolve a quantidade de lixo de um determinado tipo de um caixote
getTrashByType(Node, lixo, X) :-      caixote(Node, _, _, _, X, _, _, _, _).
getTrashByType(Node, organico, X) :-  caixote(Node, _, _, _, _, X, _, _, _).
getTrashByType(Node, papel, X) :-     caixote(Node, _, _, _, _, _, X, _, _).
getTrashByType(Node, embalagem, X) :- caixote(Node, _, _, _, _, _, _, X, _).
getTrashByType(Node, vidro, X) :-     caixote(Node, _, _, _, _, _, _, _, X).

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

dfSolve(Path/Custo/Quantidade, Tipo) :-
    dfCost(s, Tipo, [s], Path, Custo, Quantidade).

dfCost(t, Tipo, Hist, Path, 0, 0):-
    reverse(Hist, Path).

dfCost(Nodo, Tipo, Hist, Path, Custo, Quantidade):-
    adjacenteCusto(Nodo, NodoProx, CustoMovimento),
    getTrashByType(Nodo, Tipo, QuantidadeNodo),
    \+ member(NodoProx, Hist),
    dfCost(NodoProx, Tipo, [NodoProx|Hist], Path, Custo2, Quantidade2),
    Custo is CustoMovimento + Custo2,
    Quantidade is QuantidadeNodo + Quantidade2,
    Quantidade < 15000.


%----------------------------------------------------------------------------------------------------------------%
%============================================  Depth Search Limitada  ===========================================\
%----------------------------------------------------------------------------------------------------------------%

dflSolve(Path/Custo/Quantidade, Tipo, Limit) :-
    dflCost(s, Tipo, [s], Path, Custo, Quantidade, Limit).

dflCost(t, Tipo, Hist, Path, 0, 0, Limit):-
    reverse(Hist, Path).

dflCost(Nodo, Tipo, Hist, Path, Custo, Quantidade, Limit):-
    Limit > 0,
    adjacenteCusto(Nodo, NodoProx, CustoMovimento),
    getTrashByType(Nodo, Tipo, QuantidadeNodo),
    \+ member(NodoProx, Hist),
    Limit2 is Limit - 1,
    dflCost(NodoProx, Tipo, [NodoProx|Hist], Path, Custo2, Quantidade2, Limit2),
    Custo is CustoMovimento + Custo2,
    Quantidade is QuantidadeNodo + Quantidade2,
    Quantidade < 15000.


%----------------------------------------------------------------------------------------------------------------%
%================================================  Breadth First  ===============================================\
%----------------------------------------------------------------------------------------------------------------%

breadth_first(Path/Custo/Quantidade, Tipo):- 
    breadth_first_aux([[s]], Path, Tipo, Quantidade),
    calcula_distancia_caminho(Path,Custo).



breadth_first_aux([[t|Visitados]|_], Path, Tipo, Quantidade):- 
    Visitados = [Start|_], 
    adjacenteCusto(Start,t,_),
    reverse([t|Visitados], Path),
    soma(Path, Tipo, Quantidade),
    Quantidade < 15000.


breadth_first_aux([Visitados|Restantes], Path, Tipo, Quantidade) :-                    
    Visitados = [Start|_],            
    findall( X,
        ( adjacenteCusto(Start,X,_), \+ member(X, Visitados) ),
        [T|Extendido]),
    maplist( adiciona_queue(Visitados), [T|Extendido], VisitadosExtendido),      
    append( Restantes, VisitadosExtendido, QueueAtualizada),  
    breadth_first_aux( QueueAtualizada, Path, Tipo, Quantidade ).


adiciona_queue( A, B, [B|A]).

calcula_distancia_caminho([Gid1,Gid2|T],Distancia):-
    adjacenteCusto(Gid1,Gid2,DistanciaLigacao),
    calcula_distancia_caminho([Gid2|T],DistanciaAcumulada),
    Distancia is DistanciaLigacao + DistanciaAcumulada.
  
calcula_distancia_caminho([_],0).
calcula_distancia_caminho([],0).

%----------------------------------------------------------------------------------------------------------------%
%================================================  Gready Search  ===============================================\
%----------------------------------------------------------------------------------------------------------------%

adjacenteGulosa([Nodo|Caminho]/Custo/_, [ProxNodo,Nodo|Caminho]/NovoCusto/Est) :-
	adjacenteCusto(Nodo, ProxNodo, PassoCusto),
    \+ member2(ProxNodo, Caminho),
	NovoCusto is Custo + PassoCusto,
	estimativa(ProxNodo, Est).

resolveGulosa(Nodo, Caminho/Custo/Quantidade, Tipo) :-
    estimativa(Nodo, Estimativa),
    agulosa([[Nodo]/0/Estimativa], CaminhoInverso/Custo/_, Tipo, Quantidade),
    reverse(CaminhoInverso, Caminho).

agulosa(Caminhos, Caminho, Tipo, Quantidade) :-
    melhorGulosa(Caminhos, Caminho),
    Caminho = [Nodo|Xs]/_/_,
    soma([Nodo|Xs], Tipo, Quantidade),
    Quantidade < 15000,
    fim(Nodo).

agulosa(Caminhos, SolucaoCaminho, Tipo, Quantidade) :-
    melhorGulosa(Caminhos, MelhorCaminho),
    % writeln(MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expandeGulosa(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    agulosa(NovoCaminhos, SolucaoCaminho, Tipo, Quantidade).

melhorGulosa([Caminho], Caminho) :- !.

melhorGulosa([Caminho1/Custo1/Est1,_/Custo3/Est3|Caminhos], MelhorCaminho) :-
	Est1 =< Est3, !,
	melhorGulosa([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).

melhorGulosa([_|Caminhos], MelhorCaminho) :- 
	melhorGulosa(Caminhos, MelhorCaminho).

expandeGulosa(Caminho, ExpCaminhos) :-
	findall(NovoCaminho, adjacenteGulosa(Caminho,NovoCaminho), ExpCaminhos).

%----------------------------------------------------------------------------------------------------------------%
%===================================================  A Star  ===================================================\
%----------------------------------------------------------------------------------------------------------------%

adjacenteAS([Nodo|Caminho]/Custo/_, [ProxNodo,Nodo|Caminho]/NovoCusto/Est) :-
	adjacenteCusto(Nodo, ProxNodo, PassoCusto),
    \+ member(ProxNodo, Caminho),
	NovoCusto is Custo + PassoCusto,
	estimativa(ProxNodo, Est).

resolveAEstrela(Nodo, Caminho/Custo/Quantidade, Tipo) :-
    estimativa(Nodo, Estimativa),
    aestrela([[Nodo]/0/Estima], CaminhoInverso/Custo/_, Tipo, Quantidade),
    reverse(CaminhoInverso, Caminho).

aestrela(Caminhos, Caminho, Tipo, Quantidade) :-
	melhorAS(Caminhos, Caminho),   
	Caminho = [Nodo|Xs]/_/_,
    soma([Nodo|Xs], Tipo, Quantidade),
    Quantidade < 15000,
    fim(Nodo).

aestrela(Caminhos, SolucaoCaminho, Tipo, Quantidade) :-
    melhorAS(Caminhos, MelhorCaminho),
    seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
    expandeaestrela(MelhorCaminho, ExpCaminhos),
    append(OutrosCaminhos, ExpCaminhos, NovoCaminhos),
    aestrela(NovoCaminhos, SolucaoCaminho, Tipo, Quantidade).

melhorAS([Caminho], Caminho) :- !.

melhorAS([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :-
	Custo1 + Est1 =< Custo2 + Est2, !,
	melhorAS([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).
	
melhorAS([_|Caminhos], MelhorCaminho) :- 
	melhorAS(Caminhos, MelhorCaminho).
    
expandeaestrela(Caminho, ExpCaminhos) :-
    findall(NovoCaminho, adjacenteAS(Caminho,NovoCaminho), ExpCaminhos). 


%----------------------------------------------------------------------------------------------------------------%  
%  Indicador produtividade : Máxima Quantidade Recolhida                                                         \
%----------------------------------------------------------------------------------------------------------------%  

% Type - Tipo de lixo pretendido
% H - Caminho escolhido / Lixo máximo
maxTrashQuantity(Type, H) :- 
    findall((P,Q), (dfSolve(P/C/Q, Type), Q > 800), L),
    mergesort(1,Type,L,R),
    head(R,H).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena os caminhos por mair quantidade de lixo de um tipo
mergeTrashQuantity(Type,X,[],X):- !.
mergeTrashQuantity(Type,[],X,X):- !.
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H1|T3]):-
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    C1 >= C2,
    mergeTrashQuantity(Type,T1,[H2|T2],T3).
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    getSecondTuple(H1,C1),
    getSecondTuple(H2,C2),
    C1 < C2,
    mergeTrashQuantity(Type,[H1|T1],T2,T3).
mergeTrashQuantity(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeTrashQuantity(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%  
%  Indicador produtividade : Menor quantidade percorrida entre pontos                                            \
%----------------------------------------------------------------------------------------------------------------%  

% T - Tipo de lixo
% H - Caminho escolhido
% A - Distância média entre dois pontos
distanceBetweenAVG(T,H,A) :- 
    findall((Path,Cost), dfSolve(Path/Cost/Quant, T), L),
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
% Calcula o percurso com mais pontos de recolha de um determinado tipo                                           \
%----------------------------------------------------------------------------------------------------------------%  

% Type - Tipo de lixo pretendido
% H - Caminho escolhido
% Q - Pontos do tipo Type
maxTrashPointsType(Type, H, Q) :-                  
    findall(Path, dfSolve(Path/Cost/Quant, Type), L), 
    mergesort(3,Type,L,R),
    head(R,H),
    countTrashPoints(Type,H,Q).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena por mais pontos de um determinado tipo
mergeTrashPoints(Type,X,[],X):- !.
mergeTrashPoints(Type,[],X,X):- !.
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H1|T3]):-
    countTrashPoints(Type,H1,TP1),
    countTrashPoints(Type,H2,TP2),
    TP1 >= TP2,
    mergeTrashPoints(Type,T1,[H2|T2],T3).
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H2|T3]):- 
    countTrashPoints(Type,H1,TP1),
    countTrashPoints(Type,H2,TP2),
    TP1 < TP2,
    mergeTrashPoints(Type,[H1|T1],T2,T3).
mergeTrashPoints(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeTrashPoints(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%
% Caminho mais rápido (menos distancia)                                                                          \
%----------------------------------------------------------------------------------------------------------------%

% H - Caminho escolhido com respetiva distância
fasttestPath(Type,H) :- 
    findall((Path,Cost), dfSolve(Path/Cost/Quant, Type), L),
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
% H - Caminho/Custo/Quantidade lixo
% Res - Resultado da divisão Q/C
mostEfficient(Type, H, Res) :- 
    findall((P,C,Q), dfSolve(P/C/Q, Type), L), 
    mergesort(5,Type,L,R),
    head(R,H),
    getSecondTriplet(H,Cost),
    getThirdTriplet(H,Quant),
    Res is Quant/Cost.
  
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Ordena caminhos por maior eficiencia
mergeEficient(Type,X,[],X):- !.
mergeEficient(Type,[],X,X):- !.
mergeEficient(Type,[H1|T1],[H2|T2],[H1|T3]):-
    getSecondTriplet(H1,C1),
    getSecondTriplet(H2,C2),
    getThirdTriplet(H1,TQ1),
    getThirdTriplet(H2,TQ2),
    TQ1 / C1 >= TQ2 / C2,
    mergeEficient(Type,T1,[H2|T2],T3).
mergeEficient(Type,[H1|T1],[H2|T2],[H2|T3]):-  
    getSecondTriplet(H1,C1),
    getSecondTriplet(H2,C2),
    getThirdTriplet(H1,TQ1),
    getThirdTriplet(H2,TQ2),
    TQ1 / C1 < TQ2 / C2,
    mergeEficient(Type,[H1|T1],T2,T3).
mergeEficient(Type,[H1|T1],[H2|T2],[H1,H2|T3]):- 
    mergeEficient(Type,T1,T2,T3).


%----------------------------------------------------------------------------------------------------------------%
% Estatísticas                                                                                                   \
%----------------------------------------------------------------------------------------------------------------%

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Estatísticas Procura em Profundidade
statisticsDF(R,T) :-
    statistics(global_stack, [G1,L1]),
    time(dfSolve(R,T)),
    statistics(global_stack, [G2,L2]),
    Res is G2 - G1,
    write("Memory: "), 
    write(Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Estatísticas Procura em largura
statisticsBF(R,T) :-
    statistics(global_stack, [G1,L1]),
    time(breadth_first(R,T)),
    statistics(global_stack, [G2,L2]),
    Res is G2 - G1,
    write("Memory: "), 
    write(Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Estatísticas Procura em Profundidade Limitada
statisticsDFL(R,T,L) :-
    statistics(global_stack, [G1,L1]),
    time(dflSolve(R,T,L)),
    statistics(global_stack, [G2,L2]),
    Res is G2 - G1,
    write("Memory: "), 
    write(Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Estatísticas Procura Gulosa
statisticsG(R,T) :-
    statistics(global_stack, [G1,L1]),
    time(resolveGulosa(s,R,T)),
    statistics(global_stack, [G2,L2]),
    Res is G2 - G1,
    write("Memory: "), 
    write(Res).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Estatísticas Procura A Estrela
statisticsAS(R,T) :-
    statistics(global_stack, [G1,L1]),
    time(resolveAEstrela(s,R,T)),
    statistics(global_stack, [G2,L2]),
    Res is G2 - G1,
    write("Memory: "), 
    write(Res).