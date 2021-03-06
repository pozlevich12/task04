package com.table.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.table.entity.AuthProvider;
import com.table.entity.User;
import com.table.repository.UserRepository;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "about";
    }

    @GetMapping("/sortById")
    public String sortById(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return Long.compare(user1.getId(), user2.getId());
            }
        });
        model.addAttribute("users", users);
        return "about";
    }

    @GetMapping("/sortByName")
    public String sortByName(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareTo(user2.getName());
            }
        });
        model.addAttribute("users", users);
        return "about";
    }

    @GetMapping("/sortByFirstLogin")
    public String sortByFirstLogin(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getFirstVisit().compareTo(user2.getFirstVisit());
            }
        });
        model.addAttribute("users", users);
        return "about";
    }

    @GetMapping("/sortByLastLogin")
    public String sortByLastLogin(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getLastVisit().compareTo(user2.getLastVisit()) * (-1);
            }
        });
        model.addAttribute("users", users);
        return "about";
    }

    @GetMapping("/GOOGLE")
    public String sortByGoogle(Model model) {
        model.addAttribute("users", userRepository.findByAuthProvider(AuthProvider.GOOGLE));
        return "about";
    }

    @GetMapping("/GITHUB")
    public String sortByGitHub(Model model) {
        model.addAttribute("users", userRepository.findByAuthProvider(AuthProvider.GITHUB));
        return "about";
    }

    @GetMapping("/AZURE")
    public String sortByAzure(Model model) {
        model.addAttribute("users", userRepository.findByAuthProvider(AuthProvider.AZURE));
        return "about";
    }

    @GetMapping("/Blocked")
    public String sortByBlocked(Model model) {
        model.addAttribute("users", userRepository.findByBlocked(true));
        return "about";
    }

    @GetMapping("/UnBlocked")
    public String sortByUnBlocked(Model model) {
        model.addAttribute("users", userRepository.findByBlocked(false));
        return "about";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=block")
    public String blocking(@RequestParam(value = "checkbox", required = false) Long[] checkboxes, Model model) {
        if (checkboxes != null) {
            Iterable<Long> ids = Arrays.asList(checkboxes);
            List<User> users = (List<User>) userRepository.findAllById(ids);
            for (User user : users) {
                user.setBlocked(true);
                userRepository.save(user);
            }
        }
        model.addAttribute("users", userRepository.findAll());
        return "redirect:about";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=unblock")
    public String unBlocking(@RequestParam(value = "checkbox", required = false) Long[] checkboxes, Model model) {
        if (checkboxes != null) {
            Iterable<Long> ids = Arrays.asList(checkboxes);
            List<User> users = (List<User>) userRepository.findAllById(ids);
            for (User user : users) {
                user.setBlocked(false);
                userRepository.save(user);
            }
        }
        model.addAttribute("users", userRepository.findAll());
        return "redirect:about";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam(value = "checkbox", required = false) Long[] checkboxes, Model model) {
        if (checkboxes != null) {
            Iterable<Long> ids = Arrays.asList(checkboxes);
            userRepository.deleteAllById(ids);
        }
        model.addAttribute("users", userRepository.findAll());
        return "redirect:about";
    }
}