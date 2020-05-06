package ch.gruetzner.fhnw.mada.huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IO {
	
	private static final File DATA_DIR = new File("data");
	
	static {
		if(!DATA_DIR.exists()) {
			DATA_DIR.mkdir();
		}
	}
	
	private IO() {
		//static helper only
	}
	
	public static List<Integer> getCharacters(String fileName) {
		try(BufferedReader reader = getReader(fileName)) {
			List<Integer> charList = new ArrayList<>();
			int i;
			while((i=reader.read()) != -1) {
				charList.add(i);
			}//TODO: handle non-ASCII characters
			return charList;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public static void exportTableToFile(Map<Integer, String> lookupTable) {
		StringBuilder outputBuilder = new StringBuilder();
		for(Map.Entry<Integer, String> characterEntry : lookupTable.entrySet()) {
			outputBuilder.append(characterEntry.getKey()).append(":").append(characterEntry.getValue()).append("-");
		}
		
		String output = outputBuilder.substring(0, outputBuilder.length() - 1); //trim away the last "-"
		try {
			File outputFile = new File(DATA_DIR, "dec_tab.txt");
			if(outputFile.exists()) {
				outputFile.delete();
			}
			outputFile.createNewFile();
			Files.writeString(Paths.get(outputFile.toURI()), output, StandardCharsets.US_ASCII);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public static Map<String, Integer> readLookupTable() {
		String data = "";
		try(BufferedReader reader = getReader("dec_tab.txt")) {
			data = reader.lines().collect(Collectors.joining());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		
		if("".equals(data)) {
			throw new RuntimeException("No data readable in dec_tab.txt");
		}
		
		Map<String, Integer> lookupTable = new HashMap<>();
		List<String> entries = Arrays.asList(data.split("-"));
		for(String entry : entries) {
			String[] entrySplit = entry.split(":");
			//use the encoded code as key, and the actual character as value
			//--> easier for decoding
			lookupTable.put(entrySplit[1], Integer.valueOf(entrySplit[0]));
		}
		
		return lookupTable;
	}
	
    private static BufferedReader getReader(String fileName) {
        InputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(DATA_DIR, fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        return new BufferedReader(reader);
    }
    
    public static void writeCompressedContent(byte[] content) {
    	try(FileOutputStream fos = new FileOutputStream(new File(DATA_DIR, "output.dat"))) {
    		fos.write(content);
    	} catch(IOException ioe) {
    		throw new RuntimeException(ioe);
    	}
    }
    
    public static byte[] readCompressedContent() {
    	File file = new File(DATA_DIR, "output.dat");
    	byte[] compressedContent = new byte[(int)file.length()];
    	
    	try(FileInputStream fis = new FileInputStream(file)) {
    		fis.read(compressedContent);
    	} catch (IOException ioe) {
    		throw new RuntimeException(ioe);
    	}
    	
    	return compressedContent;
    }

	public static void writeCharactersToFile(List<Integer> decodedCharacters) {
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(new File(DATA_DIR, "decompress.txt")), StandardCharsets.US_ASCII)) {
			for(Integer character : decodedCharacters) {
				writer.write(character.intValue());
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
