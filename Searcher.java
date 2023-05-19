import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

public class Searcher {
	private Directory index;
	private DirectoryReader ireader;
	
	public Searcher(Directory index) {
		this.index = index;
	}
	
	public IndexSearcher createSearcher(IndexWriter iwriter) throws IOException {
		ireader = DirectoryReader.open(iwriter);
		return new IndexSearcher(ireader);
	}
	
	public ArrayList<Document> parseQueryAndGetResults(IndexSearcher isearcher, Analyzer analyzer, String text, String field, int limit) throws ParseException, IOException{
		ArrayList<Document> documents = new ArrayList<>();
		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(text);
		ScoreDoc[] hits = isearcher.search(query, limit).scoreDocs;
		for(ScoreDoc hit : hits) {
			Document hitDoc = isearcher.doc(hit.doc);
			documents.add(hitDoc);
		}
		return documents;
	}
	
	public void printResults(ArrayList<Document> documents) {
		for(int i = 0; i < documents.size(); i++) {
			Document doc = documents.get(i);
			System.out.println("Document " + (i + 1) + " Information");
			System.out.println("-----------------------");
			System.out.println("Artist : " + doc.get("Artist"));
			System.out.println("Title : " + doc.get("Title"));
			System.out.println("Album : " + doc.get("Album"));
			System.out.println("Year : " + doc.get("Year"));
			System.out.println();
		}
	}
	
	public Directory getIndex() {
		return index;
	}

	public void setIndex(Directory index) {
		this.index = index;
	}

	public DirectoryReader getIReader() {
		return ireader;
	}

	public void setIReader(DirectoryReader ireader) {
		this.ireader = ireader;
	}
}
