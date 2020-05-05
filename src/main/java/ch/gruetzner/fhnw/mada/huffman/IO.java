package ch.gruetzner.fhnw.mada.huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class IO {
	
	private IO() {
		//static helper only
	}
	
	public static List<String> getContent(String fileName) {
		try(BufferedReader reader = getReader(fileName)) {
			List<String> lines = reader.lines().collect(Collectors.toList());
			if(lines.stream().anyMatch(s -> s.contains("�"))) {
				throw new IllegalArgumentException("Input file contains non-ASCII charachters!");
			}
			return lines;
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
