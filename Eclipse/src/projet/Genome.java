package projet;

import java.io.IOException;
import java.util.ArrayList;

public class Genome
{
    private String genomeType = "inconnu";
    private String[] hierarchy;
    private ArrayList<String> genome;
    private int geneSizeMax = 10000;
    private ArrayList<String> geneList;
    private ArrayList<String> geneOkList;
    
    public Genome()
    {
        this.hierarchy = new String[] {"", "", "", "", "", ""};
        this.genomeType = "";
        this.genome = new ArrayList<String>();
        this.geneList = new ArrayList<String>();
        this.geneOkList = new ArrayList<String>();
    }
    
    public Genome(String id) throws IOException
    {
        this.genome = HtmlGenome.getGenome(id, this.geneSizeMax);
        this.geneOkList = new ArrayList<String>();
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
        	String g = this.geneList.get(i);
        	if(g != null && (g.length() % 3 == 0) && dseq.contains(g.substring(0,3)) && fseq.contains(g.substring(g.length()-3, g.length())))
            {
            	this.geneOkList.add(this.geneList.get(i));
            }
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
    
    ArrayList<String> getGenomeList()
    {
        return this.geneList; 
    }
}
