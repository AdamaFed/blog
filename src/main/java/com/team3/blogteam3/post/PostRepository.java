package com.team3.blogteam3.post;

import com.team3.blogteam3.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Integer> {
}
