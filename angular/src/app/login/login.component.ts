import { Component } from '@angular/core';
import { Login } from '../interface/Login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements Login {
  nameCorrect: boolean;
  passwordCorrect: boolean;
  username: string;
  password: string;
  errormsg: string;

  constructor(private router: Router) {
    this.nameCorrect = true;
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
    fetch('http://localhost:8080/authenticate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: this.username,
        password: this.password
      })
    })
      .then((value: Response) => {
        if (value.status >= 300) {
          throw new Error('Invalid login or password');
        }

        return value.text();
      })
      .then(text => {
        localStorage.setItem('token', text);
      })
      .catch(err => {
        if (localStorage.getItem('token')) {
          localStorage.clear();
        }

        console.error(err);
        return '';
      });

    if (localStorage.getItem('token')) {
      this.router.navigate(['main']);
    }
  }
}
