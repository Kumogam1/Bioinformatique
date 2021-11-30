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
		f.log("Selectionner une ou plusieurs r\u00e9gions fonctionnelles, puis cliquer sur Start pour\nd\u00e9marrer.\n ");
		
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

		while (f.stopp1)
		{
			System.out.println("");
			if (f.startt)
			{
				Hashtable<String, ArrayList<String>> ncs = getNcs(f.ldm);
				int nbNcs = 0;

				for (String dm : f.ldm)
					nbNcs += ncs.get(dm).size();
				f.initBarre(nbNcs);
				
				for (String dm : f.ldm)
        		{					
					ArrayList<String> nbId = ncs.get(dm);
        			
					for (String nc : nbId)
					{
	        			f.doneBarre(1);
						f.logProgress(nc);
	
						for (String rf : f.lrf)
						{
							f.doneBarre(1);
							f.logProgress(nc);
							String joinName = "Exon";
		        			if(rf.equals("Intron")) { joinName = "Intron"; }
							
							String ncRegion = rf + "_" + nc;
						
							if(allreadyDone.contains(ncRegion)) {
								f.log(ncRegion + " deja pass\u00e9 ");
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
									for (List<String> gok : g.getGenomeOkList())
									{
										if (gok.get(2).equals("simple") || gok.get(2).equals("complement"))
										{
											str += rf + " "
												+ hierarchy[hierarchy.length - 1] + " "
												+ nc + " "
												+ gok.get(1) + "\n"
												+ gok.get(0).toUpperCase();
										}
										else
										{
											str += rf + " "
												+ hierarchy[hierarchy.length - 1] + " "
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
													+ hierarchy[hierarchy.length - 1] + " "
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
									
									if(g.getGenomeOkList().size() != 0)
									{
										
										String[] oldH2 = Arrays.copyOfRange(hierarchy, 0, hierarchy.length - 1);
										
										String fileName = rf + "_" + hierarchy[hierarchy.length - 1];
										
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
							if (f.stopp)
								break;
						}
						if (f.stopp)
							break;
					}
					if (f.stopp)
						break;
        		}
				f.startt = false;
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
