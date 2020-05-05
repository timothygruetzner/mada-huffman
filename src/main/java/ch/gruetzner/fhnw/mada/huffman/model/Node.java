package ch.gruetzner.fhnw.mada.huffman.model;

public class Node implements Comparable<Node> {
	
	private Integer character;
	private int frequency;
	
	private Node leftChild;
	private Node rightChild;
	
	/**
	 * Builds this node as a leaf
	 * 
	 * @param character The ASCII code for this character
	 * @param frequency The frequency of this character
	 */
	public Node(int character, int frequency) {
		this.character = character;
		this.frequency = frequency;
	}
	
	/**
	 * Builds this node as a parent of two children with a combined frequency
	 * 
	 * @param leftChild The left ('0') node
	 * @param rightChild The right ('1') node
	 */
	public Node(Node leftChild, Node rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.frequency = leftChild.frequency + rightChild.frequency;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	/**
	 * Returns the integer representing the character of this node.
	 * Returns <code>null</code> if this node is not a leaf!
	 * @return integer or null
	 */
	public Integer getCharacter() {
		return this.character;
	}
	
	@Override
	public int compareTo(Node other) {
		return this.frequency - other.frequency;
	}
	
	/**
	 * Overridden for testing purposes
	 */
	@Override
	public String toString() {
		if(character != null) {
			return "Character: " + character + ", frequency: " + frequency;
		}
		return "non-leaf node, frequency: " + frequency;
	}

	public int getFrequency() {
		return this.frequency;
	}

}
