package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Fenetre f = new Fenetre();

		f.log("Bienvenue dans GenBanks.\n ");
		f.log("Selectionner une ou plusieurs régions fonctionnelles, puis cliquer sur Start pour\ndémarrer.\n ");

		// String[] ids = new String[] {"Viruses", "Archaea", "Bacteria",
		// "Mito_metazoa", "Phages", "Plasmids", "Viroids","Samples", "dsDNA_Viruses",
		// "Eukaryota"};
		
		ArrayList<String> done = new ArrayList<String>();
		ArrayList<String> allreadyDone = new ArrayList<String>();
		ArrayList<String> doneInSpecie = new ArrayList<String>();
		
		char[] replaceChars = new char[] {' ', '[', ']', '/', '.', ':', '-', '\''};
		
		FileWriter doneFileWriter = null;
		String donePath = "./done.txt";
		
		try {
			File doneFile = new File(donePath);
			doneFile.createNewFile();
			allreadyDone = new ArrayList<String>(Arrays.asList(new String(Files.readAllBytes(Paths.get(donePath))).split("\n")));
			doneFileWriter =  new FileWriter(donePath, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ArrayList<String> d = new ArrayList<String>();
		ArrayList<String> dS = new ArrayList<String>();

		String[] hierarchy;
		String[] oldHierarchy = null;

		//ArrayList<String> h = new ArrayList<String>(Arrays.asList("NC_000001", "NC_000010", "NC_000011", "NC_000012",
		//		"NC_000013", "NC_000014", "NC_000015", "NC_000016", "NC_000017", "NC_000018", "NC_000019", "NC_000002",
		//		"NC_000020", "NC_000021", "NC_000022", "NC_000003", "NC_000004", "NC_000005", "NC_000006", "NC_000007",
		//		"NC_000008", "NC_000009", "NC_012920", "NC_000023", "NC_000024", "NC_011137", "NC_013993"));
		
		while (f.stopp1)
		{
			System.out.println("");
			while (f.startt)
			{
				Hashtable<String, ArrayList<String>> ncs = getNcs(f.ldm);
				int nbNcs = 0;

				for (String dm : f.ldm)
					nbNcs += ncs.get(dm).size();
				f.initBarre(nbNcs);
				
				for(String rf : f.lrf)
        		{
					System.out.println(rf);
					String joinName = "Exon";
        			if(rf.equals("Intron")) { joinName = "Intron"; }
        			
					for (String dm : f.ldm)
					{
						System.out.println(dm);
						ArrayList<String> nbId = ncs.get(dm);
						// nbId = new ArrayList<>(Arrays.asList("NC_014649", "NC_012932"));
	
						/*
						 * if (id == "Eukaryota") { h.addAll(nbId); nbId = (ArrayList<String>) h; }
						 */
	
						for (String nc : nbId)
						{
	
							f.doneBarre(1);
							f.logProgress(nc);
							
							String ncRegion = f.lrf + nc;
						
							if(allreadyDone.contains(ncRegion)) {
								f.log(ncRegion + " deja passé ");
								continue;
							}
							
							Genome g = getGenome(nc, rf);
							if (g == null) {
								f.log("ERROR : " + nc);
								continue;
							}
							hierarchy = g.getHierarchy();
	
							if (!(Arrays.equals(hierarchy, oldHierarchy)))
							{				    			
								d.addAll(dS);
								dS = new ArrayList<String>();
								
								if (g.getGenomeOkList().size() != 0)
									f.addNode(hierarchy);
								
								if (oldHierarchy != null)
								{
									String str = "";
									//System.out.println("Nombre de séquence : " + g.getGenomeOkList().size());
									for (List<String> gok : g.getGenomeOkList())
									{
										//System.out.println("Exemple séquence : " + gok.get(2) + " " + gok.get(1));
										
										if (gok.get(2).equals("simple") || gok.get(2).equals("complement"))
										{
											str += rf + " "
												+ oldHierarchy[oldHierarchy.length - 1] + " "
												+ nc + " "
												+ gok.get(1) + "\n"
												+ gok.get(0).toUpperCase();
										}
										else
										{
											//System.out.println(nc + " " + oldHierarchy[oldHierarchy.length - 1] + " " + gok.get(1) + " " + num);
											
											
											str += rf + " "
												+ oldHierarchy[oldHierarchy.length - 1] + " "
												+ nc + " "
												+ gok.get(1) + "\n"
												+ gok.get(0).replace(";", "").toUpperCase() + "\n";
											
											String type = gok.get(1);
											
											if (gok.get(2).equals("joincomplement"))
												type = gok.get(1).substring(11, gok.get(1).length()-1);
											
											String[] seqs = gok.get(0).split(";");
											for (int num_seq = 0; num_seq < seqs.length; num_seq++)
											{
												str += rf + " "
													+ oldHierarchy[oldHierarchy.length - 1] + " "
													+ nc + " "
													+ type + " "
													+ joinName + " "
													+ (num_seq + 1) + "\n"
													+ seqs[num_seq].toUpperCase();
												
												if (num_seq != seqs.length - 1)
													str += "\n";
											}
										}
										str += "\n";
									}
									
									String[] oldH2 = Arrays.copyOfRange(oldHierarchy, 0, oldHierarchy.length - 1);
									
									String fileName = rf + "_" + oldHierarchy[oldHierarchy.length - 1];
									
									for(int i = 0; i < oldH2.length; i++)
										for(char c : replaceChars)
											oldH2[i] = oldH2[i].replace(c, '_');
									
									for(char c : replaceChars)
										fileName = fileName.replace(c, '_');

									String fileSerialize = "./Results/"
																+ String.join("/", oldH2) + "/"
																+ fileName + "_" 
																+ nc + ".txt";

									File tmp = new File(fileSerialize);
									tmp.getParentFile().setWritable(true);
									tmp.getParentFile().mkdirs();

									BufferedWriter bufres = new BufferedWriter(new FileWriter(fileSerialize));
									bufres.write(str);
									bufres.close();
									
								}
								
								oldHierarchy = hierarchy;
								if (g.getGenomeOkList().size() != 0)
									f.log("Nouveau gene : " + hierarchy[5]);
							}
							
							doneInSpecie.add(ncRegion);
							
							for(String i : doneInSpecie)
							{
			    				doneFileWriter.write(i+"\n");
			    				doneFileWriter.flush();
			    			}
			    			done.addAll(doneInSpecie);
			    			doneInSpecie = new ArrayList<String>();
			    			
							dS.add(nc);
	
							// on termine le gene puis stop
							if (f.stopp) {
								while (f.stopp) {
									// System.out.println("STOP ");
								}
							}
						}
					}
        		}
	
				// A VERIFIER
				f.startt = false;
				f.stopp1 = false;
				f.log("Fin \n");

			}

		}
		

		
		try {
			doneFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static Genome getGenome(String nc, String rf) {
		try {
			return new Genome(nc, rf);
		} catch (IOException ex) {
			System.out.println("Le serveur de repond pas a une requete.");
		}

		return null;
	}

	static Hashtable<String, ArrayList<String>> getNcs(ArrayList<String> ids) throws IOException {
		Hashtable<String, ArrayList<String>> ncs = new Hashtable<String, ArrayList<String>>();

		for (String id : ids) {
			URL url = new URL("ftp://ftp.ncbi.nlm.nih.gov/genomes/GENOME_REPORTS/IDS/" + id + ".ids");
			System.out.println(id);
			// ArrayList<String> listToAdd = ncs.get(id);
			ArrayList<String> listToAdd = new ArrayList<String>();

			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			while (line != null) {
				Matcher m = Pattern.compile("NC_\\d{6}").matcher(line);
				if (m.find()) {
					String nc = m.group();
					listToAdd.add(nc);
				}
				line = reader.readLine();
			}
			reader.close();

			ncs.put(id, listToAdd);
		}

		return ncs;
	}
}
