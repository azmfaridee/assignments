% Prolog program for the water jug problem

% predicate to get a specific item from a list by index
member(0, SearchFor, [SearchFor|_]).
member(Index, SearchFor, [_|Tail]) :-
	member(Previous, SearchFor, Tail),
	Index is Previous + 1.

f1(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	A<5,
	NewPrevious = [State|Previous],
	\+member([5,B],NewPrevious),
	Output = [[[5,B],NewPrevious]|List].
f1(X,List,Output):-
	Output=List.


f2(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	B<2,
	NewPrevious = [State|Previous],
	\+member([A,2],NewPrevious),
	Output = [[[A,2],NewPrevious]|List].
f2(X,List,Output):-
	Output=List.


e1(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	A>0,
	NewPrevious = [State|Previous],
	\+member([0,B],NewPrevious),
	Output = [[[0,B],NewPrevious]|List].	
e1(X,List,Output):-
	Output=List.


e2(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	B>0,
	NewPrevious = [State|Previous],
	\+member([A,0],NewPrevious),
	Output = [[[A,0],NewPrevious]|List].	
e2(X,List,Output):-
	Output=List.


t12(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	Bneed is 2-B,
	A>0,
	A>=Bneed,
	Aa is A-Bneed,
	NewPrevious = [State|Previous],
	\+member([Aa,2],NewPrevious),
	Output = [[[Aa,2],NewPrevious]|List].
	
t12(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	Bneed is 2-B,
	A>0,
	A<Bneed,
	Bb is B+A,
	NewPrevious = [State|Previous],
	\+member([0,Bb],NewPrevious),
	Output = [[[0,Bb],NewPrevious]|List].
t12(X,List,Output):-
	Output=List.


t21(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	Aneed is 5-A,
	B>0,
	B>=Aneed,
	Bb is B-Aneed,
	NewPrevious = [State|Previous],
	\+member([5,Bb],NewPrevious),
	Output = [[[5,Bb],NewPrevious]|List].	
t21(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	Aneed is 5-A,
	B>0,
	B<Aneed,
	Aa is A+B,
	NewPrevious = [State|Previous],
	\+member([Aa,0],NewPrevious),
	Output = [[[Aa,0],NewPrevious]|List].	
t21(X,List,Output):-
	Output=List.
	

bfsvisit([],Goal).

bfsvisit(Queue,Goal):-
	[X|QueueTemp] = Queue,
	
	[State,Previous|_]=X,
	\+State==Goal,
	
	f1(X,[],Decendent1),
	e1(X,Decendent1,Decendent2),
	t12(X,Decendent2,Decendent3),
	f2(X,Decendent3,Decendent4),
	e2(X,Decendent4,Decendent5),
	t21(X,Decendent5,Decendent6),
	
	%printList(Decendent6),
	
	append(QueueTemp,Decendent6,NextQueue),
	bfsvisit(NextQueue,Goal).
	
	
bfsvisit(Queue,Goal):-
	[X|QueueTemp] = Queue,
	[State,Previous|_]=X,
	State==Goal,
	nl,write('Path Found :'),nl,
	printList([X]),
	nl.

bfs(A, B, C, D):-
	bfsvisit([ [[A,B],[]] ],[C,D]).
bfs(X, Y):-
	[A, B|_] = X,
	[C, D|_] = Y,
	bfsvisit([ [[A,B],[]] ],[C,D]).
	
	
takeaction(X):-
	[State,Previous|_]=X,
	reverse(Previous, RightPrevious),
	write('Order of fill/empty/transfer: '),
	write(RightPrevious),write(' for the goal: '),write(State).

	
printList([]).
printList(L):-
	L=[H|T],
	takeaction(H),
	printList(T).