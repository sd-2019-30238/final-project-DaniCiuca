package a2.demo.controller;

import a2.demo.model.Player;
import a2.demo.model.Team;
import a2.demo.model.User;
import a2.demo.repository.PlayerRepository;
import a2.demo.repository.TeamRepository;
import a2.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/login")
    public ModelAndView displayLoginPage()
    {
        ModelAndView modelAndView = new ModelAndView("loginUser");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView verifyUser(User user)
    {
        ModelAndView modelAndView = new ModelAndView("loginUser");
        if(userRepository.existsById(user.getUsername())) {
            if(userRepository.findById(user.getUsername()).get().getPassword().equals(user.getPassword()))
                return new ModelAndView("redirect:/rest/users/home/"+user.getUsername());
        }
        return modelAndView;
    }

    @GetMapping(value = "/home/{username}")
    public ModelAndView displayHomePage(@PathVariable String username)
    {
        ModelAndView modelAndView = new ModelAndView("userHome");
        User u = new User();
        u.setUsername(username);
        modelAndView.addObject("user",u);
        return modelAndView;
    }

    @GetMapping(value = "/home/LaLiga")
    public ModelAndView displayLaLigaStandings()
    {
        List<Team> teams = teamRepository.findAll();
        List<Team> laLiga = new ArrayList<Team>();
        for(Team t : teams)
        {
            if(t.getLeague().equals("LaLiga"))
                laLiga.add(t);
        }
        laLiga.sort(Comparator.comparing(a->a.getPoints()));
        Collections.reverse(laLiga);
        ModelAndView modelAndView = new ModelAndView("viewLaLiga");
        modelAndView.addObject("teams",laLiga);
        return modelAndView;
    }

    @GetMapping(value = "/home/PremierLeague")
    public ModelAndView displayPremierLeagueStandings()
    {
        List<Team> teams = teamRepository.findAll();
        List<Team> premierLeague = new ArrayList<Team>();
        for(Team t : teams)
        {
            if(t.getLeague().equals("Premier League"))
                premierLeague.add(t);
        }
        premierLeague.sort(Comparator.comparing(a->a.getPoints()));
        Collections.reverse(premierLeague);
        ModelAndView modelAndView = new ModelAndView("viewPremierLeague");
        modelAndView.addObject("teams",premierLeague);
        return modelAndView;
    }

    @GetMapping(value = "/home/SerieA")
    public ModelAndView displaySerieAStandings()
    {
        List<Team> teams = teamRepository.findAll();
        List<Team> serieA = new ArrayList<Team>();
        for(Team t : teams)
        {
            if(t.getLeague().equals("Serie A"))
                serieA.add(t);
        }
        serieA.sort(Comparator.comparing(a->a.getPoints()));
        Collections.reverse(serieA);
        ModelAndView modelAndView = new ModelAndView("viewSerieA");
        modelAndView.addObject("teams",serieA);
        return modelAndView;
    }

    @GetMapping(value = "/home/Bundesliga")
    public ModelAndView displayBundesligaStandings()
    {
        List<Team> teams = teamRepository.findAll();
        List<Team> bundesliga = new ArrayList<Team>();
        for(Team t : teams)
        {
            if(t.getLeague().equals("Bundesliga"))
                bundesliga.add(t);
        }
        bundesliga.sort(Comparator.comparing(a->a.getPoints()));
        Collections.reverse(bundesliga);
        ModelAndView modelAndView = new ModelAndView("viewBundesliga");
        modelAndView.addObject("teams",bundesliga);
        return modelAndView;
    }

    @GetMapping(value="/home/GoldenBoat")
    public ModelAndView displayGoldenBoatStandings()
    {
        List<Player> players = playerRepository.findAll();
        players.sort(Comparator.comparing(a->a.getGoals()));
        Collections.reverse(players);
        ModelAndView modelAndView = new ModelAndView("viewGoldenBoat");
        modelAndView.addObject("players",players);
        return modelAndView;
    }

    @GetMapping("/signUp")
    public ModelAndView displaySignUpPage(){
        ModelAndView modelAndView = new ModelAndView("signUpPage");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

    @PostMapping("/signUp")
    public ModelAndView registrationProcess(User user)
    {
        userRepository.save(user);
        return new ModelAndView("loginUser");
    }

}
