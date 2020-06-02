package controller;

import model.Turtorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TurtorialResposity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/home")
public class TurtorialController {
    @Autowired
    TurtorialResposity turtorialResposity;

    @GetMapping("/123")
    public List<Turtorial> getAllTutorials() {
        return turtorialResposity.findAll();
//        try {
//            List<Turtorial> turtorials = new ArrayList<Turtorial>();
//            if (title == null) {
//                turtorialResposity.findAll().forEach(turtorials::add);
//            } else {
//                turtorialResposity.findByTitleContaining(title).forEach(turtorials::add);
//            }
//            if (turtorials.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @GetMapping("turtorials/{id}")
    public ResponseEntity<Turtorial> getTurtorialById(@PathVariable("id") String id) {
        Optional<Turtorial> turtorialData = turtorialResposity.findById(id);
        if (turtorialData.isPresent()) {
            return new ResponseEntity<>(turtorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/turtorials")

    public ResponseEntity<Turtorial> createTurtorial(@RequestBody Turtorial turtorial) {
        try {
            Turtorial _turtorial = turtorialResposity.save(new Turtorial(turtorial.getTitle(), turtorial.getDescription(), false));
            return new ResponseEntity<>(_turtorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Turtorial> updateTutorial(@PathVariable("id") String id, @RequestBody Turtorial tutorial) {
        Optional<Turtorial> turtorialData = turtorialResposity.findById(id);
        if (turtorialData.isPresent()) {
            Turtorial _turtorial = turtorialData.get();
            _turtorial.setTitle(tutorial.getTitle());
            _turtorial.setDescription(tutorial.getDescription());
            _turtorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(turtorialResposity.save(_turtorial),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Turtorial>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            turtorialResposity.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteTutorial() {
        try {
            turtorialResposity.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Turtorial>> findByPublished() {
        try {
            List<Turtorial> turtorials = turtorialResposity.findByPublished(true);
            if (turtorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(turtorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
