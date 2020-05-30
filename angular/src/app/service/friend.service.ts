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
      if(response >= 300){
        throw new Error(await response.text());
      }

      console.log(`Added ${friend}!`);
    }).catch((err: Error) => {
      this.errorService.changeErrorMessage(err.message);
    });
  }
}
