import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from 'src/app/services/authentication/authenticate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credentials = {
    username: '',
    password: ''
  }
  errMessage: String = '';

  constructor(private authenticate: AuthenticateService,
   
  ) { }

  ngOnInit(): void {
  }

  login() {
    this.authenticate.login(this.credentials).subscribe(
      data => {
        console.log("đăng nhập thành công" , data)
      
      },error => {
       console.log("lỗi đăng nhập", error)
      }
    )
    console.log(this.credentials)
  }

}
