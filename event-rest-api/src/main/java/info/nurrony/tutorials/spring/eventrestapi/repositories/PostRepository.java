package info.nurrony.tutorials.spring.eventrestapi.repositories;

import org.springframework.data.repository.CrudRepository;

import info.nurrony.tutorials.spring.eventrestapi.domain.entities.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
