import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ])
  email = new FormControl('', [
    Validators.required,
    Validators.email
  ])

  firstName = new FormControl('', [
    Validators.required,
  ])

  lastName = new FormControl('',[
    Validators.required
  ])

  gender = new FormControl('' , [
    Validators.required
  ])

  city = new FormControl('' , [
    Validators.required
  ])

  age = new FormControl('', [
    Validators.required,
    Validators.min(18),
    Validators.max(120)
  ])
  password = new  FormControl('', [
    Validators.required,
    Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/gm)
  ])
  confirm_password = new FormControl('', [
    Validators.required
  ])
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
    firstName: this.firstName,
    lastName: this.lastName,
    city: this.city,
    gender: this.gender,
    email: this.email,
    age: this.age,
    password: this.password,
    confirm_password: this.confirm_password,
    // phoneNumber: this.phoneNumber
  })

  register() {
    this.showAlert = true
    this.alertMsg = 'Please wait! Your account is being created.'
    this.alertColor = 'blue'
  }
}
