package me.seungui.springdatacommonweb;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  PostRepository postRepository;

  @Test
  public void getPost() throws Exception {
    Post post = new Post();
    post.setTitle("Hello spring data");
    postRepository.save(post);

    mockMvc.perform(get("/posts/" + post.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("Hello spring data"))
    ;
  }

  @Test
  public void getPageablePosts() throws Exception {
    createPosts();

    Pageable pageable = PageRequest.of(0, 3);

    mockMvc.perform(get("/posts")
            .param("page", "3")
            .param("size", "10")
            .param("sort", "created,desc")
            .param("sort", "title")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.postList[0].title", is("Hello spring data")))
    ;
  }

  private void createPosts() {
    int postsCount = 100;

    while(postsCount > 0) {
      Post post = new Post();
      post.setTitle("Hello spring data");
      postRepository.save(post);
      postsCount--;
    }
  }
}
























