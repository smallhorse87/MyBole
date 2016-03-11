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
public class RegionPickerBuilder {
    static private ArrayList<String> allProvinces;
    static private ArrayList<ArrayList<String>> allCities;
    static private ArrayList<ArrayList<ArrayList<String>>> allDistricts;

    static private ArrayList<ArrayList<ArrayList<String>>> allDistrictIds;

    public static void constructRegions(){

        if (allProvinces != null && allCities != null && allDistricts != null)  return;

        InputStream is = AppContext.instance().getResources().openRawResource(R.raw.regions);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        allProvinces = new ArrayList<String>();
        allCities = new ArrayList<ArrayList<String>>();
        allDistricts = new ArrayList<ArrayList<ArrayList<String>>>();

        allDistrictIds = new ArrayList<ArrayList<ArrayList<String>>>();

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
                ArrayList<ArrayList<String>> districtsOfProvince   = new ArrayList<ArrayList<String>>();
                ArrayList<ArrayList<String>> districtIdsOfProvince = new ArrayList<ArrayList<String>>();

                for (int idxOfCity = 0; idxOfCity < citiesNodeList.getLength(); idxOfCity++){
                    Node cityNode = citiesNodeList.item(idxOfCity);
                    String cityName = cityNode.getAttributes().getNamedItem("name").getNodeValue();

                    //区
                    NodeList districtsNodeList = cityNode.getChildNodes();

                    ArrayList<String> districtsOfCity   = new ArrayList<String>();
                    ArrayList<String> districtIdsOfCity = new ArrayList<String>();

                    for(int idxOfDistrict = 0; idxOfDistrict < districtsNodeList.getLength(); idxOfDistrict++){
                        Node districtNode = districtsNodeList.item(idxOfDistrict);
                        String districtName = districtNode.getAttributes().getNamedItem("name").getNodeValue();
                        String districtId   = districtNode.getAttributes().getNamedItem("region").getNodeValue();

                        districtsOfCity.add(districtName);
                        districtIdsOfCity.add(districtId);
                    }

                    citiesOfProvince.add(cityName);
                    districtsOfProvince.add(districtsOfCity);
                    districtIdsOfProvince.add(districtIdsOfCity);
                }

                allProvinces.add(provinceName);
                allCities.add(citiesOfProvince);
                allDistricts.add(districtsOfProvince);
                allDistrictIds.add(districtIdsOfProvince);

            }

        } catch (Exception e) {

        }
    }

    public static OptionsPickerView getPicker(Context context, final RegionPickerListener listener){

        constructRegions();

        //选项选择器
        OptionsPickerView regionPicker = new OptionsPickerView(context);

        //三级联动效果
        regionPicker.setPicker(allProvinces, allCities, allDistricts, true);

        //设置选择的三级单位
        regionPicker.setTitle("选择城市");
        regionPicker.setCyclic(true, false, false);

        regionPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int provinceIdx, int cityIdx, int districtIdx) {

                listener.onRegionSelect(
                        getDistrctIdIdxAt(provinceIdx, cityIdx, districtIdx),
                        getProvinceNameIdxAt(provinceIdx),
                        getCityNameIdxAt(provinceIdx, cityIdx),
                        getDistrctNameIdxAt(provinceIdx, cityIdx, districtIdx));
            }
        });

        return regionPicker;
    }

    public static String getDistrctNameIdxAt(int provinceIdx, int cityIdx, int districtIdx)
    {
        ArrayList<ArrayList<String>> districtsOfProvince = allDistricts.get(provinceIdx);
        ArrayList<String> districtsOfCity = districtsOfProvince.get(cityIdx);

        return districtsOfCity.get(districtIdx);
    }

    public static String getCityNameIdxAt(int provinceIdx, int cityIdx)
    {
        ArrayList<String> citiesOfProvince = allCities.get(provinceIdx);

        return citiesOfProvince.get(cityIdx);

    }

    public static String getProvinceNameIdxAt(int provinceIdx)
    {
        return allProvinces.get(provinceIdx);

    }

    public static String getDistrctIdIdxAt(int provinceIdx, int cityIdx, int districtIdx)
    {
        ArrayList<ArrayList<String>> districtIdsOfProvince = allDistrictIds.get(provinceIdx);
        ArrayList<String> districtIdsOfCity = districtIdsOfProvince.get(cityIdx);

        return districtIdsOfCity.get(districtIdx);

    }

    public static void setDefaulSelection(String distrctId, OptionsPickerView regionPicker){

        regionPicker.setSelectOptions(0,0,0);

        for(int idxOfProvince = 0; idxOfProvince < allDistrictIds.size(); idxOfProvince ++){

            ArrayList<ArrayList<String>> districtIdsOfProvince = allDistrictIds.get(idxOfProvince);

            for(int idxOfCity = 0; idxOfCity < districtIdsOfProvince.size(); idxOfCity ++) {
                ArrayList<String> districtIdsOfCity = districtIdsOfProvince.get(idxOfCity);

                for(int idxOfDistrict = 0; idxOfDistrict < districtIdsOfCity.size(); idxOfDistrict ++){
                    String districtIdStr =  districtIdsOfCity.get(idxOfDistrict);

                    if (districtIdStr == distrctId){
                        regionPicker.setSelectOptions(idxOfProvince, idxOfCity, idxOfDistrict);
                        return;
                    }
                }
            }
        }

    }

    public interface RegionPickerListener {
        void onRegionSelect(String districtId, String province, String city, String district);
    }

}
