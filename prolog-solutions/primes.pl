% простое
prime(2) :- !.
prime(N) :- N > 2, not composite(N), !.

fill_composite(I, N, STEP) :- I =< N,
	assert(composite(I)),
	I1 is I + STEP,
	fill_composite(I1, N, STEP), !.

fill_composite(I, N, S).

body(I, N) :- prime(I),
	I2 is I * I,
	fill_composite(I2, N, I), !.

body(I, N).

for_composite(I, N) :- I =< N,
	body(I, N),
	I1 is I + 1,
	for_composite(I1, N).

init(MAX_N) :- for_composite(2, MAX_N).

prime_divisors(1, []) :- !.

up(N, DIV, [N]) :- DIV * DIV > N, !.

up(N, DIV, [D | T]) :- mod(N, DIV) =:= 0,
	D is DIV,
	N1 is div(N, DIV),
	up(N1, DIV, T), !.

up(N, DIV, L) :-
	DIV1 is DIV + 1,
	up(N, DIV1, L), !.

prime_divisors(N, L) :- number(N),
	up(N, 2, L), !.

get_num(1, [], _) :- !.

get_num(N, [DIV | T], D) :- D =< DIV,
	get_num(N1, T, DIV),
	N is N1 * DIV, !.

prime_divisors(N, [DIV | T]) :- number(DIV),
	get_num(N1, T, DIV),
	N is N1 * DIV, !.

get_prime(N, N, CURR_P, P) :- P is CURR_P, !.

get_prime(I, N, CURR_P, P) :-
	next_prime(CURR_P, NEXT_P),
	I1 is I + 1,
	get_prime(I1, N, NEXT_P, RES),
	P is RES, !.

next_prime(2, RES) :- RES is 3, !.

calc(P, RES) :- prime(P), RES is P, !.

calc(P, RES) :-
	PN is P + 2,
	calc(PN, RESN),
	RES is RESN, !.

next_prime(P, RES) :-
	PN is P + 2,
	calc(PN, RES_N),
	RES is RES_N, !.

nth_prime(N, P) :-
	number(N),
	get_prime(1, N, 2, RES),
	P is RES, !.

get_prime_i(I, N, P, P) :- N is I, !.

get_prime_i(I, N, CURR_P, P) :-
	next_prime(CURR_P, NEXT_P),
	I1 is I + 1,
	get_prime_i(I1, RES, NEXT_P, P),
	N is RES, !.

nth_prime(N, P) :-
	number(P),
	get_prime_i(1, RES, 2, P),
	N is RES, !.

lcm_calc([NA | TA], [NB | TB], LCM) :- NA < NB,
	lcm_calc(TA, [NB | TB], LCM_ACC), LCM is LCM_ACC * NA, !.

lcm_calc([NA | TA], [NB | TB], LCM) :- NA > NB,
	lcm_calc([NA | TA], TB, LCM_ACC), LCM is LCM_ACC * NB, !.

lcm_calc([NA | TA], [NB | TB], LCM) :- NA =:= NB,
	lcm_calc(TA, TB, LCM_ACC), LCM is LCM_ACC * NA, !.

lcm_calc([], [NB | TB], LCM) :-
	lcm_calc([], TB, LCM_ACC), LCM is LCM_ACC * NB, !.

lcm_calc([NA | TA], [], LCM) :-
	lcm_calc(TA, [], LCM_ACC), LCM is LCM_ACC * NA, !.

lcm_calc([], [], LCM) :- LCM is 1, !.

lcm(A, B, LCM) :-
	prime_divisors(A, ALIST),
	prime_divisors(B, BLIST),
	lcm_calc(ALIST, BLIST, RES),
	LCM is RES, !.