package projet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Genome
{
    private String genomeType = "inconnu";
    private String[] hierarchy;
    private ArrayList<String> genome;
    private int geneSizeMax = 10000;
    private ArrayList<List<String>> geneList;
    private ArrayList<List<String>> geneOkList;
    
    public Genome()
    {
        this.hierarchy = new String[] {"", "", "", "", "", ""};
        this.genomeType = "";
        this.genome = new ArrayList<String>();
        this.geneList = new ArrayList<List<String>>();
        this.geneOkList = new ArrayList<List<String>>();
    }
    
    public Genome(String id) throws IOException
    {
        this.genome = HtmlGenome.getGenome(id, this.geneSizeMax);
        this.geneOkList = new ArrayList<List<String>>();
        this.hierarchy = HtmlGenome.getHierarchy(id);
        this.genomeType =  HtmlGenome.getGenomeType(id);
        
        String dseq = "ata,atc,atg,att,ctg,gtg,tta,ttg";
        String fseq = "taa,tag,tga,tta";
        
        int geneSize = 0;
        for(int i = 0; i < this.genome.size(); i++)
        {
        	geneSize += this.genome.get(i).length();
        }
        
        this.geneList = HtmlGenome.getSeq(id, this.genome, geneSize, this.geneSizeMax);
        
        for(int i = 0; i < this.geneList.size() ; i++)
        {
        	String g = this.geneList.get(i).get(0);
        	
        	/*if(this.geneList.get(i).get(2).equals("join") || this.geneList.get(i).get(2).equals("joincomplement"))
        	{
	        	System.out.println("genome.java : " + this.geneList.get(i).get(0));
				System.out.println("genome.java nonnull ? : " + (g != null));
				System.out.println("genome.java nonvide ? : " + !g.isEmpty());
				System.out.println("genome.java %3 ? : " + (g.length() % 3 == 0));
	        	System.out.println();
        	}*/
        	
        	if(g != null 
        		&& !g.isEmpty() 
        		&& (this.geneList.get(i).get(2) != "simple" 
        			|| (dseq.contains(g.substring(0,3)) 
        			&& fseq.contains(g.substring(g.length()-3, g.length()))
        			&& (g.length() % 3 == 0))))
            {
            	//System.out.println("ISOK");
        		//if(this.geneList.get(i).get(2).equals("join") || this.geneList.get(i).get(2).equals("joincomplement"))
        		//	System.out.println("genome.java : " + this.geneList.get(i).get(1));
        		this.geneOkList.add(this.geneList.get(i));
            	//System.out.println();
            }
        	//System.out.println();
        }
    }
    
    ArrayList<String> getGenome()
    {
        return this.genome; 
    }
    
    public String[] getHierarchy()
    {
        return this.hierarchy;
    }

    String getGenomeType()
    {
        return this.genomeType; 
    }
    
    ArrayList<List<String>> getGenomeList()
    {
        return this.geneList; 
    }
    
    ArrayList<List<String>> getGenomeOkList()
    {
        return this.geneOkList; 
    }
}
