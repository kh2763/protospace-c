package in.tech_camp.protospace_c.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.tech_camp.protospace_c.custom_user.CustomUserDetail;
import in.tech_camp.protospace_c.entity.CommentEntity;
import in.tech_camp.protospace_c.entity.PrototypeEntity;
import in.tech_camp.protospace_c.form.CommentForm;
import in.tech_camp.protospace_c.form.PrototypeForm;
import in.tech_camp.protospace_c.repository.CommentRepository;
import in.tech_camp.protospace_c.repository.PrototypeRepository;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class PrototypeController {

  private final PrototypeRepository prototypeRepository;
  private final CommentRepository commentRepository;

  // トップページ（送信後のリダイレクト先）
  @GetMapping("/")
  public String showTweets(Model model) {
    // データベースからすべてのプロトタイプを取得して画面に渡す
    model.addAttribute("prototypes", prototypeRepository.findAll());

    // index.htmlを呼び出す
    return "prototypes/index";
  }

  // 投稿ページ移動メソッド
  @GetMapping("/prototypes/new")
  public String showPrototypeNew(Model model){
    model.addAttribute("prototypeForm", new PrototypeForm());
    return "prototypes/new";
  }

  //prototypeの詳細ページ移動メソッド 
    @GetMapping("/prototypes/detail/{prototypeId}")
    public String showPrototypeDetail(@PathVariable("prototypeId") Integer prototypeId, Model model) {      
        PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
        CommentForm commentForm = new CommentForm();
        
        model.addAttribute("prototype", prototype);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("comments", prototype.getComments());
        return "prototypes/detail";
  }


  //投稿処理（DB保存後にトップページへ移動）
  @PostMapping("/prototypes")
  public String createPrototype(
    @ModelAttribute("prototypeForm") @Validated PrototypeForm form, 
    BindingResult result,
    @AuthenticationPrincipal CustomUserDetail userDetails) {

    // 画像の入力チェック（ここは今まで通り）
    if (form.getImage() == null || form.getImage().isEmpty()) {
        result.rejectValue("image", "error.image");
    }

    // エラーがあれば、何もせず投稿ページに戻る
    if (result.hasErrors()) {
      return "prototypes/new";
    }
    
    PrototypeEntity pro = new PrototypeEntity();
    pro.setTitle(form.getTitle());
    pro.setCatchcopy(form.getCatchcopy());
    pro.setConcept(form.getConcept());
    // UserIdをセット
    pro.setUserId(userDetails.getUser().getId());
    
    // 変更投稿された画像データをデータベースに保存する
    try {
        // 画像ファイルをデータ（バイト配列）に変換してエンティティにセットする
        pro.setImage(form.getImage().getBytes());
        
        // データベースに情報を登録する
        prototypeRepository.insert(pro);
        
    } catch (Exception e) {
        // エラーが発生した場合はログに出力し、トップページへ戻る
        System.out.println("データ変換エラー：" + e);
        return "redirect:/";
    }
    
    // 正常に終了した場合はトップページへ移動する

    return "redirect:/";
  }

  //指定されたIDに一致する画像データをデータベースから読み込んで画面に返す
  @GetMapping("/prototypes/image/{id}")
  @ResponseBody
  public org.springframework.http.ResponseEntity<byte[]> displayImage(@PathVariable Integer id) {
      try {
          // データベースから、指定されたIDのデータを1件だけ直接取得する
          PrototypeEntity image = prototypeRepository.findById(id);
          
          // データが存在し、画像が登録されている場合
          if (image != null && image.getImage() != null) {
              // 取得したデータに「画像ファイルである」という情報を付けて画面へ返す
              return org.springframework.http.ResponseEntity.ok()
                      .contentType(org.springframework.http.MediaType.IMAGE_PNG)
                      .body(image.getImage());
          }
      } catch (Exception e) {
          // エラーが発生した場合は、その内容をコンソール（ログ）に出力する
          System.out.println("画像読み込みエラー: " + e);
      }
      return null;
  }


  //削除機能
  @PostMapping("/prototypes/{prototypeId}/delete")
  public String deleteTweet(@PathVariable("prototypeId") Integer prototypeId) {
    
    try {
      prototypeRepository.deleteById(prototypeId);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }
    return "redirect:/";
  }


  // 編集ページ移動メソッド
  @GetMapping("/prototypes/edit/{prototypeId}")
  public String showPrototypeEdit(@PathVariable("prototypeId") Integer prototypeId, Model model) {
      // 1. DBから既存のデータを取得する
      PrototypeEntity prototype = prototypeRepository.findById(prototypeId);

      // 2. フォームオブジェクトに移し替える
      PrototypeForm form = new PrototypeForm();
      form.setId(prototype.getId()); // Formにidを保持させる
      form.setTitle(prototype.getTitle());
      form.setCatchcopy(prototype.getCatchcopy());
      form.setConcept(prototype.getConcept());
      // ※画像は任意アップロードにするため、ここではセットしないことが多いです

      // 3. 画面に渡す
      model.addAttribute("prototypeForm", form);
      
      // edit.html を呼び出す（今回のHTMLを置く場所）
      return "prototypes/edit";
  }


  // 編集処理
  @PostMapping("/prototypes/edit/{prototypeId}")
  public String updatePrototype(
      @PathVariable("prototypeId") Integer prototypeId,
      @ModelAttribute("prototypeForm") @Validated PrototypeForm form, 
      BindingResult result,
      @AuthenticationPrincipal CustomUserDetail userDetails,
      Model model) {

      // 1. 【条件：自身が投稿していない場合はトップページへ】のチェック
      // 安全のため、処理の最初に対象のプロトタイプが本人のものか再度チェックします
      PrototypeEntity currentPrototype = prototypeRepository.findById(prototypeId);
      if (currentPrototype == null || !currentPrototype.getUserId().equals(userDetails.getUser().getId())) {
          return "redirect:/";
      }

      // 2. 【条件：空の入力欄がある場合は、編集できずそのページに留まる（入力済み項目は消えない）】
      if (result.hasErrors()) {
          System.out.println("--- 編集バリデーションエラーが発生しました ---");
          result.getAllErrors().forEach(System.out::println);
          // そのまま編集ページ（フォームが入った状態）を返して留まらせます
          return "prototypes/edit"; 
      }
      
      try {
          // 3. 画面から送られてきた新しい値で上書きする
          currentPrototype.setTitle(form.getTitle());
          currentPrototype.setCatchcopy(form.getCatchcopy());
          currentPrototype.setConcept(form.getConcept());
          
          // 4. 【条件：何も編集せずに保存しても、画像無しのプロトタイプにならないこと】
          // 新しい画像ファイルが選択されている（空でない）場合のみ、画像データを上書きする
          if (form.getImage() != null && !form.getImage().isEmpty()) {
              currentPrototype.setImage(form.getImage().getBytes());
          }
          
          // 5. データベースの情報を更新（UPDATE）する
          prototypeRepository.update(currentPrototype); 
          
      } catch (Exception e) {
          System.out.println("データ更新エラー：" + e);
          return "redirect:/";
      }
      
      // 6. 【条件：正しく編集できた場合は、プロトタイプ詳細ページへ遷移すること】
      // トップページではなく、編集したプロトタイプの詳細画面（/prototypes/detail/{id}）へリダイレクトします
      return "redirect:/prototypes/detail/" + prototypeId;
  }  


  // コメント投稿処理
  @PostMapping("/prototypes/{prototypeId}/comment")
  public String createComment(
      @PathVariable("prototypeId") Integer prototypeId,
      @ModelAttribute("commentForm") @Validated in.tech_camp.protospace_c.form.CommentForm form,
      BindingResult result,
      @AuthenticationPrincipal CustomUserDetail userDetails,
      Model model) {

      // バリデーションエラーがある場合、詳細画面に留まる
      if (result.hasErrors()) {
          PrototypeEntity prototype = prototypeRepository.findById(prototypeId);
          model.addAttribute("prototype", prototype);
          // エラーメッセージを渡す（HTML側の th:if="${errorMessages}" に対応させる場合）
          model.addAttribute("errorMessages", result.getAllErrors());
          return "prototypes/detail";
      }

      // エラーがなければDBに保存
      CommentEntity comment = new CommentEntity();
      comment.setText(form.getText());
      comment.setPrototypeId(prototypeId);
      comment.setUserId(userDetails.getUser().getId());

      commentRepository.insert(comment);

      // 投稿後は元の詳細画面にリダイレクト
      return "redirect:/prototypes/detail/" + prototypeId;
  }
}
