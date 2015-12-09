package com.bizforo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * question:
 * 1. 查询的最后一页偶尔出现没有结果的情况，TODO 待改进
 */
public class Main {

    public static void main(String[] args) throws IOException {

        //avoid timeout
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(100000));

        String baseurl = "http://www.qgtong.com/so/?ChannelID=1001&keyword=";

        for (int i = 1; i < 100; i++) {
            String prefix = String.valueOf(i);
            if (prefix.length() == 1) prefix = "0" + prefix;
            parseFirstClass(baseurl + prefix);
        }

    }

    private static void parseFirstClass(String url) throws IOException {
        System.out.println(url);
        Document doc = Jsoup.connect(url).get();

        Element theTable = doc.select("div.result table").first();
        Utils.parseTable(theTable);

        Elements links = doc.select("div.show_page a");
        //System.out.println( links );

        //remove duplicate
        Set<String> linkSet = new TreeSet<String>();
        for (Element link : links) {
            linkSet.add(link.attr("abs:href"));
        }

        for (String link : linkSet) {
            System.out.println(link);
            Document document = Jsoup.connect(link).get();
            Element element = document.select("div.result table").first();
            Utils.parseTable(element);
        }
    }

}
