package no.hig.imt3591.id3;

/**
 * Defines an action that can be invoked when the leaf node of the tree is reached.
 */
public interface ITreeResult {
    /**
     * Invokes the action associated with the leaf node.
     */
    public void invoke();
}
