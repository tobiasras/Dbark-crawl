import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DbarkCrawl {

    public static void main(String[] args) {
        DbarkCrawl crawl = new DbarkCrawl();

        ArrayList<String> urls = crawl.check404();

        for (int i = 0; i < urls.size(); i++) {
            System.out.println(urls.get(i));
        }
    }

    // collects all links from articles
    private LinkedList<String> getAllUrl(){


        String tips = "https://www.danskeboligarkitekter.dk/tips-og-raad";
        String billedeSerie = "https://www.danskeboligarkitekter.dk/billedserier";
        String boligreportage = "https://www.danskeboligarkitekter.dk/boligreportager";
        String guide = "https://www.danskeboligarkitekter.dk/guides";
        String artikel = "https://www.danskeboligarkitekter.dk/artikler";

        LinkedList<String> allUrls;
        allUrls = fetchUrl(tips,"/tips-og-raad/");

        allUrls.addAll(fetchUrl(billedeSerie,"/billedserier/"));
        allUrls.addAll(fetchUrl(boligreportage,"/boligreportager/"));
        allUrls.addAll(fetchUrl(guide,"/guides/"));
        allUrls.addAll(fetchUrl(artikel,"/artikler/"));

        return allUrls;
    }

    private LinkedList<String> fetchUrl(String url, String delimiter){
        ScrapeUrl scrape = new ScrapeUrl(url);
        return scrape.getAllUrls(delimiter);
    }

    public ArrayList<String> check404(){

        ArrayList<String> linksWith404 = new ArrayList<>();

        LinkedList<String> pages = getAllUrl();

        for (int i = 0; i < pages.size(); i++) {
            ScrapeUrl url = new ScrapeUrl(pages.get(i));
            LinkedList<String> urlsOnPage = url.getAllUrls("https://www.danskeboligarkitekter.dk/");

            for (int j = 0; j < urlsOnPage.size(); j++) {
                Connection connect;
                try {
                    connect = Jsoup.connect(urlsOnPage.get(j));

                    try {
                        Document document = connect.get();

                    } catch (IOException e) {
                        System.out.println("Located   " + pages.get(i));
                        System.out.println("  404:    " + urlsOnPage.get(j));


                        linksWith404.add(pages.get(i));
                    }

                } catch (IllegalArgumentException e){

                }


            }
        }

        return linksWith404;
    }




}
