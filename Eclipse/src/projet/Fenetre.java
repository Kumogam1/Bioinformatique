package projet;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
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
	
     
    public Boolean stopp  = false;
    public Boolean stopp1 = true;
    public Boolean startt  = false;
    
    public ArrayList<String> lrf = new ArrayList<String>();
    public ArrayList<String> ldm = new ArrayList<String>();
    
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
		
		
		/* ADDING ELEMENTS TO THE WINDOW */
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBackground(Color.BLACK);
		
		/* TOP PANEL */
		
		JPanel topPanel = new JPanel();
		
		// CHECKBOX
		
		// R\u00e9gions fonctionnelles
		
		JCheckBoxCustom jCheckBoxCustomCDS = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomCentromere = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomIntron = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomMobileElem = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomNcRna = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomRRna = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomTelomere = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomTRna = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustom3Utr = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustom5Utr = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomRfAll = new projet.JCheckBoxCustom();
        
        jCheckBoxCustomCDS.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomCDS.setText("CDS");

        jCheckBoxCustomCentromere.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomCentromere.setText("Centromere");

        jCheckBoxCustomIntron.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomIntron.setText("Intron");

        jCheckBoxCustomMobileElem.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomMobileElem.setText("Mobile_element");
        
        jCheckBoxCustomNcRna.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomNcRna.setText("ncRNA");
        
        jCheckBoxCustomRRna.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomRRna.setText("rRNA");
        
        jCheckBoxCustomTelomere.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomTelomere.setText("Telomere");
        
        jCheckBoxCustomTRna.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomTRna.setText("tRNA");
        
        jCheckBoxCustom3Utr.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustom3Utr.setText("3'UTR");
        
        jCheckBoxCustom5Utr.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustom5Utr.setText("5'UTR");
        
        jCheckBoxCustomRfAll.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomRfAll.setText("Tout s\u00e9lectionner");
        
        // Domaines
        
        JCheckBoxCustom jCheckBoxCustomViruses = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomArchaea = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomBacteria = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomMito_metazoa = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomPhages = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomPlasmids = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomViroids = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomSamples = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomdsDNA_Viruses = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomEukaryota = new projet.JCheckBoxCustom();
        JCheckBoxCustom jCheckBoxCustomDomAll = new projet.JCheckBoxCustom();
        
        jCheckBoxCustomViruses.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomViruses.setText("Viruses");

        jCheckBoxCustomArchaea.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomArchaea.setText("Archaea");

        jCheckBoxCustomBacteria.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomBacteria.setText("Bacteria");

        jCheckBoxCustomMito_metazoa.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomMito_metazoa.setText("Mito_metazoa");
        
        jCheckBoxCustomPhages.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomPhages.setText("Phages");
        
        jCheckBoxCustomPlasmids.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomPlasmids.setText("Plasmids");
        
        jCheckBoxCustomViroids.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomViroids.setText("Viroids");
        
        jCheckBoxCustomSamples.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomSamples.setText("Samples");
        
        jCheckBoxCustomdsDNA_Viruses.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomdsDNA_Viruses.setText("dsDNA_Viruses");
        
        jCheckBoxCustomEukaryota.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomEukaryota.setText("Eukaryota");
        
        jCheckBoxCustomDomAll.setBackground(new java.awt.Color(0, 0, 0));
        jCheckBoxCustomDomAll.setText("Tout s\u00e9lectionner");
        
        JLabel checkboxLabelRF = new JLabel("R\u00e9gions fonctionnelles :");
        JLabel checkboxLabelDomaine = new JLabel("Domaines :");
        
        JPanel checkboxPanel = new JPanel(); 
        
        checkboxPanel.setLayout(new GridLayout(0, 2));
        
        checkboxPanel.add(checkboxLabelRF);
        checkboxPanel.add(checkboxLabelDomaine);
        
        checkboxPanel.add(jCheckBoxCustomCDS);
        checkboxPanel.add(jCheckBoxCustomViruses);
        checkboxPanel.add(jCheckBoxCustomCentromere);
        checkboxPanel.add(jCheckBoxCustomArchaea);
        checkboxPanel.add(jCheckBoxCustomIntron);
        checkboxPanel.add(jCheckBoxCustomBacteria);
        checkboxPanel.add(jCheckBoxCustomMobileElem);
        checkboxPanel.add(jCheckBoxCustomMito_metazoa);
        checkboxPanel.add(jCheckBoxCustomNcRna);
        checkboxPanel.add(jCheckBoxCustomPhages);
        checkboxPanel.add(jCheckBoxCustomRRna);
        checkboxPanel.add(jCheckBoxCustomPlasmids);
        checkboxPanel.add(jCheckBoxCustomTelomere);
        checkboxPanel.add(jCheckBoxCustomViroids);
        checkboxPanel.add(jCheckBoxCustomTRna);
        checkboxPanel.add(jCheckBoxCustomSamples);
        checkboxPanel.add(jCheckBoxCustom3Utr);
        checkboxPanel.add(jCheckBoxCustomdsDNA_Viruses);
        checkboxPanel.add(jCheckBoxCustom5Utr);
        checkboxPanel.add(jCheckBoxCustomEukaryota);
        
        checkboxPanel.add(jCheckBoxCustomRfAll);
        checkboxPanel.add(jCheckBoxCustomDomAll);
        
        // BOUTTONS START STOP § // 
       
        Icon icon_pause = new ImageIcon(getClass().getResource("/img/icons8-pause-20.png"));
	    
	    Icon icon_start = new ImageIcon(getClass().getResource("/img/icons8-start-20.png"));
	    
	    JButton start = new JButton(icon_start); 
		 
	    JButton stop  = new JButton(icon_pause);
	    
        checkboxPanel.add(start);
        checkboxPanel.add(stop);
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // BOUTTONS START STOP § // 
        
		// TREE
		
		tree = new JTree(root);
		tree.setCellRenderer(new DefaultTreeCellRenderer() {	
			/**
			 * 
			 */
			private static final long serialVersionUID = -7857980626261996475L;
			
			private Icon badIcon =  new ImageIcon(new ImageIcon(getClass().getResource("/img/emoji_red.png")).getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH));
	        private Icon goodIcon =  new ImageIcon(new ImageIcon(getClass().getResource("/img/emoji_green.png")).getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH));
	       	        
	        @Override
	        public Component getTreeCellRendererComponent(JTree tree,
	                Object value, boolean selected, boolean expanded,
	                boolean isLeaf, int row, boolean focused) {
	            Component c = super.getTreeCellRendererComponent(tree, value,
	                    selected, expanded, isLeaf, row, focused);
	            if (isLeaf ) {
	            	if (value instanceof DefaultMutableTreeNode) {
	            		Object userObj = ((DefaultMutableTreeNode) value).getUserObject();
	            		if(userObj instanceof Leaf) {
		            		if( (int)((Leaf)userObj).attribute == 0) {
		            			setIcon(goodIcon);
		            		}
		            		else if( (int)((Leaf)userObj).attribute == -1) {
		            			setIcon(badIcon);
		            		}
		            		else if( (int)((Leaf)userObj).attribute == 1) {
		            			setIcon(goodIcon);
		            		}
	            		}
	            	}
	            }
	            return c;
	        }
	    });
		
	    JScrollPane treeScroll = new JScrollPane(tree);
	    treeScroll.setPreferredSize(new Dimension(300,600));
		treeScroll.getViewport().getView().setBackground(Color.WHITE);
		treeScroll.getViewport().getView().setFont(new Font("Arial", Font.CENTER_BASELINE, 12));
		
		
		
		// PANEL TETE 
		
		JPanel paneltete = new JPanel();
		JPanel paneltete1 = new JPanel();
		
		
		paneltete.setBackground(Color.blue);
		 

       
        
        
        BoxLayout boxlayouttete = new BoxLayout(paneltete, BoxLayout.Y_AXIS);
        paneltete.setLayout(boxlayouttete);
        
        JLabel genome = new  JLabel("GENOME" ,SwingConstants.LEFT);
        
        genome.setText("<html><h1>GENOME</h1></html>");
        genome.setHorizontalTextPosition(JLabel.CENTER);
        genome.setSize(100, 10);
        
        
        paneltete.add(paneltete1);
        
        // PANEL TETE 
        
        
		// PANEL GAUCHE
        
		JPanel panel1 = new JPanel();
		BoxLayout boxlayoutpanel1 = new BoxLayout(panel1, BoxLayout.Y_AXIS);
	    panel1.setLayout(boxlayoutpanel1);
	    panel1.setBackground(Color.black);
      
            // PANEL FICHIER 
	        JPanel tt = new JPanel();
			tt.setBackground(Color.lightGray);
			tt.add(new JLabel("Arborescence des fichiers "));
			tt.setPreferredSize(new Dimension(80, 25));
			
			// PANEL BOTTONS
	        JPanel bb = new JPanel();
			bb.setBackground(Color.white);
			BoxLayout boxlayoutbb = new BoxLayout(bb, BoxLayout.X_AXIS);
		    bb.setLayout(boxlayoutbb);
		    
			 
			Icon loadIconn =  new ImageIcon(new ImageIcon(getClass().getResource("/img/emoji_orange.png")).getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH));
			Icon badIconn =  new ImageIcon(new ImageIcon(getClass().getResource("/img/emoji_red.png")).getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH));
			Icon goodIconn =  new ImageIcon(new ImageIcon(getClass().getResource("/img/emoji_green.png")).getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH));
			
		     
            JLabel feuGreen = new JLabel();
            feuGreen.setText("Termin\u00e9");
            feuGreen.setIcon(goodIconn);
            
            JLabel feured = new JLabel();
            feured.setText("Actualis\u00e9");
            feured.setIcon(loadIconn);
            
            JLabel feuOrange = new JLabel();
            feuOrange.setText("Supprim\u00e9");
            feuOrange.setIcon(badIconn);
            
            bb.add(feuGreen);
            bb.add(Box.createVerticalStrut(15)); 
            bb.add(feured);
            bb.add(Box.createVerticalStrut(15)); 
            bb.add(feuOrange);
			
		// ADD TO PANEL GAUCHE 
	    panel1.add(Box.createVerticalStrut(5)); 
		panel1.add(tt);
		panel1.add(bb);
		panel1.add(treeScroll);
		
		// PANEL GAUCHE

		
		
		
		// PANEL DROIT 
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.black);

        BoxLayout boxlayoutpanel2 = new BoxLayout(panel2, BoxLayout.Y_AXIS);
        panel2.setLayout(boxlayoutpanel2);
        
         
		
			// PANEL INFORMATION 
			
	        JPanel tt1 = new JPanel();
	        JLabel info = new JLabel("Informations"); 
	        info.setForeground(Color.black);
	        tt1.setBackground(Color.LIGHT_GRAY);
	        tt1.add(info);
			
	        // PANEL MENU
	        
	       
	        JPanel tt4  = new JPanel();
			JLabel info1 = new JLabel("Menu"); 
			tt4.setBackground(Color.LIGHT_GRAY);
			info1.setForeground(Color.black);
			tt4.add(info1);
		
		// ADD PANEL DROIT 
		panel2.add(Box.createVerticalStrut(5));
		panel2.add(tt4);
		panel2.add(checkboxPanel);
		panel2.add(Box.createVerticalStrut(10));
		panel2.add(tt1);
		panel2.add(textScroll);
		
		
		
		
		topPanel.add(panel1);
		topPanel.add(Box.createHorizontalStrut(20));
		topPanel.add(panel2);
	    
	     
		
		topPanel.setBackground(Color.BLACK);
	    topPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 
	    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
	    
	    mainPanel.add(paneltete);
	    mainPanel.add(topPanel);
	 
	    
	    /* MIDDLE PANEL */
	    
	    dlProgressBar.setPreferredSize(new Dimension(500, 20));
	    dlProgressBar.setStringPainted(true);
	     
	    JPanel middlePanel = new JPanel(new GridBagLayout());
	    GridBagConstraints cst = new GridBagConstraints();
	    
	 
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridy = 0;
        middlePanel.add(dlProgressBar,cst);
	    middlePanel.setBackground(Color.BLACK);
	   
	    cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridwidth = 1;
		cst.gridx = 0;
		cst.gridy = 1;
	    
		cst.fill = GridBagConstraints.HORIZONTAL;
		cst.gridwidth = 1;
		cst.gridx = 0;
		cst.gridy = 2;
	   
	 
	    mainPanel.add(middlePanel);
	    
	    /* BOTTOM PANEL */
	    
	    JPanel bottomPanel = new JPanel();
	    bottomPanel.setLayout(new BorderLayout());
	    
	    bottomPanel.setBackground(Color.BLACK);
	    button.setBackground(Color.DARK_GRAY);
	    button.setForeground(Color.red);
	    button.setOpaque(true);
	    
	    JLabel copyright = new JLabel("BELQASMI Amine, EGNER Ana\u00efs, GU Edouard, MAYER Thomas, MOISY Arthur");
	    copyright.setHorizontalAlignment(JLabel.CENTER);
	    copyright.setFont(new Font("Courier New", Font.CENTER_BASELINE, 12));
	    copyright.setForeground(Color.WHITE);
	    copyright.setBorder(new EmptyBorder(10,0,10,10)); //top,left,bottom,right

	    bottomPanel.add(copyright, BorderLayout.SOUTH);
	
	    mainPanel.add(bottomPanel);
	    
	    
	    /* MAIN PANEL */
	    
	    this.setContentPane(mainPanel);
	    this.setVisible(true);
	    
	    /* ACTION START STOP  */
	    
	    // START
	    start.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  start.setEnabled(false);
	    	  jCheckBoxCustomCDS.setEnabled(false);
	          jCheckBoxCustomCentromere.setEnabled(false);
	          jCheckBoxCustomIntron.setEnabled(false);
	          jCheckBoxCustomMobileElem.setEnabled(false);
	          jCheckBoxCustomNcRna.setEnabled(false);
	          jCheckBoxCustomRRna.setEnabled(false);
	          jCheckBoxCustomTelomere.setEnabled(false);
	          jCheckBoxCustomTRna.setEnabled(false);
	          jCheckBoxCustom3Utr.setEnabled(false);
	          jCheckBoxCustom5Utr.setEnabled(false);
	          jCheckBoxCustomRfAll.setEnabled(false);
	          
	          jCheckBoxCustomViruses.setEnabled(false);
    		  jCheckBoxCustomArchaea.setEnabled(false);
    		  jCheckBoxCustomBacteria.setEnabled(false);
    		  jCheckBoxCustomMito_metazoa.setEnabled(false);
    		  jCheckBoxCustomPhages.setEnabled(false);
    		  jCheckBoxCustomPlasmids.setEnabled(false);
    		  jCheckBoxCustomViroids.setEnabled(false);
    		  jCheckBoxCustomSamples.setEnabled(false);
    		  jCheckBoxCustomdsDNA_Viruses.setEnabled(false);
    		  jCheckBoxCustomEukaryota.setEnabled(false);
    		  jCheckBoxCustomDomAll.setEnabled(false);
	    	  
	          log("R\u00e9gions fonctionnelles s\u00e9lectionn\u00e9es :");
	          
	          if(jCheckBoxCustomCDS.isSelected()) {
	        	  lrf.add("CDS");
	        	  log("CDS");
	          }
	          if(jCheckBoxCustomCentromere.isSelected()) {
	        	  lrf.add("Centromere");
	        	  log("Centromere");
	          }
	          if(jCheckBoxCustomIntron.isSelected()) {
	        	  lrf.add("Intron");
	        	  log("Intron");
	          }
	          if(jCheckBoxCustomMobileElem.isSelected()) {
	        	  lrf.add("mobile_element");
	        	  log("mobile_element");
	          }
	          if(jCheckBoxCustomNcRna.isSelected()) {
	        	  lrf.add("ncRNA");
	        	  log("ncRNA");
	          }
	          if(jCheckBoxCustomRRna.isSelected()) {
	        	  lrf.add("rRNA");
	        	  log("rRNA");
	          }
	          if(jCheckBoxCustomTelomere.isSelected()) {
	        	  lrf.add("Telomere");
	        	  log("Telomere");
	          }
	          if(jCheckBoxCustomTRna.isSelected()) {
	        	  lrf.add("tRNA");
	        	  log("tRNA");
	          }
	          if(jCheckBoxCustom3Utr.isSelected()) {
	        	  lrf.add("3'UTR");
	        	  log("3'UTR");
	          }
	          if(jCheckBoxCustom5Utr.isSelected()) {
	        	  lrf.add("5'UTR");
	        	  log("5'UTR");
	          }

	          log("\n");
	          
	          log("Domaines s\u00e9lectionn\u00e9s :");
	          
	          if(jCheckBoxCustomViruses.isSelected()) {
	        	  ldm.add("Viruses");
	        	  log("Viruses");
	          }
	          if(jCheckBoxCustomArchaea.isSelected()) {
	        	  ldm.add("Archaea");
	        	  log("Archaea");
	          }
	          if(jCheckBoxCustomBacteria.isSelected()) {
	        	  ldm.add("Bacteria");
	        	  log("Bacteria");
	          }
	          if(jCheckBoxCustomMito_metazoa.isSelected()) {
	        	  ldm.add("Mito_metazoa");
	        	  log("Mito_metazoa");
	          }
	          if(jCheckBoxCustomPhages.isSelected()) {
	        	  ldm.add("Phages");
	        	  log("Phages");
	          }
	          if(jCheckBoxCustomPlasmids.isSelected()) {
	        	  ldm.add("Plasmids");
	        	  log("Plasmids");
	          }
	          if(jCheckBoxCustomViroids.isSelected()) {
	        	  ldm.add("Viroids");
	        	  log("Viroids");
	          }
	          if(jCheckBoxCustomSamples.isSelected()) {
	        	  ldm.add("Samples");
	        	  log("Samples");
	          }
	          if(jCheckBoxCustomdsDNA_Viruses.isSelected()) {
	        	  ldm.add("dsDNA_Viruses");
	        	  log("dsDNA_Viruses");
	          }
	          if(jCheckBoxCustomEukaryota.isSelected()) {
	        	  ldm.add("Eukaryota");
	        	  log("Eukaryota");
	          }
	          log("\n");
	          
	          
	    	  log("D\u00e9marrage du parsing.\n");
	    	  stop.setEnabled(true);
	    	  startt = true;
	    	  stopp = false;
	    	   
	      }
	    });
	    
	    // STOP
	    stop.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  stop.setEnabled(false);
	    	  
	    	  jCheckBoxCustomViruses.setEnabled(true);
    		  jCheckBoxCustomArchaea.setEnabled(true);
    		  jCheckBoxCustomBacteria.setEnabled(true);
    		  jCheckBoxCustomMito_metazoa.setEnabled(true);
    		  jCheckBoxCustomPhages.setEnabled(true);
    		  jCheckBoxCustomPlasmids.setEnabled(true);
    		  jCheckBoxCustomViroids.setEnabled(true);
    		  jCheckBoxCustomSamples.setEnabled(true);
    		  jCheckBoxCustomdsDNA_Viruses.setEnabled(true);
    		  jCheckBoxCustomEukaryota.setEnabled(true);
    		  jCheckBoxCustomDomAll.setEnabled(true);
    		  
    		  jCheckBoxCustomDomAll.setSelected(false);
    		  
    		  jCheckBoxCustomCDS.setEnabled(true);
	          jCheckBoxCustomCentromere.setEnabled(true);
	          jCheckBoxCustomIntron.setEnabled(true);
	          jCheckBoxCustomMobileElem.setEnabled(true);
	          jCheckBoxCustomNcRna.setEnabled(true);
	          jCheckBoxCustomRRna.setEnabled(true);
	          jCheckBoxCustomTelomere.setEnabled(true);
	          jCheckBoxCustomTRna.setEnabled(true);
	          jCheckBoxCustom3Utr.setEnabled(true);
	          jCheckBoxCustom5Utr.setEnabled(true);
	          jCheckBoxCustomRfAll.setEnabled(true);
	          
	          jCheckBoxCustomRfAll.setSelected(false);
	    	  start.setEnabled(true);
	    	  ldm.clear();
	    	  lrf.clear();
	    	  log("Interruption du parsing. \n");
	    	  stopp = true;
	      }
	    });
	    
	    // ALL Region Fonctionnelles
	    jCheckBoxCustomRfAll.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  if(jCheckBoxCustomRfAll.isSelected()) {
		    	  jCheckBoxCustomCDS.setEnabled(false);
		    	  jCheckBoxCustomCDS.setSelected(true);
		    	  
		          jCheckBoxCustomCentromere.setEnabled(false);
		          jCheckBoxCustomCentromere.setSelected(true);
		          
		          jCheckBoxCustomIntron.setEnabled(false);
		          jCheckBoxCustomIntron.setSelected(true);
		          
		          jCheckBoxCustomMobileElem.setEnabled(false);
		          jCheckBoxCustomMobileElem.setSelected(true);
		          
		          jCheckBoxCustomNcRna.setEnabled(false);
		          jCheckBoxCustomNcRna.setSelected(true);
		          
		          jCheckBoxCustomRRna.setEnabled(false);
		          jCheckBoxCustomRRna.setSelected(true);
		          
		          jCheckBoxCustomTelomere.setEnabled(false);
		          jCheckBoxCustomTelomere.setSelected(true);
		          
		          jCheckBoxCustomTRna.setEnabled(false);
		          jCheckBoxCustomTRna.setSelected(true);
		          
		          jCheckBoxCustom3Utr.setEnabled(false);
		          jCheckBoxCustom3Utr.setSelected(true);
		          
		          jCheckBoxCustom5Utr.setEnabled(false);
		          jCheckBoxCustom5Utr.setSelected(true);}
	    	  else {
	    		  jCheckBoxCustomCDS.setEnabled(true);
		          jCheckBoxCustomCentromere.setEnabled(true);
		          jCheckBoxCustomIntron.setEnabled(true);
		          jCheckBoxCustomMobileElem.setEnabled(true);
		          jCheckBoxCustomNcRna.setEnabled(true);
		          jCheckBoxCustomRRna.setEnabled(true);
		          jCheckBoxCustomTelomere.setEnabled(true);
		          jCheckBoxCustomTRna.setEnabled(true);
		          jCheckBoxCustom3Utr.setEnabled(true);
		          jCheckBoxCustom5Utr.setEnabled(true);
	    		  
	    	  }
	      }
	    });
	    
	    jCheckBoxCustomDomAll.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  if(jCheckBoxCustomDomAll.isSelected()) {
	    		  jCheckBoxCustomViruses.setEnabled(false);
	    		  jCheckBoxCustomViruses.setSelected(true);
		    	  
	    		  jCheckBoxCustomArchaea.setEnabled(false);
	    		  jCheckBoxCustomArchaea.setSelected(true);
		          
	    		  jCheckBoxCustomBacteria.setEnabled(false);
	    		  jCheckBoxCustomBacteria.setSelected(true);
		          
	    		  jCheckBoxCustomMito_metazoa.setEnabled(false);
	    		  jCheckBoxCustomMito_metazoa.setSelected(true);
		          
	    		  jCheckBoxCustomPhages.setEnabled(false);
	    		  jCheckBoxCustomPhages.setSelected(true);
		          
	    		  jCheckBoxCustomPlasmids.setEnabled(false);
	    		  jCheckBoxCustomPlasmids.setSelected(true);
		          
	    		  jCheckBoxCustomViroids.setEnabled(false);
	    		  jCheckBoxCustomViroids.setSelected(true);
		          
	    		  jCheckBoxCustomSamples.setEnabled(false);
	    		  jCheckBoxCustomSamples.setSelected(true);
		          
	    		  jCheckBoxCustomdsDNA_Viruses.setEnabled(false);
	    		  jCheckBoxCustomdsDNA_Viruses.setSelected(true);
		          
	    		  jCheckBoxCustomEukaryota.setEnabled(false);
	    		  jCheckBoxCustomEukaryota.setSelected(true);}
	    	  else {
	    		  jCheckBoxCustomViruses.setEnabled(true);
	    		  jCheckBoxCustomArchaea.setEnabled(true);
	    		  jCheckBoxCustomBacteria.setEnabled(true);
	    		  jCheckBoxCustomMito_metazoa.setEnabled(true);
	    		  jCheckBoxCustomPhages.setEnabled(true);
	    		  jCheckBoxCustomPlasmids.setEnabled(true);
	    		  jCheckBoxCustomViroids.setEnabled(true);
	    		  jCheckBoxCustomSamples.setEnabled(true);
	    		  jCheckBoxCustomdsDNA_Viruses.setEnabled(true);
	    		  jCheckBoxCustomEukaryota.setEnabled(true);
	    		  
	    	  }
	      }
	    });
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
				addedTreeNode.setUserObject(new Leaf(gen[5], 0));

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
	
	public void notLoaded(String racines, String nom) {
		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		        public void run() {
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				ArrayList<String> it = new ArrayList<String>();
				it.addAll(Arrays.asList(racines.split(";")));
				it.add(nom);
				for(String nodeName : it ) {
					for(TreeNode t : Collections.list(root.children())) {
						if(t.toString().equals(nodeName)) {
							root = (DefaultMutableTreeNode)t;
							break;
						}
					}
				}
				Leaf userObj = (Leaf) root.getUserObject();
				userObj.attribute=-1;
				root.setUserObject(userObj);
				mainPanel.setVisible(false); 
				mainPanel.setVisible(true);
		    }
		});	
	}
	
	public void loaded(String[] gen) {

		java.awt.EventQueue.invokeLater(new Runnable() {
		    @Override
		        public void run() {
					DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
					for(String nodeName : gen ) {
						for(TreeNode t : Collections.list(root.children())) {
							if(t.toString().equals(nodeName)) {
								root = (DefaultMutableTreeNode)t;
								break;
							}
						}
					}
					Leaf userObj = (Leaf) root.getUserObject();
					userObj.attribute=1;
					root.setUserObject(userObj);
					mainPanel.setVisible(false); 
					mainPanel.setVisible(true);

		    	}
			});		
	}
}
