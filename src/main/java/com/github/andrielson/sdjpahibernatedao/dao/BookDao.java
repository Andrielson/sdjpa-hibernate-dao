package com.github.andrielson.sdjpahibernatedao.dao;

import com.github.andrielson.sdjpahibernatedao.domain.Book;

public interface BookDao {
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book Book);

    Book updateBook(Book Book);

    void deleteBookById(Long id);
}
