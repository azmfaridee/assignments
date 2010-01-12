#!/usr/bin/python

import copy
from pprint import pprint

PUZZLE_SIZE = 3

get_2dindex = lambda state, n: (state.index(n) / PUZZLE_SIZE, state.index(n) % PUZZLE_SIZE)

def get_next_2dstates(state):
	next_states = []

	x, y = get_2dindex(state, 0)
	next = (x, y + 1), (x, y - 1), (x + 1, y), (x - 1, y)
	next = filter(lambda x: x[0] >= 0 and x[1] >= 0 and x[0] < PUZZLE_SIZE and x[1] < PUZZLE_SIZE, next)

	for x, y in next:
		newindex = x * PUZZLE_SIZE + y
		oldindex = state.index(0)

		# without deepcopy there will be a problem
		newstate = copy.deepcopy(state)
		newstate[oldindex] = newstate[newindex]
		newstate[newindex] = 0
		next_states.append(newstate)

	return next_states

def get_manhattan_distance(current, next):
	total = 0
	for point in current:
		if point == 0: continue
		x1, y1 = get_2dindex(current, point)
		x2, y2 = get_2dindex(next, point)
		total += abs(x1 - x2) + abs(y1 - y2)
	return total

if __name__ == '__main__':
	start = [7, 2, 4, 5, 0, 6, 8, 3, 1]
	end = [0, 1, 2, 3, 4, 5, 6, 7, 8]
	b = [8, 1, 2, 3, 4, 5, 6, 7, 0]
# 	pprint(get_next_2dstates(start))
# 	print get_manhattan_distance(start, end)
	next_states = get_next_2dstates(start)
	for x in next_states:
		pprint(x), pprint(get_manhattan_distance(x, end))