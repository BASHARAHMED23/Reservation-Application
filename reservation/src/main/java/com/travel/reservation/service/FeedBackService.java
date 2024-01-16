package com.travel.reservation.service;

import com.travel.reservation.exception.BusException;
import com.travel.reservation.exception.FeedbackException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.Feedback;

import java.util.List;

public interface FeedBackService {
    public Feedback addFeedBack(Feedback feedback , Integer busId , String key) throws BusException , UserException;
    public Feedback updateFeedBack(Feedback feedback,String key) throws FeedbackException, UserException;

    public Feedback deleteFeedBack(Integer feedbackId, String key)throws FeedbackException,UserException;

    public Feedback viewFeedback(Integer id) throws FeedbackException;

    public List<Feedback> viewFeedbackAll() throws FeedbackException;


}
