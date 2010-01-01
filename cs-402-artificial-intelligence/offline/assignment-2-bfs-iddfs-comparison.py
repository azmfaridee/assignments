#!/usr/bin/python

# pprint function does some pretty printing
from pprint import pprint

# common globals
color = {}      # we have three colors:
                # 1. 'white', the discovered nodes
                # 2. 'gray', the node which is being visited, but whole childs are not yet visited
                # 3. 'black', the node whose child has been also been visited, that means there is no valid path through
                # this node
ancestor = {}
time = {}; clock = 0

# global variables for BFS
bfs_total_nodes_visited = 0
bfs_nodes_in_memory = 0

# global data for IDDFS
found = False
iddfs_total_nodes_visited = 0
iddfs_nodes_in_memory = 0

# jug capacities
jug1_cap = 5
jug2_cap = 2

# adjacent finding functions
# empty jug 1
def e1(current):
    if current[0] > 0: return 0, current[1]
    return None

# empty jug 2 
def e2(current):
    if current[1] > 0: return current[0], 0
    return None

# fill jug 1
def f1(current):
    global jug1_cap
    if current[0] < jug1_cap: return jug1_cap, current[1]
    return None

# fill jug 1
def f2(current):
    global jug2_cap
    if current[1] < jug2_cap: return current[0], jug2_cap
    return None

# transfer from jug 1 to jug 2
def t12(current):
    global jug2_cap
    if current[1] < jug2_cap and current[0] > 0:                                # reset coloring and ancestor list in every loop
        available = jug2_cap - current[1]                                       # water to be transferred to jug 2
        if current[0] >= available: return current[0] - available, jug2_cap     # if this amount can be got from jug 1
        else: return 0, current[0] + current[1]                                 # jug 1 was less than what can be filled in jug 2,
                                                                                # so just pass only what it can supply
    return None

# transfer from jug 2 to jug 1
def t21(current):
    global jug1_cap
    if current[0] < jug1_cap and current[1] > 0:                                # if first jug is not full and second jug is not empty
        availble = jug1_cap - current[0]
        if current[1] >= availble: return jug1_cap, current[1] - availble
        else: return current[0] + current[1], 0            
    return None

# use the six functions to get next state, filtering out empty (None) states on the fly
# EXPERIMENT: lambda function :)
get_next_state = lambda current: filter(None, (e1(current), e2(current), f1(current), f2(current), t12(current), t21(current)))

# path printing function
def print_path(visited, search):
    global iddfs_nodes_in_memory, bfs_nodes_in_memory, color
    
    path = []
    while True:
        if visited[search] == None: break
        path.append(visited[search])
        search = visited[search]
    path.reverse()                                                              # the path is discovered from child to root, we need to
                                                                                # reverse this path
    print 'Solution:' , path
    
    # DEBUG
    # print '###DEBUG###'; pprint(color)
    
    # alternate (and better)  way of computing memory complexity, just check the grey noded after finding the solution
    # total nodes in memory is the number of current 'gray' nodes
    # bfs_nodes_in_memory = iddfs_nodes_in_memory = len(filter(lambda value: value == 'gray', color.values()))
    
# helper function to search in a nested queue
def not_in(x, queue):
    for member in queue:
        if x == member[0]: return False
    return True

# memory complexity calculating function, personally I think calculating memory complexity
# at the end after finding the the solution will suffice as the solution is found at the highest
# depth, that one will obviously be the maximum
def calc_max_grey(type):
    global color, bfs_nodes_in_memory, iddfs_nodes_in_memory
    num_gray_nodes = len(filter(lambda value: value == 'gray', color.values()))
    
    if type == 'bfs':
        if bfs_nodes_in_memory < num_gray_nodes: bfs_nodes_in_memory = num_gray_nodes
    elif type == 'iddfs':
        if iddfs_nodes_in_memory < num_gray_nodes: iddfs_nodes_in_memory = num_gray_nodes

# recursive definition for bfs
def bfs(queue, search):
    global color, ancestor, found
    
    if queue == []: return                                                      # base case when queue is empty
    
    front = queue.pop(0); node = front[0]; parent = front[1]                    # pop the queue and get node and its parent
    
    # DEBUG
    # print 'Visiting node', front
    
    color[node] = 'gray'; ancestor[node] = parent;                              # paint it gray and push it in ancestor list
    calc_max_grey('bfs')                                                        # update memory complexity calculation,
                                                                                # superfluous
    
    adjacent = get_next_state(node)                                             # get adjacent nodes
    for x in adjacent:
        if x not in color.keys(): color[x] = 'white'                            # only add newly discovered nodes and mark them as white
                                                                                # do not overwrite gray or black nodes

    for x in adjacent:
        if color[x] == 'white' and not_in(x, queue): queue.append((x, node))
            
    # DEBUG
    # print 'Updated queue', queue, 'and visited', visited
    
    if node == search: print_path(ancestor, search); found = True; return       # if node is found, print path and end the recursion
    
    bfs(queue, search)                                                          # recursive call

    color[node] = 'black'                                                       # when bfs retuns, paint it black
    
    # DEBUG
    #pprint(color)
        
#  recursive definition for iddfs
def iddfs(pair, depth, search):
    global found, color, ancestor
    
    node = pair[0]; parent = pair[1]                                            # seperate node and parent
    
    # DEBUG
    # print 'IDDFS called for  node', node
    
    if node == None: return                                                     # base case, if called for empty node, just return
    if node in ancestor.keys():                                                 # check if the node can appear more close to the root, if so, return
        if ancestor[node] != parent: return            
    
    # DEBUG
    # print 'Visiting node', node, ', Depth', depth
    
    color[node] = 'gray';                                                       # paint it gray and push it in ancestor list
    if node not in ancestor.keys(): ancestor[node] = parent                     # do not overwrite if the node appers in lower level
    
    calc_max_grey('iddfs')                                                      # calculation for space complexity
                                                                                # superfluous
    
    if node == search: found = True; print_path(ancestor, search); return       # if found, print the path and premature return
    
    if depth == 0: color[node] = 'black'; return                                # if depth is 0, prematurely set the node to black and return
    
    adjacent = get_next_state(node)                                             # get adjacent nodes
    for x in adjacent:                                                          
        if x not in color.keys(): color[x] = 'white'                            # only add newly discovered nodes and mark them as white
        if x not in ancestor.keys(): ancestor[x] = node                         # insert in ancestor list
    
    # DEBUG
    # print '###DEBUG###'; print 'Depth:', depth, 'Node:', node; print 'Color nodes are'; pprint(color)
    # print 'Adjacent nodes are:'; pprint(adjacent)
    
    for x in adjacent:                                                          # only visit the white nodes
        if color[x] == 'white' and not found: iddfs((x, node), depth - 1, search)
        if found: break                                                         # after returning from child's recursion, see if found, then break
        
    color[node] = 'black'                                                       # paint it black
    
    # color.pop(node)
    # DEBUG
    # print '###DEBUG###'; print 'Depth:', depth, 'Node:', node; pprint(color)

# main program
if __name__ == "__main__":
    # DEBUG
    # a, b, c, d = 5, 2, 5, 1
    # a, b, c, d = 0, 0, 4, 0
    a, b, c, d = 5, 0, 0, 1
    
    #input = raw_input('Insert the starting node e.g. 0 0 for (0, 0): ').strip().split(' ')
    #a = int(input[0]); b = int(input[1])
    #input = raw_input('Insert the ending node e.g. 1 0 for (1, 0): ').strip().split(' ')
    #c = int(input[0]); d = int(input[1])
    #print
    
    print 'Finding solution using BFS'
    color = {}; ancestor = {}; found = False
    bfs([((a, b), None)], (c, d))
    # total nodes visited is the count of total 'gray' and 'black' nodes
    bfs_total_nodes_visited += len(filter(lambda value: value == 'gray' or value == 'black', color.values()))
    if not found: print ':( No Soultion found :('
    else:
        print 'Total Nodes visited when using BFS:', bfs_total_nodes_visited
        print 'Nodes in memory when using BFS:', bfs_nodes_in_memory
    
    print
    
    print 'Finding solution using IDDFS'
    for i in range(0, 10):
        # DEBUG
        # print '---Trying for depth: ', i
        
        color = {}; ancestor = {}; found = False                                # reset coloring and ancestor list in every loop
        
        iddfs(((a, b), None), i, (c, d))                                        # iddfs calling
        iddfs_total_nodes_visited += len(filter(lambda value: value == 'gray' or value == 'black', color.values()))
        if found: break
        else: pass; #print 'No solution for depth', i
    
    if not found: print ':( No Soultion found :('
    else:
        print 'Total Nodes visited when using IDDFS:', iddfs_total_nodes_visited
        print 'Nodes in memory when using IDDFS:', iddfs_nodes_in_memory