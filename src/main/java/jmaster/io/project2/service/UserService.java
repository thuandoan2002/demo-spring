package jmaster.io.project2.service;

import jmaster.io.project2.DTO.PageDTO;
import jmaster.io.project2.entity.User;
import jmaster.io.project2.DTO.UserDTO;
import jmaster.io.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Transactional
    public void create(User userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);
        //convert dto -> entity
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());


        //rollback
        userRepo.save(user);
        userRepo.save(user);
        userRepo.save(user);
    }
    public UserDTO getById(int id) { //java8, optinal
        User user =  userRepo.findById(id).orElseThrow(NoResultException::new); // java8 lambda

        UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
        //modelmapper
        userDTO.setId(userDTO.getId());
        userDTO.setName(userDTO.getName());
        userDTO.setUsername(userDTO.getUsername());
        userDTO.setBirthdate(userDTO.getBirthdate());
        userDTO.setPassword(    userDTO.getPassword());
        userDTO.setAvatar(userDTO.getAvatar());
        userDTO.setCreateAt(userDTO.getCreateAt());

        return  userDTO;

    }
    public PageDTO<UserDTO> searchByName(String name, int page, int size){
    Pageable pageable = PageRequest.of(page, size);

    Page<User> pageRS =
            userRepo.searchByName("%" + name + "%", pageable);

    PageDTO<UserDTO> pageDTO = new PageDTO<>();
    pageDTO.setTotalPages(pageRS.getTotalPages());
    pageDTO.setTotalElements((int) pageRS.getTotalElements());

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : pageRS.getContent()) {
            userDTOs.add(new ModelMapper().map(user, UserDTO.class));
        }

        pageDTO.setContents(userDTOs); // set vao pagedto
        return pageDTO;

     }

   }
