import java.util.Arrays;

public class BitArray {

    private int absoluteSize;        // Absolute size of the array in term of bits
    private byte[] bitArray;         // array of bytes that will contain the bits

    /**
     * Constructor of the array, takes size of array as input
     */
    public BitArray(int size) {
        this.absoluteSize = size;
        this.bitArray = new byte[this.bitToByte(size)];
    }

    /**
     * Calculates the number of required byte for the bitArray
     */
    private int bitToByte(int size) {
        return (size / 8) + ((size % 8 != 0) ? 1 : 0);
    }

    /**
     * sets value of bit at position <B>pos</B> to 1
     */
    public void set(int pos) {
        int i = pos / 8;
        byte k = (byte) (pos % 8);
        byte flag = (byte) (1 << k);
        this.bitArray[i] = (byte) (this.bitArray[i] | flag);
    }

    /**
     * sets value of bit at position <B>pos</B> to 0
     */
    public void clear(int pos) {
        int i = pos / 8;
        byte k = (byte) (pos % 8);
        byte flag = (byte) ~(1 << k);
        this.bitArray[i] = (byte) (this.bitArray[i] & flag);
    }

    /**
     * returns value of bit at position <B>pos</B>
     */
    public byte get(int pos) {
        int i = pos / 8;
        byte k = (byte) (pos % 8);
        byte flag = (byte) (1 << k);
        return (byte) ((this.bitArray[i] & flag) != 0 ? 1 : 0);
    }

    /**
     * returns size of the array
     */
    public int size() {
        return this.absoluteSize;
    }

	@Override
	public String toString() {
		int[] sequence = new int[absoluteSize];
		for (int i = 0; i < absoluteSize; i++) sequence[i] = get(i);
		return Arrays.toString(sequence);
	}
}
