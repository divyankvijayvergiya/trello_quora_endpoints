package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerEditRequest;
import com.upgrad.quora.api.model.AnswerEditResponse;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AnswerController {

    @Autowired
    private AnswerBusinessService answerBusinessService;

    /**
     * Controller for handle answer a question functionality in quora application.
     *
     * @param answerRequest the answer to be saved.
     * @param authorization access token of user who wish to post the answer to any question in quora
     * @param questionId    the uuid of the question against which an answer has to be posted.
     * @contributer: Vipin / Divyank
     */

    @RequestMapping(
            method = RequestMethod.POST, path = "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(final AnswerRequest answerRequest, @RequestHeader("authorization") final String authorization,
                                                       @PathVariable("questionId") final String questionId) throws AuthorizationFailedException, InvalidQuestionException {

        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(answerRequest.getAnswer());

        // Return response with created answer entity
        final AnswerEntity createdAnswerEntity = answerBusinessService.createAnswer(answerEntity, questionId, authorization);
        AnswerResponse answerResponse = new AnswerResponse().id(createdAnswerEntity.getUuid()).status("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);
    }

    /**
     * This API edits the answer which already exist in the database.
     *
     * @param authorization       To authenticate the user who is trying to edit the answer.
     * @param answerId          Id of the answe which is to be edited.
     * @param answerEditRequest Contains the new content of the answer.
     * @return
     * @throws AuthorizationFailedException ATHR-001 If the user has not signed in and ATHR-002 If the
     *                                      user is already signed out and ATHR-003 if the user is not the owner of the answer.
     * @throws AnswerNotFoundException      ANS-001 if the answer is not found in the database.
     *  @Author:Divyank
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/answer/edit/{answerId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswerContent(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("answerId") final String answerId,
            AnswerEditRequest answerEditRequest)
            throws AuthorizationFailedException, AnswerNotFoundException {

        AnswerEntity answerEntity =
                answerBusinessService.editAnswer(authorization, answerId, answerEditRequest.getContent());
        AnswerEditResponse answerEditResponse = new AnswerEditResponse().id(answerEntity.getUuid()).status("ANSWER EDITED");
        return new ResponseEntity<AnswerEditResponse>(answerEditResponse, HttpStatus.OK);
    }
}
