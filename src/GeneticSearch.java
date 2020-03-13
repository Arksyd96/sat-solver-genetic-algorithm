import java.util.*;

public class GeneticSearch {
	
	private Clauses clauses;
	private int populationSize;
	private int eliteIndividualsCount;
	private int generatedIndividualsCount;
	private double mutationRate;
	private double crossoverRate;
	private int mutationNumber;
	private int crossoverPoints;
	private int variablesCount;
	private Population population;
	private Random random = new Random(System.nanoTime());
	
	public GeneticSearch(Clauses clauses, int populationSize, int eliteIndividualsCount, 
			int generatedIndividalsCount, double mutationRate, double crossoverRate,
			int mutationsNumber, int crossoverPoints) {
		this.clauses = clauses;
		this.populationSize = populationSize;
		this.eliteIndividualsCount = eliteIndividualsCount;
		this.generatedIndividualsCount = generatedIndividalsCount;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.variablesCount = clauses.getVariableCount();
		this.mutationNumber = mutationsNumber;
		this.crossoverPoints = crossoverPoints;
		population = new Population(variablesCount, populationSize);	// Generate random population
	}
	
	public Individual searchSolution() {
		boolean solutionFound = false;
		
		do {
			List<Individual> generatedIndividuals = new ArrayList<>();
			
			// evaluates each individual and sorts the population from stronger to weaker
			// if contains solution it returns true
			solutionFound = population.evaluate(clauses.getCfnList());
			System.out.println(Arrays.toString(population.fitnessScores()));
			// new Scanner(System.in).nextInt();
			for (int i = 0; i < generatedIndividualsCount; i++) {
				double mutationScore = random.nextInt(100) * mutationRate;	// Mutation and crossover rates
				double crossoverScore = random.nextInt(100) * crossoverRate;
				
				if (mutationScore > crossoverScore) 	generatedIndividuals.add(mutation());		// adding mutated genotype
				else									generatedIndividuals.add(crossover());		// adding crossover child
			}
			population.setNewPopulation(generatedIndividuals);
		} while(!solutionFound);
		
		return population.getIndividuals().get(0);
	}
	
	public Individual mutation() {
		Individual selected = population.getIndividuals().get(random.nextInt(eliteIndividualsCount));
		int rand;
		for (int i = 0; i < mutationNumber; i++) {
			rand = random.nextInt(variablesCount);
			if (selected.getGenotype().get(rand) == 1)	selected.getGenotype().clear(rand);
			else 										selected.getGenotype().set(rand);
		}
		return selected;
	}
	
	public Individual crossover() {
		Individual[] selected = new Individual[2];
		selected[0] = population.getIndividuals().get(random.nextInt(eliteIndividualsCount));	// Selecting 2 elites
		selected[1] = population.getIndividuals().get(random.nextInt(eliteIndividualsCount));
		
		int sectionSize = variablesCount / crossoverPoints;		// Section size is array width 
																// between each crossover point
		BitArray arr = new BitArray(variablesCount);
		int rand;
		for (int i = 0; i < crossoverPoints; i++) {
			rand = random.nextInt(2);
			for(int k = i * sectionSize; k < (i + 1) * sectionSize; k++) {	
				if (selected[rand].getGenotype().get(k) == 1) arr.set(k);	// Fusion of sections randomly
			}
		}
		return new Individual(arr);
	}
	
	
}
