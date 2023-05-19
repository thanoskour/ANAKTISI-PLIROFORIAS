import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

@SuppressWarnings("serial")
public class ResultsInterface extends JFrame {

	private JPanel contentPanel;
	private ArrayList<Document> documents;
	private Initializer initializer;
	private String field;
	private int index = 0;
	private int counter = 0;

	public ResultsInterface(ArrayList<Document> documents, Initializer initializer, String field) {
		super("Lucene Search Engine for Songs");
		this.documents = documents;
		this.initializer = initializer;
		this.field = field;

		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 200, 650, 600);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		JLabel title = new JLabel("Search Results", SwingConstants.CENTER);
		title.setBounds(200, 140, 200, 30);
		title.setForeground(Color.BLACK);
		title.setBackground(Color.WHITE);
		title.setOpaque(true);
		title.setFont(title.getFont().deriveFont(20f));
		contentPanel.add(title);

		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> docsList = new JList<>(listModel);
		docsList.setBackground(new Color(153, 204, 204));
		docsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		docsList.setSelectedIndex(0);
		docsList.setVisibleRowCount(3);

		JScrollPane docsScrollPane = new JScrollPane(docsList);
		docsScrollPane.setBounds(160, 200, 300, 160);
		contentPanel.add(docsScrollPane);

		while (index < documents.size()) {
			if (index == 10) {
				break;
			} else {
				listModel.addElement("Document " + (index + 1) + " - " + field + " - Double click to see full info.");
				index++;
			}
		}

		MouseListener mouseListener2 = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList<?> theList = (JList<?>) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						String str = o.toString();
						String snum = "" + str.charAt(9);
						for (int i = 1; i < 1000; i++) {
							String ssnum = "" + str.charAt(9 + i);
							if (!ssnum.equals(" ")) {
								snum = snum + ssnum;
							} else {
								break;
							}
						}
						int inum = Integer.valueOf(snum);
						InformationInterface info = new InformationInterface(documents, inum, field);
						info.setVisible(true);
					}
				}
			}
		};

		JButton buttonGoBack = new JButton("Go Back");
		buttonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					GraphicalInterface mainFrame = new GraphicalInterface();
					mainFrame.setVisible(true);
				} catch (IOException e1) {
					// TODO
				} catch (ParseException e2) {
					// TODO
				}
			}
		});
		buttonGoBack.setBackground(Color.BLUE);
		buttonGoBack.setOpaque(true);
		buttonGoBack.setBorderPainted(false);
		buttonGoBack.setFont(new Font("Bold", Font.BOLD, 15));
		buttonGoBack.setBounds(0, 0, 100, 30);
		contentPanel.add(buttonGoBack);
	
		JButton buttonShowNext = new JButton("Show Next");
		buttonShowNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index != documents.size()) {
					if (index <= 90) {
						counter = 0;
						listModel.removeAllElements();
						int i = 0;
						while (index < documents.size()) {
							if (i == 10) {
								break;
							} else {
								listModel.addElement("Document " + (index + 1) + " - " + field
										+ " - Double click to see full info.");
								index++;
								i++;
								counter++;
							}
						}
					}
				}
			}
		});
		buttonShowNext.setBackground(Color.BLUE);
		buttonShowNext.setOpaque(true);
		buttonShowNext.setBorderPainted(false);
		buttonShowNext.setFont(new Font("Bold", Font.BOLD, 13));
		buttonShowNext.setBounds(330, 362, 130, 30);
		contentPanel.add(buttonShowNext);
	
		JButton buttonShowPrevious = new JButton("Show Previous");
		buttonShowPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index >= 11) {
					if (index >= 0) {
						listModel.removeAllElements();
						int i = 0;
						index = index - counter - 10;
						while (index < documents.size()) {
							if (i == 10) {
								break;
							} else {
								listModel.addElement("Document " + (index + 1) + " - " + field
										+ " - Double click to see full info.");
								index++;
								i++;
							}
						}
					}
				}
			}
		});
		buttonShowPrevious.setBackground(Color.BLUE);
		buttonShowPrevious.setOpaque(true);
		buttonShowPrevious.setBorderPainted(false);
		buttonShowPrevious.setFont(new Font("Bold", Font.BOLD, 13));
		buttonShowPrevious.setBounds(162, 362, 130, 30);
		contentPanel.add(buttonShowPrevious);
	
		docsList.addMouseListener(mouseListener2);
	}
	
	public ArrayList<Document> getDocuments() {
		return documents;
	}
	
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
	
	public Initializer getInitializer() {
		return initializer;
	}
	
	public void setInitializer(Initializer initializer) {
		this.initializer = initializer;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}

}
	
