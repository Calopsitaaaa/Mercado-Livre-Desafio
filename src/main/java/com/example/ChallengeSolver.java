package com.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class ChallengeSolver {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java ChallengeSolver <input-file> <output-file>");
            return;
        }
        
        String inputFile = args[0];
        String outputFile = args[1];
        
        try {
            WaveSelectionSolver solver = new WaveSelectionSolver();
            solver.solve(inputFile, outputFile);
        } catch (IOException e) {
            System.err.println("Erro ao processar os arquivos: " + e.getMessage());
        }
    }
}

class WaveSelectionSolver {
    private int numPedidos, numItens, numCorredores;
    private int[][] pedidos;
    private int[][] corredores;
    private int lb, ub;

    public void solve(String inputFile, String outputFile) throws IOException {
        Loader.loadNativeLibraries();
        lerEntrada(inputFile);
        
        if (lb > ub) {
            System.err.println("Erro: Limite inferior maior que limite superior!");
            return;
        }
        
        List<Integer> melhorWave = resolverILP();
        escreverSaida(outputFile, melhorWave);
    }

    private void lerEntrada(String inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        numPedidos = Integer.parseInt(st.nextToken());
        numItens = Integer.parseInt(st.nextToken());
        numCorredores = Integer.parseInt(st.nextToken());
        
        pedidos = new int[numPedidos][numItens];
        for (int i = 0; i < numPedidos; i++) {
            st = new StringTokenizer(br.readLine());
            int numItensPedido = Integer.parseInt(st.nextToken());
            for (int j = 0; j < numItensPedido; j++) {
                int item = Integer.parseInt(st.nextToken());
                int quantidade = Integer.parseInt(st.nextToken());
                pedidos[i][item] = quantidade;
            }
        }
        
        corredores = new int[numCorredores][numItens];
        for (int i = 0; i < numCorredores; i++) {
            st = new StringTokenizer(br.readLine());
            int numItensCorredor = Integer.parseInt(st.nextToken());
            for (int j = 0; j < numItensCorredor; j++) {
                int item = Integer.parseInt(st.nextToken());
                int quantidade = Integer.parseInt(st.nextToken());
                corredores[i][item] = quantidade;
            }
        }
        
        st = new StringTokenizer(br.readLine());
        lb = Integer.parseInt(st.nextToken());
        ub = Integer.parseInt(st.nextToken());
        br.close();
    }
    
    private List<Integer> resolverILP() {   
        MPSolver solver = new MPSolver("WaveSelection", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);
        MPVariable[] pedidoSelecionado = new MPVariable[numPedidos];
        for (int i = 0; i < numPedidos; i++) {
            pedidoSelecionado[i] = solver.makeIntVar(0, 1, "pedido_" + i);
        }
        
        MPObjective objective = solver.objective();
        for (int i = 0; i < numPedidos; i++) {
            int somaItens = Arrays.stream(pedidos[i]).sum();
            objective.setCoefficient(pedidoSelecionado[i], somaItens);
        }
        objective.setMaximization();
        
        MPConstraint lbConstraint = solver.makeConstraint(lb, ub);
        for (int i = 0; i < numPedidos; i++) {
            int somaItens = Arrays.stream(pedidos[i]).sum();
            lbConstraint.setCoefficient(pedidoSelecionado[i], somaItens);
        }
        
        solver.solve();
        List<Integer> wave = new ArrayList<>();
        for (int i = 0; i < numPedidos; i++) {
            if (pedidoSelecionado[i].solutionValue() > 0.5) {
                wave.add(i);
            }
        }
        return wave;
    }

    private void escreverSaida(String outputFile, List<Integer> wave) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write(wave.size() + "\n");
        for (int pedido : wave) {
            bw.write(pedido + "\n");
        }
        bw.close();
    }
}
