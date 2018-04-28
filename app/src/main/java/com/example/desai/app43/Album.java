package com.example.desai.app43;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Album implements Serializable {


    private String albumName;
    private List<Photos> listofphotos = null;
    private int sizes =0;
    private double dates;
    private Photos old;
    private Photos early;


    /**
     *
     */
    public Album(){
        listofphotos = new ArrayList<Photos>();
    }

    /**
     *
     * @param albumName
     */
    public  Album(String albumName){
        this.albumName = albumName;
        listofphotos = new ArrayList<Photos>();
    }


    /**
     *
     * @return
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     *
     * @param albumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     *
     * @return
     */
    public List<Photos> getListofphotos() {
        return listofphotos;
    }

    /**
     *
     * @param photos
     */
    public void setListofphotos(List<Photos> photos){
        listofphotos = photos;
        sizes = photos.size();


        this.set_early(this.listofphotos.get(0));
        this.set_old(this.listofphotos.get(this.sizes-1));

    }

    /**
     *
     * @param photos
     */
    public void addPhoto(Photos photos){
        this.listofphotos.add(photos);
        sizes++;




  //      this.set_early(this.listofphotos.get(0));
//        this.set_old(this.listofphotos.get(this.sizes-1));
    }

    /**
     *
     * @param photos
     */
    public void removePhoto(Photos photos){
        this.listofphotos.remove(photos);
        sizes--;


    }

    /**
     *
     * @param photo
     * @return
     */
    public boolean photoExists(Photos photo){
        return listofphotos.contains(photo);
    }

    /**
     *
     * @param early
     */
    public void set_early(Photos early){
        if(early!=null){
            this.early = early;
        }
    }

    /**
     *
     * @param old
     */
    public void set_old(Photos old){
        if(old!=null){
            this.old = old;
        }
    }

    /**
     *
     * @return
     */
    public Photos getEarly(){
        return early;
    }

    /**
     *
     * @return
     */
    public Photos getOld() {
        return old;
    }

    /**
     *
     * @param p
     * @return
     */
    public Photos prevPhoto(Photos p){
        if(p==null){
            return null;

        }
        if(sizes<2){
            return p;
        }

        for(Photos photos : listofphotos){
            if(p.getPhotoFile().equals(photos.getPhotoFile())){
                if(listofphotos.indexOf(photos)!=0){
                    return listofphotos.get(listofphotos.indexOf(photos) -1);
                }
            }
        }
        return p;
    }

    /**
     *
     * @param p
     * @return
     */
    public Photos nextPhoto(Photos p){
        if(p==null){
            return null;
        }
        if(sizes<2){
            return p;
        }

        for(Photos photos : listofphotos){
            if(p.getPhotoFile().equals(photos.getPhotoFile())){
                if(listofphotos.indexOf(photos)!= listofphotos.size()-1){
                    return listofphotos.get(listofphotos.indexOf(photos)+1);
                }
            }
        }
        return p;

    }


    /**
     *
     */


    /**
     *
     * @return
     */
    public String getDates(){
        if(this.sizes>0){
            return this.getOld() + " - " + this.getEarly();
        }
        return "Empty Album";
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getAlbumName();
    }

    public void setDates(double dates){
        this.dates=dates;
    }

}
