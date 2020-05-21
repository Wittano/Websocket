import { Component, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements AfterViewChecked {
  constructor(private router: Router, private userService: UserService) {}

  ngAfterViewChecked(): void {
    if (localStorage.getItem('token') == null) {
      this.router.navigate(['']);
    }
  }

  logout() {
    this.userService.logout();
  }
}
