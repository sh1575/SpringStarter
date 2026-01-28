package hello.hello_spring.controller;


import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

//    @GetMapping("hello")
//    public String hello(Model model){
//        model.addAttribute("name" , "lee");
//        return "hello";
//    }


    // html 템플릿에 GET 파라미터를 저장한다. (정적 데이터 전달)
    @GetMapping("hello")
    public void hello(Model model) {
        model.addAttribute("name", "lee");
    }

    // html 템플릿에 GET 파라미터를 저장한다. (동적 데이터 전달)
    @GetMapping("hello-mvc")
    // 반환 타입이 string인 경우 = 맵핑이랑 내가 찾는게 다를때
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    // @ResponseBody  - http body부에 직접 데이터를 추가 , return 값 그대로 반환
    // @Controller가 아닌 @RestController를 사용하면 하위 메서드에 모두
    // @ResponseBody 가 붙은것과 같은 취급을함
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    // 웹에서 객체는 항상 json : 데이터 객체 반환시 기본 json 변환,
    // 기본 getter&setter 사용
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // Lombok 활용
    // request에서 name이 매개변수의 이름과 동일하면 @RequestParam 생략 가능
    @GetMapping("bye-api")
    @ResponseBody
    public Bye byeApi(String name){
        Bye bye = new Bye();
        bye.setName(name);
        return bye;
    }


    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    //  Lombok 어노테이션 : 자동으로 get,set,toString 모두 자동 작성
    @Data
    static class Bye{
        private String name;
    }

}

