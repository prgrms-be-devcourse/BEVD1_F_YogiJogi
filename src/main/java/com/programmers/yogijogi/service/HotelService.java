package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.HotelConverter;
import com.programmers.yogijogi.converter.ImageConverter;
import com.programmers.yogijogi.converter.ReviewConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Image;
import com.programmers.yogijogi.entity.dto.HotelCreateDto;
import com.programmers.yogijogi.entity.dto.HotelDetailDto;
import com.programmers.yogijogi.entity.dto.ImageResponseDto;
import com.programmers.yogijogi.entity.dto.ReviewResponseDto;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ImageRepository;
import com.programmers.yogijogi.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    public HotelService(HotelRepository hotelRepository, ImageRepository imageRepository, ReviewRepository reviewRepository) {
        this.hotelRepository = hotelRepository;
        this.imageRepository = imageRepository;
        this.reviewRepository = reviewRepository;
    }

    // 하나의 호텔을 조회한다.
    @Transactional
    public HotelDetailDto getOne(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        return HotelConverter.of(hotel);
    }

    // 호텔 image url을 저장한다.
    @Transactional
    public void saveHotelImageUrl(Long id, String url) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        hotel.addImage(new Image(url));
    }

    // 호텔 id에 맞는 이미지를 가져온다.
    @Transactional
    public Page<ImageResponseDto> getImageByHotelId(Long hotelId, Pageable pageable) {
        return imageRepository.findImageByHotelId(hotelId, pageable)
                .map(image -> ImageConverter.of(image));
    }

    // 호텔을 저장한다.
    @Transactional
    public Long save(HotelCreateDto hotelCreateDto) {
        Hotel hotelEntity = hotelRepository.save(hotelCreateDto.toEntity());
        return hotelEntity.getId();
    }

    // 2개의 리뷰를 가져온다.
    @Transactional
    public List<ReviewResponseDto> getTwoReviewsByHotelId(Long id) {
        return reviewRepository.findTop2ByHotelId(id)
                .stream().map(review -> ReviewConverter.of(review))
                .collect(Collectors.toList());
    }
}
