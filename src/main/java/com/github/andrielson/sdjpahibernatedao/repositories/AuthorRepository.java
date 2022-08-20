package com.github.andrielson.sdjpahibernatedao.repositories;

import com.github.andrielson.sdjpahibernatedao.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
