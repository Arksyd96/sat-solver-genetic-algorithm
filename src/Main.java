import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
    	Clauses clauses = new Clauses("resources/satisfied/uf75-022.cnf");
        GeneticSearch ga = new GeneticSearch(clauses, 30, 10, 30, 0.3, 0.7, 5, 3);
        double start = System.currentTimeMillis();
        ga.searchSolution();
        double end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
