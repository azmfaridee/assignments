


import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abuzaher
 */
public class GALongestPath {

    private Random random;
    private GA ga;
    private int numIterations = 10;
    private int populationSize = 400;
    private double eliteFraction = 0.1;

    public GALongestPath(ArrayList<ArrayList<Double>> adjacencyMatrix) {
        random = new Random(new Date().getTime());
        ga = new GA(adjacencyMatrix, random);
        ga.setPopulationSize(populationSize);
        ga.setEliteFraction(eliteFraction);
    }

    public static void main(String args[]) {


//        Reader.openFile("input2.txt");
//        Reader.openFile("input3.txt");
//        Reader.openFile("input4 .txt");
//        Reader.openFile("input5.txt");
        Reader.openFile("input6.txt");
        ArrayList<Double> data = Reader.readFile();
        Reader.closeFile();

        ArrayList<ArrayList<Double>> adjacencyMatrix = new ArrayList<ArrayList<Double>>();
        int size = (int) Math.sqrt(data.size());
        for (int i = 0; i < size; i++) {
            adjacencyMatrix.add(new ArrayList<Double>());
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix.get(i).add(data.get(i * size + j));
            }
        }

        for (int i = 0; i < 10; i++) {
            GALongestPath gALongestPath = new GALongestPath(adjacencyMatrix);
            gALongestPath.runMainAlgo();

            System.out.println("GLOBAL SOLUTION: " + gALongestPath.ga.getBestChromosome());
        }


    }

    public void runMainAlgo() {
        ga.generateInitialPopulation();

//        Validator validator = new Validator(ga);
//        validator.validateCurrrentGeneration(ga.getChromosomes());

        double prevFitness = ga.getBestChromosomeFitness();
//        System.out.println(ga.getBestChromosome());

        for (int i = 0; i < numIterations; i++) {

            // corssover operation
            ga.applyCrossover();


            // code for mutation
            int perturbationFactor = ga.getPerturbationFactor();
//            System.out.println(perturbationFactor);

            ga.applyMutation();
//            validator.validateCurrrentGeneration();
            double newFitness = ga.getBestChromosomeFitness();

//            System.out.println(prevFitness);
//            System.out.println(newFitness);

            // if no improvement
            if (prevFitness == newFitness) {
//                perturbationFactor += 1;
                ga.increasePerturbationInNextIncrement = true;
//                if (perturbationFactor > (ga.getBestChromosome().getLength() - 1) / 2) {
//                    System.out.println("RESET");
//                    perturbationFactor = ga.perturbationFactorInitialValue;
//                }
//                ga.setPurturbationFactor(perturbationFactor);

//                if (perturbationFactor < (1 - delta)) {
//                    // increase the perturbation factor so that the mutation is
//                    // applied to more in the center and more variation is achieved
//                    perturbationFactor += delta;
//                    if (perturbationFactor > 1) {
//                        System.out.println("RESET");
//                        perturbationFactor = ga.perturbationFactorInitialValue;
//                    }
//                    ga.setPurturbationFactor(perturbationFactor);
//                }
            }

            prevFitness = newFitness;


//            System.out.println(ga.getBestChromosome());

        }
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setEliteFraction(double eliteFraction) {
        this.eliteFraction = eliteFraction;
    }
}
