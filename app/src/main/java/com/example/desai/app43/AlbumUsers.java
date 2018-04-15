package com.example.desai.app43;


import android.content.Context;

import java.io.*;
import java.util.ArrayList;

public class AlbumUsers implements Serializable {

    private ArrayList<User> users;

    private static final long serialVersionUID = 1L;


    static String userDir = System.getProperty("user.dir");
    public static String userListFile = userDir+"/users.dat";
    public static final String fileName = "photoalbum.ser";
    static File file = new File(userListFile);

    /**
     *
     */
    public AlbumUsers(){
        users = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     *
     * @param user
     */
    public void addUsers(User user){
        System.out.println("Album User ="+ user);
        users.add(user);
    }

    /**
     *
     * @param user
     */
    public void removeUsers(User user){

        users.remove(user);
    }


    /**
     *
     * @param givenUname
     * @return
     */
    public User getUserByUname(String givenUname){
        for (User u : users){
            if(givenUname.trim().equals(u.getUsernname())){
                return u;
            }
        }
        return null;
    }

    /**
     *
     * @param givenUname
     * @return
     */
    public boolean UserExists(String givenUname) {
        System.out.println("GivenUName = " + givenUname);
        for (User u : users) {
            if (givenUname.trim().equals(u.getUsernname())) {
                return true;
            }

        }
            return false;
    }

    /**
     *
     * @param givenUsername
     * @return
     */
    public boolean UsernameTaken(String givenUsername){
        for(User u : users){
            if(givenUsername.trim().equals(u.getUsernname())){
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static AlbumUsers readUsers() throws IOException,ClassNotFoundException{
        AlbumUsers usersavailable = null;
       file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
       ObjectInputStream ois = new ObjectInputStream(fis);
       usersavailable =(AlbumUsers) ois.readObject();
       ois.close();
        return usersavailable;
    }

    /**
     *
     * @param albumUsers
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void writeUsers(AlbumUsers albumUsers) throws IOException,ClassNotFoundException{
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(albumUsers);
        oos.close();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String userLisr = " ";
        if(users == null){
            return "No Users Exists";

        }
        else {
            for(User user : users){
                return userLisr + user.getUsernname() + "\n";
            }
        }
        return userLisr;
    }

    public static AlbumUsers loadFromDisk(Context context){
        AlbumUsers pa = null;
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pa = (AlbumUsers) ois.readObject();

//            if (pa.albums == null) {
//                pa.albums = new ArrayList<Album>();
//            }
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return pa;
    }

    public  void saveToDisk(Context context){
        ObjectOutputStream oos;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
