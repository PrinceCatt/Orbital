package org.example.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.backend.entity.Comment;
import org.example.backend.entity.Post;
import org.example.backend.entity.User;
import org.example.backend.mapper.CommentMapper;
import org.example.backend.mapper.PostMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

@RestController
@RequestMapping("/test")
public class TestController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/user")
    public Result userTest() {

        //define a new user
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setName("test");

        //register test, check status code, 20000 expected
        Result result0  = restTemplate.postForObject("http://localhost:8088/user/register", user, Result.class);
        assertEquals(result0.getCode(), 20000);

        //login test, check status code, 20000 expected, check email
       Result result1  = restTemplate.postForObject("http://localhost:8088/user/login", user, Result.class);
       assertEquals(result1.getCode(), 20000);

       String token = (String) result1.getData().get("token");
       String email = JwtUtils.getClaimsByToken(token).getSubject();

       assertEquals(email,"test@gmail.com");

       //delete test
        int userId = userMapper.findByEmail(email).getId();
        userMapper.deleteById(userId);



        //ensure deletion, check status, 20001 expected
        Result result2  = restTemplate.postForObject("http://localhost:8088/user/login", user, Result.class);
        assertEquals(result2.getCode(), 20001);

       return result1;
    }

    @GetMapping("/Post")
    public Result PostTest() throws JsonProcessingException {

        //define a new user, register
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setName("test");
        restTemplate.postForObject("http://localhost:8088/user/register", user, Result.class);
        Result result0 = restTemplate.postForObject("http://localhost:8088/user/login", user, Result.class);
        int userId = userMapper.findByEmail("test@gmail.com").getId();


        //post test, check status code, 20000 expected
        Post post = new Post();
        post.setAuthor("test");
        post.setTitle("test title");
        post.setSection("Anime");
        post.setContent("test post");

        String token = (String) result0.getData().get("token");
        ObjectMapper mapper = new ObjectMapper();

        String postJson0 = mapper.writeValueAsString(post);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Token", token);
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity0 = new HttpEntity<String>(postJson0, httpHeaders);

        Result result1 = restTemplate.postForObject("http://localhost:8088/user/post/new", httpEntity0, Result.class);
        assertEquals(result1.getCode(), 20000);

        //update post test
        List<Post> posts = postMapper.selectByUid(userId);
        Iterator<Post> iteratorP = posts.iterator();
        Post post2 = null;
        while (iteratorP.hasNext()) {
            post2 = iteratorP.next();
        }

        System.out.println(post2);

        post2.setContent("test post2");
        String postJson1 = mapper.writeValueAsString(post2);
        HttpEntity<String> httpEntity1 = new HttpEntity<String>(postJson1, httpHeaders);

        Result result2 = restTemplate.postForObject("http://localhost:8088/user/post/update", httpEntity1, Result.class);
        assertEquals(result2.getCode(), 20000);

        //comment test in the post
        Comment comment = new Comment();
        comment.setUid(userId);
        comment.setContent("test comment");
        comment.setPostId(post2.getId());
        String commentJson = mapper.writeValueAsString(comment);
        HttpEntity<String> httpEntity2 = new HttpEntity<String>(commentJson, httpHeaders);

        System.out.println(commentJson);

        Result result3 = restTemplate.postForObject("http://localhost:8088/post/comment/new", httpEntity2, Result.class);

        assertEquals(result3.getCode(), 20000);

        //like test
        int postId = post2.getId();
        List<Comment> comments = commentMapper.findByPostIdAndParentCommentIdNull(postId, -1);
        Iterator<Comment> iteratorC = comments.iterator();
        Comment comment2 = null;
        while (iteratorC.hasNext()) {
            comment2 = iteratorC.next();
        }
        int commentId = comment2.getId();

        String commentIdJson = mapper.writeValueAsString(commentId);
        HttpEntity<String> httpEntity3 = new HttpEntity<String>(commentIdJson, httpHeaders);
        Result result4 = restTemplate.postForObject("http://localhost:8088/userlike/like", httpEntity3, Result.class);
        assertEquals(result4.getCode(), 20000);

        Result result5 = restTemplate.postForObject("http://localhost:8088/userlike/status", httpEntity3, Result.class);
        assertEquals(result5.getCode(), 20000);
        assertEquals(result5.getData().get("status"), 1);

        //finish up, delete post and user
        String PostIdJson = mapper.writeValueAsString(postId);
        HttpEntity<String> httpEntity4 = new HttpEntity<String>(PostIdJson, httpHeaders);

        restTemplate.postForObject("http://localhost:8088/user/post/delete", httpEntity4, Result.class);
        userMapper.deleteById(userId);

        //final check
        assertEquals(restTemplate.postForObject("http://localhost:8088/user/login", user, Result.class).getCode(), 20001);

        return result2;
    }

}
