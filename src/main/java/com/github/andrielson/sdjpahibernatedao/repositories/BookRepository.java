package com.github.andrielson.sdjpahibernatedao.repositories;

import com.github.andrielson.sdjpahibernatedao.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
