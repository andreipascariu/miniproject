package ro.andrei.miniproject.services;

import ro.andrei.miniproject.models.User;
import ro.andrei.miniproject.repository.UserRepository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

public class UserServices {

    private UserRepository userRepository = new UserRepository();

    public boolean createUser(User user){
        return userRepository.create(user);
    }

    public boolean addAdress(String address,Integer addressno, Integer userId){
        if(userRepository.checkMultipleAddresses(userId).equals("Yes")){
        return userRepository.addAddress(address,addressno,userId);}
        else if(userRepository.checkMultipleAddresses(userId).equals("No")){
            userRepository.createMultipleAddresses(userId);
            userRepository.addAddress(address,addressno,userId);
            return true;
        }
        return false;
    }

    public ArrayList<?> getAll(){
        return userRepository.getAll();
    }

    public Object getById(int id){
        return userRepository.getById(id);
    }

    public Object getByUsername(String username){ return userRepository.getByUsername(username);}

    public ArrayList<?> getAddresses(){return userRepository.getAddresses();}

    public boolean delete(int id){return userRepository.delete(id);}

    public boolean updateAll(int id,Object object){
        return userRepository.updateAll(id,object);
    }

    public boolean updateColumn(String columnName,String columnValue,int userID){
        return userRepository.updateColumn(columnName,columnValue,userID);
    }
    public Object getByColumnName(String columnName,String columnValue){return userRepository.getByColumnName(columnName,columnValue);}

    public boolean newProfileImageLink(int id,String imageLink) throws IOException {
        userRepository.newProfileImageLink(id,imageLink);
        saveImage(imageLink);
        return true;
    }


    public static void saveImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = url.getFile();
        String destName = "images//" + fileName.substring(fileName.lastIndexOf("/"));
        System.out.println(destName);

        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destName);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }



}
