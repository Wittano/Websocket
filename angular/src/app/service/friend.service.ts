import {Injectable} from '@angular/core';
import {ErrorService} from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class FriendService {

  private apiUrl: string;

  constructor(private errorService: ErrorService) {
    this.apiUrl = 'http://localhost:8080';
  }

  getFriends(username: string): Promise<string[] | void> {
    const token: string = localStorage.getItem('token');

    return fetch(`${this.apiUrl}/friend/${username}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    }).then(async (response: Response) => {
      if (response.status >= 300) {
        throw new Error(await response.text());
      }

      this.errorService.changeErrorMessage('');
      return response.json();
    }).catch((err: Error) => {
      this.errorService.changeErrorMessage(err.message);
    });
  }

  addFriend(username: string, friend: string) {
    this.friendOperation(username, friend, 'POST');
  }

  removeFriend(username: string, friend: string) {
    this.friendOperation(username, friend, 'DELETE');
  }

  private friendOperation(username: string, friend: string, httpMethod: string): void {
    const token: string = localStorage.getItem('token');

    fetch(`${this.apiUrl}/friend/${username}/${friend}`, {
      method: httpMethod,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    }).then(async (response: Response) => {
      if (response.status >= 300) {
        throw new Error(await response.text());
      }

      console.log(httpMethod == 'POST' ? `Added ${friend}` : `Removed ${friend}`);
      this.errorService.changeErrorMessage('');
    }).catch((err: Error) => {
      this.errorService.changeErrorMessage(err.message);
    });
  }
}
