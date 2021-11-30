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
    
    public Genome(String id, String rf) throws IOException
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
        
        this.geneList = HtmlGenome.getSeq(id, this.genome, geneSize, this.geneSizeMax, rf);
        
        for(int i = 0; i < this.geneList.size() ; i++)
        {
        	String g = this.geneList.get(i).get(0);

        	if(g != null 
        		&& !g.isEmpty() 
        		&& (this.geneList.get(i).get(2) != "simple" 
        			|| ((g.length() % 3 == 0)
        			&& dseq.contains(g.substring(0,3)) 
        			&& fseq.contains(g.substring(g.length()-3, g.length())))))
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
    
    ArrayList<List<String>> getGenomeList()
    {
        return this.geneList; 
    }
    
    ArrayList<List<String>> getGenomeOkList()
    {
        return this.geneOkList; 
    }
}
