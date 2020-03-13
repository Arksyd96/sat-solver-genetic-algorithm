import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clauses {

    private int variableCount;
    private int dimension;
    private List<int[]> cfnList;

    public Clauses(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        this.cfnList = new ArrayList<>();
        for (String line : lines) {
            if (line.matches("^(( *p +cnf)?( *-?[1-9][0-9]*)+( +0)? *)")) {
                String[] array = line.trim().split(" +");
                if (array[0].equals("p")) {
                    variableCount = Integer.parseInt(array[2]);
                } else {
                    if (dimension == 0) dimension = array.length - 1;
                    cfnList.add(Arrays.stream(array)
                            .limit(dimension)
                            .mapToInt(Integer::parseInt)
                            .toArray());
                }
            }
        }
    }

    public int getVariableCount() {
        return variableCount;
    }

    public int getDimension() {
        return dimension;
    }

    public List<int[]> getCfnList() {
        return cfnList;
    }

    public int size() {
        return cfnList.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        cfnList.forEach(clause -> builder.append("\t\t").append(Arrays.toString(clause)).append("\n"));
        return "Clauses: {\n" +
                "\tdimension: " + dimension + ",\n" +
                "\tvariableCount: " + variableCount + ",\n" +
                "\tsize: " + size() + ",\n" +
                "\tclauses: [\n" +
                builder.toString() +
                "\t]\n" +
                "}";
    }
}
