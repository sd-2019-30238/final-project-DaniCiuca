package a2.demo.controller;

import a2.demo.model.Player;
import a2.demo.model.Staff;
import a2.demo.model.Team;
import a2.demo.model.User;
import a2.demo.repository.PlayerRepository;
import a2.demo.repository.StaffRepository;
import a2.demo.repository.TeamRepository;
import a2.demo.repository.UserRepository;
import a2.demo.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@RestController
@RequestMapping(value = "/rest/staff")
public class StaffController {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailServiceImpl emailService;

    @GetMapping("/login")
    public ModelAndView displayLoginPage()
    {
        ModelAndView modelAndView = new ModelAndView("loginStaff");
        modelAndView.addObject("staff",new Staff());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView verifyStaff(Staff staff)
    {
        ModelAndView modelAndView = new ModelAndView("loginStaff");
        if(staffRepository.existsById(staff.getUsername())) {
            if(staffRepository.findById(staff.getUsername()).get().getPassword().equals(staff.getPassword()))
                return new ModelAndView("redirect:/rest/staff/home");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView displayHomePage()
    {
        ModelAndView modelAndView = new ModelAndView("staffHome");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addGoals(String goals, String player)
    {
        List<User> users = userRepository.findAll();
        if(playerRepository.existsById(player))
        {
            Player p = playerRepository.findById(player).get();
            int g = Integer.parseInt(goals);
            p.setGoals(p.getGoals()+g);
            playerRepository.save(p);
            for(User u : users) {
                if(u.getEmail()!=null) {
                    emailService.sendMail(u.getEmail(),p.getName()+" has scored!",p.getName()+" has scored "+g+" goals today!");
                }
            }
        }
        return new ModelAndView("redirect:/rest/staff/home");
    }
    @PostMapping("/home")
    public ModelAndView addMatch(String team1, String team2, String goals1, String goals2)
    {
        if(teamRepository.existsById(team1) && teamRepository.existsById(team2))
        {
            Team t1 = teamRepository.findById(team1).get();
            Team t2 = teamRepository.findById(team2).get();
            int g1 = Integer.parseInt(goals1);
            int g2 = Integer.parseInt(goals2);
            t1.setPlayed(t1.getPlayed()+1);
            t2.setPlayed(t2.getPlayed()+1);
            t1.setGf(t1.getGf()+g1);
            t1.setGa(t1.getGa()+g2);
            t2.setGf(t2.getGf()+g2);
            t2.setGa(t2.getGa()+g1);
            if(g1==g2)
            {
                t1.setDrawn(t1.getDrawn()+1);
                t2.setDrawn(t2.getDrawn()+1);
                t1.setPoints(t1.getPoints()+1);
                t2.setPoints(t2.getPoints()+1);
            }
            else if(g1>g2)
            {
                t1.setWon(t1.getWon()+1);
                t2.setLost(t2.getLost()+1);
                t1.setPoints(t1.getPoints()+3);
            }
            else
            {
                t1.setLost(t1.getLost()+1);
                t2.setWon(t2.getWon()+1);
                t2.setPoints(t2.getPoints()+3);
            }
            teamRepository.save(t1);
            teamRepository.save(t2);
            List<User> users = userRepository.findAll();
            for(User u : users) {
                if(u.getEmail()!=null) {
                    emailService.sendMail(u.getEmail(),team1+" - "+team2+" has finished!","The result was:\n"+team1+" "+g1+" : "+g2+" "+team2);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView("staffHome");
        return modelAndView;
    }
}
