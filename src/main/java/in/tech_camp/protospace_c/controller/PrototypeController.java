package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String showTweets(Model model) {
    //データベースからすべてのプロトタイプを取得して画面に渡す
    model.addAttribute("prototypes", prototypeRepository.findAll());

    //index.htmlを呼び出す
    return "prototypes/index";
  }

  //投稿ページ移動メソッド
  @GetMapping("/prototypes/new")
  public String showPrototypeNew(Model model){
    model.addAttribute("prototypeForm", new PrototypeForm());
    return "prototypes/new";
  }

  //prototypeの詳細ページ移動メソッド
  @GetMapping("/prototypes/detail/{prototypeId}")
    public String showPrototypeDetail(@PathVariable("prototypeId") Integer prototypeId, Model model) {      PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
      CommentForm commentForm = new CommentForm();
      model.addAttribute("prototype", prototype);
      model.addAttribute("commentForm", commentForm);
      model.addAttribute("comments", prototype.getComments());
      return "prototypes/detail";
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
