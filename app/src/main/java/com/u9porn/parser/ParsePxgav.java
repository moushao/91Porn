package com.u9porn.parser;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.u9porn.data.model.BaseResult;
import com.u9porn.data.model.pxgav.PxgavModel;
import com.u9porn.data.model.pxgav.PxgavResultWithBlockId;
import com.u9porn.data.model.pxgav.PxgavVideoParserJsonResult;
import com.u9porn.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flymegoc
 * @date 2018/1/22
 */

public class ParsePxgav {
    private static final String TAG = ParsePxgav.class.getSimpleName();

    /**
     * @param html 原网页
     * @return json===
     */
    public static BaseResult<PxgavVideoParserJsonResult> parserVideoUrl(String html) {
        BaseResult<PxgavVideoParserJsonResult> baseResult = new BaseResult<>();
        Document document = Jsoup.parse(html);
        Element videoWrapper = document.getElementsByClass("td-post-content td-pb-padding-side").first();
        String videoHtml = videoWrapper.html();
        Logger.t(TAG).d(videoHtml);
        int index = videoHtml.indexOf("setup") + 6;
        int endIndexV = videoHtml.indexOf(");");
        String videoUrl = videoHtml.substring(index, endIndexV);
        Logger.t(TAG).d(videoUrl);

        PxgavVideoParserJsonResult pxgavVideoParserJsonResult = new Gson().fromJson(videoUrl, PxgavVideoParserJsonResult.class);

        Elements items = document.getElementsByClass("td-block-span12");
        List<PxgavModel> pxgavModelList = new ArrayList<>();
        for (Element element : items) {
            PxgavModel pxgavModel = new PxgavModel();
            Element a = element.selectFirst("a");
            String title = a.attr("title");
            pxgavModel.setTitle(title);
            String contentUrl = a.attr("href");
            pxgavModel.setContentUrl(contentUrl);
            Element img = element.selectFirst("img");
            String imgUrl = img.attr("src");
            int beginIndex = imgUrl.lastIndexOf("/");
            int endIndex = imgUrl.indexOf("-");
            String bigImg = StringUtils.subString(imgUrl, 0, endIndex);
            if (TextUtils.isEmpty(bigImg)) {
                pxgavModel.setImgUrl(imgUrl);
            } else {
                pxgavModel.setImgUrl(bigImg + ".jpg");
            }
            String pId = StringUtils.subString(imgUrl, beginIndex + 1, endIndex);
           // Logger.t(TAG).d(pId);
            pxgavModel.setpId(pId);

            int imgWidth = Integer.parseInt(img.attr("width"));
            pxgavModel.setImgWidth(imgWidth);
            int imgHeight = Integer.parseInt(img.attr("height"));
            pxgavModel.setImgHeight(imgHeight);
            pxgavModelList.add(pxgavModel);
        }
        pxgavVideoParserJsonResult.setPxgavModelList(pxgavModelList);
        baseResult.setData(pxgavVideoParserJsonResult);
        return baseResult;
    }

    public static BaseResult<PxgavResultWithBlockId> videoList(String html, boolean isLoadMoreData) {
        BaseResult<PxgavResultWithBlockId> baseResult = new BaseResult<>();

        PxgavResultWithBlockId pxgavResultWithBlockId=new PxgavResultWithBlockId();

        baseResult.setTotalPage(1);

        Document doc = Jsoup.parse(html);
        Elements items = doc.getElementsByClass("td-block-span4");
        List<PxgavModel> pxgavModelList = new ArrayList<>();
        for (Element element : items) {
            PxgavModel pxgavModel = new PxgavModel();
            Element a = element.selectFirst("a");
            String title = a.attr("title");
            pxgavModel.setTitle(title);
            String contentUrl = a.attr("href");
            pxgavModel.setContentUrl(contentUrl);
            Element img = element.selectFirst("img");
            String imgUrl = img.attr("src");
            int beginIndex = imgUrl.lastIndexOf("/");
            int endIndex = imgUrl.lastIndexOf("-");
            String bigImg = StringUtils.subString(imgUrl, 0, endIndex);
            if (TextUtils.isEmpty(bigImg)) {
                pxgavModel.setImgUrl(imgUrl);
            } else {
                pxgavModel.setImgUrl(bigImg + ".jpg");
            }
            String pId = StringUtils.subString(imgUrl, beginIndex + 1, endIndex);
            //Logger.t(TAG).d(pId);
            pxgavModel.setpId(pId);

            int imgWidth = Integer.parseInt(img.attr("width"));
            pxgavModel.setImgWidth(imgWidth);
            int imgHeight = Integer.parseInt(img.attr("height"));
            pxgavModel.setImgHeight(imgHeight);
            pxgavModelList.add(pxgavModel);
        }
        pxgavResultWithBlockId.setPxgavModelList(pxgavModelList);
        if (isLoadMoreData){
            baseResult.setData(pxgavResultWithBlockId);
            return baseResult;
        }
        //解析加载更多需要的数据
        Elements elements = doc.getElementsByClass("wpb_wrapper");
        String[] data = elements.last().getElementsByTag("script").html().split(";");
        String label = ".id = \"";
        for (String dat : data) {
            if (dat.contains(label)) {
                int startIndex = dat.indexOf(label);
                Logger.t(TAG).d(dat);
                try {
                    String blockId=dat.substring(startIndex + label.length()).replace("\"", "");
                    pxgavResultWithBlockId.setBlockId(blockId);
                    Logger.t(TAG).d("blockId数据：" + blockId);
                } catch (Exception e) {
                    Logger.t(TAG).e("无法获取blockId");
                }

                break;
            }
        }
        baseResult.setData(pxgavResultWithBlockId);
        return baseResult;
    }

    public static BaseResult<List<PxgavModel>> moreVideoList(String html) {
        BaseResult<List<PxgavModel>> baseResult = new BaseResult<>();
        baseResult.setTotalPage(1);

        Document doc = Jsoup.parse(html);
        Elements items = doc.getElementsByClass("td-block-span4");
        List<PxgavModel> pxgavModelList = new ArrayList<>();
        for (Element element : items) {
            PxgavModel pxgavModel = new PxgavModel();
            Element a = element.selectFirst("a");
            String title = a.attr("title");
            pxgavModel.setTitle(title);
            String contentUrl = a.attr("href");
            pxgavModel.setContentUrl(contentUrl);
            Element img = element.selectFirst("img");
            String imgUrl = img.attr("src");
            int beginIndex = imgUrl.lastIndexOf("/");
            int endIndex = imgUrl.lastIndexOf("-");
            String bigImg = StringUtils.subString(imgUrl, 0, endIndex);
            if (TextUtils.isEmpty(bigImg)) {
                pxgavModel.setImgUrl(imgUrl);
            } else {
                pxgavModel.setImgUrl(bigImg + ".jpg");
            }
            String pId = StringUtils.subString(imgUrl, beginIndex + 1, endIndex);
            Logger.t(TAG).d(pId);
            pxgavModel.setpId(pId);

            int imgWidth = Integer.parseInt(img.attr("width"));
            pxgavModel.setImgWidth(imgWidth);
            int imgHeight = Integer.parseInt(img.attr("height"));
            pxgavModel.setImgHeight(imgHeight);
            pxgavModelList.add(pxgavModel);
        }
        baseResult.setData(pxgavModelList);
        return baseResult;
    }
}
