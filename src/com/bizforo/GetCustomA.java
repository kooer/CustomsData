package com.bizforo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GetCustomA {

    public static void main(String[] args) throws IOException {

        Map digital = new HashMap();
        digital.put("一", "1");
        digital.put("二", "2");
        digital.put("三", "3");
        digital.put("四", "4");
        digital.put("五", "5");
        digital.put("六", "6");
        digital.put("七", "7");
        digital.put("八", "8");
        digital.put("九", "9");
        digital.put("十", "0");

        String rootUrl = "http://www.qgtong.com/hgsz/";
        Document doc = Jsoup.connect(rootUrl).get();
        Map<Integer,String> map = new TreeMap();
        doc.select("a").forEach(
                a->{
                    //System.out.println(a);
                    String text = a.text();
                    if ( text.matches("第.+章.+") && a.attr("href").contains("/hgsz/ShowArticle.asp") ) {
                        String href = a.attr("href");
                        if ( href.startsWith("/hgsz")) {
                            href = "http://www.qgtong.com" + href;
                        }
                        String k = text.substring(1,text.indexOf("章"));
                        String newKey = "";
                        for ( int i = 0; i < k.length(); i++) {
                            String o = (String)digital.get(String.valueOf(k.charAt(i)));
                            if ( o.equals("0")) {
                                if (i == 0 ) {
                                    if ( i == k.length()-1 ) {
                                        newKey += "10";
                                    } else {
                                        newKey += "1";
                                    }
                                } else if (i == k.length()-1){
                                    newKey += "0";
                                }
                            } else {
                                newKey += o;
                            }
                        }

                        map.put(Integer.valueOf(newKey), href);
                    }
                }
        );
        map.forEach(
                (k, v) -> {
                    try {

                        Document doc1 = Jsoup.connect(v).get();

                        Element theTable = doc1.select("#TagC61").first();

                        Utils.parseTable(theTable);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        );



    }

}
