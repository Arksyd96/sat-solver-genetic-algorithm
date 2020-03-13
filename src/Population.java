import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    private List<Individual> individuals;
    private int generationCount;
    private final int size;
    private final Random random;

    public Population(int variableCount, int size) {
        this.size = size;
        individuals = new ArrayList<>();
        // fittestIndex = 0;
        random = new Random(System.nanoTime());
        for (int i = 0; i < size; i++) {
            BitArray genotype = new BitArray(variableCount);
            for (int j = 0; j < variableCount; j++) {
            	if (random.nextInt(2) == 1) genotype.set(j);
            }
            individuals.add(new Individual(genotype));
        }
    }

    public boolean evaluate(List<int[]> clauses) {
    	for (Individual ind : individuals)	
    		computeFitness(ind, clauses);
    	Collections.sort(individuals);
    	if (individuals.get(0).getFitnessScore() == clauses.size())
    		return true;
    	else return false;
    }
    
    public void computeFitness(Individual individual, List<int[]> clauses) {
    	boolean proven;
    	individual.resetFitnessScore();
    	for (int[] clause : clauses) {
    		proven = false;
			for (int i = 0; i < clause.length && !proven; i++) {				
				int variable = individual.getGenotype().get(Math.abs(clause[i]));
				if ((clause[i] < 0 && variable == 0) || (clause[i] > 0 && variable == 1))	proven = true;
			}
			if (proven)	individual.incrementFitness();
		}
    }
    
    public int[] fitnessScores() {
    	int[] scores = new int[size];
    	for(int i = 0; i < size; i++)
    		scores[i] = individuals.get(i).getFitnessScore();
    	return scores;
    }
    
    public List<Individual> getIndividuals() {
    	return individuals;
    }
    
    private Individual tournamentBasedSelection() {
        Individual fittest = null;
        for (int i = 0; i < 5; i++) {
            Individual selected = individuals.get(random.nextInt(individuals.size()));
            if (fittest == null || fittest.getFitnessScore() < selected.getFitnessScore()) {
                fittest = selected;
            }
        }
        return fittest;
    }

    public int size() {
        return individuals.size();
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setNewPopulation(List<Individual> newPopulation) {
    	this.individuals = newPopulation;
    }
}
