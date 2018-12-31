package ro.andrei.miniproject.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ro.andrei.miniproject.utils.FileStorageException;
import ro.andrei.miniproject.utils.FileStorageProperties;
import ro.andrei.miniproject.utils.MyFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation= Paths.get(("./userFolders/"));

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file,int userId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            fileName="./"+userId+"/"+fileName;
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


    public boolean makeFolder(int userId){
        File folder = new File("userFolders//"+userId);
        if(!folder.exists()){
            folder.mkdir();
            return true;
        }
        return false;
    }

    public ArrayList<?> getFileTree(int userId) throws IOException {
        File folder = new File("userFolders//"+userId);
        ArrayList<String> files0 = new ArrayList<String>();
        if(folder.exists()){
            System.out.println(folder.getName());
            ArrayList<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
            System.out.println(folder);
            System.out.println(files);
            for(File f:files){
                BasicFileAttributes attr = Files.readAttributes(Paths.get(f.getPath()), BasicFileAttributes.class);
                files0.add("File Name : "+f.getName()+" |||  Creation Time (UTC)  : " + attr.creationTime() + "  |||  File Type :  " + f.getName().substring(f.getName().length() - 3));
                System.out.println("creationTime: " + attr.creationTime());
            }

            return files0;

        }
        return files0;//temporary
    }






}