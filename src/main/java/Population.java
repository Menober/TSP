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

    public static Population generateNewEmptyPopulation(int size, int generation) {
        Population population = new Population();
        population.populationSize = size;
        population.generation = generation;
        return population;
    }

    public static Population generateNewRandomPopulation(int size, int dimension) {
        Population population = new Population();
        population.populationSize = size;
        population.createRandomPopulation(size, dimension);
        return population;
    }

    public boolean isPopulationFilled() {
        return population.size() == populationSize;
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
        Double bestFitness = Double.MAX_VALUE;
        for (Individual d : population) {
            if (d.getFitness() < bestFitness) {
                bestFitness = d.getFitness();
                bestIndividual = d;
            }
        }
        return bestIndividual;
    }

    Individual getWorstIndividual() {
        Individual worstIndividual = null;
        Double worstFitness = Double.MIN_VALUE;
        for (Individual d : population) {
            if (d.getFitness() > worstFitness) {
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

    private Individual selection(int amountOfChamps) {
        Individual best = population.get(Utils.randomInt(0, population.size() - 1));
        for (int i = 0; i < amountOfChamps; i++) {
            Individual randomRival = population.get(Utils.randomInt(0, population.size() - 1));
            if (randomRival.getFitness() < best.getFitness()) {
                best = randomRival;
            }
        }

        return best;
    }

    public void crossover(Population population, double px, int amountOfChamps) {
        while (!isPopulationFilled()) {
            Individual parentA = population.selection(amountOfChamps);
            Individual parentB = population.selection(amountOfChamps);
            Individual childA = parentA.copy();
            Individual childB = parentB.copy();
            if (Utils.randomInt(0, 100000) <= px * 1000) {
                PMX(parentA, parentB, childA);
                PMX(parentB, parentA, childB);
            }
            this.population.add(childA);
            this.population.add(childB);
        }
    }

    private void PMX(Individual parentA, Individual parentB, Individual childA) {
        int indexOfCut = Utils.randomInt(0, parentA.getCitiesIds().length - 1);
        for (int i = 0; i < indexOfCut; i++) {
            int index = findIndex(parentA.getCitiesIds(), parentB.getCitiesIds()[i]);
            swap(i, index, childA);
        }
    }

    public static void swap(int i, int index, Individual childA) {
        int tmp = childA.getCitiesIds()[i];
        childA.getCitiesIds()[i] = childA.getCitiesIds()[index];
        childA.getCitiesIds()[index] = tmp;
    }

    public static int findIndex(int[] citiesIds, int cityId) {
        for (int i = 0; i < citiesIds.length; i++) {
            if (citiesIds[i] == cityId)
                return i;
        }
        return -1;
    }

    public void mutation(double pm) {
        for (Individual c : population) {
            c.mutate(pm);
        }
    }
}
