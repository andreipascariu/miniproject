package ro.andrei.miniproject.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.andrei.miniproject.models.User;
import ro.andrei.miniproject.services.UserServices;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserEndpoints {


    private UserServices userServices = new UserServices();


    @RequestMapping(value="/createUser" , method= RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(userServices.createUser(user)==true)
            return ResponseEntity.ok("OK");
        else
            return ResponseEntity.ok("createUser Error");

}

    @RequestMapping(value="/addAddress" , method=RequestMethod.POST)
    @ApiOperation("Updates an address (selected via address number from 2 to 9,1 being the main address in the user table) for an user in the address table. If a user dedicated row doesn't exist ,it creates one first")
    public ResponseEntity<?> addAddress(@RequestParam(value="Address") String address,
                                        @RequestParam(value="Address number") Integer addressno,
                                        @RequestParam(value="User Id") Integer userId
    ){
        if(userServices.addAdress(address,addressno,userId)==true)
            return ResponseEntity.ok("OK");
        else
            return ResponseEntity.ok("addAddress Error");
    }

    @RequestMapping(value="/delete" ,method=RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam(value ="User Id ") int id){
        return ResponseEntity.ok(userServices.delete(id));
    }

    @RequestMapping(value="/getAll", method=RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userServices.getAll());
    }


    @RequestMapping(value="/getAddresses" , method=RequestMethod.GET) //returns an array of all address arrays
    public ResponseEntity<?> getAddresses(){
        return ResponseEntity.ok(userServices.getAddresses());
    }

    @RequestMapping(value="/getById" , method=RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam(value="id") Integer id){
        return ResponseEntity.ok(userServices.getById(id));
    }

    @RequestMapping(value="/getByUsername" , method=RequestMethod.GET)
    public ResponseEntity<?> getByUsername(@RequestParam(value="username") String username){
        return ResponseEntity.ok(userServices.getByUsername(username));
    }

    @RequestMapping(value="/uploadProfileImageLink" , method=RequestMethod.POST)
    public ResponseEntity<?> newProfileImageLink(@RequestParam(value="User id") int id,
            @RequestParam(value="Image Link") String imagelink) throws IOException {
        return ResponseEntity.ok(userServices.newProfileImageLink(id,imagelink));
    }

    @RequestMapping(value="/getByColumnName" , method=RequestMethod.GET)
    @ApiOperation("Gets all users that have the specified 'columnValue' in the specified 'columnName'")
    public ResponseEntity<?> getByColumnName(@RequestParam(value ="Column Name") String columnName,
                                             @RequestParam(value ="Column Value") String columnValue
    ){
        return ResponseEntity.ok(userServices.getByColumnName(columnName,columnValue));
    }

    @RequestMapping(value="/updateAll" , method=RequestMethod.POST)
    @ApiOperation("Update all of the fields")
    public ResponseEntity<?> updateAll(@RequestParam(value ="User Id") int userId,
                                       @RequestBody User user
                                       ){
        return ResponseEntity.ok(userServices.updateAll(userId,user));
    }

    @RequestMapping(value="/updateCell" , method=RequestMethod.POST)
    @ApiOperation("Updates columnName with columnValue from user : userID")
    public ResponseEntity<?> updateCell(@RequestParam(value="Column Name : ") String columnName,
                                        @RequestParam(value ="Column Value: ") String columnValue,
                                        @RequestParam(value="User Id : ") int userId
    ){
      return ResponseEntity.ok(userServices.updateColumn(columnName,columnValue,userId));
    }




}


