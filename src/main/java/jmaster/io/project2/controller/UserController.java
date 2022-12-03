package jmaster.io.project2.controller;

import jmaster.io.project2.DTO.PageDTO;
import jmaster.io.project2.DTO.UserDTO;
import jmaster.io.project2.entity.User;
import jmaster.io.project2.repo.UserRepo;
import jmaster.io.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @GetMapping("/new")
    public String add() {
        return "user/add.html";
    }
    @PostMapping("/new")
    public String add(@ModelAttribute User u) throws IllegalStateException, IOException{
        if(u.getFile() !=null && !u.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "D:/file/";

            String filename = u.getFile().getOriginalFilename();
            File namFile = new File(UPLOAD_FOLDER + filename);
            u.getFile().transferTo(new File(filename));
            u.setAvatar(filename); // save to db
        }
        //goi vao service
        userService.create(u);
        return "redirect:/user/search";
    }

    @GetMapping("/download")
    public void download(
            @RequestParam("filename") String filename,
            HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "D:/file";

        File file = new File(UPLOAD_FOLDER + filename);

        //java.nio.file.Files
        Files.copy(file.toPath(), response.getOutputStream());
    }
    @GetMapping("/search")
    public  String search(@RequestParam(name = "id", required = false) Integer id,
                          @RequestParam(name = "name", required = false) String name,

                          @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                          @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

                          @RequestParam(name = "size", required = false) Integer size,
                          @RequestParam(name = "page", required = false) Integer page, Model model) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageRS =
                userRepo.searchByName("%" + name + "%", pageable);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalPages());
        model.addAttribute("userList", pageRS.getContent());
        return name;
    }


}
