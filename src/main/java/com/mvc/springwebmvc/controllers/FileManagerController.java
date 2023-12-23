package com.mvc.springwebmvc.controllers;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller @RequestMapping(path = "/file")
public class FileManagerController {
    

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) @ResponseBody
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "name") String name) throws IllegalStateException, IOException {
        Path part = Path.of("upload/"+file.getOriginalFilename());
        file.transferTo(part);
        return "Success upload file";
    }
}
