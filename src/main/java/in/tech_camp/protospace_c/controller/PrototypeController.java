package in.tech_camp.protospace_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

  //投稿処理（DB保存後にトップページへ移動）
  @PostMapping("/prototypes")
  public String createPrototype(
      @ModelAttribute("prototypeForm") @Validated PrototypeForm form, 
      BindingResult result) {

    // 追記　画像の入力チェック
    if (form.getImage() == null || form.getImage().isEmpty()) {
        result.rejectValue("image", "error.image");
    }

    // 追記 エラーがあれば、何もせず投稿ページに戻る
    if (result.hasErrors()) {
      return "prototypes/new";
    }
    
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
