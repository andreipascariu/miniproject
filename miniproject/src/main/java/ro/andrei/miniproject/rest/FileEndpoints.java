package ro.andrei.miniproject.rest;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.andrei.miniproject.models.Files;
import ro.andrei.miniproject.services.FileStorageService;
import ro.andrei.miniproject.utils.FileStorageProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class FileEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(FileEndpoints.class);

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private FileStorageService fileStorageService;


    @RequestMapping(value="/createFolder", method=RequestMethod.POST)
    @ApiOperation("Creates the folder for the user")
    public ResponseEntity<?> createFolder(@RequestParam(value ="User Id") int userId){
        return ResponseEntity.ok(fileStorageService.makeFolder(userId));
    }

    @RequestMapping(value="/getFileTree" , method=RequestMethod.GET)
    public ResponseEntity<?> getFileTree(@RequestParam(value="User id") int userId ) throws IOException {
        return ResponseEntity.ok(fileStorageService.getFileTree(userId));
    }



    @PostMapping("/uploadFile")
    public Files uploadFile(@RequestParam("file") MultipartFile file,
                            @RequestParam("User Id") int userId
                            ) {
        String fileName = fileStorageService.storeFile(file,userId);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new Files(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

}