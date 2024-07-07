import { Component,OnInit } from '@angular/core';
import { flush } from '@angular/core/testing';
import { TokenStorageServiceService } from 'src/app/services/authentication/token-storage-service.service';
import { ModalService } from 'src/app/services/modal-service/modal.service';
import { ShareServiceService } from 'src/app/services/share-service/share-service.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {


  isLoggedIn: boolean = false;
  currentUser : string = ''
  username : string  = ''

  constructor(public modal: ModalService ,
      private localstorage : TokenStorageServiceService,
      private shareService : ShareServiceService
  ) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
      this.isLoggedIn = true
    })
   }

  ngOnInit(): void {
    this.loadHeader() 
    console.log("Checkkkkkkkkkkk", this.localstorage.getTokenFromLocalStorage())
  }

  public loadHeader() :void {
    if(this.localstorage.getTokenFromLocalStorage() ){
      this.currentUser = JSON.parse(this.localstorage.getUserFromLocalStorage()).username;
      this.username =  JSON.parse(this.localstorage.getUserFromLocalStorage()).username;
    }

    if(this.localstorage.getTokenFromLocalStorage() != "TOKEN NOT FOUND"){
      this.isLoggedIn = true
      console.log("acasdcasdhcasjdhcasdcadadjhasd đã đăng nhập")
    }
    console.log(this.isLoggedIn)
    this.getUsernameAccount()
  }

  openModal($event: Event) {
    $event.preventDefault()

    this.modal.toggleModal('auth')
  }

  public getUsernameAccount() :string{
    const username = JSON.parse(this.localstorage.getUserFromLocalStorage()).username
    console.log(username ?? "uknown user" , " ==========================")
    return username ?? "uknown user"
  }

}

