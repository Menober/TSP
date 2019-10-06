import java.util.ArrayList;

public class Population {
    private int generation;
    private int populationSize;
    private ArrayList<Individual> population;

    Population() {
        this.population = new ArrayList<>();
        populationSize = 0;
        generation = 0;
    }

    public static Population generateNewRandomPopulation(int size, int dimension) {
        Population population = new Population();
        population.populationSize = size;
        population.createRandomPopulation(size, dimension);
        return population;
    }

    public void bumpGenerationNumber() {
        this.generation += 1;
    }

    void createRandomPopulation(int size, int dimension) {
        for (int i = 0; i < size; i++) {
            Individual newIndividual = new Individual(dimension);
            newIndividual.fillWithRandomCities();
            population.add(newIndividual);
        }
    }

    Individual getBestIndividual() {
        Individual bestIndividual = null;
        Double bestFitness = Double.MIN_VALUE;
        for (Individual d : population) {
            if (d.getFitness() > bestFitness) {
                bestFitness = d.getFitness();
                bestIndividual = d;
            }
        }
        return bestIndividual;
    }

    Individual getWorstIndividual() {
        Individual worstIndividual = null;
        Double worstFitness = Double.MAX_VALUE;
        for (Individual d : population) {
            if (d.getFitness() < worstFitness) {
                worstFitness = d.getFitness();
                worstIndividual = d;
            }
        }
        return worstIndividual;
    }

    public void printAllIndividuals() {
        for (Individual c : population) {
            c.printData();
        }
    }

    ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }


    void printIndividualsFitness() {
        for (Individual c : population) {
            System.out.printf("%32s", c + " fitness is: ");
            System.out.printf("%10s", c.getFitness().toString().split("\\.")[0] + ".");
            System.out.printf("%-15s", c.getFitness().toString().split("\\.")[1]);
            System.out.println();
        }
    }

    int getGeneration() {
        return generation;
    }

    Double getAverageFitness() {
        Double sumOfFitness = 0.0;
        for (Individual c : population) {
            sumOfFitness += c.getFitness();
        }
        return sumOfFitness / population.size();
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
