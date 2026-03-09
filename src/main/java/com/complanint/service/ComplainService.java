package com.complanint.service;

import com.complanint.Dto.ComplainWriteDto;
import com.complanint.Dto.ListDto;
import com.complanint.Entity.Complain;
import com.complanint.Entity.User;
import com.complanint.repository.ComplainRepo;
import com.complanint.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainService {
    @Autowired
    private ComplainRepo complainRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ComplainImageService complainImageService;

    public void save(String name, ComplainWriteDto writeDto, List<MultipartFile> multipartFiles) throws Exception {
        //  작성자의 id를 가지고 오기위해  계정명으로 id컬럼값 가져오기
        User user = userRepo.findById(name);

        // Complain 클래스 객체 만들어서 데이터 넣어주기
        //   complain 테이블에 데이터 저장할 꺼니까
        Complain complain = new Complain();
        complain.setUserId(user.getId()); // 현재 로그인회원의 id컬럼값
        complain.setTitle(writeDto.getTitle());
        complain.setContent(writeDto.getContent());
        complain.setCategory(writeDto.getCategory());
        // 민원테이블에 저장하기
        complainRepo.save(complain);

        // 민원 테이블에 저장하고 저장된 id컬럼값 가져오기
        Complain data = complainRepo.find(complain.getUserId());

        //이미지나 파일은  민원테이블의 id컬럼값이 필요하므로
        //  민원테이블저장한 이후에 한다.
        complainImageService.saveImg(multipartFiles, data.getId());
    }

    public List<ListDto> getList(String username) {
        // 로그인 계정명으로  계정 정보 가져오기 ( id 컬럼이 필요하다)
        //  id 컬럼으로  민원글에서 조회 해야 한다.
        //  ( 민원테이블의 작성자의 값이 계정 id로 저장되어있다.)
        // 하지만 이번에는 쿼리문(sql문)으로 전부 처리 해보겠다!!!

        List<Complain> complain
                = complainRepo.findByUserName(username);
        // 민원글 전체 다 가져온 다음에 ListDto 객체로 변환 시켜준다.
        //  ListDto객체들을  ArrayList에 담아준다.

        List<ListDto> listDtos = new ArrayList<>();

        for(Complain row : complain){
            ListDto listDto = new ListDto(row);
            listDtos.add(listDto);
        }
        return listDtos;

    }
}
