map_build([], node(node_v(_, _), 3)) :- !.

map_build(ListMap, TreeMap) :-
	build_ind(ListMap, 1),
	length(ListMap, Len),
	build_map(1, Len, Res),
	TreeMap = Res,
	delete_ind(1, Len), !.

build_map(Left, Right, Res) :-
	Right - Left =:= 0,
	Mid is div((Right + Left), 2),
	arrPos(Mid, Value), Res = node(Value, 1), !.


build_map(Left, Right, Res) :-
	Right - Left =:= 1,
	build_map(Left, Left, ResL),
	arrPos(Right, Value), Res = node(Value, 2, ResL), !.

build_map(Left, Right, Res) :-
	Mid is div((Left + Right), 2),
	MidL is Mid - 1, MidR is Mid + 1,
	build_map(Left, MidL, ResL), build_map(MidR, Right, ResR),
	arrPos(Mid, Value), Res = node(Value, 0, ResL, ResR), !.

build_ind([], _) :-  !.

build_ind([[Key, Value] | T], I) :-
	assert(arrPos(I, node_v(Key, Value))),
	I1 is I + 1, build_ind(T, I1), !.

build_ind([(Key, Value) | T], I) :-
	assert(arrPos(I, node_v(Key, Value))),
	I1 is I + 1, build_ind(T, I1), !.

delete_ind(I, End) :- I > End, !.

delete_ind(I, End) :- I =< End,
	retract(arrPos(I, _)), I1 is I + 1, delete_ind(I1, End), !.

map_get(node(node_v(Key, Value), 0, _, _), Key, Value) :- !.

map_get(node(node_v(Key, Value), 1), Key, Value) :- !.

map_get(node(node_v(Key, Value), 2, _), Key, Value) :- !.

map_get(node(node_v(KeyV, ValueV), 2, LeftN), Key, Value) :-
	Key < KeyV, map_get(LeftN, Key, Value), !.

map_get(node(node_v(KeyV, ValueV), 0, LeftN, RightN), Key, Value) :-
	Key > KeyV, map_get(RightN, Key, Value), !.

map_get(node(node_v(KeyV, ValueV), 0, LeftN, RightN), Key, Value) :-
	Key < KeyV, map_get(LeftN, Key, Value), !.

map_replace(node(node_v(Key, Value), 0, NodeL, NodeR), Key, ValueR, node(node_v(Key, ValueR), 0, NodeL, NodeR)) :- !.

map_replace(node(node_v(Key, Value), 1), Key, ValueR, node(node_v(Key, ValueR), 1)) :- !.

map_replace(node(node_v(Key, Value), 2, NodeL), Key, ValueR, node(node_v(Key, ValueR), 2, NodeL)) :- !.

map_replace(node(node_v(KeyV, ValueV), 2, LeftN), Key, Value, Result) :-
	Key < KeyV, map_replace(LeftN, Key, Value, ResL), Result = node(node_v(KeyV, ValueV), 2, ResL), !.

map_replace(node(node_v(KeyV, ValueV), 0, LeftN, RightN), Key, Value, Result) :-
	Key < KeyV, map_replace(LeftN, Key, Value, ResL), Result = node(node_v(KeyV, ValueV), 0, ResL, RightN), !.

map_replace(node(node_v(KeyV, ValueV), 0, LeftN, RightN), Key, Value, Result) :-
	Key > KeyV, map_replace(RightN, Key, Value, ResR), Result = node(node_v(KeyV, ValueV), 0, LeftN, ResR), !.
