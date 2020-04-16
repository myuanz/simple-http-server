package example


import fun.myuan.slask._

import scala.util.control.Exception


class BaseView() extends HTMLResponse("Index") {
  addLink(
    "stylesheet",
    "https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css",
  )
  addScript("https://code.jquery.com/jquery-3.4.1.slim.min.js")
  addScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js")
  addScript("https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js")

  def newContent(): String = ""

  content =
    s"""<nav class="navbar navbar-expand-lg navbar-light bg-light">
       |  <a class="navbar-brand" href="/">Index</a>
       |  <div class="collapse navbar-collapse" id="navbarSupportedContent">
       |    <ul class="navbar-nav mr-auto">
       |      <li class="nav-item">
       |        <a class="nav-link" href="/">登录</span></a>
       |      </li>
       |      <li class="nav-item">
       |        <a class="nav-link" href="/register">注册</a>
       |      </li>
       |    </ul>
       |  </div>
       |
       |</nav>
       |<div class='container' style='padding: 5%'>${newContent()}</div>
       |""".stripMargin
}

class Index(context: Context) extends BaseView {
  Components
  override def newContent(): String = Components.FormCompt("登录", "Login", showRegister = true)
}
class Register(context: Context) extends BaseView {
  override def newContent(): String = Components.FormCompt("注册", "Register")
}

class Login(context: Context) extends BaseView {
  
  override def newContent(): String = {
    var _newContent = "未找到数据"

    println(context.url().path, context.url().method, context.form().form)

    try {
      val form = context.form()
      Users.checkPassword(form.get("email"), form.get("password"))
      _newContent = s"欢迎你: ${context.form().get("email", "")}"

    } catch {
      case EmailError(msg) =>
    }
    if(context.form().get("email", "") != ""){
    }
    _newContent
  }
}
class RegisterPost(context: Context) extends BaseView {
  override def newContent(): String = "注册成功"
}
case class MainView() {
  var view = new Blueprints
  view.+=(Router("GET", "/", context => new Index(context)))
  view.+=(Router("GET", "/register", context => new Register(context)))
  view.+=(Router("POST", "/Login", context => new Login(context)))
  view.+=(Router("POST", "/Register", context => new RegisterPost(context)))
  view.+=(Router("GET", "/favicon.ico", context => new StaticResponse("favicon.ico")))

}
