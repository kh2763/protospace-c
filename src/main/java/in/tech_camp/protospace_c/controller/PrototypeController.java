package in.tech_camp.protospace_c.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.protospace_c.custom_user.CustomUserDetail;
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
  // @GetMapping("/prototypes/detail/{prototypeId}")
  //   public String showPrototypeDetail(@PathVariable("prototypeId") Integer prototypeId, Model model) {      PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
  //     CommentForm commentForm = new CommentForm();
  //     model.addAttribute("prototype", prototype);
  //     model.addAttribute("commentForm", commentForm);
  //     model.addAttribute("comments", prototype.getComments());
  //     return "prototypes/detail";
  // }


  //投稿処理（DB保存後にトップページへ移動）
  @PostMapping("/prototypes")
  public String createPrototype(
    @ModelAttribute("prototypeForm") @Validated PrototypeForm form, 
    BindingResult result,
    @AuthenticationPrincipal CustomUserDetail userDetails) {

    // 画像の入力チェック
    if (form.getImage() == null || form.getImage().isEmpty()) {
        result.rejectValue("image", "error.image");
    }

// 💡 ★ここを追加！何のエラーが起きているかをコンソールに全部吐き出させる
    if (result.hasErrors()) {
        System.out.println("--- バリデーションエラーが発生しました ---");
        result.getAllErrors().forEach(System.out::println);
    }

    // エラーがあれば、何もせず投稿ページに戻る
    if (result.hasErrors()) {
      return "prototypes/new";
    }
    
    PrototypeEntity pro = new PrototypeEntity();
    pro.setTitle(form.getTitle());
    pro.setCatchcopy (form.getCatchcopy ());
    pro.setConcept(form.getConcept());
    // ★ Formに入っている画像ファイルから、ファイル名（文字列）を取り出してEntityにセットするらしい
    pro.setImage(form.getImage().getOriginalFilename());
    // UserIdをセット
    pro.setUserId(userDetails.getUser().getId());
    
    // 修正 画像の表示についていろいろ
    try {
      // 💡 1. 保存先（static/images/）のパスを作る
      java.nio.file.Path uploadPath = java.nio.file.Paths.get("src/main/resources/static/images/").toAbsolutePath().normalize();
      if (!java.nio.file.Files.exists(uploadPath)) {
          java.nio.file.Files.createDirectories(uploadPath); // なければ自動で作る
      }
      
      // 💡 2. ユーザーから送られてきた本物のファイルを static/images フォルダにコピーする
      java.nio.file.Path targetLocation = uploadPath.resolve(pro.getImage());
      java.nio.file.Files.copy(form.getImage().getInputStream(), targetLocation, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
      
      // 💡 3. ファイルが無事保存できたら、DBに登録する
      prototypeRepository.insert(pro);
      
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }

    return "redirect:/";
  }
}
