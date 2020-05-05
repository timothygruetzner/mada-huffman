package ch.gruetzner.fhnw.mada.huffman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import ch.gruetzner.fhnw.mada.huffman.model.Node;

public class Huffman {
	
	public void compress() {
		List<Integer> characters = IO.getCharacters("/input.txt");
		
		//build frequency map. key: character, value: frequency
		Map<Integer, Integer> frequencyMap = new HashMap<>();
		characters.forEach(character -> {
			if(frequencyMap.containsKey(character)) {
				frequencyMap.put(character, frequencyMap.get(character)+1);
			} else {
				frequencyMap.put(character, 1);
			}
		});
		
		//use java "PriorityQueue" to keep everything sorted even when modifying the queue
		PriorityQueue<Node> queue = new PriorityQueue<>(); //our node implements comparable, so no comparator has to be set explicitly
		
		//map each map entry to one of our nodes and insert it into the queue
		frequencyMap.entrySet().stream()
			.map(entry -> new Node(entry.getKey(), entry.getValue()))
			.forEach(node -> queue.add(node));

		/*
		 * Algo for tree building:
		 * - while size is bigger than 1
		 * - poll two nodes --> they are the ones with the smallest frequency
		 * - combination of those two nodes (frequency added) is parent of both of the previous nodes 
		 */
		while(queue.size() > 1) {
			Node newParent = new Node(queue.poll(), queue.poll());
			queue.add(newParent);
		}
		
		//the last remaining node will be the absolute root node
		Node rootNode = queue.poll();
		System.out.println(rootNode);
		
		//now build a "lookup-table" by recursively passing it through the nodes
		Map<Integer, String> lookupTable = new HashMap<>();
		walkTree(rootNode, lookupTable, new StringBuilder());
		
		//print out the lookup table
		for(Map.Entry<Integer, String> tableEntry : lookupTable.entrySet()) {
			System.out.println("Character: " + ((char)tableEntry.getKey().intValue()) + ", encoded value: " + tableEntry.getValue());
		}
		
	}
	
	private void walkTree(Node node, Map<Integer, String> lookupTable, StringBuilder compressedValue) {
		//if we have a character set, we're a leaf
		if(node.getCharacter() != null) {
			lookupTable.put(node.getCharacter(), compressedValue.toString()); //add the value to the table
		} else {
			//if we're not a leaf, continue walking the tree
			walkTree(node.getLeftChild(), lookupTable, new StringBuilder(compressedValue).append("0")); //walk to the left --> append a 0
			walkTree(node.getRightChild(), lookupTable, new StringBuilder(compressedValue).append("1")); //walk to the right --> append a 1
		}
	}

}
