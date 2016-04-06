package com.zhitian.mybole.model;

import android.net.Uri;
import android.util.Log;

import com.zhitian.mybole.entity.ActivityInfo;
import com.zhitian.mybole.entity.GameInfo;
import com.zhitian.mybole.entity.ImageSetInfo;
import com.zhitian.mybole.entity.PrizeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiaosong on 16/3/22.
 */
public class ActivityFormModel {

    static private ActivityFormModel modelUnderEdit = null;

    private ActivityInfo activityInfo;

    private PrizeInfo prizeForBackup;
    private PrizeInfo prizeForEdit;

    private boolean isModified = false;

    public ActivityFormModel(){
        activityInfo = new ActivityInfo();

        modelUnderEdit = this;
    }

    public ActivityFormModel(ActivityInfo info){
        activityInfo = info;

        modelUnderEdit = this;
    }

    static public ActivityFormModel getModelForEdit(){
        return modelUnderEdit;
    }

    public void savePrizeUnderEditting(){
        if (prizeForEdit != null){
            addPrize(prizeForEdit);
        }

        prizeForBackup = null;
        prizeForEdit   = null;
    }

    public void rollback(){
        if (prizeForBackup != null){
            addPrize(prizeForBackup);
        }

        prizeForBackup = null;
        prizeForEdit   = null;
    }

    public void prepareForEdit(PrizeInfo selectedPrize){
        if(selectedPrize == null){
        //create an new prize
            prizeForBackup = null;
            prizeForEdit   = new PrizeInfo();

        }else {
        //modify an existing prize
            removePrize(selectedPrize);
            prizeForBackup = selectedPrize;

            try{
                prizeForEdit = (PrizeInfo)selectedPrize.clone();
            } catch (Exception e){

            }
        }
    }

    public String checkSanityOfPrize(){
        return prizeForEdit.checkSanity();
    }

    //---------------------------------------
    //operation on prize
    //---------------------------------------
    public void deleteImageInfo(ImageSetInfo info){
        prizeForEdit.getImgs().remove(info);
    }

    public void addPirzeImageByUriForPrize(Uri uri) {
        ImageSetInfo info = new ImageSetInfo();
        info.setUri(uri);

        if(prizeForEdit.getImgs() == null)
            prizeForEdit.setImgs(new ArrayList<ImageSetInfo>());

        prizeForEdit.getImgs().add(info);
    }

    public void setPrizeLevel(String prizeLevel) {
        prizeForEdit.setPrizeLevel(prizeLevel);
    }

    public void setPrizeName(String prizeName) {
        prizeForEdit.setName(prizeName);
    }

    public void setPrizeCount(String prizeCount) {
        prizeForEdit.setNumber(prizeCount);
    }

    public void setPrizeExpiredTime(String prizeExpiredTime) {
        prizeForEdit.setEndTime(prizeExpiredTime);
    }

    public List<ImageSetInfo> getPrizeImages(){
        if(prizeForEdit.getImgs() == null)
            prizeForEdit.setImgs(new ArrayList<ImageSetInfo>());

        return prizeForEdit.getImgs();
    }

    public String getPrizeLevel(){
        return prizeForEdit.getPrizeLevel();
    }

    public String getPrizeName() {
        return prizeForEdit.getName();
    }

    public String getPrizeCount() {
        return prizeForEdit.getNumber();
    }

    public String getPrizeExpiredTime() {
        return prizeForEdit.getEndTime();
    }
    //---------------------------------------
    //following are simple bean setter/getter
    //---------------------------------------
    public void setName(String name) {
        isModified = true;
        activityInfo.setName(name);
    }

    public void setRules(String rules) {
        isModified = true;
        activityInfo.setRules(rules);
    }

    public void setStartTime(String startTime) {
        isModified = true;
        activityInfo.setStartTime(startTime);
    }

    public void setEndTime(String endTime) {
        isModified = true;
        activityInfo.setEndTime(endTime);
    }

    public void setGame(GameInfo game) {
        isModified = true;
        activityInfo.setGame(game);
    }

    public void addPrize(PrizeInfo prize) {
        if (activityInfo.getPrizes() == null)
            activityInfo.setPrizes(new ArrayList<PrizeInfo>());

        activityInfo.getPrizes().add(prize);
    }

    public void removePrize(PrizeInfo prizeForRemove) {
        if (activityInfo.getPrizes() == null)
            return;

        boolean rlt = activityInfo.getPrizes().remove(prizeForRemove);

        Log.i("stony", rlt?"remove succ":"remove failed");
    }

    public String getName() {
        return activityInfo.getName();
    }

    public String getRules() {
        return activityInfo.getRules();
    }

    public String getStartTime() {
        return activityInfo.getStartTime();
    }

    public String getEndTime() {
        return activityInfo.getEndTime();
    }

    public GameInfo getGame() {
        return activityInfo.getGame();
    }

    public List<PrizeInfo> getPrizes() {
        if (activityInfo.getPrizes() == null)
            activityInfo.setPrizes(new ArrayList<PrizeInfo>());

        return activityInfo.getPrizes();
    }
}
