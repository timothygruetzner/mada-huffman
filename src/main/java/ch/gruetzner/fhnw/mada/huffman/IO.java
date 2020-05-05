package ch.gruetzner.fhnw.mada.huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IO {
	
	private IO() {
		//static helper only
	}
	
	public static List<Integer> getCharacters(String fileName) {
		try(BufferedReader reader = getReader(fileName)) {
			List<Integer> charList = new ArrayList<>();
			int i;
			while((i=reader.read()) != -1) {
				charList.add(i);
			}
//			if(lines.stream().anyMatch(s -> s.contains("�"))) {
//				throw new IllegalArgumentException("Input file contains non-ASCII charachters!");
//			} //TODO: handle non-ASCII characters
			return charList;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
    private static BufferedReader getReader(String fileName) {
        InputStream inputStream = IO.class.getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        return new BufferedReader(reader);
    }

}
