#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
1. 톰캣이 실행되고 'ServletContext'가 생성된 후 'ContextLoaderListener.contextInitialized()' 메서드를 호출한다
2. 'DispatcherServlet.init()'이 호출된다
3. 'RequestMapping'이 생성되고 'RequestMapping.initMapping()'이 호출된다. 
4.  URL에 대해 각 컨트롤러가 매핑된다

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
1. DispatcherServlet.service(): 사용자 요청 처리
2. RequestMapping.findController(): 사용자 요청을 처리할 수 있는 컨트롤러 반환. HomeController가 반환됨
3. HomeController.execute(): ModelAndView 객체를 반환함. 뷰 이름은 home.jsp, 모델에는 questions가 담겨있음
4. View.render(): ModelAndView에서 View를 꺼내 render 호출. Model도 함께 넘겨준다
5. 사용자에게 화면이 렌더링된다


#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
