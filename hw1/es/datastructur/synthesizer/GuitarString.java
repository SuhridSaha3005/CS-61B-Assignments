package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int bufferCapacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(bufferCapacity);
        for (int i = 0; i < bufferCapacity; i += 1) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        double removed;
        double random;
        for (int i = 0; i < buffer.capacity(); i += 1) {
            removed = buffer.dequeue();
            random = Math.random() - 0.5;
            buffer.enqueue(random);
        }
    }

    public void tic() {
        double prevFront = buffer.dequeue();
        double newFront = buffer.peek();
        buffer.enqueue(DECAY * ((prevFront + newFront) / 2));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
