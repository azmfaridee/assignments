#!/usr/bin/pythonimport copyadjacencent = {1: (3, 4, 5, 6),	2: (5, 6),	3: (1, 4, 5),	4: (1, 3, 6),	5: (1, 2, 3, 6),	6: (1, 2, 4, 5)}colors = ['Red', 'Green', 'Blue']def call_dfs(graph):	# return if no free space is found to color	if graph.index(None) == -1: return graph	outcome = graph	for node in graph.keys():		outcome = dfs(node, graph)		if outcome != None: break	# if solution found, return the solution, else return the original graph	return outcomedef dfs(node, graph):	global colors	global adjacencent	if graph[node] != None:# 		for neighbour in graph[node]:# 			if graph[node] == graph[neighbour]:# 				return graph# 		for color in colors:# 			graph[node] = color# 				graph = dfs(node, )if __name__ == '__main__':	graph = dict(zip([x for x in range(1, 7)], [None for x in range(1, 7)]))	out = dfs(1, graph)	print out