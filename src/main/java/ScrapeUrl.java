import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ScrapeUrl {

    private Document document;
    private Connection con;

    public ScrapeUrl(String url) {


        if (url.equals("https://www.danskeboligarkitekter.dk/tips-og-raad/skab-sammenhaeng-mellem-bad-og-bolig")){

        } else {
            con = Jsoup.connect(url);
            System.out.println("scraping: " + url);
            document = request();
        }
    }

    public int GetStatusCode(){
        if (con == null){
            return -1;
        }
        try {
            return request().connection().response().statusCode();
        }catch (NullPointerException e){
            System.out.println(4);
            return 404;
        }
    }


    private Document request() {
        try {
            Document document = con.get();

            if (con.response().statusCode() == 200) {
                return document;
            } else
                return null;

        } catch (IOException e) {
            return null;
        }
    }



    public LinkedList<String> getAllUrls(String contains) {

        if(document != null){
            LinkedList<String> listOfUrls = new LinkedList<>();

            for(Element link : document.select("a[href]")){
                String url = link.absUrl("href");

                if(url.contains(contains)){
                    listOfUrls.add(url);
                }
            }
            return listOfUrls;
        } else
            return new LinkedList<>();



    }

    // step 1. get all pages
    // step 2. get all urls on page
    // step 3. scrape url and get status code



}
