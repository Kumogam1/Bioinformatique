package projet;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlGenome
{	
    /**
     * Recupere la sequence adn du genome
     * 
     * @param geneID		Numero du genome
     * @param maxLength		Taille maximale du genome
     * @return				Liste contenant la sequence ADN
     * @throws IOException
     */
    public static ArrayList<String> getGenome(String geneID, int maxLength) throws IOException
    {
    	ArrayList<String> adn = new ArrayList<String>();
        String str = "";

        int x = 0;
        
        URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + geneID + "&rettype=fasta&retmode=text");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String line = reader.readLine();
        line = reader.readLine();
        while(line != null)
        {
            if(x + line.length() < maxLength)
            {
                str += line;
                x += line.length();
            }
            else if(x + line.length() == maxLength)
            {
                adn.add(str + line);
                x = 0;
                str = "";
            }
            else
            {
                int plusLength = x + line.length() - maxLength;
                
                adn.add(str + line.substring(0, line.length() - plusLength));

                str = line.substring((line.length() - plusLength), line.length());
                x = str.length();     
            }
            line = reader.readLine();
        }

        if(x > 0){
           adn.add(str);
        }
        reader.close();
            
        return adn;
    }
    
    /**
     * Recupere la hierarchie du genome
     * 
     * @param geneID	Numero du genome
     * @return			Tableau contenant la hirarchie
     * @throws IOException
     */
    public static String[] getHierarchy(String geneID) throws IOException
    {
        String[] hierarchy = new String[] {"", "", "", "", "", ""};

        int find = 0;

        URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + geneID + "&rettype=gbwithparts&retmode=text");
        
        BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = buffer.readLine();
        while(line != null && find == 0)
        {
            Matcher m1 = Pattern.compile("\\s+ORGANISM\\s+").matcher(line);
            
            if (m1.find())
            {
                hierarchy[5] = m1.replaceFirst("");
                line = buffer.readLine();
                
                Matcher m2 = Pattern.compile("[^\\.]*\\.").matcher(line);
                while (!m2.find())
                {
                    String linesupp = buffer.readLine();
                    line += linesupp;
                    m2 = Pattern.compile("[^\\.]*\\.").matcher(linesupp);
                }
                line = line.replaceAll("\\s", "");

                String[] splitArray = line.split(";", 6);
                int i = 0;
                while(i < 5 && i < splitArray.length)
                {
                    hierarchy[i] = splitArray[i];
                    i++;
                }
                find = 1;
            } 
            line = buffer.readLine();
        }
        buffer.close();
        
        return hierarchy;
    }
    
    
    /** 
     * Recherche le type du genome
     * 
     * @param geneID	Numero du genome
     * @return			Type du genome
     * @throws IOException
     */
    public static String getGenomeType(String geneID) throws IOException
    {                
        List<Matcher> matcherList = new ArrayList<Matcher>();
        List<String> genList = new ArrayList<String>();
        genList.add("chromosome");
        genList.add("DNA");
        genList.add("mitochondrion");
        genList.add("chloroplast");
        genList.add("plasmid");
        genList.add("RNA");
        
        int compteur = 0;
        
        URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + geneID + "&rettype=gbwithparts&retmode=text");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = buffer.readLine();
        while(compteur < 100 && line != null)
        {
        	matcherList.add(Pattern.compile("DEFINITION.*chromosome").matcher(line));
        	matcherList.add(Pattern.compile("=.*DNA").matcher(line));
        	matcherList.add(Pattern.compile("DEFINITION.*mitochondrion").matcher(line));
        	matcherList.add(Pattern.compile("chloroplast").matcher(line));
        	matcherList.add(Pattern.compile("DEFINITION.*plasmid").matcher(line));
        	matcherList.add(Pattern.compile("=.*RNA").matcher(line));
            
        	for(int i = 0; i < matcherList.size(); i++)
        	{
        		if(matcherList.get(i).find())
        		{
        			return genList.get(i);
        		}
        	}
        	
        	matcherList.clear();
            compteur++;
            line = buffer.readLine();
        }
        
        buffer.close();
        
        return null;
    }
    
    /**
     * Recuperation de la sequence du gene
     * 
     * @param geneID			Numero du genome
     * @param genome			Genome
     * @param genSize			Taille du genome
     * @param genSizeString		Taille du genome en chaine de caractere
     * @return
     * @throws IOException
     */
    public static ArrayList<String> getSeq(String geneID, ArrayList<String> genome, int genSize, int genSizeString) throws IOException
    {
    	ArrayList<String> res = new ArrayList<String>();
        
        URL url = new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id=" + geneID + "&rettype=gbwithparts&retmode=text");
      
        Pattern pCDS = Pattern.compile("CDS\\s+((\\d+\\.\\.\\d+)|(join)|(complement))");
        Pattern pGene = Pattern.compile(".*=.*");
        Pattern pNull = Pattern.compile("CDS\\s+(\\d+\\.\\.\\d+)");
        Pattern pComplement = Pattern.compile("CDS\\s+complement\\(((\\d+\\.\\.\\d+,)*\\d+\\.\\.\\d+)\\)");
        Pattern pJoin = Pattern.compile("CDS\\s+join\\(((\\d+\\.\\.\\d+,)+\\d+\\.\\.\\d+)\\)");
        Pattern pCompJoin = Pattern.compile("CDS\\s+complement\\(join\\(((\\d+\\.\\.\\d+,)+\\d+\\.\\.\\d+)\\)\\)");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = reader.readLine();

        while(line != null)
        {
            if(pCDS.matcher(line).find())
            {
                Matcher m = pGene.matcher(line);
                String linesupp;
                
                int exit = 0;
                while (!m.find() && exit == 0)
                {
                    if((linesupp = reader.readLine()) != null)
                    {
                        linesupp = linesupp.replaceAll("\\s", "");
                        line += linesupp;
                        m = pGene.matcher(linesupp);
                    }
                    else
                    {
                    	exit = 1;
                    }
                }

                Matcher mpNull = pNull.matcher(line);
                Matcher mpComplement = pComplement.matcher(line);
                Matcher mpJoin = pJoin.matcher(line);
                Matcher mpCompJoin = pCompJoin.matcher(line);

                while (mpNull.find())
                {
                    String s = verifGene("simple", mpNull.group(1), genome, genSize, genSizeString);
                    if(s != null) res.add(s);
                }
                while (mpComplement.find())
                {
                    String s = verifGene("complement", mpComplement.group(1), genome, genSize, genSizeString);
                    if(s != null) res.add(s);
                }
                while (mpJoin.find())
                {
                    String s = verifGene("join", mpJoin.group(1), genome, genSize, genSizeString);
                    if(s != null) res.add(s);
                }   
                while (mpCompJoin.find())
                {
                    String s = verifGene("joincomplement", mpCompJoin.group(1), genome, genSize, genSizeString);
                    if(s != null) res.add(s);
                }
            }
            line = reader.readLine();
        }
        reader.close();
            
        return res;
    }
    
    /**
     * Verification de la sequence genetique
     * 
     * @param cds			
     * @param genome		
     * @param genSize
     * @param genSizeString
     * @return
     */
    public static String verifGene(String type, String cds, ArrayList<String> genome, int genSize, int genSizeString)
    {
    	String res = "";
		String gene = "";
        String[] ids = cds.split("\\.\\.");
        if(ids.length != 2)
        {
            return null;
        }
        int bInf = Integer.parseInt(ids[0]);
        int bSup = Integer.parseInt(ids[1]);
    	
    	switch(type)
    	{
	    	case "join":
	    		int lastborne=0;
	            String[] result = cds.split(",");
	            for (int i=0; i<result.length; i++)
	            {
	            	ids = result[i].split("\\.\\.");
	                if(ids.length != 2)
	                {
	                    System.out.println("genome.join : erreur nombre de borne aprs split = "+ids.length);
	                    System.exit(1);
	                }
	                bInf = Integer.parseInt(ids[0]);
	                bSup = Integer.parseInt(ids[1]);
	                
	                if(((0 < bInf) && (bInf <= bSup) && (bSup <= genSize)) && (bInf > lastborne))
	                {
	                    lastborne = bSup;
	                    gene += getGeneSeqFromAdn(bInf-1, bSup, genome, genSizeString);
	                }
	                else
	                {
	                    return null;
	                }
	            }
	            res = gene.toLowerCase();
	    		break;
	    	case "complement":
	            if(ids.length != 2)
	            {
	                System.out.println("genome.complement : erreur nombre de borne apr�s split = "+ids.length);
	                System.exit(1);
	            }
	            if((0 < bInf) && (bInf <= bSup) && (bSup <= genSize))
	            {	                
	                StringBuilder input1 = new StringBuilder(); 
		            input1.append(getGeneSeqFromAdn(bInf-1, bSup, genome, genSizeString)); 
		            input1 = input1.reverse(); 
		            
		            gene =  input1.toString();
	            }
	            else
	            {
	                System.out.println("genome.complement : bornes injuste"+ids.length);
	                return null;
	            }
	            res = gene.toLowerCase();
	    	case "simple":
	            if(ids.length != 2)
	            {
	                System.exit(1);
	            }
	            if(((0 < bInf) && (bInf <= bSup) && (bSup <= genSize)))
	            {
	                gene = getGeneSeqFromAdn(bInf-1, bSup, genome, genSizeString);
	            }
	            else
	            {
	                System.out.println("Bornes rincees" + bInf + " " + bSup + " " + genSize);
	                return null;
	            }
	            res = gene.toLowerCase();
	    		break;
	    	case "joincomplement":
	            StringBuilder input1 = new StringBuilder(); 
	            input1.append(verifGene("join", cds, genome, genSize, genSizeString)); 
	            input1 = input1.reverse(); 
	            res =  input1.toString();
	    		break;
    	}
    	return res;
    }
    
    /**
     * Recuperation de la sequence genetique
     * 
     * @param binf			Borne inferieure
     * @param bsup			Borne superieure
     * @param genome		Genome
     * @param maxSizeAdn	Taille maximale de l'ADN
     * @return
     */
    public static String getGeneSeqFromAdn(int binf, int bsup, ArrayList<String> genome, int maxSizeAdn)
    {
        if(binf >= bsup) return null;
        
        String res;
        
        int binfTab = binf/maxSizeAdn;
        int bsubTab = bsup/maxSizeAdn;
        
        if(binfTab == bsubTab)
        {
        	res = genome.get(binfTab).substring(binf % maxSizeAdn, bsup % maxSizeAdn);
        }
        else
        {
        	res = genome.get(binfTab).substring(binf % maxSizeAdn, genome.get(binfTab).length());
            binfTab++;
            while(binfTab < bsubTab)
            {
            	res += genome.get(binfTab);
                binfTab++;
            }
            res += genome.get(binfTab).substring(0, (bsup) % maxSizeAdn);
        }
        return res;
    }
}
