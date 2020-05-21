import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { ErrorService } from './error.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl: string;

  constructor(private errorSevice: ErrorService, private router: Router) {
    this.apiUrl = 'http://localhost:8080';
  }

  authoriaztion(user: User) {
    fetch(`${this.apiUrl}/authenticate`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: user.username,
        password: user.password
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
        this.router.navigate(['home']);
      })
      .catch((err: Error) => {
        this.errorSevice.changeErrorMessage(err.message);
      });
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['']);
  }
}