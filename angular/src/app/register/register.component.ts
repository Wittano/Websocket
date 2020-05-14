import { Component, OnInit } from '@angular/core';
import { Login } from '../interface/Login'
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements Login {

  nameCorrect: boolean;
  passwordCorrect: boolean;
  emailCorrect: boolean;

  genderInputs = new FormGroup({
    female: new FormControl,
    male: new FormControl
  });
  
  regex = `^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$`

  constructor() { }

  choiceGender(gender: string){
    const unchecked = (element: HTMLInputElement) => {
      element.checked = false;
    }

    if(gender == 'female'){
      unchecked(document.querySelector("#male"));
    }
    else{
      unchecked(document.querySelector("#female"));
    }
  }

  nameValid($event: boolean): void {
    this.nameCorrect = $event;
  }

  passwordValid($event: boolean): void {
    this.passwordCorrect = $event;
  }

  emailValid($event: boolean): void {
    this.emailCorrect = $event;
  }

}
