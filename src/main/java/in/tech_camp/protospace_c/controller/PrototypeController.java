package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.protospace_c.form.PrototypeForm;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class PrototypeController {

  //投稿ページ移動メソッド
  @GetMapping("/prototypes/new")
  public String showPrototypeNew(Model model){
    model.addAttribute("prototypeForm", new PrototypeForm());
    return "prototypes/new";
  }


}
