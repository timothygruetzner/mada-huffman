package ch.gruetzner.fhnw.mada.huffman;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import ch.gruetzner.fhnw.mada.huffman.model.Node;

public class Huffman {
	
	public void compress() {
		List<Integer> inputCharacters = IO.getCharacters("input.txt");
		
		//build frequency map. key: character, value: frequency
		Map<Integer, Integer> frequencyMap = new HashMap<>();
		inputCharacters.forEach(character -> {
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
		System.out.println("Huffman tree built.");
		
		//now build a "lookup-table" by recursively passing it through the nodes
		Map<Integer, String> lookupTable = new HashMap<>();
		walkTree(rootNode, lookupTable, new StringBuilder());
		
		//output the table to the file
		IO.exportTableToFile(lookupTable);
		System.out.println("Lookup table built and written to file.");
		
		//using a string builder, we encode every character of the initial input
		StringBuilder compressedInput = new StringBuilder();
		for(Integer character : inputCharacters) {
			String mappedValue = lookupTable.get(character);
			if(mappedValue == null) {
				throw new IllegalStateException("Something is wrong with the algorithm if we don't have a mapped value.");
				
			}
			compressedInput.append(mappedValue);
		}
		
		compressedInput.append("1");
		while(compressedInput.length() % 8 != 0) {
			compressedInput.append("0");
		}
		
		byte[] compressedBytes = new BigInteger(compressedInput.toString(), 2).toByteArray();
		IO.writeCompressedContent(compressedBytes);
		System.out.println("Input compressed and written to file.");
		System.out.println("Shutting down.");
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

	public void decompress() {
		byte[] compressedData = IO.readCompressedContent();
		
		/*
		 * we do not use BigInteger for the toString functionality here - 
		   rather we go through each byte, convert it to a bit string using
		   the Integer class, and pad it with zeros to ensure a length of 8. 
		 */
		StringBuilder bytesString = new StringBuilder();
		for(byte b : compressedData) {
			//use string format to ensure length of 8 characters for each byte. replace the used padding (' ') with '0'
			String byteString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
			
			//sometimes it happens that the entire first bit is empty. we don't what that "padding only"
			if(byteString.equals("00000000")) {
				continue;
			}
			bytesString.append(byteString);
		}
		
		//trim away the last 1 and all zeros after 
		int lastOneIndex = bytesString.lastIndexOf("1");
		String compressedBitstring = bytesString.substring(0, lastOneIndex).toString();
		
		//read out the lookup table from file
		Map<String, Integer> lookupTable = IO.readLookupTable();
		
		/*
		 * as the huffman algorithm is "uniquely decodable" (eindeutig dekodierbar),
		 * we now can "walk the tree" (--> add characters one by one until we have an entry
		 * in our table) and will in the end have decoded every character  
		 */
		List<Integer> decodedCharacters = new ArrayList<Integer>();
		StringBuilder currentCharacter = new StringBuilder();
		
		for(int i = 0; i < compressedBitstring.length(); i++) {
			currentCharacter.append(compressedBitstring.charAt(i));
			
			if(lookupTable.containsKey(currentCharacter.toString())) {
				decodedCharacters.add(lookupTable.get(currentCharacter.toString()));
				currentCharacter = new StringBuilder();
			}
		}
		
		//save all characters to file
		IO.writeCharactersToFile(decodedCharacters);
		
	}

}
