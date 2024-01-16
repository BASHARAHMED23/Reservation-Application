package com.travel.reservation.controller;

import com.travel.reservation.exception.BusException;
import com.travel.reservation.exception.FeedbackException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.Feedback;
import com.travel.reservation.service.FeedBackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class FeedbackController {

    @Autowired
    private FeedBackService feedbackService;

    @PostMapping("/user/feedback/add/{busId}")
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody Feedback feedback,
                                                @PathVariable("busId") Integer busId,
                                                @RequestParam(required = false) String key) throws UserException, BusException {

        Feedback feedback2 = feedbackService.addFeedBack(feedback,busId,key);

        return new ResponseEntity<Feedback>(feedback2, HttpStatus.ACCEPTED);

    }
    @PutMapping("/user/feedback/update")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback,@RequestParam(required = false) String key) throws FeedbackException, UserException {

        Feedback feedback2 = feedbackService.updateFeedBack(feedback,key);

        return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);

    }
    @DeleteMapping("/user/feedback/delete/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id") Integer feedbackId,@RequestParam(required = false) String key) throws FeedbackException, UserException {

        Feedback feedback2 = feedbackService.deleteFeedBack(feedbackId,key);

        return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);

    }


//	viewFeedback

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> viewFeedback(@PathVariable("id") Integer ID) throws FeedbackException {

        Feedback feedback2 = feedbackService.viewFeedback(ID);

        return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);

    }

    @GetMapping("/feedback/all")
    public ResponseEntity<List<Feedback>> viewFeedbackAll() throws FeedbackException {

        List<Feedback> feedback2 =  feedbackService.viewFeedbackAll();

        return new ResponseEntity<List<Feedback>>(feedback2,HttpStatus.ACCEPTED);

    }

}