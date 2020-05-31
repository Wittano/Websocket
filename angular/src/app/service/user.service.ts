import {Injectable} from '@angular/core';
import {User} from '../models/user';
import {ErrorService} from './error.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl: string;

  constructor(private errorService: ErrorService, private router: Router) {
    this.apiUrl = 'http://localhost:8080';
  }

  authorization(user: User) {
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
      .then(async (response: Response) => {
        if (response.status >= 300) {
          throw new Error(await response.text());
        }

        return response.text();
      })
      .then(text => {
        localStorage.setItem('token', text);
        this.errorService.changeErrorMessage(null);
        this.router.navigate(['home']);
      })
      .catch(() => {
        this.errorService.changeErrorMessage("Incorrect login or password");
      });
  }

  register(user: User) {
    fetch(`${this.apiUrl}/user`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: user.username,
        password: user.password,
        email: user.email,
        gender: user.gender === 'female' ? 1 : 0,
        birthday: user.birthday
      })
    })
      .then(async (respone: Response) => {
        if (respone.status > 300) {
          throw new Error(await respone.text());
        }

        await this.router.navigate(['']);
      })
      .catch((err: Error) => {
        this.errorService.changeErrorMessage(err.message);
      });
  }

  logout() {
    if (localStorage.getItem('token')) {
      localStorage.removeItem('token');
    }

    this.router.navigate(['']);
  }
}
