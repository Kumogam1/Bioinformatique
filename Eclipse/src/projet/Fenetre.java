package projet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class Fenetre extends JFrame
{
	private static final long serialVersionUID = -5285602033430483872L;
	private JPanel  mainPanel = new JPanel();
	private JButton button = new JButton("Start");
	private JTextArea textLog = new JTextArea(65, 30);
	private JProgressBar dlProgressBar = new JProgressBar();
	private JTree tree;

	private DefaultMutableTreeNode root  = new DefaultMutableTreeNode("root");
  
	private int nbLogs = 0;
	
	public Fenetre()
	{
		this.setTitle("GenBank");
		this.setSize(1080, 900);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		textLog.setEditable(false);
		
	    /* TEXT LOG AREA */
	    
		JScrollPane textScroll = new JScrollPane(textLog);
		textLog.setBackground(Color.GRAY);
		textLog.setForeground(Color.WHITE);
		textLog.setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
		textLog.setEditable(false);
		this.log("Bienvenue. Si vous n'avez pas encore de données téléchargées, \n merci de patienter le téléchargement va bientôt démarrer. \n");
		
		/* ADDING ELEMENTS TO THE WINDOW */
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBackground(Color.BLACK);
		
		/* TOP PANEL */
		
		JPanel topPanel = new JPanel();
		
		// TREE
		tree = new JTree(root);
	    JScrollPane treeScroll = new JScrollPane(tree);
	    treeScroll.setPreferredSize(new Dimension(300, 400));
		treeScroll.getViewport().getView().setBackground(Color.WHITE);
		treeScroll.getViewport().getView().setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
	    
	    topPanel.add(treeScroll);
	    topPanel.add(Box.createHorizontalStrut(20));
	    
	    topPanel.add(textScroll);
		
		topPanel.setBackground(Color.BLACK);
	    topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
	    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
	    
	    mainPanel.add(topPanel);
	    
	    /* MIDDLE PANEL */
	    
	    dlProgressBar.setPreferredSize(new Dimension(500, 20));
	    dlProgressBar.setStringPainted(true);
	    
	    JPanel middlePanel = new JPanel();
	    middlePanel.setBackground(Color.BLACK);
	    middlePanel.add(dlProgressBar);
	    mainPanel.add(middlePanel);
	    
	    /* BOTTOM PANEL */
	    
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new BorderLayout());
	    
	    bottomPanel.setBackground(Color.BLACK);
	    button.setBackground(Color.DARK_GRAY);
	    button.setForeground(Color.WHITE);
	    button.setOpaque(true);
	    
	    JLabel copyright = new JLabel("BELQASMI Amine, EGNER Anaïs, GU Edouard, MAYER Thomas, MOISY Arthur");
	    copyright.setHorizontalAlignment(JLabel.CENTER);
	    copyright.setFont(new Font("Courier New", Font.CENTER_BASELINE, 12));
	    copyright.setForeground(Color.WHITE);
	    copyright.setBorder(new EmptyBorder(10,0,10,10)); //top,left,bottom,right

	    bottomPanel.add(copyright, BorderLayout.SOUTH);
	    
	    mainPanel.add(bottomPanel);
	    
	    
	    /* MAIN PANEL */
	    
	    this.setContentPane(mainPanel);
	    this.setVisible(true);

	    
	    /*
		// Panel mode vertical
	    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    
	    // Panel principal mode horizon
        JPanel panelP = new JPanel();
        panelP.setLayout(new BoxLayout(panelP, BoxLayout.LINE_AXIS));
        
        // Panel de gauche
	    JPanel panelL = new JPanel();
	    panelL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
	    panelL.setLayout(new BoxLayout(panelL, BoxLayout.PAGE_AXIS));
	    
	    // Label hierarchie
	    JLabel labelH = new JLabel();
	    labelH.setText("Hierarchy");
	    labelH.setForeground(Color.white);
        panelL.add(labelH);
	    
	    // Panel de droite
		JPanel panelR = new JPanel();
		panelR.setLayout(new BoxLayout(panelR, BoxLayout.PAGE_AXIS));

		// Label logs
	    JLabel labelL = new JLabel();
	    labelL.setText("Logs");
	    labelL.setForeground(Color.white);
	    panelR.add(labelL);
	    
	    // Panel logs
	    panelR.add(new JScrollPane(textLog));
	    
	    // Panel bar
	    JPanel panelRM = new JPanel();
	    pbar.setPreferredSize(new Dimension(250, 20));
	    pbar.setStringPainted(true);
	    panelRM.add(pbar);
	    Frame panelR;
		panelR.add(panelRM);
	    panelP.add(panelR);
	    panel.add(panelP);
	    
	    // Label copyright
	    JLabel copyright = new JLabel("BELQASMI Amine, EGNER Anais, GU Edouard, MAYER Thomas, MOISY Arthur");
        copyright.setHorizontalAlignment(JLabel.CENTER);
        copyright.setFont(new Font("Courier New", Font.CENTER_BASELINE, 12));
        copyright.setForeground(Color.WHITE);
        copyright.setBorder(new EmptyBorder(10,0,0,10)); 

        panel.add(copyright, BorderLayout.SOUTH); 
        
	    panel.setBackground(Color.gray);
	    panelL.setBackground(Color.gray);
	    panelR.setBackground(Color.gray);
	    panelRM.setBackground(Color.gray);
	    panelP.setBackground(Color.gray);
	    
	    this.setContentPane(panel);
	    this.setVisible(true);
	    */
	}
	
	public void addNode(String[] gen) {
		java.awt.EventQueue.invokeLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode tmp_root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				for(String nodeName : Arrays.copyOfRange(gen, 0, 5))
				{			
					for(TreeNode t : Collections.list(tmp_root.children()))
					{
						if(t.toString().equals(nodeName))
						{
							tmp_root = (DefaultMutableTreeNode) t;
							break;
						}
					}
					if(tmp_root.toString().equals(nodeName))
					{
						continue;
					}
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
					model.insertNodeInto(newNode, tmp_root, 0);
					tmp_root = newNode;
				}
				
				DefaultMutableTreeNode addedTreeNode = new DefaultMutableTreeNode(gen[5]);
				addedTreeNode.setUserObject(gen[5]);

				model.insertNodeInto(addedTreeNode, tmp_root, 0);
				model.reload(root);

				tree.setModel(model);
				for (int i = 0; i < tree.getRowCount(); i++)
				{
				    tree.expandRow(i);
				}
				mainPanel.setVisible(true);
		    }
		});
	}
	
	public void log(String log)
	{
		this.textLog.append(log + '\n');
		this.nbLogs++;
		if(nbLogs > 60)
		{
			try
			{
				this.textLog.replaceRange(null, 0, this.textLog.getLineEndOffset(9));
				nbLogs-=10; 
			} 
			catch (BadLocationException e)
			{
				System.out.println("Error at removing first lines of logs...");
				e.printStackTrace();
			}
		}
	}

	public void logProgress(String prog)
	{
		dlProgressBar.setString(prog);
	}

	public void initBarre(int max)
	{
		this.dlProgressBar.setMaximum(max);		
	}

	public void doneBarre(int val)
	{
		this.dlProgressBar.setValue(this.dlProgressBar.getValue() + val);
		this.dlProgressBar.setVisible(false);
		this.dlProgressBar.setVisible(true);
	}
	
}

