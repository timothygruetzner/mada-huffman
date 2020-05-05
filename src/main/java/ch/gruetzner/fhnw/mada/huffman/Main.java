package ch.gruetzner.fhnw.mada.huffman;

public class Main {

	public static void main(String[] args) {
		if(args.length > 0) {
			String command = args[0];
			
			if("compress".equalsIgnoreCase(command)) {
				new Huffman().compress();
				return;
			} else if("decompress".equalsIgnoreCase(command)) {
				
				return;
			}
		}
		throw new IllegalArgumentException("No such command.");
		

	}

}
