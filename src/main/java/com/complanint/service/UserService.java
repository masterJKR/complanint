package com.complanint.service;

import com.complanint.Dto.UserDto;
import com.complanint.Entity.User;
import com.complanint.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void save( UserDto userDto, PasswordEncoder passwordEncoder) {
        // 회원가입 입력한 데이터 객체인 DTO를 Entity 객체로 옮기기
        // Entity 클래스에서 비밀번호를 암호화 시키기
        //  Repository를 통해  Entity객체의 값 테이블에 저장

        User user = User.from(userDto, passwordEncoder);
        userRepo.save(user);
    }
}
