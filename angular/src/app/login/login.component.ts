import { Component, OnInit } from '@angular/core';
import { Login } from '../interface/Login';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { ErrorService } from '../service/error.service';
import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements Login, OnInit {
  nameCorrect: boolean;
  passwordCorrect: boolean;
  username: string;
  password: string;
  errorMessage: string;

  constructor(
    private userService: UserService,
    private router: Router,
    private errorSevice: ErrorService
  ) {
    this.nameCorrect = true;
  }

  ngOnInit() {
    this.errorSevice.errorMessage.subscribe((error: string) => {
      this.errorMessage = error;
    });
  }

  nameValid($event: boolean) {
    this.nameCorrect = $event;
  }

  passwordValid($event: boolean) {
    this.passwordCorrect = $event;
  }

  getUsername($event) {
    this.username = $event;
  }

  getPassword($event) {
    this.password = $event;
  }

  login(): void {
    this.userService.authoriaztion(new User(this.username, this.password));
  }
}
