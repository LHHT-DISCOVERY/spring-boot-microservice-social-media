import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from 'src/app/services/authentication/authenticate.service';
import { TokenStorageServiceService } from 'src/app/services/authentication/token-storage-service.service';
import { ModalService } from 'src/app/services/modal-service/modal.service';
import { ShareServiceService } from 'src/app/services/share-service/share-service.service';

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

  constructor(
    private authenticate: AuthenticateService,
    private localstorage: TokenStorageServiceService,
    private shareService: ShareServiceService,
    public modal: ModalService ,
   
  ) { }

  ngOnInit(): void {
  }

  login() {
    this.authenticate.login(this.credentials).subscribe(
      data => {
        console.log("đăng nhập thành công token : " , data.result.token)
        console.log("đăng nhập thành công user : " , JSON.stringify(this.credentials))
        this.localstorage.saveUserToLocalStorage(JSON.stringify(this.credentials))
        this.localstorage.saveTokenToLocalStorage(data.result.token)
        this.authenticate.isLoggedIn = true
        console.log("get username" , this.localstorage.getUserFromLocalStorage())
        this.shareService.sendClickEvent();
        this.modal.toggleModal('auth')
      },error => {
       console.log("lỗi đăng nhập", error)
      }
    )
    console.log(this.credentials)
  }

}
