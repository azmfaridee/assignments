


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abuzaher
 */
public class GA {

    private ArrayList<ArrayList<Double>> adjacencyMatrix;
    private int populationSize = 100;
    private ArrayList<Chromosome> chromosomes;
    private Random random;
    private int size;
    // low value -> mutation node selected at the ending nodes in the path
    // high value -> mutation node selected at the middle node in the path
    // value range 0.0 - 1.0
    // value 0.0 -> end node selected
    // value 1.0 -> middle node selected
    // in actual code, it should not be 0.0, use 0.01 or any small value
    public final int perturbationFactorInitialValue = 0;
    private double eliteFraction = 0.1;
    private int perturbationFactor = perturbationFactorInitialValue;
    public boolean increasePerturbationInNextIncrement = false;

    public GA(ArrayList<ArrayList<Double>> adjacencyMatrix, Random random) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.chromosomes = new ArrayList<Chromosome>();
        this.random = random;
        this.size = adjacencyMatrix.size();
    }

    private ArrayList<Integer> getNeighbours(Integer currentNode) {
        ArrayList<Integer> neighbours = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix.get(currentNode).get(i) > 0) {
                neighbours.add(i);
            }
        }
        return neighbours;
    }

    private ArrayList<Integer> getUnvisitedNeighbours(ArrayList<Integer> neighbours, ArrayList<Boolean> visited) {
        ArrayList<Integer> unvisitedList = new ArrayList<Integer>();
        int neighbour;
        for (int i = 0; i < neighbours.size(); i++) {
            neighbour = neighbours.get(i);
            if (visited.get(neighbour) == Boolean.FALSE) {
                unvisitedList.add(neighbour);
            }
        }
        return unvisitedList;
    }

    private int getNeighbourCount(Integer currentNode) {
        int neighbourCount = 0;
        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix.get(currentNode).get(i) > 0) {
                neighbourCount++;
            }
        }
        return neighbourCount;
    }

    private void completeRemainingChromosome(Integer currentNode, Integer tabuNode, Chromosome chromosome, ArrayList<Boolean> visited, boolean goingForward) {
        boolean tabuActionTaken = false;

        while (Boolean.TRUE) {
            visited.set(currentNode, Boolean.TRUE);
            chromosome.apppendNode(currentNode);
//            System.out.println(currentNode);

//            System.out.println("CURRENT NODE " + currentNode);
//            System.out.println("NEIGHBOURS " + getNeighbours(currentNode));

            ArrayList<Integer> unvisitedNeighbours = getUnvisitedNeighbours(getNeighbours(currentNode), visited);
//            System.out.println("UNVISITED" + unvisitedNeighbours);

            if (tabuNode != - 1 && tabuActionTaken == false) {
                // remove the tabue node from the path
                int index = unvisitedNeighbours.indexOf(tabuNode);
                if (index != -1) {
                    unvisitedNeighbours.remove(index);
                    // make sure this happens only once
                    tabuActionTaken = true;
                }
            }

            if (unvisitedNeighbours.isEmpty()) {
                if (goingForward) {
                    goingForward = false;
//                    System.out.println("Reversing" + chromosome);
                    chromosome.reverse();
//                    System.out.println("Reversed" + chromosome);
                    currentNode = chromosome.removeNodeByIndex(chromosome.getLength() - 1);
                    completeRemainingChromosome(currentNode, -1, chromosome, visited, false);
//                    System.out.println("New one: " + chromosome);
                    chromosome.reverse();
//                    System.out.println("Final: " + chromosome);
                }
                break;
            } else if (unvisitedNeighbours.size() == 1) {
//                System.out.println("Only one node");
//                System.out.println("Current Chromosome" + chromosome);
//                System.out.println("Neighbours" + unvisitedNeighbours);

//                chromosome.apppendNode(unvisitedNeighbours.get(0));
                currentNode = unvisitedNeighbours.get(0);

//                break;
            } else {
                ArrayList<Integer> degrees = new ArrayList<Integer>();

                double sum = 0.0;
                for (int i = 0; i < unvisitedNeighbours.size(); i++) {
                    int degree = getNeighbourCount(unvisitedNeighbours.get(i));
                    sum += degree;
                    degrees.add(degree);
                }

                ArrayList<Double> probability = new ArrayList<Double>();
                for (int i = 0; i < unvisitedNeighbours.size(); i++) {
                    probability.add(degrees.get(i) / sum);
                }

                currentNode = unvisitedNeighbours.get(Utility.getRandomNodeIndex(probability));
            }
        }
//        System.out.println("DONE");
//        System.out.println("OUTPUT CHROMOSOE " + chromosome);
    }

    public Chromosome getRandomChromosome() {
        /*
        TODO: EXCERPT FROM THE PAPER
         * 
        if we have reached a vertex with unavailable neighbors,
        instead of stopping the method, we analyze whether the degree of the
        first vertex is greater than one and keep computing the path to the
        opposite direction until reaching a finishing poin
         * 
         */

        Chromosome chromosome = new Chromosome(adjacencyMatrix);

        ArrayList<Boolean> visited = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            visited.add(Boolean.FALSE);
        }

        // generate a random first node
        int firstNode = random.nextInt(size);

        completeRemainingChromosome(firstNode, -1, chromosome, visited, true);

        return chromosome;
    }

    public void generateInitialPopulation() {
        for (int i = 0; i < populationSize; i++) {
            chromosomes.add(getRandomChromosome());
        }
        Collections.sort(chromosomes);
//        System.out.println(chromosomes);
    }

    Double getBestChromosomeFitness() {
        return chromosomes.get(0).getFitnesss();
    }

    Chromosome getBestChromosome() {
        return chromosomes.get(0);
    }

    class Pair {

        private Chromosome first;
        private Chromosome second;

        public Pair(Chromosome first, Chromosome second) {
            this.first = first;
            this.second = second;
        }

        public Chromosome getFirst() {
            return first;
        }

        public Chromosome getSecond() {
            return second;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ first: ").append(first).append(",\nsecond: ").append(second).append(" }\n");
            return sb.toString();
        }
    }

    private ArrayList<Pair> getNonIntersectingChromosomes() {
        ArrayList<Pair> nonIntersectingChromosomes = new ArrayList<Pair>();
        for (int i = 0; i < chromosomes.size(); i++) {
            for (int j = 0; j < chromosomes.size(); j++) {
                if (j > i) {
                    if (chromosomes.get(i).hasCommonNodeWith(chromosomes.get(j))) {
                        nonIntersectingChromosomes.add(new Pair(chromosomes.get(i), chromosomes.get(j)));
                    }
                }
            }
        }
        return nonIntersectingChromosomes;
    }

    public void applyCrossover() {
//        ArrayList<Pair> nonIntersectingChromosomes = getNonIntersectingChromosomes();
    }

    private Chromosome getMutatedChromosome(Chromosome parent) {
        // thresholds are in index, NOT in length
        int mutationPointEndThreshold = parent.getLength() - 2;
        int mutationPointStartThreshold = parent.getLength() / 2;

        int mutationPointIndex = mutationPointEndThreshold;
//         if perturbation is chosen ar random
//        int mutationPointIndex = random.nextInt(parent.getLength() - 2) + 1;


        if (increasePerturbationInNextIncrement == true) {
            while (true) {
                perturbationFactor += 1;
                if (perturbationFactor > (parent.getLength() - 1) / 2) {
//                    System.out.println("RESET\n---------------------------------");
                    perturbationFactor = 0;
                    break;
                }
                mutationPointIndex = Math.max(mutationPointStartThreshold, mutationPointEndThreshold - perturbationFactor);
                if (getNeighbourCount(mutationPointIndex) > 2) {
                    break;
                }// else {
//                    System.out.println("NEED TO INCREASE " + perturbationFactor);
//                }
            }
            increasePerturbationInNextIncrement = false;
        } else {
            mutationPointIndex = Math.max(mutationPointStartThreshold, mutationPointEndThreshold - perturbationFactor);
        }



        // deduct 1 from length, in the worst case, there must be at least one following
        // node remaining
//        mutationPointIndex = Math.max(mutationPointStartThreshold, mutationPointEndThreshold - perturbationFactor);
//        mutationPointIndex = (int) Math.floor((parent.getLength() - 1) * (1 - perturbationFactor / 2.0));

//        System.out.println("Mutation Point: " + mutationPointIndex);

        // copy all the nodes before the mutation point
        Chromosome child = parent.getPartialCopiedChromosome(mutationPointIndex);
//        System.out.println("PARENT " + parent);
//        System.out.println("CHILD " + child);

        // mutation point is the current node, we'll visit this node
        int currentNode = parent.getNodeValueByIndex(mutationPointIndex);
        // we do not want to visit the node next, this is the tabu node
        int tabuNode = parent.getNodeValueByIndex(mutationPointIndex + 1);

//        System.out.println(currentNode);
//        System.out.println(tabuNode);
//        System.out.println(getNeighbourCount(currentNode));

        // prepare visited node for the child
        ArrayList<Boolean> visited = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            visited.add(Boolean.FALSE);
        }
        for (int i = 0; i < child.getLength(); i++) {
            visited.set(child.getNodeList().get(i), Boolean.TRUE);
        }

        completeRemainingChromosome(currentNode, tabuNode, child, visited, true);

//        System.out.println(mutationPointIndex);
//        System.out.println(parent);
//        System.out.println(child);

        return child;
    }

    public void applyMutation() {
//        System.out.println("#BEFORE");
//        System.out.println(chromosomes);

        ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
        ArrayList<Chromosome> chromosomePool = new ArrayList<Chromosome>();
        ArrayList<Double> probList = new ArrayList<Double>();

        double sum = 0.0;
        for (int i = 0; i < chromosomes.size(); i++) {
            Chromosome chromosome = chromosomes.get(i);

//            int mutationPointEndThreshold = chromosome.getLength() - 2;
//            int mutationPointStartThreshold = (int) Math.floor(chromosome.getLength() / 2.0);


            chromosomePool.add(chromosome);
            sum += chromosome.getFitnesss();
//            sum += Math.pow(chromosome.getFitnesss(), 2);

            // if perturbation factor is in range
//            if (mutationPointEndThreshold - mutationPointStartThreshold >= perturbationFactor) {
            Chromosome child1 = getMutatedChromosome(chromosome);

//            Validator v = new Validator();
//            v.validateChromosome(child1);

            chromosomePool.add(child1);
            sum += child1.getFitnesss();
//            sum += Math.pow(child1.getFitnesss(), 2);

            Chromosome child2 = getMutatedChromosome(chromosome.getFlippedChromosome());
            chromosomePool.add(child2);
            sum += child2.getFitnesss();
//            sum += Math.pow(child2.getFitnesss(), 2);
//            } else {
//                // perturbation factor is not in range, reset it's value
//                perturbationFactor = 0;
//            }
        }

        for (int i = 0; i < chromosomePool.size(); i++) {
//            probList.add(Math.pow(chromosomePool.get(i).getFitnesss(), 2) / sum);
            probList.add(chromosomePool.get(i).getFitnesss() / sum);
        }


//        Collections.sort(newGeneration);

//        chromosomes.clear();

        Collections.sort(chromosomes);
        int numEliteChromosomes = (int) (populationSize * eliteFraction);
        newGeneration.addAll(chromosomes.subList(0, numEliteChromosomes));

        chromosomes.clear();
        chromosomes.addAll(newGeneration);

        newGeneration.clear();

        int i = 0;
        while (i < populationSize - numEliteChromosomes) {
            // TODO: only add not similar solutions
            Chromosome nextProbableChromosome = chromosomePool.get(Utility.getRandomNodeIndex(probList));

            boolean similar = false;
            for (int j = 0; j < chromosomes.size(); j++) {
                if (chromosomes.get(j).similarTo(nextProbableChromosome)) {
                    similar = true;
                }
            }
            if (!similar) {
                chromosomes.add(nextProbableChromosome);
                ++i;
            }

        }

        Collections.sort(chromosomes);
//        chromosomes.addAll(newGeneration.subList(0, populationSize));
//        chromosomes.addAll(newGeneration);

//        System.out.println("#AFTER");
//        System.out.println(chromosomes);
    }

    private void get3DegreeVertexList(Chromosome chromosome) {
//        ArrayList<Integer> nodelist = chromosome.getNodeList();
//        HashMap<Integer, Integer> degree3Vertices = new HashMap<Integer, Integer>();
//
//        for (int i = nodelist.size() / 2; i < nodelist.size(); i++) {
//            int degree = 0;
//            for (int j = 0; j < size; j++) {
//                if (adjacencyMatrix.get(i).get(j) > 0) {
//                    degree++;
//                }
//            }
//            if (degree > )
//        }
    }

    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public int getPerturbationFactor() {
        return perturbationFactor;
    }

    public void setPurturbationFactor(int purturbationFactor) {
        this.perturbationFactor = purturbationFactor;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setEliteFraction(double eliteFraction) {
        this.eliteFraction = eliteFraction;
    }

    String getChromosomeStrings() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < chromosomes.size() - 1; i++) {
            sb.append(chromosomes.get(i).getNodeList().toString()).append(", ");
        }
        sb.append(chromosomes.get(chromosomes.size() - 1).getNodeList().toString()).append("]");
        return sb.toString();
    }

    public ArrayList<ArrayList<Double>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
}
