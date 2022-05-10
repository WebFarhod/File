package com.example.file.Controller;

import com.example.file.Bin.File_bin;
import com.example.file.Bin.Filesours_bin;
import com.example.file.Model.File;
import com.example.file.Model.Filesours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileC {
    @Autowired
    File_bin file_bin;
    @Autowired
    Filesours_bin filesours_bin;
    @PostMapping("/yuklash")
    public String fileyuklash(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileData=request.getFileNames();
        System.out.println(System.currentTimeMillis());
        MultipartFile file=request.getFile(fileData.next());
        if(file!=null){
            String filenomi=file.getOriginalFilename();
            long filehajmi=file.getSize();
            String fileturi=file.getContentType();
            byte[] filebyte=file.getBytes();
            File mfile=new File();
            mfile.setAfilenom(filenomi);
            mfile.setHajm(filehajmi);
            mfile.setTuri(fileturi);

            File file1=file_bin.save(mfile);
            Filesours filesours=new Filesours();
            filesours.setSours(filebyte);
            filesours.setFile(file1);
            filesours_bin.save(filesours);
            return  "File joylandi "+file1.getId();
        }
        return "File joylanmadi";
    }
    String manzil="baza";
    @PostMapping("yuklash3")
    public String fileyuklash3(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileIData=request.getFileNames();
        MultipartFile file= request.getFile(fileIData.next());
        if(file!=null){
            String filenomi=file.getOriginalFilename();
            long filehajmi=file.getSize();
            String fileturi=file.getContentType();
            byte[] filebyte=file.getBytes();
            File mfile=new File();
            mfile.setAfilenom(filenomi);
            mfile.setHajm(filehajmi);
            mfile.setTuri(fileturi);

            File file1=file_bin.save(mfile);
            Filesours filesours=new Filesours();
            filesours.setSours(filebyte);
            filesours.setFile(file1);
            filesours_bin.save(filesours);
            return  "File joylandi "+file1.getId();
        }
        else {
            return "joylanmadi";
        }
        if (file!=null){
            File file1=new File();
            file1.setAfilenom(file.getOriginalFilename());
            file1.setTuri(file.getContentType());
            file1.setHajm(file.getSize());
            String ynom=file.getOriginalFilename();
            String[] split = ynom.split("\\.");
            String s = UUID.randomUUID().toString()+"."+split[split.length-1];
            file1.setYanginom(s);
            file_bin.save(file1);
            Path path= Paths.get(manzil+"/"+s);
            Files.copy(file.getInputStream(),path);

            return "Joylandi";
        }
        else{
            return "Joylanmadi";
        }

    }
    @PostMapping("/yuklash2")
    public String fileyuklash2(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileData= request.getFileNames();
        System.out.println(System.currentTimeMillis());
        MultipartFile file= request.getFile(fileData.next());
        if (file!=null){
            File file1=new File();
            file1.setAfilenom(file.getOriginalFilename());
            file1.setTuri(file.getContentType());
            file1.setHajm(file.getSize());
            String ynom=file.getOriginalFilename();
            String[] split = ynom.split("\\.");
            String s = UUID.randomUUID().toString()+"."+split[split.length-1];
            file1.setYanginom(s);
            file_bin.save(file1);
            Path path= Paths.get(manzil+"/"+s);
            Files.copy(file.getInputStream(),path);

            return "Joylandi";
        }
        return "Joylanmadi";
    }
    @GetMapping("/yuklash/{id}")
    public  void yuklasfile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<File> optionalFile=file_bin.findById(id);
        if(optionalFile.isPresent()){
            File file = optionalFile.get();
            Optional<Filesours> optionalFilesours = filesours_bin.findById(id);
            if(optionalFilesours.isPresent()){
                Filesours filesours=optionalFilesours.get();
                response.setContentType(file.getTuri());
                //response.setHeader("Content-Disposition","attachment; name=\""+file.getAfilenom()+"\"");
                response.setHeader("Attachment-Disposition","attachment: filename=\""+file.getAfilenom()+"\"");
                FileCopyUtils.copy(filesours.getSours(),response.getOutputStream());
            }
        }
    }
    @GetMapping("/yuklash2/{id}")
    public void yuklashfile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<File> optionalFile=file_bin.findById(id);
        if(optionalFile.isPresent()){
            File file=optionalFile.get();
            response.setContentType(file.getTuri());
            response.setHeader("Attachment-Disposition","attachment: filename=\""+file.getAfilenom());
            FileInputStream inputStream=new FileInputStream("baza"+"/"+file.getYanginom());
            FileCopyUtils.copy(inputStream,response.getOutputStream());
        }

    }


}
