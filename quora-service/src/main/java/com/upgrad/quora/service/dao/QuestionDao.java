package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The method to create a new user from given UserEntity object
     *
     * @param questionEntity: object from which new question will be created
     * @return UserEntity object
     * @Author: Vipin P K
     */

    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    /**
     * The method to create a new user from given UserEntity object
     *
     * @return list of all questions..
     * @Author: Vipin P K
     */

    public List<QuestionEntity> getAllQuestions() {
        try {
            return entityManager.createNamedQuery("allQuestions", QuestionEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get the question for the given id.
     *
     * @param questionId id of the required question.
     * @return QuestionEntity if question with given id is found else null.
     * @Author: Divyank
     */
    public QuestionEntity getQuestionById(final String questionId) {
        try {
            return entityManager
                    .createNamedQuery("getQuestionById", QuestionEntity.class)
                    .setParameter("uuid", questionId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Update the question
     *
     * @param questionEntity question entity to be updated.
     * @Author: Divyank
     */
    public void updateQuestion(QuestionEntity questionEntity) {
        try {
            entityManager.merge(questionEntity);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
