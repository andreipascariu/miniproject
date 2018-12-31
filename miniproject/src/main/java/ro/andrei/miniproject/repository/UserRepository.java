package ro.andrei.miniproject.repository;

import ro.andrei.miniproject.models.User;
import ro.andrei.miniproject.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserRepository implements IRepository{

    @Override
    public boolean create(Object object) {
        User u = (User) object;
        Connection con = DBConnection.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(username,name,dateofbirth,phonenumber,email,mainaddress,profileimage) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getName());
            ps.setString(3,u.getDateofbirth());
            ps.setString(4,u.getPhonenumber());
            ps.setString(5,u.getEmail());
            ps.setString(6,u.getMainaddress());
            ps.setString(7,u.getProfileImage());
            ps.executeUpdate();
            return true;


        }catch(Exception e){
            e.printStackTrace();
            System.out.println(u.getId());
        }


        return false;
    }



    @Override
    public boolean delete(int id) {
        Connection con = DBConnection.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override //get all users
    public ArrayList<?> getAll() {
        Connection con = DBConnection.getConnection();
        ArrayList<User> users = new ArrayList<User>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDateofbirth(rs.getString(4));
                user.setPhonenumber(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setMainaddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                users.add(user);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<?> getAddresses(){
        Connection con = DBConnection.getConnection();
        ArrayList<Object> addresses = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM addresses");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ArrayList<String>addresses2 = new ArrayList<String>();
                addresses2.add(rs.getString(1));
                addresses2.add(rs.getString(2));
                addresses2.add(rs.getString(3));
                addresses2.add(rs.getString(4));
                addresses2.add(rs.getString(5));
                addresses2.add(rs.getString(6));
                addresses2.add(rs.getString(7));
                addresses2.add(rs.getString(8));
                addresses2.add(rs.getString(9));
                addresses2.add(rs.getString(10));
                addresses2.add(rs.getString(11));
                addresses.add(addresses2);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return addresses;

    }

    public boolean createMultipleAddresses(int id){
        Connection con = DBConnection.getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO addresses(userID) VALUES(?)");
            ps.setInt(1,id);
            ps.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement("UPDATE users SET multiple_addresses = ? WHERE id = ? ");
            ps2.setInt(1,1);
            ps2.setInt(2,id);
            ps2.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String checkMultipleAddresses(int id){ //checks if the user has a multiple_address line
        Connection con = DBConnection.getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT multiple_addresses FROM users WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            if(rs.getInt(1)==0){
                return "No";
            }else if (rs.getInt(1)==1){
                return "Yes";
            }else
                return "checkMultipleAddresses error (not 0 or 1) ";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "checkMultipleAddresses error";
    }

    public boolean addAddress(String address,Integer addressno,Integer userId){
        Connection con = DBConnection.getConnection();

        try{
            String addressnumber = "address"+addressno;
            PreparedStatement ps = con.prepareStatement("UPDATE addresses SET "+addressnumber+" = ? WHERE userID = ?");
            ps.setString(1,address);
            ps.setInt(2,userId);
            ps.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Object getById(int id) {
        Connection con = DBConnection.getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDateofbirth(rs.getString(4));
                user.setPhonenumber(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setMainaddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                return user;
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return false;
    }


    public Object getByUsername(String username){
        Connection con = DBConnection.getConnection();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDateofbirth(rs.getString(4));
                user.setPhonenumber(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setMainaddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                return user;
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Object getByColumnName(String columnName,String columnValue){
        Connection con = DBConnection.getConnection();
        ArrayList<User> users = new ArrayList<>();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE "+columnName+" = ?");
            ps.setString(1,columnValue);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setName(rs.getString(3));
                user.setDateofbirth(rs.getString(4));
                user.setPhonenumber(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setMainaddress(rs.getString(7));
                user.setProfileImage(rs.getString(8));
                users.add(user);
            }return users;


        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAll(int id,Object object){
        User u = (User) object;
        Connection con = DBConnection.getConnection();
        try{

            PreparedStatement ps = con.prepareStatement("UPDATE users SET username = ? , name = ? , dateofbirth = ? ,phonenumber = ? , email = ? , mainaddress = ? , profileimage = ? WHERE id = ?  ");
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getName());
            ps.setString(3,u.getDateofbirth());
            ps.setString(4,u.getPhonenumber());
            ps.setString(5,u.getEmail());
            ps.setString(6,u.getMainaddress());
            ps.setString(7,u.getProfileImage());
            ps.setInt(8,id);
            ps.executeUpdate();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateColumn(String columnName,String columnValue,int userID){
        Connection con = DBConnection.getConnection();

        try{

            PreparedStatement ps = con.prepareStatement("UPDATE users SET "+columnName+" = ? WHERE id = ?");
            ps.setString(1,columnValue);
            ps.setInt(2,userID);
            ps.executeUpdate();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean newProfileImageLink(int id,String imageLink){

        Connection con = DBConnection.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement("UPDATE users SET profileimage = ? WHERE id = ? ");
            ps.setString(1,imageLink);
            ps.setInt(2,id);
            ps.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }




}
