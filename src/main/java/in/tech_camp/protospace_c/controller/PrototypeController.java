package in.tech_camp.protospace_c.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import in.tech_camp.protospace_c.repository.PrototypeRepository;
@Controller
public class PrototypeController {

@Autowired
private PrototypeRepository prototypeRepository;

}
