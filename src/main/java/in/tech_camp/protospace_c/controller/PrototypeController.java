package in.tech_camp.protospace_c.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import in.tech_camp.protospace_c.repository.PrototypeRepository;


@Controller
public class PrototypeController {

@Autowired
private PrototypeRepository prototypeRepository;

//「/」にアクセスしたときの処理
@GetMapping("/")
public String index(Model model) {
//データベースからすべてのプロトタイプを取得して画面に渡す
model.addAttribute("prototypes", prototypeRepository.findAll());

//index.htmlを呼び出す
return "prototypes/index";
  
}

}
