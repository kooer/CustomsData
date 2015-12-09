package com.bizforo;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by shafei on 15-12-9.
 */
public class Utils {

    public static int parseTable(Element theTable) {
        if (theTable == null) return 0;

        Elements trs = theTable.select("tr");

        for (int i = 1; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Element tdNumber = tr.select("td").first();//税则号列
            Element tdGoodsName = tdNumber.nextElementSibling();//货品名称（进出口税则）
            Element tdItemName = tdGoodsName.nextElementSibling();//商品名称及备注（海关通关系统综合分类表）
            Element tdFavoredRate = tdItemName.nextElementSibling();//最惠国税率
            Element tdNormalRate = tdFavoredRate.nextElementSibling();//普通税率
            Element tdValueAddedRate = tdNormalRate.nextElementSibling();//增值税率
            Element tdUnit = tdValueAddedRate.nextElementSibling();//计量单位
            Element tdSuperVisionCond = tdUnit.nextElementSibling();//监管条件


            System.out.print(pureNumberDigit(tdNumber.text()));
            System.out.print("\t");
            System.out.print(tdGoodsName.text());
            System.out.print("\t");
            System.out.print(tdItemName.text());
            System.out.print("\t");
            System.out.print(tdFavoredRate.text());
            System.out.print("\t");
            System.out.print(tdNormalRate.text());
            System.out.print("\t");
            System.out.print(tdValueAddedRate.text());
            System.out.print("\t");
            System.out.print(tdUnit.text());
            System.out.print("\t");
            System.out.print(tdSuperVisionCond.text());
            System.out.println();
        }

        return trs.size() - 1;
    }

    public static String pureNumberDigit(String s) {
        if (s == null) return null;
        AtomicReference<StringBuffer> sb = new AtomicReference<StringBuffer>(new StringBuffer());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') sb.get().append(ch);
        }
        return sb.get().toString();
    }
}
