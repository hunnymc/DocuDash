package doc.backendapi.controller;

import doc.backendapi.FileUpload.FileService;
import doc.backendapi.hadlers.CustomHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{userid}/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename, @PathVariable Integer userid) {
        Resource file = fileService.loadFileAsResource(filename, userid);
        String mimeType;
        try {
            mimeType = Files.probeContentType(Paths.get(file.getURI()));
        } catch (IOException e) {
            throw new CustomHttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Error occurred while determining file type.");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(file);
    }

    @PostMapping("")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        String filePath = fileService.store(file, 1);
        return "You successfully uploaded " + file.getOriginalFilename() + "! The file path is " + filePath;
    }
}