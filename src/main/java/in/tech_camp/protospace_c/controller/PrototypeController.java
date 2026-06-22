package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.tech_camp.protospace_c.entity.PrototypeEntity;
import in.tech_camp.protospace_c.form.PrototypeForm;
import in.tech_camp.protospace_c.repository.PrototypeRepository;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class PrototypeController {

  private final PrototypeRepository prototypeRepository;


  //仮のトップページ（送信後のリダイレクト先）
  @GetMapping("/")
  @ResponseBody 
  public String showTweets(Model model) {
    return "トップページ"; 
  }

  //投稿ページ移動メソッド
  @GetMapping("/prototypes/new")
  public String showPrototypeNew(Model model){
    model.addAttribute("prototypeForm", new PrototypeForm());
    return "prototypes/new";
  }

  //投稿処理（DB保存後にトップページへ移動）
  @PostMapping("/prototypes")
  public String createPrototype(@ModelAttribute("prototypeForm") PrototypeForm form) {
    PrototypeEntity pro = new PrototypeEntity();
    pro.setTitle(form.getTitle());
    pro.setCatchcopy (form.getCatchcopy ());
    pro.setConcept(form.getConcept());
    // ★ Formに入っている画像ファイルから、ファイル名（文字列）を取り出してEntityにセットするらしい
    pro.setImage(form.getImage().getOriginalFilename());
    prototypeRepository.insert(pro);
    return "redirect:/";
  }
}
