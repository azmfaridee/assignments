% Prolog program for the water jug problem

% predicate to get a specific item from a list by index
member(0, SearchFor, [SearchFor|_]).
member(Index, SearchFor, [_|Tail]) :-
	member(Previous, SearchFor, Tail),
	Index is Previous + 1.

% fill up jug 1
f1(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	A<5,
	NewPrevious = [State|Previous],
	\+member([5,B],NewPrevious),
	Output = [[[5,B],NewPrevious]|List].
f1(X,List,Output):-
	Output=List.

% fill up jug 2
f2(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	B<2,
	NewPrevious = [State|Previous],
	\+member([A,2],NewPrevious),
	Output = [[[A,2],NewPrevious]|List].
f2(X,List,Output):-
	Output=List.

% empty jug 1
e1(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	A>0,
	NewPrevious = [State|Previous],
	\+member([0,B],NewPrevious),
	Output = [[[0,B],NewPrevious]|List].	
e1(X,List,Output):-
	Output=List.

% empty jug 2
e2(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	B>0,
	NewPrevious = [State|Previous],
	\+member([A,0],NewPrevious),
	Output = [[[A,0],NewPrevious]|List].	
e2(X,List,Output):-
	Output=List.

% transfer from jug 1 to jug 2
% when jug 1 has more water than what can be transferred
t12(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	AvailableInB is 2-B,
	A>0,
	A>=AvailableInB,
	NewA is A-AvailableInB,
	NewPrevious = [State|Previous],
	\+member([NewA,2],NewPrevious),
	Output = [[[NewA,2],NewPrevious]|List].
% jug 1 has less water than what can be transferred 	
t12(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	AvailableInB is 2-B,
	A>0,
	A<AvailableInB,
	NewB is B+A,
	NewPrevious = [State|Previous],
	\+member([0,NewB],NewPrevious),
	Output = [[[0,NewB],NewPrevious]|List].
t12(X,List,Output):-
	Output=List.

% tansfer from jug 2 to jug 1
% when jug 2 has more data than what can be transferred
t21(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	AvailableInA is 5-A,
	B>0,
	B>=AvailableInA,
	NewB is B-AvailableInA,
	NewPrevious = [State|Previous],
	\+member([5,NewB],NewPrevious),
	Output = [[[5,NewB],NewPrevious]|List].
% when jug 2 has less data than what can be transferred
t21(X,List,Output):-
	[State,Previous|_]=X,
	[A,B|_] = State,
	AvailableInA is 5-A,
	B>0,
	B<AvailableInA,
	NewA is A+B,
	NewPrevious = [State|Previous],
	\+member([NewA,0],NewPrevious),
	Output = [[[NewA,0],NewPrevious]|List].	
t21(X,List,Output):-
	Output=List.
	
% base case
bfsvisit([],Goal).
% main definition
% if we have not reached the goal
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
	
% if we have reached the goal, i think the State == Goal is
% superfluous
bfsvisit(Queue,Goal):-
	[X|QueueTemp] = Queue,
	[State,Previous|_]=X,
	State==Goal,
	nl,write('Path Found :'),nl,
	printList([X]),
	nl.

% two type of the main calling function
bfs(A, B, C, D):-
	bfsvisit([ [[A,B],[]] ],[C,D]).
bfs(X, Y):-
	[A, B|_] = X,
	[C, D|_] = Y,
	bfsvisit([ [[A,B],[]] ],[C,D]).
	
% take action just writes the node in stdout	
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