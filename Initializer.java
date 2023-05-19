import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Initializer {

	private Analyzer analyzer = new StandardAnalyzer();
	private Path indexPath = Paths.get("C:\\Users\\User\\Desktop\\Anaktisi");
	private Directory index;
	private Searcher searcher;

	public ArrayList<ArrayList<ArrayList<String>>> initializeDocumentation() {
		CSVReader csvReader = new CSVReader();
		File folder = new File("C:\\Users\\User\\Desktop\\Anaktisi\\csv");
		return csvReader.read(folder);
	}

	public Directory initializeIndex() throws IOException {
		index = FSDirectory.open(indexPath);
		return index;
	}

	public IndexWriter initializeIndexWriter(ArrayList<ArrayList<ArrayList<String>>> allContents) throws IOException {
		Indexer indexer = new Indexer(allContents);
		IndexWriter writer = indexer.createIndex(analyzer, indexPath, index);
		searcher = new Searcher(index);
		return writer;
	}

	public IndexSearcher initializeIndexSearcher(IndexWriter iwriter) throws IOException, ParseException {
		return searcher.createSearcher(iwriter);
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Path getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(Path indexPath) {
		this.indexPath = indexPath;
	}

	public Directory getIndex() {
		return index;
	}

	public void setIndex(Directory index) {
		this.index = index;
	}

	public Searcher getSearcher() {
		return searcher;
	}

	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}
}
