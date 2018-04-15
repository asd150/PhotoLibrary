package com.example.desai.app43;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable

{

    private String usernname ;
    //user has list of album
    private ArrayList<Album> albums;
    //user is following other user
    private ArrayList<User> following;

    /**
     *
     */
    public User() {
        albums = new ArrayList<>();
        following =  new ArrayList<>();
    }

    /**
     *
     * @param username
     */
    public User(String username){
        albums = new ArrayList<>();
        following = new ArrayList<>();
        this.usernname=username;
}

    /**
     *
     * @return
     */
    public ArrayList<User> getFollowing() {
        return following;
    }

    /**
     *
     * @return
     */
    public String getUsernname() {
        return usernname;
    }

    /**
     *
     * @param usernname
     */
    public void setUsernname(String usernname) {
        this.usernname = usernname;
    }

    /**
     *
     * @param albums
     */
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     *
     * @param alb
     */
    public void addAlbum(Album alb){
        this.albums.add(alb);
    }

    /**
     *
     * @return
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     *
     * @param following
     */
    public void setFollowing(ArrayList<User> following) {

        this.following = following;
    }

    /**
     *
     * @param album
     */
    public void removeAlbum(Album album){
        albums.remove(album);
    }

    /**
     *
     * @param name
     * @return
     */
    public Album getAlbumName(String name){
        for(Album ab : albums){
            if(name.equals(ab.getAlbumName())){
                return ab;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getUsernname();
    }
}
