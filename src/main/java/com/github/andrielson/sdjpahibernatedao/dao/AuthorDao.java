package com.github.andrielson.sdjpahibernatedao.dao;

import com.github.andrielson.sdjpahibernatedao.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> listAuthorByLastNameLike(String lastName);

    List<Author> findAll();

    Author findAuthorByNameCriteria(String firstName, String lastName);

    Author findAuthorByNameNative(String firstName, String lastName);
}
