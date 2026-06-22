package in.tech_camp.protospace_c.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PrototypeControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testIndexPage() throws Exception {
//ルートパスにアクセスした際に２００okが返り、index.htmlが表示されることを確認
   mockMvc.perform(get("/"))
          .andExpect(status().isOk())
          .andExpect(view().name("prototypes/index"));
  }
}
