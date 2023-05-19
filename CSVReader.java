import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	public static final String DELIMITER = ",";

	public ArrayList<ArrayList<ArrayList<String>>> read(final File folder) {
		ArrayList<ArrayList<ArrayList<String>>> allCSVFilesContents = new ArrayList<>();

		for (File file : folder.listFiles()) {
			ArrayList<ArrayList<String>> fileContents = new ArrayList<>();

			try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = buffer.readLine()) != null) {
					String[] tempArr = line.split(DELIMITER);
					ArrayList<String> rowContent = new ArrayList<>(tempArr.length);
					for (String tempStr : tempArr) {
						rowContent.add(tempStr);
					}
					fileContents.add(rowContent);
				}
			} catch (IOException io) {
				io.printStackTrace();
			}

			allCSVFilesContents.add(fileContents);
		}

		return allCSVFilesContents;
	}
}
