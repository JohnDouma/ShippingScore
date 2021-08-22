package douma;

import douma.solver.AssignmentSolver;
import douma.util.FileUtils;
import douma.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("Usage: ShippingScore <address file> <driver file>");
        }

        List<String> addresses = FileUtils.readContentsOfFile(args[0]);
        List<String> driverNames = FileUtils.readContentsOfFile(args[1]);

        AssignmentSolver solver = new AssignmentSolver(addresses, driverNames);
        double totalScore = solver.solve();
        List<Pair<String,String>> assignments = solver.getAssignments();

        System.out.printf("The sum of the scores is %lf\n", totalScore);
        for (Pair<String, String> pair: assignments) {
            System.out.printf("Address %s assigned to driver %s", pair.first, pair.second);
        }
    }
}