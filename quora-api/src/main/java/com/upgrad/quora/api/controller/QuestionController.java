package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller to handle user operations related to questions functionality
 */
@RestController
@RequestMapping("/")
public class QuestionController {


    @Autowired
    private QuestionBusinessService questionBusinessService;

    /**
     * Method for signed in user to create a new question..
     *
     * @Author:Vipin P K
     */
    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        // Create question entity
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setDate(ZonedDateTime.now());

        // Return response with created question entity
        final QuestionEntity createdQuestionEntity = questionBusinessService.createQuestion(questionEntity, authorization);
        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestionEntity.getUuid()).status("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    /**
     * Method for signed in user to view all questions posted in quora..
     *
     * @Author:Vipin P K
     */
    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        // Get all questions
        List<QuestionEntity> allQuestions = questionBusinessService.getAllQuestions(authorization);

        // Create response
        List<QuestionDetailsResponse> allQuestionDetailsResponses = new ArrayList<QuestionDetailsResponse>();

        //Get all relevant details related to question and build response..

        for (int i = 0; i < allQuestions.size(); i++) {
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse()
                    .content(allQuestions.get(i).getContent())
                    .id(allQuestions.get(i).getUuid());
            allQuestionDetailsResponses.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(allQuestionDetailsResponses, HttpStatus.OK);
    }

    /**
     * Edit a question
     *
     * @param authorization       access token to authenticate user.
     * @param questionId          id of the question to be edited.
     * @param questionEditRequest new content for the question.
     * @return Id and status of the question edited.
     * @throws AuthorizationFailedException In case the access token is invalid.
     * @throws InvalidQuestionException     if question with questionId doesn't exist.
     * @Author:Divyank
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestionContent(@RequestHeader("authorization") final String authorization,
                                                                    @PathVariable("questionId") final String questionId,
                                                                    QuestionEditRequest questionEditRequest) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity =
                questionBusinessService.editQuestion(authorization, questionId, questionEditRequest.getContent());
        QuestionEditResponse questionEditResponse = new QuestionEditResponse();
        questionEditResponse.setId(questionEntity.getUuid());
        questionEditResponse.setStatus("QUESTION EDITED");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK);
    }
}
