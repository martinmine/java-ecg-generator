package no.hig.imt3591.id3;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EntropyTest {
    @Test
    public void testEmptySet() {
        List<Observation> observations = new LinkedList<>();
        Entropy entropy = new Entropy(observations);
        assertEquals(0, entropy.getEntropy(), 0);
    }
}