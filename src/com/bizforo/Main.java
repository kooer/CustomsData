package com.bizforo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * question:
 * 1. 查询的最后一页偶尔出现没有结果的情况，TODO 待改进
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String url = "http://www.qgtong.com/so/?ChannelID=1001&keyword=01";


        parseFirstClass(url);


    }

    private static void parseFirstClass(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        parseTable(doc);

        Elements links = doc.select("div.show_page a");
        //System.out.println( links );

        //remove duplicate
        Set<String> linkSet = new TreeSet<String>();
        for (Element link : links) {
            linkSet.add(link.attr("abs:href"));
        }
//        System.out.println(linkSet);

        for (String link : linkSet) {
            Document document = Jsoup.connect(link).get();
            parseTable(document);
        }
    }

    private static void parseTable(Document doc) {
        Element theTable = doc.select("div.result table").first();
        if ( theTable == null ) return;
//        System.out.println( theTable );

        Elements trs = theTable.select("tr");

        for (int i = 1; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Element tdNumber = tr.select("td").first();
            Element tdGoodsName = tdNumber.nextElementSibling();
            Element tdItemName = tdGoodsName.nextElementSibling();
            Element tdFavoredRate = tdItemName.nextElementSibling();
            Element tdNormalRate = tdFavoredRate.nextElementSibling();
            Element tdValueAddedRate = tdNormalRate.nextElementSibling();
            Element tdUnit = tdValueAddedRate.nextElementSibling();
            Element tdSuperVisionCond = tdUnit.nextElementSibling();

            System.out.print( pureNumberDigit(tdNumber.text()) );
            System.out.print( "\t" ); System.out.print( tdItemName.text() );
            System.out.print( "\t" ); System.out.print( tdSuperVisionCond.text() );
            System.out.println();
        }
    }

    public static String pureNumberDigit(String s) {
        if ( s == null ) return null;
        AtomicReference<StringBuffer> sb = new AtomicReference<StringBuffer>(new StringBuffer());
        for ( int i = 0 ; i < s.length() ; i++ ) {
            char ch = s.charAt(i);
            if ( ch >= '0' && ch <= '9') sb.get().append(ch);
        }
        return sb.get().toString();
    }
}
