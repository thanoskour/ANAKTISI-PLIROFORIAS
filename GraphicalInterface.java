import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings({ "unused", "serial" })
public class GraphicalInterface extends JFrame {

	private JPanel contentPanel;
	private JTextField textField;
	private JRadioButton checkboxArtist, checkboxTitle, checkboxAlbum, checkboxYear, checkboxLyrics, checkboxOther;
	private ButtonGroup group;
	private JButton buttonSearch;
	private Initializer initializer;
	private IndexSearcher isearcher;

	public static void main(String[] args) throws IOException, ParseException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalInterface frame = new GraphicalInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GraphicalInterface() throws IOException, ParseException {
		super("Lucene Search Engine for Songs");
		initializer = new Initializer();
		ArrayList<ArrayList<ArrayList<String>>> allDocuments = initializer.initializeDocumentation();
		Directory index = initializer.initializeIndex();
		IndexWriter iwriter = initializer.initializeIndexWriter(allDocuments);
		isearcher = initializer.initializeIndexSearcher(iwriter);

		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 200, 650, 600);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}

		JLabel title = new JLabel("Lucene Search Engine", SwingConstants.CENTER);
		title.setBounds(110, 120, 420, 100);
		title.setForeground(Color.BLACK);
		title.setBackground(Color.WHITE);
		title.setOpaque(true);
		title.setFont(title.getFont().deriveFont(40f));
		contentPanel.add(title);

		textField = new JTextField(25);
		textField.setBounds(70, 300, 490, 32);
		textField.setFont(textField.getFont().deriveFont(20f));
		textField.setBackground(Color.LIGHT_GRAY);
		contentPanel.add(textField);

		createRadioButtons();

		buttonSearch = new JButton("Search Docs");
		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textFieldValue = textField.getText();
				String field = getSelectedField();
				String text = field.isEmpty() ? textFieldValue : field + " : " + textFieldValue;

				try {
					ArrayList<Document> documents = initializer.getSearcher().parseQueryAndGetResults(isearcher,
							initializer.getAnalyzer(), text, field, 100);
					ResultsInterface result = new ResultsInterface(documents, initializer, text);
					result.setVisible(true);
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonSearch.setBackground(Color.BLUE);
		buttonSearch.setOpaque(true);
		buttonSearch.setBorderPainted(false);
		buttonSearch.setFont(new Font("Bold", Font.BOLD, 15));
		buttonSearch.setBounds(210, 367, 200, 30);
		contentPanel.add(buttonSearch);
	}

	private void createRadioButtons() {
		checkboxArtist = new JRadioButton("Artist");
		checkboxTitle = new JRadioButton("Title");
		checkboxAlbum = new JRadioButton("Album");
		checkboxYear = new JRadioButton("Year");
		checkboxLyrics = new JRadioButton("Lyrics");
		checkboxOther = new JRadioButton("Other");
		group = new ButtonGroup();
		group.add(checkboxArtist);
		group.add(checkboxTitle);
		group.add(checkboxAlbum);
		group.add(checkboxYear);
		group.add(checkboxLyrics);
		group.add(checkboxOther);
		checkboxArtist.setBounds(110, 337, 60, 20);
		contentPanel.add(checkboxArtist);
		checkboxTitle.setBounds(180, 337, 60, 20);
		contentPanel.add(checkboxTitle);
		checkboxAlbum.setBounds(250, 337, 60, 20);
		contentPanel.add(checkboxAlbum);
		checkboxYear.setBounds(320, 337, 60, 20);
		contentPanel.add(checkboxYear);
		checkboxLyrics.setBounds(390, 337, 60, 20);
		contentPanel.add(checkboxLyrics);
		checkboxOther.setBounds(460, 337, 60, 20);
		contentPanel.add(checkboxOther);
	}

	private String getSelectedField() {
		if (checkboxArtist.isSelected()) {
			return "Artist";
		} else if (checkboxTitle.isSelected()) {
			return "Title";
		} else if (checkboxAlbum.isSelected()) {
			return "Album";
		} else if (checkboxYear.isSelected()) {
			return "Year";
		} else if (checkboxLyrics.isSelected()) {
			return "Lyric";
		} else {
			return "";
		}
	}
}

