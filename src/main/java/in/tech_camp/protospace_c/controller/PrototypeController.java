package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_c.entity.PrototypeEntity;
import in.tech_camp.protospace_c.form.PrototypeForm;
import in.tech_camp.protospace_c.repository.PrototypeRepository;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class PrototypeController {

  private final PrototypeRepository prototypeRepository;

  //投稿ページ移動メソッド
  @GetMapping("/prototypes/new")
  public String showPrototypeNew(Model model){
    model.addAttribute("prototypeForm", new PrototypeForm());
    return "prototypes/new";
  }

@PostMapping("/Prototypes")
  public String createPrototype(@ModelAttribute("PrototypeForm") PrototypeForm form) {
    PrototypeEntity pro = new PrototypeEntity();
    pro.setTitle(form.getTitle());
    pro.setCatchcopy (form.getCatchcopy ());
    pro.setConcept(form.getConcept());
    pro.setImage(form.getImage());
    prototypeRepository.insert(pro);
    return "redirect:/";
  }
}
