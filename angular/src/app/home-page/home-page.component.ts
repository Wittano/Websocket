import { Component, AfterViewChecked, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import * as jwtToken from 'jwt-decode';
import { JwtToken } from '../models/jwt-token';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements AfterViewChecked, AfterViewInit {
  username: string;

  constructor(private router: Router, private userService: UserService) {
    const token: string = localStorage.getItem('token');
    const decodeToken: JwtToken = jwtToken<JwtToken>(token);

    this.username = decodeToken.sub;
  }

  ngAfterViewChecked(): void {}

  ngAfterViewInit() {
    if (localStorage.getItem('token') == null) {
      this.router.navigate(['']);
    }
  }

  logout() {
    this.userService.logout();
  }
}
