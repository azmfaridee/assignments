#!/usr/bin/python

from copy import deepcopy
import time
from pprint import pprint

PUZZLE_SIZE = 3
Infinity = float("inf")
visited = []

get_2dindex = lambda state, n: (state.index(n) / PUZZLE_SIZE, state.index(n) % PUZZLE_SIZE)

def get_next_states(state):
	next_states = []

	x, y = get_2dindex(state, 0)
	next = (x, y + 1), (x, y - 1), (x + 1, y), (x - 1, y)
	next = filter(lambda x: x[0] >= 0 and x[1] >= 0 and x[0] < PUZZLE_SIZE and x[1] < PUZZLE_SIZE, next)

	for x, y in next:
		newindex = x * PUZZLE_SIZE + y
		oldindex = state.index(0)

		newstate = deepcopy(state)
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

def ida_star(startnode, endnode):
	initial_cost_limit = get_manhattan_distance(startnode, endnode)
# 	initial_cost_limit = 2

	solution, cost_limit = dfs(startnode, 0, initial_cost_limit, [startnode])
# 	solution, cost_limit = dfs(startnode, 0, 23, [startnode])
	if solution != None: return solution, cost_limit
	if cost_limit == Infinity: return None

def dfs(node, cost_from_root, cost_limit, path):
	if node not in visited: visited.append(node)
	minimum_cost = cost_from_root + get_manhattan_distance(node, endnode)
 	print 'visited', visited
	print 'dfs for node', node
	print 'cost from root', cost_from_root, 'cost limit', cost_limit
	pprint(path)
# 	time.sleep(1)

	if minimum_cost > cost_limit: return None, minimum_cost
	if node == endnode: return path, cost_limit

	next_cost_limit = Infinity
 	for next_node in get_next_states(node):
 		if next_node in visited: continue
 		visited.append(next_node)
		solution, new_cost_limit = dfs(next_node, cost_from_root + 1, cost_limit, path + [next_node])
		if solution != None: return solution, new_cost_limit
		next_cost_limit = min(next_cost_limit, new_cost_limit)

	return None, next_cost_limit

def pretty_print(solution):
	for node in solution:
		for index, item in enumerate(node):
			if index % 3 == 0: print
			if item == 0: print ' ',
			else: print item,
		print

if __name__ == '__main__':
	startnode = [7, 2, 4, 5, 0, 6, 8, 3, 1]
# 	endnode = [7, 4, 6, 5, 2, 0, 8, 3, 1]
	endnode = [7, 4, 6, 8, 5, 3, 0, 1, 2]
# 	endnode = [0, 1, 2, 3, 4, 5, 6, 7, 8]

	# b = [8, 1, 2, 3, 4, 5, 6, 7, 0]
	answer = ida_star(startnode, endnode)
	if answer != None:
		print 'Solution found at cost limit', answer[1],
		pretty_print(answer[0])
	else: print 'No solution found for the given cost limit,'