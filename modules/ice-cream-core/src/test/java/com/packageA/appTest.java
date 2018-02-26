package com.packageA;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class appTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void testSaveItem() throws Exception {
//        String itemJson;
//
//        Gson gson = new Gson();
//        Item item = new Item();
//
//        item.setItemName("JellyType2");
//        item.setPrice(14);
//        itemJson = gson.toJson(item);
//
//        this.mockMvc.perform(post("/save")
//                .content(itemJson).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void testSelectAllItems() throws Exception {
//        this.mockMvc.perform(get("/all"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    public void testEditItem() throws Exception {
        this.mockMvc.perform(put("/editPrice").param("id","14")
                .param("name","Peanut").param("price","22"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteItem() throws Exception {
        this.mockMvc.perform(delete("/deleteItem").param("id","26"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSelectItemsByName() throws Exception {
        this.mockMvc.perform(get("/selectItem").param("itemName","Nut"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
