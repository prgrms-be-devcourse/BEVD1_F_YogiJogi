//package com.programmers.yogijogi.controller;
//
//import com.programmers.yogijogi.common.S3Uploader;
//import com.programmers.yogijogi.entity.Image;
//import com.programmers.yogijogi.service.HotelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/images")
//public class ImageApiController {
//
//    public final String HOTEL_DIRNAME = "/hotels";
//
//    @Autowired
//    private S3Uploader s3Uploader;
//
//    @Autowired
//    private HotelService hotelService;
//
//    @Autowired
//    private ImageService imageService;
//
//    @PostMapping("/hotels")
//    public ResponseEntity<String> uploadHotelImage(
//            @RequestParam("images") MultipartFile multipartFile,
//            @RequestParam("id") Long hotelId) throws IOException
//    {
//        String url = s3Uploader.upload(multipartFile, HOTEL_DIRNAME + "/" + hotelId);
//        hotelService.saveHotelImageUrl(hotelId, url);
//        return ResponseEntity.ok(url);
//    }
//
//    @GetMapping("/hotels/{hotelId}")
//    public ResponseEntity<Page<Image>> getAllImageByHotelId(
//            @PathVariable(value = "hotelId") Long hotelId,
//            Pageable pageable)
//    {
//        return ResponseEntity.ok(imageService.getAllByHotelId(hotelId, pageable));
//    }
//}
