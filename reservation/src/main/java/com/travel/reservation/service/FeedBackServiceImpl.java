package com.travel.reservation.service;

import com.travel.reservation.exception.BusException;
import com.travel.reservation.exception.FeedbackException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.Bus;
import com.travel.reservation.model.CurrentUserSession;
import com.travel.reservation.model.Feedback;
import com.travel.reservation.model.User;
import com.travel.reservation.repository.BusRepository;
import com.travel.reservation.repository.CurrentUserSessionRepository;
import com.travel.reservation.repository.FeedbackRepository;
import com.travel.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService{

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Override
    public Feedback addFeedBack(Feedback feedback, Integer busId, String key) throws BusException, UserException {

        CurrentUserSession loggedInUser = currentUserSessionRepository.findByUuid(key);

        if(loggedInUser == null) throw new UserException("Please provide a valid key to give Feedback!");

        User user = userRepository.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found"));

        Optional<Bus> busOptional = busRepository.findById(busId);if (busOptional.isEmpty()) {
            throw new BusException("Bus is not present with Id: "+ busId);
        }

        feedback.setBus(busOptional.get());
        feedback.setUser(user);
        feedback.setFeedbackDateTime(LocalDateTime.now());
        Feedback savedFeedback = feedbackRepository.save(feedback);

        return savedFeedback;
    }

    @Override
    public Feedback updateFeedBack(Feedback feedback, String key) throws FeedbackException, UserException {

        CurrentUserSession loggedInUser = currentUserSessionRepository.findByUuid(key);

        if(loggedInUser == null) throw new UserException("Please provide a valid key to update Feedback!");

        User user = userRepository.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found"));

        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedback.getFeedbackId());

        if(optionalFeedback.isPresent()){
            Feedback feedback2 = optionalFeedback.get();
            Optional<Bus> busOptional = busRepository.findById(feedback2.getBus().getBusId());

            if(!busOptional.isPresent()) throw new FeedbackException("Invalid bus details!");
            feedback.setBus(busOptional.get());
            feedback.setUser(user);
            user.getFeedbacks().add(feedback);
            feedback.setFeedbackDateTime(LocalDateTime.now());

            return feedbackRepository.save(feedback);
        }else throw new FeedbackException("Feedback not found");

    }

    @Override
    public Feedback deleteFeedBack(Integer feedbackId, String key) throws FeedbackException, UserException {


        CurrentUserSession loggedInUser = currentUserSessionRepository.findByUuid(key);

        if(loggedInUser == null) throw new UserException("Please provide a valid key to update Feedback!");

        User user = userRepository.findById(loggedInUser.getUserId()).orElseThrow(()-> new UserException("User not found"));

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        if(feedbackOptional.isPresent()){

            Feedback existingFeedback = feedbackOptional.get();
            feedbackRepository.delete(existingFeedback);
            return existingFeedback;

        }else throw new FeedbackException("No feedback found");
    }

    @Override
    public Feedback viewFeedback(Integer id) throws FeedbackException {

        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        if(optionalFeedback.isPresent()) {
            return optionalFeedback.get();
        }else throw new FeedbackException("No feedback found");
    }

    @Override
    public List<Feedback> viewFeedbackAll() throws FeedbackException {

        Optional<List<Feedback>> feedbacks = Optional.of(feedbackRepository.findAll());
        if(feedbacks.isPresent()){
            return feedbacks.get();
        }else throw new FeedbackException("No feedback found");
    }
}
