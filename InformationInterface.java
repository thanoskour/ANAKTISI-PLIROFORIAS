import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.apache.lucene.document.Document;

@SuppressWarnings("serial")
public class InformationInterface extends JFrame {

	private JPanel contentPanel;
	private ArrayList<Document> documents;
	private int docID;
	private String field;

	public InformationInterface(ArrayList<Document> documents, int docID, String field) {
		super("Lucene Search Engine for Songs");
		this.documents = documents;
		this.docID = docID;
		this.field = field;

		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 200, 550, 540);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> infoList = new JList<>(listModel);
		infoList.setBackground(new Color(153, 204, 204));
		infoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		infoList.setSelectedIndex(0);
		infoList.setVisibleRowCount(3);

		JScrollPane infoScrollPane = new JScrollPane(infoList);
		infoScrollPane.setBounds(1, 0, 500, 500);
		contentPanel.add(infoScrollPane);

		Document doc = documents.get(docID - 1);
		listModel.addElement("Artist : " + doc.get("Artist"));
		listModel.addElement("Title : " + doc.get("Title"));
		listModel.addElement("Album : " + doc.get("Album"));
		listModel.addElement("Year : " + doc.get("Year"));
		listModel.addElement("Date : " + doc.get("Date"));
		listModel.addElement("------------------------------- Lyrics -------------------------------");

		String lyrics = doc.get("Lyric");
		String[] lyr = lyrics.split(" ");
		for (int i = 0; i < lyr.length - 10; i += 10) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < 10; j++) {
				line.append(lyr[i + j]).append(" ");
			}
			listModel.addElement(line.toString().trim());
		}
	}

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
