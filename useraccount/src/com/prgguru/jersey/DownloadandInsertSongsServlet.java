package com.prgguru.jersey;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class DownloadandInsertSongsServlet
 */
@WebServlet(description = "Code to Download and insert songs into DB", urlPatterns = { "/DownloadandInsertSongsServlet" })
public class DownloadandInsertSongsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadandInsertSongsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//List<Movies> result = new ArrayList<Movies>();
        Document doc = null;
        Document docs = null;
        try {
        	String url = "https://www.songsmp3.com/1/bollywood-music.html";
            doc = Jsoup.connect(url).get();
            String title = doc.title();
            String href = doc.text();

            Elements e = doc.getElementsByAttributeValueContaining("href", "/1/bollywood-music/list-");

            for (Element src : e) {
            		
            	String charlink = src.attr("href");
            	String moviechar = src.text();
            	
            	String url1 = "https://www.songsmp3.com"+charlink;
                docs = Jsoup.connect(url1).get();
            	
                Elements e1 = docs.getElementsByAttributeValueContaining("class", "list_inside_box");
                
                for(Element srcs : e1)
                {
                	Elements e2 = srcs.getElementsByAttributeValueContaining("href", "/1/bollywood-music/");
                	
                		for(Element srcss : e2)
                		{
                			String mlink = srcss.attr("href");
                			String mname = srcss.text();
                		}
                	
                }
            	
               
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
