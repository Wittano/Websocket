import { Component, OnInit } from '@angular/core';
import { Login } from '../interface/Login';
import { FormGroup, FormControl } from '@angular/forms';
import { UserService } from '../service/user.service';
import { User } from '../models/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements Login {
  nameCorrect: boolean;
  passwordCorrect: boolean;
  emailCorrect: boolean;
  gender: string;
  name: string;
  password: string;
  email: string;

  genderInputs = new FormGroup({
    female: new FormControl(),
    male: new FormControl()
  });

  regex = `^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$`;

  constructor(private userService: UserService) {}

  choiceGender(gender: string) {
    const unchecked = (element: HTMLInputElement) => {
      element.checked = false;
    };

    this.gender = gender;

    if (gender === 'female') {
      unchecked(document.querySelector('#male'));
    } else {
      unchecked(document.querySelector('#female'));
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

  getName($event) {
    this.name = $event;
  }

  getPassword($event) {
    this.password = $event;
  }

  getEmail($event) {
    this.email = $event;
  }

  register() {
    const birthday: HTMLInputElement = document.querySelector('#brithday');

    this.userService.register(
      new User(
        this.name,
        this.password,
        this.email,
        this.gender,
        birthday.value
      )
    );
  }
}
