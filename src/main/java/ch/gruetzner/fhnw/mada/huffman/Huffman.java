package ch.gruetzner.fhnw.mada.huffman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Huffman {
	
	public void compress() {
		List<String> lines = IO.getContent("/input.txt");
		lines.forEach(s -> System.out.println(s));
		String content = lines.get(0);
		
		Map<Integer, Integer> frequencyMap = new HashMap<>();
		content.chars().forEach(character -> {
			if(frequencyMap.containsKey(character)) {
				frequencyMap.put(character, frequencyMap.get(character)+1);
			} else {
				frequencyMap.put(character, 1);
			}
		});
		
		
	}

}
