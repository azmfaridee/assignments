#!/usr/bin/python

from copy import deepcopy
from time import sleep
from pprint import pprint
import sys
import math

PUZZLE_SIZE = 4
MAX_COST_CUTOFF = 31
DEBUG_MODE = False
SIMULATION_MODE = False

Infinity = float("inf")
visited = []

get_2dindex = lambda state, n: (state.index(n) / PUZZLE_SIZE, state.index(n) % PUZZLE_SIZE)

def get_ordered(node):
	ordered = []
	for i in range(0, PUZZLE_SIZE):
		temp = deepcopy(node[i*PUZZLE_SIZE: (i+1)*PUZZLE_SIZE])
 		if i%2 == 1: temp.reverse()
		ordered += temp
	ordered.remove(0)
	return ordered

def get_cycle(startnode, endnode, key, cycle):
	if key in cycle: return cycle
	cycle.append(key)
	if startnode.index(key) == endnode.index(key): return cycle
	cycle = get_cycle(startnode, endnode, endnode[startnode.index(key)], cycle)
	return cycle

def check_solvability(startnode, endnode):
	startnode = get_ordered(startnode)
	endnode = get_ordered(endnode)
	cycles = []
	actual_count = 0

	check = dict(zip(startnode, [False for i in range(0, PUZZLE_SIZE * PUZZLE_SIZE)]))
	for item in check.keys():
		if check[item] == False:
			cycle = get_cycle(startnode, endnode, item, [])
			if cycle != []:
				cycles.append(cycle)
				for x in cycle: check[x] = True
	for cycle in cycles:
		if len(cycle) == 2: actual_count += 1
		if len(cycle) > 2: actual_count += len(cycle) - 1

	if actual_count % 2 == 0: return True
	return False

def get_next_states(state):
	next_states = []

	x, y = get_2dindex(state, 0)
	next = filter(lambda x: x[0] >= 0 and x[1] >= 0 and x[0] < PUZZLE_SIZE and x[1] < PUZZLE_SIZE,
			((x, y + 1), (x, y - 1), (x + 1, y), (x - 1, y)))

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

def pretty_print(solution):
	for nodeindex, node in enumerate(solution):
		print 'Position:', nodeindex,
		for index, item in enumerate(node):
			if index % PUZZLE_SIZE == 0: print
			if item == 0: print ' ',
			else: print item,
		print


def ida_star(startnode, endnode):
	cost_cuttoff = get_manhattan_distance(startnode, endnode)

	while cost_cuttoff < MAX_COST_CUTOFF:
		solution, updated_cost_cutoff = dfs(startnode, 0, cost_cuttoff, [startnode])
		if solution != None: return solution, updated_cost_cutoff
		if updated_cost_cutoff == Infinity: return None
		if DEBUG_MODE == True:
			print 'DEBUG: No solution for f(n) =', cost_cutoff
			print 'DEBUG: Trying for f(n) = ', updated_cost_cutoff
		cost_cuttoff = updated_cost_cutoff

def dfs(node, cost_from_root, cost_cutoff, path):
	minimum_cost = cost_from_root + get_manhattan_distance(node, endnode)

	if DEBUG_MODE == True:
		print 'DEBUG: DFS for node: ', node
		print 'DEBUG: Cost (depth) from root: ', cost_from_root
		print 'DEBUG: Parent\'s cost limit:', cost_cutoff
		print 'DEBUG: Curent cost limit i.e. f(n): ', minimum_cost
		print 'DEBUG: Path so far:'; pprint(path)
	if SIMULATION_MODE == True: sleep(1)

	if minimum_cost > cost_cutoff: return None, minimum_cost
	if node == endnode: return path, cost_cutoff

	next_cost_cutoff = Infinity
	next_states = get_next_states(node)
	if DEBUG_MODE == True: print 'DEBUG: Node', node, 'has following child nodes: ', next_states
 	for next_node in next_states:
		solution, new_cost_cutoff = dfs(next_node, cost_from_root + 1, cost_cutoff, path + [next_node])
		if solution != None: return solution, new_cost_cutoff
		next_cost_cutoff = min(next_cost_cutoff, new_cost_cutoff)

	return None, next_cost_cutoff

if __name__ == '__main__':
	# 8 puzzle
	startnode = [7, 2, 4, 5, 0, 6, 8, 3, 1]
# 	endnode = [7, 4, 6, 5, 2, 0, 8, 3, 1]
	endnode = [7, 4, 6, 8, 5, 3, 0, 1, 2]
# 	endnode = [7, 4, 6, 5, 2, 1, 0, 8, 3]
	# takes a lot of time
# 	endnode = [0, 1, 2, 3, 4, 5, 6, 7, 8]
	# this node is unsolvable
# 	endnode = [1, 2, 4, 5, 0, 6, 8, 3, 7]

	# 15 puzzle
# 	startnode = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0]
# 	endnode = [1, 2, 3, 4, 5, 6, 7, 8, 0, 15, 12, 14, 13, 9, 11, 10]

	PUZZLE_SIZE = int(math.sqrt(len(startnode)))
	if check_solvability(startnode, endnode) == False:
		print 'The goal is not reachable'
		sys.exit(1)
	answer = ida_star(startnode, endnode)
	if answer != None:
		print 'Solution found at cost limit', answer[1]
		pretty_print(answer[0])
	else: print 'No solution found within maximum cost limit', MAX_COST_CUTOFF