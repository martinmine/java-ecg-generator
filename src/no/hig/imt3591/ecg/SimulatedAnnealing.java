package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Hill climbing with simulated annealing
 *
 */
public class SimulatedAnnealing {

    private List<Point.Double> observations;
    private int currentStateIndex;
    private Random random;

    private double initialTemperature;
    private double coolingRate;
    private int leftIndex;
    private int rightIndex;


    public SimulatedAnnealing(List<Point.Double> list, double temperature, double coolingRate) {
        this.random = new Random();
        this.observations = list;
        this.currentStateIndex = 0;
        this.initialTemperature = temperature;
        this.coolingRate = coolingRate;
    }

    public double energy(int state) {
        return observations.get(state).getY();
    }

    public int neighbor(int state) {
        int nextIndicator = (Math.random() > 0.5)? 1: -1;
        int offset = 1;

        if ((state - offset) < 0  ||  (state + offset) > (observations.size() - 1)) {
            return random.nextInt(observations.size() - 1);
        }

        int next = state + (nextIndicator * offset);
        System.out.println("next\t" + next);


        return state + (nextIndicator * offset);
    }

    public double acceptanceProbability(double newEnergy, double energy, double temperature) {

        if (newEnergy > energy) {
            return 1.0d;
        }

        if (temperature == 0.0) {
            return 0.0d;
        }

        double energyDifference = (newEnergy - energy);

        return Math.exp(energyDifference / temperature);
    }

    public double search() {
        int proposedStateIndex;
        int globalMaximumStateIndex;

        double currentEnergy;
        double neighborEnergy;
        double probability;

        // Temperature to change. Resets on done.
        double temperature = initialTemperature;

        currentStateIndex = random.nextInt(observations.size() - 1);

        globalMaximumStateIndex = currentStateIndex;
        leftIndex = currentStateIndex;
        rightIndex = currentStateIndex;

        while (temperature > 0.01) {
            proposedStateIndex = neighbor(currentStateIndex);
            currentEnergy = energy(currentStateIndex);
            neighborEnergy = energy(proposedStateIndex);
            probability = acceptanceProbability(neighborEnergy, currentEnergy, temperature);

            double acceptanceBoundary = Math.random();

            if (probability >= acceptanceBoundary) {
                currentStateIndex = proposedStateIndex;

                if (currentStateIndex < leftIndex) {
                    leftIndex = currentStateIndex;
                }

                if (this.currentStateIndex > rightIndex) {
                    rightIndex = currentStateIndex;
                }

                if (energy(currentStateIndex) > energy(globalMaximumStateIndex)) {
                    globalMaximumStateIndex = currentStateIndex;
                }
            }

            temperature *= coolingRate;
        }

        System.out.println("Boundary: " + leftIndex + " - " + rightIndex);

        return energy(globalMaximumStateIndex);
    }
}
