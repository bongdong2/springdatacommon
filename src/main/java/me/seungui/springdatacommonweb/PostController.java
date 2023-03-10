package me.seungui.springdatacommonweb;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostRepository postRepository;

  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping("/posts/{id}")
  public String getPost(@PathVariable("id") Post post) {
    return post.getTitle();
  }

  @GetMapping("/posts")
  public PagedModel<EntityModel<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
    return assembler.toModel(postRepository.findAll(pageable));
  }
}
