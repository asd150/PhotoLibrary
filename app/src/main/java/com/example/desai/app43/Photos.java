package com.example.desai.app43;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Photos implements Serializable {



    private ArrayList<Tag> tags;
    private Calendar date_time;
    private File photoFile;
    private List<Album> parentAlb;
    private String caption;
    transient Bitmap image;
    private ArrayList<String> personlist =new ArrayList<>();
    private ArrayList<String> locationlist = new ArrayList<>();
     private Map<String, ArrayList<String>> tagsHashTable = new HashMap<>();

    /**
     *
     * @param photoFile
     */
    public Photos(File photoFile){
        parentAlb = new ArrayList<Album>();
        tags = new ArrayList<>();
        this.photoFile = photoFile;
        date_time = Calendar.getInstance();
        date_time.set(Calendar.MILLISECOND,0);
    }

    /**
     *
     * @return
     */
    public File getPhotoFile() {
        return photoFile;
    }

    /**
     *
     * @param photoFile
     */

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    /**
     *
     * @return
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     */
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    /**
     *
     * @param tag
     */
    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    /**
     *
     * @param tag
     */
    public void addTag(Tag tag){
        tags.add(tag);
    }

    /**
     *
     * @param tag
     * @return
     */
    public boolean tagexist(Tag tag){
        for(Tag tg : tags){
            if(tg.equals(tag)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Calendar getDate_time() {
        return date_time;
    }

    /**
     *
     * @param date_time
     */
    public void setDate_time(Calendar date_time) {
        this.date_time = date_time;
    }

    /**
     *
     * @return
     */
    public List<Album> getParentAlb() {
        return parentAlb;
    }

    /**
     *
     * @param parentAlb
     */
    public void setParentAlb(List<Album> parentAlb) {
        this.parentAlb = parentAlb;
    }

    /**
     *
     * @return
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     *
     * @param alb
     */
    public void addParentAlb(Album alb){
        parentAlb.add(alb);
    }

    /**
     *
     * @param alb
     */
    public void removeParentAlb(Album alb){
        parentAlb.remove(alb);

    }

    /**
     *
     * @param alb
     * @return
     */
    public boolean existingAlb(Album alb){
        if(parentAlb.contains(alb)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public String getDate(){
        String[] arrTime = date_time.getTime().toString().split("\\s+");
        return arrTime[0] + "," + arrTime[1] + "," + arrTime[2];
    }
    public Bitmap getImage() {
        return image;
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int b;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        while((b = ois.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        image = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if(image != null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
            byte bitmapBytes[] = byteStream.toByteArray();
            oos.write(bitmapBytes, 0, bitmapBytes.length);
        }
    }


    public void personArray(String person){
        personlist.add(person);



    }
    public void locationArray(String location){
        locationlist.add(location);

    }

    public ArrayList<String> getLocationlist() {
        return locationlist;
    }

    public ArrayList<String> getPersonlist() {
        return personlist;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
//    public String[] getTagsAsString(){
//
//        String[] tagsAsSingleString = new String[tagsHashTable.size()];
//
//        tagsAsSingleString = (String[]) tagsHashTable.values().toArray();
//
//        return tagsAsSingleString;
//    }
//
//    public String[][] getTagsWithKeyValues(){
//
//        int tagCount = 0;
//
//        ArrayList<String> loc = tagsHashTable.get("location");
//        ArrayList<String> per = tagsHashTable.get("person");
//
//        if (loc != null) tagCount += loc.size();
//        if (per != null) tagCount += per.size();
//
//        String[][] tagsArray = new String[2][tagCount];
//
//        int j = 0;
//
//        if (loc != null) {
//            for(int i = 0; i < loc.size(); i++) { tagsArray[0][j] = "location";
//                tagsArray[1][j] = loc.get(i);
//                j++;
//            }
//        }
//
//        if (per != null) {
//            for(int i = 0; i < per.size(); i++) { tagsArray[0][j] = "person";
//                tagsArray[1][j] = per.get(i);
//                j++;
//            }
//        }
//
//        return tagsArray;
//
//    }
//    public void removeTag(String key, String value){
//        getListWithKey(key).remove(value);
//    }
//
//    public ArrayList<String> getListWithKey(String key) {
//        return tagsHashTable.get(key);
//    }
//
//    public void addTag(String key, String value){
//        if (tagsHashTable.containsKey(key)){
//            if (tagsHashTable.get(key).contains(value)) {
//                return;
//            }
//            tagsHashTable.get(key).add(value);
//        } else {
//            ArrayList<String> arrList = new ArrayList<String>();
//            arrList.add(value);
//            tagsHashTable.put(key, arrList);
//
//        }
//    }
//    public ArrayList<String> personTags(){
//        ArrayList<String> person = tagsHashTable.get("person");
//        return person;
//    }
//    public ArrayList<String> locationTags(){
//        ArrayList<String> location = tagsHashTable.get("location");
//        return location;
//    }
}
