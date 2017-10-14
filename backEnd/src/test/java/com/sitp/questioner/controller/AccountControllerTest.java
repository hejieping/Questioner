package com.sitp.questioner.controller;

import com.sitp.questioner.Application;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by qi on 2017/7/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountControllerTest {
    @Autowired
    private  WebApplicationContext wac;
    private  MockMvc mockMvc;

    @Before
    public  void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testRegister() throws Exception
    {
        String content = "{\"username\":\"12345\",\"password\":\"123\"}";
        mockMvc.perform(post("/register","json")
                .characterEncoding("UTF-8")
                .content(content.getBytes())
                .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print());
    }

    @Test
    public void testLoginWithUser() throws Exception
    {
        String content = "{\"username\":\"123\",\"password\":\"123\"}";
        MvcResult mvcResult =  mockMvc.perform(post("/auth","json")
                .content(content.getBytes())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject result = (JSONObject) jsonObject.getJSONObject("result");
        String token = result.getString("token");

        mockMvc.perform(get("/user","json")
                           .header("Authorization",token)
                           .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());


    }

    @Test(expected = NestedServletException.class)
    public void testAccessWithoutPermission() throws Exception
    {
        String content = "{\"username\":\"123\",\"password\":\"123\"}";
        MvcResult mvcResult =  mockMvc.perform(post("/auth","json")
                .content(content.getBytes())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject result = (JSONObject) jsonObject.getJSONObject("result");
        String token = result.getString("token");
        mockMvc.perform(get("/admin","json")
                .header("Authorization",token)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginWithAdmin_User() throws Exception
    {
        String content = "{\"username\":\"luoyuxia\",\"password\":\"luoyuxia\"}";
        MvcResult mvcResult =  mockMvc.perform(post("/auth","json")
                .content(content.getBytes())
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject result =  jsonObject.getJSONObject("result");
        String token = result.getString("token");

        mockMvc.perform(get("/user","json")
                .header("Authorization",token)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/admin","json")
                .header("Authorization",token)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));
    }

    @After
    public  void tearDown()
    {

    }

}
