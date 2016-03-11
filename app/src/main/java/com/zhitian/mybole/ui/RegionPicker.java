package com.zhitian.mybole.ui;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhitian.mybole.AppContext;
import com.zhitian.mybole.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by chenxiaosong on 16/3/11.
 */
public class RegionPicker {

    public static OptionsPickerView BuildRegionPicker(Context context, OptionsPickerView.OnOptionsSelectListener listener){
        InputStream is = AppContext.instance().getResources().openRawResource(R.raw.regions);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        ArrayList<String> allProvinces = new ArrayList<String>();
        ArrayList<ArrayList<String>> allCities = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<ArrayList<String>>> allDistricts = new ArrayList<ArrayList<ArrayList<String>>>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            Element root = document.getDocumentElement();
            NodeList provincesNodeList = root.getChildNodes();

            //省
            for(int idxOfProvince = 0; idxOfProvince < provincesNodeList.getLength(); idxOfProvince++){
                Node provinceNode = provincesNodeList.item(idxOfProvince);
                String provinceName = provinceNode.getAttributes().getNamedItem("name").getNodeValue();

                //市
                NodeList citiesNodeList = provinceNode.getChildNodes();

                ArrayList<String>            citiesOfProvince = new ArrayList<String>();
                ArrayList<ArrayList<String>> districtsOfProvince = new ArrayList<ArrayList<String>>();

                for (int idxOfCity = 0; idxOfCity < citiesNodeList.getLength(); idxOfCity++){
                    Node cityNode = citiesNodeList.item(idxOfCity);
                    String cityName = cityNode.getAttributes().getNamedItem("name").getNodeValue();

                    //区
                    NodeList districtsNodeList = cityNode.getChildNodes();

                    ArrayList<String> districtsOfCity = new ArrayList<String>();

                    for(int idxOfDistrict = 0; idxOfDistrict < districtsNodeList.getLength(); idxOfDistrict++){
                        Node districtNode = districtsNodeList.item(idxOfDistrict);
                        String districtName = districtNode.getAttributes().getNamedItem("name").getNodeValue();

                        districtsOfCity.add(districtName);
                    }

                    citiesOfProvince.add(cityName);
                    districtsOfProvince.add(districtsOfCity);
                }

                allProvinces.add(provinceName);
                allCities.add(citiesOfProvince);
                allDistricts.add(districtsOfProvince);

            }

        } catch (Exception e) {

        }

        //选项选择器
        OptionsPickerView regionPicker = new OptionsPickerView(context);

        //三级联动效果
        regionPicker.setPicker(allProvinces, allCities, allDistricts, true);

        //设置选择的三级单位
        regionPicker.setTitle("选择城市");
        regionPicker.setCyclic(true, false, false);

        //设置默认选中的三级项目
        //监听确定选择按钮
        regionPicker.setSelectOptions(0, 0, 0);

        regionPicker.setOnoptionsSelectListener(listener);

//        regionPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//            }
//        });

        return regionPicker;
    }

}
