public class Configuration {
    int populationSize;
    int generations;
    double crossover;
    double mutation;
    int amount;

    public Configuration(int populationSize, int generations, double crossover, double mutation, int amount) {
        this.populationSize = populationSize;
        this.generations = generations;
        this.crossover = crossover;
        this.mutation = mutation;
        this.amount = amount;
    }
}
