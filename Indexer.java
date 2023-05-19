import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

public class Indexer {

	private ArrayList<ArrayList<ArrayList<String>>> allContents;

	public Indexer(ArrayList<ArrayList<ArrayList<String>>> allContents) {
		this.allContents = allContents;
	}

	public IndexWriter createIndex(Analyzer analyzer, Path path, Directory index) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);

		for (ArrayList<ArrayList<String>> contentsOfFiles : allContents) {
			for (int j = 1; j < contentsOfFiles.size(); j++) {
				ArrayList<String> rowContent = contentsOfFiles.get(j);
				if (rowContent.size() != 7) {
					continue;
				}
				Document doc = createDocument(rowContent);
				writer.addDocument(doc);
			}
		}

		return writer;
	}

	private Document createDocument(ArrayList<String> rowContent) {
		String artist = rowContent.get(1);
		String title = rowContent.get(2);
		String album = rowContent.get(3);
		String year = rowContent.get(4);
		String date = rowContent.get(5);
		String lyric = rowContent.get(6);

		Document doc = new Document();
		doc.add(new TextField("Artist", artist, Field.Store.YES));
		doc.add(new TextField("Title", title, Field.Store.YES));
		doc.add(new TextField("Album", album, Field.Store.YES));
		doc.add(new TextField("Year", year, Field.Store.YES));
		doc.add(new TextField("Date", date, Field.Store.YES));
		doc.add(new TextField("Lyric", lyric, Field.Store.YES));

		return doc;
	}
}
