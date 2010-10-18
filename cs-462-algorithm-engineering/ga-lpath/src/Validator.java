


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abuzaher
 */
public class Validator {

    private ArrayList<ArrayList<Double>> adjacencyMatrix = null;

    public Validator(GA ga) {
        this.adjacencyMatrix = ga.getAdjacencyMatrix();
    }

    public Validator() {
    }

    public void validateCurrrentGeneration(ArrayList<Chromosome> chromosomes) {
        for (int i = 0; i < chromosomes.size(); i++) {
            Chromosome chromosome = chromosomes.get(i);
            boolean valid = true;
            for (int j = 0; j < chromosome.getLength() - 1; j++) {
                if (adjacencyMatrix.get(chromosome.getNodeValueByIndex(j)).get(chromosome.getNodeValueByIndex(j + 1)) == 0) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("INVALID CHROMOSOME " + chromosome);
                System.exit(-1);
            }
        }
    }

    public void validateChromosome(Chromosome chromosome) {
        if (adjacencyMatrix == null) {
            adjacencyMatrix = chromosome.getAdjacencyMatrix();
        }
        boolean valid = true;
        for (int j = 0; j < chromosome.getLength() - 1; j++) {
            if (adjacencyMatrix.get(chromosome.getNodeValueByIndex(j)).get(chromosome.getNodeValueByIndex(j + 1)) == 0) {
                valid = false;
                break;
            }
        }
        if (!valid) {
            System.out.println("INVALID CHROMOSOME " + chromosome);
            System.exit(-1);
        }
    }
}
