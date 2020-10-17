package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method to persist the new answer posted by any user on a question.
     *
     * @param answerEntity object build through post request.
     * @author Vipin P K
     */
    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        entityManager.persist(answerEntity);
        return answerEntity;
    }

    /**
     * Fetches an answer from DB based on the answerId
     *
     * @param answerId id of the answer to be fetched.
     * @return Answer if there exist one with that id in DB else null.
     * @author Divyank
     */
    public AnswerEntity getAnswerById(final String answerId) {
        try {
            return entityManager
                    .createNamedQuery("getAnswerByUuid", AnswerEntity.class)
                    .setParameter("uuid", answerId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * updates the row of information in answer table of DB using method merge it changes it's state from detached to persistent.
     *
     * @param answerEntity answer to be updated.
     * @author Divyank
     */
    public void updateAnswer(AnswerEntity answerEntity) {
        entityManager.merge(answerEntity);
    }


    /**
     * Removes the entity from database
     *
     * @param answerId uuid of the answer to be deleted
     * @author Vipin P K
     */
    public void performDeleteAnswer(final String answerId) {
        AnswerEntity answerEntity = getAnswerById(answerId);
        entityManager.remove(answerEntity);
    }

    /**
     * Removes the entity from database
     *
     * @param questionId uuid of the question for the aswers to be fetched
     * @author Vipin P K
     */
    // fetch all the answers to the question using questionId
    public List<AnswerEntity> getAllAnswersToQuestion(final String questionId) {
        return entityManager
                .createNamedQuery("getAllAnswersToQuestion", AnswerEntity.class)
                .setParameter("uuid", questionId)
                .getResultList();
    }

}
