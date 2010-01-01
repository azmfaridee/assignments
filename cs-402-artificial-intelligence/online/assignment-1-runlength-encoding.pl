updateList(UpdateChar, [], NewList):-
	NewElement = [UpdateChar, 1],
	NewList = [NewElement].
	
updateList(UpdateChar, CurrentList, NewList):-
	[Element|Tail] = CurrentList,
	[Char, Freq|_] = Element,
	Char == UpdateChar,
	NewFreq is Freq + 1,
	NewElement = [Char, NewFreq],
	NewList = [NewElement|Tail].
	
updateList(UpdateChar, CurrentList, NewList):-
	[Element|Tail] = CurrentList,
	[Char, Freq|_] = Element,
	Char \= UpdateChar,
	updateList(UpdateChar, Tail, TempList),
	NewList = [Element | TempList].


encode([], CurrentList, CurrentChar, X):-
	X = CurrentList.

encode(Data, CurrentList, CurrentChar, X):-
	[Char|Tail] = Data,
	Char == CurrentChar,
	updateList(CurrentChar, CurrentList, NewList),
	encode(Tail, NewList, CurrentChar, X).

encode(Data, CurrentList, CurrentChar, X):-
	[Char|Tail] = Data,
	Char \= CurrentChar,
	NewElement = [Char, 1],
	append(CurrentList, [NewElement], NewList),
	encode(Tail, NewList, Char, X).
	
encode(List, X):-
	encode(List, [], 1, X).
	
run:-
	encode([a, a, b, c, c, c, a, d, d], X).
	