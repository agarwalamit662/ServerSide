package com.prgguru.jersey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {
        public void execute(JobExecutionContext context)
                        throws JobExecutionException {
        		
        		System.out.println(Calendar.getInstance().getTime().toString()+" Inserts and Updates Entering");
        		
        		insertHindiLyricsNet();
        		updateHindiLyricsNet();
        		
        		insertandUpdateThisYearMovies();
        		//insertPreviousYearMovies();
        		
        		insertPunjabiPopSongs();
        		insertIndiPopSongs();
        		
        		/*try {
        			DBConnection.getInsertSongData();
        			//insertDataIntoFiles();
					//insertPunjabiDataIntoFiles();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
        		//insertandUpdateNewYearMoviesSongs();
        		
        			
                System.out.println(Calendar.getInstance().getTime().toString()+"Inserts and Updates Complete");
        }
        public void insertDataIntoFiles() throws Exception
        {
        	final String[] str1={"Latest Movies Songs","A MOVIE SONGS","B MOVIE SONGS","C MOVIE SONGS","D MOVIE SONGS","E MOVIE SONGS","F MOVIE SONGS","G MOVIE SONGS","H MOVIE SONGS","I MOVIE SONGS","J MOVIE SONGS"
                    ,"K MOVIE SONGS","L MOVIE SONGS","M MOVIE SONGS","N MOVIE SONGS","O MOVIE SONGS","P MOVIE SONGS","Q MOVIE SONGS","R MOVIE SONGS","S MOVIE SONGS","T MOVIE SONGS","U MOVIE SONGS","V MOVIE SONGS","W MOVIE SONGS","X MOVIE SONGS","Y MOVIE SONGS","Z MOVIE SONGS"};

            String fName = "C:/Users/amitagarwal3/eclipseprojects/useraccount/src/com/prgguru/jersey/songs";
        	final String[] str2={"2011","2001","1991","1981","1971","1961","1950"};
        	
        	for(int i = 1 ; i < str1.length ; i++){
        	
        		for(int j = 0 ; j < str2.length ; j++){
        		
        			
        			
        				File f = new File(fName+String.valueOf(i)+String.valueOf(j)+".txt");
        				String data = Utility.constructSCharWiseSongJSON(""+str1[i].charAt(0),str2[j]);
        				DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
        				out.writeBytes(data);
        				out.close();
        				
					
        		
        		}
        	
        	}
        	
        }
        
        public void insertPunjabiDataIntoFiles() throws Exception
        {
        	final String[] str1={"Latest Movies Songs","A MOVIE SONGS","B MOVIE SONGS","C MOVIE SONGS","D MOVIE SONGS","E MOVIE SONGS","F MOVIE SONGS","G MOVIE SONGS","H MOVIE SONGS","I MOVIE SONGS","J MOVIE SONGS"
                    ,"K MOVIE SONGS","L MOVIE SONGS","M MOVIE SONGS","N MOVIE SONGS","O MOVIE SONGS","P MOVIE SONGS","Q MOVIE SONGS","R MOVIE SONGS","S MOVIE SONGS","T MOVIE SONGS","U MOVIE SONGS","V MOVIE SONGS","W MOVIE SONGS","X MOVIE SONGS","Y MOVIE SONGS","Z MOVIE SONGS"};

            String fName = "C:/Users/amitagarwal3/eclipseprojects/useraccount/src/com/prgguru/jersey/punjabi";
        	final String[] str2={"2011","2001","1991","1981","1971","1961","1950"};
        	
        	for(int i = 1 ; i < str1.length ; i++){
        	
        			
        			
        				File f = new File(fName+String.valueOf(i)+".txt");
        				String data = Utility.constructSCharWisePunjabiSongJSON(""+str1[i].charAt(0),null);
        				DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
        				out.writeBytes(data);
        				out.close();
        				
				
        	
        	}
        	
        }
        
        public void insertPreviousYearMovies()
        {
        	System.out.println(Calendar.getInstance().getTime().toString()+"This method is used to insert previous year movies only");
        	
        	Calendar cal = Calendar.getInstance();
        	int curryear = Calendar.getInstance().get(Calendar.YEAR)-1;
        	int thisyear = curryear+1;
        	String year = String.valueOf(thisyear);
        	
        	try {
				if(DBConnection.getSaveLastYearSuccessful(curryear))
				{
					System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything Last Year songs already in DB");
				}
				else
				{
					Document doc = null;
		            Document docs = null;
		            try {
		            	String url = "http://www.songsmp3.info/1/bollywood-music.html";
		            	int count = 0;
		            	Response response= Jsoup.connect(url)
		            	           .ignoreContentType(true)
		            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		            	           .referrer("http://www.google.com")   
		            	           .timeout(12000) 
		            	           .followRedirects(true)
		            	           .execute();

		            	doc = response.parse();
		            	
		                String title = doc.title();
		                String href = doc.text();
		                // Getting the list of Elements like A, B , C , D, E ... Z
		                Elements e = doc.getElementsByAttributeValueContaining("href", "/1/bollywood-music/list-");

		                for (Element src : e) {
		                	
		                	
		                	//if(count == 4)
		                 		//break;
		                		
		                	String charlink = src.attr("href");
		                	
		                	String moviechar = src.text();
		                	//System.out.println(moviechar);
		                	// Getting the lists of movies for A or B or .. Z
		                	String url1 = "http://www.songsmp3.info"+charlink;
		                	if(url1.contains("list-a.html"))
		                		continue;
		                	response= Jsoup.connect(url1)
		             	           .ignoreContentType(true)
		             	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		             	           .referrer("http://www.google.com")   
		             	           .timeout(12000) 
		             	           .followRedirects(true)
		             	           .execute();
		                	
		                	
		                	docs = response.parse();
		                	
		                    Elements e1 = docs.getElementsByAttributeValue("class", "list_main_box");
		                    
		                    for(Element srcs : e1)
		                    {
		                    	
		                    	//if(count == 4)
		                     	//	break;
		                    	
		                    	// getting the movies list for a particular character
		                    	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "/1/bollywood-music/");
		                    		
		                    		for(Element srcss : e2)
		                    		{
		                    			
		                    			// Movie related Data
		                    			
		                    			//if(count == 4)
		                             	//	break;
		                    			String mlink = srcss.attr("href");
		                    			System.out.println(Calendar.getInstance().getTime().toString()+"MLINK is: "+mlink);
		                    			Pattern pattern = Pattern.compile("/(.*?)/");
		                    		    Matcher matcher = pattern.matcher(mlink);
		                    		    int MOVIENUMBER = 0;
		                    		    int countMatcher = 0;
		                    		    while (matcher.find()) {
		                    		    	if(countMatcher == 1)
		                    		    	{
		                    		    		MOVIENUMBER = Integer.parseInt(matcher.group(1));
		                    		    	}
		                    		    	countMatcher = countMatcher+1;
		                    		        
		                    		    }
		                    		    //System.out.println("MovieNumber is: "+MOVIENUMBER);
		                    		    if(mlink.contains("-"+year))
											continue;
		                    			Movie movieObject = new Movie();
		                    			
		                    			String mname = srcss.text();
		                    			//System.out.println(mname);
		                    			Pattern patternYear = Pattern.compile("(\\(.*?)\\)");
		                    		    Matcher matcherYear = patternYear.matcher(mname);
		                    		    String relyear="";
		                    		    
		                    		    while (matcherYear.find()) {
		                    		    		String sb =  matcherYear.group();
		                    		    		sb = sb.replaceAll("\\(","");
				                    			sb = sb.replaceAll("\\)", "");
				                    			
		                    		    		if(sb.matches("^-?\\d+$"))
		                    		    			relyear = sb;
		                    		    	
		                    		    }
		                    		    if(relyear.length() ==0)
		                    		    	continue;
		                    			movieObject.setMOVIENAME(mname);
		                    			movieObject.setMOVIENUMBER(MOVIENUMBER);
		                    			movieObject.setMOVIESTARTCHAR(moviechar);
		                    			movieObject.setRELEASE_DATE(relyear);
		                    			//System.out.println("Relese Year is: "+relyear);
		                    			
		                    			Document doc3 = null;
		                    			// Going to the movie page which contains songs
		                    			String url2 = "http://www.songsmp3.info"+mlink;
		                            	response= Jsoup.connect(url2)
		                         	           .ignoreContentType(true)
		                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		                         	           .referrer("http://www.google.com")   
		                         	           .timeout(12000) 
		                         	           .followRedirects(true)
		                         	           .execute();
		                            	
		                            	docs = response.parse();
		                    			
		                            	Elements movie_url = docs.getElementsByAttributeValueContaining("alt", mname);
		                            	String urlimage = "NA";
		                            	try{
		                            	if(movie_url != null && movie_url.size() > 0)
		                            		urlimage= "http://www.songsmp3.info"+movie_url.get(0).attr("src");
		                            	}
		                            	catch(Exception es)
		                            	{
		                            		System.out.println(Calendar.getInstance().getTime().toString()+mname);
		                            	}
		                            	Elements movie_details = docs.getElementsByAttributeValueContaining("class", "movie_details");
		                            	String actors = "Not Available";
		                            	String director = "Not Available";
		                            	String music_director = "Not Available";
		                            	String singer = "Not Available";
		                            	for(Element mo_details : movie_details)
		                            	{
		                            		Elements stars = mo_details.getElementsByAttributeValueContaining("href", "/stars/");
		                            		
		                            		for(Element star_details : stars)
		                            		{
		                            			actors = actors+" , "+star_details.text();
		                            		}
		                            		
		                            		Elements directors = mo_details.getElementsByAttributeValueContaining("href", "/directors/");
		                            		
		                            		for(Element dir : directors)
		                            		{
		                            			director = director+" , "+dir.text();
		                            		}
		                            		
		                            		Elements music_directors = mo_details.getElementsByAttributeValueContaining("href", "/music-directors/");
		                            		
		                            		for(Element music_dir : music_directors)
		                            		{
		                            			music_director = music_director+" , "+music_dir.text();
		                            		}
		                            		
		                            		Elements singers = mo_details.getElementsByAttributeValueContaining("href", "/singers/");
		                            		
		                            		for(Element sing : singers)
		                            		{
		                            			singer = singer+" , "+sing.text();
		                            		}
		                            		
		                            	}
		                            	
		                            	movieObject.setACTORS(actors);
		                            	movieObject.setSINGERS(singer);
		                            	movieObject.setDIRECTOR(director);
		                            	movieObject.setMUSIC_DIRECTOR(music_director);
		                            	movieObject.setURLS(urlimage);
		                            	
		                            	
		                            	//System.out.println(movieObject.getMOVIENUMBER()+" : "+movieObject.getMOVIENAME()+ " : "+movieObject.getMOVIESTARTCHAR()+" : "
		                            	//		+movieObject.getRELEASE_DATE()+" : "+movieObject.getMUSIC_DIRECTOR()+" : "+movieObject.getACTORS()+" : "+movieObject.getSINGERS()
		                            	//		+" : "+movieObject.getDIRECTOR()+" : "+movieObject.getURLS());
		                            	//insertandUpdateMovieObject(int movieNumber, String MOVIESTARTCHAR, String MOVIENAME, String releaseyear , String MUSIC_DIRECTOR, String ACTORS, String SINGERS, String DIRECTOR, String URLS) throws SQLException{
		                            	// Going to fetch songs lists for Movie
		                            	DBConnection.insertandUpdateMovieObject(movieObject.getMOVIENUMBER(), movieObject.getMOVIESTARTCHAR(), movieObject.getMOVIENAME(), movieObject.getRELEASE_DATE(), movieObject.getMUSIC_DIRECTOR(), movieObject.getACTORS(), movieObject.getSINGERS(), movieObject.getDIRECTOR(), movieObject.getURLS());
		                            	Elements s = docs.getElementsByAttributeValue("class", "download-single-links_box");
		                            	
		                            	for(Element songs : s)
		                            	{
		                            		
		                            		
		                            		//if(count == 4)
		                                 	//	break;
		                            		
		                            		Elements artists = songs.getElementsByAttributeValueContaining("class", "link-item");
		                            		
		                            		for(Element sings : artists)
	                            			{
		                            			String songSingers = "Not Available";
		                            			Elements singers = sings.getElementsByAttributeValueContaining("href", "/singers/");
		                            			for(Element singerSong :singers){
		                            			
		                            				songSingers = songSingers+" , "+singerSong.text();
		                            			}
	                            			
		                            			Elements song = sings.getElementsByAttributeValueContaining("href", "/download/");
			                            		// getting links of songs
			                            		for(Element links : song)
			                            		{
			                            			
			                            			
			                            			
			                            			//if(count == 4)
			                                     	//	break;
			                            			String songlink = links.attr("href");
			                            			String songname = links.text();
			                            			//System.out.println(songname);
			                            			
			                            			String url4 = "http://www.songsmp3.info"+songlink;
			                            			
			                            			response= Jsoup.connect(url4)
			                                  	           .ignoreContentType(true)
			                                  	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			                                  	           .referrer("http://www.google.com")   
			                                  	           .timeout(12000) 
			                                  	           .followRedirects(true)
			                                  	           .execute();
			                                     	
			                                     	
			                                     	docs = response.parse();
			                                     	// going inside the songs page
			                                     	Elements linksDwnld = docs.getElementsByAttributeValueContaining("href", "http://101songs.com/fileDownload/Songs/128");
			                                     	
			                                     	for(Element looper : linksDwnld)
			                                     	{
			                                     		// Getting songs Details
			                                     		//if(count == 4)
			                                         	//	break;
			                                     		String link128kb = looper.attr("href");
			                                     		
			                                     		//System.out.println(link128kb);
			                                     		
			                                     		Songs songObject = new Songs();
			                                     		songObject.setMovie(movieObject);
			                                     		songObject.setSONGNAME(songname);
			                                     		songObject.setSINGERS(songSingers);
			                                     		songObject.setSONGLINK_128KBPS(link128kb);
			                                     		
			                                     		
			                                     		String finalSongLink = getFinalRedirectedUrl(link128kb);
			                                     		//System.out.println("Final Song link is : " +finalSongLink);
			                                     		songObject.setSONGLINK_128KBPS_CONV(finalSongLink);
			                                     		URL linkToHit = convertToURLEscapingIllegalCharacters(finalSongLink);
			                                     		HttpURLConnection con = (HttpURLConnection) linkToHit.openConnection();
			                                     		con.connect();
			                                     		InputStreamReader isr = new InputStreamReader(con.getInputStream());
			                                     		
			                                     		BufferedReader reader = new BufferedReader(isr);
			                                     		
			                                     		byte[] buffer = new byte[4096];
			                                     		int n = - 1;
			                                     		boolean containsData = false;
			                                     		
			                                     		while (reader.readLine() != null) 
			                                     		{
			                                     			//System.out.println("Contains: "+reader.readLine());
			                                     			containsData = true;
			                                     			break;
			                                     		}
			                                     		con.disconnect();
			                                     		reader.close();
			                                     		isr.close();
			                                     		if(containsData)
			                                     			songObject.setWORKING_LINK("1");
			                                     		else
			                                     			songObject.setWORKING_LINK("0");
			                                     		
			                                     		String checks = "http://101songs.com/fileDownload/Songs/128/";
			                                     		int len = checks.length();
			                                     		String songNo = link128kb.substring(len, link128kb.length());
			                                     		songNo = songNo.replace(".mp3", "");
			                                     		songObject.setSONG_ID(Integer.parseInt(songNo));
			                                     		
			                                     		/*System.out.println(songObject.getSONG_ID()+ " : "+songObject.getSONGNAME()
			                                     		+" : "+songObject.getSINGERS()+" : "+songObject.getSONGLINK_128KBPS()+
			                                     		" : "+songObject.getMovie().getMOVIENUMBER());*/
			                                     		
			                                     		DBConnection.insertandUpdateMovieSongObject(songObject.getMovie().getMOVIENUMBER(), songObject.getSONGNAME(), songObject.getSINGERS(), songObject.getSONG_ID() ,songObject.getSONGLINK_128KBPS(),songObject.getSONGLINK_128KBPS_CONV(),songObject.getWORKING_LINK()) ;
			                                     		
			                                     	}
			                                     	
			                            			
			                            		}
		                            			
		                            			
	                            			}
		                            		
		                            		
		                            		
		                            	}
		                            	count = count+1;
		                            	
		                    		}
		                    	
		                    }
		                	
		                   
		                }
		                
		                if(DBConnection.setSaveLastYearSuccessful(curryear))
		                {
		                	System.out.println(Calendar.getInstance().getTime().toString()+"update successful last year Movies done");
		                }

		            } catch (IOException e) {
		            	
		            	if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println(Calendar.getInstance().getTime().toString()+"update last year Movies because catch occured");
		                }
		            	
		                e.printStackTrace();
		            }
		            catch(Exception e)
		            {
		            	if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println(Calendar.getInstance().getTime().toString()+"update last year Movies because catch occured");
		                }
		            	e.printStackTrace();
		            }
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				
				e3.printStackTrace();
			}
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting 1st method previous year movies data complete");
        }
        
        
        
        
        public void insertandUpdateThisYearMovies()
        {
        	System.out.println(Calendar.getInstance().getTime().toString()+"This method is used to insert and update this year movies songs only");
        	
        	Calendar cal = Calendar.getInstance();
        	cal.add(Calendar.DATE, 1);
        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        	//System.out.println(cal.getTime());
        	// Output "Wed Sep 26 14:23:28 EST 2012"

        	String todayDate = formatDate.format(cal.getTime());
        	//System.out.println(formatted);
        	
        	int curryear = Calendar.getInstance().get(Calendar.YEAR);
        	String year = String.valueOf(curryear);
        	
        	try {
				if(DBConnection.getSaveThisYearSuccessful(todayDate))
				{
					System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything This Year songs already in DB");
				}
				else
				{
					try {
						
						Document doc = null;
						Document docs = null;
						try {
							String url = "http://www.songsmp3.info/1/bollywood-music.html";
							int count = 0;
							Response response= Jsoup.connect(url)
	            	           .ignoreContentType(true)
	            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
	            	           .referrer("http://www.google.com")   
	            	           .timeout(12000) 
	            	           .followRedirects(true)
	            	           .execute();

			            	doc = response.parse();
			            		
			                String title = doc.title();
			                String href = doc.text();
			                //System.out.println(title+" Title ");
			                // Getting the list of Elements like A, B , C , D, E ... Z
			                Elements e = doc.getElementsByAttributeValueContaining("href", "/1/bollywood-music/list-");
		
			                for (Element src : e) {
			                	
			                	
			                	//if(count == 4)
			                 		//break;
			                		
			                	String charlink = src.attr("href");
			                	
			                	String moviechar = src.text();
			                	//System.out.println(moviechar);
			                	// Getting the lists of movies for A or B or .. Z
			                	String url1 = "http://www.songsmp3.info"+charlink;
			                	response= Jsoup.connect(url1)
			             	           .ignoreContentType(true)
			             	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			             	           .referrer("http://www.google.com")   
			             	           .timeout(12000) 
			             	           .followRedirects(true)
			             	           .execute();
			                	
			                	
			                	docs = response.parse();
			                	
			                    Elements e1 = docs.getElementsByAttributeValue("class", "list_main_box");
			                    
			                    for(Element srcs : e1)
			                    {
			                    	
			                    	//if(count == 4)
			                     		//break;
			                    	
			                    	// getting the movies list for a particular character
			                    	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "-"+year);
			                    		
			                    		for(Element srcss : e2)
			                    		{
			                    			
			                    			// Movie related Data
			                    			
			                    			//if(count == 4)
			                             		//break;
			                    			String mlink = srcss.attr("href");
			                    			//System.out.println("MLINK is: "+mlink);
			                    			Pattern pattern = Pattern.compile("/(.*?)/");
			                    		    Matcher matcher = pattern.matcher(mlink);
			                    		    int MOVIENUMBER = 0;
			                    		    int countMatcher = 0;
			                    		    while (matcher.find()) {
			                    		    	if(countMatcher == 1)
			                    		    	{
			                    		    		MOVIENUMBER = Integer.parseInt(matcher.group(1));
			                    		    	}
			                    		    	countMatcher = countMatcher+1;
			                    		        
			                    		    }
			                    		    //System.out.println("MovieNumber is: "+MOVIENUMBER);
			                    		    
			                    			Movie movieObject = new Movie();
			                    			
			                    			String mname = srcss.text();
			                    			//System.out.println(mname);
			                    			Pattern patternYear = Pattern.compile("(\\(.*?)\\)");
			                    		    Matcher matcherYear = patternYear.matcher(mname);
			                    		    String relyear="";
			                    		    
			                    		    while (matcherYear.find()) {
			                    		    		String sb =  matcherYear.group();
			                    		    		sb = sb.replaceAll("\\(","");
					                    			sb = sb.replaceAll("\\)", "");
					                    			
			                    		    		if(sb.matches("^-?\\d+$"))
			                    		    			relyear = sb;
			                    		    	
			                    		    }
			                    		    if(relyear.length() ==0)
			                    		    	continue;
			                    			movieObject.setMOVIENAME(mname);
			                    			movieObject.setMOVIENUMBER(MOVIENUMBER);
			                    			movieObject.setMOVIESTARTCHAR(moviechar);
			                    			movieObject.setRELEASE_DATE(relyear);
			                    			//System.out.println("Relese Year is: "+relyear);
			                    			
			                    			Document doc3 = null;
			                    			// Going to the movie page which contains songs
			                    			String url2 = "http://www.songsmp3.info"+mlink;
			                            	response= Jsoup.connect(url2)
			                         	           .ignoreContentType(true)
			                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			                         	           .referrer("http://www.google.com")   
			                         	           .timeout(12000) 
			                         	           .followRedirects(true)
			                         	           .execute();
			                            	
			                            	docs = response.parse();
			                    			
			                            	Elements movie_url = docs.getElementsByAttributeValueContaining("alt", mname);
			                            	String urlimage = "NA";
			                            	try{
				                            	if(movie_url != null && movie_url.size() > 0)
				                            		urlimage= "http://www.songsmp3.info"+movie_url.get(0).attr("src");
				                            	}
				                            	catch(Exception es)
				                            	{
				                            		System.out.println(Calendar.getInstance().getTime().toString()+mname);
				                            	}
			                            	Elements movie_details = docs.getElementsByAttributeValueContaining("class", "movie_details");
			                            	String actors = "Not Available";
			                            	String director = "Not Available";
			                            	String music_director = "Not Available";
			                            	String singer = "Not Available";
			                            	for(Element mo_details : movie_details)
			                            	{
			                            		Elements stars = mo_details.getElementsByAttributeValueContaining("href", "/stars/");
			                            		
			                            		for(Element star_details : stars)
			                            		{
			                            			actors = actors+" , "+star_details.text();
			                            		}
			                            		
			                            		Elements directors = mo_details.getElementsByAttributeValueContaining("href", "/directors/");
			                            		
			                            		for(Element dir : directors)
			                            		{
			                            			director = director+" , "+dir.text();
			                            		}
			                            		
			                            		Elements music_directors = mo_details.getElementsByAttributeValueContaining("href", "/music-directors/");
			                            		
			                            		for(Element music_dir : music_directors)
			                            		{
			                            			music_director = music_director+" , "+music_dir.text();
			                            		}
			                            		
			                            		Elements singers = mo_details.getElementsByAttributeValueContaining("href", "/singers/");
			                            		
			                            		for(Element sing : singers)
			                            		{
			                            			singer = singer+" , "+sing.text();
			                            		}
			                            		
			                            	}
			                            	
			                            	movieObject.setACTORS(actors);
			                            	movieObject.setSINGERS(singer);
			                            	movieObject.setDIRECTOR(director);
			                            	movieObject.setMUSIC_DIRECTOR(music_director);
			                            	movieObject.setURLS(urlimage);
			                            	
			                            	
			                            	/*System.out.println(movieObject.getMOVIENUMBER()+" : "+movieObject.getMOVIENAME()+ " : "+movieObject.getMOVIESTARTCHAR()+" : "
			                            			+movieObject.getRELEASE_DATE()+" : "+movieObject.getMUSIC_DIRECTOR()+" : "+movieObject.getACTORS()+" : "+movieObject.getSINGERS()
			                            			+" : "+movieObject.getDIRECTOR()+" : "+movieObject.getURLS());
			                            	*/
			                            	DBConnection.insertandUpdateMovieObject(movieObject.getMOVIENUMBER(), movieObject.getMOVIESTARTCHAR(), movieObject.getMOVIENAME(), movieObject.getRELEASE_DATE(), movieObject.getMUSIC_DIRECTOR(), movieObject.getACTORS(), movieObject.getSINGERS(), movieObject.getDIRECTOR(), movieObject.getURLS());
			                            	// Going to fetch songs lists for Movie
			                            	
			                            	Elements s = docs.getElementsByAttributeValue("class", "download-single-links_box");
			                            	
			                            	for(Element songs : s)
			                            	{
			                            		
			                            		
			                            		//if(count == 4)
			                                 		//break;
			                            		
			                            		Elements artists = songs.getElementsByAttributeValueContaining("class", "link-item");
			                            		
			                            		for(Element sings : artists)
		                            			{
			                            			String songSingers = "Not Available";
			                            			Elements singers = sings.getElementsByAttributeValueContaining("href", "/singers/");
			                            			for(Element singerSong :singers){
			                            			
			                            				songSingers = songSingers+" , "+singerSong.text();
			                            			}
		                            			
			                            			Elements song = sings.getElementsByAttributeValueContaining("href", "/download/");
				                            		// getting links of songs
				                            		for(Element links : song)
				                            		{
				                            			
				                            			
				                            			
				                            			//if(count == 4)
				                                     		//break;
				                            			String songlink = links.attr("href");
				                            			String songname = links.text();
				                            			//System.out.println(songname);
				                            			
				                            			String url4 = "http://www.songsmp3.info"+songlink;
				                            			
				                            			response= Jsoup.connect(url4)
				                                  	           .ignoreContentType(true)
				                                  	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
				                                  	           .referrer("http://www.google.com")   
				                                  	           .timeout(12000) 
				                                  	           .followRedirects(true)
				                                  	           .execute();
				                                     	
				                                     	
				                                     	docs = response.parse();
				                                     	// going inside the songs page
				                                     	Elements linksDwnld = docs.getElementsByAttributeValueContaining("href", "http://101songs.com/fileDownload/Songs/128");
				                                     	
				                                     	for(Element looper : linksDwnld)
				                                     	{
				                                     		// Getting songs Details
				                                     		//if(count == 4)
				                                         		//break;
				                                     		String link128kb = looper.attr("href");
				                                     		
				                                     		//System.out.println(link128kb);
				                                     		
				                                     		Songs songObject = new Songs();
				                                     		songObject.setMovie(movieObject);
				                                     		songObject.setSONGNAME(songname);
				                                     		songObject.setSINGERS(songSingers);
				                                     		songObject.setSONGLINK_128KBPS(link128kb);
				                                     		
				                                     		String finalSongLink = getFinalRedirectedUrl(link128kb);
				                                     		//System.out.println("Final Song link is : " +finalSongLink);
				                                     		songObject.setSONGLINK_128KBPS_CONV(finalSongLink);
				                                     		URL linkToHit = convertToURLEscapingIllegalCharacters(finalSongLink);
				                                     		HttpURLConnection con = (HttpURLConnection) linkToHit.openConnection();
				                                     		con.connect();
				                                     		InputStreamReader isr = new InputStreamReader(con.getInputStream());
				                                     		
				                                     		BufferedReader reader = new BufferedReader(isr);
				                                     		
				                                     		byte[] buffer = new byte[4096];
				                                     		int n = - 1;
				                                     		boolean containsData = false;
				                                     		
				                                     		while (reader.readLine() != null) 
				                                     		{
				                                     			//System.out.println("Contains: "+reader.readLine());
				                                     			containsData = true;
				                                     			break;
				                                     		}
				                                     		con.disconnect();
				                                     		reader.close();
				                                     		isr.close();
				                                     		if(containsData)
				                                     			songObject.setWORKING_LINK("1");
				                                     		else
				                                     			songObject.setWORKING_LINK("0");
				                                     		
				                                     		String checks = "http://101songs.com/fileDownload/Songs/128/";
				                                     		int len = checks.length();
				                                     		String songNo = link128kb.substring(len, link128kb.length());
				                                     		songNo = songNo.replace(".mp3", "");
				                                     		songObject.setSONG_ID(Integer.parseInt(songNo));
				                                     		
				                                     		/*System.out.println(songObject.getSONG_ID()+ " : "+songObject.getSONGNAME()
				                                     		+" : "+songObject.getSINGERS()+" : "+songObject.getSONGLINK_128KBPS()+
				                                     		" : "+songObject.getMovie().getMOVIENUMBER());*/
				                                     		
				                                     		DBConnection.insertandUpdateMovieSongObject(songObject.getMovie().getMOVIENUMBER(), songObject.getSONGNAME(), songObject.getSINGERS(), songObject.getSONG_ID() ,songObject.getSONGLINK_128KBPS(),songObject.getSONGLINK_128KBPS_CONV(),songObject.getWORKING_LINK()) ;
				                                     		
				                                     	}
				                                     	
				                            			
				                            		}
			                            			
			                            			
		                            			}
			                            		
			                            		
			                            		
			                            	}
			                            	count = count+1;
			                            	
			                    		}
			                    	
			                    }
			                	
			                   
			                }
			                if(DBConnection.setSaveThisYearSuccessful(todayDate))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update successful this year Movies done");
			                }
			                
			            } catch (IOException e) {
			            	
			            	if(DBConnection.setSaveThisYearSuccessful("1000-02-02"))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update failed as catch occured this year Movies done");
			                }
			                e.printStackTrace();
			            }
			            catch(Exception e)
			            {
			            	if(DBConnection.setSaveThisYearSuccessful("1000-02-02"))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update failed as catch occured this year Movies done");
			                }
			            	e.printStackTrace();
			            }
		        		
		        	} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
        	
				}
				}
        		catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting 2nd method this year movie songs");
        	
        }
        
        public void insertandUpdateNewYearMoviesSongs()
        {
        	int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        	int curryear = Calendar.getInstance().get(Calendar.YEAR)+1;
        	String year = String.valueOf(curryear);
        	
        	Calendar cal = Calendar.getInstance();
        	cal.add(Calendar.DATE, 1);
        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        	//System.out.println(cal.getTime());
        	// Output "Wed Sep 26 14:23:28 EST 2012"

        	String todayDate = formatDate.format(cal.getTime());
        	
        	
        	try {
				if(DBConnection.getSaveNewYearSuccessful(todayDate))
				{
					System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything New Year songs already in DB");
				}
				else
				{
					try {
        		
						Document doc = null;
						Document docs = null;
						try {
							String url = "http://www.songsmp3.info/1/bollywood-music.html";
							int count = 0;
							Response response= Jsoup.connect(url)
	            	           .ignoreContentType(true)
	            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
	            	           .referrer("http://www.google.com")   
	            	           .timeout(12000) 
	            	           .followRedirects(true)
	            	           .execute();

			            	doc = response.parse();
			            	
			                String title = doc.title();
			                String href = doc.text();
			                // Getting the list of Elements like A, B , C , D, E ... Z
			                Elements e = doc.getElementsByAttributeValueContaining("href", "/1/bollywood-music/list-");
		
			                for (Element src : e) {
			                	
			                	
			                	//if(count == 4)
			                 		//break;
			                		
			                	String charlink = src.attr("href");
			                	
			                	String moviechar = src.text();
			                	//System.out.println(moviechar);
			                	// Getting the lists of movies for A or B or .. Z
			                	String url1 = "http://www.songsmp3.info"+charlink;
			                	response= Jsoup.connect(url1)
			             	           .ignoreContentType(true)
			             	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			             	           .referrer("http://www.google.com")   
			             	           .timeout(12000) 
			             	           .followRedirects(true)
			             	           .execute();
			                	
			                	
			                	docs = response.parse();
			                	
			                    Elements e1 = docs.getElementsByAttributeValue("class", "list_main_box");
			                    
			                    for(Element srcs : e1)
			                    {
			                    	
			                    	//if(count == 4)
			                     		//break;
			                    	
			                    	// getting the movies list for a particular character
			                    	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "-"+year);
			                    		
			                    		for(Element srcss : e2)
			                    		{
			                    			
			                    			// Movie related Data
			                    			
			                    			//if(count == 4)
			                             		//break;
			                    			String mlink = srcss.attr("href");
			                    			//System.out.println("MLINK is: "+mlink);
			                    			Pattern pattern = Pattern.compile("/(.*?)/");
			                    		    Matcher matcher = pattern.matcher(mlink);
			                    		    int MOVIENUMBER = 0;
			                    		    int countMatcher = 0;
			                    		    while (matcher.find()) {
			                    		    	if(countMatcher == 1)
			                    		    	{
			                    		    		MOVIENUMBER = Integer.parseInt(matcher.group(1));
			                    		    	}
			                    		    	countMatcher = countMatcher+1;
			                    		        
			                    		    }
			                    		    //System.out.println("MovieNumber is: "+MOVIENUMBER);
			                    		    
			                    			Movie movieObject = new Movie();
			                    			
			                    			String mname = srcss.text();
			                    			//System.out.println(mname);
			                    			Pattern patternYear = Pattern.compile("(\\(.*?)\\)");
			                    		    Matcher matcherYear = patternYear.matcher(mname);
			                    		    String relyear="";
			                    		    
			                    		    while (matcherYear.find()) {
			                    		    		String sb =  matcherYear.group();
			                    		    		sb = sb.replaceAll("\\(","");
					                    			sb = sb.replaceAll("\\)", "");
					                    			
			                    		    		if(sb.matches("^-?\\d+$"))
			                    		    			relyear = sb;
			                    		    	
			                    		    }
			                    		    if(relyear.length() ==0)
			                    		    	continue;
			                    			movieObject.setMOVIENAME(mname);
			                    			movieObject.setMOVIENUMBER(MOVIENUMBER);
			                    			movieObject.setMOVIESTARTCHAR(moviechar);
			                    			movieObject.setRELEASE_DATE(relyear);
			                    			//System.out.println("Relese Year is: "+relyear);
			                    			
			                    			Document doc3 = null;
			                    			// Going to the movie page which contains songs
			                    			String url2 = "http://www.songsmp3.info"+mlink;
			                            	response= Jsoup.connect(url2)
			                         	           .ignoreContentType(true)
			                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			                         	           .referrer("http://www.google.com")   
			                         	           .timeout(12000) 
			                         	           .followRedirects(true)
			                         	           .execute();
			                            	
			                            	docs = response.parse();
			                    			
			                            	Elements movie_url = docs.getElementsByAttributeValueContaining("alt", mname);
			                            	String urlimage = "NA";
			                            	try{
				                            	if(movie_url != null && movie_url.size() > 0)
				                            		urlimage= "http://www.songsmp3.info"+movie_url.get(0).attr("src");
				                            	}
				                            	catch(Exception es)
				                            	{
				                            		System.out.println(Calendar.getInstance().getTime().toString()+mname);
				                            	}
			                            	Elements movie_details = docs.getElementsByAttributeValueContaining("class", "movie_details");
			                            	String actors = "Not Available";
			                            	String director = "Not Available";
			                            	String music_director = "Not Available";
			                            	String singer = "Not Available";
			                            	for(Element mo_details : movie_details)
			                            	{
			                            		Elements stars = mo_details.getElementsByAttributeValueContaining("href", "/stars/");
			                            		
			                            		for(Element star_details : stars)
			                            		{
			                            			actors = actors+" , "+star_details.text();
			                            		}
			                            		
			                            		Elements directors = mo_details.getElementsByAttributeValueContaining("href", "/directors/");
			                            		
			                            		for(Element dir : directors)
			                            		{
			                            			director = director+" , "+dir.text();
			                            		}
			                            		
			                            		Elements music_directors = mo_details.getElementsByAttributeValueContaining("href", "/music-directors/");
			                            		
			                            		for(Element music_dir : music_directors)
			                            		{
			                            			music_director = music_director+" , "+music_dir.text();
			                            		}
			                            		
			                            		Elements singers = mo_details.getElementsByAttributeValueContaining("href", "/singers/");
			                            		
			                            		for(Element sing : singers)
			                            		{
			                            			singer = singer+" , "+sing.text();
			                            		}
			                            		
			                            	}
			                            	
			                            	movieObject.setACTORS(actors);
			                            	movieObject.setSINGERS(singer);
			                            	movieObject.setDIRECTOR(director);
			                            	movieObject.setMUSIC_DIRECTOR(music_director);
			                            	movieObject.setURLS(urlimage);
			                            	
			                            	
			                            	/*System.out.println(movieObject.getMOVIENUMBER()+" : "+movieObject.getMOVIENAME()+ " : "+movieObject.getMOVIESTARTCHAR()+" : "
			                            			+movieObject.getRELEASE_DATE()+" : "+movieObject.getMUSIC_DIRECTOR()+" : "+movieObject.getACTORS()+" : "+movieObject.getSINGERS()
			                            			+" : "+movieObject.getDIRECTOR()+" : "+movieObject.getURLS());
			                            	*/
			                            	DBConnection.insertandUpdateMovieObject(movieObject.getMOVIENUMBER(), movieObject.getMOVIESTARTCHAR(), movieObject.getMOVIENAME(), movieObject.getRELEASE_DATE(), movieObject.getMUSIC_DIRECTOR(), movieObject.getACTORS(), movieObject.getSINGERS(), movieObject.getDIRECTOR(), movieObject.getURLS());
			                            	
			                            	// Going to fetch songs lists for Movie
			                            	Elements s = docs.getElementsByAttributeValue("class", "download-single-links_box");
			                            	
			                            	for(Element songs : s)
			                            	{
			                            		
			                            		
			                            		//if(count == 4)
			                                 		//break;
			                            		
			                            		Elements artists = songs.getElementsByAttributeValueContaining("class", "link-item");
			                            		
			                            		for(Element sings : artists)
		                            			{
			                            			String songSingers = "Not Available";
			                            			Elements singers = sings.getElementsByAttributeValueContaining("href", "/singers/");
			                            			for(Element singerSong :singers){
			                            			
			                            				songSingers = songSingers+" , "+singerSong.text();
			                            			}
		                            			
			                            			Elements song = sings.getElementsByAttributeValueContaining("href", "/download/");
				                            		// getting links of songs
				                            		for(Element links : song)
				                            		{
				                            			
				                            			
				                            			
				                            			//if(count == 4)
				                                     		//break;
				                            			String songlink = links.attr("href");
				                            			String songname = links.text();
				                            			//System.out.println(songname);
				                            			
				                            			String url4 = "http://www.songsmp3.info"+songlink;
				                            			
				                            			response= Jsoup.connect(url4)
				                                  	           .ignoreContentType(true)
				                                  	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
				                                  	           .referrer("http://www.google.com")   
				                                  	           .timeout(12000) 
				                                  	           .followRedirects(true)
				                                  	           .execute();
				                                     	
				                                     	
				                                     	docs = response.parse();
				                                     	// going inside the songs page
				                                     	Elements linksDwnld = docs.getElementsByAttributeValueContaining("href", "http://101songs.com/fileDownload/Songs/128");
				                                     	
				                                     	for(Element looper : linksDwnld)
				                                     	{
				                                     		// Getting songs Details
				                                     		//if(count == 4)
				                                         		//break;
				                                     		String link128kb = looper.attr("href");
				                                     		
				                                     		//System.out.println(link128kb);
				                                     		
				                                     		Songs songObject = new Songs();
				                                     		songObject.setMovie(movieObject);
				                                     		songObject.setSONGNAME(songname);
				                                     		songObject.setSINGERS(songSingers);
				                                     		songObject.setSONGLINK_128KBPS(link128kb);
				                                     		
				                                     		String finalSongLink = getFinalRedirectedUrl(link128kb);
				                                     		//System.out.println("Final Song link is : " +finalSongLink);
				                                     		songObject.setSONGLINK_128KBPS_CONV(finalSongLink);
				                                     		URL linkToHit = convertToURLEscapingIllegalCharacters(finalSongLink);
				                                     		HttpURLConnection con = (HttpURLConnection) linkToHit.openConnection();
				                                     		con.connect();
				                                     		InputStreamReader isr = new InputStreamReader(con.getInputStream());
				                                     		
				                                     		BufferedReader reader = new BufferedReader(isr);
				                                     		
				                                     		byte[] buffer = new byte[4096];
				                                     		int n = - 1;
				                                     		boolean containsData = false;
				                                     		
				                                     		while (reader.readLine() != null) 
				                                     		{
				                                     			//System.out.println("Contains: "+reader.readLine());
				                                     			containsData = true;
				                                     			break;
				                                     		}
				                                     		con.disconnect();
				                                     		reader.close();
				                                     		isr.close();
				                                     		if(containsData)
				                                     			songObject.setWORKING_LINK("1");
				                                     		else
				                                     			songObject.setWORKING_LINK("0");
				                                     		
				                                     		String checks = "http://101songs.com/fileDownload/Songs/128/";
				                                     		int len = checks.length();
				                                     		String songNo = link128kb.substring(len, link128kb.length());
				                                     		songNo = songNo.replace(".mp3", "");
				                                     		songObject.setSONG_ID(Integer.parseInt(songNo));
				                                     		
				                                     		/*System.out.println(songObject.getSONG_ID()+ " : "+songObject.getSONGNAME()
				                                     		+" : "+songObject.getSINGERS()+" : "+songObject.getSONGLINK_128KBPS()+
				                                     		" : "+songObject.getMovie().getMOVIENUMBER());*/
				                                     		
				                                     		DBConnection.insertandUpdateMovieSongObject(songObject.getMovie().getMOVIENUMBER(), songObject.getSONGNAME(), songObject.getSINGERS(), songObject.getSONG_ID() ,songObject.getSONGLINK_128KBPS(),songObject.getSONGLINK_128KBPS_CONV(),songObject.getWORKING_LINK()) ;
				                                     	
				                                     		}
				                                     	
				                            			
				                            		}
			                            			
			                            			
		                            			}
			                            		
			                            		
			                            		
			                            	}
			                            	count = count+1;
			                            	
			                    		}
			                    	
			                    }
			                	
			                   
			                }
			                if(DBConnection.setSaveNewYearSuccessful(todayDate))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update successful New year Movies done");
			                }
			                
			            } catch (IOException e) {
			            	
			            	if(DBConnection.setSaveNewYearSuccessful("1000-02-02"))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update not successful as catch New year Movies done");
			                }
			            	
			                e.printStackTrace();
			            }
			            catch(Exception e)
			            {
			            	if(DBConnection.setSaveNewYearSuccessful("1000-02-02"))
			                {
			                	System.out.println(Calendar.getInstance().getTime().toString()+"update not successful in catch New year Movies done");
			                }
			            	e.printStackTrace();
			            }
		        		
		        	} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
        	
				}
				}
        		catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting 3rd  method Next Year Movie Songs");
        }
        
        public URL convertToURLEscapingIllegalCharacters(String string){
            try {
                String decodedURL = string;
                		//URLDecoder.decode(string, "UTF-8");
                URL url = new URL(decodedURL);
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()); 
                decodedURL=uri.toASCIIString();
                return new URL(decodedURL); 
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        
        public static String getFinalRedirectedUrl(String url)  {       
            String finalRedirectedUrl = url;
            try {
                HttpURLConnection connection;
                do {
                        connection = (HttpURLConnection) new URL(finalRedirectedUrl).openConnection();
                        connection.setInstanceFollowRedirects(false);
                        connection.setUseCaches(false);
                        connection.setRequestMethod("GET");
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode >=300 && responseCode <400)
                        {
                            String redirectedUrl = connection.getHeaderField("Location");
                            if(null== redirectedUrl) {
                                break;
                            }
                            finalRedirectedUrl =redirectedUrl;
                        }
                        else
                            break;
                } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
                connection.disconnect();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return finalRedirectedUrl;  
            }
        
        public void insertIndiPopSongs()
        {
        	System.out.println(Calendar.getInstance().getTime().toString()+"This method is used to insert indi Pop songs only");
        	
        	Calendar cal = Calendar.getInstance();
        	int curryear = Calendar.getInstance().get(Calendar.YEAR)-1;
        	int thisyear = curryear+1;
        	String year = String.valueOf(thisyear);
        	
        	/*try {*/
				/*if(DBConnection.getSaveLastYearSuccessful(curryear))
				{
					System.out.println("Dont Do Anything Last Year songs already in DB");
				}
				else
				{*/
					Document doc = null;
		            Document docs = null;
		            
		            cal.add(Calendar.DATE, 1);
		        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		        	//System.out.println(cal.getTime());
		        	// Output "Wed Sep 26 14:23:28 EST 2012"

		        	String todayDate = formatDate.format(cal.getTime());
		        	//System.out.println(formatted);
		        	
		        	
		        	
		        	try {
						if(DBConnection.getSaveThisYearPopSuccessful(todayDate))
						{
							System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything This Year songs already in DB");
						}
						else
						{
		            
		            try {
		            	String url = "http://www.songsmp3.info/5/indipop-mp3-songs.html";
		            	int count = 0;
		            	Response response= Jsoup.connect(url)
		            	           .ignoreContentType(true)
		            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		            	           .referrer("http://www.google.com")   
		            	           .timeout(12000) 
		            	           .followRedirects(true)
		            	           .execute();

		            	doc = response.parse();
		            	
		                String title = doc.title();
		                String href = doc.text();
		                // Getting the list of Elements like A, B , C , D, E ... Z
		                Elements e = doc.getElementsByAttributeValueContaining("href", "/5/indipop-mp3-songs/list-");

		                for (Element src : e) {
		                	
		                	
		                	//if(count == 4)
		                 		//break;
		                		
		                	String charlink = src.attr("href");
		                	
		                	String moviechar = src.text();
		                	//System.out.println(moviechar);
		                	// Getting the lists of movies for A or B or .. Z
		                	String url1 = "http://www.songsmp3.info"+charlink;
		                	/*if(url1.contains("list-a.html"))
		                		continue;*/
		                	response= Jsoup.connect(url1)
		             	           .ignoreContentType(true)
		             	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		             	           .referrer("http://www.google.com")   
		             	           .timeout(12000) 
		             	           .followRedirects(true)
		             	           .execute();
		                	
		                	
		                	docs = response.parse();
		                	
		                    Elements e1 = docs.getElementsByAttributeValue("class", "list_main_box");
		                    
		                    for(Element srcs : e1)
		                    {
		                    	
		                    	//if(count == 4)
		                     	//	break;
		                    	
		                    	// getting the movies list for a particular character
		                    	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "/5/indipop-mp3-songs/");
		                    		
		                    		for(Element srcss : e2)
		                    		{
		                    			
		                    			// Movie related Data
		                    			
		                    			//if(count == 4)
		                             	//	break;
		                    			String mlink = srcss.attr("href");
		                    			//System.out.println("MLINK is: "+mlink);
		                    			Pattern pattern = Pattern.compile("/(.*?)/");
		                    		    Matcher matcher = pattern.matcher(mlink);
		                    		    int MOVIENUMBER = 0;
		                    		    int countMatcher = 0;
		                    		    while (matcher.find()) {
		                    		    	if(countMatcher == 1)
		                    		    	{
		                    		    		MOVIENUMBER = Integer.parseInt(matcher.group(1));
		                    		    	}
		                    		    	countMatcher = countMatcher+1;
		                    		        
		                    		    }
		                    		    //System.out.println("MovieNumber is: "+MOVIENUMBER);
		                    		    /*if(mlink.contains("-"+year))
											continue;*/
		                    			Movie movieObject = new Movie();
		                    			
		                    			String mname = srcss.text();
		                    			//System.out.println(mname);
		                    			Pattern patternYear = Pattern.compile("(\\(.*?)\\)");
		                    		    Matcher matcherYear = patternYear.matcher(mname);
		                    		    String relyear="";
		                    		    
		                    		    while (matcherYear.find()) {
		                    		    		String sb =  matcherYear.group();
		                    		    		sb = sb.replaceAll("\\(","");
				                    			sb = sb.replaceAll("\\)", "");
				                    			
		                    		    		if(sb.matches("^-?\\d+$"))
		                    		    			relyear = sb;
		                    		    	
		                    		    }
		                    		    /*if(relyear.length() ==0)
		                    		    	continue;*/
		                    			movieObject.setMOVIENAME(mname);
		                    			movieObject.setMOVIENUMBER(MOVIENUMBER);
		                    			movieObject.setMOVIESTARTCHAR(moviechar);
		                    			movieObject.setRELEASE_DATE(relyear);
		                    			//System.out.println("Relese Year is: "+relyear);
		                    			
		                    			Document doc3 = null;
		                    			// Going to the movie page which contains songs
		                    			String url2 = "http://www.songsmp3.info"+mlink;
		                            	response= Jsoup.connect(url2)
		                         	           .ignoreContentType(true)
		                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		                         	           .referrer("http://www.google.com")   
		                         	           .timeout(12000) 
		                         	           .followRedirects(true)
		                         	           .execute();
		                            	
		                            	docs = response.parse();
		                    			
		                            	Elements movie_url = docs.getElementsByAttributeValueContaining("alt", mname);
		                            	String urlimage = "NA";
		                            	try{
		                            	if(movie_url != null && movie_url.size() > 0)
		                            		urlimage= "http://www.songsmp3.info"+movie_url.get(0).attr("src");
		                            	}
		                            	catch(Exception es)
		                            	{
		                            		System.out.println(Calendar.getInstance().getTime().toString()+mname);
		                            	}
		                            	Elements movie_details = docs.getElementsByAttributeValueContaining("class", "movie_details");
		                            	String actors = "Not Available";
		                            	String director = "Not Available";
		                            	String music_director = "Not Available";
		                            	String singer = "Not Available";
		                            	for(Element mo_details : movie_details)
		                            	{
		                            		Elements stars = mo_details.getElementsByAttributeValueContaining("href", "/stars/");
		                            		
		                            		for(Element star_details : stars)
		                            		{
		                            			actors = actors+" , "+star_details.text();
		                            		}
		                            		
		                            		Elements directors = mo_details.getElementsByAttributeValueContaining("href", "/directors/");
		                            		
		                            		for(Element dir : directors)
		                            		{
		                            			director = director+" , "+dir.text();
		                            		}
		                            		
		                            		Elements music_directors = mo_details.getElementsByAttributeValueContaining("href", "/music-directors/");
		                            		
		                            		for(Element music_dir : music_directors)
		                            		{
		                            			music_director = music_director+" , "+music_dir.text();
		                            		}
		                            		
		                            		Elements singers = mo_details.getElementsByAttributeValueContaining("href", "/singers/");
		                            		
		                            		for(Element sing : singers)
		                            		{
		                            			singer = singer+" , "+sing.text();
		                            		}
		                            		
		                            	}
		                            	
		                            	movieObject.setACTORS(actors);
		                            	movieObject.setSINGERS(singer);
		                            	movieObject.setDIRECTOR(director);
		                            	movieObject.setMUSIC_DIRECTOR(music_director);
		                            	movieObject.setURLS(urlimage);
		                            	
		                            	
		                            	//System.out.println(movieObject.getMOVIENUMBER()+" : "+movieObject.getMOVIENAME()+ " : "+movieObject.getMOVIESTARTCHAR()+" : "
		                            	//		+movieObject.getRELEASE_DATE()+" : "+movieObject.getMUSIC_DIRECTOR()+" : "+movieObject.getACTORS()+" : "+movieObject.getSINGERS()
		                            	//		+" : "+movieObject.getDIRECTOR()+" : "+movieObject.getURLS());
		                            	//insertandUpdateMovieObject(int movieNumber, String MOVIESTARTCHAR, String MOVIENAME, String releaseyear , String MUSIC_DIRECTOR, String ACTORS, String SINGERS, String DIRECTOR, String URLS) throws SQLException{
		                            	// Going to fetch songs lists for Movie
		                            	DBConnection.insertandUpdateIndiPopObject(movieObject.getMOVIENUMBER(), movieObject.getMOVIESTARTCHAR(), movieObject.getMOVIENAME(), movieObject.getRELEASE_DATE(), movieObject.getMUSIC_DIRECTOR(), movieObject.getACTORS(), movieObject.getSINGERS(), movieObject.getDIRECTOR(), movieObject.getURLS());
		                            	Elements s = docs.getElementsByAttributeValue("class", "download-single-links_box");
		                            	
		                            	for(Element songs : s)
		                            	{
		                            		
		                            		
		                            		//if(count == 4)
		                                 	//	break;
		                            		
		                            		Elements artists = songs.getElementsByAttributeValueContaining("class", "link-item");
		                            		
		                            		for(Element sings : artists)
	                            			{
		                            			String songSingers = "Not Available";
		                            			Elements singers = sings.getElementsByAttributeValueContaining("href", "/singers/");
		                            			for(Element singerSong :singers){
		                            			
		                            				songSingers = songSingers+" , "+singerSong.text();
		                            			}
	                            			
		                            			Elements song = sings.getElementsByAttributeValueContaining("href", "/download/");
			                            		// getting links of songs
			                            		for(Element links : song)
			                            		{
			                            			
			                            			
			                            			
			                            			//if(count == 4)
			                                     	//	break;
			                            			String songlink = links.attr("href");
			                            			String songname = links.text();
			                            			//System.out.println(songname);
			                            			
			                            			String url4 = "http://www.songsmp3.info"+songlink;
			                            			
			                            			response= Jsoup.connect(url4)
			                                  	           .ignoreContentType(true)
			                                  	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			                                  	           .referrer("http://www.google.com")   
			                                  	           .timeout(12000) 
			                                  	           .followRedirects(true)
			                                  	           .execute();
			                                     	
			                                     	
			                                     	docs = response.parse();
			                                     	// going inside the songs page
			                                     	Elements linksDwnld = docs.getElementsByAttributeValueContaining("href", "http://101songs.com/fileDownload/Songs/128");
			                                     	
			                                     	for(Element looper : linksDwnld)
			                                     	{
			                                     		// Getting songs Details
			                                     		//if(count == 4)
			                                         	//	break;
			                                     		String link128kb = looper.attr("href");
			                                     		
			                                     		//System.out.println(link128kb);
			                                     		
			                                     		Songs songObject = new Songs();
			                                     		songObject.setMovie(movieObject);
			                                     		songObject.setSONGNAME(songname);
			                                     		songObject.setSINGERS(songSingers);
			                                     		songObject.setSONGLINK_128KBPS(link128kb);
			                                     		
			                                     		
			                                     		String finalSongLink = getFinalRedirectedUrl(link128kb);
			                                     		//System.out.println("Final Song link is : " +finalSongLink);
			                                     		songObject.setSONGLINK_128KBPS_CONV(finalSongLink);
			                                     		URL linkToHit = convertToURLEscapingIllegalCharacters(finalSongLink);
			                                     		HttpURLConnection con = (HttpURLConnection) linkToHit.openConnection();
			                                     		con.connect();
			                                     		InputStreamReader isr = new InputStreamReader(con.getInputStream());
			                                     		
			                                     		BufferedReader reader = new BufferedReader(isr);
			                                     		
			                                     		byte[] buffer = new byte[4096];
			                                     		int n = - 1;
			                                     		boolean containsData = false;
			                                     		
			                                     		while (reader.readLine() != null) 
			                                     		{
			                                     			//System.out.println("Contains: "+reader.readLine());
			                                     			containsData = true;
			                                     			break;
			                                     		}
			                                     		con.disconnect();
			                                     		reader.close();
			                                     		isr.close();
			                                     		if(containsData)
			                                     			songObject.setWORKING_LINK("1");
			                                     		else
			                                     			songObject.setWORKING_LINK("0");
			                                     		
			                                     		String checks = "http://101songs.com/fileDownload/Songs/128/";
			                                     		int len = checks.length();
			                                     		String songNo = link128kb.substring(len, link128kb.length());
			                                     		songNo = songNo.replace(".mp3", "");
			                                     		songObject.setSONG_ID(Integer.parseInt(songNo));
			                                     		
			                                     		/*System.out.println(songObject.getSONG_ID()+ " : "+songObject.getSONGNAME()
			                                     		+" : "+songObject.getSINGERS()+" : "+songObject.getSONGLINK_128KBPS()+
			                                     		" : "+songObject.getMovie().getMOVIENUMBER());*/
			                                     		
			                                     		DBConnection.insertandUpdateIndiPopSongObject(songObject.getMovie().getMOVIENUMBER(), songObject.getSONGNAME(), songObject.getSINGERS(), songObject.getSONG_ID() ,songObject.getSONGLINK_128KBPS(),songObject.getSONGLINK_128KBPS_CONV(),songObject.getWORKING_LINK()) ;
			                                     		
			                                     	}
			                                     	
			                            			
			                            		}
		                            			
		                            			
	                            			}
		                            		
		                            		
		                            		
		                            	}
		                            	count = count+1;
		                            	
		                    		}
		                    	
		                    }
		                	
		                   
		                }
		                DBConnection.setSaveThisYearPopSuccessful(todayDate);
		                /*if(DBConnection.setSaveLastYearSuccessful(curryear))
		                {
		                	System.out.println("update successful last year Movies done");
		                }*/

		            } catch (IOException e) {
		            	
		            	/*if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println("update last year Movies because catch occured");
		                }*/
		            	
		                e.printStackTrace();
		            }
		            catch(Exception e)
		            {
		            	/*if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println("update last year Movies because catch occured");
		                }*/
		            	e.printStackTrace();
		            }
		            }
					}
		        	catch (SQLException e3) {
						// TODO Auto-generated catch block
						
						e3.printStackTrace();
					}
				
			/*} */
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting 1st method Indi Pop data complete");
        }
        
        public void insertPunjabiPopSongs()
        {
        	System.out.println(Calendar.getInstance().getTime().toString()+"This method is used to insert Punjabi songs only");
        	
        	Calendar cal = Calendar.getInstance();
        	int curryear = Calendar.getInstance().get(Calendar.YEAR)-1;
        	int thisyear = curryear+1;
        	String year = String.valueOf(thisyear);
        	
        	/*try {*/
				/*if(DBConnection.getSaveLastYearSuccessful(curryear))
				{
					System.out.println("Dont Do Anything Last Year songs already in DB");
				}
				else
				{*/
        	
					Document doc = null;
		            Document docs = null;
		           
		            
		        	cal.add(Calendar.DATE, 1);
		        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		        	//System.out.println(cal.getTime());
		        	// Output "Wed Sep 26 14:23:28 EST 2012"

		        	String todayDate = formatDate.format(cal.getTime());
		        	//System.out.println(formatted);
		        	
		        	
		        	
		        	try {
						if(DBConnection.getSaveThisYearPunjabiSuccessful(todayDate))
						{
							System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything Punjabi songs already in DB");
						}
						else
						{        
		            
		            
		            try {
		            	String url = "http://www.songsmp3.info/2/punjabi-music.html";
		            	int count = 0;
		            	Response response= Jsoup.connect(url)
		            	           .ignoreContentType(true)
		            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		            	           .referrer("http://www.google.com")   
		            	           .timeout(12000) 
		            	           .followRedirects(true)
		            	           .execute();

		            	doc = response.parse();
		            	
		                String title = doc.title();
		                String href = doc.text();
		                // Getting the list of Elements like A, B , C , D, E ... Z
		                Elements e = doc.getElementsByAttributeValueContaining("href", "/2/punjabi-music/list-");

		                for (Element src : e) {
		                	
		                	
		                	//if(count == 4)
		                 		//break;
		                		
		                	String charlink = src.attr("href");
		                	
		                	String moviechar = src.text();
		                	//System.out.println(moviechar);
		                	// Getting the lists of movies for A or B or .. Z
		                	String url1 = "http://www.songsmp3.info"+charlink;
		                	/*if(url1.contains("list-a.html"))
		                		continue;*/
		                	response= Jsoup.connect(url1)
		             	           .ignoreContentType(true)
		             	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		             	           .referrer("http://www.google.com")   
		             	           .timeout(12000) 
		             	           .followRedirects(true)
		             	           .execute();
		                	
		                	
		                	docs = response.parse();
		                	
		                    Elements e1 = docs.getElementsByAttributeValue("class", "list_main_box");
		                    
		                    for(Element srcs : e1)
		                    {
		                    	
		                    	//if(count == 4)
		                     	//	break;
		                    	
		                    	// getting the movies list for a particular character
		                    	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "/2/punjabi-music/");
		                    		
		                    		for(Element srcss : e2)
		                    		{
		                    			
		                    			// Movie related Data
		                    			
		                    			//if(count == 4)
		                             	//	break;
		                    			String mlink = srcss.attr("href");
		                    			//System.out.println("MLINK is: "+mlink);
		                    			Pattern pattern = Pattern.compile("/(.*?)/");
		                    		    Matcher matcher = pattern.matcher(mlink);
		                    		    int MOVIENUMBER = 0;
		                    		    int countMatcher = 0;
		                    		    while (matcher.find()) {
		                    		    	if(countMatcher == 1)
		                    		    	{
		                    		    		MOVIENUMBER = Integer.parseInt(matcher.group(1));
		                    		    	}
		                    		    	countMatcher = countMatcher+1;
		                    		        
		                    		    }
		                    		    //System.out.println("MovieNumber is: "+MOVIENUMBER);
		                    		    /*if(mlink.contains("-"+year))
											continue;*/
		                    			Movie movieObject = new Movie();
		                    			
		                    			String mname = srcss.text();
		                    			//System.out.println(mname);
		                    			Pattern patternYear = Pattern.compile("(\\(.*?)\\)");
		                    		    Matcher matcherYear = patternYear.matcher(mname);
		                    		    String relyear="";
		                    		    
		                    		    while (matcherYear.find()) {
		                    		    		String sb =  matcherYear.group();
		                    		    		sb = sb.replaceAll("\\(","");
				                    			sb = sb.replaceAll("\\)", "");
				                    			
		                    		    		if(sb.matches("^-?\\d+$"))
		                    		    			relyear = sb;
		                    		    	
		                    		    }
		                    		    /*if(relyear.length() ==0)
		                    		    	continue;*/
		                    			movieObject.setMOVIENAME(mname);
		                    			movieObject.setMOVIENUMBER(MOVIENUMBER);
		                    			movieObject.setMOVIESTARTCHAR(moviechar);
		                    			movieObject.setRELEASE_DATE(relyear);
		                    			//System.out.println("Relese Year is: "+relyear);
		                    			
		                    			Document doc3 = null;
		                    			// Going to the movie page which contains songs
		                    			String url2 = "http://www.songsmp3.info"+mlink;
		                            	response= Jsoup.connect(url2)
		                         	           .ignoreContentType(true)
		                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
		                         	           .referrer("http://www.google.com")   
		                         	           .timeout(12000) 
		                         	           .followRedirects(true)
		                         	           .execute();
		                            	
		                            	docs = response.parse();
		                    			
		                            	Elements movie_url = docs.getElementsByAttributeValueContaining("alt", mname);
		                            	String urlimage = "NA";
		                            	try{
		                            	if(movie_url != null && movie_url.size() > 0)
		                            		urlimage= "http://www.songsmp3.info"+movie_url.get(0).attr("src");
		                            	}
		                            	catch(Exception es)
		                            	{
		                            		System.out.println(Calendar.getInstance().getTime().toString()+mname);
		                            	}
		                            	Elements movie_details = docs.getElementsByAttributeValueContaining("class", "movie_details");
		                            	String actors = "Not Available";
		                            	String director = "Not Available";
		                            	String music_director = "Not Available";
		                            	String singer = "Not Available";
		                            	for(Element mo_details : movie_details)
		                            	{
		                            		Elements stars = mo_details.getElementsByAttributeValueContaining("href", "/stars/");
		                            		
		                            		for(Element star_details : stars)
		                            		{
		                            			actors = actors+" , "+star_details.text();
		                            		}
		                            		
		                            		Elements directors = mo_details.getElementsByAttributeValueContaining("href", "/directors/");
		                            		
		                            		for(Element dir : directors)
		                            		{
		                            			director = director+" , "+dir.text();
		                            		}
		                            		
		                            		Elements music_directors = mo_details.getElementsByAttributeValueContaining("href", "/music-directors/");
		                            		
		                            		for(Element music_dir : music_directors)
		                            		{
		                            			music_director = music_director+" , "+music_dir.text();
		                            		}
		                            		
		                            		Elements singers = mo_details.getElementsByAttributeValueContaining("href", "/singers/");
		                            		
		                            		for(Element sing : singers)
		                            		{
		                            			singer = singer+" , "+sing.text();
		                            		}
		                            		
		                            	}
		                            	
		                            	movieObject.setACTORS(actors);
		                            	movieObject.setSINGERS(singer);
		                            	movieObject.setDIRECTOR(director);
		                            	movieObject.setMUSIC_DIRECTOR(music_director);
		                            	movieObject.setURLS(urlimage);
		                            	
		                            	
		                            	//System.out.println(movieObject.getMOVIENUMBER()+" : "+movieObject.getMOVIENAME()+ " : "+movieObject.getMOVIESTARTCHAR()+" : "
		                            	//		+movieObject.getRELEASE_DATE()+" : "+movieObject.getMUSIC_DIRECTOR()+" : "+movieObject.getACTORS()+" : "+movieObject.getSINGERS()
		                            	//		+" : "+movieObject.getDIRECTOR()+" : "+movieObject.getURLS());
		                            	//insertandUpdateMovieObject(int movieNumber, String MOVIESTARTCHAR, String MOVIENAME, String releaseyear , String MUSIC_DIRECTOR, String ACTORS, String SINGERS, String DIRECTOR, String URLS) throws SQLException{
		                            	// Going to fetch songs lists for Movie
		                            	DBConnection.insertandUpdatePunjabiObject(movieObject.getMOVIENUMBER(), movieObject.getMOVIESTARTCHAR(), movieObject.getMOVIENAME(), movieObject.getRELEASE_DATE(), movieObject.getMUSIC_DIRECTOR(), movieObject.getACTORS(), movieObject.getSINGERS(), movieObject.getDIRECTOR(), movieObject.getURLS());
		                            	Elements s = docs.getElementsByAttributeValue("class", "download-single-links_box");
		                            	
		                            	for(Element songs : s)
		                            	{
		                            		
		                            		
		                            		//if(count == 4)
		                                 	//	break;
		                            		
		                            		Elements artists = songs.getElementsByAttributeValueContaining("class", "link-item");
		                            		
		                            		for(Element sings : artists)
	                            			{
		                            			String songSingers = "Not Available";
		                            			Elements singers = sings.getElementsByAttributeValueContaining("href", "/singers/");
		                            			for(Element singerSong :singers){
		                            			
		                            				songSingers = songSingers+" , "+singerSong.text();
		                            			}
	                            			
		                            			Elements song = sings.getElementsByAttributeValueContaining("href", "/download/");
			                            		// getting links of songs
			                            		for(Element links : song)
			                            		{
			                            			
			                            			
			                            			
			                            			//if(count == 4)
			                                     	//	break;
			                            			String songlink = links.attr("href");
			                            			String songname = links.text();
			                            			//System.out.println(songname);
			                            			
			                            			String url4 = "http://www.songsmp3.info"+songlink;
			                            			
			                            			response= Jsoup.connect(url4)
			                                  	           .ignoreContentType(true)
			                                  	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			                                  	           .referrer("http://www.google.com")   
			                                  	           .timeout(12000) 
			                                  	           .followRedirects(true)
			                                  	           .execute();
			                                     	
			                                     	
			                                     	docs = response.parse();
			                                     	// going inside the songs page
			                                     	Elements linksDwnld = docs.getElementsByAttributeValueContaining("href", "http://101songs.com/fileDownload/Songs/128");
			                                     	
			                                     	for(Element looper : linksDwnld)
			                                     	{
			                                     		// Getting songs Details
			                                     		//if(count == 4)
			                                         	//	break;
			                                     		String link128kb = looper.attr("href");
			                                     		
			                                     		//System.out.println(link128kb);
			                                     		
			                                     		Songs songObject = new Songs();
			                                     		songObject.setMovie(movieObject);
			                                     		songObject.setSONGNAME(songname);
			                                     		songObject.setSINGERS(songSingers);
			                                     		songObject.setSONGLINK_128KBPS(link128kb);
			                                     		
			                                     		
			                                     		String finalSongLink = getFinalRedirectedUrl(link128kb);
			                                     		//System.out.println("Final Song link is : " +finalSongLink);
			                                     		songObject.setSONGLINK_128KBPS_CONV(finalSongLink);
			                                     		URL linkToHit = convertToURLEscapingIllegalCharacters(finalSongLink);
			                                     		HttpURLConnection con = (HttpURLConnection) linkToHit.openConnection();
			                                     		con.connect();
			                                     		InputStreamReader isr = new InputStreamReader(con.getInputStream());
			                                     		
			                                     		BufferedReader reader = new BufferedReader(isr);
			                                     		
			                                     		byte[] buffer = new byte[4096];
			                                     		int n = - 1;
			                                     		boolean containsData = false;
			                                     		
			                                     		while (reader.readLine() != null) 
			                                     		{
			                                     			//System.out.println("Contains: "+reader.readLine());
			                                     			containsData = true;
			                                     			break;
			                                     		}
			                                     		con.disconnect();
			                                     		reader.close();
			                                     		isr.close();
			                                     		if(containsData)
			                                     			songObject.setWORKING_LINK("1");
			                                     		else
			                                     			songObject.setWORKING_LINK("0");
			                                     		
			                                     		String checks = "http://101songs.com/fileDownload/Songs/128/";
			                                     		int len = checks.length();
			                                     		String songNo = link128kb.substring(len, link128kb.length());
			                                     		songNo = songNo.replace(".mp3", "");
			                                     		songObject.setSONG_ID(Integer.parseInt(songNo));
			                                     		
			                                     		/*System.out.println(songObject.getSONG_ID()+ " : "+songObject.getSONGNAME()
			                                     		+" : "+songObject.getSINGERS()+" : "+songObject.getSONGLINK_128KBPS()+
			                                     		" : "+songObject.getMovie().getMOVIENUMBER());*/
			                                     		
			                                     		DBConnection.insertandUpdatePunjabiSongObject(songObject.getMovie().getMOVIENUMBER(), songObject.getSONGNAME(), songObject.getSINGERS(), songObject.getSONG_ID() ,songObject.getSONGLINK_128KBPS(),songObject.getSONGLINK_128KBPS_CONV(),songObject.getWORKING_LINK()) ;
			                                     		
			                                     	}
			                                     	
			                            			
			                            		}
		                            			
		                            			
	                            			}
		                            		
		                            		
		                            		
		                            	}
		                            	count = count+1;
		                            	
		                    		}
		                    	
		                    }
		                	
		                   
		                }
		                DBConnection.setSaveThisYearPunjabiSuccessful(todayDate);
		                /*if(DBConnection.setSaveLastYearSuccessful(curryear))
		                {
		                	System.out.println("update successful last year Movies done");
		                }*/

		            } catch (IOException e) {
		            	
		            	/*if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println("update last year Movies because catch occured");
		                }*/
		            	
		                e.printStackTrace();
		            }
		            catch(Exception e)
		            {
		            	/*if(DBConnection.setSaveLastYearSuccessful(curryear-1))
		                {
		                	System.out.println("update last year Movies because catch occured");
		                }*/
		            	e.printStackTrace();
		            }
		            }
		        }
		        catch (SQLException e3) {
						// TODO Auto-generated catch block
						
						e3.printStackTrace();
				}
		        	
				
			/*} */
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting 1st method Indi Pop data complete");
        }
        
        
        public void insertLyricsSong(){
        	
        	Document doc = null;
            Document docs = null;
            try {
            	
            	String url = "http://www.lyricsmint.com/2011/09/latest-hindi-movie-songs-lyrics.html";
            	int count = 0;
            	Response response= Jsoup.connect(url)
            	           .ignoreContentType(true)
            	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
            	           .referrer("http://www.google.com")   
            	           .timeout(12000) 
            	           .followRedirects(true)
            	           .execute();

            	doc = response.parse();
            	
                String title = doc.title();
                String href = doc.text();
                
                Elements e = doc.getElementsByAttributeValueContaining("style", "list-style-type: none; padding: 0px; margin: 0px; font-size: 16px;");
                for(Element elist: e){
                	
                	Elements eAHref = elist.getElementsByAttributeValueContaining("href", "http://www.lyricsmint.com/");
                	for(Element eMov : eAHref){
                		
                		String hrefFinal = eMov.attr("href");
                		System.out.println(Calendar.getInstance().getTime().toString()+hrefFinal);
                		response= Jsoup.connect(hrefFinal)
                 	           .ignoreContentType(true)
                 	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                 	           .referrer("http://www.google.com")   
                 	           .timeout(12000) 
                 	           .followRedirects(true)
                 	           .execute();

                		doc = response.parse();
                		Elements eSongsElements = doc.getElementsByAttributeValueContaining("style", "font-size: 18px; line-height: 32px; word-spacing:");
                		//font-size: 18px; line-height: 32px; word-spacing:1px;
                		for(Element eSongsList : eSongsElements){
                			
                			Elements eAHrefLyricsSongs = eSongsList.getElementsByAttributeValueContaining("href", "http://www.lyricsmint.com/");
                			
                			for(Element songLyrics : eAHrefLyricsSongs ){
                				
                				String hrefOfLyrics = songLyrics.attr("href");
                				System.out.println(Calendar.getInstance().getTime().toString()+"Hello : "+hrefOfLyrics);
                				response= Jsoup.connect(hrefOfLyrics)
                          	           .ignoreContentType(true)
                          	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                          	           .referrer("http://www.google.com")   
                          	           .timeout(12000) 
                          	           .followRedirects(true)
                          	           .execute();

                         		doc = response.parse();
                         		
                         		
                         		
                         		Elements lyricsFinal = doc.getElementsByAttributeValue("id", "lyric");
                         		
                         		for(Element getLyrics : lyricsFinal){
                         			
                         			Elements pElement = getLyrics.getElementsByTag("p");
                         			String lyricsMySong = "";
                         			for(Element eL :pElement)
                         			{
                         				String html = eL.html();
                         				String tempText = html.replaceAll("<br>", "\n");
                         				
                         				lyricsMySong = lyricsMySong + tempText + "\r\n";
                         				
                         			}
                         			
                         			System.out.println(Calendar.getInstance().getTime().toString()+lyricsMySong);
              
                         			
                         			break;
                         		}
                				
                				
                			}
                			
                			break;
                		}
                		
                		
                	}
                	break;
                	
                }
                
                
                
                
            }
            catch(Exception exc){
            	
            }
        	
        	
        }
        
        public void insertHindiLyricsNet(){
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Entering Insert Hindi Lyrics Method");
        	
        	String[] chars = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        	//String[] chars = {"D","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        	String[] pages = {"-page-1.html","-page-2.html","-page-3.html","-page-4.html","-page-5.html","-page-6.html","-page-7.html","-page-8.html","-page-9.html","-page-10.html"};
        	String baseurl = "http://www.hindilyrics.net/lyrics/hindi-songs-starting-";
        	//http://www.hindilyrics.net/lyrics/hindi-songs-starting-
        	
        	Document doc = null;
            Document docs = null;
            
            Calendar cal = Calendar.getInstance();
        	cal.add(Calendar.DATE, 1);
        	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        	//System.out.println(cal.getTime());
        	// Output "Wed Sep 26 14:23:28 EST 2012"

        	String todayDate = formatDate.format(cal.getTime());
        	//System.out.println(formatted);
        	
        	int curryear = Calendar.getInstance().get(Calendar.YEAR);
        	String year = String.valueOf(curryear);
        	
        	try {
				if(DBConnection.getSaveThisYearLyricsSuccessful(todayDate))
				{
					System.out.println(Calendar.getInstance().getTime().toString()+"Dont Do Anything Lyrics are already added for today");
				}
				else
				{
            
            
					try {
            	
            	for(int i = 0; i < chars.length ; i++){
            	
            		for(int j = 0; j < pages.length ; j++){
            			
            			String url = baseurl+chars[i]+pages[j];
            			//System.out.println("URL IS : "+url);
            			Response response= Jsoup.connect(url)
                	           .ignoreContentType(true)
                	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                	           .referrer("http://www.google.com")   
                	           .timeout(12000) 
                	           .followRedirects(true)
                	           .execute();

            			doc = response.parse();
            			
            			Elements e = doc.getElementsByAttributeValueContaining("href", "/lyrics/hindi-lyrics-of-");
            			
            			for(Element e1 : e){
            				
            				String MOVIENAME = e1.text();
            				//System.out.println("MOVIENAME IS : " +MOVIENAME);
            				int ISLATEST = 0;
            				String href = e1.attr("href");
            				//System.out.println("HREF IS : "+href);
            				href = href.replaceAll("\n", "");
            				href = href.replaceAll("\r", "");
            				href = "http://www.hindilyrics.net"+href;
            				//int mOVIENUMBER, String mOVIESTARTCHAR, String mOVIENAME,String uRLS,int ISLATEST
            				MOVIELYRICS mlObject = new MOVIELYRICS(0,MOVIENAME.substring(0, 1),MOVIENAME,null,ISLATEST);
            				
            				response= Jsoup.connect(href)
                     	           .ignoreContentType(true)
                     	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                     	           .referrer("http://www.google.com")   
                     	           .timeout(12000) 
                     	           .followRedirects(true)
                     	           .execute();

                 			doc = response.parse();
            				
                 			Elements urlMovie = doc.getElementsByAttributeValueContaining("src", "/movie-pic/");
                 			for(Element u : urlMovie){
                 				String srcimage = u.attr("src");
                 				srcimage = "http://www.hindilyrics.net"+srcimage;
                 				mlObject.setURLS(srcimage);
                 				break;
                 			}
                 			int MOVIENUMBER = DBConnection.insertandUpdateMovieLyricsObject(0, String.valueOf(MOVIENAME.charAt(0)), MOVIENAME,mlObject.getURLS(),ISLATEST);
                 			mlObject.setMOVIENUMBER(MOVIENUMBER);
                 			
                 			Elements e8 = doc.getElementsByAttributeValueContaining("class","square");
                 			for(Element e6 : e8){
                 			
                 			Elements e2 = e6.getElementsByAttributeValueContaining("href", "/lyrics/of-");
                 			
                 			for(Element e3 : e2)
                 			{
                 				
                 				
                 				String href1 = e3.attr("href");
                 				//System.out.println("HREF1 is : "+href1);
                 				href1 = href1.replaceAll("\n", "");
                				href1 = href1.replaceAll("\r", "");
                 				href1 = "http://www.hindilyrics.net"+href1;
                				
                				String sName = e3.text();
                				SongsLyrics slObject = new SongsLyrics(mlObject, sName, 0, null);
                				response= Jsoup.connect(href1)
                         	           .ignoreContentType(true)
                         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                         	           .referrer("http://www.google.com")   
                         	           .timeout(12000) 
                         	           .followRedirects(true)
                         	           .execute();

                     			doc = response.parse();
                     			
                     			Elements e4 = doc.getElementsByAttributeValue("face", "verdana");
                     			
                     			for(Element e5 : e4){
                     				slObject.setLYRICS(e5.text());
                     				DBConnection.insertandUpdateMovieLyricsSongsObject(mlObject,slObject.getSONGNAME(),slObject.getLYRICS());
                     				//System.out.println(e5.text());
                     				break;
                     				
                     			}
                     			//System.out.println("");
                 				
                 			}
                 			break;
                 			}
                 			
                 			
            			}
            			
            		}
            	}
            	
            	boolean success = DBConnection.setSaveThisYearLyricsSuccessful(todayDate);
            	
            	
            
            }
            catch(Exception e){
            		System.out.println(Calendar.getInstance().getTime().toString()+"In Exception");
            	}
				}
        	}
        	catch(Exception es){
        		
        	}
        	
        	System.out.println(Calendar.getInstance().getTime().toString()+"Exiting insertHindiLyricsMethod");
        	
        }
        
        
public void updateHindiLyricsNet() {
        	String baseurl = "http://www.hindilyrics.net";
        	//http://www.hindilyrics.net/lyrics/hindi-songs-starting-
        	System.out.println(Calendar.getInstance().getTime().toString()+"Entering updateHindiLyricsNet method");
        	Document doc = null;
            Document docs = null;
            
            try{
            	
            	boolean done =  DBConnection.updateIsLatestToZero();
            	
            	Response response= Jsoup.connect(baseurl)
         	           .ignoreContentType(true)
         	           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
         	           .referrer("http://www.google.com")   
         	           .timeout(12000) 
         	           .followRedirects(true)
         	           .execute();

     			doc = response.parse();
     			
     			Element e = doc.getElementById("new_thumb");
     			
     			Elements toUpdate = e.getElementsByAttributeValueContaining("href", "/lyrics/hindi-lyrics-of-");
     			for(Element eNew : toUpdate){
     				
     				
     				String href = eNew.attr("href");
     				System.out.println(Calendar.getInstance().getTime().toString()+"HREF IS: "+href);
     				String mname = href.replaceAll("/lyrics/hindi-lyrics-of-", "");
     				
     				mname = mname.replaceAll("%20", " ");
     				mname = mname.replaceAll(".html", "");
     				//mname = mname.replaceAll("20", "");
     				mname = mname.toUpperCase();
     				System.out.println(Calendar.getInstance().getTime().toString()+"Moviename is : "+mname);
     				
     				boolean toOne =  DBConnection.updateIsLatestToOne(mname);
     				
     			}
     			
            }catch(Exception e){
            	
            }
                    	
            System.out.println(Calendar.getInstance().getTime().toString()+"Exiting updateHindiLyricsNet");
        	
        }
        
        
}
