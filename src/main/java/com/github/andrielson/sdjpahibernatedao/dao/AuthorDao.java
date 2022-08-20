package com.github.andrielson.sdjpahibernatedao.dao;

import com.github.andrielson.sdjpahibernatedao.domain.Author;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
