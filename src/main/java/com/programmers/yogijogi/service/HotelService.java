package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.HotelConverter;
import com.programmers.yogijogi.converter.ImageConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Image;
import com.programmers.yogijogi.entity.dto.HotelDetailDto;
import com.programmers.yogijogi.entity.dto.ImageResponseDto;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public HotelDetailDto getOne(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        return HotelConverter.of(hotel);
    }

    @Transactional
    public void saveHotelImageUrl(Long id, String url) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        hotel.addImage(new Image(url));
    }

    @Transactional
    public Page<ImageResponseDto> getImageByHotelId(Long hotelId, Pageable pageable) {
        return imageRepository.findImageByHotelId(hotelId, pageable)
                .map(image -> ImageConverter.of(image));
    }
}
