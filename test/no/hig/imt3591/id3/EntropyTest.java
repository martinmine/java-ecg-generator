package no.hig.imt3591.id3;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EntropyTest {
    @Test
    public void testEmptySet() {
        List<Observation<Object>> observations = new LinkedList<>();
        Entropy<Object> entropy = new Entropy<>(observations);
        assertEquals(0, entropy.getEntropy(), 0);
    }
}