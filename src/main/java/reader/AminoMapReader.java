package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 
 * @author Justin
 *
 */
public class AminoMapReader {

	/**
	 * Reads the amino acid encoding into a map.
	 * 
	 * @param file
	 *            Path name
	 * @return Map with amino acid encoding
	 * @throws IOException
	 *             File is invalid
	 */
	public static HashMap<String, String> readIntoMap(final String file)
			throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] split = line.split(" ");
			map.put(split[0], split[1]);
		}
		br.close();
		return map;
	}
}
