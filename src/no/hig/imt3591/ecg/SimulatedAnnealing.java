package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Hill climbing with simulated annealing
 */
public class SimulatedAnnealing implements IRDetection {

    private final List<Point.Double> observations;
    private int currentStateIndex;
    private final Random random;

    private final double initialTemperature;
    private final double coolingRate;
    private final double temperatureLimit;

    public SimulatedAnnealing(List<Point.Double> list, double temperature, double coolingRate, double temperatureLimit) {
        this.random = new Random();
        this.observations = list;
        this.currentStateIndex = 0;
        this.initialTemperature = temperature;
        this.coolingRate = coolingRate;
        this.temperatureLimit = temperatureLimit;
    }

    /**
     * Defines what the energy is from the observations.
     * In this case this function is unnecessary what it returns is a private
     * variable - accessible within this class. However, this is just for testing
     * SA using its terminology.
     * @param stateIndex The current state index.
     * @return Double voltage (energy)
     */
    public double energy(int stateIndex) {
        return observations.get(stateIndex).getY();
    }

    /**
     * Choosing the next proposed neighbour randomly.
     * However, if the next state has a lower energy it looks one step further
     * accepting it if it has a higher energy. Otherwise, the "bas" solution.
     * @param stateIndex The current state index.
     * @return Integer next proposed neighbour.
     */
    public int neighbor(int stateIndex) {
        int nextIndicator = (Math.random() > 0.5)? 1: -1;
        int nextIndex = stateIndex + nextIndicator;

        // Handel out of bound exceptions (new random position):
        if (nextIndex < 0  ||  nextIndex > (observations.size() - 1)) {
            return random.nextInt(observations.size() - 1);
        }
        double energyDifference = (energy(nextIndex) - energy(stateIndex));

        if (energyDifference < 0.0) {
            int nextIndex2 = nextIndex + nextIndicator;

            // Accepts a better state, otherwise new random position (out of bound exception).
            if (nextIndex2 >= 0  &&  nextIndex2 < observations.size()) {
                double energyDifference2 = (energy(nextIndex2) - energy(stateIndex));
                if (energyDifference2 > 0.0) {
                    return nextIndex2;
                }
            } else {
                return random.nextInt(observations.size() - 1);
            }
        }
        return nextIndex;
    }

    /**
     * This determines the acceptance probability for choosing a bad solution. Good solutions would always
     * be picked.
     * @param newEnergy The Proposed neighbours energy.
     * @param energy The current states energy
     * @param temperature The current temperature (how much are we willing to accept bad moves)
     * @return The probability for accepting a new move.
     */
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

    /**
     * The simulated annealing schedule for finding the global maximum.
     * @return Point double, both energy (voltage) and timestamp (for drawing).
     */
    @Override
    public Point.Double getMaximum() {
        int proposedStateIndex;
        int globalMaximumStateIndex;

        double currentEnergy;
        double neighborEnergy;
        double probability;

        // Temperature to change. Resets on done.
        double temperature = initialTemperature;

        currentStateIndex = random.nextInt(observations.size() - 1);

        globalMaximumStateIndex = currentStateIndex;

        // Loop until the temperature has reach its limit.
        while (temperature > temperatureLimit) {
            proposedStateIndex = neighbor(currentStateIndex);
            currentEnergy = energy(currentStateIndex);
            neighborEnergy = energy(proposedStateIndex);
            probability = acceptanceProbability(neighborEnergy, currentEnergy, temperature);

            double acceptanceBoundary = Math.random();

            // Should we accept the new neighbour?
            if (probability >= acceptanceBoundary) {
                currentStateIndex = proposedStateIndex;

                // Keep saving the global maximum.
                if (energy(currentStateIndex) > energy(globalMaximumStateIndex)) {
                    globalMaximumStateIndex = currentStateIndex;
                }
            }

            temperature *= coolingRate;
        }

        return observations.get(globalMaximumStateIndex);
    }
}
