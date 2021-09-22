package projet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Fenetre f = new Fenetre();

        String[] ids = new String[] {"Viruses","Eukaryota", "Archaea", "Bacteria", "Mito_metazoa", "Phages", "Plasmids", "Samples", "Viroids", "dsDNA_Viruses"};

        ArrayList<String> d = new ArrayList<String>();
		ArrayList<String> dS = new ArrayList<String>();
		
		String[] hierarchy ;
		String[] oldHierarchy = null;
		
        Hashtable<String, ArrayList<String>> ncs = getNcs(ids);
        
        int nbNcs = 0;
        
        for (String id : ids)
        	nbNcs += ncs.get(id).size();
        
        f.initBarre(nbNcs);
        
        ArrayList<String> h = new ArrayList<String>(Arrays.asList("NC_000001", "NC_000010", "NC_000011", "NC_000012", "NC_000013", "NC_000014", "NC_000015", "NC_000016", "NC_000017", "NC_000018", "NC_000019", "NC_000002", "NC_000020", "NC_000021", "NC_000022", "NC_000003", "NC_000004", "NC_000005", "NC_000006", "NC_000007", "NC_000008", "NC_000009", "NC_012920", "NC_000023", "NC_000024", "NC_011137", "NC_013993"));
        
        for (String id : ids)
        {
	        ArrayList<String> nbId = ncs.get(id);
	        if (id == "Eukaryota")
	        {
	        	h.addAll(nbId);
	        	nbId = (ArrayList<String>) h;
	        }

			for (String nc : nbId)
			{

	        	f.doneBarre(1);
	        	f.logProgress(nc);
	        	
	        	Genome g = getGenome(nc);
	        	if(g == null)
	        	{
	        		f.log("ERROR : " + nc);
	        		continue;
	        	}
	        	hierarchy = g.getHierarchy();

	    		if(!(Arrays.equals(hierarchy, oldHierarchy)))
	    		{
	    			d.addAll(dS);
	    			dS = new ArrayList<String>();
            		f.addNode( hierarchy );
             		if (oldHierarchy != null )
             		{
             			ArrayList<String> tab = new ArrayList<String>();
             			
             			for(int i = 0; i < g.getGenome().size(); i++)
             				tab.add(g.getGenome().get(i));

            			String fileSerialize = "./Results/"+String.join("/", oldHierarchy) +".txt";
            			File tmp = new File(fileSerialize);
            			tmp.getParentFile().setWritable(true);
            			tmp.getParentFile().mkdirs();
						FileOutputStream fos = new FileOutputStream(tmp);
			            ObjectOutputStream oos = new ObjectOutputStream(fos);
			            oos.writeObject(tab);
			            oos.close();

			            fos.close();
            		}
            		oldHierarchy = hierarchy;
            		f.log("Nouveau gene : " + hierarchy[5]);
            	}
	    		dS.add(nc);
			}
        }
	}
	
	static Genome getGenome(String nc)
	{
		try {
			return new Genome(nc);
		} catch(IOException ex) {
			System.out.println("Le serveur de repond pas a une requete.");
		}
		
		return null;
	}
		
	static Hashtable<String, ArrayList<String>> getNcs(String[] ids) throws IOException
	{
        Hashtable<String,  ArrayList<String>> ncs = new Hashtable<String,  ArrayList<String>>();
        
        for(String id : ids)
        {
        	ncs.put(id, new ArrayList<String>());
        }
        
        for (String id : ids)
        {    
        	URL  url = new URL("ftp://ftp.ncbi.nlm.nih.gov/genomes/GENOME_REPORTS/IDS/" + id + ".ids");
        	
            ArrayList<String> listToAdd = ncs.get(id);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            while (line != null)
            {   
                Matcher m = Pattern.compile("NC_\\d{6}").matcher(line);
                if(m.find())
                {
                	String nc = m.group();
                	listToAdd.add(nc);
                }
                line = reader.readLine();
            }
            reader.close();
        }
        return ncs;
	}
}
