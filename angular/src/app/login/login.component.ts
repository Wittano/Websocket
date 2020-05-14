import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  nameCorrect: boolean;
  passwordCorrect: boolean

  constructor() { 
    this.nameCorrect = true
  }

  nameValid($event: boolean){
    this.nameCorrect = $event;
  }

  passwordValid($event: boolean){
    this.passwordCorrect = $event;
  }
}
