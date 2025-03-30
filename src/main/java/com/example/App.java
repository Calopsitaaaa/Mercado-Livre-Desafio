package com.example;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;

public class App {
    public static void main(String[] args) {
        // Load OR-Tools native library
        Loader.loadNativeLibraries();

        // Create the model
        CpModel model = new CpModel();

        // Define variables
        IntVar x = model.newIntVar(0, 10, "x");
        IntVar y = model.newIntVar(0, 10, "y");

        // Add constraints
        model.addLessOrEqual(x, y);

        // Define the objective
        model.maximize(x);

        // Solve the problem
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);

        if (status == CpSolverStatus.OPTIMAL) {
            System.out.println("Solution found:");
            System.out.println("x = " + solver.value(x));
            System.out.println("y = " + solver.value(y));
        } else {
            System.out.println("No solution found.");
        }
    }
}