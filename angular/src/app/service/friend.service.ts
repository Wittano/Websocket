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

    this.errorService.changeErrorMessage('');
    return fetch(`${this.apiUrl}/friend/${username}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    }).then((response: Response) => {
      if (response.status >= 300) {
        throw new Error("User isn't exist");
      }

      return response.json();
    }).catch((err: Error) => {
      this.errorService.changeErrorMessage(err.message);
    });
  }

  async addFriend(username: string, friend: string): Promise<boolean> {
    return await this.friendOperation(username, friend, 'POST');
  }

  async removeFriend(username: string, friend: string): Promise<boolean> {
    return await this.friendOperation(username, friend, 'DELETE');
  }

  private async friendOperation(username: string, friend: string, httpMethod: string): Promise<boolean> {
    const token: string = localStorage.getItem('token');

    return await fetch(`${this.apiUrl}/friend/${username}/${friend}`, {
      method: httpMethod,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    }).then((response: Response) => {
      if (response.status >= 300) {
        throw new Error(`${friend} isn't exist`);
      }

      console.log(httpMethod == 'POST' ? `Added ${friend}` : `Removed ${friend}`);
      return true;
    }).catch((error: Error) => {
      this.errorService.changeErrorMessage(error.message);
      return false;
    });
  }
}
