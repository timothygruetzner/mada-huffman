package ch.gruetzner.fhnw.mada.huffman;

public class Main {

	public static void main(String[] args) {
		if(args.length > 0) {
			String command = args[0];
			Huffman huff = new Huffman();
			if("compress".equalsIgnoreCase(command)) {
				huff.compress();
				return;
			} else if("decompress".equalsIgnoreCase(command)) {
				huff.decompress();
				return;
			}
		}
		throw new IllegalArgumentException("No such command.");
		

	}

}
