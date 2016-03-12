package com.zhitian.mybole.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;
import com.zhitian.mybole.api.BoleApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.msebera.android.httpclient.Header;
/**
 * Created by chenxiaosong on 16/3/12.
 */
public class RegionXmlUtil {

    //获取是否需要进行下载更新：分析updatetime
    //获取xml文件的来源：1，来自raw data 2，来自应用文件夹下
    //如果有需要就进行下载，
    private static final String REGION_XML_NAME = "regions.xml";

    public static InputStream readXml(){

    try {

        File file = new File(xmlFilePath());

        if (file.exists())
            return new FileInputStream(xmlFilePath());
        else
            return AppContext.instance().getResources().openRawResource(R.raw.regions);

    } catch (Exception e){

    }
        return null;
    }

    private static String xmlFilePath(){
        return AppContext.instance().getCacheDir() + "/" + REGION_XML_NAME;
//        return "/sdcard/" + REGION_XML_NAME;
    }

    public static boolean doesUpdateNeeded(String updtimeStr){
        InputStream is = readXml();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            Element root = document.getDocumentElement();
            //updateAt
            int localFileUpdateTime = Integer.parseInt(root.getAttributes().getNamedItem("updateAt").getNodeValue());
            int remoteFileUpdateTime = Integer.parseInt(updtimeStr);

            if (remoteFileUpdateTime > localFileUpdateTime)
                return true;
            else
                return false;

        }  catch (Exception e){
            Log.e("what is wrong", e.toString());
        }

        return false;
    }

    public static void downloadRegionXml(String downloadUrl){
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg", "application/octet-stream", "application/xml"};

        BoleApi.downloadRegionXml(downloadUrl,new BinaryHttpResponseHandler(allowedContentTypes) {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] binaryData) {
                String tempPath = xmlFilePath();

                File file = new File(tempPath);

                try {
                    if (file.exists())
                        file.delete();

                    file.createNewFile();

                    OutputStream stream = new FileOutputStream(file);
                    stream.write(binaryData);
                    stream.close();

                    Toast.makeText(AppContext.instance(), "下载成功", Toast.LENGTH_LONG).show();

                } catch (IOException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] binaryData, Throwable error) {
                Toast.makeText(AppContext.instance(), "下载失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                Log.e("下载 Progress>>>>>", bytesWritten + " / " + totalSize);
            }

            @Override
            public void onRetry(int retryNo) {

            }
        });
    }
}
