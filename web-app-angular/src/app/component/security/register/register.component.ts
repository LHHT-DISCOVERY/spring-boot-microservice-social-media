import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { AuthenticateService } from 'src/app/services/authentication/authenticate.service';
import { userRegister } from 'src/app/model/userRegister';
import { ModalService } from 'src/app/services/modal-service/modal.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  availableRoles: string[] = ['USER', 'ADMIN'];
  constructor(private authenticate: AuthenticateService,
              public modal: ModalService ,
  ) { }

  ngOnInit(): void {
  }

  userData = {
    "username": "HuuKeeerd",
    "password": "12345678r",
    "firstName": "Lý Huỳnh Hữu",
    "lastName": "Kéređ",
    "dob": "2001-10-20",
    "city": null,
    "email": "lytri102@gmail.com",
    "gender": "male",
    "roles": ['USER']
  };

  username = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ])

  password = new  FormControl('', [
    Validators.required,
    // Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/gm)
  ])
  confirm_password = new FormControl('', [
    Validators.required
  ])

  firstName = new FormControl('', [
    Validators.required,
  ])

  lastName = new FormControl('',[
    Validators.required
  ])

  dob = new FormControl('', [
    Validators.required
  ])

  city = new FormControl('' , [
    Validators.required
  ])

  email = new FormControl('', [
    Validators.required,
    Validators.email
  ])

  gender = new FormControl('' , [
    Validators.required
  ])

  roles = new FormArray([new FormControl('USER')], Validators.required)

  // phoneNumber = new FormControl('', [
  //   Validators.required,
  //   Validators.minLength(13),
  //   Validators.maxLength(13)
  // ])

  showAlert = false
  alertMsg = 'Please wait! Your account is being created.'
  alertColor = 'blue'

  registerForm = new FormGroup({
    username: this.username,
    password: this.password,
    firstName: this.firstName,
    lastName: this.lastName,
    dob: this.dob,
    city: this.city,
    gender: this.gender,
    email: this.email,
    roles : this.roles
  })
  
 

  register() {
    const requestData : userRegister = {
      username: this.registerForm.value.username || '',
      password: this.registerForm.value.password|| '',
      firstName: this.registerForm.value.firstName|| '',
      lastName: this.registerForm.value.lastName|| '',
      dob: this.registerForm.value.dob|| '',
      city: this.registerForm.value.city|| '',
      gender: this.registerForm.value.gender|| '',
      email: this.registerForm.value.email || '',
      roles: this.registerForm.value.roles || String['']
    };
  
    this.authenticate.signup(requestData).subscribe(
      (data) => {
        console.log("Đăng ký thành công", data);
        this.modal.toggleModal('auth')
      },
      (error) => {
        console.log("Đăng ký thất bại: " + JSON.stringify(error));
      }
    );
  
    this.showAlert = true;
    this.alertMsg = 'Please wait! Your account is being created..';
    this.alertColor = 'blue';
  }
}
