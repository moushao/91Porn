package com.u9porn.parser;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.u9porn.data.model.BaseResult;
import com.u9porn.data.model.Mm99;
import com.u9porn.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * @author flymegoc
 * @date 2018/2/1
 */

public class Parse99Mm {

    private static final String TAG = Parse99Mm.class.getSimpleName();

    public static BaseResult<List<Mm99>> parse99MmList(String html, int page) {
        BaseResult<List<Mm99>> baseResult = new BaseResult<>();
        baseResult.setTotalPage(1);
        Logger.t(TAG).d(html);
        Document doc = Jsoup.parse(html);
        Element ul = doc.getElementById("piclist");
        Elements lis = ul.select("li");
        List<Mm99> mm99List = new ArrayList<>();
        for (Element li : lis) {
            Mm99 mm99 = new Mm99();
            Element a = li.selectFirst("dt").selectFirst("a");
            String contentUrl = "http://www.99mm.me" + a.attr("href");
            mm99.setContentUrl(contentUrl);

            int startIndex = contentUrl.lastIndexOf("/");
            int endIndex = contentUrl.lastIndexOf(".");
            String idStr = StringUtils.subString(contentUrl, startIndex + 1, endIndex);

            if (!TextUtils.isEmpty(idStr) && TextUtils.isDigitsOnly(idStr)) {
                mm99.setId(Integer.parseInt(idStr));
            } else {
                Logger.t(TAG).d(idStr);
            }

            Element img = a.selectFirst("img");
            String title = img.attr("alt");
            mm99.setTitle(title);
            String imgUrl = img.attr("src");
            HttpUrl httpUrl = HttpUrl.parse(imgUrl);
            if (httpUrl == null) {
                imgUrl = img.attr("data-img");
            }
            Logger.t(TAG).d("图片链接::" + imgUrl);
            mm99.setImgUrl(imgUrl);
            int imgWidth = Integer.parseInt(img.attr("width"));
            mm99.setImgWidth(imgWidth);

            mm99List.add(mm99);
        }

        if (page == 1) {
            Element pageElement = doc.getElementsByClass("all").first();
            if (pageElement != null) {
                String pageStr = pageElement.text().replace("...", "").trim();
                if (!TextUtils.isEmpty(pageStr) && TextUtils.isDigitsOnly(pageStr)) {
                    baseResult.setTotalPage(Integer.parseInt(pageStr));
                } else {
                    Logger.t(TAG).d(pageStr);
                }
            }
        }

        baseResult.setData(mm99List);
        return baseResult;
    }

    public static List<String> parse99MmImageList(String html) {

        Document doc = Jsoup.parse(html);

        Element elementBox = doc.getElementById("picbox");
        String imgUrl = elementBox.selectFirst("img").attr("src").trim();
        HttpUrl httpUrl = HttpUrl.parse(imgUrl);
        Element element = doc.body().select("script").first();
        String javaScript = element.toString();
        String data = StringUtils.subString(javaScript, javaScript.indexOf("[") + 1, javaScript.lastIndexOf(";") - 1);
        String[] dataArray = data.replace("\"", "").split(",");

        int imgIdArrayLength = dataArray.length - 6;

        String[] imgIdArray = new String[imgIdArrayLength];
        System.arraycopy(dataArray, 6, imgIdArray, 0, imgIdArrayLength);
        Logger.t(TAG).d(dataArray);
        Logger.t(TAG).d(imgIdArray);

        List<String> stringImageList = new ArrayList<>();
        String host;
        if (httpUrl == null) {
            host = "http://fj.kanmengmei.com/";
        } else {
            host = httpUrl.scheme() +"://"+ httpUrl.host();
        }

        for (int i = 0; i < imgIdArrayLength; i++) {
            String tmpImgUrl = host + "/" + dataArray[1] + (i + 1) + "-" + imgIdArray[i] + ".jpg";
            Logger.t(TAG).d(tmpImgUrl);
            stringImageList.add(tmpImgUrl);
        }
        return stringImageList;

    }


}
