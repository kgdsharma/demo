import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bigram {

	private static final String FILE = "src/BigramInputFile.txt";
	private static final int nGramCount = 2;

	public static void main(String[] args) {

		Bigram bigram = new Bigram();

		Bigram.displayBigram(bigram.readFileInput(), nGramCount);

	}

	private static List<String> getProcessedList(String input, int ngramCount) {

		String[] someString = input.split(" ");

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < someString.length - 1; i++) {
			String temp = "";
			for (int j = i; j < i + ngramCount; j++) {
				if (j <= someString.length - 1)
					temp = temp + " " + someString[j];
			}
			list.add(temp.trim());			
		}
		return list;

	}

	private static class Result {
		String ngramString;
		int ngramCount;

		public Result(String ngramString, int ngramCount ) {
			this.ngramCount = ngramCount;
			this.ngramString = ngramString;
		}

		public boolean equals(Object obj) {
			if (null == obj) {
				return false;
			}
			
			if(!(obj instanceof Result)){
				return false;
			}			

			Result rslt = (Result) obj;

			if (this.ngramString != null && this.ngramString.equals(rslt.ngramString)) {
				return true;
			}
			
			return false;

		}

		public int hashCode() {
			int hash = 7;
			hash = 49 * hash + (this.ngramString != null ? this.ngramString.hashCode() : 0);			
			return hash;
		}
	}

	private static List<Result> getResultList(String s, int nGram) {

		List<String> processedList = getProcessedList(s, nGram);
		List<Result> resultList = new ArrayList<Result>();

		for (int i = 0; i < processedList.size(); i++) {
			int count = 0;
			for (int j = 0; j < processedList.size(); j++) {
				if (processedList.get(i).equalsIgnoreCase(processedList.get(j))) {
					count++;
				}
			}

			Result rslt = new Result(processedList.get(i), count);
			resultList.add(rslt);
		}

		return resultList;

	}

	private static void displayBigram(String input, int nGramCount) {

		List<Result> rsltlst = getResultList(input, nGramCount);
		List<Result> deDupRsltlst = rsltlst.stream().distinct().collect(Collectors.toList());

		deDupRsltlst.forEach(result -> {
			System.out.println(result.ngramString + " " + result.ngramCount);
		});

	}

	private String readFileInput() {

		String inputData = null;

		try {
			inputData = new String(Files.readAllBytes(Paths.get(FILE)));

		} catch (IOException e) {			
			e.printStackTrace();
		} finally {}

		return inputData;

	}

}

